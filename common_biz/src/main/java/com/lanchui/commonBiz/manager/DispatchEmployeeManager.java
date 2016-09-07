package com.lanchui.commonBiz.manager;


import com.xiwa.base.bean.PaginationDescribe;
import com.xiwa.base.manager.BasicManager;
import com.xiwa.base.manager.ManagerException;



/**
 * Created by wangj on 2015/5/19.
 */
public interface DispatchEmployeeManager extends BasicManager {

   public PaginationDescribe getByPages(int page,int pageSize, Class c, String order,boolean desc) throws ManagerException;


}
