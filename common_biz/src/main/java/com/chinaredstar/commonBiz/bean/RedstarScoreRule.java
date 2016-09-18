package com.chinaredstar.commonBiz.bean;

import com.xiwa.base.bean.Identified;

import java.util.Date;

/**
 * Created by lenovo on 2016/7/13.
 */
public class RedstarScoreRule implements Identified {

    private int id;

    private String name;//  名称

    private String alias;//  别名

    private Date createDate;//  创建时间

    private Integer createEmployeeId;//  创建员工ID

    private String createXingMing;//  创建人姓名

    private Date updateDate;//  修改时间

    private Integer updateEmployeeId;//  修改员工ID

    private String updateEmployeeXingMing;//  修改员工姓名

    private Integer deltaValue;//  变更数值

    private String remark;//  备注

    private boolean used;// 是否使用

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getCreateEmployeeId() {
        return createEmployeeId;
    }

    public void setCreateEmployeeId(Integer createEmployeeId) {
        this.createEmployeeId = createEmployeeId;
    }

    public String getCreateXingMing() {
        return createXingMing;
    }

    public void setCreateXingMing(String createXingMing) {
        this.createXingMing = createXingMing;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getUpdateEmployeeId() {
        return updateEmployeeId;
    }

    public void setUpdateEmployeeId(Integer updateEmployeeId) {
        this.updateEmployeeId = updateEmployeeId;
    }

    public String getUpdateEmployeeXingMing() {
        return updateEmployeeXingMing;
    }

    public void setUpdateEmployeeXingMing(String updateEmployeeXingMing) {
        this.updateEmployeeXingMing = updateEmployeeXingMing;
    }

    public Integer getDeltaValue() {
        return deltaValue;
    }

    public void setDeltaValue(Integer deltaValue) {
        this.deltaValue = deltaValue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
