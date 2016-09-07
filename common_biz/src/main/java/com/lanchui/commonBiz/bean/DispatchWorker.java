package com.lanchui.commonBiz.bean;

import com.xiwa.base.bean.Identified;

import java.util.Date;

/**
 * Created by wangj on 2015/4/8.
 */
public class DispatchWorker implements Identified {
    private int id;
    private String xingming;//工人姓名
    private String idCardNumber;//身份证号
    private String phone;//工人手机号
    private String installCategory;//安装类别
    private String address;//地址
    private boolean insuranceEnable;//是否参加保险
    private boolean haveElectrical;//持有电工证
    private String constructionRange;//施工范围
    private String transport;//交通工具
    private boolean haveSmartphone;//是否使用智能手机
    private String remark;//备注
    private boolean constructionEnable;//是否施工
    private boolean enable;//是否可用
    private String constructionCount;//施工单数
    private String hometown;//籍贯
    private Date birthday;//生日
    private String appraisal;//评价
    private Date addingDate;//加盟时间
    private String bank;//开户银行
    private String branchName;//支行名称
    private String bankCardNumber;//银行卡号
    private boolean neverHire;//永不录用
    private String belongedId;//belongedId
    private String createEmployeeId;//创建人ID
    private String createXingMing;//创建人姓名
    private String serialNumber;//序列号
    private String province;//省名称
    private String city;//城市名称
    private String area;//城区名称
    private String provinceCode;//省代码
    private String cityCode;//城市代码
    private String areaCode;//城区代码
    private String weixinId;//微信ID
    private String shopId;//所属店铺
    private String shopName;//店铺名称
    private boolean showData;//是否显示
    private String weixin;//微信
    private String status;//状态
    private int merchantId;//商户ID
    private String merchantName;//商户名
    private int communityId;//社区ID
    private String communityName;//社区名

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getXingming() {
        return xingming;
    }

    public void setXingming(String xingming) {
        this.xingming = xingming;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getInstallCategory() {
        return installCategory;
    }

    public void setInstallCategory(String installCategory) {
        this.installCategory = installCategory;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isInsuranceEnable() {
        return insuranceEnable;
    }

    public void setInsuranceEnable(boolean insuranceEnable) {
        this.insuranceEnable = insuranceEnable;
    }

    public boolean isHaveElectrical() {
        return haveElectrical;
    }

    public void setHaveElectrical(boolean haveElectrical) {
        this.haveElectrical = haveElectrical;
    }

    public String getConstructionRange() {
        return constructionRange;
    }

    public void setConstructionRange(String constructionRange) {
        this.constructionRange = constructionRange;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public boolean isHaveSmartphone() {
        return haveSmartphone;
    }

    public void setHaveSmartphone(boolean haveSmartphone) {
        this.haveSmartphone = haveSmartphone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isConstructionEnable() {
        return constructionEnable;
    }

    public void setConstructionEnable(boolean constructionEnable) {
        this.constructionEnable = constructionEnable;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAppraisal() {
        return appraisal;
    }

    public void setAppraisal(String appraisal) {
        this.appraisal = appraisal;
    }

    public Date getAddingDate() {
        return addingDate;
    }

    public void setAddingDate(Date addingDate) {
        this.addingDate = addingDate;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBankCardNumber() {
        return bankCardNumber;
    }

    public void setBankCardNumber(String bankCardNumber) {
        this.bankCardNumber = bankCardNumber;
    }

    public boolean isNeverHire() {
        return neverHire;
    }

    public void setNeverHire(boolean neverHire) {
        this.neverHire = neverHire;
    }

    public String getCreateXingMing() {
        return createXingMing;
    }

    public void setCreateXingMing(String createXingMing) {
        this.createXingMing = createXingMing;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
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

    public String getWeixinId() {
        return weixinId;
    }

    public void setWeixinId(String weixinId) {
        this.weixinId = weixinId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public boolean isShowData() {
        return showData;
    }

    public void setShowData(boolean showData) {
        this.showData = showData;
    }

    public String getConstructionCount() {
        return constructionCount;
    }

    public void setConstructionCount(String constructionCount) {
        this.constructionCount = constructionCount;
    }

    public String getBelongedId() {
        return belongedId;
    }

    public void setBelongedId(String belongedId) {
        this.belongedId = belongedId;
    }

    public String getCreateEmployeeId() {
        return createEmployeeId;
    }

    public void setCreateEmployeeId(String createEmployeeId) {
        this.createEmployeeId = createEmployeeId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getWeixin() { return weixin; }

    public void setWeixin(String weixin) { this.weixin = weixin; }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}
