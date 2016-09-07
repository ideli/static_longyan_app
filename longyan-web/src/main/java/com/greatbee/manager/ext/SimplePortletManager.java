package com.greatbee.manager.ext;

import com.greatbee.bean.Portlet;
import com.greatbee.manager.PortletManager;
import com.xiwa.base.manager.AbstractBasicManager;
import com.xiwa.base.manager.ManagerException;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import java.util.List;

/**
 * @author
 * @version 1.00 2011-7-31 14:09:18
 */
public class SimplePortletManager extends AbstractBasicManager implements PortletManager
{
    public SimplePortletManager()
    {
        super(Portlet.class);
    }

    /**
     * ��ȡϵͳPortlet
     *
     * @return
     * @throws com.xiwa.base.manager.ManagerException
     *
     */
    public List<Portlet> getPortletList(boolean system) throws ManagerException
    {
        Session session = getSession();
        try {
            session.beginTransaction();
            Criteria c = session.createCriteria(Portlet.class);
            c.add(Expression.eq("system", system));
//            c.add(Expression.eq("showWeb",showWeb));
            return c.list();
        }
        catch (Exception e) {
            throw new ManagerException(e.getMessage(), e);
        }
        finally {
            releaseSession(session);
        }
    }

    /**
     * 获取某个员工的Portlet
     *
     * @param employeeId
     * @return
     * @throws com.xiwa.base.manager.ManagerException
     *
     */
    public List<Portlet> getEmployeePortletList(int employeeId) throws ManagerException
    {
        Session session = getSession();
        try {
            session.beginTransaction();
            Criteria c = session.createCriteria(Portlet.class);
            c.add(Expression.eq("employeeId", employeeId));
            return c.list();
        }
        catch (Exception e) {
            throw new ManagerException(e.getMessage(), e);
        }
        finally {
            releaseSession(session);
        }
    }
}
