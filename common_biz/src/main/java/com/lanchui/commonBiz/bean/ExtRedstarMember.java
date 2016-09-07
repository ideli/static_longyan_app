package com.lanchui.commonBiz.bean;

import com.xiwa.base.bean.Identified;

import java.util.Date;

/**
 * Created by niu on 2016/6/27.
 */
public class ExtRedstarMember implements Identified {


    private int id;
    private Date createDate;
    private Date updateDate;
    private int createEmployeeId;
    private String createXingming;
    private int updateEmployeeId;
    private String updateEmployeeXingMing;
    private int ownerId;
    private String ownerXingMing;
    private String province;
    private String provinceCode;
    private String city;
    private String cityCode;
    private String area;
    private String areaCode;
    private String xingMing;
    private int communityId;
    private String communityName;
    private String building;
    private String unit;
    private String gender;
    private String phone;


    private String room;
    private String source;

    private String areaAmount;
    private Integer areaAmountId;

    private  String roomLayout;
    private Integer roomLayoutId;


    private String renovationStatus;
    private Integer renovationStatusId;


    private  String roomType;

    private  Integer roomTypeId;


    /**
     * 2016 05 19 新增字段
     * hallAmount（INT），bedroomAmount（INT），housingAreaAmount（double）
     */
    private  Integer hallAmount;

    private  Integer bedroomAmount;

    private  Double housingAreaAmount;

    private String communityAddress;

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
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

    public int getCreateEmployeeId() {
        return createEmployeeId;
    }

    public void setCreateEmployeeId(int createEmployeeId) {
        this.createEmployeeId = createEmployeeId;
    }

    public String getCreateXingming() {
        return createXingming;
    }

    public void setCreateXingming(String createXingming) {
        this.createXingming = createXingming;
    }

    public int getUpdateEmployeeId() {
        return updateEmployeeId;
    }

    public void setUpdateEmployeeId(int updateEmployeeId) {
        this.updateEmployeeId = updateEmployeeId;
    }

    public String getUpdateEmployeeXingMing() {
        return updateEmployeeXingMing;
    }

    public void setUpdateEmployeeXingMing(String updateEmployeeXingMing) {
        this.updateEmployeeXingMing = updateEmployeeXingMing;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerXingMing() {
        return ownerXingMing;
    }

    public void setOwnerXingMing(String ownerXingMing) {
        this.ownerXingMing = ownerXingMing;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getXingMing() {
        return xingMing;
    }

    public void setXingMing(String xingMing) {
        this.xingMing = xingMing;
    }

    public int getCommunityId() {
        return communityId;
    }

    public void setCommunityId(int communityId) {
        this.communityId = communityId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRenovationStatus() {
        return renovationStatus;
    }

    public void setRenovationStatus(String renovationStatus) {
        this.renovationStatus = renovationStatus;
    }




    @Override
    public int getId() {
        return id;
    }


    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


    public String getAreaAmount() {
        return areaAmount;
    }

    public void setAreaAmount(String areaAmount) {
        this.areaAmount = areaAmount;
    }

    public Integer getAreaAmountId() {
        return areaAmountId;
    }

    public void setAreaAmountId(Integer areaAmountId) {
        this.areaAmountId = areaAmountId;
    }

    public String getRoomLayout() {
        return roomLayout;
    }

    public void setRoomLayout(String roomLayout) {
        this.roomLayout = roomLayout;
    }

    public Integer getRoomLayoutId() {
        return roomLayoutId;
    }

    public void setRoomLayoutId(Integer roomLayoutId) {
        this.roomLayoutId = roomLayoutId;
    }

    public Integer getRenovationStatusId() {
        return renovationStatusId;
    }

    public void setRenovationStatusId(Integer renovationStatusId) {
        this.renovationStatusId = renovationStatusId;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Integer getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Integer roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public Integer getHallAmount() {
        return hallAmount;
    }

    public void setHallAmount(Integer hallAmount) {
        this.hallAmount = hallAmount;
    }

    public Integer getBedroomAmount() {
        return bedroomAmount;
    }

    public void setBedroomAmount(Integer bedroomAmount) {
        this.bedroomAmount = bedroomAmount;
    }

    public Double getHousingAreaAmount() {
        return housingAreaAmount;
    }

    public void setHousingAreaAmount(Double housingAreaAmount) {
        this.housingAreaAmount = housingAreaAmount;
    }

    public String getCommunityAddress() {
        return communityAddress;
    }

    public void setCommunityAddress(String communityAddress) {
        this.communityAddress = communityAddress;
    }
}
