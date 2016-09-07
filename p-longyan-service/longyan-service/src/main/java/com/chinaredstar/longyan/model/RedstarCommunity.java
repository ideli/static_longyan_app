package com.chinaredstar.longyan.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "xiwa_redstar_community")
public class RedstarCommunity implements Serializable {

    private static final long serialVersionUID = 3538387222476428741L;
    @Id
    @GeneratedValue(generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(columnDefinition = "BIGINT")
    private Integer id;

    private String city;

    private String province;

    private Integer merchantId;

    private String name;

    private String area;

    private String provinceCode;

    private String cityCode;

    private String areaCode;

    private String address;

    private String logo;

    @Column(columnDefinition = "TINYINT(2)")
    private boolean userOpen;

    private Integer currentNo;

    private BigDecimal propertyFare;

    private Integer areaMonut;

    private Integer roomMount;

    private Integer parkingMount;

    private String hotline;

    private String pickupAddress;

    private BigDecimal distributionCharge;

    private BigDecimal freeDistribution;

    @Column(columnDefinition = "TINYINT(2)")
    private boolean systemOpen;

    private Integer alreadyInputAmount;

    private Integer alreadyCheckAmount;

    private BigDecimal longitude;

    private BigDecimal latitude;

    private Integer buildingAmount;

    private Integer priceSection;

    private Integer createEmployeeId;

    private String createXingming;

    private Integer updateEmployeeId;

    private String updateEmployeeXingMing;

    private Date updateDate;

    private Date createDate;

    private Integer ownerId;

    private String ownerXingMing;

    private Integer ownerMallId;

    private String ownerMallName;

    private String source;


    private String shortName;

    private String constructionTypes;

    private String renovations;

    private String deliveryTime;

    //非数据库字段，前端需要字段
    @Transient
    private Double inputRate = 0.0;//  录入率
    @Transient
    private Double occupanyRate = 0.0;//  入住率

    public Double getInputRate() {
        return inputRate;
    }

    public void setInputRate(Double inputRate) {
        this.inputRate = inputRate;
    }

    public Double getOccupanyRate() {
        return occupanyRate;
    }

    public void setOccupanyRate(Double occupanyRate) {
        this.occupanyRate = occupanyRate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode == null ? null : provinceCode.trim();
    }

    public Integer getAreaMonut() {
        return areaMonut;
    }

    public void setAreaMonut(Integer areaMonut) {
        this.areaMonut = areaMonut;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    public boolean getUserOpen() {
        return userOpen;
    }

    public void setUserOpen(boolean userOpen) {
        this.userOpen = userOpen;
    }

    public Integer getCurrentNo() {
        return currentNo;
    }

    public void setCurrentNo(Integer currentNo) {
        this.currentNo = currentNo;
    }

    public BigDecimal getPropertyFare() {
        return propertyFare;
    }

    public void setPropertyFare(BigDecimal propertyFare) {
        this.propertyFare = propertyFare;
    }


    public Integer getRoomMount() {
        return roomMount;
    }

    public void setRoomMount(Integer roomMount) {
        this.roomMount = roomMount;
    }

    public Integer getParkingMount() {
        return parkingMount;
    }

    public void setParkingMount(Integer parkingMount) {
        this.parkingMount = parkingMount;
    }

    public String getHotline() {
        return hotline;
    }

    public void setHotline(String hotline) {
        this.hotline = hotline == null ? null : hotline.trim();
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress == null ? null : pickupAddress.trim();
    }

    public BigDecimal getDistributionCharge() {
        return distributionCharge;
    }

    public void setDistributionCharge(BigDecimal distributionCharge) {
        this.distributionCharge = distributionCharge;
    }

    public BigDecimal getFreeDistribution() {
        return freeDistribution;
    }

    public void setFreeDistribution(BigDecimal freeDistribution) {
        this.freeDistribution = freeDistribution;
    }

    public boolean getSystemOpen() {
        return systemOpen;
    }

    public void setSystemOpen(boolean systemOpen) {
        this.systemOpen = systemOpen;
    }

    public Integer getAlreadyInputAmount() {
        return alreadyInputAmount;
    }

    public void setAlreadyInputAmount(Integer alreadyInputAmount) {
        this.alreadyInputAmount = alreadyInputAmount;
    }

    public Integer getAlreadyCheckAmount() {
        return alreadyCheckAmount;
    }

    public void setAlreadyCheckAmount(Integer alreadyCheckAmount) {
        this.alreadyCheckAmount = alreadyCheckAmount;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public Integer getBuildingAmount() {
        return buildingAmount;
    }

    public void setBuildingAmount(Integer buildingAmount) {
        this.buildingAmount = buildingAmount;
    }

    public Integer getPriceSection() {
        return priceSection;
    }

    public void setPriceSection(Integer priceSection) {
        this.priceSection = priceSection;
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

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    public Integer getOwnerMallId() {
        return ownerMallId;
    }

    public void setOwnerMallId(Integer ownerMallId) {
        this.ownerMallId = ownerMallId;
    }

    public String getOwnerMallName() {
        return ownerMallName;
    }

    public void setOwnerMallName(String ownerMallName) {
        this.ownerMallName = ownerMallName == null ? null : ownerMallName.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }


    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName == null ? null : shortName.trim();
    }

    public String getConstructionTypes() {
        return constructionTypes;
    }

    public void setConstructionTypes(String constructionTypes) {
        this.constructionTypes = constructionTypes == null ? null : constructionTypes.trim();
    }

    public String getRenovations() {
        return renovations;
    }

    public void setRenovations(String renovations) {
        this.renovations = renovations == null ? null : renovations.trim();
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime == null ? null : deliveryTime.trim();
    }
}