package com.chinaredstar.commonBiz.util;

import com.xiwa.base.pipeline.PipelineContext;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by usagizhang on 15-10-24.
 */
public class HeaderUtil {
    /**
     * 根据key拿到header中对应的value
     *
     * @param context
     * @param key
     * @return
     */
    public static String getObjectFromHeader(PipelineContext context, String key) {
        HttpServletRequest request = context.getRequest().getHttpServletRequest();
        return request.getHeader(key);
    }
}
