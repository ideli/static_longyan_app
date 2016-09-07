package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.RedstarMallEmployee;
import com.lanchui.commonBiz.manager.RedstarMallEmployeeManager;
import com.xiwa.base.bean.search.ext.IntSearch;
import com.xiwa.base.bean.search.ext.MultiSearchBean;
import com.xiwa.base.manager.AbstractBasicManager;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.util.CollectionUtil;
import com.xiwa.base.util.StringUtil;

import java.util.List;

/**
 * Created by lenovo on 2016/4/26.
 */
public class SimpleRedstarMallEmployeeManager extends AbstractBasicManager implements RedstarMallEmployeeManager {

    public SimpleRedstarMallEmployeeManager() {
        super(RedstarMallEmployee.class);
    }

    public RedstarMallEmployee getRedstarMallEmployee(int employeeId, int mallId) throws ManagerException {
        MultiSearchBean multiSearchBean = new MultiSearchBean();
        IntSearch employeeIdSearch = new IntSearch("employeeId");
        employeeIdSearch.setSearchValue(StringUtil.getString(employeeId));
        IntSearch mallSearch = new IntSearch("shoppingMallId");
        mallSearch.setSearchValue(StringUtil.getString(mallId));
        multiSearchBean.addSearchBean(mallSearch);
        multiSearchBean.addSearchBean(employeeIdSearch);

        List<RedstarMallEmployee> list = this.searchIdentify(multiSearchBean);
        if (CollectionUtil.isValid(list)) {
            return list.get(0);
        } else {
            return null;
        }
    }
}
