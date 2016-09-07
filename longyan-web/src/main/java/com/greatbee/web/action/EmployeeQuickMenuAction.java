package com.greatbee.web.action;

import com.greatbee.manager.GreatbeeDriver;
import com.xiwa.base.bean.search.ext.IntSearch;
import com.xiwa.base.bean.search.ext.MultiSearchBean;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.zeus.trinity.bean.Employee;
import com.xiwa.zeus.util.SessionTool;
import org.apache.struts2.support.AdvanceActionSupport;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 快捷方式
 *
 * @author CarlChen
 * @version 1.00 13-11-3 上午11:00
 */
@Scope ("prototype")
@Component ()
public class EmployeeQuickMenuAction extends AdvanceActionSupport
{
    private GreatbeeDriver greatbeeDriver;

    /**
     * 列表
     *
     * @return
     */
    public String list()
    {
        Employee loginEmployee = (Employee)SessionTool.getSessionDataObject(SessionTool.SESSION_EMPLOYEE);
        int belongedId = (Integer)SessionTool.getSessionDataObject(SessionTool.Session_Belonged);

        MultiSearchBean searchBean = new MultiSearchBean();

        IntSearch belongedIdSearch = new IntSearch("belongedId");
        belongedIdSearch.setSearchValue(String.valueOf(belongedId));
        searchBean.addSearchBean(belongedIdSearch);

        IntSearch createEmployeeId = new IntSearch("createEmployeeId");
        createEmployeeId.setSearchValue(String.valueOf(loginEmployee.getId()));
        searchBean.addSearchBean(createEmployeeId);

        try {
            dataList = greatbeeDriver.getEmployeeQuickMenuManager().searchIdentify(searchBean);
            response.addKey(Data_List, dataList);
        }
        catch (ManagerException e) {
            response.setOk(false);
            response.setMessage(e.getMessage());
        }
        return JSON;
    }
}
