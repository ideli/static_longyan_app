package com.chinaredstar.nvwaBiz.manager;

import com.chinaredstar.nvwaBiz.bean.NvwaRole;
import com.chinaredstar.nvwaBiz.bean.NvwaEmployee;
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
public interface NvwaEmployeeManager extends BasicManager {
    public NvwaEmployee getEmployeeByPhone(String phone) throws ManagerException;

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

    public NvwaEmployee getEmployeeByCode(String code) throws ManagerException;

    public NvwaEmployee getEmployeeById(int id) throws ManagerException;

    public List<NvwaRole> getRoleByEmployeeId(int employeeId) throws ManagerException;

    public Map<String,Object> getDataListByIds(Integer page,Integer pageSize,Collection operationBeanIds, String queryColumn, String orderColumn, Boolean desc) throws ManagerException;
}
