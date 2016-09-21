package com.chinaredstar.commonBiz.manager.ext;

import com.chinaredstar.commonBiz.bean.RedstarMessageCenter;
import com.chinaredstar.commonBiz.manager.RedstarMessageCenterManager;
import com.xiwa.base.manager.AbstractBasicManager;

/**
 * Created by StevenDong on 2016/9/21
 */
public class SimpleRedstarMessageCenterManager extends AbstractBasicManager implements RedstarMessageCenterManager {
    public SimpleRedstarMessageCenterManager() {
        super(RedstarMessageCenter.class);
    }
}
