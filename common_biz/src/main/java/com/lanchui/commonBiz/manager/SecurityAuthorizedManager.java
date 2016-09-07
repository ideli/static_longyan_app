package com.lanchui.commonBiz.manager;

import com.xiwa.base.manager.BasicManager;

/**
 * Created by mdc on 2016/7/13.
 */
public interface SecurityAuthorizedManager extends BasicManager {

    int getAuthorizeCount(String roles,String roleName);

}
