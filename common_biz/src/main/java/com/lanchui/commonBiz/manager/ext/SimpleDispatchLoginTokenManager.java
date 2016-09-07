package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.DispatchLoginToken;
import com.lanchui.commonBiz.manager.DispatchLoginTokenManager;
import com.xiwa.base.manager.AbstractBasicManager;

/**
 * Created by bingcheng on 2015/6/23.
 */
public class SimpleDispatchLoginTokenManager extends AbstractBasicManager implements DispatchLoginTokenManager {

    public SimpleDispatchLoginTokenManager() {
        super(DispatchLoginToken.class);
    }
}
