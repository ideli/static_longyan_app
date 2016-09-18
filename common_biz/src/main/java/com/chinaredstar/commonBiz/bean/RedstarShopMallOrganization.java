package com.chinaredstar.commonBiz.bean;

import com.xiwa.base.bean.Identified;

/**
 * Created by lenovo on 2016/4/27.
 */
public class RedstarShopMallOrganization implements Identified {

    private int id;

    private  int parentId;//    父节点ID

    private  String name;   //   名称

    private  String alias;   //   别名

    private  int inputMemberAmount;   //   录入会员数量

    private  int inputCommunityAmount;//   录入小区数量

    private  Integer inputCommunityRoomAmount;//   录入小区住户数量

    private  Integer employeeCount;//   员工数量

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getInputMemberAmount() {
        return inputMemberAmount;
    }

    public void setInputMemberAmount(int inputMemberAmount) {
        this.inputMemberAmount = inputMemberAmount;
    }

    public int getInputCommunityAmount() {
        return inputCommunityAmount;
    }

    public void setInputCommunityAmount(int inputCommunityAmount) {
        this.inputCommunityAmount = inputCommunityAmount;
    }

    public Integer getInputCommunityRoomAmount() {
        return inputCommunityRoomAmount;
    }

    public void setInputCommunityRoomAmount(Integer inputCommunityRoomAmount) {
        this.inputCommunityRoomAmount = inputCommunityRoomAmount;
    }

    public Integer getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(Integer employeeCount) {
        this.employeeCount = employeeCount;
    }
}
