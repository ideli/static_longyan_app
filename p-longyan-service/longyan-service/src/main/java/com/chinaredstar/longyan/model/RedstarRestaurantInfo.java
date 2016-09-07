package com.chinaredstar.longyan.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
@Table(name = "xiwa_redstar_restaurant_info")
public class RedstarRestaurantInfo implements Serializable {
    private static final long serialVersionUID = 1946060366270763030L;
    @Id
    @GeneratedValue(generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(columnDefinition = "BIGINT")
    private Integer id;

    private Date createDate;

    private String name;

    private String remark;

    private String checkinTime;

    private String checkoutTime;

    private String openDinnerTime;

    private String closeDinnerTime;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getCheckinTime() {
        return checkinTime;
    }

    public void setCheckinTime(String checkinTime) {
        this.checkinTime = checkinTime == null ? null : checkinTime.trim();
    }

    public String getCheckoutTime() {
        return checkoutTime;
    }

    public void setCheckoutTime(String checkoutTime) {
        this.checkoutTime = checkoutTime == null ? null : checkoutTime.trim();
    }

    public String getOpenDinnerTime() {
        return openDinnerTime;
    }

    public void setOpenDinnerTime(String openDinnerTime) {
        this.openDinnerTime = openDinnerTime == null ? null : openDinnerTime.trim();
    }

    public String getCloseDinnerTime() {
        return closeDinnerTime;
    }

    public void setCloseDinnerTime(String closeDinnerTime) {
        this.closeDinnerTime = closeDinnerTime == null ? null : closeDinnerTime.trim();
    }
}