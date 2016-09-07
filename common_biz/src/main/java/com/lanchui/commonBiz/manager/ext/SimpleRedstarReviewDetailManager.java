package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.RedstarReviewDetail;
import com.lanchui.commonBiz.manager.RedstarReviewDetailManager;
import com.lanchui.commonBiz.manager.RedstarReviewManager;
import com.xiwa.base.manager.AbstractBasicManager;

/**
 * Created by lenovo on 2016/6/2.
 */
public class SimpleRedstarReviewDetailManager extends AbstractBasicManager implements RedstarReviewDetailManager {
    public SimpleRedstarReviewDetailManager() {
        super(RedstarReviewDetail.class);
    }
}
