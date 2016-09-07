package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.RedstarStorePV;
import com.lanchui.commonBiz.manager.RedStarPvManager;
import com.xiwa.base.manager.AbstractBasicManager;

/**
 * Created by LeiYun on 2016/6/30.
 */
public class SimpleRedStarPvManager extends AbstractBasicManager implements RedStarPvManager {

    public SimpleRedStarPvManager() {
        super(RedstarStorePV.class);
    }
}
