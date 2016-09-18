package com.chinaredstar.longyan.util;

import com.chinaredstar.longyan.bean.constant.LanchuiConstant;
import com.xiwa.base.pipeline.Pipeline;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

/**
 * Created by wangj.
 */
public abstract class BasePipeline implements Pipeline, LanchuiConstant {
    protected static Logger logger = Logger.getLogger(BasePipeline.class);


    /**
     * get response object
     *
     * @return
     */
//    protected HttpServletResponse getResponse(PipelineContext pipelineContext) {
//        return (HttpServletResponse) pipelineContext.getContextObject(CONTEXT_HTTP_Response_Object);
//    }
    protected void _loggerRequestAndResponse(Object req, Object resp) {
        logger.error("[Request]");
        logger.error(JSONObject.fromObject(req));
        logger.error("[Response]");
        logger.error(JSONObject.fromObject(resp));
    }

}
