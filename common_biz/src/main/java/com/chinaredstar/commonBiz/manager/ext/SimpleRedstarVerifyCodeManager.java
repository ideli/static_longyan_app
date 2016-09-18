package com.chinaredstar.commonBiz.manager.ext;

import com.chinaredstar.commonBiz.manager.RedstarVerifyCodeManager;
import com.chinaredstar.commonBiz.bean.RedstarVerifyCode;
import com.xiwa.base.manager.AbstractBasicManager;

/**
 * Created by usagizhang on 16-4-25.
 */
public class SimpleRedstarVerifyCodeManager extends AbstractBasicManager implements RedstarVerifyCodeManager {
    public SimpleRedstarVerifyCodeManager() {
        super(RedstarVerifyCode.class);
    }
}
