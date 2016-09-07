package com.chinaredstar.longyan.model;


import java.io.Serializable;
import java.util.Date;


public class RedstarVerifyCode implements Serializable {
    private static final long serialVersionUID = 2207737150239183077L;

    private Integer id;

    private Date createDate;

    private String phone;

    private String code;

    private boolean expired;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public boolean getExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }
}