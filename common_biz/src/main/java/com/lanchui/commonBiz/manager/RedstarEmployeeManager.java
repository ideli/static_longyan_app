package com.lanchui.commonBiz.manager;

import com.lanchui.commonBiz.bean.RedstarEmployee;
import com.lanchui.commonBiz.bean.RedstarRole;
import com.xiwa.base.bean.search.SearchBean;
import com.xiwa.base.manager.BasicManager;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.security.bean.ext.SimpleAuthorized;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2016/4/22.
 */
public interface RedstarEmployeeManager extends BasicManager {
    public RedstarEmployee getEmployeeByPhone(String phone) throws ManagerException;

    /**
     * 验证是否注册过
     *
     * @param phone
     * @return
     * @throws ManagerException
     */
    public boolean isRegister(String phone) throws ManagerException;

    public List<SimpleAuthorized> searchSimpleAuthorized(SearchBean multiSearchBean) throws ManagerException;

    public void addAuthorized(SimpleAuthorized authorized) throws ManagerException;

    public void updateAuthorized(SimpleAuthorized authorized) throws ManagerException;

    public List getBeanList(Collection operationBeanIds, String queryColumn, String orderColumn, Boolean desc) throws ManagerException;

    public RedstarEmployee getEmployeeByCode(String code) throws ManagerException;

    public List<RedstarRole> getRoleByEmployeeId(int employeeId) throws ManagerException;

    public Map<String,Object> getDataListByIds(Integer page,Integer pageSize,Collection operationBeanIds, String queryColumn, String orderColumn, Boolean desc) throws ManagerException;
}
