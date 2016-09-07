package com.greatbee.util;

import com.lanchui.commonBiz.bean.DispatchLoginToken;
import com.lanchui.commonBiz.bean.RedstarCommunity;
import com.lanchui.commonBiz.manager.DispatchDriver;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.pipeline.PipelineContext;
import com.xiwa.base.pipeline.PipelineException;
import com.xiwa.base.util.StringUtil;
import com.xiwa.security.driver.SecurityDriver;
import com.xiwa.zeus.util.SessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * 验证prodruce的session
 * Created by root on 12/9/14.
 */
@Component("verifyCommunitySession")
public class VerifyCommunitySession extends BasePipeline {

    @Autowired
    private DispatchDriver dispatchDriver;
    @Autowired
    protected SecurityDriver securityDriver;

    @Override
    public void execute(PipelineContext context) throws PipelineException {

        HttpServletRequest request = context.getRequest().getHttpServletRequest();
        if (request.getSession().getAttribute(SESSION_DATA_MAP) != null) {
            Map<String, Object> map = (Map<String, Object>) request.getSession().getAttribute(SESSION_DATA_MAP);
            RedstarCommunity community = (RedstarCommunity) map.get(Community_Session);
            if (community != null) {
                //有session直接返回
                return;
            } else if (map.get(SessionTool.SESSION_Community) != null) {
                //有session直接返回
                return;
            }
        }

        //没有session校验cookie是否有值
        try {
           /* Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                String loginToken = _getObjectFromCookie(cookies, COOKIE_COMMUNITY_LOGIN_TOKEN_NAME);
                if (StringUtil.isValid(loginToken)) {
                    logger.info("[VerifySession][execute][getBeanListByColumn][request] communityLogintoken=" + loginToken);
                    List<DispatchLoginToken> tokens = dispatchDriver.getDispatchLoginTokenManager().getBeanListByColumn(COOKIE_LOGIN_TOKEN_NAME, loginToken, "createDate", false);
                    logger.info("[VerifySession][execute][getBeanListByColumn][response] tokens.size()=" + tokens.size());
                    if (CollectionUtil.isValid(tokens)) {
                        DispatchLoginToken token = tokens.get(0);
                        //校验token是否过期
                        boolean isOutTime = this._verifyIsOutTime(token);
                        if (!isOutTime) {//没有过期
                            int communityAuthId = token.getTargetId();
                            DispatchCommunityAuthorized dispatchCommunityAuthorized = (DispatchCommunityAuthorized) dispatchDriver.getDispatchCommunityAuthorizedManager().getBean(communityAuthId);
                            logger.info("[VerifySession][execute] merchantId=" + JSONObject.fromObject(dispatchCommunityAuthorized.getCommunityId()).toString());
                            RedstarCommunity community = (RedstarCommunity) this.dispatchDriver.getDispatchCommunityManager().getBean(dispatchCommunityAuthorized.getCommunityId());
                            RedstarCommunity community = (RedstarCommunity) this.dispatchDriver.getDispatchCommunityManager().getBean(dispatchCommunityAuthorized.getCommunityId());
                            logger.info("[VerifySession][execute] merchant=" + JSONObject.fromObject(community).toString());
                            DispatchMerchant merchant = (DispatchMerchant) this.dispatchDriver.getDispatchMerchantManager().getBean(community.getMerchantId());
//                            List<Resource> resources = CommunityUtil.getResource(dispatchCommunityAuthorized, community, dispatchDriver, securityDriver);
//                            logger.info("[VerifySession][execute] securityResources=" + resources.size());
                            CommunityUtil.initAuth(dispatchCommunityAuthorized, community, merchant, new ArrayList<Resource>(), context);
                            return;
                        }
                    }
                }
            }*/
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new PipelineException(e.getMessage());
        }

        context.getResponse().setOk(false);
        context.getResponse().setCode(ERROR_CODE_NO_SESSION);
        throw new PipelineException("session is null");
    }


    /**
     * 根据key拿到cookie中对应的value
     *
     * @param cookies
     * @param key
     * @return
     */
    private String _getObjectFromCookie(Cookie[] cookies, String key) {
        for (int i = 0; i < cookies.length; i++) {
            if (StringUtil.equals(cookies[i].getName(), key)) {
                return cookies[i].getValue();
            }
        }
        return null;
    }

    /**
     * 校验cookie是否过期
     *
     * @param token
     * @return
     */
    private boolean _verifyIsOutTime(DispatchLoginToken token) throws ManagerException {
        //判断是否过期
        Date expiredTime = token.getExpiredTime();
        if (expiredTime.getTime() < new Date(System.currentTimeMillis()).getTime()) {
            return true;
        }
        return false;
    }
}
