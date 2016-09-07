package com.chinaredstar.longyan.api;

import com.chinaredstar.longyan.api.vo.Response;

/**
 * Created by mdc on 2016/7/28.
 */
public interface StoreService {
    //获取店铺信息
    Response getStoreAndReview(String qrCode, String userId, String dataSK,int page,int rows);

    Response favoriteStore(String storeId, boolean favorite, String userId);
}
