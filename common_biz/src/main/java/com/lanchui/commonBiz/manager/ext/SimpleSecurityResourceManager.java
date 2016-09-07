package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.SecurityResource;
import com.lanchui.commonBiz.manager.SecurityResourceManager;
import com.xiwa.base.manager.AbstractBasicManager;

/**
 * Created by Administrator on 2015/5/4.
 */
public class SimpleSecurityResourceManager extends AbstractBasicManager implements SecurityResourceManager {

    public SimpleSecurityResourceManager() {
        super(SecurityResource.class);
    }
}
