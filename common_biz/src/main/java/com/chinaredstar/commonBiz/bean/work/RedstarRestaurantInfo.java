package com.chinaredstar.commonBiz.bean.work;

import com.xiwa.base.bean.Identified;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * Created by lenovo on 2016/7/11.
 */
public class RedstarRestaurantInfo implements Identified {

    private int id;

    private Date createDate;

    private String name;

    private String remark;

    private String checkinTime;

    private String checkoutTime;

    private String openDinnerTime;

    private String closeDinnerTime;

//    public static void main(String[] args) {
//        DateTime now  = DateTime.now();
//        System.out.println(now.getYear());
//        System.out.println(now.getMonthOfYear());
//        System.out.println(now.getDayOfMonth());
//    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getOpenDinnerTime() {
        return openDinnerTime;
    }

    public void setOpenDinnerTime(String openDinnerTime) {
        this.openDinnerTime = openDinnerTime;
    }

    public String getCloseDinnerTime() {
        return closeDinnerTime;
    }

    public void setCloseDinnerTime(String closeDinnerTime) {
        this.closeDinnerTime = closeDinnerTime;
    }
}
