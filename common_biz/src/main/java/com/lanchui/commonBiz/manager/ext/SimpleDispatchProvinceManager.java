package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.DispatchProvince;
import com.lanchui.commonBiz.manager.DispatchProvinceManager;
import com.xiwa.base.bean.search.ext.TextSearch;
import com.xiwa.base.manager.AbstractBasicManager;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.util.CollectionUtil;

import javax.xml.soap.Text;
import java.util.List;

/**
 * Created by wangj on 2015/4/21.
 */
public class SimpleDispatchProvinceManager extends AbstractBasicManager implements DispatchProvinceManager {

    public SimpleDispatchProvinceManager() {
        super(DispatchProvince.class);
    }

    @Override
    public DispatchProvince getProvince(String provinceCode) throws ManagerException {
        TextSearch codeSearch = new TextSearch("provinceCode");
        codeSearch.setSearchValue(provinceCode);
        List<DispatchProvince> provinces = this.searchIdentify(codeSearch);
        if (CollectionUtil.isValid(provinces)) {
            return provinces.get(0);
        }
        return null;
    }
}
