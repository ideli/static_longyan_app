package com.chinaredstar.commonBiz.manager;

import com.xiwa.base.bean.search.SearchBean;
import com.xiwa.base.manager.BasicManager;

/**
 * Created by lenovo on 2016/4/21.
 */
public interface DispatchSecurityOperationLogManager extends BasicManager {

    public  Integer getCount(Class c,String _today,String compareColumn,SearchBean searchBean);

    public Integer getLtSum(Class c,String _today,String compareColumn,SearchBean searchBean);

    public Integer getAllCount(Class c,SearchBean searchBean);


    public Integer getSum(Class c,String _today,String compareColumn,String sumColumn,SearchBean searchBean,Boolean gt);

    public  Integer getCountryCount(Class c,String _today,String compareColumn,SearchBean searchBean);

    public  Integer getLastDayData(Class c,String compareColumn,String beginDay,String endDay, SearchBean searchBean);
}
