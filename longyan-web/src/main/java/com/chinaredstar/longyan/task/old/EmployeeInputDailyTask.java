package com.chinaredstar.longyan.task.old;

import com.chinaredstar.nvwaBiz.bean.NvwaEmployee;
import com.chinaredstar.commonBiz.bean.RedstarEmployeeDayInput;
import com.chinaredstar.commonBiz.bean.RedstarTaskLog;
import com.chinaredstar.longyan.bean.constant.LanchuiConstant;
import com.chinaredstar.commonBiz.bean.constant.CommonBizConstant;
import com.chinaredstar.commonBiz.manager.DispatchDriver;
import com.chinaredstar.commonBiz.manager.RedstarCommonManager;
import com.chinaredstar.commonBiz.manager.RedstarTaskLogManager;
import com.xiwa.base.bean.search.ext.IntSearch;
import com.xiwa.base.bean.search.ext.MultiSearchBean;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.util.CollectionUtil;
import com.xiwa.base.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by niu on 2016/6/1.
 */
@Component(value = "employeeInputDailyTask")
public class EmployeeInputDailyTask implements LanchuiConstant, CommonBizConstant {

    @Autowired
    private RedstarCommonManager redstarCommonManager;

    @Autowired
    private DispatchDriver dispatchDriver;

    @Autowired
    private RedstarTaskLogManager redstarTaskLogManager;

