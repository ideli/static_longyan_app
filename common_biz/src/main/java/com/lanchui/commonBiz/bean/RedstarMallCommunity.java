package com.lanchui.commonBiz.bean;

import com.xiwa.base.bean.Identified;

/**
 * Created by lenovo on 2016/4/26.
 */
public class RedstarMallCommunity implements Identified {

    private int id;

    private  int shoppingMallId;//  商场ID

    private  String shoppingMallName;//  商场名称

    private  int communityId;//    小区ID

    private  String communityName;//  小区名称

    private  double distance;//     小区和商场的距离

    private  int memberAmount;//  会员数量

    private  int memberInputAmount;//  会员录入数量

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShoppingMallId() {
        return shoppingMallId;
    }

    public void setShoppingMallId(int shoppingMallId) {
        this.shoppingMallId = shoppingMallId;
    }

    public String getShoppingMallName() {
        return shoppingMallName;
    }

    public void setShoppingMallName(String shoppingMallName) {
        this.shoppingMallName = shoppingMallName;
    }

    public int getCommunityId() {
        return communityId;
    }

    public void setCommunityId(int communityId) {
        this.communityId = communityId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getMemberAmount() {
        return memberAmount;
    }

    public void setMemberAmount(int memberAmount) {
        this.memberAmount = memberAmount;
    }

    public int getMemberInputAmount() {
        return memberInputAmount;
    }

    public void setMemberInputAmount(int memberInputAmount) {
        this.memberInputAmount = memberInputAmount;
    }
}
