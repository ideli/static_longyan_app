package com.greatbee.lanchui.task;

import com.greatbee.bean.constant.LanchuiConstant;
import com.lanchui.commonBiz.bean.*;
import com.lanchui.commonBiz.bean.constant.CommonBizConstant;
import com.lanchui.commonBiz.manager.DispatchDriver;
import com.lanchui.commonBiz.manager.RedstarCommonManager;
import com.lanchui.commonBiz.manager.RedstarTaskLogManager;
import com.xiwa.base.bean.search.SearchBean;
import com.xiwa.base.bean.search.ext.IntSearch;
import com.xiwa.base.bean.search.ext.MultiSearchBean;
import com.xiwa.base.bean.search.ext.TextSearch;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.util.CollectionUtil;
import com.xiwa.base.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by niu on 2016/6/1.
 */
@Component(value = "shoppingMallInputDailyTask")
public class ShoppingMallInputDailyTask implements LanchuiConstant, CommonBizConstant {

    @Autowired
    private RedstarCommonManager redstarCommonManager;

    @Autowired
    private RedstarTaskLogManager redstarTaskLogManager;

    public void excute() throws ManagerException {

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //今日
        Integer year = calendar.get(Calendar.YEAR);
        Integer month = calendar.get(Calendar.MONTH) + 1;
        Integer day = calendar.get(Calendar.DAY_OF_MONTH);


        calendar.add(Calendar.DAY_OF_MONTH, -1);
        //昨日
        Integer _year = calendar.get(Calendar.YEAR);
        Integer _month = calendar.get(Calendar.MONTH) + 1;
        Integer _day = calendar.get(Calendar.DAY_OF_MONTH);


        String remark = "商场录入日报";

/*任务开始日志*/
        Long startTime = System.currentTimeMillis();
        RedstarTaskLog redstarTaskLog = new RedstarTaskLog();
        redstarTaskLog.setTaskClassName(this.getClass().getSimpleName());
        redstarTaskLog.setLogDatetime(new Date());
        redstarTaskLog.setRemark(remark);
        redstarTaskLog.setAction(taskLogStart);
        redstarTaskLogManager.addBean(redstarTaskLog);
/*over*/

        //商场列表
        TextSearch typeSearch = new TextSearch("mallType");
        typeSearch.setSearchValue(Default_MallType);
        List<RedstarShoppingMall> shoppingMallList = redstarCommonManager.getDataList(RedstarShoppingMall.class, typeSearch);

//        List<RedstarMallEmployee> mallEmployeeList;
//        IntSearch mallIdSearch;
//        IntSearch userIdSearch;

        MultiSearchBean multiSearchBean;
        MultiSearchBean _multiSearchBean;

        //今日
        IntSearch yearSearch = new IntSearch("year");
        yearSearch.setSearchValue(String.valueOf(year));

        IntSearch monthSearch = new IntSearch("month");
        monthSearch.setSearchValue(String.valueOf(month));

        IntSearch daySearch = new IntSearch("day");
        daySearch.setSearchValue(String.valueOf(day));

        //昨日
        IntSearch _yearSearch = new IntSearch("year");
        _yearSearch.setSearchValue(String.valueOf(_year));

        IntSearch _monthSearch = new IntSearch("month");
        _monthSearch.setSearchValue(String.valueOf(_month));

        IntSearch _daySearch = new IntSearch("day");
        _daySearch.setSearchValue(String.valueOf(_day));


        //当天数据
        multiSearchBean = new MultiSearchBean();
        multiSearchBean.addSearchBean(monthSearch);
        multiSearchBean.addSearchBean(yearSearch);
        multiSearchBean.addSearchBean(daySearch);
        //当月商场日报数据列表
        List<RedstarShoppingMallDayInput> currentDayDataList = redstarCommonManager.getDataList(RedstarShoppingMallDayInput.class, multiSearchBean);


        //昨天数据
        _multiSearchBean = new MultiSearchBean();
        _multiSearchBean.addSearchBean(_monthSearch);
        _multiSearchBean.addSearchBean(_yearSearch);
        _multiSearchBean.addSearchBean(_daySearch);
        //昨天商场日报数据列表
        List<RedstarShoppingMallDayInput> lastDayDataList = redstarCommonManager.getDataList(RedstarShoppingMallDayInput.class, _multiSearchBean);


        for (RedstarShoppingMall mall : shoppingMallList) {
//            mallIdSearch = new IntSearch("shoppingMallId");
//            mallIdSearch.setSearchValue(String.valueOf(mall.getId()));
            //商场员工
//            mallEmployeeList = redstarCommonManager.getDataList(RedstarMallEmployee.class, mallIdSearch);

            //当前商场总数
            Integer mallCommunityCount = 0;
            Integer mallMemberCount = 0;

            Integer _mallCommunityCount = 0;
            Integer _mallMemberCount = 0;

            //查询商场下员工日报
//            for (RedstarMallEmployee me:mallEmployeeList){
//                userIdSearch=new IntSearch("employeeId");
//                userIdSearch.setSearchValue(String.valueOf(me.getEmployeeId()));
//                multiSearchBean = new MultiSearchBean();
//                multiSearchBean.addSearchBean(userIdSearch);
//                multiSearchBean.addSearchBean(monthSearch);
//                multiSearchBean.addSearchBean(yearSearch);
//                multiSearchBean.addSearchBean(daySearch);
//                List<RedstarEmployeeDayInput> dayList = redstarCommonManager.getDataList(RedstarEmployeeDayInput.class,multiSearchBean);
//                if(!CollectionUtils.isEmpty(dayList)){
//                    for (RedstarEmployeeDayInput data:dayList){
//                        mallCommunityCount+=data.getInputCommunityAmount();
//                        mallMemberCount+=data.getInputMemberAmount();
//                    }
//                }
//
//
//                _multiSearchBean = new MultiSearchBean();
//                _multiSearchBean.addSearchBean(userIdSearch);
//                _multiSearchBean.addSearchBean(_monthSearch);
//                _multiSearchBean.addSearchBean(_yearSearch);
//                _multiSearchBean.addSearchBean(_daySearch);
//                List<RedstarEmployeeDayInput> _dayList = redstarCommonManager.getDataList(RedstarEmployeeDayInput.class,_multiSearchBean);
//                if(!CollectionUtils.isEmpty(_dayList)){
//                    for (RedstarEmployeeDayInput data:dayList){
//                        _mallCommunityCount+=data.getInputCommunityAmount();
//                        _mallMemberCount+=data.getInputMemberAmount();
//                    }
//                }
//            }


            //今天
            String currentReportSql = "select sum(inputCommunityAmount),sum(inputMemberAmount) from xiwa_redstar_mall_employee,xiwa_redstar_report_employee_input_daily where xiwa_redstar_mall_employee.employeeId=xiwa_redstar_report_employee_input_daily.employeeId and xiwa_redstar_mall_employee.shoppingMallId=" + mall.getId() + " and xiwa_redstar_report_employee_input_daily.year=" + year + " and xiwa_redstar_report_employee_input_daily.month=" + month + " and xiwa_redstar_report_employee_input_daily.day=" + day + ";";
            List<Object[]> currentReportList = redstarCommonManager.queryBySql(currentReportSql);
            if (CollectionUtil.isValid(currentReportList)) {
                Object[] item = currentReportList.get(0);
                if (item.length >= 2) {
                    mallCommunityCount = DataUtil.getInt(item[0], 0);
                    mallMemberCount = DataUtil.getInt(item[1], 0);
                }
            }

            //昨日
            String lastReportSql = "select sum(inputCommunityAmount),sum(inputMemberAmount) from xiwa_redstar_mall_employee,xiwa_redstar_report_employee_input_daily where xiwa_redstar_mall_employee.employeeId=xiwa_redstar_report_employee_input_daily.employeeId and xiwa_redstar_mall_employee.shoppingMallId=" + mall.getId() + " and xiwa_redstar_report_employee_input_daily.year=" + _year + " and xiwa_redstar_report_employee_input_daily.month=" + _month + " and xiwa_redstar_report_employee_input_daily.day=" + _day + ";";
            List<Object[]> lastReportList = redstarCommonManager.queryBySql(lastReportSql);
            if (CollectionUtil.isValid(lastReportList)) {
                Object[] item = lastReportList.get(0);
                if (item.length >= 2) {
                    _mallCommunityCount = DataUtil.getInt(item[0], 0);
                    _mallMemberCount = DataUtil.getInt(item[1], 0);
                }
            }

/*
            //当天数据
            multiSearchBean = new MultiSearchBean();
            multiSearchBean.addSearchBean(monthSearch);
            multiSearchBean.addSearchBean(yearSearch);
            multiSearchBean.addSearchBean(daySearch);
            multiSearchBean.addSearchBean(mallIdSearch);
           // List<RedstarShoppingMallDayInput>  currentMonthDataList = redstarCommonManager.getDataList(RedstarShoppingMallDayInput.class,multiSearchBean);
*/

            //今天数据
            saveOrUpdateData(mall, mallCommunityCount, mallMemberCount, year, month, day, currentDayDataList);

/*            //昨天数据
            _multiSearchBean = new MultiSearchBean();
            _multiSearchBean.addSearchBean(_monthSearch);
            _multiSearchBean.addSearchBean(_yearSearch);
            _multiSearchBean.addSearchBean(_daySearch);
            _multiSearchBean.addSearchBean(mallIdSearch);*/

            //昨天数据
            saveOrUpdateData(mall, _mallCommunityCount, _mallMemberCount, _year, _month, _day, lastDayDataList);


        }



/*任务结束日志*/
        redstarTaskLog = new RedstarTaskLog();
        redstarTaskLog.setTaskClassName(this.getClass().getSimpleName());
        redstarTaskLog.setLogDatetime(new Date());
        redstarTaskLog.setRemark(remark);
        redstarTaskLog.setAction(taskLogEnd);
        redstarTaskLog.setExecuteTime(System.currentTimeMillis() - startTime);
        redstarTaskLogManager.addBean(redstarTaskLog);
/*over*/
    }

