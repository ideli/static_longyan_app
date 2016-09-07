package com.greatbee.lanchui.task;

import com.greatbee.bean.constant.LanchuiConstant;
import com.lanchui.commonBiz.bean.RedstarCommunity;
import com.lanchui.commonBiz.bean.RedstarMallCommunity;
import com.lanchui.commonBiz.bean.RedstarShoppingMall;
import com.lanchui.commonBiz.bean.RedstarTaskLog;
import com.lanchui.commonBiz.manager.DispatchDriver;
import com.lanchui.commonBiz.manager.RedstarTaskLogManager;
import com.lanchui.commonBiz.manager.ext.SimpleRedstarMallCommunityManager;
import com.xiwa.base.bean.search.ext.IntSearch;
import com.xiwa.base.bean.search.ext.MultiSearchBean;
import com.xiwa.base.bean.search.ext.TextSearch;
import com.xiwa.base.manager.ManagerException;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by lenovo on 2016/4/26.
 */

/**
 * REDSTAR-90
 * 计算商场附近的小区
 * 列出商场周边小区Task
 */
@Component(value = "shopCommunityTask")
public class ShoppingTask implements LanchuiConstant {

   @Autowired
    private DispatchDriver dispatchDriver;

    @Autowired
    private SimpleRedstarMallCommunityManager redstarMallCommunityManager;

    @Autowired
    private RedstarTaskLogManager redstarTaskLogManager;

    public void Execute() throws JobExecutionException {
        try {
            this.getCommunity();
        } catch (ManagerException e) {
            e.printStackTrace();
        }
    }

