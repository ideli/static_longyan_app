package com.chinaredstar.longyan.bean.constant;

/**
 * Portlet类型
 *
 * @author CarlChen
 * @version 1.00 13-1-23 下午5:14
 */
public enum PortletType
{
    Module("module");

    private String type;

    private PortletType(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }
}
