package com.lanchui.commonBiz.bean;

import com.xiwa.base.bean.Identified;
import com.xiwa.zeus.trinity.bean.ShopUser;

/**
 * Created by usagizhang on 15-12-7.
 */
public class DispatchShopAuthorized extends ShopUser implements Identified {
    private String roles;
    private boolean haveSetPassword;
    private boolean active;

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public boolean isHaveSetPassword() {
        return haveSetPassword;
    }

    public void setHaveSetPassword(boolean haveSetPassword) {
        this.haveSetPassword = haveSetPassword;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
