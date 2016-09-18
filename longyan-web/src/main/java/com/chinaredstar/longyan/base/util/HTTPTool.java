package com.chinaredstar.longyan.base.util;

import com.xiwa.base.cmd.SessionTimeoutException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Session Tool
 *
 * @author CarlChen
 * @version 1.00 2010-9-25 13:07:24
 */
public class HTTPTool {
    private HTTPTool() {
    }

    /**
     * Get Http Session
     *
     * @return
     */
    public static HttpSession getHttpSession() {
        return getHttpSession(false);
    }

    /**
     * Get Http Session
     *
     * @return
     */
    public static HttpSession getHttpSession(boolean isCreate) {
        return getHttpSession(isCreate, (HttpServletRequest) getServletRequest());
    }

    /**
     * Get Http Session
     *
     * @param isCreate
     * @param request
     * @return
     */
    public static HttpSession getHttpSession(boolean isCreate, HttpServletRequest request) {
        HttpSession session = null;
        if (request != null) {
            session = request.getSession(isCreate);
        }
        if (session == null) {
            throw new SessionTimeoutException("error_session_timeout");
        }
        return session;
    }

    /**
     * Get Servlet Request
     *
     * @return
     */
    public static ServletRequest getServletRequest() {
        ServletRequest request = getBindRequest();

        if (request == null) {
            try {
                request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            } catch (NullPointerException e) {
                request = null;
            }
        }

        return request;
    }

    public static final ThreadLocal<ServletRequest> reqSession = new ThreadLocal<ServletRequest>();

    /**
     * Get Bind HttpRequest
     *
     * @return
     */
    public static ServletRequest getBindRequest() {
        synchronized (reqSession) {
            return reqSession.get();
        }
    }

    /**
     * Bind Session
     *
     * @param request
     */
    public static void bindRequest(ServletRequest request) {
        synchronized (reqSession) {
            unBindRequest();
            reqSession.set(request);
        }
    }

    /**
     * Un Bind Http Session
     */
    public static void unBindRequest() {
        synchronized (reqSession) {
            reqSession.set(null);
        }
    }
}
