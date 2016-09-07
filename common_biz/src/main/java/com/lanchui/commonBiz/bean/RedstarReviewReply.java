package com.lanchui.commonBiz.bean;

import com.xiwa.base.bean.Identified;

import java.util.Date;

/**
 * Created by lenovo on 2016/5/31.
 */
public class RedstarReviewReply implements Identified {

    private int id;

    private Date createDate;

    private String replyId;//  回复人ID

    private String replyXingMing;//    回复人姓名

    private int reviewId;//    点评Id

    private String content;//  回复内容

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    public String getReplyXingMing() {
        return replyXingMing;
    }

    public void setReplyXingMing(String replyXingMing) {
        this.replyXingMing = replyXingMing;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
