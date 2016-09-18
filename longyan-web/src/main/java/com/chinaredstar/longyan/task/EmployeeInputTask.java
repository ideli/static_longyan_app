package com.chinaredstar.longyan.task;

import com.chinaredstar.commonBiz.bean.RedstarEmployee;
import com.chinaredstar.commonBiz.bean.RedstarEmployeeMonth;
import com.chinaredstar.commonBiz.bean.RedstarTaskLog;
import com.chinaredstar.longyan.bean.constant.LanchuiConstant;
import com.chinaredstar.commonBiz.manager.DispatchDriver;
import com.chinaredstar.commonBiz.manager.RedstarCommonManager;
import com.chinaredstar.commonBiz.manager.RedstarTaskLogManager;
import com.xiwa.base.bean.search.ext.IntSearch;
import com.xiwa.base.bean.search.ext.MultiSearchBean;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.util.CollectionUtil;
import com.xiwa.base.util.DataUtil;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * REDSTAR-89
 * Task：员工录入月报
 * Created by niu on 2016/4/26.
 */
@Component(value = "employeeInputTask")
public class EmployeeInputTask implements LanchuiConstant {

    @Autowired
    private DispatchDriver dispatchDriver;
    @Autowired
    private RedstarCommonManager redstarCommonManager;

    @Autowired
    private RedstarTaskLogManager redstarTaskLogManager;

    public void Execute() throws JobExecutionException {
        try {
            this.employeeMonthInput();
        } catch (ManagerException e) {
            e.printStackTrace();
        }
    }

