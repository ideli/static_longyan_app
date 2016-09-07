package com.lanchui.commonBiz.manager;

import com.lanchui.commonBiz.bean.RedstarEmployeeDayInput;
import com.xiwa.base.manager.BasicManager;

import java.util.List;

/**
 * Created by lenovo on 2016/7/12.
 */
public interface RedstarEmployeeDayInputManager extends BasicManager {
    List<RedstarEmployeeDayInput> getRedstarEmployeeDayInputInfo(int year, int month, int day);
}
