package com.chinaredstar.commonBiz.bean.constant;

/**
 * Created by root on 2/10/15.
 */
public interface WeixinConstant {
    public final static String response_type = "code";//固定值code
    public final static String scope = "snsapi_base";//固定值
    public final static String state = "STATE";//固定值
    public final static String Authorization_Code = "authorization_code";
    public final static String Lang = "zh_CN";

    public final static String Device_Info = "WEB";
    public final static String Trade_Type = "JSAPI";

    public final static String WEIXIN_ORDER_STATUS_SUCCESS = "SUCCESS";
    public final static String WEIXIN_API_SIGN_TYPE = "MD5";


//    public final static String weixinOpenHost = "https://open.weixin.qq.com";
//    public final static String weixinQyHost = "https://qyapi.weixin.qq.com";
//    public final static String weixinCorpID = "wx3c8df80caac6217c";
//    public final static String weixinSecret = "tHoCRFr6wdgZ-6qKgdxXPB_4JJcznUEXcEpGbrszeXPb_-7wgbFdMpkbE2_eNeF_";
//    public final static String initSessionRedirectUri = "http://weixin.usagilab.com:8080/weixin/initSession.action";
}
