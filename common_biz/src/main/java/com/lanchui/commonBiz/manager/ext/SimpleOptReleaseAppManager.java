package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.OptReleaseApp;
import com.lanchui.commonBiz.manager.OptReleaseAppManager;
import com.xiwa.base.manager.AbstractBasicManager;

/**
 * Created by Administrator on 2015/10/9.
 */
public class SimpleOptReleaseAppManager extends AbstractBasicManager implements OptReleaseAppManager {
    public SimpleOptReleaseAppManager() {
        super(OptReleaseApp.class);
    }
}
