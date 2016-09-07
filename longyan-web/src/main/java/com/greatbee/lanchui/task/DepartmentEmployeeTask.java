package com.greatbee.lanchui.task;

import com.greatbee.bean.constant.LanchuiConstant;
import com.greatbee.util.HttpClientUtil;
import com.greatbee.util.SpringUtil;
import com.lanchui.commonBiz.bean.RedstarDepartment;
import com.lanchui.commonBiz.bean.RedstarEmployee;
import com.lanchui.commonBiz.bean.RedstarTaskLog;
import com.lanchui.commonBiz.manager.RedstarCommonManager;
import com.lanchui.commonBiz.manager.RedstarDepartmentManager;
import com.lanchui.commonBiz.manager.RedstarEmployeeManager;
import com.lanchui.commonBiz.manager.RedstarTaskLogManager;
import com.opensymphony.oscache.util.StringUtil;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.zeus.trinity.bean.Department;
import com.xiwa.zeus.trinity.manager.DepartmentManager;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by mdc on 2016/7/14.
 */
@Component(value = "departmentEmployeeTask")
public class DepartmentEmployeeTask implements LanchuiConstant {

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
        redstarTaskLog.setRemark("组织架构同步");
        redstarTaskLog.setAction(taskLogStart);
        try {
            redstarTaskLogManager.addBean(redstarTaskLog);
        } catch (ManagerException e) {
            e.printStackTrace();
        }

