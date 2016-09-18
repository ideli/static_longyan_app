package com.chinaredstar.commonBiz.bean;

import com.xiwa.base.bean.Identified;

import java.util.Date;

/**
 * Created by LeiYun on 2016/7/12.
 */
public class RedstarCommunityBuilding implements Identified {

    private Date createDate;

    private Integer  createEmployeeId;//    创建员工ID

    private String createXingMing;//    创建人姓名

    private Integer communityId;//  小区id

    private int id;

    private  String communityName;//    小区名称

    private String buildingName;//  栋名称

    private Integer roomAmount;//  住宅数量

    private String remark;//    备注

    private Integer unitAmount;//   单元数

    private Integer floorAmount;//  楼层数

    @Override
    public int getId() {
        return id;
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

    public Integer getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }

    public void setId(int id) {
        this.id = id;
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

    public Integer getRoomAmount() {
        return roomAmount;
    }

    public void setRoomAmount(Integer roomAmount) {
        this.roomAmount = roomAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getUnitAmount() {
        return unitAmount;
    }

    public void setUnitAmount(Integer unitAmount) {
        this.unitAmount = unitAmount;
    }

    public Integer getFloorAmount() {
        return floorAmount;
    }

    public void setFloorAmount(Integer floorAmount) {
        this.floorAmount = floorAmount;
    }
}
