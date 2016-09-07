package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.RedstarMallCommunity;
import com.lanchui.commonBiz.manager.RedstarMallCommunityManager;
import com.xiwa.base.manager.AbstractBasicManager;

/**
 * Created by lenovo on 2016/4/26.
 */
public class SimpleRedstarMallCommunityManager  extends AbstractBasicManager implements RedstarMallCommunityManager{

    public SimpleRedstarMallCommunityManager() {
        super(RedstarMallCommunity.class);
    }
}
