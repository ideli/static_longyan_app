package com.greatbee.security.interceptor;

import com.greatbee.util.ReFreshTokenUtil;
import com.greatbee.util.SessionInitUtil;
import com.greatbee.util.SpringUtil;
import com.lanchui.commonBiz.bean.RedstarSession;
import com.lanchui.commonBiz.manager.DispatchDriver;
import com.lanchui.commonBiz.manager.RedstarSessionManager;
import com.lanchui.commonBiz.util.CookieUtil;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.xiwa.base.bean.Constants;
import com.xiwa.base.bean.ext.SimpleResponse;
import com.xiwa.base.cmd.SessionTimeoutException;
import com.xiwa.base.util.StringUtil;
import com.xiwa.security.driver.SecurityDriver;
import com.xiwa.security.web.common.SecurityTool;
import com.xiwa.zeus.trinity.manager.TrinityDriver;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.support.AdvanceActionSupport;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author CarlChen
 * @version 1.00 2012-3-17 19:38:06
 */
public class AuthenticationInterceptor extends AbstractInterceptor {
    protected static Logger logger = Logger.getLogger(AuthenticationInterceptor.class);

    private static final String SsoAuthBean = "ssoAuth";

 /*   @Autowired
    protected SecurityDriver securityDriver;

    @Autowired
    private  TrinityDriver trinityDriver;

    @Autowired
    private DispatchDriver dispatchDriver;*/


    /**
     * 检查权限
     *
     * @param actionInvocation
     * @return
     * @throws Exception
     */
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        try {

            HttpServletRequest request = ServletActionContext.getRequest();
            HttpServletResponse response = ServletActionContext.getResponse();
            ApplicationContext context =SpringUtil.getContext();

            ReFreshTokenUtil.refreshToken(context,request,response);

            boolean isLogin = SecurityTool.isSecurityLogin(null);
            if (isLogin) {
                return actionInvocation.invoke();
            } else {
                /*
                //手机第一次访问，需要获取Session
                ApplicationContext context =
                    WebApplicationContextUtils.getWebApplicationContext(ServletActionContext.getServletContext());
                try {
                    ((SSOAuth)context.getBean(SsoAuthBean)).ssoAuth();
                    return actionInvocation.invoke();
                }
                catch (Exception e) {
                    logger.error(e.getMessage(),e);
                    throw new SessionTimeoutException("error_session_timeout",e);
                }
                */

                        //WebApplicationContextUtils.getWebApplicationContext(ServletActionContext.getServletContext());
                String token = CookieUtil.getCookieValue("_token", request);
                if (StringUtil.isValid(token)) {
                    //有token
                    RedstarSession session = ((RedstarSessionManager) context.getBean("redstarSessionManager")).getActivatedSession(token);
                    if (session != null) {
                        //token
                        //获取Authorized对象
                        //调用初始化session的工具类函数

                        SecurityDriver securityDriver = (SecurityDriver) context.getBean("securityDriver");

                        TrinityDriver trinityDriver = (TrinityDriver) context.getBean("trinityDriver");

                        DispatchDriver dispatchDriver = (DispatchDriver) context.getBean("dispatchDriver");

                        SessionInitUtil.sessionInit(securityDriver,trinityDriver,dispatchDriver,session,response);

                        return actionInvocation.invoke();
                    }
                }

                throw new SessionTimeoutException("error_session_timeout");
            }
        } catch (SessionTimeoutException e) {
            SimpleResponse response = new SimpleResponse();
            response.setOk(false);
            response.setMessage("session time out");
            response.setCode(Constants.Error_Code_No_Session);

            ((AdvanceActionSupport) actionInvocation.getAction()).setResponse(response);
            return "no_auth";
        }
    }
}
