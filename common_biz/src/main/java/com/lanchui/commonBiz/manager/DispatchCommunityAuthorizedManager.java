package com.lanchui.commonBiz.manager;

import com.lanchui.commonBiz.bean.DispatchCommunityAuthorized;
import com.xiwa.base.manager.BasicManager;
import com.xiwa.base.manager.ManagerException;

/**
 * Created by wangj on 2015/6/3.
 */
public interface DispatchCommunityAuthorizedManager extends BasicManager {

    /**
     * 根据登录名和密码找出社区登陆用户对象
     *
     * @param account
     * @param password
     * @return
     */
    public DispatchCommunityAuthorized getDispatchCommunityAuthorized(String account, String password) throws ManagerException;
}
