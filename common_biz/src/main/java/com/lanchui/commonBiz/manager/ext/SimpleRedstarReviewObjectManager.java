package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.RedstarReviewObject;
import com.lanchui.commonBiz.manager.RedstarReviewObjectManager;
import com.xiwa.base.manager.AbstractBasicManager;

/**
 * Created by lenovo on 2016/6/2.
 */
public class SimpleRedstarReviewObjectManager extends AbstractBasicManager implements RedstarReviewObjectManager {
    public SimpleRedstarReviewObjectManager() {
        super(RedstarReviewObject.class);
    }
}
