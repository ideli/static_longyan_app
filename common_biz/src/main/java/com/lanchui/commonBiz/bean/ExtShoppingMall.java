package com.lanchui.commonBiz.bean;

/**
 * Created by niu on 2016/4/27.
 */
public class ExtShoppingMall {


    private int id;
    private String name;
    private Integer inputMemberAmountRank;
    private Integer  inputMemberAmount;
    private Integer  inputCommunityAmount;
    private Integer communityMemberAmount;
    private Integer employeeCount;
    private Integer organizationId;
    private String  organizationName;

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getInputMemberAmountRank() {
        return inputMemberAmountRank;
    }

    public void setInputMemberAmountRank(Integer inputMemberAmountRank) {
        this.inputMemberAmountRank = inputMemberAmountRank;
    }

    public Integer getInputMemberAmount() {
        return inputMemberAmount;
    }

    public void setInputMemberAmount(Integer inputMemberAmount) {
        this.inputMemberAmount = inputMemberAmount;
    }

    public Integer getInputCommunityAmount() {
        return inputCommunityAmount;
    }

    public void setInputCommunityAmount(Integer inputCommunityAmount) {
        this.inputCommunityAmount = inputCommunityAmount;
    }

    public Integer getCommunityMemberAmount() {
        return communityMemberAmount;
    }

    public void setCommunityMemberAmount(Integer communityMemberAmount) {
        this.communityMemberAmount = communityMemberAmount;
    }

    public Integer getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(Integer employeeCount) {
        this.employeeCount = employeeCount;
    }


    //inputCommunityAmountRank`


}
