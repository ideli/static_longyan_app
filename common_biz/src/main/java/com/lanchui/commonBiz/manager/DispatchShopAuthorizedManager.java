package com.lanchui.commonBiz.manager;

import com.lanchui.commonBiz.bean.DispatchShopAuthorized;
import com.xiwa.base.manager.BasicManager;
import com.xiwa.base.manager.ManagerException;

/**
 * Created by usagizhang on 15-12-7.
 */
public interface DispatchShopAuthorizedManager extends BasicManager {

    /**
     * 获取店铺授权
     *
     * @param account
     * @param password
     * @return
     * @throws com.xiwa.base.manager.ManagerException
     */
    public DispatchShopAuthorized getShopAuthorized(String account, String password) throws ManagerException;
}
