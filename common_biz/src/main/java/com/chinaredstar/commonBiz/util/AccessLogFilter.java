package com.chinaredstar.commonBiz.util;

import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * access log
 *
 * @author XueChao.Zhang
 * @version 1.00 14/12/2 下午4:53
 */
public class AccessLogFilter implements Filter {
    private static Logger logger = Logger.getLogger(AccessLogFilter.class);

    public void init(FilterConfig paramFilterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse rep = (HttpServletResponse) response;
        req.setCharacterEncoding("UTF-8");
        logger.info("ip-> " + req.getRemoteAddr() +
                ",path-> " + req.getRequestURI() +
                ",request data -> " + JSONObject.fromObject(req.getParameterMap()));
        logger.info("path-> " + req.getRequestURI());
        logger.info("request data-> " + JSONObject.fromObject(req.getParameterMap()));
        chain.doFilter(request, response);
    }

    public void destroy() {
    }
}
