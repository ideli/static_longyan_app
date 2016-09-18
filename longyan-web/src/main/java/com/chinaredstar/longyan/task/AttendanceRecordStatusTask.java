package com.chinaredstar.longyan.task;

import com.chinaredstar.longyan.bean.constant.LanchuiConstant;
import com.chinaredstar.longyan.util.DayDataSearchUtil;
import com.chinaredstar.commonBiz.bean.RedstarEmployee;
import com.chinaredstar.commonBiz.bean.RedstarTaskLog;
import com.chinaredstar.commonBiz.bean.work.RedstarAttendanceCheckpoint;
import com.chinaredstar.commonBiz.bean.work.RedstarAttendanceDepartment;
import com.chinaredstar.commonBiz.bean.work.RedstarAttendanceRecord;
import com.chinaredstar.commonBiz.manager.RedstarCommonManager;
import com.chinaredstar.commonBiz.manager.RedstarTaskLogManager;
import com.xiwa.base.bean.search.ext.BooleanSearch;
import com.xiwa.base.bean.search.ext.IntSearch;
import com.xiwa.base.bean.search.ext.MultiSearchBean;
import com.xiwa.base.bean.search.ext.TextSearch;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by niu on 2016/7/14.
 */
@Component(value = "attendanceRecordStatusTask")
public class AttendanceRecordStatusTask  implements LanchuiConstant{


    @Autowired
    private RedstarCommonManager redstarCommonManager;

    @Autowired
    private RedstarTaskLogManager redstarTaskLogManager;


