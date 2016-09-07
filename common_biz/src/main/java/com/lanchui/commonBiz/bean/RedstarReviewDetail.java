package com.lanchui.commonBiz.bean;

import com.xiwa.base.bean.Identified;

import java.util.Date;

/**
 * xiwa_redstar_review_detail
 * Created by lenovo on 2016/6/2.
 */
public class RedstarReviewDetail implements Identified {

    private int id;

    private Date createDate;//  创建时间

    private String labelCode;// 标签代码

    private int score;//    分数

    private int reviewId;//  点评Id

   private RedstarReview redstarReview;

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

    public String getLabelCode() {
        return labelCode;
    }

    public void setLabelCode(String labelCode) {
        this.labelCode = labelCode;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

  public RedstarReview getRedstarReview() {
        return redstarReview;
    }

    public void setRedstarReview(RedstarReview redstarReview) {
        this.redstarReview = redstarReview;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }
}
