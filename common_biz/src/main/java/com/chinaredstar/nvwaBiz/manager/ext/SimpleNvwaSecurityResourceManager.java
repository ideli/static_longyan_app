package com.chinaredstar.nvwaBiz.manager.ext;

import com.chinaredstar.nvwaBiz.bean.NvwaSecurityResource;
import com.chinaredstar.nvwaBiz.manager.NvwaSecurityResourceManager;
import com.xiwa.base.manager.AbstractBasicManager;

/**
 * Created by Administrator on 2015/5/4.
 */
public class SimpleNvwaSecurityResourceManager extends AbstractBasicManager implements NvwaSecurityResourceManager {

    public SimpleNvwaSecurityResourceManager() {
        super(NvwaSecurityResource.class);
    }
}
