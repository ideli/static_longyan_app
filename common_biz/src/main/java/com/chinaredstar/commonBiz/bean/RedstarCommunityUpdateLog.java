package com.chinaredstar.commonBiz.bean;

import com.xiwa.base.bean.Identified;

import java.util.Date;


/**
 * Created by StevenDong on 2016/9/21
 */
public class RedstarCommunityUpdateLog extends RedstarCommunity implements Identified {

    private int communityId;

    private Integer reviewStatus = 0; //审核状态

    private String editColumnName; //本次修改的字段名

    private Date auditShowDate; //审核显示时间（方便前台展示非数据库字段）

    public RedstarCommunityUpdateLog() {

    }

    public RedstarCommunityUpdateLog(RedstarCommunity community) {

        this.setCommunityId(community.getId());
        this.setAddress(community.getAddress());
        this.setAlreadyCheckAmount(community.getAlreadyCheckAmount());
        this.setAlreadyInputAmount(community.getAlreadyInputAmount());
        this.setArea(community.getArea());
        this.setAreaCode(community.getAreaCode());
        this.setAreaMonut(community.getAreaMonut());
        this.setBuildingAmount(community.getBuildingAmount());
        this.setBuildingDate(community.getBuildingDate());
        this.setCity(community.getCity());
        this.setCityCode(community.getCityCode());
        this.setCompleteStatus(community.getCompleteStatus());
        this.setConstructionTypes(community.getConstructionTypes());
        this.setCreateDate(community.getCreateDate());
        this.setCreateEmployeeId(community.getCreateEmployeeId());
        this.setCreateXingMing(community.getCreateXingMing());
        this.setCurrentNo(community.getCurrentNo());
        this.setDataBelong(community.getDataBelong());
        this.setDeliveryTime(community.getDeliveryTime());
        this.setDevelopers(community.getDevelopers());
        this.setDistributionCharge(community.getDistributionCharge());
        this.setFreeDistribution(community.getFreeDistribution());
        this.setHotline(community.getHotline());
        this.setInputRate(community.getInputRate());
        this.setLatitude(community.getLatitude());
        this.setLogo(community.getLogo());
        this.setLongitude(community.getLongitude());
        this.setMerchantId(community.getMerchantId());
        this.setName(community.getName());
        this.setOccupanyRate(community.getOccupanyRate());
        this.setOwnerId(community.getOwnerId());
        this.setOwnerMallId(community.getOwnerMallId());
        this.setOwnerMallName(community.getOwnerMallName());
        this.setOwnerXingMing(community.getOwnerXingMing());
        this.setPickupAddress(community.getPickupAddress());
        this.setPriceSection(community.getPriceSection());
        this.setProgramFeatures(community.getProgramFeatures());
        this.setPropertyName(community.getPropertyName());
        this.setProvince(community.getProvince());
        this.setProvinceCode(community.getProvinceCode());
        this.setReclaimStatus(community.getReclaimStatus());
        this.setRenovations(community.getRenovations());
        this.setReviewStatus(community.getReviewStatus());
        this.setRoomMount(community.getRoomMount());
        this.setShortName(community.getShortName());
        this.setSource(community.getSource());
        this.setUpdateDate(community.getUpdateDate());
        this.setUpdateEmployeeId(community.getUpdateEmployeeId());
        this.setUpdateEmployeeXingMing(community.getUpdateEmployeeXingMing());
    }

    public Date getAuditShowDate() {
        return auditShowDate;
    }

    public void setAuditShowDate(Date auditShowDate) {
        this.auditShowDate = auditShowDate;
    }

    public int getCommunityId() {
        return communityId;
    }

    public void setCommunityId(int communityId) {
        this.communityId = communityId;
    }

    @Override
    public Integer getReviewStatus() {
        return reviewStatus;
    }

    @Override
    public void setReviewStatus(Integer reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public String getEditColumnName() {
        return editColumnName;
    }

    public void setEditColumnName(String editColumnName) {
        this.editColumnName = editColumnName;
    }
}
