package com.chinaredstar.longyan.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by mdc on 2016/8/3.
 */
@Entity
@Table(name="xiwa_redstar_lottery_log")
public class RedstarLotteryLog extends BaseEntity {
    private Integer employeeId;

    private String employeeName;

    private String employeeCode;

    private String departmentCode;

    private String departmentName;

    private String mobile;

    private String presentCode;

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPresentCode() {
        return presentCode;
    }

    public void setPresentCode(String presentCode) {
        this.presentCode = presentCode;
    }


    public void setEmployeeInfo (RedstarEmployee em) {
        this.employeeCode = em.getEmployeeCode();
        this.employeeId = em.getId();
        this.employeeName = em.getXingMing();
        this.departmentCode = em.getDepartmentCode();
        this.departmentName = "";
        this.mobile = em.getMobilePhone();
    }

}
