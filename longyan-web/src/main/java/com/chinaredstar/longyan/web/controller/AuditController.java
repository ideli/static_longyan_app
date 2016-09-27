package com.chinaredstar.longyan.web.controller;

import com.chinaredstar.commonBiz.bean.RedstarEmployeeDayInput;
import com.chinaredstar.commonBiz.bean.RedstarMessageCenter;
import com.chinaredstar.commonBiz.bean.constant.CommonBizConstant;
import com.chinaredstar.commonBiz.manager.DispatchDriver;
import com.chinaredstar.commonBiz.manager.RedstarCommonManager;
import com.chinaredstar.nvwaBiz.bean.NvwaEmployee;
import com.xiwa.base.bean.Request;
import com.xiwa.base.bean.Response;
import com.xiwa.base.bean.search.ConditionType;
import com.xiwa.base.bean.search.ext.IntSearch;
import com.xiwa.base.bean.search.ext.MultiSearchBean;
import com.xiwa.base.pipeline.PipelineContext;
import com.xiwa.base.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by StevenDong on 2016/9/27
 */
@Controller
@RequestMapping(value = "/audit")
public class AuditController extends BaseController implements CommonBizConstant {

    //
    @RequestMapping(value = "/viewAuditList", method = RequestMethod.GET)
    @ResponseBody
    public Response getViewAuditList() {
        PipelineContext pipelineContext = this.buildPipelineContent();
        Response res = pipelineContext.getResponse();
        Request req = pipelineContext.getRequest();

        try {

            // 成功与否消息文字设置
            setSuccessMsg(res);
        } catch (Exception e) {
            setUnknowException(e, res);
        }
        return res;
    }


}
