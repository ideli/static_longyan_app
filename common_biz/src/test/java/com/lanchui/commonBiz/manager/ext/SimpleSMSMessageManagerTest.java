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
 * SimpleSMSMessageManager Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>���� 26, 2015</pre>
 */
public class SimpleSMSMessageManagerTest  extends BaseBizTest implements CommonBizConstant{

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
     * Method: sendSMSMessage(String phone, String content)
     */
    @Test
    public void testSendSMSMessageForPhoneContent() throws Exception {
//        commonBizDriver.getSMSMessageManager().sendSMSMessage("18620510854", "你好，世界！");
    }

    /**
     * Method: sendSMSMessage(String phone, MessageTemplate template, Map<String, Object> data)
     */
    @Test
    public void testSendSMSMessageForPhoneTemplateData() throws Exception {
//TODO: Test goes here...
        Map data = new HashMap();
        data.put("xingming","xiaobc");
  //      MessageTemplate t = commonBizDriver.getMessageTemplateManager().getMessageTemplateByAlias(Sms_Notice_User_Signon);
 //       commonBizDriver.getSMSMessageManager().sendSMSMessage("18620510854",t,data);
    }


    /**
     * Method: parseResStr(String resStr)
     */
    @Test
    public void testParseResStr() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = SimpleSMSMessageManager.getClass().getMethod("parseResStr", String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

} 
