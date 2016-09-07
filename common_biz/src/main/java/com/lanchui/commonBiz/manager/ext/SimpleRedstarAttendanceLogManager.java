package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.work.RedstarAttendanceLog;
import com.lanchui.commonBiz.manager.RedstarAttendanceLogManager;
import com.xiwa.base.manager.AbstractBasicManager;

/**
 * Created by lenovo on 2016/7/13.
 */
public class SimpleRedstarAttendanceLogManager extends AbstractBasicManager implements RedstarAttendanceLogManager {
    public SimpleRedstarAttendanceLogManager(){
        super(RedstarAttendanceLog.class);
    }
}
