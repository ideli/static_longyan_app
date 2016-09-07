package com.lanchui.commonBiz.bean;

import com.xiwa.base.bean.Identified;

import java.util.Date;

/**
 * Created by bingcheng on 2015/10/9.
 */
public class DispatchPushDevice implements Identified {

    private int id;
    private String registrationId;//-- 极光推送唯一注册标识
    private String platform;//-- 描述哪个平台注册的设备，IOS,Android
    private String source;//-- 描述是哪个app注册的设备。UserApp,WorkerApp,MerchantApp,CommunityApp......
    private Date createDate;
    private int loginUserId;//-- 描述当前登录用户的id

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getLoginUserId() {
        return loginUserId;
    }

    public void setLoginUserId(int loginUserId) {
        this.loginUserId = loginUserId;
    }
}
