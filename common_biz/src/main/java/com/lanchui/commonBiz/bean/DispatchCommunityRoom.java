package com.lanchui.commonBiz.bean;

import com.xiwa.base.bean.Identified;

import java.util.Date;

/**
 * Created by wangj on 2015/7/21.
 */
public class DispatchCommunityRoom implements Identified {
    private int id;

    private Date createDate;//  创建时间

    private String createEmployeeId;//  创建员工ID

    private String createXingMing;//  创建姓名

    private int merchantId;//  商户ID

    private String merchantName;//  商户姓名

    private int createMerchantUserId;//  创建商业用户ID

    private String createMerchantUserXingMing;//  创建商业用户姓名

    private int communityId;//  小区ID

    private String communityName;//  小区姓名

    private int createCommunityUserId;//  创建小区用户ID

    private String createCommunityUserXingMing;//  创建小区用户姓名

    private int gardenId;//

    private String gardenName;//

    private int buildingId;//  栋ID

    private String buildingName;//  栋名字

    private int unitId;//  单元ID

    private String unitName;//  单元名

    private String room;//  室

    private String status;//  状态

    private String floor;//  楼层

    private String ownerXingMing;//  业主姓名

    private String ownerPhone;//  业主电话

    private String remark;//  备注

    private Date contractSigningDate;//合同签订日期

    private String bankName;//托付银行

    private Date rentDate;//交房出租日期

    private String purchaseContractNo;//购房合同号

    private String banckAccount;//银行账号

    private String apartmentLayout;//户型

    private Date repossessionDate;//收楼日期

    private String accountName;//账号姓名

    private String orientation;//朝向

    private Date reportDecorationDate;//报装修日期

    private String landCertificateNo;//土地证号

    private String builtupArea;//建筑面积

    private String propertyRightNo;//产权证号

    private String setArea;//套内面积

    private String haveKey;//钥匙情况

    private String publicArea;//公摊面积

    private String monthlyManagementFee;//月管理费

    private String publicCoefficient;//公摊系数

    private String payment;//缴纳情况

    private String application;//用途

    private String billingAddress;//账单地址

    private String ownerPlate;//车牌号

    private String monthlyCarCharge;//月车收费

    private String parkingSituation;//车位情况

    private String unitPersonnel;//单元人员

    private String pet;//宠物

    private String housingResources;//房源

    private String finalEdit;//最后编辑

    private String maintenanceList;//维修单

    private String paidService;//有偿服务

    private String idCardNumber;//身份证

    private String idCardAddress;//证件地址

    private String paymentAmount;//缴费金额

    private String paymentPeriod;//缴费周期

    private Date expiresdate;//缴费到期时间

    private String feeScale;//收费标准

    private String keyAddress;//关联地址

