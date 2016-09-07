package com.greatbee.util;

import com.lanchui.commonBiz.bean.DispatchCommunityAuthorized;
import com.lanchui.commonBiz.bean.DispatchLoginToken;
import com.lanchui.commonBiz.bean.DispatchMerchant;
import com.lanchui.commonBiz.bean.RedstarCommunity;
import com.lanchui.commonBiz.bean.constant.TargetType;
import com.lanchui.commonBiz.manager.DispatchDriver;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.pipeline.PipelineContext;
import com.xiwa.base.util.DataUtil;
import com.xiwa.base.util.StringUtil;
import com.xiwa.security.bean.Resource;
import com.xiwa.security.bean.constant.AuthTarget;
import com.xiwa.security.driver.SecurityDriver;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by usagizhang on 15-11-5.
 */
public class CommunityUtil {

    public static final String COOKIE_LOGIN_TOKEN_NAME = "loginToken";
    //社区的sessionKey
    public final static String Community_Session = "community_session";
    //社区的sessionResource
    public final static String Community_Resource_Session = "community_resource_session";
    //社区登陆用户
    public final static String Community_Auth_Session = "community_auth_session";
    //商户的sessionKey
    public final static String Merchant_Session = "merchant_session";

    /**
     * 初始化授权信息
     *
     * @param dispatchCommunityAuthorized
     * @throws com.xiwa.base.manager.ManagerException
     */
    public static void initAuth(DispatchCommunityAuthorized dispatchCommunityAuthorized, RedstarCommunity community, DispatchMerchant merchant, List<Resource> resources, PipelineContext pipelineContext) throws ManagerException {
        //判断社区账号是否存在
        if (dispatchCommunityAuthorized != null) {
            //合区账号存在
            //社区Id有效
            if (dispatchCommunityAuthorized.getCommunityId() > 0) {

                //设置session
                if (community != null && community.getMerchantId() > 0) {
                    //资源存到session中
                    addObjectToSession(Community_Resource_Session, resources, pipelineContext);
                    //添加社区对象到session中
                    addObjectToSession(Community_Session, community, pipelineContext);
                    //添加社区登陆对象到session中
                    addObjectToSession(Community_Auth_Session, dispatchCommunityAuthorized, pipelineContext);
                    //添加物业对象到session中
                    addObjectToSession(Merchant_Session, merchant, pipelineContext);
                } else {
                    throw new ManagerException("该商户不存在");
                }
            } else {
                throw new ManagerException("该社区不存在");
            }
        } else {
            throw new ManagerException("社区账号不存在");
        }
    }

    /**
     * 初始化token
     *
     * @param dispatchCommunityAuthorized
     * @param pipelineContext
     * @throws ManagerException
     */
    public static void initToken(DispatchCommunityAuthorized dispatchCommunityAuthorized, PipelineContext pipelineContext, DispatchDriver dispatchDriver) throws ManagerException {
        //生成token
        //设置cookie信息
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        //将登陆信息存到数据库
        DispatchLoginToken loginToken = new DispatchLoginToken();
        Date nowDate = new Date(System.currentTimeMillis());
        loginToken.setLoginToken(token);
        loginToken.setCreateDate(nowDate);
        Date expiredDate = new Date(nowDate.getTime() + DataUtil.getLong("90", 0) * 24 * 60 * 60 * 1000);//有效期90天
        loginToken.setExpiredTime(expiredDate);
        loginToken.setTarget(TargetType.Community.getType());
        loginToken.setTargetId(dispatchCommunityAuthorized.getId());
        dispatchDriver.getDispatchLoginTokenManager().addBean(loginToken);
        //返回到前台，写入到cookie中
        pipelineContext.getResponse().addKey(COOKIE_LOGIN_TOKEN_NAME, token);
    }

    /**
     * 根据角色获取resource
     *
     * @param dispatchCommunityAuthorized
     * @param community
     * @return
     * @throws ManagerException
     */
    public static List<Resource> getResource(DispatchCommunityAuthorized dispatchCommunityAuthorized, RedstarCommunity community, DispatchDriver dispatchDriver, SecurityDriver securityDriver) throws ManagerException {
        List<Resource> resources = new ArrayList<Resource>();
        List<Resource> resourceList = new ArrayList<Resource>();
        String roles = dispatchCommunityAuthorized.getRoles();
        int[] roleIds = StringUtil.toIntArray(roles, ',');
        //取得社区对应的role
//        List<Role> roleList = securityDriver.getRoleManager().getBeanList(roleIds);
        //商户Id存在
        resources = securityDriver.getResourceManager().getAllResources(AuthTarget.Community);
//        if (community.getMerchantId() > 0) {
//            DispatchMerchant merchant = (DispatchMerchant) dispatchDriver.getDispatchMerchantManager().getBean(community.getMerchantId());
//            //社区对应的role存在
//            if (CollectionUtil.isValid(roleList)) {
//                for (Role role : roleList) {
//                    //belongID相同
//                    if (merchant.getBelongedId() == role.getBelongedId()) {
//                        resources = securityDriver.getResourceManager().getAllResources(AuthTarget.Community);
//                        if (role.isSupperRole()) {
//                            return resources;
//                        } else {
//                            int[] resourceId = StringUtil.toIntArray(role.getResources(), ',');
//                            List<Resource> roleResources = securityDriver.getResourceManager().getResourceList(resourceId);
//                            for (Resource resource : roleResources) {
//                                //登陆源是商户
//                                if (StringUtil.equals(resource.getAuthTarget(), AuthTarget.Community.getTarget())) {
//                                    resourceList.add(resource);
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
        return resources;
    }

    /**
     * 设置session 值
     *
     * @param key
     * @param value
     * @throws ManagerException
     */
    protected static void addObjectToSession(String key, Object value, PipelineContext context) throws ManagerException {
        HttpServletRequest request = context.getRequest().getHttpServletRequest();
        if (request.getSession().getAttribute("session_data_map") == null) {
            request.getSession().setAttribute("session_data_map", new HashMap<String, Object>());
        }
        Map<String, Object> map = (Map<String, Object>) request.getSession().getAttribute("session_data_map");
        map.put(key, value);
    }
}
