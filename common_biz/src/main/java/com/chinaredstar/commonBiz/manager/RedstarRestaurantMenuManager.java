package com.chinaredstar.commonBiz.manager;

import com.chinaredstar.commonBiz.bean.work.RedstarRestaurantMenu;
import com.xiwa.base.manager.BasicManager;
import com.xiwa.base.manager.ManagerException;

import java.util.List;

/**
 * Created by lenovo on 2016/7/8.
 */
public interface RedstarRestaurantMenuManager extends BasicManager {
    List<RedstarRestaurantMenu> findByRestaurantIdAndDate(int restaurantId) throws ManagerException;
}
