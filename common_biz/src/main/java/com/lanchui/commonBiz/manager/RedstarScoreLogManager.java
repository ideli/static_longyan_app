package com.lanchui.commonBiz.manager;

import com.lanchui.commonBiz.bean.RedstarScoreLog;
import com.xiwa.base.manager.BasicManager;

import java.util.List;

/**
 * Created by LeiYun on 2016/6/29.
 */
public interface RedstarScoreLogManager extends BasicManager {

    public List<Object[]> getScoreLogInfo(String startDate, String endDate);
}