    //根据商场辐射半径，列出所有商场周边的社区列表
    public void getCommunity() throws ManagerException {

        Long startTime = System.currentTimeMillis();
        RedstarTaskLog redstarTaskLog = new RedstarTaskLog();
        redstarTaskLog.setTaskClassName(this.getClass().getSimpleName());
        redstarTaskLog.setLogDatetime(new Date());
        redstarTaskLog.setRemark("计算商场附近的小区");
        redstarTaskLog.setAction(taskLogStart);
        redstarTaskLogManager.addBean(redstarTaskLog);


        //System.out.println("任务执行--->");


        //System.out.println("任务执行--->");

        //所有的商场数据
        List<RedstarShoppingMall> mallList = dispatchDriver.getRedstarShoppingMallManager().getBeanList();

        RedstarMallCommunity redstarMallCommunity;

        //商场数据
        for (RedstarShoppingMall m : mallList) {

            //System.out.println("任务执行--->" + m.getId());

            if (m.getRadiationRange() > 0) {
                Double longitude = m.getLng();
                Double latitude = m.getLat();

                if (latitude == null || longitude == null) {
                    continue;
                }

                //辐射小区
                List<Object[]> dataList = dispatchDriver.getRedstarCommunityManager().getDistanceDataList(longitude, latitude, m.getRadiationRange());

                if(CollectionUtils.isEmpty(dataList)){
                    continue;
                }

                for (Object[] obj : dataList) {
                    MultiSearchBean multiSearchBean = new MultiSearchBean();

                    IntSearch shoppingMallSearch = new IntSearch("shoppingMallId");
                    shoppingMallSearch.setSearchValue(String.valueOf(m.getId()));

                    IntSearch communitySearch = new IntSearch("communityId");
                    communitySearch.setSearchValue(String.valueOf(obj[0]));

                    multiSearchBean.addSearchBean(shoppingMallSearch);
                    multiSearchBean.addSearchBean(communitySearch);

                    //查询小区和商场关联
                    List<RedstarMallCommunity> mallCommunityList = redstarMallCommunityManager.searchIdentify(multiSearchBean);

                    //添加或更新
                    if (CollectionUtils.isEmpty(mallCommunityList)) {
                        redstarMallCommunity = new RedstarMallCommunity();
                        redstarMallCommunity.setCommunityId((Integer) obj[0]);
                        redstarMallCommunity.setCommunityName((String) obj[1]);
                        redstarMallCommunity.setMemberAmount((Integer) obj[2]);
                        if (obj[3] != null) {
                            redstarMallCommunity.setMemberInputAmount((Integer) obj[3]);
                        } else {
                            redstarMallCommunity.setMemberInputAmount(0);
                        }

                        redstarMallCommunity.setShoppingMallId(m.getId());
                        redstarMallCommunity.setShoppingMallName(m.getName());
                        redstarMallCommunity.setDistance((Double) obj[4]);

                        redstarMallCommunityManager.addBean(redstarMallCommunity);
                    } else {
                        redstarMallCommunity = mallCommunityList.get(0);
                        redstarMallCommunity.setCommunityId((Integer) obj[0]);
                        redstarMallCommunity.setCommunityName((String) obj[1]);
                        redstarMallCommunity.setMemberAmount(((Integer) obj[2]));

                        if (obj[3] != null) {
                            redstarMallCommunity.setMemberInputAmount((Integer) obj[3]);
                        } else {
                            redstarMallCommunity.setMemberInputAmount(0);
                        }

                        redstarMallCommunity.setShoppingMallId(m.getId());
                        redstarMallCommunity.setShoppingMallName(m.getName());
                        redstarMallCommunity.setDistance((Double) obj[4]);
                        redstarMallCommunityManager.updateBean(redstarMallCommunity);
                    }
                }

            } else if (m.getRadiationRange() == 0) {
                //查询商场所属区下的所有小区
                TextSearch textSearch = new TextSearch("cityCode");
                textSearch.setSearchKey(m.getCityCode());
                List<RedstarCommunity> dataList = dispatchDriver.getRedstarCommunityManager().searchIdentify(textSearch);

                if (CollectionUtils.isEmpty(dataList)){
                    continue;
                }

                //查询商场小区关联
                for (RedstarCommunity community : dataList) {

                    MultiSearchBean multiSearchBean = new MultiSearchBean();

                    IntSearch shoppingMallSearch = new IntSearch("shoppingMallId");
                    shoppingMallSearch.setSearchValue(String.valueOf(m.getId()));

                    IntSearch communitySearch = new IntSearch("communityId");
                    communitySearch.setSearchValue(String.valueOf(community.getId()));

                    multiSearchBean.addSearchBean(shoppingMallSearch);
                    multiSearchBean.addSearchBean(communitySearch);

                    List<RedstarMallCommunity> mallCommunityList = redstarMallCommunityManager.searchIdentify(multiSearchBean);

                    //添加或更新
                    if (CollectionUtils.isEmpty(mallCommunityList)) {
                        redstarMallCommunity = new RedstarMallCommunity();
                        redstarMallCommunity.setCommunityId(community.getId());
                        redstarMallCommunity.setCommunityName(community.getName());
                        redstarMallCommunity.setMemberAmount(community.getRoomMount());
                        redstarMallCommunity.setMemberInputAmount(community.getAlreadyInputAmount());
                        redstarMallCommunity.setShoppingMallId(m.getId());
                        redstarMallCommunity.setShoppingMallName(m.getName());
                        redstarMallCommunityManager.addBean(redstarMallCommunity);
                    } else {
                        redstarMallCommunity = mallCommunityList.get(0);
                        redstarMallCommunity.setCommunityId(community.getId());
                        redstarMallCommunity.setCommunityName(community.getName());
                        redstarMallCommunity.setMemberAmount(community.getRoomMount());
                        redstarMallCommunity.setMemberInputAmount(community.getAlreadyInputAmount());
                        redstarMallCommunity.setShoppingMallId(m.getId());
                        redstarMallCommunity.setShoppingMallName(m.getName());
                        redstarMallCommunityManager.updateBean(redstarMallCommunity);
                    }
                }
            }

        }


        redstarTaskLog = new RedstarTaskLog();
        redstarTaskLog.setTaskClassName(this.getClass().getSimpleName());
        redstarTaskLog.setLogDatetime(new Date());
        redstarTaskLog.setRemark("计算商场附近的小区");
        redstarTaskLog.setAction(taskLogEnd);
        redstarTaskLog.setExecuteTime(System.currentTimeMillis()-startTime);
        redstarTaskLogManager.addBean(redstarTaskLog);


    }

}
