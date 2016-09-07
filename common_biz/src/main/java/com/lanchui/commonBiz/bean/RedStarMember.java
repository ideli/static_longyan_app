package com.lanchui.commonBiz.bean;

import com.xiwa.base.bean.Identified;

import java.util.Date;

/**
 * Created by lenovo on 2016/4/22.
 */
public class RedStarMember implements Identified {

    private int id;

    private Date createDate;//  创建时间

    private Date updateDate;//  修改时间

    private int createEmployeeId;//  创建员工ID

    private String createXingming;//  创建人姓名

    private int updateEmployeeId;//  修改员工ID

    private String updateEmployeeXingMing;//  修改员工姓名

    private int ownerId;//  分配员工id

    private String ownerXingMing;//  分配员工姓名

    private String province;//  省

    private String provinceCode;//  省代码

    private String city;//  市

    private String cityCode;//  市代码

    private String area;//  区

    private String areaCode;//  区代码

    private String xingMing;//  姓名

    private int communityId;//  小区ID

    private String communityName;//  小区名称
    private String building;//  栋

    private String unit;//  单元

    private String gender;//  性别

    private String phone;//  电话

    private String room;//  室

    private String source;//  数据来源

    private String areaAmount;//    建筑面积

    private Integer areaAmountId;// 建筑面积ID

    private  String roomLayout;//  房屋装修类型

    private Integer roomLayoutId;//  房屋装修类型ID

    private String renovationStatus;//  装修年限

    private Integer renovationStatusId;//  装修年限ID

    private  String roomType;//  房屋类型

    private  Integer roomTypeId;//  房屋类型ID

    private  Integer buildingId;//  栋ID

    private  Integer unitId;//  单元ID

    private String unitName;//  单元名称

    private String buildingName;//   栋名称


    /**
     * 2016 05 19 新增字段
     * hallAmount（INT），bedroomAmount（INT），housingAreaAmount（double）
     */
    private  Integer hallAmount;//  厅数量

    private  Integer bedroomAmount;//  室数量

    private  Double housingAreaAmount;//  精确面积

    private String communityAddress;//  小区地址




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


    public Integer getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
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
        this.unitName = unitName;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }
}
