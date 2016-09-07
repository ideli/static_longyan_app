package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.RedstarMallMonth;
import com.lanchui.commonBiz.manager.RedstarMallMonthManager;
import com.xiwa.base.manager.AbstractBasicManager;
import com.xiwa.base.manager.AbstractManager;

/**
 * Created by lenovo on 2016/4/27.
 */
public class SimpleRedstarMallMonthManager extends AbstractBasicManager implements RedstarMallMonthManager {
    public SimpleRedstarMallMonthManager() {
        super(RedstarMallMonth.class);
    }
}
