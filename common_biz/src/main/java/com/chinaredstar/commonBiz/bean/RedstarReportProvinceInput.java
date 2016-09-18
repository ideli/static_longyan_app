package com.chinaredstar.commonBiz.bean;

import com.xiwa.base.bean.Identified;

import java.util.Date;

/**
 * Created by lenovo on 2016/5/10.
 */
public class RedstarReportProvinceInput implements Identified {
    private int id;

    private Date createDate;//  创建时间

    private Date updateDate;//  修改时间

    private String provinceCode;//  省代码

    private  String   province;//  省

    private Integer employeeCount;//  员工数量

    private  Integer inputCommunityAmount;//  录入小区数量

    private  Integer inputMemberAmount;//  录入会员数量

    private Integer inputCommunityRoomAmount;//  录入小区住户数量

    private  Integer mallCount;//  商场数量

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        this.provinceCode = provinceCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Integer getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(Integer employeeCount) {
        this.employeeCount = employeeCount;
    }

    public Integer getInputCommunityAmount() {
        return inputCommunityAmount;
    }

    public void setInputCommunityAmount(Integer inputCommunityAmount) {
        this.inputCommunityAmount = inputCommunityAmount;
    }

    public Integer getInputMemberAmount() {
        return inputMemberAmount;
    }

    public void setInputMemberAmount(Integer inputMemberAmount) {
        this.inputMemberAmount = inputMemberAmount;
    }

    public Integer getInputCommunityRoomAmount() {
        return inputCommunityRoomAmount;
    }

    public void setInputCommunityRoomAmount(Integer inputCommunityRoomAmount) {
        this.inputCommunityRoomAmount = inputCommunityRoomAmount;
    }


    public Integer getMallCount() {
        return mallCount;
    }

    public void setMallCount(Integer mallCount) {
        this.mallCount = mallCount;
    }
}
