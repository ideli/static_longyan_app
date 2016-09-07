package com.lanchui.commonBiz.bean.work;

import com.xiwa.base.bean.Identified;

import java.util.Date;

/**
 * Created by lenovo on 2016/7/7.
 */
public class RedstarAttendanceRecord implements Identified {

  public final static String ALL_TYPE = "all";
  public final static String CHECKIN_TYPE = "checkin";
  public final static String CHECKOUT_TYPE = "checkout";

  private int id;

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
    this.employeeXingMing = employeeXingMing;
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
    this.userRemark = userRemark;
  }

  public String getUserRemarkType() {
    return userRemarkType;
  }

  public void setUserRemarkType(String userRemarkType) {
    this.userRemarkType = userRemarkType;
  }

  public String getHrRemark() {
    return hrRemark;
  }

  public void setHrRemark(String hrRemark) {
    this.hrRemark = hrRemark;
  }


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
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

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}


