package com.lanchui.commonBiz.bean;

import com.xiwa.base.bean.Identified;

import java.util.Date;

/**
 * Created by wangj on 2015/9/19.
 */
public class DispatchShop implements Identified{

    private int id;
    private  String address;
    private  String shopName;
    private  String shopOwner;
    private  String idCard;
    private  String fixedPhone;
    private  String phone;
    private  String province;
    private  String city;
    private  String area;
    private  String provinceCode;
    private  String cityCode;
    private  String areaCode;
    private  String installCategory;
    private  String weixin;
    private  String qq;
    private  String manageProjects;
    private  String status;
    private Date createDate;
    private  int saleCount;
    private  int shopCategoryId;
    private  String shopCategoryName;
    private  int merchantId;
    private  String merchantName;
    private  int createMerchantUserId;
    private  String createMerchantUserXingMing;
    private  int communityId;
    private  String communityName;
    private  int createCommunityUserId;
    private  String createCommunityUserXingMing;

    private String shopIcon;//商品图表

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopOwner() {
        return shopOwner;
    }

    public void setShopOwner(String shopOwner) {
        this.shopOwner = shopOwner;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getFixedPhone() {
        return fixedPhone;
    }

    public void setFixedPhone(String fixedPhone) {
        this.fixedPhone = fixedPhone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getInstallCategory() {
        return installCategory;
    }

    public void setInstallCategory(String installCategory) {
        this.installCategory = installCategory;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getManageProjects() {
        return manageProjects;
    }

    public void setManageProjects(String manageProjects) {
        this.manageProjects = manageProjects;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(int saleCount) {
        this.saleCount = saleCount;
    }

    public int getShopCategoryId() {
        return shopCategoryId;
    }

    public void setShopCategoryId(int shopCategoryId) {
        this.shopCategoryId = shopCategoryId;
    }

    public String getShopCategoryName() {
        return shopCategoryName;
    }

    public void setShopCategoryName(String shopCategoryName) {
        this.shopCategoryName = shopCategoryName;
    }

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public int getCreateMerchantUserId() {
        return createMerchantUserId;
    }

    public void setCreateMerchantUserId(int createMerchantUserId) {
        this.createMerchantUserId = createMerchantUserId;
    }

    public String getCreateMerchantUserXingMing() {
        return createMerchantUserXingMing;
    }

    public void setCreateMerchantUserXingMing(String createMerchantUserXingMing) {
        this.createMerchantUserXingMing = createMerchantUserXingMing;
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

    public int getCreateCommunityUserId() {
        return createCommunityUserId;
    }

    public void setCreateCommunityUserId(int createCommunityUserId) {
        this.createCommunityUserId = createCommunityUserId;
    }

    public String getCreateCommunityUserXingMing() {
        return createCommunityUserXingMing;
    }

    public void setCreateCommunityUserXingMing(String createCommunityUserXingMing) {
        this.createCommunityUserXingMing = createCommunityUserXingMing;
    }

    public String getShopIcon() {
        return shopIcon;
    }

    public void setShopIcon(String shopIcon) {
        this.shopIcon = shopIcon;
    }
}
