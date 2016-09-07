package com.lanchui.commonBiz.manager;

import com.xiwa.base.manager.ManagerException;

/**
 * 极光推送manager
 * Created by bingcheng on 2015/10/9.
 */
public interface JPushMessageManager {

    /**
     * 把内容推送到用户手机上
     *
     * @param phone
     * @param content
     */
    public void sendMessageByJPush(String registrationId, String content) throws ManagerException;


}
