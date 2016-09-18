package com.chinaredstar.commonBiz.util;

import com.xiwa.base.cmd.SessionTimeoutException;
import com.xiwa.base.pipeline.PipelineContext;
import com.xiwa.base.util.HTTPTool;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Created by root on 12/9/14.
 */
public class SessionUtil {
    //授权目标
    protected static final String Session_Auth_Target = "session_auth_target";

    //授权后存储的自定义信息
    protected static final String SESSION_DATA_MAP = "session_data_map";
    //存储Belonged对象，是从Auth中读取的
    public static final String Session_Belonged = "session_belonged";
    //登陆Employee
    public static final String SESSION_EMPLOYEE = "session_employee";
    //登陆的Zeus
    public static final String SESSION_ZEUS = "session_zeus";
    //登陆客户
    public static final String SESSION_CUSTOMER = "session_customer";

    public static final String Session_Worker="weixin_work";

    /**
     * 获取Session Data Map
     *
     * @return
     * @throws com.xiwa.base.cmd.SessionTimeoutException
     */
    public static HashMap<String, Object> getSessionDataMap(PipelineContext context) throws SessionTimeoutException {
//        return (HashMap<String, Object>) HTTPTool.getHttpSession().getAttribute(SESSION_DATA_MAP);
        if (context.getRequest().getHttpServletRequest().getSession().getAttribute(SESSION_DATA_MAP) == null) {
            context.getRequest().getHttpServletRequest().getSession().setAttribute(SESSION_DATA_MAP, new HashMap<String, Object>());
        }
        return (HashMap<String, Object>) context.getRequest().getHttpServletRequest().getSession().getAttribute(SESSION_DATA_MAP);
    }

    /**
     * 添加Session Data Object
     *
     * @param objectKey
     * @param object
     * @throws com.xiwa.base.cmd.SessionTimeoutException
     */
    public static void addSessionDataObject(PipelineContext context, String objectKey, Object object) throws SessionTimeoutException {
        getSessionDataMap(context).put(objectKey, object);
    }

    /**
     * 获取Session Data Object
     *
     * @param objectKey
     * @return
     * @throws com.xiwa.base.cmd.SessionTimeoutException
     */
    public static Object getSessionDataObject(PipelineContext context, String objectKey) throws SessionTimeoutException {
        HashMap<String, Object> dataMap = getSessionDataMap(context);
        if (dataMap != null) {
            return dataMap.get(objectKey);
        } else {
            throw new SessionTimeoutException("error_session_timeout");
        }
    }

    /**
     * Get Session DataObject
     *
     * @param request
     * @param objectKey
     * @return
     * @throws com.xiwa.base.cmd.SessionTimeoutException
     */
    public static Object getSessionDataObject(HttpServletRequest request, String objectKey)
            throws SessionTimeoutException {
        HashMap<String, Object> dataMap =
                (HashMap<String, Object>) HTTPTool.getHttpSession(false, request).getAttribute(SESSION_DATA_MAP);
        if (dataMap != null) {
            return dataMap.get(objectKey);
        } else {
            throw new SessionTimeoutException("error_session_timeout");
        }
    }
}
