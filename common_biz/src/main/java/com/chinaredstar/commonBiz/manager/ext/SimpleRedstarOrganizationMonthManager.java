package com.chinaredstar.commonBiz.manager.ext;

import com.chinaredstar.commonBiz.bean.RedstarOrganizationMonth;
import com.chinaredstar.commonBiz.manager.RedstarOrganizationMonthManager;
import com.xiwa.base.manager.AbstractBasicManager;

/**
 * Created by lenovo on 2016/4/27.
 */
public class SimpleRedstarOrganizationMonthManager extends AbstractBasicManager implements RedstarOrganizationMonthManager {
    public SimpleRedstarOrganizationMonthManager() {
        super(RedstarOrganizationMonth.class);
    }
}
