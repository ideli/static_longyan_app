package com.chinaredstar.nvwaBiz.bean;

import com.xiwa.base.bean.Identified;

import java.util.Date;

/**
 * Created by usagizhang on 16-5-2.
 */
public class NvwaDepartment implements Identified {
    private int id;

    private String name;//  名称

    private Date createDate;//  创建时间

    private Integer belongedId;//

    private Integer parentId = 0;// 父节点ID

    private boolean showData;// 显示日期

    private String departmentCode;// 红星系统的部门代码

    private String pinYin;// 拼音

    private String description;// 描述

    private String alias;// 别名

    private boolean leaf;//

    private String companyCode;// 公司代码、PS公司ID

    private String companyDescr;//  公司简述、公司名称

    private String departmentParentCode;

//    private Integer peopleNumber;

    public String getDepartmentParentCode() {
        return departmentParentCode;
    }

    public void setDepartmentParentCode(String departmentParentCode) {
        this.departmentParentCode = departmentParentCode;
    }

    @Override
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getBelongedId() {
        return belongedId;
    }

    public void setBelongedId(Integer belongedId) {
        this.belongedId = belongedId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public boolean isShowData() {
        return showData;
    }

    public void setShowData(boolean showData) {
        this.showData = showData;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getPinYin() {
        return pinYin;
    }

    public void setPinYin(String pinYin) {
        this.pinYin = pinYin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyDescr() {
        return companyDescr;
    }

    public void setCompanyDescr(String companyDescr) {
        this.companyDescr = companyDescr;
    }

//    public Integer getPeopleNumber() {
//        return peopleNumber;
//    }
//
//    public void setPeopleNumber(Integer peopleNumber) {
//        this.peopleNumber = peopleNumber;
//    }
}
