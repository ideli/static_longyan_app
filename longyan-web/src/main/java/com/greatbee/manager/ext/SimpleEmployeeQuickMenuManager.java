package com.greatbee.manager.ext;

import com.greatbee.bean.EmployeeQuickMenu;
import com.greatbee.manager.EmployeeQuickMenuManager;
import com.xiwa.base.manager.AbstractBasicManager;

/**
 * @author CarlChen
 * @version 1.00 13-11-3 上午10:58
 */
public class SimpleEmployeeQuickMenuManager extends AbstractBasicManager implements EmployeeQuickMenuManager
{
    public SimpleEmployeeQuickMenuManager()
    {
        super(EmployeeQuickMenu.class);
    }
}
