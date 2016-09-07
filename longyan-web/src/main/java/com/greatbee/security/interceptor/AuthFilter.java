package com.greatbee.security.interceptor;

import com.lanchui.commonBiz.bean.RedstarSession;
import com.lanchui.commonBiz.manager.DispatchDriver;
import com.lanchui.commonBiz.util.CookieUtil;
import com.xiwa.base.bean.Constants;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.util.StringUtil;
import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by usagizhang on 16-4-19.
 */
public class AuthFilter extends StrutsPrepareAndExecuteFilter implements Constants {

    @Autowired
    DispatchDriver dispatchDriver;

    /**
     * Do Filter
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws java.io.IOException
     * @throws ServletException
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        String token = CookieUtil.getCookieValue("_token", (HttpServletRequest) request);
        if (StringUtil.isValid(token)) {
            try {
                RedstarSession session = dispatchDriver.getRedstarSessionManager().getActivatedSession(token);
                if (session != null) {

                }
            } catch (ManagerException e) {
                e.printStackTrace();
            }
        }
        super.doFilter(request, response, filterChain);
    }

    public void destroy() {
        super.destroy();
    }
}
