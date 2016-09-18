package com.chinaredstar.longyan.task;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.greatbee.bean.constant.LanchuiConstant;
import com.chinaredstar.commonBiz.bean.RedstarDepartment;
import com.chinaredstar.commonBiz.bean.RedstarEmployee;
import com.chinaredstar.commonBiz.bean.RedstarTaskLog;
import com.chinaredstar.commonBiz.manager.RedstarCommonManager;
import com.chinaredstar.commonBiz.manager.RedstarDepartmentManager;
import com.chinaredstar.commonBiz.manager.RedstarEmployeeManager;
import com.chinaredstar.commonBiz.manager.RedstarTaskLogManager;
import com.xiwa.base.bean.search.ext.MultiSearchBean;
import com.xiwa.base.bean.search.ext.TextSearch;
import com.xiwa.base.manager.ManagerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mdc on 2016/8/24.
 */
@Component(value = "departmentPeopleNumberTask")
public class DepartmentPeopleNumberTask implements LanchuiConstant {

    @Autowired
    private RedstarDepartmentManager redstarDepartmentManager;

    @Autowired
    private RedstarEmployeeManager redstarEmployeeManager;


    @Autowired
    private RedstarTaskLogManager redstarTaskLogManager;

    @Autowired
    private RedstarCommonManager redstarCommonManager;

    public void execute() {
        Long startTime = System.currentTimeMillis();
        RedstarTaskLog redstarTaskLog = new RedstarTaskLog();
        redstarTaskLog.setTaskClassName(this.getClass().getSimpleName());
        redstarTaskLog.setLogDatetime(new Date());
        redstarTaskLog.setRemark("部门人数数据更新");
        redstarTaskLog.setAction(taskLogStart);

        try {
            HashMap<String, Integer> empMap = new HashMap<String, Integer>();
            List<RedstarDepartment> departments = redstarDepartmentManager.getBeanList();
            for (RedstarDepartment department : departments) {
                if (!StringUtils.isEmpty(department.getDepartmentCode())) {
                    MultiSearchBean multiSearchBean = new MultiSearchBean();
                    TextSearch codeSearch = new TextSearch("departmentCode");
                    codeSearch.setSearchValue(department.getDepartmentCode());
                    TextSearch hrStatus = new TextSearch("hrStatus");
                    hrStatus.setSearchValue("A");
                    multiSearchBean.addSearchBean(codeSearch);
                    multiSearchBean.addSearchBean(hrStatus);
                    List<RedstarEmployee> employees = redstarEmployeeManager.searchIdentify(multiSearchBean);
                    if (empMap.containsKey(department.getDepartmentCode())) {
                        Integer count = empMap.get(department.getDepartmentCode());
                        empMap.put(department.getDepartmentCode(), employees.size() + count);
                    } else {
                        empMap.put(department.getDepartmentCode(), employees.size());
                    }
                    if (!StringUtils.isEmpty(department.getDepartmentParentCode())) {
                        if (empMap.containsKey(department.getDepartmentParentCode())) {
                            Integer count = empMap.get(department.getDepartmentParentCode());
                            empMap.put(department.getDepartmentParentCode(),employees.size() + count);
                        } else {
                            empMap.put(department.getDepartmentParentCode(),employees.size());
                        }
                    }
                }
            }
            List<Object> updateDepts = new ArrayList<Object>();
            for (RedstarDepartment updateDept : departments) {
                if (empMap.containsKey(updateDept.getDepartmentCode())) {
                    updateDept.setPeopleNumber(empMap.get(updateDept.getDepartmentCode()));
                }
                updateDepts.add(updateDept);
            }
            redstarCommonManager.batchUpdateIdentified(RedstarDepartment.class, updateDepts);
            updateDepts = new ArrayList<Object>();
            List<RedstarDepartment> zeroDepts = redstarDepartmentManager.getBeanListByColumn("peopleNumber",0);
            List<RedstarDepartment> suDepts = redstarDepartmentManager.getBeanListByColumn("departmentParentCode","");
            for (RedstarDepartment zeroDept : zeroDepts){
                if(!StringUtils.isEmpty(zeroDept.getDepartmentCode())) {
                    List<RedstarDepartment> suns = redstarDepartmentManager.getBeanListByColumn("departmentParentCode", zeroDept.getDepartmentCode());
                    int sum = zeroDept.getPeopleNumber();
                    for (RedstarDepartment sun : suns) {
                        sum += sun.getPeopleNumber();
                    }
                    zeroDept.setPeopleNumber(sum);
                    updateDepts.add(zeroDept);
                }
            }
            redstarCommonManager.batchUpdateIdentified(RedstarDepartment.class, updateDepts);
            updateDepts = new ArrayList<Object>();
            for (RedstarDepartment zeroDept : suDepts){
                if(!StringUtils.isEmpty(zeroDept.getDepartmentCode())) {
                    List<RedstarDepartment> suns = redstarDepartmentManager.getBeanListByColumn("departmentParentCode", zeroDept.getDepartmentCode());
                    int sum = 0;
                    for (RedstarDepartment sun : suns) {
                        sum += sun.getPeopleNumber();
                    }
                    zeroDept.setPeopleNumber(sum);
                    updateDepts.add(zeroDept);
                }
            }
            redstarCommonManager.batchUpdateIdentified(RedstarDepartment.class, updateDepts);
        } catch (ManagerException e) {
            e.printStackTrace();
        }


        redstarTaskLog = new RedstarTaskLog();
        redstarTaskLog.setTaskClassName(this.getClass().getSimpleName());
        redstarTaskLog.setLogDatetime(new Date());
        redstarTaskLog.setRemark("部门人数数据更新");
        redstarTaskLog.setAction(taskLogEnd);
        redstarTaskLog.setExecuteTime(System.currentTimeMillis() - startTime);
        try {
            redstarTaskLogManager.addBean(redstarTaskLog);
        } catch (ManagerException e) {
            e.printStackTrace();
        }
    }
}
