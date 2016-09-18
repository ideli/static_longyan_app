package com.chinaredstar.longyan.bean.bo;

import java.util.List;

/**
 * Created by usagizhang on 16-4-25.
 */
public class ProvinceObject {
    private String provinceName;
    private String provinceCode;
    private List<CityObject> cityList;

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public List<CityObject> getCityList() {
        return cityList;
    }

    public void setCityList(List<CityObject> cityList) {
        this.cityList = cityList;
    }
}
