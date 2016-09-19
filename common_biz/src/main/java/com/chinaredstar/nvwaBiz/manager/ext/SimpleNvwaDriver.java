package com.chinaredstar.nvwaBiz.manager.ext;

import com.chinaredstar.nvwaBiz.manager.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by usagizhang on 16/9/19.
 */
@Component("nvwaDriver")
public class SimpleNvwaDriver implements NvwaDriver{

    @Autowired
    private NvwaSecurityOperationLogManager nvwaSecurityOperationLogManager;
    @Autowired
    private NvwaEmployeeManager nvwaEmployeeManager;
    @Autowired
    private NvwaDepartmentManager nvwaDepartmentManager;
    @Autowired
    private NvwaSecurityAuthorizedManager nvwaSecurityAuthorizedManager;
    @Autowired
    private NvwaSecurityResourceManager nvwaSecurityResourceManager;


    @Override
    public NvwaSecurityOperationLogManager getNvwaSecurityOperationLogManager() {
        return nvwaSecurityOperationLogManager;
    }

    @Override
    public NvwaEmployeeManager getNvwaEmployeeManager() {
        return nvwaEmployeeManager;
    }

    @Override
    public NvwaDepartmentManager getNvwaDepartmentManager() {
        return nvwaDepartmentManager;
    }

    @Override
    public NvwaSecurityAuthorizedManager getNvwaSecurityAuthorizedManager() {
        return nvwaSecurityAuthorizedManager;
    }

    @Override
    public NvwaSecurityResourceManager getNvwaSecurityResourceManager() {
        return nvwaSecurityResourceManager;
    }
}
