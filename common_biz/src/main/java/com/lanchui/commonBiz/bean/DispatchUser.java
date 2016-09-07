package com.lanchui.commonBiz.bean;

import com.xiwa.base.bean.Identified;

import java.util.Date;

/**
 * Created by wangj on 2015/4/8.
 */
public class DispatchUser implements Identified {
    private int id;
    private String xingMing;//姓名
    private String phone;//手机号码
    private String weixin;//微信号
    private Date createDate;//创建日期
    private String sex;//性别
    private String nickName;//昵称
    private String openId;//用户标识

    private String province;//省
    private String city;//城市
    private String country;//国家
    private String headimgUrl;//头像图片url
    private String privilege;//特权
    private String unionId;//unionId

    private String password;//密码
    private boolean showData;//是否显示
    private int roomId;
    private int merchantId;
    private String merchantName;
    private int createMerchantId;
    private String createMerchantName;
    private int communityId;
    private String communityName;
    private int createCommunityId;
    private String createCommunityName;
    private String ownerIdentity;
    private String idCardNumber;
    private String idCardAddress;

    private Double lanchuiBalance;//蓝锤用户余额
    private Double propertyBalance;//物业费余额
    private Double propertyUnpaid;//物业费未交金额
    private Double landouBalance;//蓝豆

    private String job;//职业
    private String interest;//兴趣
    private String checkinTime;//入住年限
    private String userRemark;//个性签名

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getXingMing() {
        return xingMing;
    }

    public void setXingMing(String xingMing) {
        this.xingMing = xingMing;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgUrl() {
        return headimgUrl;
    }

    public void setHeadimgUrl(String headimgUrl) {
        this.headimgUrl = headimgUrl;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isShowData() { return showData; }

    public void setShowData(boolean showData) { this.showData = showData; }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
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

    public int getCreateMerchantId() {
        return createMerchantId;
    }

    public void setCreateMerchantId(int createMerchantId) {
        this.createMerchantId = createMerchantId;
    }

    public String getCreateMerchantName() {
        return createMerchantName;
    }

    public void setCreateMerchantName(String createMerchantName) {
        this.createMerchantName = createMerchantName;
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

    public int getCreateCommunityId() {
        return createCommunityId;
    }

    public void setCreateCommunityId(int createCommunityId) {
        this.createCommunityId = createCommunityId;
    }

    public String getCreateCommunityName() {
        return createCommunityName;
    }

    public void setCreateCommunityName(String createCommunityName) {
        this.createCommunityName = createCommunityName;
    }

    public String getOwnerIdentity() {
        return ownerIdentity;
    }

    public void setOwnerIdentity(String ownerIdentity) {
        this.ownerIdentity = ownerIdentity;
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

    public Double getLanchuiBalance() {
        return lanchuiBalance;
    }

    public void setLanchuiBalance(Double lanchuiBalance) {
        this.lanchuiBalance = lanchuiBalance;
    }

    public Double getPropertyBalance() {
        return propertyBalance;
    }

    public void setPropertyBalance(Double propertyBalance) {
        this.propertyBalance = propertyBalance;
    }

    public Double getPropertyUnpaid() {
        return propertyUnpaid;
    }

    public void setPropertyUnpaid(Double propertyUnpaid) {
        this.propertyUnpaid = propertyUnpaid;
    }

    public Double getLandouBalance() {
        return landouBalance;
    }

    public void setLandouBalance(Double landouBalance) {
        this.landouBalance = landouBalance;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getCheckinTime() {
        return checkinTime;
    }

    public void setCheckinTime(String checkinTime) {
        this.checkinTime = checkinTime;
    }

    public String getUserRemark() {
        return userRemark;
    }

    public void setUserRemark(String userRemark) {
        this.userRemark = userRemark;
    }
}