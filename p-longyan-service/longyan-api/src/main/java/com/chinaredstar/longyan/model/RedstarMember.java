package com.chinaredstar.longyan.model;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class RedstarMember implements Serializable {
    private static final long serialVersionUID = 6598439408157797362L;

    private Integer id;

    private Date createDate ;

    private Date updateDate;

    private Integer createEmployeeId;

    private String createXingming;

    private Integer updateEmployeeId;

    private String updateEmployeeXingMing;

    private Integer ownerId;

    private String ownerXingMing;

    private String province;

    private String provinceCode;

    private String city;

    private String cityCode;

    private String area;

    private String areaCode;

    private String xingMing;

    private Integer communityId;

    private String communityName;

    private String building;

    private String unit;

    private String gender;

    private String phone;

    private String renovationStatus;

    private String areaAmount;

    private String room;

    private String source;

    private Integer areaAmountId;

    private String roomLayout;

    private Integer roomLayoutId;

    private Integer renovationStatusId;

    private Integer roomTypeId;

    private String roomType;

    private Integer hallAmount;

    private Integer bedroomAmount;

    private BigDecimal housingAreaAmount;

    private Integer buildingId;

    private String buildingName;

    private Integer unitId;

    private String unitName;

    private String shortName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getCreateEmployeeId() {
        return createEmployeeId;
    }

    public void setCreateEmployeeId(Integer createEmployeeId) {
        this.createEmployeeId = createEmployeeId;
    }

    public String getCreateXingming() {
        return createXingming;
    }

    public void setCreateXingming(String createXingming) {
        this.createXingming = createXingming == null ? null : createXingming.trim();
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
        this.updateEmployeeXingMing = updateEmployeeXingMing == null ? null : updateEmployeeXingMing.trim();
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerXingMing() {
        return ownerXingMing;
    }

    public void setOwnerXingMing(String ownerXingMing) {
        this.ownerXingMing = ownerXingMing == null ? null : ownerXingMing.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode == null ? null : provinceCode.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
    }

    public String getXingMing() {
        return xingMing;
    }

    public void setXingMing(String xingMing) {
        this.xingMing = xingMing == null ? null : xingMing.trim();
    }

    public Integer getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName == null ? null : communityName.trim();
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building == null ? null : building.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getRenovationStatus() {
        return renovationStatus;
    }

    public void setRenovationStatus(String renovationStatus) {
        this.renovationStatus = renovationStatus == null ? null : renovationStatus.trim();
    }

    public String getAreaAmount() {
        return areaAmount;
    }

    public void setAreaAmount(String areaAmount) {
        this.areaAmount = areaAmount == null ? null : areaAmount.trim();
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room == null ? null : room.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
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
        this.roomLayout = roomLayout == null ? null : roomLayout.trim();
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

    public Integer getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Integer roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType == null ? null : roomType.trim();
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

    public BigDecimal getHousingAreaAmount() {
        return housingAreaAmount;
    }

    public void setHousingAreaAmount(BigDecimal housingAreaAmount) {
        this.housingAreaAmount = housingAreaAmount;
    }

    public Integer getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName == null ? null : buildingName.trim();
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName == null ? null : unitName.trim();
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName == null ? null : shortName.trim();
    }
}