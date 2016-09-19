package com.chinaredstar.longyan.task;

import com.chinaredstar.longyan.bean.constant.LanchuiConstant;
import com.chinaredstar.longyan.util.HttpClientUtil;
import com.chinaredstar.longyan.util.SpringUtil;
import com.chinaredstar.nvwaBiz.bean.NvwaDepartment;
import com.chinaredstar.nvwaBiz.bean.NvwaEmployee;
import com.chinaredstar.commonBiz.bean.RedstarTaskLog;
import com.chinaredstar.commonBiz.manager.RedstarCommonManager;
import com.chinaredstar.nvwaBiz.manager.NvwaDepartmentManager;
import com.chinaredstar.nvwaBiz.manager.NvwaEmployeeManager;
import com.chinaredstar.commonBiz.manager.RedstarTaskLogManager;
import com.xiwa.base.manager.ManagerException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
    private NvwaDepartmentManager redstarDepartmentManager;

    @Autowired
    private NvwaEmployeeManager redstarEmployeeManager;

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
        String userCenterUrl = systemConfig.get("userCenterUrl").toString();
        JSONObject jsonObjectDept = HttpClientUtil.post(userCenterUrl + "/employee/getPsSyncDepartmentListByDeptId", params);
        try {
            // sync dept start

            if (jsonObjectDept.containsKey("errorCode") && jsonObjectDept.getInt("errorCode") == 0) {
                if (jsonObjectDept.containsKey("data")) {
                    List<NvwaDepartment> oldDepts = redstarDepartmentManager.getBeanList();
                    List<NvwaDepartment> newDepts = new ArrayList<NvwaDepartment>();
                    if (jsonObjectDept.containsKey("data")) {
                        List<NvwaDepartment> insertDeptList = new ArrayList<NvwaDepartment>();
                        List<Object> updateDeptList = new ArrayList<Object>();
                        JSONArray deptArray = jsonObjectDept.getJSONArray("data");
                        for (int i = 0; i < deptArray.size(); i++) { // 将用户中心的部门信息封装成list
                            JSONObject deptByUc = (JSONObject) deptArray.get(i);
                            NvwaDepartment department = new NvwaDepartment();
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
                        for (NvwaDepartment newDept : newDepts) {
                            isInsert = true;
                            for (NvwaDepartment oldDept : oldDepts) {
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
                        redstarCommonManager.batchUpdateIdentified(NvwaDepartment.class, updateDeptList);
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
                List<NvwaEmployee> oldEmpleeInfos = redstarEmployeeManager.getBeanList();
                List<NvwaEmployee> newEmployeeInfos = new ArrayList<NvwaEmployee>();
                if (jsonObjectEmp.containsKey("data")) {
                    List<NvwaEmployee> insertEmpList = new ArrayList<NvwaEmployee>();
                    List<Object> updateEmpList = new ArrayList<Object>();
                    JSONArray deptArray = jsonObjectEmp.getJSONArray("data");
                    for (int i = 0; i < deptArray.size(); i++) {
                        JSONObject empByUc = (JSONObject) deptArray.get(i);
                        NvwaEmployee newEmp = new NvwaEmployee();
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
                    for (NvwaEmployee newEmp : newEmployeeInfos) {
                        isInsert = true;
                        for (NvwaEmployee oldEmp : oldEmpleeInfos) {
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
                    redstarCommonManager.batchUpdateIdentified(NvwaEmployee.class, updateEmpList);
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
