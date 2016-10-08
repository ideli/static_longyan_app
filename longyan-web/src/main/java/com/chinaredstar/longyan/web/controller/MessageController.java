package com.chinaredstar.longyan.web.controller;


import com.chinaredstar.commonBiz.bean.RedstarMessageCenter;
import com.chinaredstar.commonBiz.bean.constant.CommonBizConstant;
import com.chinaredstar.commonBiz.manager.DispatchDriver;
import com.chinaredstar.longyan.exception.BasicException;
import com.chinaredstar.longyan.exception.BusinessException;
import com.chinaredstar.longyan.exception.FormException;
import com.chinaredstar.nvwaBiz.bean.NvwaEmployee;
import com.chinaredstar.nvwaBiz.manager.NvwaDriver;
import com.redstar.sms.api.AppPushService;
import com.xiwa.base.bean.PaginationDescribe;
import com.xiwa.base.bean.Response;
import com.xiwa.base.bean.search.ext.IntSearch;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.pipeline.PipelineContext;
import com.xiwa.base.util.CollectionUtil;
import com.xiwa.base.util.DataUtil;
import com.xiwa.base.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
            NvwaEmployee employee = getEmployeeromSession();
//            NvwaEmployee extEmployee = (NvwaEmployee) nvwaDriver.getNvwaEmployeeManager().getBean(employee.getId());
            String employeeCode = employee.getEmployeeCode();


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
            NvwaEmployee employee = getEmployeeromSession();
//            NvwaEmployee extEmployee = (NvwaEmployee) nvwaDriver.getNvwaEmployeeManager().getBean(employee.getId());
            String employeeCode = employee.getEmployeeCode();

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


    @RequestMapping(value = "/my-message-list", method = RequestMethod.POST)
    public Response myMessage() {
        PipelineContext pipelineContext = this.buildPipelineContent();
        Response res = pipelineContext.getResponse();


        try {
            //从session中获取员工信息
            NvwaEmployee employee = getEmployeeromSession();
            //页数
            Integer page = pipelineContext.getRequest().getInt("page");
            //每页记录数
            Integer pageSize = pipelineContext.getRequest().getInt("pageSize");
            if (page == 0) {
                page = Page_Default;
            }
            if (pageSize == 0) {
                pageSize = PageSize_Default;
            }
            IntSearch recipientIDSearch = new IntSearch("recipientID");
            recipientIDSearch.setSearchValue(StringUtil.getString(employee.getId()));
            //获取消息列表
            PaginationDescribe<RedstarMessageCenter> pageData = dispatchDriver.getRedstarMessageCenterManager().searchBeanPage(page, pageSize, recipientIDSearch, "createDate", false);
            //返回给客户端
            res.addKey("page_data", pageData);
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
            NvwaEmployee employee = getEmployeeromSession();
            //获取消息
            List<RedstarMessageCenter> list = dispatchDriver.getRedstarMessageCenterManager().getBeanListByColumn("id", message_id, "createDate", false);
            if (CollectionUtil.isValid(list)) {
                RedstarMessageCenter item = list.get(0);
                //更新消息状态
                item.setReadFlg(1);
                item.setReadDate(new Date());
                dispatchDriver.getRedstarMessageCenterManager().updateBean(item);
                //返回给客户端
            } else {
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

    /**
     * 消息推送接口
     *
     * @return
     */
    @RequestMapping(value = "/push", method = RequestMethod.POST)
    @ResponseBody
    public Response pushMessageToClient() throws ManagerException {
        PipelineContext pipelineContext = buildPipelineContent();
        //员工号
        String empCode = pipelineContext.getRequest().getString("empCode");
        //消息标题
        String title = pipelineContext.getRequest().getString("title");
        //正文 仅Android版本会显示
        String content = pipelineContext.getRequest().getString("content");
        //额外参数
        String extParm = pipelineContext.getRequest().getString("extParm");
        if (StringUtil.isInvalid(extParm)) {
            extParm = null;
        }

        // 由输入的员工部门ID查询员工信息
        NvwaEmployee extEmployee = nvwaDriver.getNvwaEmployeeManager().getEmployeeByCode(empCode);

        //插入数据到message center的表
        RedstarMessageCenter messageCenter = new RedstarMessageCenter();

        // 消息接收者的员工ID
        messageCenter.setRecipientID(extEmployee.getId());
        // 消息接收者的员工姓名
        messageCenter.setRecipientName(extEmployee.getUserName());
        // 消息标题
        messageCenter.setMessageTitle(title);
        // 消息内容
        messageCenter.setMessageContent(content);
        // 消息读取完成标志位（0=未读取，1=读取完成）
        messageCenter.setReadFlg(0);
        // 消息类型（push=推送消息）
        messageCenter.setMessageType("push");
        // 消息来源（0=龙眼外部消息源，1=龙眼消息源）
        messageCenter.setMessageSource("1");
        // 消息创建时间
        messageCenter.setCreateDate(new Date());

        dispatchDriver.getRedstarMessageCenterManager().addBean(messageCenter);

        //调用dubbo接口发送消息
        if (StringUtil.isValid(empCode)) {
            pipelineContext.getResponse().getDataMap().putAll(appPushService.sendPush(title, content, "LY", empCode, null, 0, extParm));
        }
        return pipelineContext.getResponse();
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
