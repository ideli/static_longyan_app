package com.chinaredstar.longyan.api;

import com.chinaredstar.longyan.api.vo.RedstarReviewForm;
import com.chinaredstar.longyan.api.vo.Response;

/**
 * Created by mdc on 2016/7/28.
 */
public interface ReviewService {

    // 获取点评标签列表
    Response labelList(String objectType);

    // 获取点评结果描述列表
    Response getResultDesc();

    //添加点评
    Response addReview(RedstarReviewForm redstarReview, String detail);

    //点评删除
    Response removeReview(String reviewId, String userId);

    //获取点评对象的点评列表
    Response getReviewList(String objectId, String objectType, String userId, int page, int rows);

    //根据userId查询点评列表（分页
    Response myList(String userId, int page, int rows);

    //回复点评
    Response replyReview(String reviewId, String replyId, String replyXingMing, String content); //TODO

    //点评点赞OR取消点赞
    Response reviewLiked(String objectId, String ObjectType, String userId, String userObject, boolean isLiked);

    Response getLikedList(String objectId, String objectType, String userId);

    //获取点评对象的点评数量
    Response getReviewListNum(String objectId, String objectType);

    //添加投诉
    Response addSuggestion(String objectId, String objectType, String userId, String content, String userObject, int type);


    //投诉列表/我的投诉列表
    Response complainList(String objectId, String objectType, String userId);

    //分享链接记录-M
    Response shardLink(String linkUrl, String userId, String userObject);

    //收藏OR取消收藏
    Response favorite(String objectId, String objectType, String userId, boolean isFavorite);

    //我的收藏列表
    Response favoriteList(String userId, String objectType, String objectId);
}
