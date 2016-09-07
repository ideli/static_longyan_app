package com.chinaredstar.longyan.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "xiwa_crm_employee")
public class RedstarEmployee implements Serializable {

    private static final long serialVersionUID = -4908500539733978916L;
    @Id
    @GeneratedValue(generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(columnDefinition = "BIGINT")
    private Integer id;

    private Integer departmentId;

    private Integer enterpriseUserId;

    private String userName;

    private String gongHao;

    private String xingMing;

    private String gender;

    private Date birthday;

    private String idNumber;

    private String address;

    private String youBian;

    private String addressPhone;

    private String mobilePhone;

    private String email;

    private Date entryDate;

    private Date createDate;

    private Date updateDate;

    private Integer belongedId;

    private String serialNumber;

    @Column(columnDefinition = "TINYINT(4)")
    private boolean customerManager;

    private Date lastActiveTime;

    @Column(columnDefinition = "TINYINT(4)")
    private boolean financeLeader;

    private String urgentPersonRelation;

    private String urgentPhone;

    private String urgentPerson;

    private String idPhotoCopy;

    private String employeeStatusField;

    private String photos;

    private Date probationaryEndDate;

    private Date probationaryStartDate;

    private Date reinstatedDate;

    private Date leavingDate;

    private Integer institutionId;

    private Integer workingTeamId;

    private Integer jobTitleId;

    private Integer jobLevelId;

    @Column(columnDefinition = "TINYINT(4)")
    private boolean showData;

    private String skin;

    private String head;

    private String englishName;

    private String fixedPhone;

    private String lang;

    private Integer integral;

    private String nickname;

    private String zoneCode;

    @Column(columnDefinition = "TINYINT(4)")
    private boolean sale;

    private String zipCode;

    private String backupEmployeeSerialNumber;

    private String projects;

    private String skills;

    private String deviceType;

    private String deviceToken;

    private String bankCardNo;

    private String openId;

    private Integer inputMemberAmount;

    private Integer inputCommunityAmount;

    private String source;

    private Integer inputMemberAmountRank;

    private Integer inputCommunityAmountRank;

    private String employeeCode;

    private Integer employeeRecord;

    private String hrStatus;

    private String businessUnitCode;

    private String businessUnitName;

    private String departmentCode;

    private Integer inputCommunityRoomAmount;

    private Double score;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getEnterpriseUserId() {
        return enterpriseUserId;
    }

    public void setEnterpriseUserId(Integer enterpriseUserId) {
        this.enterpriseUserId = enterpriseUserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getGongHao() {
        return gongHao;
    }

    public void setGongHao(String gongHao) {
        this.gongHao = gongHao == null ? null : gongHao.trim();
    }

    public String getXingMing() {
        return xingMing;
    }

    public void setXingMing(String xingMing) {
        this.xingMing = xingMing == null ? null : xingMing.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
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
        this.idNumber = idNumber == null ? null : idNumber.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getYouBian() {
        return youBian;
    }

    public void setYouBian(String youBian) {
        this.youBian = youBian == null ? null : youBian.trim();
    }

    public String getAddressPhone() {
        return addressPhone;
    }

    public void setAddressPhone(String addressPhone) {
        this.addressPhone = addressPhone == null ? null : addressPhone.trim();
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone == null ? null : mobilePhone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
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

    public Integer getBelongedId() {
        return belongedId;
    }

    public void setBelongedId(Integer belongedId) {
        this.belongedId = belongedId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber == null ? null : serialNumber.trim();
    }

    public boolean getCustomerManager() {
        return customerManager;
    }

    public void setCustomerManager(boolean customerManager) {
        this.customerManager = customerManager;
    }

    public Date getLastActiveTime() {
        return lastActiveTime;
    }

    public void setLastActiveTime(Date lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }

    public boolean getFinanceLeader() {
        return financeLeader;
    }

    public void setFinanceLeader(boolean financeLeader) {
        this.financeLeader = financeLeader;
    }

    public String getUrgentPersonRelation() {
        return urgentPersonRelation;
    }

    public void setUrgentPersonRelation(String urgentPersonRelation) {
        this.urgentPersonRelation = urgentPersonRelation == null ? null : urgentPersonRelation.trim();
    }

    public String getUrgentPhone() {
        return urgentPhone;
    }

    public void setUrgentPhone(String urgentPhone) {
        this.urgentPhone = urgentPhone == null ? null : urgentPhone.trim();
    }

    public String getUrgentPerson() {
        return urgentPerson;
    }

    public void setUrgentPerson(String urgentPerson) {
        this.urgentPerson = urgentPerson == null ? null : urgentPerson.trim();
    }

    public String getIdPhotoCopy() {
        return idPhotoCopy;
    }

    public void setIdPhotoCopy(String idPhotoCopy) {
        this.idPhotoCopy = idPhotoCopy == null ? null : idPhotoCopy.trim();
    }

    public String getEmployeeStatusField() {
        return employeeStatusField;
    }

    public void setEmployeeStatusField(String employeeStatusField) {
        this.employeeStatusField = employeeStatusField == null ? null : employeeStatusField.trim();
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos == null ? null : photos.trim();
    }

    public Date getProbationaryEndDate() {
        return probationaryEndDate;
    }

    public void setProbationaryEndDate(Date probationaryEndDate) {
        this.probationaryEndDate = probationaryEndDate;
    }

    public Date getProbationaryStartDate() {
        return probationaryStartDate;
    }

    public void setProbationaryStartDate(Date probationaryStartDate) {
        this.probationaryStartDate = probationaryStartDate;
    }

    public Date getReinstatedDate() {
        return reinstatedDate;
    }

    public void setReinstatedDate(Date reinstatedDate) {
        this.reinstatedDate = reinstatedDate;
    }

    public Date getLeavingDate() {
        return leavingDate;
    }

    public void setLeavingDate(Date leavingDate) {
        this.leavingDate = leavingDate;
    }

    public Integer getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(Integer institutionId) {
        this.institutionId = institutionId;
    }

    public Integer getWorkingTeamId() {
        return workingTeamId;
    }

    public void setWorkingTeamId(Integer workingTeamId) {
        this.workingTeamId = workingTeamId;
    }

    public Integer getJobTitleId() {
        return jobTitleId;
    }

    public void setJobTitleId(Integer jobTitleId) {
        this.jobTitleId = jobTitleId;
    }

    public Integer getJobLevelId() {
        return jobLevelId;
    }

    public void setJobLevelId(Integer jobLevelId) {
        this.jobLevelId = jobLevelId;
    }

    public boolean getShowData() {
        return showData;
    }

    public void setShowData(boolean showData) {
        this.showData = showData;
    }

    public String getSkin() {
        return skin;
    }

    public void setSkin(String skin) {
        this.skin = skin == null ? null : skin.trim();
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head == null ? null : head.trim();
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName == null ? null : englishName.trim();
    }

    public String getFixedPhone() {
        return fixedPhone;
    }

    public void setFixedPhone(String fixedPhone) {
        this.fixedPhone = fixedPhone == null ? null : fixedPhone.trim();
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang == null ? null : lang.trim();
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getZoneCode() {
        return zoneCode;
    }

    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode == null ? null : zoneCode.trim();
    }

    public boolean getSale() {
        return sale;
    }

    public void setSale(boolean sale) {
        this.sale = sale;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode == null ? null : zipCode.trim();
    }

    public String getBackupEmployeeSerialNumber() {
        return backupEmployeeSerialNumber;
    }

    public void setBackupEmployeeSerialNumber(String backupEmployeeSerialNumber) {
        this.backupEmployeeSerialNumber = backupEmployeeSerialNumber == null ? null : backupEmployeeSerialNumber.trim();
    }

    public String getProjects() {
        return projects;
    }

    public void setProjects(String projects) {
        this.projects = projects == null ? null : projects.trim();
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills == null ? null : skills.trim();
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType == null ? null : deviceType.trim();
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken == null ? null : deviceToken.trim();
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo == null ? null : bankCardNo.trim();
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
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

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode == null ? null : employeeCode.trim();
    }

    public Integer getEmployeeRecord() {
        return employeeRecord;
    }

    public void setEmployeeRecord(Integer employeeRecord) {
        this.employeeRecord = employeeRecord;
    }

    public String getHrStatus() {
        return hrStatus;
    }

    public void setHrStatus(String hrStatus) {
        this.hrStatus = hrStatus == null ? null : hrStatus.trim();
    }

    public String getBusinessUnitCode() {
        return businessUnitCode;
    }

    public void setBusinessUnitCode(String businessUnitCode) {
        this.businessUnitCode = businessUnitCode == null ? null : businessUnitCode.trim();
    }

    public String getBusinessUnitName() {
        return businessUnitName;
    }

    public void setBusinessUnitName(String businessUnitName) {
        this.businessUnitName = businessUnitName == null ? null : businessUnitName.trim();
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode == null ? null : departmentCode.trim();
    }

    public Integer getInputCommunityRoomAmount() {
        return inputCommunityRoomAmount;
    }

    public void setInputCommunityRoomAmount(Integer inputCommunityRoomAmount) {
        this.inputCommunityRoomAmount = inputCommunityRoomAmount;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}