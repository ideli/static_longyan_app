package com.lanchui.commonBiz.util;

import com.lanchui.commonBiz.bean.MessageTemplate;
import com.lanchui.commonBiz.bean.constant.CommonBizConstant;
import com.xiwa.base.bean.StringTemplateLoader;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.util.StringUtil;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * 模板工具类，主要提供一个方法  根据模板类和输入参数，返回短信/微信/邮件内容
 * Created by bingcheng on 2015/3/24.
 */
public class MessageTemplateUtil implements CommonBizConstant {

    private final static Logger logger = Logger.getLogger(MessageTemplateUtil.class);

    /**
     * 根据模板类和输入参数，返回短信/微信/邮件内容
     *
     * @param messageTemplate
     * @param data
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public static String getMessageContent(MessageTemplate messageTemplate, Map<String, Object> data) throws IOException, TemplateException, ManagerException {
        if (messageTemplate == null) {
            logger.error("[MessageTemplateUtil][getMessageContent][error] the template not found!");
        }
        StringWriter writer = new StringWriter();
        Configuration contentCfg = new Configuration();
        contentCfg.setTemplateLoader(new StringTemplateLoader(messageTemplate.getContent()));
        contentCfg.setDefaultEncoding(SMS_ENCODEFORM);
        contentCfg.getTemplate("").process(data, writer);
        return writer.toString();
    }

    /**
     * 根据模板输出内容
     *
     * @param messageTemplate
     * @param data
     * @return
     * @throws IOException
     * @throws TemplateException
     * @throws ManagerException
     */
    public static String getMessageContent(String messageTemplate, Map<String, Object> data) throws IOException, TemplateException, ManagerException {
        if (StringUtil.isInvalid(messageTemplate)) {
            logger.error("[MessageTemplateUtil][getMessageContent][error] the template not found!");
        }
        StringWriter writer = new StringWriter();
        Configuration contentCfg = new Configuration();
        contentCfg.setTemplateLoader(new StringTemplateLoader(messageTemplate));
        contentCfg.setDefaultEncoding(SMS_ENCODEFORM);
        contentCfg.getTemplate("").process(data, writer);
        return writer.toString();
    }

}
