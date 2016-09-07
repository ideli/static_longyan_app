package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.DispatchUser;
import com.lanchui.commonBiz.manager.DispatchUserManager;
import com.xiwa.base.manager.AbstractBasicManager;

/**
 * Created by ASUS on 2015/4/8.
 */
public class SimpleDispatchUserManager extends AbstractBasicManager implements DispatchUserManager {

    public SimpleDispatchUserManager() {
        super(DispatchUser.class);
    }
}