    public void excute() throws ManagerException {

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        Integer year = calendar.get(Calendar.YEAR);
        Integer month = calendar.get(Calendar.MONTH) + 1;
        Integer day = calendar.get(Calendar.DAY_OF_MONTH);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String _today = dateFormat.format(date);


        calendar.add(Calendar.DAY_OF_MONTH,-1);
        String _yesterday = dateFormat.format(calendar.getTime());
        Integer _year = calendar.get(Calendar.YEAR);
        Integer _month = calendar.get(Calendar.MONTH) + 1;
        Integer _day = calendar.get(Calendar.DAY_OF_MONTH);



        String remark = "员工录入日报";

/*任务开始日志*/
        Long startTime = System.currentTimeMillis();
        RedstarTaskLog redstarTaskLog = new RedstarTaskLog();
        redstarTaskLog.setTaskClassName(this.getClass().getSimpleName());
        redstarTaskLog.setLogDatetime(new Date());
        redstarTaskLog.setRemark(remark);
        redstarTaskLog.setAction(taskLogStart);
        redstarTaskLogManager.addBean(redstarTaskLog);
/*over*/


        try {
            IntSearch belongedSearch = new IntSearch("belongedId");
            belongedSearch.setSearchValue(String.valueOf(LOG_BELONG_ID));
            List<NvwaEmployee> employeeList = redstarCommonManager.getDataList(NvwaEmployee.class, belongedSearch);

            IntSearch yearSearch = new IntSearch("year");
            yearSearch.setSearchValue(String.valueOf(year));

            IntSearch monthSearch = new IntSearch("month");
            monthSearch.setSearchValue(String.valueOf(month));

            IntSearch daySearch = new IntSearch("day");
            daySearch.setSearchValue(String.valueOf(day));


            IntSearch _yearSearch = new IntSearch("year");
            _yearSearch.setSearchValue(String.valueOf(_year));

            IntSearch _monthSearch = new IntSearch("month");
            _monthSearch.setSearchValue(String.valueOf(_month));

            IntSearch _daySearch = new IntSearch("day");
            _daySearch.setSearchValue(String.valueOf(_day));


            List<Object> todayList = new ArrayList<Object>();
            todayList.add(_today);

            //计算小区录入日报
            StringBuffer stringBuffer = new StringBuffer("SELECT ownerId,ownerXingming,count(*) FROM xiwa_redstar_community WHERE  ownerid>0  AND unix_timestamp(createDate)>=unix_timestamp(?)  group by ownerId;");
            List<Object[]> communityDayList = redstarCommonManager.excuteBySql(stringBuffer.toString(), todayList);

            //计算住宅录入日报
            stringBuffer = new StringBuffer(" SELECT ownerId,ownerXingming,count(*) FROM xiwa_redstar_member WHERE ownerid>0  AND unix_timestamp(createDate)>=unix_timestamp(?)   group by ownerId;");
            List<Object[]> memberDayList = redstarCommonManager.excuteBySql(stringBuffer.toString(), todayList);


            //重新计算昨天的数据
            List<Object> lastDayList = new ArrayList<Object>();
            lastDayList.add(_yesterday);
            lastDayList.add(_today);

            stringBuffer = new StringBuffer("SELECT ownerId,ownerXingming,count(*) FROM xiwa_redstar_community WHERE  ownerid>0  AND unix_timestamp(createDate)>=unix_timestamp(?)  AND unix_timestamp(createDate)<unix_timestamp(?)   group by ownerId;");
            List<Object[]> communityYesDayList = redstarCommonManager.excuteBySql(stringBuffer.toString(), lastDayList);

            stringBuffer = new StringBuffer(" SELECT ownerId,ownerXingming,count(*) FROM xiwa_redstar_member WHERE ownerid>0  AND unix_timestamp(createDate)>=unix_timestamp(?)  AND unix_timestamp(createDate)<unix_timestamp(?) group by ownerId;");
            List<Object[]> memberYesDayList = redstarCommonManager.excuteBySql(stringBuffer.toString(), lastDayList);


            MultiSearchBean multiSearchBean = new MultiSearchBean();
            multiSearchBean.addSearchBean(monthSearch);
            multiSearchBean.addSearchBean(yearSearch);
            multiSearchBean.addSearchBean(daySearch);

            List<RedstarEmployeeDayInput> dayList = redstarCommonManager.getDataList(RedstarEmployeeDayInput.class, multiSearchBean);

            MultiSearchBean _multiSearchBean = new MultiSearchBean();
            _multiSearchBean.addSearchBean(_monthSearch);
            _multiSearchBean.addSearchBean(_yearSearch);
            _multiSearchBean.addSearchBean(_daySearch);
            List<RedstarEmployeeDayInput> _dayList = redstarCommonManager.getDataList(RedstarEmployeeDayInput.class, _multiSearchBean);


            for (NvwaEmployee r : employeeList) {
                Integer communityCount = 0;
                Integer memberCount = 0;
                RedstarEmployeeDayInput employeeDayInput = null;
                //遍历社区录入日报结果
                if (CollectionUtil.isValid(communityDayList)) {
                    for (Object[] item : communityDayList) {
                        if (item.length >= 3) {
                            Integer empId = DataUtil.getInt(item[0], 0);
                            if (DataUtil.getInt(empId, 0) == DataUtil.getInt(r.getId(), 0)) {
                                communityCount = DataUtil.getInt(item[2], 0);
                            }
                        }
                    }
                }

                //遍历住宅录入日报结果
                if (CollectionUtil.isValid(memberDayList)) {
                    for (Object[] item : memberDayList) {
                        if (item.length >= 3) {
                            Integer empId = DataUtil.getInt(item[0], 0);
                            if (DataUtil.getInt(empId, 0) == DataUtil.getInt(r.getId(), 0)) {
                                memberCount = DataUtil.getInt(item[2], 0);
                            }
                        }
                    }
                }


                //遍历日报
                if (CollectionUtil.isValid(dayList)) {
                    for (RedstarEmployeeDayInput item : dayList) {
                        if (item.getEmployeeId() == r.getId()) {
                            employeeDayInput = item;
                        }
                    }
                }

                saveOrUpdateData(employeeDayInput,r,communityCount,memberCount,year,month,day);

/*                if (employeeDayInput == null) {
                    //不存在,Insert
                    employeeDayInput = new RedstarEmployeeDayInput();
                    employeeDayInput.setEmployeeId(r.getId());
                    employeeDayInput.setEmployeeXingMing(r.getXingMing());
                    employeeDayInput.setYear(year);
                    employeeDayInput.setMonth(month);
                    employeeDayInput.setDay(day);
                    employeeDayInput.setInputCommunityAmount(communityCount);
                    employeeDayInput.setInputMemberAmount(memberCount);
                    redstarCommonManager.addData(employeeDayInput);
                } else {
                    //存在,update
                    if (communityCount == employeeDayInput.getInputCommunityAmount() && memberCount == employeeDayInput.getInputMemberAmount()) {

                    } else {
                        employeeDayInput.setInputCommunityAmount(communityCount);
                        employeeDayInput.setInputMemberAmount(memberCount);
                        redstarCommonManager.updateData(employeeDayInput);
                    }
                }*/




                Integer _communityCount = 0;
                Integer _memberCount = 0;
                RedstarEmployeeDayInput _employeeDayInput = null;
                /**
                 * 昨天结果
                 */

                if (CollectionUtil.isValid(communityYesDayList)) {
                    for (Object[] item : communityYesDayList) {
                        if (item.length >= 3) {
                            Integer empId = DataUtil.getInt(item[0], 0);
                            if (DataUtil.getInt(empId, 0) == DataUtil.getInt(r.getId(), 0)) {
                                _communityCount = DataUtil.getInt(item[2], 0);
                            }
                        }
                    }
                }

                //遍历住宅录入日报结果
                if (CollectionUtil.isValid(memberYesDayList)) {
                    for (Object[] item : memberYesDayList) {
                        if (item.length >= 3) {
                            Integer empId = DataUtil.getInt(item[0], 0);
                            if (DataUtil.getInt(empId, 0) == DataUtil.getInt(r.getId(), 0)) {
                                _memberCount = DataUtil.getInt(item[2], 0);
                            }
                        }
                    }
                }


                //遍历日报
                if (CollectionUtil.isValid(_dayList)) {
                    for (RedstarEmployeeDayInput item : _dayList) {
                        if (item.getEmployeeId() == r.getId()) {
                            _employeeDayInput = item;
                        }
                    }
                }

                saveOrUpdateData(_employeeDayInput,r,_communityCount,_memberCount,_year,_month,_day);
/*
                if (_employeeDayInput == null) {
                    //不存在,Insert
                    _employeeDayInput = new RedstarEmployeeDayInput();
                    _employeeDayInput.setEmployeeId(r.getId());
                    _employeeDayInput.setEmployeeXingMing(r.getXingMing());
                    _employeeDayInput.setYear(year);
                    _employeeDayInput.setMonth(month);
                    _employeeDayInput.setDay(day);
                    _employeeDayInput.setInputCommunityAmount(_communityCount);
                    _employeeDayInput.setInputMemberAmount(_memberCount);
                    redstarCommonManager.addData(_employeeDayInput);
                } else {
                    //存在,update
                    if (_communityCount == _employeeDayInput.getInputCommunityAmount() && _memberCount == _employeeDayInput.getInputMemberAmount()) {

                    } else {
                        _employeeDayInput.setInputCommunityAmount(_communityCount);
                        _employeeDayInput.setInputMemberAmount(_memberCount);
                        redstarCommonManager.updateData(_employeeDayInput);
                    }
                }
*/


                /**
                 * 昨天结果
                 */

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
        } catch (Exception e) {
            e.printStackTrace();
            redstarTaskLog = new RedstarTaskLog();
            redstarTaskLog.setTaskClassName(this.getClass().getSimpleName());
            redstarTaskLog.setLogDatetime(new Date());
            redstarTaskLog.setRemark(remark);
            redstarTaskLog.setAction(taskError);
            redstarTaskLog.setExecuteTime(System.currentTimeMillis() - startTime);
            redstarTaskLogManager.addBean(redstarTaskLog);
        }

    }


    private void saveOrUpdateData(RedstarEmployeeDayInput employeeDayInput,
                                  NvwaEmployee r, Integer communityCount, Integer memberCount,
                                  Integer year, Integer month, Integer day) throws ManagerException {


        if (employeeDayInput == null) {
            //不存在,Insert
            employeeDayInput = new RedstarEmployeeDayInput();
            employeeDayInput.setEmployeeId(r.getId());
            employeeDayInput.setEmployeeXingMing(r.getXingMing());
            employeeDayInput.setYear(year);
            employeeDayInput.setMonth(month);
            employeeDayInput.setDay(day);
            employeeDayInput.setInputCommunityAmount(communityCount);
            employeeDayInput.setInputMemberAmount(memberCount);
            redstarCommonManager.addData(employeeDayInput);
        } else {
            //存在,update
            if (communityCount == employeeDayInput.getInputCommunityAmount() && memberCount == employeeDayInput.getInputMemberAmount()) {

            } else {
                employeeDayInput.setInputCommunityAmount(communityCount);
                employeeDayInput.setInputMemberAmount(memberCount);
                redstarCommonManager.updateData(employeeDayInput);
            }
        }



    }

}