    public void execute(){

        Calendar calendar =Calendar.getInstance();
        calendar.add(Calendar.DATE,-1);


        DateFormat dateFormat = new SimpleDateFormat("HH:mm");

        Long startTime = System.currentTimeMillis();

        try{

            //开始日志

            RedstarTaskLog redstarTaskLog = new RedstarTaskLog();
            redstarTaskLog.setTaskClassName(this.getClass().getSimpleName());
            redstarTaskLog.setLogDatetime(new Date());
            redstarTaskLog.setRemark("打卡状态计算");
            redstarTaskLog.setAction(taskLogStart);
            redstarTaskLogManager.addBean(redstarTaskLog);

            //昨天数据计算参数
            List<Object> paramList = DayDataSearchUtil.getParamList(calendar);

            //今天是否是节假日
            String holidaySql = "SELECT count(*) FROM xiwa_redstar_attendance_holiday WHERE year=? AND month=? AND day=?";

            Integer sqlCount =  redstarCommonManager.getCountBySql(holidaySql,paramList);

            if (sqlCount==0){
                //所有记录
                String dataSql = "SELECT * FROM xiwa_redstar_attendance_record WHERE year=? AND month=? AND day=?";
                List<RedstarAttendanceRecord>  recordList =  redstarCommonManager.excuteSql(dataSql,paramList,RedstarAttendanceRecord.class);

                MultiSearchBean userSearch = new MultiSearchBean();
                IntSearch belongSearch = new IntSearch("belongedId");
                belongSearch.setSearchValue(String.valueOf(LOG_BELONG_ID));
                BooleanSearch booleanSearch = new BooleanSearch("showData");
                booleanSearch.setValue("1");
                userSearch.addSearchBean(belongSearch);
                userSearch.addSearchBean(booleanSearch);

                List<RedstarEmployee> employeeList = redstarCommonManager.getDataList(RedstarEmployee.class,userSearch);
                //所有用户
                for (RedstarEmployee redstarEmployee :employeeList){

                    String departmentCode =redstarEmployee.getDepartmentCode();

                    //部门id为空
                    if (StringUtil.isInvalid(departmentCode)){
                        continue;
                    }

/*                MultiSearchBean  multiSearchBean = DayDataSearchUtil.getMultiSearchBean(calendar);
                IntSearch employeeIdSearch = new IntSearch("employeeId");
                employeeIdSearch.setSearchValue(String.valueOf(redstarEmployee.getId()));
                multiSearchBean.addSearchBean(employeeIdSearch);
                List<RedstarAttendanceRecord>  recordList =  redstarCommonManager.getDataList(RedstarAttendanceRecord.class,multiSearchBean);*/

/*
                List<Object> paramList = DayDataSearchUtil.getParamList(calendar);
                paramList.add(redstarEmployee.getId());
                String sql = "SELECT * FROM xiwa_redstar_attendance_record WHERE year=? and month=? and day=? and employeeId=?";
                List<RedstarAttendanceRecord>  recordList =  redstarCommonManager.excuteSql(sql,paramList,RedstarAttendanceRecord.class);
*/

                    RedstarAttendanceRecord record=null;

                    for (RedstarAttendanceRecord data:recordList){
                        if (redstarEmployee.getId()==data.getEmployeeId()){
                            record=data;
                            break;
                        }
                    }

                    if (record!=null){

                        //自动保存的未打卡的记录
                        if (record.getCheckinTime()==null&&record.getCheckoutTime()==null){
                            continue;
                        }

                        TextSearch deptSearch = new TextSearch("departmentCode");
                        deptSearch.setSearchValue(departmentCode);

                        List<RedstarAttendanceDepartment> departmentList =  redstarCommonManager.getDataList(RedstarAttendanceDepartment.class,deptSearch);

                        if (!CollectionUtils.isEmpty(departmentList)){

                            RedstarAttendanceCheckpoint point = (RedstarAttendanceCheckpoint) redstarCommonManager.getDataById(departmentList.get(0).getCheckpointId(), RedstarAttendanceCheckpoint.class.getName());


                            if(point==null){
                                continue;
                            }

                            String checkInTime = point.getCheckinTime();
                            String checkOutTime = point.getCheckoutTime();

                            Date checkInDate = record.getCheckinTime();
                            Date checkOutDate = record.getCheckoutTime();

                            //未签退
                            if (checkOutDate==null){
                                record.setStatus("noCheckout");
                                redstarCommonManager.updateData(record);
                                continue;
                            }

                            if (checkInDate!=null&&checkOutDate!=null){
                                String [] checkInArr = checkInTime.split(":");
                                String [] checkOutArr = checkOutTime.split(":");
                                String [] empCheckInArr =dateFormat.format(checkInDate) .split(":");
                                String [] empCheckOutArr = dateFormat.format(checkOutDate).split(":");

                                //考勤状态 迟到
                                String status="";
                                if(Integer.parseInt(empCheckInArr[0])>=Integer.parseInt(checkInArr[0])){
                                    if(Integer.parseInt(empCheckInArr[1])>Integer.parseInt(checkInArr[1])){
                                        status="late";
                                    }
                                }

                                //考勤状态 迟到且早退
                                if(Integer.parseInt(empCheckOutArr[0])<Integer.parseInt(checkOutArr[0])){
                                    if (StringUtil.isValid(status)){
                                        status="lateAndEarly";
                                    }else{
                                        status="early";
                                    }
                                }

                                ////考勤状态 迟到且早退  小时相同但分钟小于签退时间
                                if(Integer.parseInt(empCheckOutArr[0])==Integer.parseInt(checkOutArr[0])){
                                    if(Integer.parseInt(empCheckOutArr[1])<Integer.parseInt(checkOutArr[1])){
                                        if (StringUtil.isValid(status)){
                                            status="lateAndEarly";
                                        }else{
                                            status="early";
                                        }
                                    }
                                }

                                if (StringUtil.isValid(status)){
                                    record.setStatus(status);
                                    redstarCommonManager.updateData(record);
                                    continue;
                                }
                            }

                        }

                    }else{
                        //没有打卡记录 添加一句没打卡的记录
                        RedstarAttendanceRecord redstarAttendanceRecord = new RedstarAttendanceRecord();
                        redstarAttendanceRecord.setYear(calendar.get(Calendar.YEAR));
                        redstarAttendanceRecord.setMonth(calendar.get(Calendar.MONTH) + 1);
                        redstarAttendanceRecord.setDay(calendar.get(Calendar.DATE));
                        redstarAttendanceRecord.setEmployeeId(redstarEmployee.getId());
                        redstarAttendanceRecord.setEmployeeXingMing(redstarEmployee.getXingMing());
                        redstarAttendanceRecord.setCreateDate(new Date());
                        redstarAttendanceRecord.setStatus("noCheck");
                        redstarCommonManager.addData(redstarAttendanceRecord);
                    }

                }

            }

            redstarTaskLog = new RedstarTaskLog();
            redstarTaskLog.setTaskClassName(this.getClass().getSimpleName());
            redstarTaskLog.setLogDatetime(new Date());
            redstarTaskLog.setRemark("打卡状态计算");
            redstarTaskLog.setAction(taskLogEnd);
            redstarTaskLog.setExecuteTime(System.currentTimeMillis()-startTime);
            redstarTaskLogManager.addBean(redstarTaskLog);

        }catch (Exception e){
            //记录任务异常信息
            e.printStackTrace();
            RedstarTaskLog redstarTaskLog = new RedstarTaskLog();
            redstarTaskLog.setTaskClassName(this.getClass().getSimpleName());
            redstarTaskLog.setLogDatetime(new Date());
            redstarTaskLog.setRemark("打卡状态计算任务遇到异常");
            redstarTaskLog.setAction(taskError);
            redstarTaskLog.setExecuteTime(System.currentTimeMillis()-startTime);
            try {
                redstarTaskLogManager.addBean(redstarTaskLog);
            } catch (ManagerException e1) {
            }
        }
    }


    public static void main(String[] args){
/*        System.out.println(Integer.parseInt("05"));
        Calendar calendar =Calendar.getInstance();
        calendar.add(Calendar.DATE,-1);
        System.out.println(new SimpleDateFormat("HH:mm").format(calendar.getTime()));*/
    }


}
