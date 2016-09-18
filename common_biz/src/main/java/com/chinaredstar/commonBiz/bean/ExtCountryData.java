package com.chinaredstar.commonBiz.bean;

/**
 * Created by niu on 2016/5/16.
 */
public class ExtCountryData {

    private int id;

    private  Integer newUserCount;

    private  Integer activeUserCount;

    private  Integer communityInputAmount;

    private  Integer memberInputAmount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getNewUserCount() {
        return newUserCount;
    }

    public void setNewUserCount(Integer newUserCount) {
        this.newUserCount = newUserCount;
    }

    public Integer getActiveUserCount() {
        return activeUserCount;
    }

    public void setActiveUserCount(Integer activeUserCount) {
        this.activeUserCount = activeUserCount;
    }

    public Integer getCommunityInputAmount() {
        return communityInputAmount;
    }

    public void setCommunityInputAmount(Integer communityInputAmount) {
        this.communityInputAmount = communityInputAmount;
    }

    public Integer getMemberInputAmount() {
        return memberInputAmount;
    }

    public void setMemberInputAmount(Integer memberInputAmount) {
        this.memberInputAmount = memberInputAmount;
    }
}
