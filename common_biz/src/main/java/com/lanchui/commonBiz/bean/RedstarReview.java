package com.lanchui.commonBiz.bean;

import com.xiwa.base.bean.Identified;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lenovo on 2016/5/31.
 */
public class RedstarReview implements Identified {

    private int id;

    private Date createDate;//  创建日期

    private String objectCode; //   点评对象代码

    private String objectId; // 点评对象ID

    private String objectName; //   点评对象名称

    private String comment; //  评论内容

    private double score; //    评分

    private String userObject; //   用户对象

    private String userId;//    用户ID

    private boolean showData;//   是否删除的状态（ture为正常）

    private String nickName;//    昵称

    private String picture;//     存储图片的路径（imgUrl）

    private boolean isLiked;//     不对应数据库表，只作为查询出的数据结果

    private int likedCount;//     点赞数量

    public int getLikedCount() {
        return likedCount;
    }

    public void setLikedCount(int likedCount) {
        this.likedCount = likedCount;
    }

    private List<RedstarReviewDetail> redstarReviewDetails;    //一对多

    private List<RedstarReviewReply> redstarReviewReplies;

    public List<RedstarReviewReply> getRedstarReviewReplies() {
        return redstarReviewReplies;
    }

    public void setRedstarReviewReplies(List<RedstarReviewReply> redstarReviewReplies) {
        this.redstarReviewReplies = redstarReviewReplies;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean isLiked) {
        this.isLiked = isLiked;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPicture() {

        return picture;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public boolean isShowData() {
        return showData;
    }

    public void setShowData(boolean showData) {
        this.showData = showData;
    }




    public List<RedstarReviewDetail> getRedstarReviewDetails() {
        return redstarReviewDetails;
    }

    public void setRedstarReviewDetails(List<RedstarReviewDetail> redstarReviewDetails) {
        this.redstarReviewDetails = redstarReviewDetails;
    }

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

    public String getObjectCode() {
        return objectCode;
    }

    public void setObjectCode(String objectCode) {
        this.objectCode = objectCode;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getUserObject() {
        return userObject;
    }

    public void setUserObject(String userObject) {
        this.userObject = userObject;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
