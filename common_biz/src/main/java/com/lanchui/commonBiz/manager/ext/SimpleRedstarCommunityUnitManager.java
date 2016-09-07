package com.lanchui.commonBiz.manager.ext;


import com.lanchui.commonBiz.bean.RedstarCommunity;
import com.lanchui.commonBiz.bean.RedstarCommunityUnit;
import com.lanchui.commonBiz.manager.RedstarCommunityUnitManager;
import com.xiwa.base.manager.AbstractBasicManager;

/**
 * Created by lenovo on 2016/7/13.
 */
public class SimpleRedstarCommunityUnitManager extends AbstractBasicManager implements RedstarCommunityUnitManager {

    public SimpleRedstarCommunityUnitManager() {
        super(RedstarCommunityUnit.class);
    }

}
