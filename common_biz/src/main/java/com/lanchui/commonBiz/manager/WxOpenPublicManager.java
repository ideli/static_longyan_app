package com.lanchui.commonBiz.manager;

import com.xiwa.base.manager.BasicManager;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.pipeline.PipelineContext;
import net.sf.json.JSONObject;

import java.io.UnsupportedEncodingException;


/**
 * 微信公众号接口
 * 暂时没有公众号的需求
 * <p/>
 * Created by bingcheng on 2015/4/1.
 */
public interface WxOpenPublicManager {

    /**
     * 根据access_token 和 openid 获取用户信息
     * @return
     */
    public JSONObject getAuthUserInfo(String code) throws ManagerException;


    /**
     * 获取主页跳转url
     * @return
     */
    public String getIndexUrl();

    /**
     * 获取注册页面url
     * @return
     */
    public String getRegisterUrl();

    /**
     * 获取授权authUrl
     *
     * @return
     * @throws java.io.UnsupportedEncodingException
     */
    public String getAuthUrl(String orderId) throws UnsupportedEncodingException;

    /**
     * 获取openid 的url
     * @return
     */
    public String getOpenIdUrl(String code);
    /**
     * 获取openId
     * @param code
     * @return
     * @throws ManagerException
     */
    public String getOpenId(String code) throws ManagerException;




    public String getWeixinApiSecret();
    public String getOpenMerchantId();
    public String getOpenSecret();
    public String getOpenAppId();

    /*
     *************************下面是 调用jssdk  需要的接口
     */
    /**
     * 获取signature
     *
     * @param urls
     * @return
     * @throws Exception
     */
    public JSONObject getSignature(String urls) throws Exception;

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


}
