package com.chinaredstar.commonBiz.manager;

import com.chinaredstar.nvwaBiz.manager.NvwaSecurityOperationLogManager;
import com.chinaredstar.nvwaBiz.manager.NvwaDepartmentManager;
import com.chinaredstar.nvwaBiz.manager.NvwaEmployeeManager;

/**
 * Created by usagizhang on 15-4-15.
 */
public interface DispatchDriver {

    public DispatchCityManager getDispatchCityManager();

    public DispatchProvinceManager getDispatchProvinceManager();

    public DispatchLocationManager getDispatchLocationManager();

    public RedstarCommunityManager getRedstarCommunityManager();

    public RedstarSessionManager getRedstarSessionManager();

    public RedstarMemberManager getRedStarMemberManager();

    public RedstarEmployeeMonthManager getRedstarEmployeeMonthManager();

    public RedstarShoppingMallManager getRedstarShoppingMallManager();

    public RedstarMallCommunityManager getRedstarMallCommunityManager();

    public RedstarShopMallOrganizationManager getRedstarShopMallOrganizationManager();

    public RedstarMallEmployeeManager getRedstarMallEmployeeManager();

    public CacheManager getCacheManager();

    public RedstarTaskLogManager getRedstarTaskLogManager();

    public RedstarAttendanceRecordManager getRedstarAttendanceRecordManager();

    public RedstarScoreLogManager getRedstarScoreLogManager();

    public  RedstarCommunityBuildingManager getRedstarCommunityBuildingManager();

    public RedstarAppAdManager getRedstarAppAdManager();

    public RedstarCommonManager getRedstarCommonManager();



}
