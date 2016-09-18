package com.greatbee.security.util;

import com.greatbee.base.util.HTTPTool;
import com.chinaredstar.commonBiz.bean.RedstarSession;
import com.chinaredstar.commonBiz.manager.RedstarSessionManager;
import com.chinaredstar.commonBiz.util.CookieUtil;
import com.xiwa.base.bean.Identified;
import com.xiwa.base.cmd.SessionTimeoutException;
import com.xiwa.base.components.bean.Node;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.util.CollectionUtil;
import com.xiwa.base.util.RandomGUIDUtil;
import com.xiwa.base.util.StringUtil;
import com.xiwa.security.bean.Resource;
import com.xiwa.security.bean.constant.AuthTarget;
import com.xiwa.security.bean.constant.ResourceType;
import com.xiwa.zeus.trinity.bean.Employee;
import com.xiwa.zeus.util.SessionTool;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Security Tool
 *
 * @author CarlChen
 * @version 1.00 2011-2-11 15:40:23
 */
public class SecurityTool extends SessionTool {
    private static final String _Default_Skin = "metro";
    private static final String _Default_Lang = "zh_CN";
    public static final String Session_Skin = "session_skin";
    public static final String Session_Lang = "session_lang";

    /**
     * 读取用户皮肤
     *
     * @param request
     * @return
     */
    public static final String getUserSkin(HttpServletRequest request) {
        HashMap<String, Object> dataMap = (HashMap<String, Object>) request.getSession().getAttribute(SESSION_DATA_MAP);
        if (dataMap != null) {
            String skin = (String) dataMap.get(Session_Skin);

            return StringUtil.isValid(skin) ? skin : _Default_Skin;
        } else {
            return _Default_Skin;
        }
    }

    /**
     * 用户是否是系统默认语言
     *
     * @param request
     * @return
     */
    public static boolean isUserSystemDefaultLang(HttpServletRequest request) {
        return _Default_Lang.equalsIgnoreCase(getUserLang(request));
    }

    /**
     * 用户语言
     *
     * @param request
     * @return
     */
    public static final String getUserLang(HttpServletRequest request) {
        HashMap<String, Object> dataMap = (HashMap<String, Object>) request.getSession().getAttribute(SESSION_DATA_MAP);
        if (dataMap != null) {
            String lang = (String) dataMap.get(Session_Lang);

            return StringUtil.isValid(lang) ? lang : _Default_Lang;
        } else {
            return _Default_Lang;
        }
    }

    /**
     * 读取登陆对象
     *
     * @return
     */
    public static Identified getLoginTarget() {
        AuthTarget authTarget = getSessionAuthTarget();
        if (AuthTarget.Employee.equals(authTarget)) {
            return (Identified) SessionTool.getSessionDataObject(SessionTool.SESSION_EMPLOYEE);
        } else if (AuthTarget.Customer.equals(authTarget)) {
            return (Identified) SessionTool.getSessionDataObject(SessionTool.SESSION_CUSTOMER);
        } else if (AuthTarget.Zeus.equals(authTarget)) {
            return (Identified) SessionTool.getSessionDataObject(SessionTool.SESSION_ZEUS);
        }
        return null;
    }