    @Override
    public int getId() {
        return id;
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

    public String getCreateEmployeeId() {
        return createEmployeeId;
    }

    public void setCreateEmployeeId(String createEmployeeId) {
        this.createEmployeeId = createEmployeeId;
    }

    public String getCreateXingMing() {
        return createXingMing;
    }

    public void setCreateXingMing(String createXingMing) {
        this.createXingMing = createXingMing;
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

    public int getGardenId() {
        return gardenId;
    }

    public void setGardenId(int gardenId) {
        this.gardenId = gardenId;
    }

    public String getGardenName() {
        return gardenName;
    }

    public void setGardenName(String gardenName) {
        this.gardenName = gardenName;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOwnerXingMing() {
        return ownerXingMing;
    }

    public void setOwnerXingMing(String ownerXingMing) {
        this.ownerXingMing = ownerXingMing;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public Date getContractSigningDate() {
        return contractSigningDate;
    }

    public void setContractSigningDate(Date contractSigningDate) {
        this.contractSigningDate = contractSigningDate;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Date getRentDate() {
        return rentDate;
    }

    public void setRentDate(Date rentDate) {
        this.rentDate = rentDate;
    }

    public String getPurchaseContractNo() {
        return purchaseContractNo;
    }

    public void setPurchaseContractNo(String purchaseContractNo) {
        this.purchaseContractNo = purchaseContractNo;
    }

    public String getBanckAccount() {
        return banckAccount;
    }

    public void setBanckAccount(String banckAccount) {
        this.banckAccount = banckAccount;
    }

    public String getApartmentLayout() {
        return apartmentLayout;
    }

    public void setApartmentLayout(String apartmentLayout) {
        this.apartmentLayout = apartmentLayout;
    }

    public Date getRepossessionDate() {
        return repossessionDate;
    }

    public void setRepossessionDate(Date repossessionDate) {
        this.repossessionDate = repossessionDate;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public Date getReportDecorationDate() {
        return reportDecorationDate;
    }

    public void setReportDecorationDate(Date reportDecorationDate) {
        this.reportDecorationDate = reportDecorationDate;
    }

    public String getLandCertificateNo() {
        return landCertificateNo;
    }

    public void setLandCertificateNo(String landCertificateNo) {
        this.landCertificateNo = landCertificateNo;
    }

    public String getBuiltupArea() {
        return builtupArea;
    }

    public void setBuiltupArea(String builtupArea) {
        this.builtupArea = builtupArea;
    }

    public String getPropertyRightNo() {
        return propertyRightNo;
    }

    public void setPropertyRightNo(String propertyRightNo) {
        this.propertyRightNo = propertyRightNo;
    }

    public String getSetArea() {
        return setArea;
    }

    public void setSetArea(String setArea) {
        this.setArea = setArea;
    }

    public String getHaveKey() {
        return haveKey;
    }

    public void setHaveKey(String haveKey) {
        this.haveKey = haveKey;
    }

    public String getPublicArea() {
        return publicArea;
    }

    public void setPublicArea(String publicArea) {
        this.publicArea = publicArea;
    }

    public String getMonthlyManagementFee() {
        return monthlyManagementFee;
    }

    public void setMonthlyManagementFee(String monthlyManagementFee) {
        this.monthlyManagementFee = monthlyManagementFee;
    }

    public String getPublicCoefficient() {
        return publicCoefficient;
    }

    public void setPublicCoefficient(String publicCoefficient) {
        this.publicCoefficient = publicCoefficient;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getOwnerPlate() {
        return ownerPlate;
    }

    public void setOwnerPlate(String ownerPlate) {
        this.ownerPlate = ownerPlate;
    }

    public String getMonthlyCarCharge() {
        return monthlyCarCharge;
    }

    public void setMonthlyCarCharge(String monthlyCarCharge) {
        this.monthlyCarCharge = monthlyCarCharge;
    }

    public String getParkingSituation() {
        return parkingSituation;
    }

    public void setParkingSituation(String parkingSituation) {
        this.parkingSituation = parkingSituation;
    }

    public String getUnitPersonnel() {
        return unitPersonnel;
    }

    public void setUnitPersonnel(String unitPersonnel) {
        this.unitPersonnel = unitPersonnel;
    }

    public String getPet() {
        return pet;
    }

    public void setPet(String pet) {
        this.pet = pet;
    }

    public String getHousingResources() {
        return housingResources;
    }

    public void setHousingResources(String housingResources) {
        this.housingResources = housingResources;
    }

    public String getFinalEdit() {
        return finalEdit;
    }

    public void setFinalEdit(String finalEdit) {
        this.finalEdit = finalEdit;
    }

    public String getMaintenanceList() {
        return maintenanceList;
    }

    public void setMaintenanceList(String maintenanceList) {
        this.maintenanceList = maintenanceList;
    }

    public String getPaidService() {
        return paidService;
    }

    public void setPaidService(String paidService) {
        this.paidService = paidService;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getIdCardAddress() {
        return idCardAddress;
    }

    public void setIdCardAddress(String idCardAddress) {
        this.idCardAddress = idCardAddress;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentPeriod() {
        return paymentPeriod;
    }

    public void setPaymentPeriod(String paymentPeriod) {
        this.paymentPeriod = paymentPeriod;
    }

    public Date getExpiresdate() {
        return expiresdate;
    }

    public void setExpiresdate(Date expiresdate) {
        this.expiresdate = expiresdate;
    }

    public String getFeeScale() {
        return feeScale;
    }

    public void setFeeScale(String feeScale) {
        this.feeScale = feeScale;
    }

    public String getKeyAddress() {
        return keyAddress;
    }

    public void setKeyAddress(String keyAddress) {
        this.keyAddress = keyAddress;
    }
}
