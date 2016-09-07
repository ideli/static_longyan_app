package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.DispatchWorker;
import com.lanchui.commonBiz.bean.MessageTemplate;
import com.lanchui.commonBiz.bean.constant.CommonBizConstant;
import com.lanchui.commonBiz.bean.constant.WeixinConstant;
import com.lanchui.commonBiz.manager.WxEnterpriseManager;
import com.lanchui.commonBiz.util.HttpClientUtil;
import com.lanchui.commonBiz.util.MessageTemplateUtil;
import com.xiwa.base.manager.AbstractBasicManager;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.pipeline.PipelineContext;
import com.xiwa.base.util.Charset;
import com.xiwa.base.util.CollectionUtil;
import com.xiwa.base.util.StringUtil;
import freemarker.template.TemplateException;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * 企业号
 * Created by bingcheng on 2015/4/1.
 */
public class SimpleWxEnterpriseManager implements WxEnterpriseManager, CommonBizConstant, WeixinConstant {

    private final static Logger logger = Logger.getLogger(SimpleWxEnterpriseManager.class);

    private String weixinOpenHost;
    private String weixinQyHost;
    private String weixinCorpID;
    private String weixinSecret;
    private String initSessionRedirectUri;
    private int appid;
    private int department;
    private int employeeAppid;
    private boolean weixinToggle;

    private String access_token;
    private long lastUpdateToken = 0;
    private long updateInterval = 1000 * 7200;

    /**
     * controller调用：@RequestMapping(value = "/authUrl")
     * pipelineContext 必须在controller层初始化：PipelineContext pipelineContext = buildPipelineContent();
     * 获取微信授权入口
     *
     * @return
     */
    @Override
    public PipelineContext authUrl(PipelineContext pipelineContext) throws Exception {
        if (pipelineContext == null) {
            throw new ManagerException("获取authUrl失败！");
        }
        pipelineContext.getResponse().addKey("authUrl", this.getAuthUrl());
        return pipelineContext;
    }


    /**
     * controller层调用：@RequestMapping(value = "/getSignature")
     * <p/>
     * 获取signature
     *
     * @return pipelineContext
     */
    @Override
    public JSONObject getSignature(String signUrl) throws Exception {

        //get ticket
        String jsapi_ticket = this._getTicket();
        logger.info("[getSignature]jsapi_ticket" + jsapi_ticket);
        //get sign
        String nonce_str = _createNonceStr();
        String timestamp = _createTimestamp();
        String string1;
        String signature = "";
        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + signUrl;
        logger.info("[getSignature]signUrl=" + signUrl);

        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            //hex
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logger.error(e);
            logger.error(e.getMessage());
            logger.error(e.toString());
            throw new Exception(e.getMessage());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            logger.error(e);
            logger.error(e.getMessage());
            logger.error(e.toString());
            throw new Exception(e.getMessage());
        }

