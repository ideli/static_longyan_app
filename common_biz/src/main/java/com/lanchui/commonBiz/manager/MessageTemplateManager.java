package com.lanchui.commonBiz.manager;


import com.lanchui.commonBiz.bean.MessageTemplate;
import com.xiwa.base.manager.BasicManager;
import com.xiwa.base.manager.ManagerException;

/**
 * Created by bingcheng on 2015/3/23.
 */
public interface MessageTemplateManager extends BasicManager {

    //这个manager继承BasicManager,然后要有下面几个方法
    //根据alias获取MessageTemplate对象
    public MessageTemplate getMessageTemplateByAlias(String alias) throws ManagerException;

}
