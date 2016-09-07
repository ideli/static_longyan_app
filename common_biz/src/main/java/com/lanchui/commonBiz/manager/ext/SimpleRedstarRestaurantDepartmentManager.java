package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.work.RedstarRestaurantDepartment;
import com.lanchui.commonBiz.manager.RedstarRestaurantDepartmentManager;
import com.xiwa.base.manager.AbstractBasicManager;

/**
 * Created by lenovo on 2016/7/8.
 */
public class SimpleRedstarRestaurantDepartmentManager extends AbstractBasicManager implements RedstarRestaurantDepartmentManager {
    public SimpleRedstarRestaurantDepartmentManager() {
        super(RedstarRestaurantDepartment.class);
    }
}
