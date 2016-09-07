package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.constant.CommonBizConstant;
import com.lanchui.commonBiz.bean.constant.WeixinConstant;
import com.lanchui.commonBiz.manager.WxOpenPublicManager;
import com.lanchui.commonBiz.util.DoubleUtil;
import com.lanchui.commonBiz.util.HttpClientUtil;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.pipeline.PipelineContext;
import com.xiwa.base.util.Charset;
import com.xiwa.base.util.DataUtil;
import com.xiwa.base.util.MD5Util;
import com.xiwa.base.util.StringUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import javax.xml.crypto.Data;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

/**
 * 公众号
 * Created by bingcheng on 2015/4/1.
 */
public class SimpleWxOpenPublicManager implements WxOpenPublicManager, CommonBizConstant,WeixinConstant {

    private static final Logger logger = Logger.getLogger(SimpleWxOpenPublicManager.class);


    private String weixinApiHost;

    private String openAppId;

    private String openSecret;

    private String openIndex;

    private String openRegister;

    private String weixinMerchantHost;

    private String openMerchantId;

    private String openRedirectUrl;

    private String weixinOpenHost;

    private String weixinApiSecret;

    private String access_token;
    private long lastUpdateToken = 0;
    private long updateInterval = 1000 * 7200;

    /**
     * d根据code 获取授权的accessToken
     * @param code
     * @return
     */
    private JSONObject _initAuthAccessToken(String code) throws ManagerException {
        //TODO code没有检查是否为空字符串
        StringBuilder url = new StringBuilder(weixinApiHost);
        url.append("/sns/oauth2/access_token?");
        url.append("appid="+openAppId);
        url.append("&secret="+openSecret);
        url.append("&code="+code);
        url.append("&grant_type=authorization_code");

        String httpResponse = HttpClientUtil.get(url.toString());
        if (StringUtil.isInvalid(httpResponse)) {
            logger.error("[SimpleWxOpenPublicManager][getAuthAccessToken][error] httpResponse is invalid!");
        }
        JSONObject authAccessToken = JSONObject.fromObject(httpResponse);
        return authAccessToken;
    }

    /**
     * 根据access_token 和 openid 获取用户信息
     * @return
     */
    @Override
    public JSONObject getAuthUserInfo(String code) throws ManagerException{
        //TODO code没有做空字符串判断
        JSONObject tokenOpenId = _initAuthAccessToken(code);
        String access_token = null;
        String openId = null;
        if (tokenOpenId.containsKey("access_token")) {
            access_token = tokenOpenId.getString("access_token");
        }
        //TODO 没有access token 要做异常处理
        if(tokenOpenId.containsKey("openid")){
            openId = tokenOpenId.getString("openid");
        }
        //TODO 没有openId 要做异常处理

        StringBuilder url = new StringBuilder(weixinApiHost);
        url.append("/sns/userinfo?");
        url.append("access_token="+access_token);
        url.append("&openid="+openId);
        url.append("&lang=zh_CN");

        String httpResponse = HttpClientUtil.get(url.toString());
        if (StringUtil.isInvalid(httpResponse)) {
            logger.error("[SimpleWxOpenPublicManager][getAuthUserInfo][error] httpResponse is invalid!");
            return null;
        }
        //TODO 这里还需要判断下这个字符串是不是一个JSON,不是JSON要抛出异常

        JSONObject userinfo = JSONObject.fromObject(httpResponse);

        return userinfo;
    }

    /**
     * 获取授权authUrl
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    @Override
    public String getAuthUrl(String orderId) throws UnsupportedEncodingException {
        StringBuilder urlBuilder = new StringBuilder(this.getWeixinOpenHost());
        urlBuilder.append("/connect/oauth2/authorize?");
        urlBuilder.append("appid=").append(this.getOpenAppId());
        urlBuilder.append("&redirect_uri=").append(URLEncoder.encode(this.getOpenRedirectUrl(), Charset.UTF_8));
        urlBuilder.append("&response_type=").append(response_type);
        urlBuilder.append("&scope=").append(scope);
        urlBuilder.append("&state=").append(orderId);
        urlBuilder.append("#wechat_redirect");
        return urlBuilder.toString();
    }


    /**
     * 获取openid 的url
     * @return
     */
    @Override
    public String getOpenIdUrl(String code) {
        StringBuilder urlBuilder = new StringBuilder(this.getWeixinApiHost());
        urlBuilder.append("/sns/oauth2/access_token?");
        urlBuilder.append("appid=").append(this.getOpenAppId());
        urlBuilder.append("&secret=").append(this.getOpenSecret());
        urlBuilder.append("&code=").append(code);
        urlBuilder.append("&grant_type=").append(Authorization_Code);
        return urlBuilder.toString();
    }

