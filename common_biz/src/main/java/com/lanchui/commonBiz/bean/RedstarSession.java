package com.lanchui.commonBiz.bean;

import com.xiwa.base.bean.Identified;

import java.util.Date;

/**
 * Created by usagizhang on 16-4-19.
 */
public class RedstarSession implements Identified {

    private int id;

    private boolean activated; //   是否激活

    private String source; //   数据来源

    private Date expiredDate; //    失效日期

    private Date createDate; //   创建日期

    private int objectId; //

    private String loginTarget; //   登录目标

    private String token; //   令牌，标记，记号

    private String openId; //

    private String userToken; //   用户令牌

    private String userRefreshToken; //   用户刷新令牌

    private Date userTokenExpiredDate; //   用户令牌失效日期


    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public String getLoginTarget() {
        return loginTarget;
    }

    public void setLoginTarget(String loginTarget) {
        this.loginTarget = loginTarget;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getUserRefreshToken() {
        return userRefreshToken;
    }

    public void setUserRefreshToken(String userRefreshToken) {
        this.userRefreshToken = userRefreshToken;
    }

    public Date getUserTokenExpiredDate() {
        return userTokenExpiredDate;
    }

    public void setUserTokenExpiredDate(Date userTokenExpiredDate) {
        this.userTokenExpiredDate = userTokenExpiredDate;
    }
}
