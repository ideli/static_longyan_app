package com.lanchui.commonBiz.util;

import com.lanchui.commonBiz.bean.DispatchPushDevice;
import com.lanchui.commonBiz.bean.DispatchUser;
import com.lanchui.commonBiz.manager.DispatchDriver;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.util.CollectionUtil;
import com.xiwa.base.util.StringUtil;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.List;

/**
 * 消息推送工具类
 * Created by usagizhang on 15-11-19.
 */
public class PushUtil {
    /**
     * 用户登陆更新
     *
     * @param registrationId
     * @param platform
     * @param source
     * @param loginUser
     * @param dispatchDriver
     * @throws ManagerException
     */
    public static void login(String registrationId, String platform, String source, DispatchUser loginUser, DispatchDriver dispatchDriver, Logger logger) throws ManagerException {
        if (StringUtil.isValid(registrationId)) {
            logger.info("[PushUtil][login][" + registrationId + "][" + platform + "][" + source + "]");
            //如果有registrationId
            List<DispatchPushDevice> dispatchPushDeviceList = dispatchDriver.getDispatchPushDeviceManager().getBeanListByColumn("loginUserId", loginUser.getId());
            if (CollectionUtil.isValid(dispatchPushDeviceList)) {
                //当前用户有登陆过
                DispatchPushDevice device = dispatchPushDeviceList.get(0);
                if (device != null && StringUtil.isValid(device.getRegistrationId()) && StringUtil.equals(device.getRegistrationId().trim().toUpperCase(), registrationId.trim().toUpperCase())) {
                    //如果ID相等，什么操作都不处理
                } else {
                    //如果ID不等，update数据库这条记录
                    device.setRegistrationId(registrationId.trim());
                    device.setPlatform(platform);
                    device.setSource(source);
                    dispatchDriver.getDispatchPushDeviceManager().updateBean(device);
                }
            } else {
                DispatchPushDevice device = new DispatchPushDevice();
                device.setCreateDate(new Date());
                device.setLoginUserId(loginUser.getId());
                device.setRegistrationId(registrationId.trim());
                device.setPlatform(platform);
                device.setSource(source);
                dispatchDriver.getDispatchPushDeviceManager().addBean(device);
            }
        }
    }
}
