package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.DispatchUser;
import com.lanchui.commonBiz.bean.constant.CommonBizConstant;
import com.lanchui.commonBiz.manager.JPushMessageManager;
import com.lanchui.commonBiz.util.HttpClientUtil;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.util.StringUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by bingcheng on 2015/10/9.
 */
public class SimpleJPushMessageManager implements JPushMessageManager, CommonBizConstant {

    private static final Logger logger = Logger.getLogger(SimpleJPushMessageManager.class);

    private String jpushSign;
    private String jpushApiUrl;



    /**
     * 把内容推送到用户手机上
     *
     * @param registrationId
     * @param content
     */
    @Override
    public void sendMessageByJPush(String registrationId, String content) throws ManagerException {
        if (StringUtil.isInvalid(registrationId)) {
            logger.error("[SimpleJPushMessageManager][sendMessageByJPush][error] send message failed,the registrationId is invalid!");
        }
        if (StringUtil.isInvalid(content)) {
            logger.error("[SimpleJPushMessageManager][sendMessageByJPush][error] send message failed,the content is invalid!");
        }

      /*  JSONObject postBody = new JSONObject();

        postBody.put("platform","all");
        postBody.put("audience","");*/

        String postBodyStr = "{'platform':'all','audience':{'registration_id':['"+registrationId+"']},'notification':{'alert':'"+content+"'}}";

        //发送http请求，并接收http响应
        String resStr = HttpClientUtil.sendPost(jpushApiUrl,postBodyStr,"Authorization",jpushSign);
        System.out.println(resStr);
        logger.info(resStr);
    }

    public String getJpushSign() {
        return jpushSign;
    }

    public void setJpushSign(String jpushSign) {
        this.jpushSign = jpushSign;
    }

    public String getJpushApiUrl() {
        return jpushApiUrl;
    }

    public void setJpushApiUrl(String jpushApiUrl) {
        this.jpushApiUrl = jpushApiUrl;
    }
}
