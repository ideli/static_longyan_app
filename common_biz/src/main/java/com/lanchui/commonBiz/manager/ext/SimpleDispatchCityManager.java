package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.DispatchCity;
import com.lanchui.commonBiz.bean.DispatchProvince;
import com.lanchui.commonBiz.manager.DispatchCityManager;
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
