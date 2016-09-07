package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.work.RedstarAttendanceRecord;
import com.lanchui.commonBiz.manager.RedstarAttendanceRecordManager;
import com.xiwa.base.manager.AbstractBasicManager;

/**
 * Created by LeiYun on 2016/7/8.
 */
public class SimpleRedstarAttendanceRecordManager extends AbstractBasicManager implements RedstarAttendanceRecordManager {

    public SimpleRedstarAttendanceRecordManager(){
        super((RedstarAttendanceRecord.class));
    }


}