    /**
     * 获取openId
     * @param code
     * @return
     * @throws ManagerException
     */
    @Override
    public String getOpenId(String code) throws ManagerException {
        String url = this.getOpenIdUrl(code);
        logger.error("[SimpleWxOpenPublicManager][getOpenId][getOpenUrl] url=" + url);
        String httpResponse = HttpClientUtil.get(url);
        if (StringUtil.isInvalid(httpResponse)) {
            logger.error("[SimpleWxOpenPublicManager][getOpenId][error] httpResponse is invalid!");
        }
        logger.error("[SimpleWxOpenPublicManager][getOpenId][info] httpResponse=" + httpResponse);
        JSONObject jsonObject = JSONObject.fromObject(httpResponse);
        if (jsonObject.containsKey("openid")) {
            return jsonObject.getString("openid");
            //更新lastupdate时间戳,以免暴力请求导致sdk不能使用
        //    lastUpdateToken = System.currentTimeMillis();
        //    System.out.println(access_token);
        }
        return null;
    }



/*
    //加密测试
    public static void main(String []args){
        try {
            String test = "5.10";
            System.out.println(DataUtil.getDouble(test,0));
            int money = DataUtil.getInt(DataUtil.getDouble(test,1.00)*100,0);
            System.out.println(money);
       //     String testStr = "appId=wxcb92f114c4411c42&nonceStr=4de9d1e53d084059a867d83ca910e04e&package=prepay_id=wx20150521170144f12b3b8f840027147914&signType=MD5&timeStamp=1432198904&key=d467f09dd6fa449c829f2634e514db0d";
        //    String ss = MD5Util.getMD5(testStr);
        //    System.out.println(ss);
       //     String sss = MD5Util.getMD5String(testStr);
       //     System.out.println(sss);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/



    /**
     * 微信支付接口  签名
     * @param map
     * @return
     */
    private String _wxPaySign(Map<String,String> map) throws Exception {
        String stringTmp;
        String signature = "";
        //注意这里参数名必须全部小写，且必须有序
        stringTmp = "appId=" + map.get("appId") +
                "&nonceStr=" + map.get("nonceStr") +
                "&package=" + map.get("package") +
                "&signType=" + map.get("signType") +
                "&timeStamp=" + map.get("timeStamp") +
                "&key=" + this.getWeixinApiSecret().trim();
        logger.info("[getSignature]stringTmp=" + stringTmp);

        signature = MD5Util.getMD5(stringTmp.trim());

        return signature.toUpperCase();
    }

    /**
     * ************************************ 下面是 jssdk 接口
     */

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
        sign.put("appId", this.getOpenAppId());
        logger.info("[getSignature]signature=" + sign.toString());
        return sign;

    }

    /**
     * 获取ticket
     *
     * @return
     * @throws Exception
     */
    private String _getTicket() throws Exception {
        String url = this.getWeixinApiHost() + "/cgi-bin/ticket/getticket?access_token=" + this.getAccessTokenString()+"&type=jsapi";
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
     * 获取accessToken的url
     *
     * @return
     */
    @Override
    public String getAccessTokenUrl() {
        StringBuilder urlBuilder = new StringBuilder(this.getWeixinApiHost());
        urlBuilder.append("/cgi-bin/token?");
        urlBuilder.append("grant_type=client_credential");
        urlBuilder.append("&appid=").append(this.getOpenAppId());
        urlBuilder.append("&secret=").append(this.getOpenSecret());
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
     * 获取主页跳转url
     * @return
     */
    public String getIndexUrl(){
        return this.getOpenIndex();
    }

    /**
     * 获取注册页面url
     * @return
     */
    public String getRegisterUrl(){
        return this.getOpenRegister();
    }


    public String getWeixinApiHost() {
        return weixinApiHost;
    }

    public void setWeixinApiHost(String weixinApiHost) {
        this.weixinApiHost = weixinApiHost;
    }

    public String getOpenAppId() {
        return openAppId;
    }

    public void setOpenAppId(String openAppId) {
        this.openAppId = openAppId;
    }

    public String getOpenSecret() {
        return openSecret;
    }

    public void setOpenSecret(String openSecret) {
        this.openSecret = openSecret;
    }

    public String getOpenIndex() {
        return openIndex;
    }

    public void setOpenIndex(String openIndex) {
        this.openIndex = openIndex;
    }

    public String getOpenRegister() {
        return openRegister;
    }

    public void setOpenRegister(String openRegister) {
        this.openRegister = openRegister;
    }

    public String getWeixinMerchantHost() {
        return weixinMerchantHost;
    }

    public void setWeixinMerchantHost(String weixinMerchantHost) {
        this.weixinMerchantHost = weixinMerchantHost;
    }

    public String getOpenMerchantId() {
        return openMerchantId;
    }

    public void setOpenMerchantId(String openMerchantId) {
        this.openMerchantId = openMerchantId;
    }

    public String getOpenRedirectUrl() {
        return openRedirectUrl;
    }

    public void setOpenRedirectUrl(String openRedirectUrl) {
        this.openRedirectUrl = openRedirectUrl;
    }

    public String getWeixinOpenHost() {
        return weixinOpenHost;
    }

    public void setWeixinOpenHost(String weixinOpenHost) {
        this.weixinOpenHost = weixinOpenHost;
    }

    public String getWeixinApiSecret() {
        return weixinApiSecret;
    }

    public void setWeixinApiSecret(String weixinApiSecret) {
        this.weixinApiSecret = weixinApiSecret;
    }
}
