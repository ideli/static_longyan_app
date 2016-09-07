package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.DispatchCity;
import com.lanchui.commonBiz.bean.DispatchLocation;
import com.lanchui.commonBiz.manager.DispatchLocationManager;
import com.xiwa.base.bean.search.ext.TextSearch;
import com.xiwa.base.manager.AbstractBasicManager;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.util.CollectionUtil;

import java.util.List;

/**
 * Created by wangj on 2015/4/21.
 */
public class SimpleDispatchLocationManager extends AbstractBasicManager implements DispatchLocationManager{

    public SimpleDispatchLocationManager(){ super(DispatchLocation.class );}

    @Override
    public DispatchLocation getLoction(String code) throws ManagerException {
        TextSearch codeSearch = new TextSearch("areaCode");
        codeSearch.setSearchValue(code);
        List<DispatchLocation> cities = this.searchIdentify(codeSearch);
        if (CollectionUtil.isValid(cities)) {
            return cities.get(0);
        }
        return null;
    }
}
