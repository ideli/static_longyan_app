package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.DispatchPushDevice;
import com.lanchui.commonBiz.manager.DispatchPushDeviceManager;
import com.xiwa.base.manager.AbstractBasicManager;

/**
 * Created by bingcheng on 2015/10/9.
 */
public class SimpleDispatchPushDeviceManager extends AbstractBasicManager implements DispatchPushDeviceManager {

    public SimpleDispatchPushDeviceManager() {
        super(DispatchPushDevice.class);
    }
}
