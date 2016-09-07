package com.chinaredstar.longyan.model;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
@Table(name = "xiwa_redstar_store")
public class RedstarStore implements Serializable {
    private static final long serialVersionUID = 2303370837081602727L;
    @Id
    @GeneratedValue(generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(columnDefinition = "BIGINT")
    private Integer id;

    private String storeName;

    private String storeType;

    private String cityCode;

    private String city;

    private Integer belongMarketId;

    private String belongMarketCode;

    private String belongMarketAddress;

    private String belongMarket;

    private Date openTime;

    private Date closeTime;

    private String openingTime;

    private String rootCategory;

    private String type;

    private String specialService;

    private String salesSupport;

    private String storeImg;

    private String honors;

    @Column(columnDefinition = "TINYINT(2)")
    private boolean fixedPrices;

    @Column(columnDefinition = "TINYINT(2)")
    private boolean genuineQuery;

    @Column(columnDefinition = "TINYINT(2)")
    private boolean greenLead;

    @Column(columnDefinition = "TINYINT(2)")
    private boolean categorySales;

    private String brandLogo;

    private String brandName;

    private String introduction;

    private Date brandCreateDate;

    private Date createDate;

    private String qrCode;

    private String storeCode;

    private String complaintTel;

    @Transient
    private boolean favorite = false;

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName == null ? null : storeName.trim();
    }

    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType == null ? null : storeType.trim();
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public Integer getBelongMarketId() {
        return belongMarketId;
    }

    public void setBelongMarketId(Integer belongMarketId) {
        this.belongMarketId = belongMarketId;
    }

    public String getBelongMarketCode() {
        return belongMarketCode;
    }

    public void setBelongMarketCode(String belongMarketCode) {
        this.belongMarketCode = belongMarketCode == null ? null : belongMarketCode.trim();
    }

    public String getBelongMarketAddress() {
        return belongMarketAddress;
    }

    public void setBelongMarketAddress(String belongMarketAddress) {
        this.belongMarketAddress = belongMarketAddress == null ? null : belongMarketAddress.trim();
    }

    public String getBelongMarket() {
        return belongMarket;
    }

    public void setBelongMarket(String belongMarket) {
        this.belongMarket = belongMarket == null ? null : belongMarket.trim();
    }

    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime == null ? null : openingTime.trim();
    }

    public String getRootCategory() {
        return rootCategory;
    }

    public void setRootCategory(String rootCategory) {
        this.rootCategory = rootCategory == null ? null : rootCategory.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getSpecialService() {
        return specialService;
    }

    public void setSpecialService(String specialService) {
        this.specialService = specialService == null ? null : specialService.trim();
    }

    public String getSalesSupport() {
        return salesSupport;
    }

    public void setSalesSupport(String salesSupport) {
        this.salesSupport = salesSupport == null ? null : salesSupport.trim();
    }

    public String getStoreImg() {
        return storeImg;
    }

    public void setStoreImg(String storeImg) {
        this.storeImg = storeImg == null ? null : storeImg.trim();
    }

    public String getHonors() {
        return honors;
    }

    public void setHonors(String honors) {
        this.honors = honors == null ? null : honors.trim();
    }

    public boolean getFixedPrices() {
        return fixedPrices;
    }

    public void setFixedPrices(boolean fixedPrices) {
        this.fixedPrices = fixedPrices;
    }

    public boolean getGenuineQuery() {
        return genuineQuery;
    }

    public void setGenuineQuery(boolean genuineQuery) {
        this.genuineQuery = genuineQuery;
    }

    public boolean getGreenLead() {
        return greenLead;
    }

    public void setGreenLead(boolean greenLead) {
        this.greenLead = greenLead;
    }

    public boolean getCategorySales() {
        return categorySales;
    }

    public void setCategorySales(boolean categorySales) {
        this.categorySales = categorySales;
    }

    public String getBrandLogo() {
        return brandLogo;
    }

    public void setBrandLogo(String brandLogo) {
        this.brandLogo = brandLogo == null ? null : brandLogo.trim();
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName == null ? null : brandName.trim();
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    public Date getBrandCreateDate() {
        return brandCreateDate;
    }

    public void setBrandCreateDate(Date brandCreateDate) {
        this.brandCreateDate = brandCreateDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode == null ? null : qrCode.trim();
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode == null ? null : storeCode.trim();
    }

    public String getComplaintTel() {
        return complaintTel;
    }

    public void setComplaintTel(String complaintTel) {
        this.complaintTel = complaintTel == null ? null : complaintTel.trim();
    }
}