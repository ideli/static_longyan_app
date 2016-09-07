package com.lanchui.commonBiz.manager;


import com.xiwa.base.manager.BasicManager;
import com.xiwa.base.manager.ManagerException;

import java.util.List;

/**
 * Created by mdc on 2016/6/29.
 */
public interface RedstarStoreManager extends BasicManager {
    List calDetail(int storeId) throws ManagerException;
}