    //更新或添加商场数据
    private void saveOrUpdateData(RedstarShoppingMall mall, Integer mallCommunityCount, Integer mallMemberCount, Integer year, Integer month, Integer day, List<RedstarShoppingMallDayInput> dataList) throws ManagerException {
        RedstarShoppingMallDayInput redstarShoppingMallDayInput = null;
        if (!CollectionUtils.isEmpty(dataList)) {
            for (RedstarShoppingMallDayInput data : dataList) {
                if (data.getShoppingMallId() == mall.getId()) {
                    redstarShoppingMallDayInput = data;
                }
            }
        }
        if (redstarShoppingMallDayInput == null) {
            redstarShoppingMallDayInput = new RedstarShoppingMallDayInput();
            redstarShoppingMallDayInput.setShoppingMallId(mall.getId());
            redstarShoppingMallDayInput.setShoppingMallName(mall.getName());
            redstarShoppingMallDayInput.setYear(year);
            redstarShoppingMallDayInput.setMonth(month);
            redstarShoppingMallDayInput.setDay(day);
            redstarShoppingMallDayInput.setInputCommunityAmount(mallCommunityCount);
            redstarShoppingMallDayInput.setInputMemberAmount(mallMemberCount);
            redstarCommonManager.addData(redstarShoppingMallDayInput);
        } else {
            if (redstarShoppingMallDayInput.getInputCommunityAmount() == mallCommunityCount
                    && redstarShoppingMallDayInput.getInputMemberAmount() == mallMemberCount) {
            } else {
                redstarShoppingMallDayInput.setInputCommunityAmount(mallCommunityCount);
                redstarShoppingMallDayInput.setInputMemberAmount(mallMemberCount);
                redstarCommonManager.updateData(redstarShoppingMallDayInput);
            }
        }

    }

}