    //员工录入月报  统计任务
    public void employeeMonthInput() throws ManagerException {

        //统一使用当前的日期
        Date currentDate = new Date();

        Long startTime = System.currentTimeMillis();
        RedstarTaskLog redstarTaskLog = new RedstarTaskLog();
        redstarTaskLog.setTaskClassName(this.getClass().getSimpleName());
        redstarTaskLog.setLogDatetime(currentDate);
        redstarTaskLog.setRemark("员工录入月报统计");
        redstarTaskLog.setAction(taskLogStart);
        redstarTaskLogManager.addBean(redstarTaskLog);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        //当前年月
        Integer year = calendar.get(Calendar.YEAR);
        Integer month = calendar.get(Calendar.MONTH) + 1;
        IntSearch yearSearch = new IntSearch("year");
        yearSearch.setSearchValue(String.valueOf(year));
        IntSearch monthSearch = new IntSearch("month");
        monthSearch.setSearchValue(String.valueOf(month));


        Calendar c1 = Calendar.getInstance();
        c1.setTime(currentDate);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //当月1号
        c1.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        String dayFirst = dateFormat.format(c1.getTime());
        //下月一号
        c1.add(Calendar.MONTH, 1);
        String dayLast = dateFormat.format(c1.getTime());

        //重新设置为当前日期
        c1.setTime(currentDate);
        c1.set(Calendar.DAY_OF_MONTH, 1);
        c1.add(Calendar.MONTH,-1);
/*        Integer lastYear = calendar.get(Calendar.YEAR);
        Integer lastMonth = calendar.get(Calendar.MONTH)+1;*/
        //上月1号
        String lastMonthDay = dateFormat.format(c1.getTime());

        Integer _year = c1.get(Calendar.YEAR);
        Integer _month = c1.get(Calendar.MONTH) + 1;
        IntSearch lastYearSearch = new IntSearch("year");
        lastYearSearch.setSearchValue(String.valueOf(_year));
        IntSearch lastMonthSearch = new IntSearch("month");
        lastMonthSearch.setSearchValue(String.valueOf(_month));

        //获取员工第一天和下个月第一天之间的录入 作为本月数据
//        StringBuffer stringBuffer =  new StringBuffer("SELECT COUNT(*) FROM xiwa_redstar_community WHERE  ownerId = ?  AND unix_timestamp(createDate) >= unix_timestamp('");
//        stringBuffer.append(dayFirst).append("')  AND unix_timestamp(createDate) < unix_timestamp('").append(dayLast).append("')    UNION ALL");
//        stringBuffer.append(" SELECT COUNT(*) FROM xiwa_redstar_member WHERE  ownerId =?  AND unix_timestamp(createDate) >= unix_timestamp('");
//        stringBuffer.append(dayFirst).append("')  AND unix_timestamp(createDate) < unix_timestamp('").append(dayLast).append("')");
//        String sql = stringBuffer.toString();
//        List<Object> paramList;
//        MultiSearchBean extDataSearch;
//        IntSearch employeeSearch;
//
//        Integer communityCount;
//        Integer memberCount;
//        //员工列表
//        IntSearch belongedSearch = new IntSearch("belongedId");
//        belongedSearch.setSearchValue(String.valueOf(LOG_BELONG_ID));
//        List<RedstarEmployee> employeeList = redstarCommonManager.getDataList(RedstarEmployee.class,belongedSearch);
//
//        for (RedstarEmployee redstarEmployee : employeeList) {
//            paramList=new ArrayList<Object>(2);
//            paramList.add(redstarEmployee.getId());
//            paramList.add(redstarEmployee.getId());
//            List resultList =  redstarCommonManager.excuteBySql(sql, paramList);
//            communityCount = Integer.parseInt(String.valueOf(resultList.get(0)));
//            memberCount = Integer.parseInt(String.valueOf(resultList.get(1)));
//
//            employeeSearch = new IntSearch("employeeId");
//            employeeSearch.setSearchValue(String.valueOf(redstarEmployee.getId()));
//
//            extDataSearch= new MultiSearchBean();
//            extDataSearch.addSearchBean(yearSearch);
//            extDataSearch.addSearchBean(monthSearch);
//            extDataSearch.addSearchBean(employeeSearch);
//
//            List<RedstarEmployeeMonth> dataList =  redstarCommonManager.getDataList(RedstarEmployeeMonth.class,extDataSearch);
//            RedstarEmployeeMonth employeeMonthData;
//            if (CollectionUtils.isEmpty(dataList)) {
//                employeeMonthData = new RedstarEmployeeMonth();
//                employeeMonthData.setInputMemberAmount(memberCount);
//                employeeMonthData.setInputCommunityAmount(communityCount);
//                employeeMonthData.setEmployeeId(redstarEmployee.getId());
//                employeeMonthData.setEmployeeXingMing(redstarEmployee.getXingMing());
//                employeeMonthData.setMonth(month);
//                employeeMonthData.setYear(year);
//                dispatchDriver.getRedstarEmployeeMonthManager().addBean(employeeMonthData);
//            }else {
//                employeeMonthData =dataList.get(0);
//                //数据无变化不执行更新
//                if(communityCount==employeeMonthData.getInputCommunityAmount()&&memberCount==employeeMonthData.getInputMemberAmount()){
//                    continue;
//                }else{
//                    employeeMonthData.setInputCommunityAmount(communityCount);
//                    employeeMonthData.setInputMemberAmount(memberCount);
//                    dispatchDriver.getRedstarEmployeeMonthManager().updateBean(employeeMonthData);
//                }
//            }
//        }

//        //当月最后一天
//        //计算录入小区数，录入小区总户数
//        String community_sql = "select ownerId,ownerXingming,count(*),sum(roomMount) from xiwa_redstar_community where ownerid>0 group by ownerId;";
//        List<Object[]> communityList = redstarCommonManager.queryBySql(community_sql);
//        for (Object[] community : communityList) {
//            if (community.length >= 4) {
//                try {
//                    int employeeId = DataUtil.getInt(community[0], 0);
//                    int inputCommunityAmount = DataUtil.getInt(community[2], 0);
//                    int inputCommunityRoomAmount = DataUtil.getInt(community[3], 0);
//                    RedstarEmployee employee = (RedstarEmployee) dispatchDriver.getRedstarEmployeeManager().getBean(employeeId);
//                    employee.setInputCommunityAmount(inputCommunityAmount);
//                    employee.setInputCommunityRoomAmount(inputCommunityRoomAmount);
//                    dispatchDriver.getRedstarEmployeeManager().updateBean(employee);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        //计算录入住宅数
//        String member_sql = "select ownerId,ownerXingming,count(*) from xiwa_redstar_member where ownerid>0 group by ownerId;";
//        List<Object[]> memberList = redstarCommonManager.queryBySql(member_sql);
//        for (Object[] member : memberList) {
//            if (member.length >= 3) {
//                try {
//                    int employeeId = DataUtil.getInt(member[0], 0);
//                    int inputMemberCount = DataUtil.getInt(member[2], 0);
//                    RedstarEmployee employee = (RedstarEmployee) dispatchDriver.getRedstarEmployeeManager().getBean(employeeId);
//                    employee.setInputMemberAmount(inputMemberCount);
//                    dispatchDriver.getRedstarEmployeeManager().updateBean(employee);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }

        //获取员工列表
        // List<RedstarEmployee> employeeList = dispatchDriver.getRedstarEmployeeManager().getBeanList();

       /* for (RedstarEmployee redstarEmployee : employeeList) {
            Integer communityCount = DataUtil.getInt(redstarEmployee.getInputCommunityAmount(), 0);
            Integer memberCount = DataUtil.getInt(redstarEmployee.getInputMemberAmount(), 0);



            //查询之前月的数据
            IntSearch employeeSearch = new IntSearch("employeeId");
            employeeSearch.setSearchValue(String.valueOf(redstarEmployee.getId()));
            List<RedstarEmployeeMonth> monthList = dispatchDriver.getRedstarEmployeeMonthManager().searchIdentify(employeeSearch);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH) + 1;

            RedstarEmployeeMonth employeeMonthData = null;

            //减去之前的统计
            if (!CollectionUtils.isEmpty(monthList)) {
                for (RedstarEmployeeMonth m : monthList) {
                    if (year.equals(m.getYear()) && month.equals(m.getMonth())) {
                        employeeMonthData = m;
                        continue;
                    }
                    communityCount -= DataUtil.getInt(m.getInputCommunityAmount(), 0);
                    memberCount -= DataUtil.getInt(m.getInputMemberAmount(), 0);
                }
            }
            //防止出现负数
            if (communityCount < 0) {
                communityCount = 0;
            }
            if (memberCount < 0) {
                memberCount = 0;
            }


            MultiSearchBean extDataSearch = new MultiSearchBean();
            IntSearch yearSearch = new IntSearch("year");
            yearSearch.setSearchValue(String.valueOf(year));

            IntSearch monthSearch = new IntSearch("month");
            monthSearch.setSearchValue(String.valueOf(month));

            extDataSearch.addSearchBean(yearSearch);
            extDataSearch.addSearchBean(monthSearch);
            extDataSearch.addSearchBean(employeeSearch);


            //新增或更新记录
            if (employeeMonthData == null) {
                employeeMonthData = new RedstarEmployeeMonth();
                employeeMonthData.setInputMemberAmount(memberCount);
                employeeMonthData.setInputCommunityAmount(communityCount);
                employeeMonthData.setEmployeeId(redstarEmployee.getId());
                employeeMonthData.setEmployeeXingMing(redstarEmployee.getXingMing());
                employeeMonthData.setMonth(month);
                employeeMonthData.setYear(year);
                dispatchDriver.getRedstarEmployeeMonthManager().addBean(employeeMonthData);
            } else {
                employeeMonthData.setInputCommunityAmount(communityCount);
                employeeMonthData.setInputMemberAmount(memberCount);
                dispatchDriver.getRedstarEmployeeMonthManager().updateBean(employeeMonthData);
            }
        }*/

        //本月数据
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(dayFirst);
        paramList.add(dayLast);

        List<Object> _paramList = new ArrayList<Object>();
        _paramList.add(lastMonthDay);
        _paramList.add(dayFirst);

        //计算小区录入月报
        StringBuffer stringBuffer = new StringBuffer("SELECT ownerId,ownerXingming,count(*) FROM xiwa_redstar_community WHERE  ownerid>0  AND unix_timestamp(createDate) >= unix_timestamp(?)");
        stringBuffer.append("  AND unix_timestamp(createDate) < unix_timestamp(?)").append("  group by ownerId;");
        List<Object[]> communityMonthList = redstarCommonManager.excuteBySql(stringBuffer.toString(), paramList);

        List<Object[]> communityLastMonthList = redstarCommonManager.excuteBySql(stringBuffer.toString(),_paramList);



        //计算住宅录入月报
        stringBuffer = new StringBuffer(" SELECT ownerId,ownerXingming,count(*) FROM xiwa_redstar_member WHERE ownerid>0  AND unix_timestamp(createDate) >= unix_timestamp(?)");
        stringBuffer.append("  AND unix_timestamp(createDate) < unix_timestamp(?)").append("  group by ownerId;");
        List<Object[]> memberMonthList = redstarCommonManager.excuteBySql(stringBuffer.toString(), paramList);

        List<Object[]> memberLastMonthList = redstarCommonManager.excuteBySql(stringBuffer.toString(),_paramList);







        //计算小区录入总数
        String community_sql = "select ownerId,ownerXingming,count(*),sum(roomMount) from xiwa_redstar_community where ownerid>0 group by ownerId;";
        List<Object[]> communityList = redstarCommonManager.queryBySql(community_sql);
        //计算住宅录入总数
        String member_sql = "select ownerId,ownerXingming,count(*) from xiwa_redstar_member where ownerid>0 group by ownerId;";
        List<Object[]> memberList = redstarCommonManager.queryBySql(member_sql);

//        for (Object[] community : communityList) {
//            if (community.length >= 4) {
//                try {
//                    int employeeId = DataUtil.getInt(community[0], 0);
//                    int inputCommunityAmount = DataUtil.getInt(community[2], 0);
//                    int inputCommunityRoomAmount = DataUtil.getInt(community[3], 0);
//                    RedstarEmployee employee = (RedstarEmployee) dispatchDriver.getRedstarEmployeeManager().getBean(employeeId);
//                    employee.setInputCommunityAmount(inputCommunityAmount);
//                    employee.setInputCommunityRoomAmount(inputCommunityRoomAmount);
//                    dispatchDriver.getRedstarEmployeeManager().updateBean(employee);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        //计算录入住宅数
//        String member_sql = "select ownerId,ownerXingming,count(*) from xiwa_redstar_member where ownerid>0 group by ownerId;";
//        List<Object[]> memberList = redstarCommonManager.queryBySql(member_sql);
//        for (Object[] member : memberList) {
//            if (member.length >= 3) {
//                try {
//                    int employeeId = DataUtil.getInt(member[0], 0);
//                    int inputMemberCount = DataUtil.getInt(member[2], 0);
//                    RedstarEmployee employee = (RedstarEmployee) dispatchDriver.getRedstarEmployeeManager().getBean(employeeId);
//                    employee.setInputMemberAmount(inputMemberCount);
//                    dispatchDriver.getRedstarEmployeeManager().updateBean(employee);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }


        //员工列表
        IntSearch belongedSearch = new IntSearch("belongedId");
        belongedSearch.setSearchValue(String.valueOf(LOG_BELONG_ID));
        List<RedstarEmployee> employeeList = redstarCommonManager.getDataList(RedstarEmployee.class, belongedSearch);

        MultiSearchBean extDataSearch = new MultiSearchBean();
        extDataSearch.addSearchBean(yearSearch);
        extDataSearch.addSearchBean(monthSearch);
        List<RedstarEmployeeMonth> employeeMonthList = redstarCommonManager.getDataList(RedstarEmployeeMonth.class, extDataSearch);


        extDataSearch = new MultiSearchBean();
        extDataSearch.addSearchBean(lastYearSearch);
        extDataSearch.addSearchBean(lastMonthSearch);
        List<RedstarEmployeeMonth> lastEmployeeMonthList = redstarCommonManager.getDataList(RedstarEmployeeMonth.class, extDataSearch);


        for (RedstarEmployee redstarEmployee : employeeList) {
            //录入月报数字
            Integer communityMonthCount = 0;
            Integer memberMonthCount = 0;

            //上月数据
            Integer lastCommunityMonthCount = 0;
            Integer lastMemberMonthCount = 0;

            //录入总数字
            Integer communityCount = 0;
            Integer communityMemberCount = 0;
            Integer memberCount = 0;

            //遍历社区录入月报结果
            if(CollectionUtil.isValid(communityMonthList)){
                for (Object[] item : communityMonthList) {
                    if (item.length >= 3) {
                        Integer empId = DataUtil.getInt(item[0], 0);
                        if (DataUtil.getInt(empId, 0) == DataUtil.getInt(redstarEmployee.getId(), 0)) {
                            communityMonthCount = DataUtil.getInt(item[2], 0);
                        }
                    }
                }
            }

            //上月
            if(CollectionUtil.isValid(communityLastMonthList)){
                for (Object[] item : communityLastMonthList) {
                    if (item.length >= 3) {
                        Integer empId = DataUtil.getInt(item[0], 0);
                        if (DataUtil.getInt(empId, 0) == DataUtil.getInt(redstarEmployee.getId(), 0)) {
                            lastCommunityMonthCount = DataUtil.getInt(item[2], 0);
                        }
                    }
                }
            }



            //遍历住宅录入月报结果
            if(CollectionUtil.isValid(memberMonthList)){
                for (Object[] item : memberMonthList) {
                    if (item.length >= 3) {
                        Integer empId = DataUtil.getInt(item[0], 0);
                        if (DataUtil.getInt(empId, 0) == DataUtil.getInt(redstarEmployee.getId(), 0)) {
                            memberMonthCount = DataUtil.getInt(item[2], 0);
                        }
                    }
                }
            }

            //上月
            if(CollectionUtil.isValid(memberLastMonthList)){
                for (Object[] item : memberLastMonthList) {
                    if (item.length >= 3) {
                        Integer empId = DataUtil.getInt(item[0], 0);
                        if (DataUtil.getInt(empId, 0) == DataUtil.getInt(redstarEmployee.getId(), 0)) {
                            lastMemberMonthCount = DataUtil.getInt(item[2], 0);
                        }
                    }
                }
            }

            //遍历小区录入总数结果
            if(CollectionUtil.isValid(communityList)){
                for (Object[] item : communityList) {
                    if (item.length >= 4) {
                        Integer empId = DataUtil.getInt(item[0], 0);
                        if (DataUtil.getInt(empId, 0) == DataUtil.getInt(redstarEmployee.getId(), 0)) {
                            communityCount = DataUtil.getInt(item[2], 0);
                            communityMemberCount = DataUtil.getInt(item[3], 0);
                        }
                    }
                }
            }

            //遍历住宅录入总数结果
            if(CollectionUtil.isValid(memberList)){
                for (Object[] item : memberList) {
                    if (item.length >= 3) {
                        Integer empId = DataUtil.getInt(item[0], 0);
                        if (DataUtil.getInt(empId, 0) == DataUtil.getInt(redstarEmployee.getId(), 0)) {
                            memberCount = DataUtil.getInt(item[2], 0);
                        }
                    }
                }
            }


            RedstarEmployeeMonth employeeMonthData = null;
            //遍历月报
            if(CollectionUtil.isValid(employeeMonthList)){
                for (RedstarEmployeeMonth item : employeeMonthList) {
                    if (item.getEmployeeId() == redstarEmployee.getId()) {
                        employeeMonthData = item;
                    }
                }
            }
            //写入录入月报数据
            saveOrUpdateData(employeeMonthData,redstarEmployee,communityMonthCount,memberMonthCount,year,month);


            //上月
            employeeMonthData = null;
            //遍历月报
            if(CollectionUtil.isValid(lastEmployeeMonthList)){
                for (RedstarEmployeeMonth item : lastEmployeeMonthList) {
                    if (item.getEmployeeId() == redstarEmployee.getId()) {
                        employeeMonthData = item;
                    }
                }
            }
            saveOrUpdateData(employeeMonthData,redstarEmployee,lastCommunityMonthCount,lastMemberMonthCount,_year,_month);
/*            if (employeeMonthData == null) {
                employeeMonthData = new RedstarEmployeeMonth();
                employeeMonthData.setInputMemberAmount(memberMonthCount);
                employeeMonthData.setInputCommunityAmount(communityMonthCount);
                employeeMonthData.setEmployeeId(redstarEmployee.getId());
                employeeMonthData.setEmployeeXingMing(redstarEmployee.getXingMing());
                employeeMonthData.setMonth(month);
                employeeMonthData.setYear(year);
                dispatchDriver.getRedstarEmployeeMonthManager().addBean(employeeMonthData);
            } else {
                //数据无变化不执行更新
                if (communityMonthCount == employeeMonthData.getInputCommunityAmount() && memberMonthCount == employeeMonthData.getInputMemberAmount()) {
                    //如果数据没变化,不执行任何操作
                } else {
                    //如果数据有发生变化,执行更新操作
                    employeeMonthData.setInputCommunityAmount(communityMonthCount);
                    employeeMonthData.setInputMemberAmount(memberMonthCount);
                    dispatchDriver.getRedstarEmployeeMonthManager().updateBean(employeeMonthData);
                }
            }*/

            //写入员工录入总数
            if (communityCount == DataUtil.getInt(redstarEmployee.getInputCommunityAmount(), 0) &&
                    communityMemberCount == DataUtil.getInt(redstarEmployee.getInputCommunityRoomAmount(), 0) &&
                    memberCount == DataUtil.getInt(redstarEmployee.getInputMemberAmount(), 0)) {
                //如果数据没变化,不执行任何操作
            } else {
                //如果数据有发生变化,执行更新操作
                redstarEmployee.setInputCommunityAmount(communityCount);
                redstarEmployee.setInputCommunityRoomAmount(communityMemberCount);
                redstarEmployee.setInputMemberAmount(memberCount);
                dispatchDriver.getRedstarEmployeeManager().updateBean(redstarEmployee);
            }
        }


        redstarTaskLog = new RedstarTaskLog();
        redstarTaskLog.setTaskClassName(this.getClass().getSimpleName());
        redstarTaskLog.setLogDatetime(new Date());
        redstarTaskLog.setRemark("员工录入月报统计");
        redstarTaskLog.setAction(taskLogEnd);
        redstarTaskLog.setExecuteTime(System.currentTimeMillis() - startTime);
        redstarTaskLogManager.addBean(redstarTaskLog);
    }

