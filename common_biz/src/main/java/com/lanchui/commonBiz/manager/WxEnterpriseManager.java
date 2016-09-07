package com.lanchui.commonBiz.manager;

import com.lanchui.commonBiz.bean.DispatchWorker;
import com.lanchui.commonBiz.bean.MessageTemplate;
import com.xiwa.base.manager.BasicManager;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.pipeline.PipelineContext;
import net.sf.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 企业号
 * Created by bingcheng on 2015/4/1.
 */
public interface WxEnterpriseManager {

    /**
     * controller调用：@RequestMapping(value = "/authUrl")
     * pipelineContext 必须在controller层初始化：PipelineContext pipelineContext = buildPipelineContent();
     * 获取微信授权入口
     *
     * @return
     */
    public PipelineContext authUrl(PipelineContext pipelineContext) throws Exception;


    /**
     * 获取signature
     *
     * @param urls
     * @return
     * @throws Exception
     */
    public JSONObject getSignature(String urls) throws Exception;

    /**
     * controller层调用：@RequestMapping(value = "/getAccessToken")
     * 获取accessToken
     * 参数pipelineContext必须在controller层先初始化：PipelineContext pipelineContext = buildPipelineContent();
     *
     * @return pipelineContext
     */
    public PipelineContext getAccessToken(PipelineContext pipelineContext) throws Exception;

    /**
     * 获取授权authUrl
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    public String getAuthUrl() throws UnsupportedEncodingException;

    /**
     * 获取accessToken的url
     *
     * @return
     */
    public String getAccessTokenUrl();

    /**
     * 只需要access_token有值就可以了，不需要返回，获取access_token值
     *
     * @throws ManagerException
     */
    public void initAccessToken() throws ManagerException;

    /**
     * 获取accesstoken的值，添加点击频率校验，以免暴力请求导致sdk不能使用
     *
     * @return
     * @throws Exception
     */
    public String getAccessTokenString() throws Exception;

    /**
     * 拿到  获取用户信息接口的 url
     *
     * @param code
     * @return
     * @throws Exception
     */
    public String getUserInfoUrl(String code) throws Exception;

    /**
     * 获取用户信息
     *
     * @param code
     * @return
     * @throws Exception
     */
    public JSONObject getUserInfo(String code) throws Exception;

    /**
     * 发送微信消息
     *
     * @param weixinID
     * @param message
     * @return
     * @throws ManagerException
     */
    public JSONObject sendMessage(String weixinID, String message) throws Exception;


    /**
     * 获取用户code
     *
     * @param pipelineContext
     * @return
     */
    public String getUserId(PipelineContext pipelineContext);

    /**
     * 发送微信消息
     *
     * @param weixinId
     * @param content
     * @throws ManagerException
     */
    public void sendWxMessage(String weixinId, String content) throws Exception;

    /**
     * 根据模板发送微信消息
     *
     * @param weixinId
     * @param template
     * @param data
     * @throws ManagerException
     */
    public void sendWxMessage(String weixinId, MessageTemplate template, Map<String, Object> data) throws Exception;

    /**
     * 获取WeixinCorpID
     *
     * @return
     */
    public String getWeixinCorpID();

    /**
     * 添加企业号通讯录
     *
     * @param worker
     * @throws Exception
     */
    public void createMember(DispatchWorker worker) throws Exception;

    /**
     * 发送微信消息
     *
     * @param weixinID
     * @param message
     * @return
     * @throws ManagerException
     */
    public JSONObject sendEmployeeMessage(String weixinID, String message) throws Exception;

    /**
     * 根据模板发送微信消息
     *
     * @param weixinId
     * @param template
     * @param data
     * @throws ManagerException
     */
    public void sendEmployeeWxMessage(String weixinId, MessageTemplate template, Map<String, Object> data) throws Exception;

    /**
     * 获取企业号部门通讯录
     *
     * @throws Exception
     */
    public JSONObject getDepartMember() throws Exception;
}
