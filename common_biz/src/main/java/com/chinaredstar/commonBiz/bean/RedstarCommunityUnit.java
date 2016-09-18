package com.chinaredstar.commonBiz.bean;

import com.xiwa.base.bean.Identified;

import java.util.Date;

/**
 * Created by lenovo on 2016/7/12.
 */
public class RedstarCommunityUnit implements Identified {

    private int id;

    private Date createDate;//  创建时间

    private Integer  createEmployeeId;//    创建员工ID

    private Integer  communityId;//     小区ID

    private Integer  buildingId;//  栋ID

    private Integer  roomAmount;//  住宅数量

    private String createXingMing;//    创建人姓名

    private String communityName;//    小区名称

    private String buildingName;//  栋的名字

    private String unitName;//  单元名字

    private String remark;//    备注

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

    public Integer getCreateEmployeeId() {
        return createEmployeeId;
    }

    public void setCreateEmployeeId(Integer createEmployeeId) {
        this.createEmployeeId = createEmployeeId;
    }

    public Integer getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }

    public Integer getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
    }

    public Integer getRoomAmount() {
        return roomAmount;
    }

    public void setRoomAmount(Integer roomAmount) {
        this.roomAmount = roomAmount;
    }

    public String getCreateXingMing() {
        return createXingMing;
    }

    public void setCreateXingMing(String createXingMing) {
        this.createXingMing = createXingMing;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
