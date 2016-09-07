package com.greatbee.lanchui.task;

import com.greatbee.bean.constant.LanchuiConstant;
import com.lanchui.commonBiz.bean.RedstarTaskLog;
import com.lanchui.commonBiz.manager.RedstarTaskLogManager;
import com.xiwa.base.manager.ManagerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by leiyun 2016/8/24.
 */
//@Component(value = "testTask")
public class Test implements LanchuiConstant{
    @Autowired
    private RedstarTaskLogManager redstarTaskLogManager;

    public void test () throws ManagerException {
        Long startTime = System.currentTimeMillis();    //todo 开始时间
        RedstarTaskLog redstarTaskLog = new RedstarTaskLog(); // todo log

        redstarTaskLog.setTaskClassName(this.getClass().getSimpleName()); // todo 工作类名称
        redstarTaskLog.setLogDatetime(new Date());  // todo 日志日期时间
        redstarTaskLog.setRemark("测试打印");
        redstarTaskLog.setAction(taskLogStart); //todo 活动
        redstarTaskLogManager.addBean(redstarTaskLog);  //todo 添加 RedstarTaskLog


        System.out.print("打印test————");


        redstarTaskLog = new RedstarTaskLog();
        redstarTaskLog.setTaskClassName(this.getClass().getSimpleName());
        redstarTaskLog.setLogDatetime(new Date());
        redstarTaskLog.setRemark("测试打印");
        redstarTaskLog.setAction(taskLogEnd);   // todo 添加成功
        redstarTaskLog.setExecuteTime(System.currentTimeMillis() - startTime); // todo 执行时间
        redstarTaskLogManager.addBean(redstarTaskLog);  //todo 添加tasklog


    }


}
