package com.chinaredstar.commonBiz.bean;

import com.xiwa.base.bean.Identified;

/**
 * Created by StevenDong on 2016/9/21
 */
public class RedstarCommunityUpdateLog extends RedstarCommunity implements Identified {

    private int reviewStatus; //审核状态

    public int getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(int reviewStatus) {
        this.reviewStatus = reviewStatus;
    }
}
