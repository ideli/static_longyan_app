package com.chinaredstar.longyan.util;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
/**
 * Created by niu on 2016/5/5.
 * 获取Spring ApplicationContext
 */
@Component
public class SpringUtil implements ApplicationContextAware {

    private   static  ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context=applicationContext;
    }

    public static ApplicationContext getContext(){
        return context;
    }
}
