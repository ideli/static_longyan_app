package com.lanchui.commonBiz.util;

import com.xiwa.base.pipeline.PipelineContext;
import com.xiwa.base.util.StringUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by usagizhang on 15-10-24.
 */
public class CookieUtil {
    /**
     * 根据key拿到cookie中对应的value
     *
     * @param context
     * @param key
     * @return
     */
    public static String getObjectFromCookie(PipelineContext context, String key) {
        HttpServletRequest request = context.getRequest().getHttpServletRequest();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (StringUtil.equals(cookies[i].getName(), key)) {
                    return cookies[i].getValue();
                }
            }
        }
        return null;
    }

    private static Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

    public static String getCookieValue(String key, HttpServletRequest request) {
        Map<String, Cookie> cookieMap = ReadCookieMap(request);
        if (cookieMap.containsKey(key)) {
            Cookie cookie = (Cookie) cookieMap.get(key);
            return cookie.getValue();
        } else {
            return null;
        }
    }


    public static void setCookie(String key, String value, int age, String path, HttpServletResponse response) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(age);
        cookie.setPath(path);
        response.addCookie(cookie);
    }
}
