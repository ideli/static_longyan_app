package com.chinaredstar.commonBiz.manager.ext;

import com.chinaredstar.commonBiz.bean.RedstarCommunityUpdateLog;
import com.chinaredstar.commonBiz.manager.RedstarCommunityUpdateLogManager;
import com.xiwa.base.manager.AbstractBasicManager;

/**
 * Created by StevenDong on 2016/9/21
 */
public class SimpleRedstarCommunityUpdateLogManager extends AbstractBasicManager implements RedstarCommunityUpdateLogManager {

    public SimpleRedstarCommunityUpdateLogManager() {
        super(RedstarCommunityUpdateLog.class);
    }
}
