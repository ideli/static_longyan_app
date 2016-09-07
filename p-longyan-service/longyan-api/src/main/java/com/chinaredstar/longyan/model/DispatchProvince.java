package com.chinaredstar.longyan.model;


import java.io.Serializable;


public class DispatchProvince implements Serializable {

    private static final long serialVersionUID = 1414668869738078250L;

    private Integer id;

    private String province;

    private String provinceCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode == null ? null : provinceCode.trim();
    }
}