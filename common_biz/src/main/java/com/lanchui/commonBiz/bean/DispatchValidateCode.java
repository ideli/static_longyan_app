package com.lanchui.commonBiz.bean;

import com.xiwa.base.bean.Identified;

import java.util.Date;

/**
 * Created by bingcheng on 2015/4/17.
 */
public class DispatchValidateCode implements Identified {

    private int id;

    private String phone;

    private String validateCode;

    private Date valideDate;

    private Date createDate;

    private boolean expired;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public Date getValideDate() {
        return valideDate;
    }

    public void setValideDate(Date valideDate) {
        this.valideDate = valideDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }
}
