package com.chinaredstar.commonBiz.manager.ext;

import com.chinaredstar.commonBiz.manager.RedstarRestaurantDepartmentManager;
import com.chinaredstar.commonBiz.bean.work.RedstarRestaurantDepartment;
import com.xiwa.base.manager.AbstractBasicManager;

/**
 * Created by lenovo on 2016/7/8.
 */
public class SimpleRedstarRestaurantDepartmentManager extends AbstractBasicManager implements RedstarRestaurantDepartmentManager {
    public SimpleRedstarRestaurantDepartmentManager() {
        super(RedstarRestaurantDepartment.class);
    }
}
