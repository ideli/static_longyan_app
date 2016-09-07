package com.chinaredstar.longyan.model;


import java.io.Serializable;
import java.util.Date;


public class RedstarAttendanceRecord implements Serializable {

    private static final long serialVersionUID = -8936417417716766607L;

    private Integer id;

    private Date createDate;

    private Integer employeeId;

    private String employeeXingMing;

    private Integer year;

    private Integer month;

    private Integer day;

    private Date checkinTime;

    private Date checkoutTime;

    private String userRemark;

    private String userRemarkType;

    private String hrRemark;

    private String status;

    private String province;

    private String city;

    private String area;

    private String address;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeXingMing() {
        return employeeXingMing;
    }

    public void setEmployeeXingMing(String employeeXingMing) {
        this.employeeXingMing = employeeXingMing == null ? null : employeeXingMing.trim();
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Date getCheckinTime() {
        return checkinTime;
    }

    public void setCheckinTime(Date checkinTime) {
        this.checkinTime = checkinTime;
    }

    public Date getCheckoutTime() {
        return checkoutTime;
    }

    public void setCheckoutTime(Date checkoutTime) {
        this.checkoutTime = checkoutTime;
    }

    public String getUserRemark() {
        return userRemark;
    }

    public void setUserRemark(String userRemark) {
        this.userRemark = userRemark == null ? null : userRemark.trim();
    }

    public String getUserRemarkType() {
        return userRemarkType;
    }

    public void setUserRemarkType(String userRemarkType) {
        this.userRemarkType = userRemarkType == null ? null : userRemarkType.trim();
    }

    public String getHrRemark() {
        return hrRemark;
    }

    public void setHrRemark(String hrRemark) {
        this.hrRemark = hrRemark == null ? null : hrRemark.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }
}