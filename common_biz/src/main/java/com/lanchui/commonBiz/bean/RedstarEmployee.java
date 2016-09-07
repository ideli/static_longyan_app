package com.lanchui.commonBiz.bean;

import com.xiwa.base.bean.Identified;

import java.util.Date;

/**
 * Created by lenovo on 2016/4/22.
 */
public class RedstarEmployee implements Identified {

    private int id;

    private Integer departmentId;// 部门ID

    private String userName;// 用户名称

    private String gongHao;// 工号

    private String xingMing;// 姓名

    private String gender;// 性别

    private Date birthday;// 出生日期

    private String idNumber;// 身份证号码

    private String address;// 地址

    private String youBian;// 邮编

    private String addressPhone;//

    private String mobilePhone;// 移动电话

    private String email;// 邮箱

    private String description;// 描述

    private String remark;// 备注

    private String photos;// 照片

    private Date lastActiveTime;// 最后活动时间

    private Date entryDate;//入职时间

    private Date createDate;// 创建时间

    private String englishName;// 英文名

    private String tags;// 标签，车牌照

    private String fixedPhone;// 固话

    private String sign;// 指示牌

    private Integer inputMemberAmount;// 已录入会员数量

    private Integer inputCommunityAmount;// 已录入小区数量

    private Integer inputCommunityRoomAmount;// 已录入房间数量

    private String openId;// 数字身份识别

    private String bankCardNo;// 银行卡号

    private String source;// 数据来源

    private Boolean showData;// 显示日期

    //private Integer showData;
    private String employeeCode;//  红星系统的员工ID

    private Integer employeeRecord;// 雇员记录

    private String hrStatus;// 人事状态

    private String businessUnitCode;// 业务单位代码

    private String businessUnitName;// 业务单位名称

    private String departmentCode;//    部门代码

    private String role;// 角色

    private Integer belongedId;//

    private Integer inputMemberAmountRank;// 录入会员数量排名

    private Integer inputCommunityAmountRank;// 录入小区数量排名

    private String head;//

    private Integer score;// 分数

    public void setInputMemberAmount(Integer inputMemberAmount) {
        this.inputMemberAmount = inputMemberAmount;
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


    public Integer getBelongedId() {
        return belongedId;
    }

    public void setBelongedId(Integer belongedId) {
        this.belongedId = belongedId;
    }


    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

   /* public Boolean getShowData() {
        return showData;
    }

    public void setShowData(Boolean showData) {
        this.showData = showData;
    }*/




    @Override
    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGongHao() {
        return gongHao;
    }

    public void setGongHao(String gongHao) {
        this.gongHao = gongHao;
    }

    public String getXingMing() {
        return xingMing;
    }

    public void setXingMing(String xingMing) {
        this.xingMing = xingMing;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getYouBian() {
        return youBian;
    }

    public void setYouBian(String youBian) {
        this.youBian = youBian;
    }

    public String getAddressPhone() {
        return addressPhone;
    }

    public void setAddressPhone(String addressPhone) {
        this.addressPhone = addressPhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }


    public Date getLastActiveTime() {
        return lastActiveTime;
    }

    public void setLastActiveTime(Date lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }


    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }


    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getFixedPhone() {
        return fixedPhone;
    }

    public void setFixedPhone(String fixedPhone) {
        this.fixedPhone = fixedPhone;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Integer getInputCommunityAmount() {
        return inputCommunityAmount;
    }

    public Integer getInputMemberAmount() {
        return inputMemberAmount;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }


    public String getHrStatus() {
        return hrStatus;
    }

    public void setHrStatus(String hrStatus) {
        this.hrStatus = hrStatus;
    }

    public String getBusinessUnitCode() {
        return businessUnitCode;
    }

    public void setBusinessUnitCode(String businessUnitCode) {
        this.businessUnitCode = businessUnitCode;
    }

    public String getBusinessUnitName() {
        return businessUnitName;
    }

    public void setBusinessUnitName(String businessUnitName) {
        this.businessUnitName = businessUnitName;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Integer getEmployeeRecord() {
        return employeeRecord;
    }

    public void setEmployeeRecord(Integer employeeRecord) {
        this.employeeRecord = employeeRecord;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getInputCommunityRoomAmount() {
        return inputCommunityRoomAmount;
    }

    public void setInputCommunityRoomAmount(Integer inputCommunityRoomAmount) {
        this.inputCommunityRoomAmount = inputCommunityRoomAmount;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Boolean getShowData() {
        return showData;
    }

    public void setShowData(Boolean showData) {
        this.showData = showData;
    }
}
