package com.chinaredstar.commonBiz.bean;

import com.xiwa.base.bean.Identified;

/**
 * Created by wangj on 2015/4/15.
 */
public class DispatchCity implements Identified
{
    private int id;

    private  String province;// 省

    private  String provinceCode;// 省代码

    private  String city;// 城市

    private  String cityCode;// 城市代码

    private  boolean hot;// 是否热门

    private  boolean open;//    是否开通


    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public boolean isHot() {
        return hot;
    }

    public void setHot(boolean hot) {
        this.hot = hot;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}