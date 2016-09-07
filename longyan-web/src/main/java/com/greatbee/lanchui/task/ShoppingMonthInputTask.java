package com.greatbee.lanchui.task;

import com.greatbee.bean.constant.LanchuiConstant;
import com.lanchui.commonBiz.bean.*;
import com.lanchui.commonBiz.manager.*;
import com.xiwa.base.bean.search.SearchBean;
import com.xiwa.base.bean.search.ext.IntSearch;
import com.xiwa.base.bean.search.ext.MultiSearchBean;
import com.xiwa.base.bean.search.ext.TextSearch;
import com.xiwa.base.manager.ManagerException;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * REDSTAR-93
 * TASK：商场排名计算
 * Created by lenovo on 2016/4/27.
 */
//商场月录入统计
@Component(value = "shoppingMonthInputTask")
public class ShoppingMonthInputTask implements LanchuiConstant {


    @Autowired
    private DispatchDriver dispatchDriver;

    @Autowired
    private RedstarMallEmployeeManager redstarMallEmployeeManager;

    @Autowired
    private RedstarMallMonthManager redstarMallMonthManager;

    @Autowired
    private RedstarTaskLogManager redstarTaskLogManager;

    @Autowired
    private RedstarCommonManager redstarCommonManager;


    public void Execute() throws JobExecutionException {
        try {
            this.mallMonthInput();
        } catch (ManagerException e) {
            e.printStackTrace();
        }
    }

    public void mallMonthInput() throws ManagerException {

        Long startTime = System.currentTimeMillis();
        RedstarTaskLog redstarTaskLog = new RedstarTaskLog();
        redstarTaskLog.setTaskClassName(this.getClass().getSimpleName());
        redstarTaskLog.setLogDatetime(new Date());
        redstarTaskLog.setRemark("商场月录入统计");
        redstarTaskLog.setAction(taskLogStart);
        redstarTaskLogManager.addBean(redstarTaskLog);


        TextSearch typeSearch = new TextSearch("mallType");
        typeSearch.setSearchValue(Default_MallType);

        //获取所有商场
        List<RedstarShoppingMall> mallList = dispatchDriver.getRedstarShoppingMallManager().searchIdentify(typeSearch);


        MultiSearchBean multiSearchBean;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        Integer year = calendar.get(Calendar.YEAR);
        Integer month = calendar.get(Calendar.MONTH) + 1;

        IntSearch yearSearch = new IntSearch("year");
        yearSearch.setSearchValue(String.valueOf(year));

        IntSearch monthSearch = new IntSearch("month");
        monthSearch.setSearchValue(String.valueOf(month));


        multiSearchBean = new MultiSearchBean();
        multiSearchBean.addSearchBean(monthSearch);
        multiSearchBean.addSearchBean(yearSearch);
        //当月商场日报数据列表
        List<RedstarMallMonth>  currentMonthDataList = redstarCommonManager.getDataList(RedstarMallMonth.class,multiSearchBean);



        //上月
        calendar.add(Calendar.MONTH,-1);
        Integer _year = calendar.get(Calendar.YEAR);
        Integer _month = calendar.get(Calendar.MONTH) + 1;

        IntSearch lastYearSearch = new IntSearch("year");
        lastYearSearch.setSearchValue(String.valueOf(_year));

        IntSearch lastMonthSearch = new IntSearch("month");
        lastMonthSearch.setSearchValue(String.valueOf(_month));


        //昨天数据
        multiSearchBean = new MultiSearchBean();
        multiSearchBean.addSearchBean(lastYearSearch);
        multiSearchBean.addSearchBean(lastMonthSearch);
        //昨天商场日报数据列表
        List<RedstarMallMonth>  lastMonthDataList = redstarCommonManager.getDataList(RedstarMallMonth.class,multiSearchBean);




        IntSearch mallIdSearch;//商场id
        IntSearch empSearch;//员工id
        MultiSearchBean inputMonthSearch;

        for (RedstarShoppingMall m : mallList) {

            mallIdSearch = new IntSearch("shoppingMallId");
            mallIdSearch.setSearchValue(String.valueOf(m.getId()));

            //获取商场下的员工
            List<RedstarMallEmployee> empList = redstarMallEmployeeManager.searchIdentify(mallIdSearch);
            if(CollectionUtils.isEmpty(empList)){
                continue;
            }

            Integer communityCount = 0;
            Integer memberCount = 0;

            Integer lastCommunityCount = 0;
            Integer lastMemberCount = 0;


            //所有员工
            for (RedstarMallEmployee me : empList) {
                inputMonthSearch = new MultiSearchBean();

                empSearch = new IntSearch("employeeId");
                empSearch.setSearchValue(String.valueOf(me.getEmployeeId()));

                inputMonthSearch.addSearchBean(empSearch);
                inputMonthSearch.addSearchBean(yearSearch);
                inputMonthSearch.addSearchBean(monthSearch);

                //当月数据
                List<RedstarEmployeeMonth> monthList = dispatchDriver.getRedstarEmployeeMonthManager().searchIdentify(inputMonthSearch);
                //计算录入总数量
                if(!CollectionUtils.isEmpty(monthList)){
                    for (RedstarEmployeeMonth redstarEmployeeMonth : monthList) {
                        memberCount += redstarEmployeeMonth.getInputMemberAmount();
                        communityCount += redstarEmployeeMonth.getInputCommunityAmount();
                    }
                }

                //上月数据
                inputMonthSearch = new MultiSearchBean();
                inputMonthSearch.addSearchBean(empSearch);
                inputMonthSearch.addSearchBean(lastYearSearch);
                inputMonthSearch.addSearchBean(lastMonthSearch);
                List<RedstarEmployeeMonth> lastMonthList = dispatchDriver.getRedstarEmployeeMonthManager().searchIdentify(inputMonthSearch);
                //计算录入总数量
                if(!CollectionUtils.isEmpty(lastMonthList)){
                    for (RedstarEmployeeMonth redstarEmployeeMonth : lastMonthList) {
                        lastMemberCount += redstarEmployeeMonth.getInputMemberAmount();
                        lastCommunityCount += redstarEmployeeMonth.getInputCommunityAmount();
                    }
                }
            }

            //当前月
/*            inputMonthSearch = new MultiSearchBean();
            inputMonthSearch.addSearchBean(yearSearch);
            inputMonthSearch.addSearchBean(monthSearch);
            inputMonthSearch.addSearchBean(mallIdSearch);*/
            saveOrUpdateData(m,communityCount,memberCount,year,month,currentMonthDataList);

            //上月
/*            inputMonthSearch = new MultiSearchBean();
            inputMonthSearch.addSearchBean(lastYearSearch);
            inputMonthSearch.addSearchBean(lastMonthSearch);
            inputMonthSearch.addSearchBean(mallIdSearch);*/
            saveOrUpdateData(m,lastCommunityCount,lastMemberCount,_year,_month,lastMonthDataList);
        }


        redstarTaskLog = new RedstarTaskLog();
        redstarTaskLog.setTaskClassName(this.getClass().getSimpleName());
        redstarTaskLog.setLogDatetime(new Date());
        redstarTaskLog.setRemark("商场月录入统计");
        redstarTaskLog.setAction(taskLogEnd);
        redstarTaskLog.setExecuteTime(System.currentTimeMillis()-startTime);
        redstarTaskLogManager.addBean(redstarTaskLog);
    }