        HashMap systemConfig = (HashMap) SpringUtil.getContext().getBean("systemConfig");
        Map<String, String> params = new HashMap<String, String>();
        params.put("appId", "c3");
        params.put("appSecret", "s333");
        String userCenterUrl = systemConfig.get("sysUserUrl").toString();
        JSONObject jsonObjectDept = HttpClientUtil.post(userCenterUrl + "/employee/getPsSyncDepartmentListByDeptId", params);
        try {
            // sync dept start

            if (jsonObjectDept.containsKey("errorCode") && jsonObjectDept.getInt("errorCode") == 0) {
                if (jsonObjectDept.containsKey("data")) {
                    List<RedstarDepartment> oldDepts = redstarDepartmentManager.getBeanList();
                    List<RedstarDepartment> newDepts = new ArrayList<RedstarDepartment>();
                    if (jsonObjectDept.containsKey("data")) {
                        List<RedstarDepartment> insertDeptList = new ArrayList<RedstarDepartment>();
                        List<Object> updateDeptList = new ArrayList<Object>();
                        JSONArray deptArray = jsonObjectDept.getJSONArray("data");
                        for (int i = 0; i < deptArray.size(); i++) { // 将用户中心的部门信息封装成list
                            JSONObject deptByUc = (JSONObject) deptArray.get(i);
                            RedstarDepartment department = new RedstarDepartment();
                            department.setDepartmentCode(deptByUc.getString("deptId"));
                            department.setName(deptByUc.getString("descrShort"));
                            department.setDescription(deptByUc.getString("descr"));
                            department.setCompanyCode(deptByUc.getString("companyId"));
                            department.setCompanyDescr(deptByUc.getString("companyDescr"));
                            department.setDepartmentParentCode(deptByUc.getString("parentDeptId"));
                            department.setPinYin("");
                            department.setShowData(true);
                            department.setBelongedId(10944);
                            department.setCreateDate(DateTime.now().toDate());
                            department.setLeaf(false);
                            newDepts.add(department);
                        }
                        boolean isInsert = true;
                        for (RedstarDepartment newDept : newDepts) {
                            isInsert = true;
                            for (RedstarDepartment oldDept : oldDepts) {
                                if (newDept.getDepartmentCode().equals(oldDept.getDepartmentCode())) {
                                    oldDept.setName(newDept.getName());
                                    oldDept.setDescription(newDept.getDescription());
                                    oldDept.setCompanyCode(newDept.getCompanyCode());
                                    oldDept.setCompanyDescr(newDept.getCompanyDescr());
//                                    oldDept.setParentId(newDept.getParentId());
                                    oldDept.setDepartmentParentCode(newDept.getDepartmentParentCode());
                                    updateDeptList.add(oldDept);
                                    isInsert = false;
                                    break;
                                }
                            }
                            if (isInsert) {
                                insertDeptList.add(newDept);
                            }
                        }
                        if (insertDeptList.size() > 0) redstarCommonManager.addBeans(insertDeptList);
                        redstarCommonManager.batchUpdateIdentified(RedstarDepartment.class, updateDeptList);
                        String sql = "UPDATE xiwa_crm_department t,( SELECT a.id AS id, b.id AS parentId FROM xiwa_crm_department a LEFT JOIN xiwa_crm_department b ON a.departmentParentCode = b.departmentCode";
                        sql += " WHERE a.parentId >= 0 ) c SET t.parentId = c.parentId WHERE t.id = c.id;";
                        redstarCommonManager.excuteBySql(sql);

                    }
                }
            }
            // sync dept end
            //sync employee start


            JSONObject jsonObjectEmp = HttpClientUtil.post(userCenterUrl + "/employee/getPsSyncEmployeesListByDeptId", params);
            if (jsonObjectEmp.containsKey("errorCode") && jsonObjectEmp.getInt("errorCode") == 0) {
                List<RedstarEmployee> oldEmpleeInfos = redstarEmployeeManager.getBeanList();
                List<RedstarEmployee> newEmployeeInfos = new ArrayList<RedstarEmployee>();
                if (jsonObjectEmp.containsKey("data")) {
                    List<RedstarEmployee> insertEmpList = new ArrayList<RedstarEmployee>();
                    List<Object> updateEmpList = new ArrayList<Object>();
                    JSONArray deptArray = jsonObjectEmp.getJSONArray("data");
                    for (int i = 0; i < deptArray.size(); i++) {
                        JSONObject empByUc = (JSONObject) deptArray.get(i);
                        RedstarEmployee newEmp = new RedstarEmployee();
                        newEmp.setEmployeeCode(empByUc.getString("emplid"));
                        newEmp.setXingMing(empByUc.getString("name"));
                        newEmp.setHrStatus(empByUc.getString("hrStatus"));
                        newEmp.setGender("M".equals(empByUc.getString("sex")) ? "male":"female");
                        newEmp.setDepartmentCode(empByUc.getString("deptid"));
                        newEmp.setMobilePhone(empByUc.getString("phone"));
                        newEmp.setEmail(empByUc.getString("email"));
                        newEmp.setHead(empByUc.getString("posnDescr"));
                        newEmp.setCreateDate(DateTime.now().toDate());
                        newEmp.setBelongedId(10944);
                        newEmp.setShowData(true);
                        newEmployeeInfos.add(newEmp);
                    }

                    boolean isInsert = true;
                    for (RedstarEmployee newEmp : newEmployeeInfos) {
                        isInsert = true;
                        for (RedstarEmployee oldEmp : oldEmpleeInfos) {
                            if (newEmp.getEmployeeCode().equals(oldEmp.getEmployeeCode())) {
                                oldEmp.setXingMing(newEmp.getXingMing());
                                oldEmp.setHrStatus(newEmp.getHrStatus());
                                oldEmp.setGender(newEmp.getGender());
                                oldEmp.setDepartmentCode(newEmp.getDepartmentCode());
                                oldEmp.setMobilePhone(newEmp.getMobilePhone());
                                oldEmp.setEmail(newEmp.getEmail());
                                updateEmpList.add(oldEmp);
                                isInsert = false;
                                break;
                            }
                        }
                        if (isInsert) {
                            insertEmpList.add(newEmp);
                        }
                    }
                    if (insertEmpList.size() > 0) redstarCommonManager.addBeans(insertEmpList);
                    redstarCommonManager.batchUpdateIdentified(RedstarEmployee.class, updateEmpList);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        redstarTaskLog = new RedstarTaskLog();
        redstarTaskLog.setTaskClassName(this.getClass().getSimpleName());
        redstarTaskLog.setLogDatetime(new Date());
        redstarTaskLog.setRemark("组织架构同步");
        redstarTaskLog.setAction(taskLogEnd);
        redstarTaskLog.setExecuteTime(System.currentTimeMillis() - startTime);
        try {
            redstarTaskLogManager.addBean(redstarTaskLog);
        } catch (ManagerException e) {
            e.printStackTrace();
        }
    }
}
