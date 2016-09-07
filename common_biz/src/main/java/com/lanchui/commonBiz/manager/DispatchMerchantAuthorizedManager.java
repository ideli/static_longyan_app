package com.lanchui.commonBiz.manager;

import com.lanchui.commonBiz.bean.DispatchMerchantAuthorized;
import com.xiwa.base.manager.BasicManager;
import com.xiwa.base.manager.ManagerException;

/**
 * Created by wangj on 2015/5/4.
 */
public interface DispatchMerchantAuthorizedManager extends BasicManager {

    /**
     * 根据登录名和密码找出商铺登陆用户对象
     *
     * @param account
     * @param password
     * @return
     */
    public DispatchMerchantAuthorized getDispatchMerchantAuthorized(String account, String password) throws ManagerException;
}