    /**
     * 初始化Session
     */
    public static void initSecuritySession(AuthTarget authTarget, int belongedId, List<Resource> resources,
                                           Object loginTarget, int objectId,String openId,String userToken,String refreshToken,RedstarSessionManager sessionManager, HttpServletResponse resp) throws ManagerException {
        HttpSession session = HTTPTool.getHttpSession(true);
        session.removeAttribute(Session_Auth_Target);
        session.removeAttribute(SESSION_RESOURCES);
        session.removeAttribute(SESSION_DATA_MAP);

        session.setAttribute(Session_Auth_Target, authTarget);
        session.setAttribute(SESSION_DATA_MAP, new HashMap<String, Object>());


        addObjectToSession(SessionTool.Session_Belonged, belongedId);

        if (AuthTarget.Employee.equals(authTarget)) {
            addObjectToSession(SessionTool.SESSION_EMPLOYEE, loginTarget);

            //皮肤
            String skin = ((Employee) loginTarget).getSkin();
            if (StringUtil.isInvalid(skin)) {
                skin = _Default_Skin;
            }
            addObjectToSession(Session_Skin, skin);

            //语言
            String lang = ((Employee) loginTarget).getLang();
            if (StringUtil.isInvalid(lang)) {
                lang = _Default_Lang;
            }
            addObjectToSession(Session_Lang, lang);
        } else if (AuthTarget.Zeus.equals(authTarget)) {
            addObjectToSession(SessionTool.SESSION_ZEUS, loginTarget);
        } else if (AuthTarget.Customer.equals(authTarget)) {
            addObjectToSession(SessionTool.SESSION_CUSTOMER, loginTarget);
        } else if (AuthTarget.Weixin.equals(authTarget)) {
            addObjectToSession(SessionTool.SESSION_CUSTOMER, loginTarget);
        } else if (AuthTarget.Merchant.equals(authTarget)) {
            addObjectToSession(SessionTool.SESSION_MERCHANT, loginTarget);
        } else if (AuthTarget.Community.equals(authTarget)) {
            addObjectToSession(SessionTool.SESSION_Community, loginTarget);
        } else if (AuthTarget.Shop.equals(authTarget)) {
            addObjectToSession(SessionTool.SESSION_Shop, loginTarget);
        }

        if(StringUtil.isValid(openId)) {
            //是否存在
            //RedstarSession redstarSession = sessionManager.getActivatedSessio(openId);
            String token = RandomGUIDUtil.getRawGUID().replace("-", "");

            //if (null == redstarSession) {
            RedstarSession  redstarSession = new RedstarSession();
                redstarSession.setCreateDate(new Date(System.currentTimeMillis()));
                //session有效期=1800秒
                redstarSession.setExpiredDate(new Date(System.currentTimeMillis() + 1800 * 1000));
                redstarSession.setActivated(true);
                redstarSession.setLoginTarget(authTarget.getTarget());
                redstarSession.setObjectId(objectId);
                redstarSession.setToken(token);
                redstarSession.setOpenId(openId);
                redstarSession.setUserToken(userToken);
                redstarSession.setUserRefreshToken(refreshToken);
                long time = System.currentTimeMillis() + 7200 * 1000;
                CookieUtil.setCookie("_tokenExpiredDate", String.valueOf(time), 30*24*60*60, "/", resp);
                redstarSession.setUserTokenExpiredDate(new Date(time));
                sessionManager.createSession(redstarSession);
            //}
/*            else{
                //session有效期=1800秒
                redstarSession.setExpiredDate(new Date(System.currentTimeMillis() + 1800 * 1000));
                redstarSession.setToken(token);
                redstarSession.setUserToken(userToken);
                redstarSession.setUserRefreshToken(refreshToken);
                redstarSession.setUserTokenExpiredDate(new Date(System.currentTimeMillis() + 7200 * 1000));
                sessionManager.updateBean(redstarSession);
            }*/

            CookieUtil.setCookie("_token", token, 30*24*60*60, "/", resp);
        }
        if(StringUtil.isValid(openId)){
            CookieUtil.setCookie("_openId", openId, 30*24*60*60, "/", resp);
        }
        session.setAttribute(SESSION_RESOURCES, resources);
    }


