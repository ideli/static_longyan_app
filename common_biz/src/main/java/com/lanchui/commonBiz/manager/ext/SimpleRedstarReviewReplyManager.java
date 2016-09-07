package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.RedstarReviewReply;
import com.lanchui.commonBiz.manager.RedstarReviewReplyManager;
import com.xiwa.base.manager.AbstractBasicManager;

/**
 * Created by lenovo on 2016/6/2.
 */
public class SimpleRedstarReviewReplyManager extends AbstractBasicManager implements RedstarReviewReplyManager {

    public SimpleRedstarReviewReplyManager() {
        super(RedstarReviewReply.class);
    }

}
