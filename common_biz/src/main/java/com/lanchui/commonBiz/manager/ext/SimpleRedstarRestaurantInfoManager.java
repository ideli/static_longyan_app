package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.work.RedstarRestaurantInfo;
import com.lanchui.commonBiz.manager.RedstarRestaurantInfoManager;
import com.xiwa.base.manager.AbstractBasicManager;

/**
 * Created by lenovo on 2016/7/11.
 */
public class SimpleRedstarRestaurantInfoManager extends AbstractBasicManager implements RedstarRestaurantInfoManager {
    public SimpleRedstarRestaurantInfoManager() {
        super(RedstarRestaurantInfo.class);
    }
}
