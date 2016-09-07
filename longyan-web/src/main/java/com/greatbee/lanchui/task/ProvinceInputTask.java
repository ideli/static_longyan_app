package com.greatbee.lanchui.task;

import com.greatbee.bean.constant.LanchuiConstant;
import com.lanchui.commonBiz.bean.*;
import com.lanchui.commonBiz.manager.DispatchDriver;
import com.lanchui.commonBiz.manager.RedstarCommonManager;
import com.lanchui.commonBiz.manager.RedstarTaskLogManager;
import com.xiwa.base.bean.search.ext.IntSearch;
import com.xiwa.base.bean.search.ext.IntegerSearch;
import com.xiwa.base.bean.search.ext.TextSearch;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.util.CollectionUtil;
import com.xiwa.base.util.DataUtil;
import com.xiwa.base.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by niu on 2016/5/10.
 */
@Component(value = "provinceInputTask")
public class ProvinceInputTask implements LanchuiConstant {

    @Autowired
    private RedstarCommonManager redstarCommonManager;

    @Autowired
    private DispatchDriver dispatchDriver;

    @Autowired
    private RedstarTaskLogManager redstarTaskLogManager;

    public void provinceInput() throws ManagerException {

/*        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"任务开始执行---》");*/

        Long startTime = System.currentTimeMillis();    //todo 开始时间
        RedstarTaskLog redstarTaskLog = new RedstarTaskLog(); // todo log

        redstarTaskLog.setTaskClassName(this.getClass().getSimpleName());
        redstarTaskLog.setLogDatetime(new Date());
        redstarTaskLog.setRemark("省数据统计");
        redstarTaskLog.setAction(taskLogStart);
        redstarTaskLogManager.addBean(redstarTaskLog);

        List<DispatchProvince> provinceList = dispatchDriver.getDispatchProvinceManager().getBeanList();

        //所有省

        if (!CollectionUtils.isEmpty(provinceList)) {
            TextSearch textSearch;
            RedstarReportProvinceInput provinceInput;
            for (DispatchProvince data : provinceList) {

                Integer inputCommunityAmount = 0;
                Integer inputMemberAmount = 0;
                Integer inputCommunityRoomAmount = 0;

                textSearch = new TextSearch("provinceCode");
                textSearch.setSearchValue(data.getProvinceCode());
                List<RedstarCommunity> communityList = dispatchDriver.getRedstarCommunityManager().searchIdentify(textSearch);
                for (RedstarCommunity community : communityList) {
                    if (community != null) {
                        if (DataUtil.getInt(community.getOwnerId(), 0) > 0) {
                            inputCommunityAmount = inputCommunityAmount + 1;    //录入小区数
                            //小区总户数
                            inputCommunityRoomAmount = inputCommunityRoomAmount + DataUtil.getInt(community.getRoomMount(), 0);

                            IntSearch communityIdSearch = new IntSearch("communityId");
                            communityIdSearch.setSearchValue(StringUtil.getString(community.getId()));
                            List<RedStarMember> memberList = dispatchDriver.getRedStarMemberManager().searchIdentify(communityIdSearch);
                            if (CollectionUtil.isValid(memberList)) {
                                inputMemberAmount = inputMemberAmount + memberList.size();
                            }
                        }
                    }
                }
//                List<RedstarShoppingMall> mallList = dispatchDriver.getRedstarShoppingMallManager().searchIdentify(textSearch);
//
//
//               Integer employeeCount=0;
//               Integer inputCommunityAmount=0;
//               Integer inputMemberAmount=0;
//               Integer inputCommunityRoomAmount=0;
//
//
//               if(!CollectionUtils.isEmpty(mallList)){
//                    for (RedstarShoppingMall mall:mallList){
//                        employeeCount+=mall.getEmployeeCount();
//                        inputCommunityAmount+=mall.getInputCommunityAmount();
//                        inputMemberAmount+=mall.getInputMemberAmount();
//                        inputCommunityRoomAmount+=mall.getInputCommunityRoomAmount();
//                    }
//               }
//
                //查询当前数据是否存在
                textSearch = new TextSearch("provinceCode");
                textSearch.setSearchValue(data.getProvinceCode());
                List<RedstarReportProvinceInput> extList = redstarCommonManager.getDataList(RedstarReportProvinceInput.class, textSearch);

                if (CollectionUtils.isEmpty(extList)) {
                    provinceInput = new RedstarReportProvinceInput();
//                   provinceInput.setEmployeeCount(employeeCount);
                    provinceInput.setInputCommunityAmount(inputCommunityAmount);
                    provinceInput.setInputMemberAmount(inputMemberAmount);
                    provinceInput.setInputCommunityRoomAmount(inputCommunityRoomAmount);
                    provinceInput.setCreateDate(new Date());
                    provinceInput.setUpdateDate(new Date());
                    provinceInput.setProvince(data.getProvince());
                    provinceInput.setProvinceCode(data.getProvinceCode());
                    redstarCommonManager.addData(provinceInput);
                } else {
                    provinceInput = extList.get(0);
//                   provinceInput.setEmployeeCount(employeeCount);
                    if(provinceInput.getInputCommunityAmount()==inputCommunityAmount
                            && provinceInput.getInputMemberAmount()==inputMemberAmount
                            && provinceInput.getInputCommunityRoomAmount()==inputCommunityRoomAmount){
                    }else{
                        provinceInput.setInputCommunityAmount(inputCommunityAmount);
                        provinceInput.setInputMemberAmount(inputMemberAmount);
                        provinceInput.setInputCommunityRoomAmount(inputCommunityRoomAmount);
                        provinceInput.setUpdateDate(new Date());
                        redstarCommonManager.updateData(provinceInput);
                    }
                }
            }
        }


        redstarTaskLog = new RedstarTaskLog();
        redstarTaskLog.setTaskClassName(this.getClass().getSimpleName());
        redstarTaskLog.setLogDatetime(new Date());
        redstarTaskLog.setRemark("省数据统计");
        redstarTaskLog.setAction(taskLogEnd);
        redstarTaskLog.setExecuteTime(System.currentTimeMillis() - startTime);
        redstarTaskLogManager.addBean(redstarTaskLog);

/*
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"任务执行结束---》");
*/

    }


}