    private void saveOrUpdateData(RedstarShoppingMall m,Integer communityCount,Integer memeberCount,
                              Integer year,Integer month,List<RedstarMallMonth> dataList) throws ManagerException {


        RedstarMallMonth redstarMallMonth=null;

        //遍历月数据
        if (!CollectionUtils.isEmpty(dataList)){
            for (RedstarMallMonth data:dataList){
                if (m.getId()==data.getShoppingMallId()){
                    redstarMallMonth=data;
                }
            }
        }

        //添加或更新
        if (redstarMallMonth==null) {
            redstarMallMonth = new RedstarMallMonth();
            redstarMallMonth.setShoppingMallId(m.getId());
            redstarMallMonth.setShoppingMallName(m.getName());
            redstarMallMonth.setInputCommunityAmount(communityCount);
            redstarMallMonth.setInputMemberAmount(memeberCount);
            redstarMallMonth.setMonth(month);
            redstarMallMonth.setYear(year);
            redstarMallMonthManager.addBean(redstarMallMonth);
        }else {
            if(redstarMallMonth.getInputCommunityAmount()==communityCount&&redstarMallMonth.getInputMemberAmount()==memeberCount){

            }else{
                redstarMallMonth.setShoppingMallId(m.getId());
                redstarMallMonth.setShoppingMallName(m.getName());
                redstarMallMonth.setInputCommunityAmount(communityCount);
                redstarMallMonth.setInputMemberAmount(memeberCount);
                redstarMallMonth.setMonth(month);
                redstarMallMonth.setYear(year);
                redstarMallMonthManager.updateBean(redstarMallMonth);
            }
        }

    }





}