package com.chinaredstar.nvwaBiz.bean;

import com.xiwa.base.bean.Identified;

/**
 * Created by wangj on 2015/5/4.
 */
public class NvwaSecurityResource implements Identified {

    private int id;

    private String serialNumber;//    序号

    private String name;//    名称

    private String resourceUrl;//   资源地址

    private int parentId;//    父节点ID

    private String description;//   描述

    private int sortNum;//  种类数量

    private String resourceTypeField;//  资源类型字段

    private String alias;//  别名

    private boolean showData;//  显示日期

    private String pinYin;//   拼音

    private int workflowId; //  工作流程ID

    private String actionName;//  功能名称

    private String nextStatus;//    下一个状态

    private boolean start; //   开始

    private String attributes;//    属性

    private boolean leaf;//

    private boolean shelf;//  架子 搁板

    private String authTarget;//

    private int authTargetId;//

    private String serviceIndustryCodes;    //服务行业规范


    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    public String getResourceTypeField() {
        return resourceTypeField;
    }

    public void setResourceTypeField(String resourceTypeField) {
        this.resourceTypeField = resourceTypeField;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public boolean isShowData() {
        return showData;
    }

    public void setShowData(boolean showData) {
        this.showData = showData;
    }

    public String getPinYin() {
        return pinYin;
    }

    public void setPinYin(String pinYin) {
        this.pinYin = pinYin;
    }

    public int getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(int workflowId) {
        this.workflowId = workflowId;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getNextStatus() {
        return nextStatus;
    }

    public void setNextStatus(String nextStatus) {
        this.nextStatus = nextStatus;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public boolean isShelf() {
        return shelf;
    }

    public void setShelf(boolean shelf) {
        this.shelf = shelf;
    }

    public String getAuthTarget() {
        return authTarget;
    }

    public void setAuthTarget(String authTarget) {
        this.authTarget = authTarget;
    }

    public String getServiceIndustryCodes() {
        return serviceIndustryCodes;
    }

    public void setServiceIndustryCodes(String serviceIndustryCodes) {
        this.serviceIndustryCodes = serviceIndustryCodes;
    }

    public int getAuthTargetId() {
        return authTargetId;
    }

    public void setAuthTargetId(int authTargetId) {
        this.authTargetId = authTargetId;
    }
}
