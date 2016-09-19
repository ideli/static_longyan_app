package com.chinaredstar.nvwaBiz.manager;

/**
 * Created by usagizhang on 16/9/19.
 */
public interface NvwaDriver {

    public NvwaSecurityOperationLogManager getNvwaSecurityOperationLogManager();

    public NvwaEmployeeManager getNvwaEmployeeManager();

    public NvwaDepartmentManager getNvwaDepartmentManager();

    public NvwaSecurityAuthorizedManager getNvwaSecurityAuthorizedManager();

    public NvwaSecurityResourceManager getNvwaSecurityResourceManager();


}
