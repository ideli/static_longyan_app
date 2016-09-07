package com.lanchui.commonBiz.manager.ext;

import com.lanchui.commonBiz.BaseBizTest;
import com.lanchui.commonBiz.bean.constant.CommonBizConstant;
import com.xiwa.base.manager.ManagerException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * SimpleWeixinMessageManager Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>���� 26, 2015</pre>
 */
public class SimpleWeixinMessageManagerTest extends BaseBizTest implements CommonBizConstant{

    public void setUp() throws ManagerException {
        super.setUp();
    }

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: sendWxMessage(String weixinId, String content)
     */
    @Test
    public void testSendWxMessageForWeixinIdContent() throws Exception {
 //       commonBizDriver.getWxEnterpriseManager().sendWxMessage("xiaobc@lanchui.com","你好，世界！");
    }

    /**
     * Method: sendWxMessage(String weixinId, MessageTemplate template, Map<String, Object> data)
     */
    @Test
    public void testSendWxMessageForWeixinIdTemplateData() throws Exception {
//TODO: Test goes here...
        Map data = new HashMap();
        data.put("xingming","xiaobc");
  //      MessageTemplate t = commonBizDriver.getMessageTemplateManager().getMessageTemplateByAlias(Wx_Notice_User_Signon);
  //      commonBizDriver.getWxEnterpriseManager().sendWxMessage("xiaobc@lanchui.com",t,data);
    }


} 
