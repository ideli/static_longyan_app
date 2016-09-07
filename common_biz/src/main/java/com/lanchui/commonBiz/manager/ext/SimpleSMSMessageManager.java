package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.bean.MessageTemplate;
import com.lanchui.commonBiz.bean.SmsMessageLog;
import com.lanchui.commonBiz.bean.constant.CommonBizConstant;
import com.lanchui.commonBiz.manager.SMSMessageManager;
import com.lanchui.commonBiz.util.HttpClientUtil;
import com.lanchui.commonBiz.util.MessageTemplateUtil;
import com.xiwa.base.manager.AbstractBasicManager;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.util.CollectionUtil;
import com.xiwa.base.util.DataUtil;
import com.xiwa.base.util.RandomGUIDUtil;
import com.xiwa.base.util.StringUtil;
import freemarker.template.TemplateException;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 发送短信实现类
 * Created by bingcheng on 2015/3/23.
 */

public class SimpleSMSMessageManager extends AbstractBasicManager implements SMSMessageManager, CommonBizConstant {


    private static final Logger logger = Logger.getLogger(SimpleSMSMessageManager.class);
    private boolean SMSToggle;

//    /**
//     * 把内容发送到用户手机上
//     *
//     * @param phone
//     * @param content
//     */
//    @Override
//    public void sendSMSMessage(String phone, String content, DispatchOrder order) throws ManagerException {
//        if (StringUtil.isInvalid(phone)) {
//            logger.error("[SimpleSMSMessageManager][sendSMSMessage][error] send message failed,the phone is invalid!");
//        }
//        if (StringUtil.isInvalid(content)) {
//            logger.error("[SimpleSMSMessageManager][sendSMSMessage][error] send message failed,the content is invalid!");
//        }
//        String sm = INIT_NULL;
//        String messageContentLog = content;
//        try {
//            sm = new String(Hex.encodeHex(content.getBytes(SMS_ENCODEFORM)));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            logger.error("[SimpleSMSMessageManager][sendSMSMessage][error] encodeHex failed");
//        }
//        String phoneStart = phone.substring(0,3);
//        String []phone_YD = {"134","135","136","137","138","139","147","150","151","152","157","158","159","182","183","187","188"};
//        String []phone_LT = {"130","131","132","155","156","185","186","145"};
//        String phoneFlag = "DX";
//        for(int i=0;i<phone_YD.length;i++){
//            if(StringUtil.equals(phone_YD[i],phoneStart)){
//                phoneFlag = "YD";
//                break;
//            }
//        }
//        for(int j=0;j<phone_LT.length;j++){
//            if(StringUtil.equals(phone_LT[j],phoneStart)){
//                phoneFlag = "LT";
//                break;
//            }
//        }
//        //组成url字符串
//        StringBuilder smsUrl = new StringBuilder();
//        if(StringUtil.equals(phoneFlag,"LT")){
//            smsUrl.append(SMS_MTURL_LT);
//        }else if(StringUtil.equals(phoneFlag,"YD")){
//            smsUrl.append(SMS_MTURL_YD);
//        }else{
//            smsUrl.append(SMS_MTURL_DX);
//        }
//        smsUrl.append("?command=" + COMMAND_REQUEST);
//        smsUrl.append("&spid=" + SMS_SPID);
//        smsUrl.append("&sppassword=" + SMS_SPPWD);
//        smsUrl.append("&spsc=" + SMS_SPSC);
//        smsUrl.append("&sa=" + SMS_SA);
//        smsUrl.append("&da=" + SMS_PRE_PHONE + phone);
//        smsUrl.append("&sm=" + sm);
//        smsUrl.append("&dc=" + SMS_DC);
//
//        //发送http请求，并接收http响应
//        String resStr = HttpClientUtil.get(smsUrl.toString());
//        System.out.println(resStr);
//        logger.info(resStr);
//        //log message
//        this._logMessage(resStr, messageContentLog, phone, smsUrl.toString(), order);
//    }
//
//    @Override
//    public void sendSMSMessage(String phone, MessageTemplate template, Map<String, Object> data, DispatchOrder order) throws ManagerException {
//        if (template == null) {
//            logger.info("template is empty");
//            return;
//        }
//        if (!template.isActive()) {
//            logger.info("template is not active:" + template.getAlias());
//            return;
//        }
//
//        if (StringUtil.isInvalid(phone)) {
//            logger.info("发送短信失败，输入手机号码无效！");
//            return;
//        }
//        if (!phone.startsWith("1") || phone.length() != 11) {
//            logger.info("the phone is error!phone:" + phone);
//            logger.info("发送短信失败，输入手机号码错误！");
//            return;
//        }
//        if (CollectionUtil.isInvalid(data)) {
//            logger.info("发送短信失败，输入模板参数无效！");
//            return;
//        }
//        String sm = INIT_NULL;
//        try {
//            //根据模板和data拿到短信内容
//            sm = MessageTemplateUtil.getMessageContent(template, data);
//        } catch (IOException e) {
//            e.printStackTrace();
//            logger.error("[SimpleSMSMessageManager][sendSMSMessage][error] get template failed,IO Exception");
//        } catch (TemplateException e) {
//            e.printStackTrace();
//            logger.error("[SimpleSMSMessageManager][sendSMSMessage][error] get template failed");
//        }
//        //send sms message
//        //短信开关开启
//        if (SMSToggle)
//            this.sendSMSMessage(phone, sm, order);
//    }
//
//    private void _logMessage(String resStr, String messageContentLog, String phone, String requestUrl, DispatchOrder order) {
//        Map<String, String> map = parseResStr(resStr);
//        try {
//            SmsMessageLog messageLog = new SmsMessageLog();
//            messageLog.setMessage(messageContentLog);
//            messageLog.setCreateDate(DataUtil.getTimestamp(new Date()));
//            messageLog.setPhone(phone);
//            messageLog.setRequestUrl(requestUrl);
//            messageLog.setSerialNumber(RandomGUIDUtil.getRawGUID().replace("-", ""));
//            messageLog.setSource(SMS_SA);
//            if (CollectionUtil.isValid(map)) {
//
//                messageLog.setStatus(map.get("mtstat"));
//            }
//            messageLog.setOrderId(StringUtil.getString(order.getId()));
//            messageLog.setOrderSerialNumber(order.getSerialNumber());
//            //保存短信息
//            this.addIdentified(messageLog);
//        } catch (Exception e) {
//            logger.error(e);
//            logger.error(e.getMessage());
//        }
//    }

    /**
     * 将 短信下行 请求响应字符串解析到一个HashMap中
     *
     * @param resStr
     * @return
     */
    private HashMap<String, String> parseResStr(String resStr) {
        HashMap<String, String> pp = new HashMap<String, String>();
        try {
            String[] ps = resStr.split("&");
            for (int i = 0; i < ps.length; i++) {
                int ix = ps[i].indexOf("=");
                if (ix != -1) {
                    pp.put(ps[i].substring(0, ix), ps[i].substring(ix + 1));
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return pp;
    }

    public boolean isSMSToggle() { return SMSToggle; }

    public void setSMSToggle(boolean SMSToggle) { this.SMSToggle = SMSToggle; }
}
