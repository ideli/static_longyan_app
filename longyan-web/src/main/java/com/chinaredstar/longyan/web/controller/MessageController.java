package com.chinaredstar.longyan.web.controller;


import com.chinaredstar.nvwaBiz.bean.NvwaEmployee;
import com.chinaredstar.commonBiz.bean.constant.CommonBizConstant;
import com.chinaredstar.commonBiz.manager.DispatchDriver;
import com.chinaredstar.nvwaBiz.manager.NvwaDriver;
import com.redstar.sms.api.AppPushService;
import com.xiwa.base.bean.Response;
import com.xiwa.base.pipeline.PipelineContext;
import com.xiwa.base.util.StringUtil;
import com.xiwa.zeus.trinity.bean.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by niu on 2016/6/8.
 */
@RestController
@RequestMapping("/message")
public class MessageController extends BaseController implements CommonBizConstant {

    @Autowired
    private DispatchDriver dispatchDriver;
    @Autowired
    private NvwaDriver nvwaDriver;


    @Autowired
    private AppPushService appPushService;


    @RequestMapping(value = "/app/register", method = RequestMethod.POST)
    public Response register(String platformType, String tag) {
        PipelineContext pipelineContext = this.buildPipelineContent();
        Response res = pipelineContext.getResponse();

        if (StringUtil.isInvalid(platformType)) {
            setErrorMessage(res, "平台类型必填");
            return res;
        }
        if ((!platformType.equals(Android)) && (!platformType.equals(Ios))) {
            setErrorMessage(res, "平台类型错误");
            return res;
        }

        if (StringUtil.isInvalid(tag)) {
            setErrorMessage(res, "tag必填");
            return res;
        }
        try {
            Employee employee = getEmployeeromSession();
            NvwaEmployee extEmployee = (NvwaEmployee) nvwaDriver.getNvwaEmployeeManager().getBean(employee.getId());
            String employeeCode = extEmployee.getEmployeeCode();


            Map<String, Object> result = appPushService.registerPush(AppCode, tag, employeeCode, platformType);

            if (Integer.parseInt(result.get("code").toString()) == 0) {
                setSuccessMessage(res, result.get("message").toString());
            } else {
                setErrorMessage(res, result.get("message").toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            setErrorMessage(res, "服务器响应异常");
        }
        return res;
    }


    @RequestMapping(value = "/app/destroy", method = RequestMethod.POST)
    public Response destroy(String platformType, String tag) {
        PipelineContext pipelineContext = this.buildPipelineContent();
        Response res = pipelineContext.getResponse();

        if (StringUtil.isInvalid(platformType)) {
            setErrorMessage(res, "平台类型必填");
            return res;
        }
        if ((!platformType.equals(Android)) && (!platformType.equals(Ios))) {
            setErrorMessage(res, "平台类型错误");
            return res;
        }

        if (StringUtil.isInvalid(tag)) {
            setErrorMessage(res, "tag必填");
            return res;
        }
        try {
            Employee employee = getEmployeeromSession();
            NvwaEmployee extEmployee = (NvwaEmployee) nvwaDriver.getNvwaEmployeeManager().getBean(employee.getId());
            String employeeCode = extEmployee.getEmployeeCode();

            Map<String, Object> result = appPushService.destroyPush(AppCode, employeeCode, platformType);
            if (Integer.parseInt(result.get("code").toString()) == 0) {
                setSuccessMessage(res, result.get("message").toString());
            } else {
                setErrorMessage(res, result.get("message").toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            setErrorMessage(res, "服务器响应异常");
        }
        return res;
    }


    private Response setErrorMessage(Response response, String msg) {
        response.setMessage(msg);
        response.setCode(-1001);
        response.setOk(Boolean.FALSE);
        return response;
    }

    private Response setSuccessMessage(Response response, String msg) {
        response.setMessage(msg);
        response.setCode(0);
        response.setOk(Boolean.TRUE);
        return response;
    }

}
