package com.chinaredstar.commonBiz.manager.ext;

import com.chinaredstar.commonBiz.manager.*;
import com.chinaredstar.nvwaBiz.manager.NvwaSecurityOperationLogManager;
import com.chinaredstar.nvwaBiz.manager.NvwaDepartmentManager;
import com.chinaredstar.nvwaBiz.manager.NvwaEmployeeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by usagizhang on 15-4-15.
 */
@Component("dispatchDriver")
public class SimpleDispatchDriver implements DispatchDriver {
    @Autowired
    private DispatchCityManager dispatchCityManager;
    @Autowired
    private DispatchProvinceManager dispatchProvinceManager;
    @Autowired
    private DispatchLocationManager dispatchLocationManager;
    @Autowired
    private RedstarCommunityManager redstarCommunityManager;
    @Autowired
    private RedstarSessionManager redstarSessionManager;

    @Autowired
    private RedstarMemberManager redstarMemberManager;

    @Autowired
    private RedstarVerifyCodeManager redstarVerifyCodeManager;

    @Autowired
    private RedstarEmployeeDayInputManager redstarEmployeeDayInputManager;

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
    private CacheManager cacheManager;

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

    @Autowired
    private RedstarMessageCenterManager redstarMessageCenterManager;

    @Override
    public DispatchCityManager getDispatchCityManager() {
        return dispatchCityManager;
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
    public RedstarSessionManager getRedstarSessionManager() {
        return redstarSessionManager;
    }



    @Override
    public RedstarMemberManager getRedStarMemberManager() {
        return redstarMemberManager;
    }



    @Override
    public RedstarCommunityManager getRedstarCommunityManager() {
        return redstarCommunityManager;
    }

    @Override
    public RedstarEmployeeDayInputManager getRedstarEmployeeDayInputManager() {
        return redstarEmployeeDayInputManager;
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
    public RedstarAttendanceRecordManager getRedstarAttendanceRecordManager() {
        return redstarAttendanceRecordManager;
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

    @Override
    public RedstarMessageCenterManager getRedstarMessageCenterManager() {
        return redstarMessageCenterManager;
    }
}
