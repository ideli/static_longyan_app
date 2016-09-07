package com.lanchui.commonBiz.bean;

import com.xiwa.base.bean.Identified;

/**
 * Created by usagizhang on 16-7-14.
 */
public class RecommendCommunityObject implements Identified {

    private int id;

    private String name;//  姓名

    private String city;//  市

    private String province;//  省

    private String area;//  区

    private String provinceCode;//  省ID

    private String cityCode;//  市ID

    private String areaCode;//  区ID

    private String address;//  地址

    private Integer roomMount = 0;// 总户数

    private Integer alreadyInputAmount = 0; //  录入数量

    private Integer createEmployeeId = 0;// 创建员工ID

    private String createXingMing;//  创建人姓名

    private String longitude;// 经度

    private String latitude;// 维度

    private String distance;// 距离、间隔

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getRoomMount() {
        return roomMount;
    }

    public void setRoomMount(Integer roomMount) {
        this.roomMount = roomMount;
    }

    public Integer getAlreadyInputAmount() {
        return alreadyInputAmount;
    }

    public void setAlreadyInputAmount(Integer alreadyInputAmount) {
        this.alreadyInputAmount = alreadyInputAmount;
    }

    public Integer getCreateEmployeeId() {
        return createEmployeeId;
    }

    public void setCreateEmployeeId(Integer createEmployeeId) {
        this.createEmployeeId = createEmployeeId;
    }

    public String getCreateXingMing() {
        return createXingMing;
    }

    public void setCreateXingMing(String createXingMing) {
        this.createXingMing = createXingMing;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
