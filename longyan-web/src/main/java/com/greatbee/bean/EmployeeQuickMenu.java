package com.greatbee.bean;

import com.xiwa.base.bean.BelongedBean;
import com.xiwa.base.bean.Identified;
import com.xiwa.zeus.trinity.bean.CreateEmployee;

/**
 * @author CarlChen
 * @version 1.00 13-11-3 上午10:54
 */
public class EmployeeQuickMenu implements Identified,BelongedBean,CreateEmployee
{
    private int id;
    private String name;
    private String moduleName;

    private int belongedId;
    private int createEmployeeId;

    /**
     * 返回实体唯一标识
     *
     * @return 标识
     */
    public int getId()
    {
        return id;
    }

    /**
     * 隶属的对象ID
     *
     * @return
     */
    public int getBelongedId()
    {
        return belongedId;
    }

    /**
     * 设置Belonged id
     *
     * @param belongedId
     */
    public void setBelongedId(int belongedId)
    {
        this.belongedId = belongedId;
    }

    /**
     * 创建员工ID
     *
     * @return
     */
    public int getCreateEmployeeId()
    {
        return createEmployeeId;
    }

    /**
     * 创建人姓名
     *
     * @return
     */
    public String getCreateXingMing()
    {
        return null;
    }

    public String getName()
    {
        return name;
    }

    public String getModuleName()
    {
        return moduleName;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setModuleName(String moduleName)
    {
        this.moduleName = moduleName;
    }

    public void setCreateEmployeeId(int createEmployeeId)
    {
        this.createEmployeeId = createEmployeeId;
    }
}
