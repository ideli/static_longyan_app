package com.chinaredstar.commonBiz.manager;

import com.chinaredstar.commonBiz.bean.RedstarSession;
import com.xiwa.base.manager.BasicManager;
import com.xiwa.base.manager.ManagerException;

/**
 * Created by wangj on 2015/4/15.
 */
public interface RedstarSessionManager extends BasicManager {

    public void createSession(RedstarSession session) throws ManagerException;

    public RedstarSession getActivatedSession(String token) throws ManagerException;
/*    public RedstarSession getActivatedSessio(String openId) throws ManagerException;*/
}
