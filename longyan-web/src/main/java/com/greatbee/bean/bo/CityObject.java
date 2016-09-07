package com.greatbee.bean.bo;

import java.util.List;

/**
 * Created by usagizhang on 16-4-25.
 */
public class CityObject {
    private String cityName;
    private String cityCode;
    private List<AreaObject> areaList;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public List<AreaObject> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<AreaObject> areaList) {
        this.areaList = areaList;
    }
}
