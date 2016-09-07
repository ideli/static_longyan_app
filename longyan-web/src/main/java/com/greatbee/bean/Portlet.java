package com.greatbee.bean;

import com.xiwa.base.bean.AliasBean;
import com.xiwa.base.bean.ShowWebAble;
import com.xiwa.base.bean.ext.SimpleINDP;
import com.xiwa.zeus.trinity.bean.EmployeeInjectable;

/**
 * Portlet
 *
 * @author CarlChen
 * @version 1.00 2011-2-26 20:00:41
 */
public class Portlet extends SimpleINDP implements AliasBean, ShowWebAble, EmployeeInjectable
{
    private String title;
    private String url;
    private String alias;
    private String portletType;

    //系统Portlet
    private boolean system;
    private boolean showWeb;

    private int employeeId;

    public String getPortletType()
    {
        return portletType;
    }

    public void setPortletType(String portletType)
    {
        this.portletType = portletType;
    }

    /**
     * ����
     *
     * @return
     */
    public String getAlias()
    {
        return alias;
    }

    public String getTitle()
    {
        return title;
    }

    public String getUrl()
    {
        return url;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public void setAlias(String alias)
    {
        this.alias = alias;
    }

    /**
     * �Ƿ���ϵͳPortlet
     *
     * @return
     */
    public boolean isSystem()
    {
        return system;
    }

    public void setSystem(boolean system)
    {
        this.system = system;
    }

    /**
     * �Ƿ���վǰ̨��ʾ
     *
     * @return
     */
    public boolean isShowWeb()
    {
        return showWeb;
    }

    /**
     * �����Ƿ���վǰ̨��ʾ
     *
     * @param showWeb
     */
    public void setShowWeb(boolean showWeb)
    {
        this.showWeb = showWeb;
    }

    /**
     * ��ȡԱ��ID
     *
     * @return
     */
    public int getEmployeeId()
    {
        return employeeId;
    }

    /**
     * ����Ա��ID
     *
     * @param employeeId
     */
    public void setEmployeeId(int employeeId)
    {
        this.employeeId = employeeId;
    }
}