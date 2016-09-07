package com.chinaredstar.longyan.api.vo;

import java.io.Serializable;

/**
 * Created by leiyun 2016/7/29.
 */
public class Community implements Serializable {
    private static final long serialVersionUID = 3789876946416627155L;

    private String provinceCode;

    private String cityCode;

    private String areaCode;

    private String communityName;

    private Integer page = 1;

    private Integer pageSzie = 20;

    private String orderBy = "id";

    private boolean isAsc = false;



    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSzie() {
        return pageSzie;
    }

    public void setPageSzie(Integer pageSzie) {
        this.pageSzie = pageSzie;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public boolean isAsc() {
        return isAsc;
    }

    public void setIsAsc(boolean isAsc) {
        this.isAsc = isAsc;
    }
}
