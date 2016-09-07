package com.lanchui.commonBiz.manager.ext;


import com.lanchui.commonBiz.bean.MessageSMSQueue;
import com.lanchui.commonBiz.manager.MessageSMSQueueManager;
import com.xiwa.base.manager.AbstractBasicManager;

/**
 * Created by bingcheng on 2015/8/18.
 */
public class SimpleMessageSMSQueueManager extends AbstractBasicManager implements MessageSMSQueueManager {

    public SimpleMessageSMSQueueManager() {
        super(MessageSMSQueue.class);
    }
}
