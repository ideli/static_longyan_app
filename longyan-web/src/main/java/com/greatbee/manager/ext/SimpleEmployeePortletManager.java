package com.greatbee.manager.ext;

import com.greatbee.bean.EmployeePortlet;
import com.greatbee.manager.EmployeePortletManager;
import com.xiwa.base.manager.AbstractBasicManager;

/**
 * @author CarlChen
 * @version 1.00 2011-7-31 14:10:36
 */
public class SimpleEmployeePortletManager extends AbstractBasicManager implements EmployeePortletManager
{
    public SimpleEmployeePortletManager()
    {
        super(EmployeePortlet.class);
    }
}
