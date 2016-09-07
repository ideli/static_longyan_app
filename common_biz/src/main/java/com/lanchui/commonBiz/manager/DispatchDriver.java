package com.lanchui.commonBiz.manager;

/**
 * Created by usagizhang on 15-4-15.
 */
public interface DispatchDriver {

    public DispatchWorkerManager getDispatchWorkerManager();

    public DispatchCityManager getDispatchCityManager();

    public DispatchUserManager getDispatchUserManager();

    public DispatchProvinceManager getDispatchProvinceManager();

    public DispatchLocationManager getDispatchLocationManager();

    //根据登录名和密码找出商铺登陆用户对象
    public DispatchMerchantAuthorizedManager getDispatchMerchantAuthorizedManager();

    public DispatchMerchantManager getDispatchMerchantManager();

    public DispatchEmployeeManager getDispatchEmployeeManager();

    public RedstarCommunityManager getRedstarCommunityManager();

    public DispatchCommunityAuthorizedManager getDispatchCommunityAuthorizedManager();

    public DispatchLoginTokenManager getDispatchLoginTokenManager();

    public DispatchCommunityGardenManager getDispatchCommunityGardenManager();

    public DispatchCommunityBuildingManager getDispatchCommunityBuildingManager();

    public DispatchCommunityUnitManager getDispatchCommunityUnitManager();

    public DispatchCommunityRoomManager getDispatchCommunityRoomManager();

    public DispatchCommunityRoomStatusManager getDispatchCommunityRoomStatusManager();

    public DispatchUserRoomManager getDispatchUserRoomManager();

    public DispatchShopManager getDispatchShopManager();

    public DispatchShopCategoryManager getDispatchShopCategoryManager();

    public DispatchPushDeviceManager getDispatchPushDeviceManager();

    public DispatchShopAuthorizedManager getDispatchShopAuthorizedManager();

    public MessageSMSQueueManager getMessageSMSQueueManager();

    public RedstarSessionManager getRedstarSessionManager();

    public DispatchSecurityOperationLogManager getSecurityOperationLogManager();

    public RedstarMemberManager getRedStarMemberManager();

    public RedstarEmployeeManager getRedstarEmployeeManager();

    public RedstarDepartmentManager getRedstarDepartmentManager();

    public RedstarVerifyCodeManager getRedstarVerifyCodeManager();

    public RedstarEmployeeMonthManager getRedstarEmployeeMonthManager();

    public RedstarShoppingMallManager getRedstarShoppingMallManager();

    public RedstarMallCommunityManager getRedstarMallCommunityManager();

    public RedstarShopMallOrganizationManager getRedstarShopMallOrganizationManager();

    public RedstarMallEmployeeManager getRedstarMallEmployeeManager();

    public CacheManager getCacheManager();

    public RedstarTaskLogManager getRedstarTaskLogManager();

    public RedstarReviewManager getRedstarReviewManager();

    public RedstarReviewDetailManager getRedstarReviewDetailManager();

    public RedstarReviewLabelManager getRedstarReviewLabelManager();

    public RedstarReviewObjectManager getRedstarReviewObjectManager();

    public RedstarReviewResultDescManager getRedstarReviewResultDescManager();

    public RedstarReviewReplyManager getRedstarReviewReplyManager();

    public RedStarUvManager getRedStarUvManager();

    public RedStarPvManager getRedStarPvManager();




    public RedstarStoreManager getRedstarStoreManager();

    public RedstarStoreFavoriteManager getRedstarStoreFavoriteManager();

    public RedstarReviewLikedManager getRedstarReviewLikedManager();

    public RedstarAttendanceRecordManager getRedstarAttendanceRecordManager();

    public RedstarScoreLogManager getRedstarScoreLogManager();

    public  RedstarCommunityBuildingManager getRedstarCommunityBuildingManager();

    public RedstarAppAdManager getRedstarAppAdManager();

    public RedstarCommonManager getRedstarCommonManager();



}
