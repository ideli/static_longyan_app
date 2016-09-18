package com.greatbee.util;

import com.chinaredstar.commonBiz.util.*;
import com.chinaredstar.commonBiz.util.HttpClientUtil;
import com.greatbee.bean.constant.LanchuiConstant;
import com.chinaredstar.commonBiz.bean.RedstarSession;
import com.chinaredstar.commonBiz.manager.RedstarCommonManager;
import com.xiwa.base.bean.search.ext.TextSearch;
import com.xiwa.base.util.StringUtil;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by niu on 2016/7/8.
 */
public class UserUtil {


    public static JSONObject getUserInfo (HttpServletRequest request,RedstarCommonManager redstarCommonManager){
        try{

            String openId=CookieUtil.getCookieValue("_openId",request);

            if (StringUtil.isInvalid(openId)){
                String token = request.getHeader(LanchuiConstant.Request_Header_Token);
                TextSearch textSearch = new TextSearch("token");
                textSearch.setSearchValue(token);

                List<RedstarSession> sessionList = redstarCommonManager.getDataList(RedstarSession.class,textSearch);

                openId = sessionList.get(0).getOpenId();
            }

            HashMap systemConfig = (HashMap) SpringUtil.getContext().getBean("systemConfig");
            String grantType = "client_credentials";
            String appId = systemConfig.get("appId").toString();
            String appSecret = systemConfig.get("appSecret").toString();
            String userCenterUrl = systemConfig.get("userCenterUrl").toString();

            Map<String, String> tokenParams = new HashMap<String, String>();
            tokenParams.put("grantType", grantType);
            tokenParams.put("appId", appId);
            tokenParams.put("appSecret", appSecret);

            JSONObject tokenObj = com.chinaredstar.commonBiz.util.HttpClientUtil.post(userCenterUrl + "/employee/getTokenByClient", tokenParams);

            String accessToken=tokenObj.getJSONObject("data").get("accessToken").toString();
            //获取accessToken
            Map<String, String> meParams = new HashMap<String, String>();
            meParams.put("accessToken", accessToken);
            meParams.put("openid",openId);

            JSONObject userObj = HttpClientUtil.post(userCenterUrl + "/employee/getBasicInfo", meParams);
            return  userObj;
        }catch (Exception e){
            e.printStackTrace();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("errorCode",500);
            jsonObject.put("errorMsg","用户信息获取失败");
            return  jsonObject;
        }
    }

}
