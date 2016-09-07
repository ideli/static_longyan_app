package com.greatbee.bean;

import com.xiwa.base.bean.Identified;

/**
 * Employee Portlet
 *
 * @author CarlChen
 * @version 1.00 2011-7-31 13:59:54
 */
public class EmployeePortlet implements Identified 
{
    private int id;
    private int employeeId;
    private int portletId;

    /**
     * ����ʵ��Ψһ��ʶ
     *
     * @return ��ʶ
     */
    public int getId()
    {
        return id;
    }

    public int getEmployeeId()
    {
        return employeeId;
    }

    public void setEmployeeId(int employeeId)
    {
        this.employeeId = employeeId;
    }

    public int getPortletId()
    {
        return portletId;
    }

    public void setPortletId(int portletId)
    {
        this.portletId = portletId;
    }

    public void setId(int id)
    {
        this.id = id;
    }
}
