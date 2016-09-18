package com.chinaredstar.commonBiz.manager.ext;

import com.chinaredstar.commonBiz.bean.RedstarMallMonth;
import com.chinaredstar.commonBiz.manager.RedstarMallMonthManager;
import com.xiwa.base.manager.AbstractBasicManager;

/**
 * Created by lenovo on 2016/4/27.
 */
public class SimpleRedstarMallMonthManager extends AbstractBasicManager implements RedstarMallMonthManager {
    public SimpleRedstarMallMonthManager() {
        super(RedstarMallMonth.class);
    }
}
