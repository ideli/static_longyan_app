package com.lanchui.commonBiz.manager;

import com.lanchui.commonBiz.bean.RedstarEmployeeMonth;
import com.xiwa.base.manager.BasicManager;
import com.xiwa.base.manager.ManagerException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by lenovo on 2016/4/26.
 */
public interface RedstarEmployeeMonthManager extends BasicManager {
    List<RedstarEmployeeMonth> getRedstarEmployeeDayInputInfo(int year, int month);
    public <T> List<T> getRankDataList (List<Object> paramList, Class<T> targetClass) throws ManagerException;
    public <T> List<T> getCurrentUserDataList (List<Object> paramList, Class<T> targetClass) throws ManagerException;
    public <T> List<T> getHistoryList (List<Object> paramList, Class<T> targetClass) throws ManagerException;
}
