package com.chinaredstar.commonBiz.manager.ext;

import com.chinaredstar.commonBiz.bean.DispatchCity;
import com.chinaredstar.commonBiz.manager.DispatchCityManager;
import com.xiwa.base.bean.search.ext.TextSearch;
import com.xiwa.base.manager.AbstractBasicManager;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.util.CollectionUtil;

import java.util.List;

/**
 * Created by Administrator on 2015/4/15.
 */
public class SimpleDispatchCityManager extends AbstractBasicManager implements DispatchCityManager {

    public SimpleDispatchCityManager() {
        super(DispatchCity.class);
    }

    @Override
    public DispatchCity getCity(String provinceCode) throws ManagerException {
        TextSearch codeSearch = new TextSearch("cityCode");
        codeSearch.setSearchValue(provinceCode);
        List<DispatchCity> cities = this.searchIdentify(codeSearch);
        if (CollectionUtil.isValid(cities)) {
            return cities.get(0);
        }
        return null;
    }
}
