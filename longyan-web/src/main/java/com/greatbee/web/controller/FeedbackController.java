package com.greatbee.web.controller;

import com.greatbee.exception.BusinessException;
import com.greatbee.exception.constant.CommonExceptionType;
import com.chinaredstar.commonBiz.bean.RedstarFeedback;
import com.chinaredstar.commonBiz.bean.RedstarFeedbackType;
import com.chinaredstar.commonBiz.bean.constant.CommonBizConstant;
import com.chinaredstar.commonBiz.manager.RedstarCommonManager;
import com.xiwa.base.bean.Request;
import com.xiwa.base.bean.Response;
import com.xiwa.base.pipeline.PipelineContext;
import com.xiwa.base.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Created by lenovo on 2016/5/18.
 */
@Controller
@RequestMapping("/feedback")
public class FeedbackController extends BaseController implements CommonBizConstant {

    @Autowired
    private RedstarCommonManager redstarCommonManager;

    @RequestMapping(value = "/typeList")
    @ResponseBody
    public Response getTypeList() {
        PipelineContext pipelineContext = this.buildPipelineContent();
        Response res = pipelineContext.getResponse();
        try {
            res.addKey("result", redstarCommonManager.getDataList(RedstarFeedbackType.class, null));
            setSuccessMsg(res);
        } catch (Exception e) {
            setUnknowException(e, res);
        }
        return res;
    }


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Response create() {
        PipelineContext pipelineContext = this.buildPipelineContent();
        Response res = pipelineContext.getResponse();
        Request req = pipelineContext.getRequest();
        RedstarFeedback redstarFeedback = new RedstarFeedback();
        try {
            String content = req.getString("content");
            if (StringUtil.isValid(content) && content.trim().length() > 0) {
                redstarFeedback.setContent(content);
            } else {
                throw new BusinessException(CommonExceptionType.ParameterError);
            }
            String alias = req.getString("alias");
            if (StringUtil.isValid(alias)) {
                redstarFeedback.setFeedbackType(alias);
            }

            String phone = req.getString("phone");
            if (StringUtil.isValid(phone)) {
                redstarFeedback.setContactPhone(phone);
            }

            String name = req.getString("name");
            if (StringUtil.isValid(name)) {
                redstarFeedback.setXingMing(name);
            }
            redstarFeedback.setCreateDate(new Date());
            //添加数据
            redstarCommonManager.addData(redstarFeedback);
            setSuccessMsg(res, "反馈成功");
        } catch (BusinessException e) {
            setBusinessException(e, res);
        } catch (Exception e) {
            setUnknowException(e, res);
        }
        return res;
    }


}