        JSONObject sign = new JSONObject();
        sign.put("url", signUrl);
        sign.put("jsapi_ticket", jsapi_ticket);
        sign.put("nonceStr", nonce_str);
        sign.put("timestamp", timestamp);
        sign.put("signature", signature);
        sign.put("appId", this.getWeixinCorpID());
        logger.info("[getSignature]signature=" + sign.toString());
        return sign;

    }

    /**
     * controller层调用：@RequestMapping(value = "/getAccessToken")
     * 获取accessToken
     * 参数pipelineContext必须在controller层先初始化：PipelineContext pipelineContext = buildPipelineContent();
     *
     * @return pipelineContext
     */
    @Override
    public PipelineContext getAccessToken(PipelineContext pipelineContext) throws Exception {
        pipelineContext.getResponse().addKey("access_token", this.getAccessTokenString());
        return pipelineContext;
    }

    /**
     * 获取授权authUrl
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    @Override
    public String getAuthUrl() throws UnsupportedEncodingException {
        StringBuilder urlBuilder = new StringBuilder(this.getWeixinOpenHost());
        urlBuilder.append("/connect/oauth2/authorize?");
        urlBuilder.append("appid=").append(this.getWeixinCorpID());
        urlBuilder.append("&redirect_uri=").append(URLEncoder.encode(this.getInitSessionRedirectUri(), Charset.UTF_8));
        urlBuilder.append("&response_type=").append(response_type);
        urlBuilder.append("&scope=").append(scope);
        urlBuilder.append("&state=").append(state);
        urlBuilder.append("#wechat_redirect");
        return urlBuilder.toString();
    }

    /**
     * 获取accessToken的url
     *
     * @return
     */
    @Override
    public String getAccessTokenUrl() {
        StringBuilder urlBuilder = new StringBuilder(this.getWeixinQyHost());
        urlBuilder.append("/cgi-bin/gettoken?");
        urlBuilder.append("corpid=").append(this.getWeixinCorpID());
        urlBuilder.append("&corpsecret=").append(this.getWeixinSecret());
        return urlBuilder.toString();
    }

    /**
     * 只需要access_token有值就可以了，不需要返回，获取access_token值
     *
     * @throws ManagerException
     */
    @Override
    public void initAccessToken() throws ManagerException {
        String url = this.getAccessTokenUrl();
        String httpResponse = HttpClientUtil.get(url);
        if (StringUtil.isInvalid(httpResponse)) {
            logger.error("[SimpleWxEnterpriseManager][initAccessToken][error] httpResponse is invalid!");
        }
        JSONObject jsonObject = JSONObject.fromObject(httpResponse);
        if (jsonObject.containsKey("access_token")) {
            access_token = jsonObject.getString("access_token");
            //更新lastupdate时间戳,以免暴力请求导致sdk不能使用
            lastUpdateToken = System.currentTimeMillis();
            System.out.println(access_token);
        }
    }


    /**
     * 获取accesstoken的值，添加点击频率校验，以免暴力请求导致sdk不能使用
     *
     * @return
     * @throws Exception
     */
    @Override
    public String getAccessTokenString() throws Exception {
        if (accessTokenInvalid()) {
            initAccessToken();
        }
        return access_token;
    }

    /**
     * 拿到  获取用户信息接口的 url
     *
     * @param code
     * @return
     * @throws Exception
     */
    @Override
    public String getUserInfoUrl(String code) throws Exception {
        StringBuilder urlBuilder = new StringBuilder(this.getWeixinQyHost());
        urlBuilder.append("/cgi-bin/user/getuserinfo?");
        urlBuilder.append("access_token=").append(this.getAccessTokenString());
        urlBuilder.append("&code=").append(code);
        urlBuilder.append("&agentid=").append(this.getAppid());
        return urlBuilder.toString();
    }

    /**
     * 获取用户信息
     *
     * @param code
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject getUserInfo(String code) throws Exception {
        String url = getUserInfoUrl(code);
        String httpResponse = HttpClientUtil.get(url);
        if (StringUtil.isInvalid(httpResponse)) {
            logger.error("[SimpleWxEnterpriseManager][getUserInfo][error] httpResponse is invalid!");
        }
        JSONObject jsonObject = JSONObject.fromObject(httpResponse);
        return jsonObject;
    }

    /**
     * 发送微信消息
     *
     * @param weixinID
     * @param message
     * @return
     * @throws ManagerException
     */
    @Override
    public JSONObject sendMessage(String weixinID, String message) throws Exception {
        logger.info("[sendMessage][request]weixinID=" + weixinID + ",message=" + message);
        String accessToken = this.getAccessTokenString();
        logger.info("[sendMessage]accessToken=" + accessToken);
        //build url
        StringBuilder urlBuilder = new StringBuilder(this.getWeixinQyHost());
        urlBuilder.append("/cgi-bin/message/send?");
        urlBuilder.append("access_token=").append(accessToken);
        //微信开关开启
        if (weixinToggle) {
            logger.info("[sendMessage]url=" + urlBuilder.toString());
            JSONObject responseJson = JSONObject.fromObject(HttpClientUtil.sendPostBody(urlBuilder.toString(), _textMessageBuilder(weixinID, message).toString()));
            logger.info("[sendMessage]response=" + responseJson.toString());
            return responseJson;
        } else {
            logger.info("[sendMessage] weixinToggle=" + weixinToggle);
            return null;
        }
    }

    /**
     * 发送微信消息
     *
     * @param weixinID
     * @param message
     * @return
     * @throws ManagerException
     */
    @Override
    public JSONObject sendEmployeeMessage(String weixinID, String message) throws Exception {
        logger.info("[sendMessage][request]weixinID=" + weixinID + ",message=" + message);
        String accessToken = this.getAccessTokenString();
        logger.info("[sendMessage]accessToken=" + accessToken);
        //build url
        StringBuilder urlBuilder = new StringBuilder(this.getWeixinQyHost());
        urlBuilder.append("/cgi-bin/message/send?");
        urlBuilder.append("access_token=").append(accessToken);
        //微信开关开启
        if (weixinToggle) {
            logger.info("[sendMessage]url=" + urlBuilder.toString());
            JSONObject responseJson = JSONObject.fromObject(HttpClientUtil.sendPostBody(urlBuilder.toString(), _textEmployeeMessageBuilder(weixinID, message).toString()));
            logger.info("[sendMessage]response=" + responseJson.toString());
            return responseJson;
        } else {
            logger.info("[sendMessage] weixinToggle=" + weixinToggle);
            return null;
        }
    }

    /**
     * 校验，以免暴力请求导致sdk不能使用
     *
     * @return
     */
    private boolean accessTokenInvalid() {
        if (StringUtil.isInvalid(access_token)) {
            return true;
        } else if (lastUpdateToken + updateInterval < System.currentTimeMillis()) {
            return true;
        }
        return false;
    }

    /**
     * 获取用户code
     *
     * @param pipelineContext
     * @return
     */
    @Override
    public String getUserId(PipelineContext pipelineContext) {
        String userId = pipelineContext.getRequest().getString("code");
        return userId;
    }


    /**
     * 发送微信消息
     *
     * @param weixinId
     * @param content
     * @throws ManagerException
     */
    @Override
    public void sendWxMessage(String weixinId, String content) throws Exception {
        logger.info("无模板发送微信信息，开始。。。。");
        if (StringUtil.isInvalid(weixinId)) {
            logger.error("[SimpleWxEnterpriseManager][sendWxMessage][error] weixinId is invalid!");
        }
        if (StringUtil.isInvalid(content)) {
            logger.error("[SimpleWxEnterpriseManager][sendWxMessage][error] message content is invalid!");
        }
        //发送微信信息
        this.sendMessage(weixinId, content);
        logger.info("无模板发送微信信息，结束。。。。");
    }

    /**
     * 根据模板发送微信消息
     *
     * @param weixinId
     * @param template
     * @param data
     * @throws ManagerException
     */
    @Override
    public void sendWxMessage(String weixinId, MessageTemplate template, Map<String, Object> data) throws Exception {
        logger.info("根据模板发送微信信息，开始。。。。");
        if (StringUtil.isInvalid(weixinId)) {
            logger.error("[SimpleWxEnterpriseManager][sendWxMessage][error] weixinId is invalid!");
        }
        if (CollectionUtil.isInvalid(data)) {
            logger.error("[SimpleWxEnterpriseManager][sendWxMessage][error] tamplate data is invalid!");
        }
        if (template == null || StringUtil.isInvalid(template.getContent())) {
            logger.error("[SimpleWxEnterpriseManager][sendWxMessage][error] tamplate is invalid!");
        }

        String sm = CommonBizConstant.INIT_NULL;
        try {
            //根据模板和data拿到短信内容
            sm = MessageTemplateUtil.getMessageContent(template, data);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("[SimpleWxEnterpriseManager][sendWxMessage][error] send weixin message failed,IOException!");
        } catch (TemplateException e) {
            e.printStackTrace();
            logger.error("[SimpleWxEnterpriseManager][sendWxMessage][error] send weixin message failed,TemplateException!");
        }
        logger.info("转换后的微信信息：" + sm);
        //发送微信信息
        this.sendMessage(weixinId, sm);
        logger.info("根据模板发送微信信息，结束。。。。");
    }

    /**
     * 根据模板发送微信消息
     *
     * @param weixinId
     * @param template
     * @param data
     * @throws ManagerException
     */
    @Override
    public void sendEmployeeWxMessage(String weixinId, MessageTemplate template, Map<String, Object> data) throws Exception {
        logger.info("根据模板发送微信信息，开始。。。。");
        if (StringUtil.isInvalid(weixinId)) {
            logger.error("[SimpleWxEnterpriseManager][sendWxMessage][error] weixinId is invalid!");
        }
        if (CollectionUtil.isInvalid(data)) {
            logger.error("[SimpleWxEnterpriseManager][sendWxMessage][error] tamplate data is invalid!");
        }
        if (template == null || StringUtil.isInvalid(template.getContent())) {
            logger.error("[SimpleWxEnterpriseManager][sendWxMessage][error] tamplate is invalid!");
        }

        String sm = CommonBizConstant.INIT_NULL;
        try {
            //根据模板和data拿到短信内容
            sm = MessageTemplateUtil.getMessageContent(template, data);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("[SimpleWxEnterpriseManager][sendWxMessage][error] send weixin message failed,IOException!");
        } catch (TemplateException e) {
            e.printStackTrace();
            logger.error("[SimpleWxEnterpriseManager][sendWxMessage][error] send weixin message failed,TemplateException!");
        }
        logger.info("转换后的微信信息：" + sm);
        //发送微信信息
        this.sendEmployeeMessage(weixinId, sm);
        logger.info("根据模板发送微信信息，结束。。。。");
    }

    /**
     * 添加企业号通讯录
     *
     * @param worker
     * @throws Exception
     */
    @Override
    public void createMember(DispatchWorker worker) throws Exception {
        logger.info("[sendMessage][request]worker=" + JSONObject.fromObject(worker).toString());
        String accessToken = this.getAccessTokenString();
        logger.info("[sendMessage]accessToken=" + accessToken);
        //build url
        StringBuilder urlBuilder = new StringBuilder(this.getWeixinQyHost());
        urlBuilder.append("/cgi-bin/user/create?");
        urlBuilder.append("access_token=").append(accessToken);
        logger.info("[sendMessage]url=" + urlBuilder.toString());
        JSONObject responseJson = JSONObject.fromObject(HttpClientUtil.sendPostBody(urlBuilder.toString(), _memberBuilder(worker).toString()));
        logger.info("[sendMessage]response=" + responseJson.toString());
    }

    /**
     * 获取企业号部门通讯录
     *
     * @throws Exception
     */
    @Override
    public JSONObject getDepartMember() throws Exception {
        String accessToken = this.getAccessTokenString();
        logger.info("[sendMessage]accessToken=" + accessToken);
        //build url
        StringBuilder urlBuilder = new StringBuilder(this.getWeixinQyHost());
        urlBuilder.append("/cgi-bin/user/simplelist?");
        urlBuilder.append("access_token=").append(accessToken);
        urlBuilder.append("&department_id=").append(department);
        logger.info("[sendMessage]url=" + urlBuilder.toString());
        JSONObject responseJson = JSONObject.fromObject(HttpClientUtil.get(urlBuilder.toString()));
        logger.info("[sendMessage]response=" + responseJson.toString());
        return responseJson;
    }

    //========================================private================================================

    /**
     * 创建随机数
     *
     * @return
     */
    private String _createNonceStr() {
        return UUID.randomUUID().toString();
    }

    /**
     * 创建当前时间戳
     *
     * @return
     */
    private String _createTimestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    /**
     * 获取ticket
     *
     * @return
     * @throws Exception
     */
    private String _getTicket() throws Exception {
        String url = this.getWeixinQyHost() + "/cgi-bin/get_jsapi_ticket?access_token=" + this.getAccessTokenString();
        //get ticket
        String httpResponse = HttpClientUtil.get(url);
        logger.info(httpResponse);
        if (StringUtil.isInvalid("输出jsapi：" + httpResponse)) {
            logger.error("[SimpleWxEnterpriseManager][_getTicket][error] httpResponse is invalid!");
        }
        JSONObject jsonObject = JSONObject.fromObject(httpResponse);
        if (jsonObject.containsKey("ticket")) {
            return jsonObject.getString("ticket");
        } else {
            logger.error("[SimpleWxEnterpriseManager][_getTicket][error] httpResponse is invalid!");
            return null;
        }
    }

    /**
     * 构造发送微信消息的参数列表
     *
     * @param weixinID
     * @param message
     * @return
     */
    private JSONObject _textMessageBuilder(String weixinID, String message) {
        JSONObject content = new JSONObject();
        content.put("content", message);
        JSONObject body = new JSONObject();
        body.put("touser", weixinID);
        body.put("msgtype", "text");
        body.put("agentid", this.getAppid());
        body.put("text", content);
        body.put("safe", "0");
        return body;
    }

    /**
     * 构造发送微信消息的参数列表
     *
     * @param weixinID
     * @param message
     * @return
     */
    private JSONObject _textEmployeeMessageBuilder(String weixinID, String message) {
        JSONObject content = new JSONObject();
        content.put("content", message);
        JSONObject body = new JSONObject();
        body.put("touser", weixinID);
        body.put("msgtype", "text");
        body.put("agentid", this.getEmployeeAppid());
        body.put("text", content);
        body.put("safe", "0");
        return body;
    }

    /**
     * 微信消息需要转码
     *
     * @param hash
     * @return
     */
    private String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    /**
     * 请求包结构体构成
     *
     * @param worker
     * @return
     */
    private JSONObject _memberBuilder(DispatchWorker worker) {
        JSONObject body = new JSONObject();
        if (worker != null) {//worker不为空
            logger.info("worker=" + JSONObject.fromObject(worker).toString());
            body.put("userid", StringUtil.getString(worker.getWeixinId()));
            body.put("name", worker.getXingming());
            body.put("mobile", worker.getPhone());
            List<Integer> depart = new ArrayList<Integer>();
            depart.add(department);
            body.put("department", depart);
            body.put("position", Position_Worker);
            body.put("weixinid", worker.getWeixin());
            body.put("email", null);
            logger.info("body=" + JSONObject.fromObject(body).toString());
        }
        return body;
    }

    //=======================================geter and seter================================================
    public String getWeixinOpenHost() {
        return weixinOpenHost;
    }

    public void setWeixinOpenHost(String weixinOpenHost) {
        this.weixinOpenHost = weixinOpenHost;
    }

    public String getWeixinQyHost() {
        return weixinQyHost;
    }

    public void setWeixinQyHost(String weixinQyHost) {
        this.weixinQyHost = weixinQyHost;
    }

    public String getWeixinCorpID() {
        return weixinCorpID;
    }

    public void setWeixinCorpID(String weixinCorpID) {
        this.weixinCorpID = weixinCorpID;
    }

    public String getWeixinSecret() {
        return weixinSecret;
    }

    public void setWeixinSecret(String weixinSecret) {
        this.weixinSecret = weixinSecret;
    }

    public int getAppid() {
        return appid;
    }

    public void setAppid(int appid) {
        this.appid = appid;
    }

    public String getInitSessionRedirectUri() {
        return initSessionRedirectUri;
    }

    public void setInitSessionRedirectUri(String initSessionRedirectUri) {
        this.initSessionRedirectUri = initSessionRedirectUri;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public int getEmployeeAppid() {
        return employeeAppid;
    }

    public void setEmployeeAppid(int employeeAppid) {
        this.employeeAppid = employeeAppid;
    }

    public boolean isWeixinToggle() {
        return weixinToggle;
    }

    public void setWeixinToggle(boolean weixinToggle) {
        this.weixinToggle = weixinToggle;
    }
}
