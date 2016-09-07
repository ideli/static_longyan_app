package com.chinaredstar.longyan.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Entity
@Table(name = "xiwa_redstar_shopping_mall")
public class RedstarShoppingMall implements Serializable {
    private static final long serialVersionUID = -519019700973369484L;
    @Id
    @GeneratedValue(generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(columnDefinition = "BIGINT")
    private Integer id;

    private String name;

    private String type;

    private String province;

    private String city;

    private String area;

    private String provinceCode;

    private String cityCode;

    private String areaCode;

    private BigDecimal lng;

    private BigDecimal lat;

    private Date openingDate;

    private BigDecimal constructionArea;

    private BigDecimal rentArea;

    private String businessHours;

    private Integer organizationId;

    private String mallCode;

    private String contactXingming;

    private String contactPhone;

    private String contactMail;

    private String businessCategory;

    private Integer radiationRange;

    private Integer inputMemberAmount;

    private Integer inputCommunityAmount;

    private Integer inputMemberAmountRank;

    private Integer inputCommunityAmountRank;

    private Integer inputCommunityRoomAmount;

    private Integer employeeCount;

    private String mallType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
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

    public BigDecimal getLng() {
        return lng;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public Date getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
    }

    public BigDecimal getConstructionArea() {
        return constructionArea;
    }

    public void setConstructionArea(BigDecimal constructionArea) {
        this.constructionArea = constructionArea;
    }

    public BigDecimal getRentArea() {
        return rentArea;
    }

    public void setRentArea(BigDecimal rentArea) {
        this.rentArea = rentArea;
    }

    public String getBusinessHours() {
        return businessHours;
    }

    public void setBusinessHours(String businessHours) {
        this.businessHours = businessHours == null ? null : businessHours.trim();
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public String getMallCode() {
        return mallCode;
    }

    public void setMallCode(String mallCode) {
        this.mallCode = mallCode == null ? null : mallCode.trim();
    }

    public String getContactXingming() {
        return contactXingming;
    }

    public void setContactXingming(String contactXingming) {
        this.contactXingming = contactXingming == null ? null : contactXingming.trim();
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone == null ? null : contactPhone.trim();
    }

    public String getContactMail() {
        return contactMail;
    }

    public void setContactMail(String contactMail) {
        this.contactMail = contactMail == null ? null : contactMail.trim();
    }

    public String getBusinessCategory() {
        return businessCategory;
    }

    public void setBusinessCategory(String businessCategory) {
        this.businessCategory = businessCategory == null ? null : businessCategory.trim();
    }

    public Integer getRadiationRange() {
        return radiationRange;
    }

    public void setRadiationRange(Integer radiationRange) {
        this.radiationRange = radiationRange;
    }

    public Integer getInputMemberAmount() {
        return inputMemberAmount;
    }

    public void setInputMemberAmount(Integer inputMemberAmount) {
        this.inputMemberAmount = inputMemberAmount;
    }

    public Integer getInputCommunityAmount() {
        return inputCommunityAmount;
    }

    public void setInputCommunityAmount(Integer inputCommunityAmount) {
        this.inputCommunityAmount = inputCommunityAmount;
    }

    public Integer getInputMemberAmountRank() {
        return inputMemberAmountRank;
    }

    public void setInputMemberAmountRank(Integer inputMemberAmountRank) {
        this.inputMemberAmountRank = inputMemberAmountRank;
    }

    public Integer getInputCommunityAmountRank() {
        return inputCommunityAmountRank;
    }

    public void setInputCommunityAmountRank(Integer inputCommunityAmountRank) {
        this.inputCommunityAmountRank = inputCommunityAmountRank;
    }

    public Integer getInputCommunityRoomAmount() {
        return inputCommunityRoomAmount;
    }

    public void setInputCommunityRoomAmount(Integer inputCommunityRoomAmount) {
        this.inputCommunityRoomAmount = inputCommunityRoomAmount;
    }

    public Integer getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(Integer employeeCount) {
        this.employeeCount = employeeCount;
    }

    public String getMallType() {
        return mallType;
    }

    public void setMallType(String mallType) {
        this.mallType = mallType == null ? null : mallType.trim();
    }
}