package com.chinaredstar.longyan.task;

import com.chinaredstar.commonBiz.bean.*;
import com.chinaredstar.commonBiz.manager.*;
import com.greatbee.bean.constant.LanchuiConstant;
import com.xiwa.base.bean.search.SearchBean;
import com.xiwa.base.bean.search.ext.IntSearch;
import com.xiwa.base.bean.search.ext.MultiSearchBean;
import com.xiwa.base.bean.search.ext.TextSearch;
import com.xiwa.base.manager.ManagerException;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * REDSTAR-91
 * Created by niu on 2016/4/27.
 * 大区数据统计  中区 大区 全国 录入数据总数
 */
@Component(value = "organizationInputTask")
public class OrganizationInputTask implements LanchuiConstant {


    @Autowired
    private DispatchDriver dispatchDriver;

    @Autowired
    private RedstarShopMallOrganizationManager redstarShopMallOrganizationManager;

    @Autowired
    private RedstarMallMonthManager redstarMallMonthManager;

    @Autowired
    private RedstarOrganizationMonthManager redstarOrganizationMonthManager;

    @Autowired
    private RedstarTaskLogManager redstarTaskLogManager;

    @Autowired
    private RedstarCommonManager redstarCommonManager;


    public void Execute() throws JobExecutionException {
        try {
            this.OrganizationInput();
        } catch (ManagerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 1.根据parentId=-1先从redstar_shopping_mall_organization遍历出所有涉及到的组织。
     * 2，再由每个organization再遍历出他们的子节点（parentId和Id的关联）
     * 3.查出所有涉及组织的商场列表
     * 4.从xiwa_redstar_report_mall_input_month 查出所有涉及商场本月的社区录入数和住户录入数的情况。
     * 5.将sum（社区录入数）和 sum（住户录入数）写到 xiwa_redstar_report_organization_input_month
     */

    public void OrganizationInput() throws ManagerException {

        Long startTime = System.currentTimeMillis();
        RedstarTaskLog redstarTaskLog = new RedstarTaskLog();
        redstarTaskLog.setTaskClassName(this.getClass().getSimpleName());
        redstarTaskLog.setLogDatetime(new Date());
        redstarTaskLog.setRemark("大区数据统计");
        redstarTaskLog.setAction(taskLogStart);
        redstarTaskLogManager.addBean(redstarTaskLog);

        IntSearch intSearch = new IntSearch("parentId");
        //根节点
        intSearch.setSearchValue(Default_Root_Id);
        //父节点为-1集合
        List<RedstarShopMallOrganization> redstarShopMallOrganizationList = redstarShopMallOrganizationManager.searchIdentify(intSearch);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Integer year = calendar.get(Calendar.YEAR);
        Integer month = calendar.get(Calendar.MONTH) + 1;

        IntSearch yearSearch = new IntSearch("year");
        yearSearch.setSearchValue(String.valueOf(year));
        IntSearch monthSearch = new IntSearch("month");
        monthSearch.setSearchValue(String.valueOf(month));
        taskProcess(redstarShopMallOrganizationList,yearSearch,monthSearch,year,month);


        calendar.add(Calendar.MONTH,-1);

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;

        yearSearch.setSearchValue(String.valueOf(year));
        monthSearch.setSearchValue(String.valueOf(month));
        taskProcess(redstarShopMallOrganizationList, yearSearch, monthSearch, year, month);

/*
        //全国count
        Integer allMemberCount = 0;
        Integer allCommunityCount =0;

        //商场类型
        TextSearch typeSearch = new TextSearch("mallType");
        typeSearch.setSearchValue(Default_MallType);
        MultiSearchBean multiSearchBean;

        //查询所有组织
        for (RedstarShopMallOrganization rs : redstarShopMallOrganizationList) {

            //大区count
            Integer bigCommunityCount=0;
            Integer bigMemberCount=0;

            //rs 父id为-1的数据
            IntSearch parentIdSearch = new IntSearch("parentId");
            parentIdSearch.setSearchValue(String.valueOf(rs.getId()));

            //获取商场组织关系  查询大区的下级
            List<RedstarShopMallOrganization> dataList = redstarShopMallOrganizationManager.searchIdentify(parentIdSearch);

            if(CollectionUtils.isEmpty(dataList)){
                continue;
            }

            //遍历中区
            for (RedstarShopMallOrganization data : dataList) {

                multiSearchBean= new MultiSearchBean();

                Integer communityCount = 0;
                Integer memberCount = 0;

                IntSearch dataIdSearch = new IntSearch("organizationId");
                dataIdSearch.setSearchValue(String.valueOf(data.getId()));

                multiSearchBean.addSearchBean(dataIdSearch);
                multiSearchBean.addSearchBean(typeSearch);

                //中区下的所有商场
                List<RedstarShoppingMall> mallList = dispatchDriver.getRedstarShoppingMallManager().searchIdentify(multiSearchBean);

                if(CollectionUtils.isEmpty(mallList)){
                    continue;
                }


                //查询商场的统计数据  统计中区数据  中区下的所有商场数据
                for (RedstarShoppingMall m : mallList) {
                    MultiSearchBean extDataSearch = new MultiSearchBean();

                    IntSearch mallIdSearch = new IntSearch("shoppingMallId");
                    mallIdSearch.setSearchValue(String.valueOf(m.getId()));



                    extDataSearch.addSearchBean(mallIdSearch);
                    extDataSearch.addSearchBean(yearSearch);
                    extDataSearch.addSearchBean(monthSearch);

                    //当月商场录入情况
                    List<RedstarMallMonth> mallMonthList = redstarMallMonthManager.searchIdentify(extDataSearch);

                    //计算录入数据
                    if(!CollectionUtils.isEmpty(mallMonthList)){
                        for (RedstarMallMonth mallMonth : mallMonthList) {
                            communityCount += mallMonth.getInputCommunityAmount();
                            memberCount += mallMonth.getInputMemberAmount();
                        }
                    }
                }

                //大区统计
                bigCommunityCount+=communityCount;
                bigMemberCount+=memberCount;





                //查询中区统计
                MultiSearchBean dataSearch = new MultiSearchBean();
                dataSearch.addSearchBean(dataIdSearch);
                dataSearch.addSearchBean(yearSearch);
                dataSearch.addSearchBean(monthSearch);

                List<RedstarOrganizationMonth> redstarOrganizationMonthList = redstarOrganizationMonthManager.searchIdentify(dataSearch);

                RedstarOrganizationMonth _thisData;
                if (CollectionUtils.isEmpty(redstarOrganizationMonthList)) {
                    _thisData = new RedstarOrganizationMonth();
                    _thisData.setYear(year);
                    _thisData.setMonth(month);
                    _thisData.setInputMemberAmount(memberCount);
                    _thisData.setInputCommunityAmount(communityCount);
                    _thisData.setOrganizationId(data.getId());
                    _thisData.setOrganizationName(data.getName());
                    redstarOrganizationMonthManager.addBean(_thisData);
                }else {
                    _thisData = redstarOrganizationMonthList.get(0);
                    _thisData.setInputMemberAmount(memberCount);
                    _thisData.setInputCommunityAmount(communityCount);
                    redstarOrganizationMonthManager.updateBean(_thisData);
                }

            }


            //查询当前大区下是否存在商场

            multiSearchBean =new MultiSearchBean();

            IntSearch bigSearch = new IntSearch("organizationId");
            bigSearch.setSearchValue(String.valueOf(rs.getId()));

            multiSearchBean.addSearchBean(bigSearch);
            multiSearchBean.addSearchBean(typeSearch);
            //大区下的所有商场
            List<RedstarShoppingMall> bigMallList = dispatchDriver.getRedstarShoppingMallManager().searchIdentify(multiSearchBean);

            //如果大区存在商场
            if(!CollectionUtils.isEmpty(bigMallList)) {
                //查询当月数据
                for (RedstarShoppingMall m : bigMallList) {
                    MultiSearchBean extDataSearch = new MultiSearchBean();

                    IntSearch mallIdSearch = new IntSearch("shoppingMallId");
                    mallIdSearch.setSearchValue(String.valueOf(m.getId()));

                    extDataSearch.addSearchBean(mallIdSearch);
                    extDataSearch.addSearchBean(yearSearch);
                    extDataSearch.addSearchBean(monthSearch);

                    //当月商场录入情况
                    List<RedstarMallMonth> mallMonthList = redstarMallMonthManager.searchIdentify(extDataSearch);

                    //计算录入数据
                    if (!CollectionUtils.isEmpty(mallMonthList)) {
                        for (RedstarMallMonth mallMonth : mallMonthList) {
                            bigCommunityCount += mallMonth.getInputCommunityAmount();
                            bigMemberCount += mallMonth.getInputMemberAmount();
                        }
                    }
                }
            }


            //查询大区统计
            MultiSearchBean dataSearch = new MultiSearchBean();

            IntSearch dataIdSearch = new IntSearch("organizationId");
            dataIdSearch.setSearchValue(String.valueOf(rs.getId()));

            dataSearch.addSearchBean(dataIdSearch);
            dataSearch.addSearchBean(yearSearch);
            dataSearch.addSearchBean(monthSearch);

            List<RedstarOrganizationMonth> bigDataList = redstarOrganizationMonthManager.searchIdentify(dataSearch);

            RedstarOrganizationMonth bigData;

            if(CollectionUtils.isEmpty(bigDataList)){
                bigData = new RedstarOrganizationMonth();
                bigData.setYear(year);
                bigData.setMonth(month);
                bigData.setInputMemberAmount(bigMemberCount);
                bigData.setInputCommunityAmount(bigCommunityCount);
                bigData.setOrganizationId(rs.getId());
                bigData.setOrganizationName(rs.getName());
                redstarOrganizationMonthManager.addBean(bigData);
            }else{
                bigData = bigDataList.get(0);
                bigData.setInputMemberAmount(bigMemberCount);
                bigData.setInputCommunityAmount(bigCommunityCount);
                redstarOrganizationMonthManager.updateBean(bigData);
            }
            //所有数据
            //allMemberCount+=bigMemberCount;
            //allCommunityCount+=bigCommunityCount;
        }


        //2016 05 23 总数据查询员工当月

        String countSql = "SELECT IFNULL(sum(inputCommunityAmount),0),IFNULL(sum(inputMemberAmount),0) FROM xiwa_redstar_report_employee_input_month WHERE year=? AND month=?";

        List<Object> paramList = new ArrayList<Object>();
        paramList.add(year);
        paramList.add(month);

        List<Object[]> result = redstarCommonManager.excuteBySql(countSql,paramList);

        if(!CollectionUtils.isEmpty(result)){
            allCommunityCount = Integer.parseInt(String.valueOf(result.get(0)[0]));
            allMemberCount = Integer.parseInt(String.valueOf(result.get(0)[1]));
        }

        //总数据是否存在
        MultiSearchBean dataSearch = new MultiSearchBean();

        IntSearch dataIdSearch = new IntSearch("organizationId");
        dataIdSearch.setSearchValue(String.valueOf(Default_Root_Id));

        dataSearch.addSearchBean(dataIdSearch);
        dataSearch.addSearchBean(yearSearch);
        dataSearch.addSearchBean(monthSearch);

        List<RedstarOrganizationMonth> bigDataList = redstarOrganizationMonthManager.searchIdentify(dataSearch);

        RedstarOrganizationMonth totalData;

        if(CollectionUtils.isEmpty(bigDataList)){
            totalData = new RedstarOrganizationMonth();
            totalData.setYear(year);
            totalData.setMonth(month);
            totalData.setInputMemberAmount(allMemberCount);
            totalData.setInputCommunityAmount(allCommunityCount);
            totalData.setOrganizationId(Integer.parseInt(Default_Root_Id));
            totalData.setOrganizationName("全国");
            redstarOrganizationMonthManager.addBean(totalData);
        }else{
            totalData = bigDataList.get(0);
            totalData.setInputMemberAmount(allMemberCount);
            totalData.setInputCommunityAmount(allCommunityCount);
            redstarOrganizationMonthManager.updateBean(totalData);
        }
*/


        redstarTaskLog = new RedstarTaskLog();
        redstarTaskLog.setTaskClassName(this.getClass().getSimpleName());
        redstarTaskLog.setLogDatetime(new Date());
        redstarTaskLog.setRemark("大区数据统计");
        redstarTaskLog.setAction(taskLogEnd);
        redstarTaskLog.setExecuteTime(System.currentTimeMillis()-startTime);
        redstarTaskLogManager.addBean(redstarTaskLog);
    }





    private  void  taskProcess(List<RedstarShopMallOrganization> redstarShopMallOrganizationList,
                               SearchBean yearSearch,SearchBean monthSearch,Integer year,Integer month) throws ManagerException {

        //全国count
        Integer allMemberCount = 0;
        Integer allCommunityCount =0;

        //商场类型
        TextSearch typeSearch = new TextSearch("mallType");
        typeSearch.setSearchValue(Default_MallType);
        MultiSearchBean multiSearchBean;

        //查询所有组织
        for (RedstarShopMallOrganization rs : redstarShopMallOrganizationList) {

            //大区count
            Integer bigCommunityCount=0;
            Integer bigMemberCount=0;

            //rs 父id为-1的数据
            IntSearch parentIdSearch = new IntSearch("parentId");
            parentIdSearch.setSearchValue(String.valueOf(rs.getId()));

            //获取商场组织关系  查询大区的下级
            List<RedstarShopMallOrganization> dataList = redstarShopMallOrganizationManager.searchIdentify(parentIdSearch);

            if(CollectionUtils.isEmpty(dataList)){
                continue;
            }

            //遍历中区
            for (RedstarShopMallOrganization data : dataList) {

                multiSearchBean= new MultiSearchBean();

                Integer communityCount = 0;
                Integer memberCount = 0;

                IntSearch dataIdSearch = new IntSearch("organizationId");
                dataIdSearch.setSearchValue(String.valueOf(data.getId()));

                multiSearchBean.addSearchBean(dataIdSearch);
                multiSearchBean.addSearchBean(typeSearch);

                //中区下的所有商场
                List<RedstarShoppingMall> mallList = dispatchDriver.getRedstarShoppingMallManager().searchIdentify(multiSearchBean);

                if(CollectionUtils.isEmpty(mallList)){
                    continue;
                }


                //查询商场的统计数据  统计中区数据  中区下的所有商场数据
                for (RedstarShoppingMall m : mallList) {
                    MultiSearchBean extDataSearch = new MultiSearchBean();

                    IntSearch mallIdSearch = new IntSearch("shoppingMallId");
                    mallIdSearch.setSearchValue(String.valueOf(m.getId()));



                    extDataSearch.addSearchBean(mallIdSearch);
                    extDataSearch.addSearchBean(yearSearch);
                    extDataSearch.addSearchBean(monthSearch);

                    //当月商场录入情况
                    List<RedstarMallMonth> mallMonthList = redstarMallMonthManager.searchIdentify(extDataSearch);

                    //计算录入数据
                    if(!CollectionUtils.isEmpty(mallMonthList)){
                        for (RedstarMallMonth mallMonth : mallMonthList) {
                            communityCount += mallMonth.getInputCommunityAmount();
                            memberCount += mallMonth.getInputMemberAmount();
                        }
                    }
                }

                //大区统计
                bigCommunityCount+=communityCount;
                bigMemberCount+=memberCount;





                //查询中区统计
                MultiSearchBean dataSearch = new MultiSearchBean();
                dataSearch.addSearchBean(dataIdSearch);
                dataSearch.addSearchBean(yearSearch);
                dataSearch.addSearchBean(monthSearch);

                List<RedstarOrganizationMonth> redstarOrganizationMonthList = redstarOrganizationMonthManager.searchIdentify(dataSearch);

                RedstarOrganizationMonth _thisData;
                if (CollectionUtils.isEmpty(redstarOrganizationMonthList)) {
                    _thisData = new RedstarOrganizationMonth();
                    _thisData.setYear(year);
                    _thisData.setMonth(month);
                    _thisData.setInputMemberAmount(memberCount);
                    _thisData.setInputCommunityAmount(communityCount);
                    _thisData.setOrganizationId(data.getId());
                    _thisData.setOrganizationName(data.getName());
                    redstarOrganizationMonthManager.addBean(_thisData);
                }else {
                    _thisData = redstarOrganizationMonthList.get(0);
                    _thisData.setInputMemberAmount(memberCount);
                    _thisData.setInputCommunityAmount(communityCount);
                    redstarOrganizationMonthManager.updateBean(_thisData);
                }

            }


            //查询当前大区下是否存在商场

            multiSearchBean =new MultiSearchBean();

            IntSearch bigSearch = new IntSearch("organizationId");
            bigSearch.setSearchValue(String.valueOf(rs.getId()));

            multiSearchBean.addSearchBean(bigSearch);
            multiSearchBean.addSearchBean(typeSearch);
            //大区下的所有商场
            List<RedstarShoppingMall> bigMallList = dispatchDriver.getRedstarShoppingMallManager().searchIdentify(multiSearchBean);

            //如果大区存在商场
            if(!CollectionUtils.isEmpty(bigMallList)) {
                //查询当月数据
                for (RedstarShoppingMall m : bigMallList) {
                    MultiSearchBean extDataSearch = new MultiSearchBean();

                    IntSearch mallIdSearch = new IntSearch("shoppingMallId");
                    mallIdSearch.setSearchValue(String.valueOf(m.getId()));

                    extDataSearch.addSearchBean(mallIdSearch);
                    extDataSearch.addSearchBean(yearSearch);
                    extDataSearch.addSearchBean(monthSearch);

                    //当月商场录入情况
                    List<RedstarMallMonth> mallMonthList = redstarMallMonthManager.searchIdentify(extDataSearch);

                    //计算录入数据
                    if (!CollectionUtils.isEmpty(mallMonthList)) {
                        for (RedstarMallMonth mallMonth : mallMonthList) {
                            bigCommunityCount += mallMonth.getInputCommunityAmount();
                            bigMemberCount += mallMonth.getInputMemberAmount();
                        }
                    }
                }
            }


            //查询大区统计
            MultiSearchBean dataSearch = new MultiSearchBean();

            IntSearch dataIdSearch = new IntSearch("organizationId");
            dataIdSearch.setSearchValue(String.valueOf(rs.getId()));

            dataSearch.addSearchBean(dataIdSearch);
            dataSearch.addSearchBean(yearSearch);
            dataSearch.addSearchBean(monthSearch);

            List<RedstarOrganizationMonth> bigDataList = redstarOrganizationMonthManager.searchIdentify(dataSearch);

            RedstarOrganizationMonth bigData;

            if(CollectionUtils.isEmpty(bigDataList)){
                bigData = new RedstarOrganizationMonth();
                bigData.setYear(year);
                bigData.setMonth(month);
                bigData.setInputMemberAmount(bigMemberCount);
                bigData.setInputCommunityAmount(bigCommunityCount);
                bigData.setOrganizationId(rs.getId());
                bigData.setOrganizationName(rs.getName());
                redstarOrganizationMonthManager.addBean(bigData);
            }else{
                bigData = bigDataList.get(0);
                bigData.setInputMemberAmount(bigMemberCount);
                bigData.setInputCommunityAmount(bigCommunityCount);
                redstarOrganizationMonthManager.updateBean(bigData);
            }
            //所有数据
            //allMemberCount+=bigMemberCount;
            //allCommunityCount+=bigCommunityCount;
        }


        //2016 05 23 总数据查询员工当月

        String countSql = "SELECT IFNULL(sum(inputCommunityAmount),0),IFNULL(sum(inputMemberAmount),0) FROM xiwa_redstar_report_employee_input_month WHERE year=? AND month=?";

        List<Object> paramList = new ArrayList<Object>();
        paramList.add(year);
        paramList.add(month);

        List<Object[]> result = redstarCommonManager.excuteBySql(countSql,paramList);

        if(!CollectionUtils.isEmpty(result)){
            allCommunityCount = Integer.parseInt(String.valueOf(result.get(0)[0]));
            allMemberCount = Integer.parseInt(String.valueOf(result.get(0)[1]));
        }

        //总数据是否存在
        MultiSearchBean dataSearch = new MultiSearchBean();

        IntSearch dataIdSearch = new IntSearch("organizationId");
        dataIdSearch.setSearchValue(String.valueOf(Default_Root_Id));

        dataSearch.addSearchBean(dataIdSearch);
        dataSearch.addSearchBean(yearSearch);
        dataSearch.addSearchBean(monthSearch);

        List<RedstarOrganizationMonth> bigDataList = redstarOrganizationMonthManager.searchIdentify(dataSearch);

        RedstarOrganizationMonth totalData;

        if(CollectionUtils.isEmpty(bigDataList)){
            totalData = new RedstarOrganizationMonth();
            totalData.setYear(year);
            totalData.setMonth(month);
            totalData.setInputMemberAmount(allMemberCount);
            totalData.setInputCommunityAmount(allCommunityCount);
            totalData.setOrganizationId(Integer.parseInt(Default_Root_Id));
            totalData.setOrganizationName("全国");
            redstarOrganizationMonthManager.addBean(totalData);
        }else{
            totalData = bigDataList.get(0);
            totalData.setInputMemberAmount(allMemberCount);
            totalData.setInputCommunityAmount(allCommunityCount);
            redstarOrganizationMonthManager.updateBean(totalData);
        }


    }


}
