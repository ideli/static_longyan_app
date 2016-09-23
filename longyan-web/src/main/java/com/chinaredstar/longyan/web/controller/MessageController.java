package com.chinaredstar.longyan.web.controller;


import com.chinaredstar.commonBiz.bean.RedstarMessageCenter;
import com.chinaredstar.longyan.exception.BasicException;
import com.chinaredstar.longyan.exception.BusinessException;
import com.chinaredstar.longyan.exception.FormException;
import com.chinaredstar.longyan.exception.NoSessionException;
import com.chinaredstar.nvwaBiz.bean.NvwaEmployee;
import com.chinaredstar.commonBiz.bean.constant.CommonBizConstant;
import com.chinaredstar.commonBiz.manager.DispatchDriver;
import com.chinaredstar.nvwaBiz.manager.NvwaDriver;
import com.redstar.sms.api.AppPushService;
import com.xiwa.base.bean.Response;
import com.xiwa.base.pipeline.PipelineContext;
import com.xiwa.base.util.CollectionUtil;
import com.xiwa.base.util.DataUtil;
import com.xiwa.base.util.StringUtil;
import com.xiwa.zeus.trinity.bean.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
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


    @RequestMapping(value = "/my-message", method = RequestMethod.POST)
    public Response myMessage() {
        PipelineContext pipelineContext = this.buildPipelineContent();
        Response res = pipelineContext.getResponse();


        try {
            //从session中获取员工信息
            Employee employee = getEmployeeromSession();
            //获取消息列表
            List<RedstarMessageCenter> list= dispatchDriver.getRedstarMessageCenterManager().getBeanListByColumn("recipientID", employee.getId(), "createDate", false);
            //返回给客户端
            res.addKey("message",list);
        } catch (Exception e) {
            e.printStackTrace();
            setErrorMessage(res, "服务器响应异常");
        }
        return res;
    }

    @RequestMapping(value = "/read-message", method = RequestMethod.POST)
    public Response readMessage(int message_id) {
        PipelineContext pipelineContext = this.buildPipelineContent();
        Response res = pipelineContext.getResponse();

        try {
            if (message_id < 1) {
                throw new FormException("消息ID不能为空");
            }
            //从session中获取员工信息
            Employee employee = getEmployeeromSession();
            //获取消息
            List<RedstarMessageCenter> list= dispatchDriver.getRedstarMessageCenterManager().getBeanListByColumn("id", message_id, "createDate", false);
            if(CollectionUtil.isValid(list)){
                RedstarMessageCenter item=list.get(0);
                //更新消息状态
                item.setReadFlg(1);
                item.setReadDate(new Date());
                dispatchDriver.getRedstarMessageCenterManager().updateBean(item);
                //返回给客户端
            }else{
                //消息不存在
                throw new BusinessException("消息不存在");
            }
        } catch (BasicException e) {
            res.setOk(false);
            res.setCode(DataUtil.getInt(e.getErrorCode(), 0));
            res.setMessage(e.getMessage());
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
