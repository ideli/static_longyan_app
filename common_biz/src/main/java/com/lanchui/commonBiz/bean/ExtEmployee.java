package com.lanchui.commonBiz.bean;

import com.xiwa.base.bean.Identified;

import java.util.Date;

/**
 * Created by lenovo on 2016/4/26.
 */
public class ExtEmployee implements Identified {

    private int id;
    private String city;
    private String province;
    private int merchantId;
    private String name;
    private String area;
    private String provinceCode;
    private String cityCode;
    private String areaCode;
    private String address;
    private String logo;
    private boolean userOpen;
    private int currentNo;
    private String hotline;
    private String pickupAddress;//提货点
    private String distributionCharge;//配送费
    private String freeDistribution;//配送费满减规则


    private String programFeatures;
    private Integer areaMonut;
    private Integer roomMount;
    private Integer buildingAmount;
    private Integer alreadyCheckAmount;
    private Integer priceSection;
    private String buildingDate;
    private String developers;


    private Integer createEmployeeId;
    private Integer updateEmployeeId;
    private String  createXingMing;
    private String  updateEmployeeXingMing;
    private Date updateDate;

    private Date createDate;


    private Integer  ownerId;

    private String ownerXingMing;

    private String source;

    //录入数量
    private  Integer alreadyInputAmount;

    private Double inputRate;

    private Double occupanyRate;

    public Double getInputRate() {
        return inputRate;
    }

    private  Double distance;

    public void setInputRate(Double inputRate) {
        this.inputRate = inputRate;
    }

    public Double getOccupanyRate() {
        return occupanyRate;
    }

    public void setOccupanyRate(Double occupanyRate) {
        this.occupanyRate = occupanyRate;
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
        this.ownerXingMing = ownerXingMing;
    }

    public Integer getCreateEmployeeId() {
        return createEmployeeId;
    }

    public void setCreateEmployeeId(Integer createEmployeeId) {
        this.createEmployeeId = createEmployeeId;
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

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getProgramFeatures() {
        return programFeatures;
    }

    public void setProgramFeatures(String programFeatures) {
        this.programFeatures = programFeatures;
    }

    public Integer getAreaMonut() {
        return areaMonut;
    }

    public void setAreaMonut(Integer areaMonut) {
        this.areaMonut = areaMonut;
    }

    public Integer getRoomMount() {
        return roomMount;
    }

    public void setRoomMount(Integer roomMount) {
        this.roomMount = roomMount;
    }

    public Integer getBuildingAmount() {
        return buildingAmount;
    }

    public void setBuildingAmount(Integer buildingAmount) {
        this.buildingAmount = buildingAmount;
    }

    public Integer getAlreadyCheckAmount() {
        return alreadyCheckAmount;
    }

    public void setAlreadyCheckAmount(Integer alreadyCheckAmount) {
        this.alreadyCheckAmount = alreadyCheckAmount;
    }

    public Integer getPriceSection() {
        return priceSection;
    }

    public void setPriceSection(Integer priceSection) {
        this.priceSection = priceSection;
    }

    public String getBuildingDate() {
        return buildingDate;
    }

    public void setBuildingDate(String buildingDate) {
        this.buildingDate = buildingDate;
    }

    public String getDevelopers() {
        return developers;
    }

    public void setDevelopers(String developers) {
        this.developers = developers;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLogo() { return logo; }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public boolean isUserOpen() {
        return userOpen;
    }

    public void setUserOpen(boolean userOpen) {
        this.userOpen = userOpen;
    }

    public int getCurrentNo() {
        return currentNo;
    }

    public void setCurrentNo(int currentNo) {
        this.currentNo = currentNo;
    }

    public String getHotline() {
        return hotline;
    }

    public void setHotline(String hotline) {
        this.hotline = hotline;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public String getDistributionCharge() {
        return distributionCharge;
    }

    public void setDistributionCharge(String distributionCharge) {
        this.distributionCharge = distributionCharge;
    }

    public String getFreeDistribution() {
        return freeDistribution;
    }

    public void setFreeDistribution(String freeDistribution) {
        this.freeDistribution = freeDistribution;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateXingMing() {
        return createXingMing;
    }

    public void setCreateXingMing(String createXingMing) {
        this.createXingMing = createXingMing;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getAlreadyInputAmount() {
        return alreadyInputAmount;
    }

    public void setAlreadyInputAmount(Integer alreadyInputAmount) {
        this.alreadyInputAmount = alreadyInputAmount;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
