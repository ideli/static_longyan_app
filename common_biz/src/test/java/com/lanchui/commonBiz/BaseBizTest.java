package com.lanchui.commonBiz;

import com.xiwa.base.bean.TestConfig;
import com.xiwa.base.manager.ManagerException;
import junit.framework.TestCase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by CarlChen on 3/25/15.
 */
public class BaseBizTest extends TestCase implements TestConfig
{
    protected ApplicationContext context;

//    protected CommonBizDriver commonBizDriver;

    /**
     * @return
     */
    public String getServerConfigName()
    {
        return "test_common_biz_server.xml";
    }

    /**
     * Set Up
     */
    public void setUp() throws ManagerException
    {
        context = new ClassPathXmlApplicationContext(getServerConfigName());
  //      commonBizDriver = (CommonBizDriver) context.getBean("commonBizDriver");
    }


    /**
     * Test Context
     */
    public void testContext()
    {
        // test yefei
        assertNotNull(context);
    }

    /**
     * Tear Down
     */
    public void tearDown()
    {
    }
}