    /**
     * 初始化Session
     * 2016.7.29 新版本
     */
    public static void initSecuritySession2(AuthTarget authTarget, int belongedId, List<Resource> resources,
                                           Object loginTarget, int objectId,RedstarSessionManager sessionManager, HttpServletResponse resp,String userId) throws ManagerException {
        HttpSession session = HTTPTool.getHttpSession(true);
        session.removeAttribute(Session_Auth_Target);
        session.removeAttribute(SESSION_RESOURCES);
        session.removeAttribute(SESSION_DATA_MAP);

        session.setAttribute(Session_Auth_Target, authTarget);
        session.setAttribute(SESSION_DATA_MAP, new HashMap<String, Object>());


        addObjectToSession(SessionTool.Session_Belonged, belongedId);

        if (AuthTarget.Employee.equals(authTarget)) {
            addObjectToSession(SessionTool.SESSION_EMPLOYEE, loginTarget);

            //皮肤
            String skin = ((Employee) loginTarget).getSkin();
            if (StringUtil.isInvalid(skin)) {
                skin = _Default_Skin;
            }
            addObjectToSession(Session_Skin, skin);

            //语言
            String lang = ((Employee) loginTarget).getLang();
            if (StringUtil.isInvalid(lang)) {
                lang = _Default_Lang;
            }
            addObjectToSession(Session_Lang, lang);
        } else if (AuthTarget.Zeus.equals(authTarget)) {
            addObjectToSession(SessionTool.SESSION_ZEUS, loginTarget);
        } else if (AuthTarget.Customer.equals(authTarget)) {
            addObjectToSession(SessionTool.SESSION_CUSTOMER, loginTarget);
        } else if (AuthTarget.Weixin.equals(authTarget)) {
            addObjectToSession(SessionTool.SESSION_CUSTOMER, loginTarget);
        } else if (AuthTarget.Merchant.equals(authTarget)) {
            addObjectToSession(SessionTool.SESSION_MERCHANT, loginTarget);
        } else if (AuthTarget.Community.equals(authTarget)) {
            addObjectToSession(SessionTool.SESSION_Community, loginTarget);
        } else if (AuthTarget.Shop.equals(authTarget)) {
            addObjectToSession(SessionTool.SESSION_Shop, loginTarget);
        }

        if (StringUtil.isValid(userId)){
            String token = RandomGUIDUtil.getRawGUID().replace("-", "");
            RedstarSession  redstarSession = new RedstarSession();
            redstarSession.setCreateDate(new Date());
            redstarSession.setActivated(true);
            redstarSession.setLoginTarget(authTarget.getTarget());
            redstarSession.setObjectId(objectId);
            redstarSession.setToken(token);
            redstarSession.setOpenId(userId);
            sessionManager.createSession(redstarSession);
            CookieUtil.setCookie("_token", token, 30*24*60*60, "/", resp);
        }

        session.setAttribute(SESSION_RESOURCES, resources);
    }

    /**
     * 销毁Session
     */
    public static void destroySecuritySession() {
        HttpSession session = null;
        try {
            session = HTTPTool.getHttpSession();
            if (session != null) {
                session.removeAttribute(Session_Auth_Target);
                session.removeAttribute(SESSION_RESOURCES);
                session.removeAttribute(SESSION_DATA_MAP);
                session.invalidate();
            }
        } catch (SessionTimeoutException e) {
            e.printStackTrace();
        }
    }

