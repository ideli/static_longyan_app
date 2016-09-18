package com.chinaredstar.commonBiz.manager;

import com.chinaredstar.commonBiz.bean.work.RedstarRestaurantOrder;
import com.xiwa.base.manager.BasicManager;
import com.xiwa.base.manager.ManagerException;

import java.util.List;

/**
 * Created by lenovo on 2016/7/8.
 */
public interface RedstarRestaurantOrderManager extends BasicManager {

    boolean findByRestaurantIdAndEmployeeIdAndDay(int restaurantId, int employeeId,String status,RedstarCommonManager redstarCommonManager) throws ManagerException;

    List<RedstarRestaurantOrder> findByRestaurantIdAndEmployeeIdAndDayInfo(int restaurantId, int employeeId,String status) throws ManagerException;
}
