package com.greatbee.web.action;

import com.greatbee.bean.constant.LanchuiConstant;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.util.DataUtil;
import com.xiwa.base.util.StringUtil;
import com.xiwa.security.bean.constant.AuthTarget;
import com.xiwa.security.bean.ext.SimpleAuthorized;
import com.xiwa.security.web.action.SecurityEntranceAction;
import com.xiwa.security.web.common.SecurityTool;
import com.xiwa.zeus.bean.OEMConfig;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author CarlChen
 * @version 1.00 14-4-9 上午11:32
 */
@Scope("prototype")
@Component()
public class IndexAction extends SecurityEntranceAction implements LanchuiConstant {
    /**
     * Index
     *
     * @return
     */
    public String index() {
        if (SecurityTool.isSecurityLogin(AuthTarget.Employee)) {
            return REDIRECT;
        } else {
            //判断有没有OEM配置
            String serverName = ServletActionContext.getRequest().getServerName();
            System.out.println(serverName);
            try {
                OEMConfig oemConfig = zeusBusinessDriver.getOEMConfigManager().getOEMConfig(serverName);
                if (oemConfig != null) {
                    oem = oemConfig.getIndexAlias();
                    if (StringUtil.isValid(oem)) {
                        return OEM;
                    }
                }
            } catch (ManagerException e) {
                e.printStackTrace();
            }
        }
        return SUCCESS;
    }

    /**
     * 首次登陆是否需要修改密码
     *
     * @return
     */
    public String changePassword() {

        //原始密码
        int id = DataUtil.getInt(ServletActionContext.getRequest().getParameter("id"), 0);

        try {
            //登陆账号存在
            if (id > 0) {
                logger.info("[MerchantAction][changePassword] id=" + id);
                SimpleAuthorized simpleAuthorized = (SimpleAuthorized) this.securityDriver.getAuthorizedManager().getAuthorized(LC_BELONG_ID, id, AuthTarget.Employee.getTarget());
                logger.info("[MerchantAction][changePassword] authorized=" + JSONObject.fromObject(simpleAuthorized).toString());
                //未重设密码
                if (!simpleAuthorized.isHaveSetPassword()) {
                    this.response.setOk(true);
                } else {
                    this.response.setOk(false);
                }
            } else {
                this.response.setOk(false);
                this.response.setMessage("登陆账号不存在");
            }
        } catch (ManagerException e) {
            e.printStackTrace();
            logger.error(e);
            logger.error(e.getMessage());
            logger.error(e.toString());
            this.response.setOk(false);
            this.response.setMessage(e.getMessage());
        }

        return JSON;
    }

}
