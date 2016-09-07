package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.RedstarEmployeeMonth;
import com.lanchui.commonBiz.manager.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by usagizhang on 15-4-15.
 */
@Component("dispatchDriver")
public class SimpleDispatchDriver implements DispatchDriver {
    @Autowired
    private DispatchWorkerManager dispatchWorkerManager;
    @Autowired
    private DispatchCityManager dispatchCityManager;
    @Autowired
    private DispatchUserManager dispatchUserManager;
    @Autowired
    private DispatchProvinceManager dispatchProvinceManager;
    @Autowired
    private DispatchLocationManager dispatchLocationManager;
    @Autowired
    private DispatchMerchantAuthorizedManager dispatchMerchantAuthorizedManager;
    @Autowired
    private DispatchMerchantManager dispatchMerchantManager;
    @Autowired
    private DispatchEmployeeManager dispatchEmployeeManager;
    @Autowired
    private RedstarCommunityManager redstarCommunityManager;
    @Autowired
    private DispatchCommunityAuthorizedManager dispatchCommunityAuthorizedManager;
    @Autowired
    private DispatchLoginTokenManager dispatchLoginTokenManager;
    @Autowired
    private DispatchCommunityGardenManager dispatchCommunityGardenManager;
    @Autowired
    private DispatchCommunityBuildingManager dispatchCommunityBuildingManager;
    @Autowired
    private DispatchCommunityUnitManager dispatchCommunityUnitManager;
    @Autowired
    private DispatchCommunityRoomManager dispatchCommunityRoomManager;
    @Autowired
    private DispatchCommunityRoomStatusManager dispatchCommunityRoomStatusManager;
    @Autowired
    private DispatchUserRoomManager dispatchUserRoomManager;
    @Autowired
    private DispatchShopManager dispatchShopManager;
    @Autowired
    private DispatchShopCategoryManager dispatchShopCategoryManager;
    @Autowired
    private DispatchPushDeviceManager dispatchPushDeviceManager;
    @Autowired
    private DispatchShopAuthorizedManager dispatchShopAuthorizedManager;
    @Autowired
    private MessageSMSQueueManager messageSMSQueueManager;
    @Autowired
    private RedstarSessionManager redstarSessionManager;

    @Autowired
    private RedstarMemberManager redstarMemberManager;

    @Autowired
    private DispatchSecurityOperationLogManager dispatchSecurityOperationLogManager;

    @Autowired
    private RedstarEmployeeManager redstarEmployeeManager;
    @Autowired
    private RedstarDepartmentManager redstarDepartmentManager;

    @Autowired
    private RedstarVerifyCodeManager redstarVerifyCodeManager;
    @Autowired
    private RedstarEmployeeMonthManager redstarEmployeeMonthManager;

    @Autowired
    private RedstarShoppingMallManager redstarShoppingMallManager;

    @Autowired
    private RedstarMallCommunityManager redstarMallCommunityManager;

    @Autowired
    private RedstarShopMallOrganizationManager redstarShopMallOrganizationManager;
    @Autowired
    private RedstarMallEmployeeManager redstarMallEmployeeManager;

    @Autowired
    private RedstarTaskLogManager redstarTaskLogManager;

    @Autowired
    private RedstarReviewReplyManager redstarReviewReplyManager;

    @Autowired
    private RedstarReviewResultDescManager redstarReviewResultDescManager;

    @Autowired
    private RedstarReviewDetailManager redstarReviewDetailManager;

    @Autowired
    private RedstarReviewObjectManager redstarReviewObjectManager;

    @Autowired
    private RedstarReviewManager redstarReviewManager;

    @Autowired
    private RedstarReviewLabelManager redstarReviewLabelManager;

    @Autowired
    private RedstarStoreManager redstarStoreManager;

    @Autowired
    private RedstarStoreFavoriteManager redstarStoreFavoriteManager;

    @Autowired
    private RedstarReviewLikedManager redstarReviewLikedManager;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private RedStarUvManager redStarUvManager;

    @Autowired
    private RedStarPvManager redStarPvManager;

    @Autowired
    private RedstarAttendanceRecordManager redstarAttendanceRecordManager;

    @Autowired
    private RedstarScoreLogManager redstarScoreLogManager;

    @Autowired
    private RedstarCommunityBuildingManager redstarCommunityBuildingManager;

    @Autowired
    private RedstarAppAdManager redstarAppAdManager;

    @Autowired
    private RedstarCommonManager redstarCommonManager;

    @Override
    public DispatchWorkerManager getDispatchWorkerManager() {
        return dispatchWorkerManager;
    }

    @Override
    public DispatchCityManager getDispatchCityManager() {
        return dispatchCityManager;
    }

    @Override
    public DispatchUserManager getDispatchUserManager() {
        return dispatchUserManager;
    }

    @Override
    public DispatchProvinceManager getDispatchProvinceManager() {
        return dispatchProvinceManager;
    }

    @Override
    public DispatchLocationManager getDispatchLocationManager() {
        return dispatchLocationManager;
    }

    @Override
    public DispatchMerchantAuthorizedManager getDispatchMerchantAuthorizedManager() {
        return dispatchMerchantAuthorizedManager;
    }

    @Override
    public DispatchMerchantManager getDispatchMerchantManager() {
        return dispatchMerchantManager;
    }


    @Override
    public DispatchEmployeeManager getDispatchEmployeeManager() {
        return dispatchEmployeeManager;
    }


    @Override
    public DispatchCommunityAuthorizedManager getDispatchCommunityAuthorizedManager() {
        return dispatchCommunityAuthorizedManager;
    }

