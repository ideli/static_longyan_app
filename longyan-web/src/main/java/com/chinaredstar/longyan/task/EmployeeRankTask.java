package com.chinaredstar.longyan.task;

import com.chinaredstar.longyan.bean.constant.LanchuiConstant;
import com.chinaredstar.commonBiz.bean.RedstarEmployee;
import com.chinaredstar.commonBiz.bean.RedstarTaskLog;
import com.chinaredstar.commonBiz.manager.DispatchDriver;
import com.chinaredstar.commonBiz.manager.RedstarTaskLogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * REDSTAR-92
 * TASK：用户排名计算
 * <p/>
 * 员工排名计算
 * Created by lenovo on 2016/4/28.
 */
@Component(value ="employeeRankTask")
public class EmployeeRankTask implements LanchuiConstant {

    @Autowired
    private RedstarTaskLogManager redstarTaskLogManager;

    @Autowired
    private DispatchDriver dispatchDriver;




    public void employeeRank() {



        try {

            Long startTime = System.currentTimeMillis();
            RedstarTaskLog redstarTaskLog = new RedstarTaskLog();
            redstarTaskLog.setTaskClassName(this.getClass().getSimpleName());
            redstarTaskLog.setLogDatetime(new Date());
            redstarTaskLog.setRemark("员工排名统计");
            redstarTaskLog.setAction(taskLogStart);
            redstarTaskLogManager.addBean(redstarTaskLog);

            //查询所有员工根据录入住户总数倒序
            List<RedstarEmployee> employeeMemberList = dispatchDriver.getRedstarEmployeeManager().getBeanList("inputMemberAmount", false);
            //排名结果插入到表中
            int i = 0;
            if (employeeMemberList.size() > 0) {
                for (RedstarEmployee employee : employeeMemberList) {
                    try {
                        employee.setInputMemberAmountRank(i);
                        dispatchDriver.getRedstarEmployeeManager().updateBean(employee);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        i++;
                    }
                }
            }
            //查询所有员工根据录入小区总数倒序
            List<RedstarEmployee> employeeCommunityList = dispatchDriver.getRedstarEmployeeManager().getBeanList("inputCommunityAmount", false);
            //排名结果插入到表中
            int a = 0;
            if (employeeCommunityList.size() > 0) {
                for (RedstarEmployee employe : employeeCommunityList) {
                    try {
                        employe.setInputCommunityAmountRank(a);
                        dispatchDriver.getRedstarEmployeeManager().updateBean(employe);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        a++;
                    }
                }
            }


            redstarTaskLog = new RedstarTaskLog();
            redstarTaskLog.setTaskClassName(this.getClass().getSimpleName());
            redstarTaskLog.setLogDatetime(new Date());
            redstarTaskLog.setRemark("员工排名统计");
            redstarTaskLog.setAction(taskLogEnd);
            redstarTaskLog.setExecuteTime(System.currentTimeMillis()-startTime);
            redstarTaskLogManager.addBean(redstarTaskLog);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
