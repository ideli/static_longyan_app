package com.chinaredstar.commonBiz.manager;

import com.chinaredstar.commonBiz.bean.RedstarEmployeeDayInput;
import com.xiwa.base.manager.BasicManager;

import java.util.List;

/**
 * Created by lenovo on 2016/7/12.
 */
public interface RedstarEmployeeDayInputManager extends BasicManager {
    List<RedstarEmployeeDayInput> getRedstarEmployeeDayInputInfo(int year, int month, int day);
}
