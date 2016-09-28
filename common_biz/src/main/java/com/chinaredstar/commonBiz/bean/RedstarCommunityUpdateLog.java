package com.chinaredstar.commonBiz.bean;

import com.xiwa.base.bean.Identified;

/**
 * Created by StevenDong on 2016/9/21
 */
public class RedstarCommunityUpdateLog extends RedstarCommunity implements Identified {

    private int communityId;

    private Integer reviewStatus=0; //审核状态

    private String editColumnName; //本次修改的字段名

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

    public RedstarCommunityUpdateLog() {

    }

    public RedstarCommunityUpdateLog(RedstarCommunity community) {
        this.setCommunityId(community.getId());
        this.setName(community.getName());
        this.setShortName(community.getShortName());
        this.setAddress(community.getAddress());
        this.setProvince(community.getProvince());
        this.setProvinceCode(community.getProvinceCode());
        this.setCity(community.getCity());
        this.setCityCode(community.getCityCode());
        this.setArea(community.getArea());
        this.setAreaCode(community.getArea());
        this.setAlreadyCheckAmount(community.getAlreadyCheckAmount());
        this.setAlreadyInputAmount(community.getAlreadyInputAmount());
        this.setHotline(community.getHotline());
        this.setProgramFeatures(community.getProgramFeatures());
        this.setAreaMonut(community.getAreaMonut());
        this.setRoomMount(community.getRoomMount());
        this.setBuildingAmount(community.getBuildingAmount());
        this.setPriceSection(community.getPriceSection());
        this.setBuildingDate(community.getBuildingDate());
        this.setDevelopers(community.getDevelopers());
        this.setCreateEmployeeId(community.getCreateEmployeeId());
        this.setCreateXingMing(community.getCreateXingMing());
        this.setUpdateEmployeeId(community.getUpdateEmployeeId());
        this.setUpdateEmployeeXingMing(community.getUpdateEmployeeXingMing());
        this.setCreateDate(community.getCreateDate());
        this.setUpdateDate(community.getUpdateDate());
        this.setLongitude(community.getLongitude());
        this.setLatitude(community.getLatitude());
        this.setOwnerId(community.getOwnerId());
        this.setOwnerXingMing(community.getOwnerXingMing());
        this.setOwnerMallId(community.getOwnerMallId());
        this.setOwnerMallName(community.getOwnerMallName());
        this.setSource(community.getSource());
        this.setConstructionTypes(community.getConstructionTypes());
        this.setRenovations(community.getRenovations());
        this.setDeliveryTime(community.getDeliveryTime());
        this.setPropertyName(community.getPropertyName());
        this.setDataBelong(community.getDataBelong());
        this.setReclaimStatus(community.getReclaimStatus());
        this.setCompleteStatus(community.getCompleteStatus());

    }
}
