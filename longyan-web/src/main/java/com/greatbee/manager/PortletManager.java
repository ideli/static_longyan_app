package com.greatbee.manager;

import com.greatbee.bean.Portlet;
import com.xiwa.base.manager.BasicManager;
import com.xiwa.base.manager.ManagerException;

import java.util.List;

/**
 * Portlet Manager
 *
 * @author CarlChen
 * @version 1.00 2011-2-26 20:40:07
 */
public interface PortletManager extends BasicManager
{
    /**
     * ��ȡϵͳPortlet
     *
     * @return
     * @throws ManagerException
     */
    public List<Portlet> getPortletList(boolean system) throws ManagerException;

    /**
     * 获取某个员工的Portlet
     *
     * @param employeeId
     * @return
     * @throws ManagerException
     */
    public List<Portlet> getEmployeePortletList(int employeeId) throws ManagerException;
}
