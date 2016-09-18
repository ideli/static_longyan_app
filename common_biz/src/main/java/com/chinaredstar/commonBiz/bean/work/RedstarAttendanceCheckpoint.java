package com.chinaredstar.commonBiz.bean.work;

import com.xiwa.base.bean.Identified;

import java.util.Date;

/**
 * Created by lenovo on 2016/7/8.
 */
public class RedstarAttendanceCheckpoint implements Identified {

    /*
    *   `id` int(11) NOT NULL AUTO_INCREMENT,
  `createDate` datetime DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL COMMENT '考勤点名称',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `latitude` double(11,6) DEFAULT NULL COMMENT '考勤点维度',
  `longitude` double(11,6) DEFAULT NULL COMMENT '考勤点经度',
  `distance` double(11,2) DEFAULT NULL COMMENT '距离,单位:千米',
  `checkinTime` varchar(64) DEFAULT NULL COMMENT '上班时间',
  `checkoutTime` varchar(64) DEFAULT NULL COMMENT '下班时间',*/

    private int id;

    private String remark;
    private String name;

    private String checkinTime;
    private String checkoutTime;
    private Date createDate;

    private Double latitude;
    private Double longitude;
    private Double distance;

    private String today;


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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getCheckinTime() {
        return checkinTime;
    }

    public void setCheckinTime(String checkinTime) {
        this.checkinTime = checkinTime;
    }

    public String getCheckoutTime() {
        return checkoutTime;
    }

    public void setCheckoutTime(String checkoutTime) {
        this.checkoutTime = checkoutTime;
    }

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }
}