    /**
     * Security�Ƿ��¼
     *
     * @return
     */
    public static boolean isSecurityLogin(AuthTarget authTarget) {
        try {
            AuthTarget sessionTarget = getSessionAuthTarget();
            return sessionTarget != null && (authTarget == null || sessionTarget.equals(authTarget));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 读取授权对象
     *
     * @return
     * @throws SessionTimeoutException
     */
    public static AuthTarget getSessionAuthTarget() throws SessionTimeoutException {
        AuthTarget authTarget = (AuthTarget) HTTPTool.getHttpSession(false).getAttribute(Session_Auth_Target);
        if (authTarget == null) {
            throw new SessionTimeoutException("error_session_timeout");
        }
        return authTarget;
    }

    /**
     * 读取资源
     *
     * @param resourceUrl
     * @param session
     * @return
     */
    public static Resource getResource(String resourceUrl, HttpSession session) {
        List<Resource> resourceList = getSessionResources(session);
        for (Resource r : resourceList) {
            if (resourceUrl.equalsIgnoreCase(r.getResourceUrl())) {
                return r;
            }
        }
        return null;
    }

    /**
     * 读取资源
     *
     * @param resourceId
     * @param session
     * @return
     */
    public static Resource getResource(int resourceId, HttpSession session) {
        List<Resource> resourceList = getSessionResources(session);
        for (Resource r : resourceList) {
            if (r.getId() == resourceId) {
                return r;
            }
        }
        return null;
    }

    /**
     * 读取元素
     *
     * @param resourceUrl
     * @return
     */
    public static Resource getResource(String resourceUrl) {
        return getResource(resourceUrl, HTTPTool.getHttpSession(false));
    }

    /**
     * 读取资源
     *
     * @param resourceId
     * @return
     */
    public static Resource getResource(int resourceId) {
        return getResource(resourceId, HTTPTool.getHttpSession(false));
    }

    /**
     * 获取子资源
     *
     * @return
     */
    public static List<Resource> getChildResource(int parentId) {
        List<Resource> childResources = new ArrayList<Resource>();

        List<Resource> resourceList = getSessionResources(HTTPTool.getHttpSession(false));
        for (Resource r : resourceList) {
            Node parent = r.getParentNode();
            if (parent != null && parent.getId() == parentId) {
                childResources.add(r);
            }
        }
        return childResources;
    }

    /**
     * ��ȡ�滭�е���Դ�б�
     *
     * @return
     * @throws SessionTimeoutException
     */
    public static List<Resource> getSessionResourcesByType(ResourceType rt)
            throws SessionTimeoutException {
        return getSessionResourcesByType(HTTPTool.getHttpSession(false), rt);
    }

    /**
     * ��ȡ�滭�е���Դ�б�
     *
     * @param session
     * @return
     * @throws SessionTimeoutException
     */
    public static List<Resource> getSessionResourcesByType(HttpSession session, ResourceType rt)
            throws SessionTimeoutException {
        List<Resource> resourceList = getSessionResources(session);

        List<Resource> typeResource = new ArrayList<Resource>();
        for (Resource r : resourceList) {
            ResourceType rType = r.getResourceType();
            if (rType != null && rType.equals(rt)) {
                typeResource.add(r);
            }
        }
        return typeResource;
    }

    /**
     * 获取session员工对象
     *
     * @param session
     * @return
     */
    public static Employee getSessionEmployee(HttpSession session) {
        Employee employee = (Employee) session.getAttribute(SESSION_EMPLOYEE);
        return employee;
    }

    /**
     * 获取Session资源
     *
     * @param session
     * @return
     */
    public static List<Resource> getSessionResources(HttpSession session) {
        List<Resource> resourceList = (List<Resource>) session.getAttribute(SESSION_RESOURCES);
        if (CollectionUtil.isInvalid(resourceList)) {
            throw new SessionTimeoutException("error_session_timeout");
        }
        return resourceList;
    }

    /**
     * 设置资源
     *
     * @param session
     * @param resourceList
     */
    public static void setSessionResources(HttpSession session, List<Resource> resourceList) {
        session.setAttribute(SESSION_RESOURCES, resourceList);
    }

    /**
     * 设置session 值
     *
     * @param key
     * @param value
     * @throws ManagerException
     */
    protected static void addObjectToSession(String key, Object value) throws ManagerException {
        HttpServletRequest request = _getHttpServletRequest();
        if (request.getSession().getAttribute("session_data_map") == null) {
            request.getSession().setAttribute("session_data_map", new HashMap<String, Object>());
        }
        Map<String, Object> map = (Map<String, Object>) request.getSession().getAttribute("session_data_map");
        map.put(key, value);
    }

    protected static HttpServletRequest _getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    protected static HttpServletResponse _getHttpServletResponse() {
        return ((ServletWebRequest) RequestContextHolder.getRequestAttributes()).getResponse();
    }
}
