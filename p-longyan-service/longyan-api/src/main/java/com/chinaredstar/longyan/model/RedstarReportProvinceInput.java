package com.chinaredstar.longyan.model;


import java.io.Serializable;
import java.util.Date;

public class RedstarReportProvinceInput implements Serializable {
    private static final long serialVersionUID = 5405098528378424870L;

    private Integer id;

    private Integer inputCommunityRoomAmount;

    private Date createDate;

    private Date updateDate;

    private String provinceCode;

    private Integer employeeCount;

    private Integer inputMemberAmount;

    private String province;

    private Integer inputCommunityAmount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInputCommunityRoomAmount() {
        return inputCommunityRoomAmount;
    }

    public void setInputCommunityRoomAmount(Integer inputCommunityRoomAmount) {
        this.inputCommunityRoomAmount = inputCommunityRoomAmount;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode == null ? null : provinceCode.trim();
    }

    public Integer getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(Integer employeeCount) {
        this.employeeCount = employeeCount;
    }

    public Integer getInputMemberAmount() {
        return inputMemberAmount;
    }

    public void setInputMemberAmount(Integer inputMemberAmount) {
        this.inputMemberAmount = inputMemberAmount;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public Integer getInputCommunityAmount() {
        return inputCommunityAmount;
    }

    public void setInputCommunityAmount(Integer inputCommunityAmount) {
        this.inputCommunityAmount = inputCommunityAmount;
    }
}