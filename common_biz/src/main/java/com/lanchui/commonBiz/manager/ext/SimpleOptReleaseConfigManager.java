package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.OptReleaseConfig;
import com.lanchui.commonBiz.manager.OptReleaseConfigManager;
import com.xiwa.base.manager.AbstractBasicManager;

/**
 * Created by Administrator on 2015/10/9.
 */
public class SimpleOptReleaseConfigManager extends AbstractBasicManager implements OptReleaseConfigManager {
    public SimpleOptReleaseConfigManager() {
        super(OptReleaseConfig.class);
    }
}
