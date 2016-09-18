package com.chinaredstar.longyan.util;

import com.chinaredstar.longyan.bean.constant.LanchuiConstant;
import com.chinaredstar.commonBiz.bean.RedstarSession;
import com.chinaredstar.commonBiz.manager.DispatchDriver;
import com.chinaredstar.commonBiz.manager.RedstarSessionManager;
import com.chinaredstar.commonBiz.util.CookieUtil;
import com.xiwa.base.cmd.SessionTimeoutException;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.util.StringUtil;
import net.sf.json.JSONObject;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2016/5/17.
 */
public class ReFreshTokenUtil {

    public static void refreshToken(ApplicationContext context, HttpServletRequest request, HttpServletResponse response) throws ManagerException {
        String token = CookieUtil.getCookieValue("_token", request);
//        String expireDate = CookieUtil.getCookieValue("_tokenExpiredDate", request);
        boolean flag = true;

//        String openId = "51029458";
//        Map<String, String> params = new HashMap<String, String>();

//        if (jsonObjects.getInt("errorCode") == 0) {
//
//        }

        if(StringUtil.isInvalid(token)){
            token=request.getHeader(LanchuiConstant.Request_Header_Token);
        }

        if (StringUtil.isValid(token)) {
            //有token
            RedstarSession session = ((RedstarSessionManager) context.getBean("redstarSessionManager")).getActivatedSession(token);
            if (session != null) {
                if (System.currentTimeMillis() > session.getUserTokenExpiredDate().getTime()) {
                    HashMap systemConfig = (HashMap) SpringUtil.getContext().getBean("systemConfig");
                    //用户中心更新accessToken
                    String refreshToken = session.getUserRefreshToken();
                    String grantType = "refresh_token";
                    String appId = systemConfig.get("appId").toString();
                    String appSecret = systemConfig.get("appSecret").toString();
                    String userCenterUrl = systemConfig.get("userCenterUrl").toString();
                    Map<String, String> param = new HashMap<String, String>();
                    param.put("appId", appId);
                    param.put("grantType", grantType);
                    param.put("appSecret", appSecret);
                    param.put("refreshToken", refreshToken);
                    JSONObject jsonObject = HttpClientUtil.post(userCenterUrl + "/employee/oauth/refreshToken", param);
                    if (jsonObject.getInt("errorCode") == 0) {
                        String accessToken = jsonObject.getJSONObject("data").get("accessToken").toString();
                        String refresh = jsonObject.getJSONObject("data").get("refreshToken").toString();
                    /*如果离职的就登出 start*/
                        param = new HashMap<String, String>();
                        param.put("accessToken", accessToken);
                        param.put("openid", session.getOpenId());
                        param.put("appId", appId);
                        param.put("appSecret", appSecret);
                        JSONObject jsonObjectStatus = HttpClientUtil.post(userCenterUrl + "/employee/me", param);
                        if (jsonObjectStatus.getInt("errorCode") == 0) {
                            String status = jsonObjectStatus.getJSONObject("data").get("hrStatus").toString();
                            if (!"A".equals(status)) {
                                throw new SessionTimeoutException("error_session_timeout");
                            }
                        } else {
                            throw new SessionTimeoutException("error_session_timeout");
                        }
                     /*如果离职的就登出 end*/
                        //更新该数据
                        session.setUserToken(accessToken);
                        session.setUserRefreshToken(refresh);
                        long time = System.currentTimeMillis() + 7200 * 1000;
                        session.setUserTokenExpiredDate(new Date(time));
                        DispatchDriver dispatchDriver = (DispatchDriver) context.getBean("dispatchDriver");
                        dispatchDriver.getRedstarSessionManager().updateBean(session);
//                        CookieUtil.setCookie("_tokenExpiredDate", String.valueOf(time), 30 * 24 * 60 * 60, "/", response);
                    } else {
                        throw new SessionTimeoutException("error_session_timeout");
                    }
                }
            } else {
                throw new SessionTimeoutException("error_session_timeout");
            }
        } else {
            throw new SessionTimeoutException("error_session_timeout");
        }

    }


}