    @Override
    public DispatchLoginTokenManager getDispatchLoginTokenManager() {
        return dispatchLoginTokenManager;
    }

    @Override
    public DispatchCommunityGardenManager getDispatchCommunityGardenManager() {
        return dispatchCommunityGardenManager;
    }

    @Override
    public DispatchCommunityBuildingManager getDispatchCommunityBuildingManager() {
        return dispatchCommunityBuildingManager;
    }

    @Override
    public DispatchCommunityUnitManager getDispatchCommunityUnitManager() {
        return dispatchCommunityUnitManager;
    }

    @Override
    public DispatchCommunityRoomManager getDispatchCommunityRoomManager() {
        return dispatchCommunityRoomManager;
    }

    @Override
    public DispatchCommunityRoomStatusManager getDispatchCommunityRoomStatusManager() {
        return dispatchCommunityRoomStatusManager;
    }

    @Override
    public DispatchUserRoomManager getDispatchUserRoomManager() {
        return dispatchUserRoomManager;
    }

    @Override
    public DispatchShopManager getDispatchShopManager() {
        return dispatchShopManager;
    }

    @Override
    public DispatchShopCategoryManager getDispatchShopCategoryManager() {
        return dispatchShopCategoryManager;
    }

    @Override
    public DispatchPushDeviceManager getDispatchPushDeviceManager() {
        return dispatchPushDeviceManager;
    }


    @Override
    public DispatchShopAuthorizedManager getDispatchShopAuthorizedManager() {
        return dispatchShopAuthorizedManager;
    }

    @Override
    public MessageSMSQueueManager getMessageSMSQueueManager() {
        return messageSMSQueueManager;
    }

    @Override
    public RedstarSessionManager getRedstarSessionManager() {
        return redstarSessionManager;
    }

    @Override
    public DispatchSecurityOperationLogManager getSecurityOperationLogManager() {
        return dispatchSecurityOperationLogManager;
    }

    @Override
    public RedstarMemberManager getRedStarMemberManager() {
        return redstarMemberManager;
    }

    @Override
    public RedstarEmployeeManager getRedstarEmployeeManager() {
        return redstarEmployeeManager;
    }

    @Override
    public RedstarCommunityManager getRedstarCommunityManager() {
        return redstarCommunityManager;
    }

    @Override
    public RedstarVerifyCodeManager getRedstarVerifyCodeManager() {
        return redstarVerifyCodeManager;
    }

    @Override
    public RedstarEmployeeMonthManager getRedstarEmployeeMonthManager() {
        return redstarEmployeeMonthManager;
    }

    @Override
    public RedstarShoppingMallManager getRedstarShoppingMallManager() {
        return redstarShoppingMallManager;
    }

    @Override
    public RedstarMallCommunityManager getRedstarMallCommunityManager() {
        return redstarMallCommunityManager;
    }

    @Override
    public RedstarShopMallOrganizationManager getRedstarShopMallOrganizationManager() {
        return redstarShopMallOrganizationManager;
    }

    @Override
    public RedstarDepartmentManager getRedstarDepartmentManager() {
        return redstarDepartmentManager;
    }

    @Override
    public RedstarMallEmployeeManager getRedstarMallEmployeeManager() {
        return redstarMallEmployeeManager;
    }

    @Override
    public CacheManager getCacheManager() {
        return cacheManager;
    }

    @Override
    public RedstarTaskLogManager getRedstarTaskLogManager() {
        return redstarTaskLogManager;
    }

    @Override
    public RedstarReviewReplyManager getRedstarReviewReplyManager() {
        return redstarReviewReplyManager;
    }

    @Override
    public RedStarUvManager getRedStarUvManager() {
        return redStarUvManager;
    }

    @Override
    public RedStarPvManager getRedStarPvManager() {
        return redStarPvManager;
    }

    @Override
    public RedstarStoreManager getRedstarStoreManager() {
        return redstarStoreManager;
    }

    @Override
    public RedstarStoreFavoriteManager getRedstarStoreFavoriteManager() {
        return redstarStoreFavoriteManager;
    }

    @Override
    public RedstarReviewLikedManager getRedstarReviewLikedManager() {
        return redstarReviewLikedManager;
    }

    @Override
    public RedstarAttendanceRecordManager getRedstarAttendanceRecordManager() {
        return redstarAttendanceRecordManager;
    }

    @Override
    public RedstarReviewResultDescManager getRedstarReviewResultDescManager() {
        return redstarReviewResultDescManager;
    }

    @Override
    public RedstarReviewDetailManager getRedstarReviewDetailManager() {
        return redstarReviewDetailManager;
    }

    @Override
    public RedstarReviewObjectManager getRedstarReviewObjectManager() {
        return redstarReviewObjectManager;
    }

    @Override
    public RedstarReviewManager getRedstarReviewManager() {
        return redstarReviewManager;
    }

    @Override
    public RedstarReviewLabelManager getRedstarReviewLabelManager() {
        return redstarReviewLabelManager;
    }

    @Override
    public RedstarScoreLogManager getRedstarScoreLogManager() {
        return redstarScoreLogManager;
    }

    @Override
    public RedstarCommunityBuildingManager getRedstarCommunityBuildingManager() {
        return redstarCommunityBuildingManager;
    }

    @Override
    public RedstarAppAdManager getRedstarAppAdManager() {
        return redstarAppAdManager;
    }

    @Override
    public RedstarCommonManager getRedstarCommonManager() {
        return redstarCommonManager;
    }
}
