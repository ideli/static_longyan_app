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
}
