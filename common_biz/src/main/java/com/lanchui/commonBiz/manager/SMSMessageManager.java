package com.lanchui.commonBiz.manager;

import com.lanchui.commonBiz.bean.MessageTemplate;
import com.xiwa.base.manager.BasicManager;
import com.xiwa.base.manager.ManagerException;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 短消息manager
 * Created by bingcheng on 2015/3/23.
 */
public interface SMSMessageManager extends BasicManager {


//    //这个manager继承BasicManager,然后要有下面几个方法
//
//    //发消息
//    //参数，phone=电话号码，content=消息内容
//    public void sendSMSMessage(String phone, String content, DispatchOrder order) throws ManagerException;
//
//    //发消息,使用freemarker把数据标记在模板上
//    //参数，phone=电话号码，template=模板对象，data表示模板的数据,order订单
//    public void sendSMSMessage(String phone, MessageTemplate template, Map<String, Object> data, DispatchOrder order) throws ManagerException;

}