    private  void saveOrUpdateData(RedstarEmployeeMonth employeeMonthData,RedstarEmployee redstarEmployee,Integer communityMonthCount,
                                   Integer memberMonthCount,Integer year,Integer month) throws ManagerException {

        if (employeeMonthData == null) {
            employeeMonthData = new RedstarEmployeeMonth();
            employeeMonthData.setInputMemberAmount(memberMonthCount);
            employeeMonthData.setInputCommunityAmount(communityMonthCount);
            employeeMonthData.setEmployeeId(redstarEmployee.getId());
            employeeMonthData.setEmployeeXingMing(redstarEmployee.getXingMing());
            employeeMonthData.setMonth(month);
            employeeMonthData.setYear(year);
            dispatchDriver.getRedstarEmployeeMonthManager().addBean(employeeMonthData);
        } else {
            //数据无变化不执行更新
            if (communityMonthCount == employeeMonthData.getInputCommunityAmount() && memberMonthCount == employeeMonthData.getInputMemberAmount()) {
                //如果数据没变化,不执行任何操作
            } else {
                //如果数据有发生变化,执行更新操作
                employeeMonthData.setInputCommunityAmount(communityMonthCount);
                employeeMonthData.setInputMemberAmount(memberMonthCount);
                dispatchDriver.getRedstarEmployeeMonthManager().updateBean(employeeMonthData);
            }
        }
    }


}
