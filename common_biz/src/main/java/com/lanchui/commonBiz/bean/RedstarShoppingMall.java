package com.lanchui.commonBiz.bean;

import com.xiwa.base.bean.Identified;

import java.util.Date;

/**
 * Created by lenovo on 2016/4/26.
 */
public class RedstarShoppingMall implements Identified {

    private int id;

    private  Integer radiationRange;//    辐射范围

    private  String name;//   名称

    private  String province;//   省

    private  String type;//   类型

    private  String city;//   市

    private  String area;//   区

    private  String provinceCode;//    省代码

    private  String cityCode;//   市代码

    private  String areaCode;//    区代码

    private  String address;//    地址

    private  double lng;

    private  double lat;

    private Date openingDate;   //开业时间

    private  double constructionArea;   //建筑面积

    private  double rentArea;   //出租面积

    private  String businessHours;  //营业时间；上班时间

    //private  int departmentId;
    private  String contactXingming;    //联系人

    private  String remark; //备注

    private  String contactPhone;   //联系电话

    private  String contactMail;    //联系邮箱

    private  String businessCategory;   //业务类型

    private int inputMemberAmountRank;  //录入会员数量排名

    private int inputCommunityAmountRank;   //录入小区数量排名

    private int  inputMemberAmount; //录入会员数量

    private int  inputCommunityAmount;  //录入小区数量

    private int inputCommunityRoomAmount;   //录入小区住户

    private int organizationId; //组织ID

    private String mallCode;    // 红星内部系统商场code

    private  Integer employeeCount; //员工数量

    private String mallType;    //商场类型



    @Override
    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public Integer getRadiationRange() {
        return radiationRange;
    }

    public void setRadiationRange(Integer radiationRange) {
        this.radiationRange = radiationRange;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public Date getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
    }

    public double getConstructionArea() {
        return constructionArea;
    }

    public void setConstructionArea(double constructionArea) {
        this.constructionArea = constructionArea;
    }

    public double getRentArea() {
        return rentArea;
    }

    public void setRentArea(double rentArea) {
        this.rentArea = rentArea;
    }

    public String getBusinessHours() {
        return businessHours;
    }

    public void setBusinessHours(String businessHours) {
        this.businessHours = businessHours;
    }

   /* public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }*/

    public String getContactXingming() {
        return contactXingming;
    }

    public void setContactXingming(String contactXingming) {
        this.contactXingming = contactXingming;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactMail() {
        return contactMail;
    }

    public void setContactMail(String contactMail) {
        this.contactMail = contactMail;
    }

    public String getBusinessCategory() {
        return businessCategory;
    }

    public void setBusinessCategory(String businessCategory) {
        this.businessCategory = businessCategory;
    }

    public Integer getInputCommunityRoomAmount() {
        return inputCommunityRoomAmount;
    }

    public void setInputCommunityRoomAmount(Integer inputCommunityRoomAmount) {
        this.inputCommunityRoomAmount = inputCommunityRoomAmount;
    }

    public Integer getInputMemberAmountRank() {
        return inputMemberAmountRank;
    }

    public void setInputMemberAmountRank(Integer inputMemberAmountRank) {
        this.inputMemberAmountRank = inputMemberAmountRank;
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

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public Integer getInputCommunityAmountRank() {
        return inputCommunityAmountRank;
    }

    public void setInputCommunityAmountRank(Integer inputCommunityAmountRank) {
        this.inputCommunityAmountRank = inputCommunityAmountRank;
    }

    public String getMallCode() {
        return mallCode;
    }

    public void setMallCode(String mallCode) {
        this.mallCode = mallCode;
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
        this.mallType = mallType;
    }
}
