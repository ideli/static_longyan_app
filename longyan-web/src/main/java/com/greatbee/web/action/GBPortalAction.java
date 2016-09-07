package com.greatbee.web.action;

import com.greatbee.bean.Portlet;
import com.greatbee.bean.constant.PortletType;
import com.greatbee.manager.GreatbeeDriver;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.util.CollectionUtil;
import com.xiwa.base.util.StringUtil;
import com.xiwa.security.bean.Resource;
import com.xiwa.security.bean.constant.AuthTarget;
import com.xiwa.security.bean.constant.ResourceType;
import com.xiwa.security.bean.constant.ResourceUrl;
import com.xiwa.security.driver.SecurityDriver;
import com.xiwa.security.web.bean.Menu;
import com.xiwa.security.web.common.SecurityTool;
import com.xiwa.zeus.bean.ModuleConfig;
import com.xiwa.zeus.bean.Report;
import com.xiwa.zeus.manager.ZeusDriver;
import com.xiwa.zeus.trinity.bean.Employee;
import com.xiwa.zeus.trinity.bean.EnterpriseUser;
import com.xiwa.zeus.trinity.manager.TrinityDriver;
import com.xiwa.zeus.util.SessionTool;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.support.AdvanceActionSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Portal Action
 *
 * @author CarlChen
 * @version 1.00 2011-2-25 23:45:55
 */
@Scope("prototype")
@Component()
public class GBPortalAction extends AdvanceActionSupport {
    @Autowired
    protected ZeusDriver zeusDriver;
    @Autowired
    protected SecurityDriver securityDriver;
    @Autowired
    protected TrinityDriver trinityDriver;

    private GreatbeeDriver greatbeeDriver;

    private Employee loginEmployee;
    private EnterpriseUser enterpriseUser;
    private AuthTarget authTarget;
    private String htmlTitle;
    private String data_im_host;
    private String data_im_api;

    /**
     * Portal Index
     *
     * @return
     */
    public String index() {
        try {
            boolean isLogin = SecurityTool.isSecurityLogin(null);
            if (isLogin) {
                authTarget = SecurityTool.getSessionAuthTarget();
                if (AuthTarget.Employee.equals(authTarget)) {
                    loginEmployee =
                            (Employee) SessionTool.getSessionDataObject(SessionTool.SESSION_EMPLOYEE);

                    int belongedId =
                            (Integer) SessionTool.getSessionDataObject(SessionTool.Session_Belonged);
                    enterpriseUser = (EnterpriseUser) trinityDriver.getEnterpriseUserManager()
                            .getBean(belongedId);
                    htmlTitle = getText("sns_html_main_title");
//                    data_im_host = imConfig.getIm_host();
//                    data_im_api = imConfig.getIm_api();
                }
                if (_isWeixin(ServletActionContext.getRequest()) || _isMobile(ServletActionContext.getRequest())) {
//                    return "weixin";
                    HttpServletResponse response = ServletActionContext.getResponse();
                    response.sendRedirect("../longyan/auth/index");
                }

                String v = ServletActionContext.getRequest().getParameter("v");
                return StringUtil.isValid(v) ? v : SUCCESS;
            } else {
                return TIMEOUT;
            }
        } catch (Exception e) {
            e.printStackTrace();
            addActionError(getText(e.getMessage()));
            return ERROR;
        }
    }

    /**
     * 生成菜单
     */
    public String generateMenus() {
        String industryCode = ServletActionContext.getRequest().getParameter("industryCode");
        String type = ServletActionContext.getRequest().getParameter("type");
        String resourceUrl = ServletActionContext.getRequest().getParameter("resourceUrl");

        ResourceType rt = null;
        ResourceUrl ru = ResourceUrl.getResourceUrl(resourceUrl);

        if (StringUtil.isValid(type)) {
            rt = ResourceType.getResourceType(type);
        }
        if (rt == null) {
            rt = ResourceType.Page;
        }
        List<Resource> pageResources = SecurityTool.getSessionResourcesByType(rt);

        if (CollectionUtil.isValid(pageResources)) {
            List<Menu> menuList = new ArrayList<Menu>();

            for (Resource r : pageResources) {
                boolean t = true;
                if (ru != null) {
                    if (!ru.getUrl().equalsIgnoreCase(r.getResourceUrl())) {
                        t = false;
                    }
                }

                //根节点判断是否是当前APP
                if (StringUtil.isValid(industryCode)) {
                    Resource parent = (Resource) r.getParentNode();
                    String serviceIndustryCodes = r.getServiceIndustryCodes();
                    if (parent != null &&
                            parent.getId() == Resource.DEFAULT_ROOT_NODE_ID &&
                            StringUtil.isValid(serviceIndustryCodes)) {
                        if (serviceIndustryCodes.indexOf(industryCode) < 0) {
                            t = false;
                        }
                    }
                }
                if (t) {
                    menuList.add(new Menu(r));
                }
            }
            response.addKey("data_list", menuList);
        }
        return JSON;
    }

    /**
     * 生成员工授权的所有菜单，应用于Zeus
     *
     * @return
     */
    public String generateEmployeeMenus() {
        try {
            //必须是Zeus登陆
            if (SecurityTool.isSecurityLogin(AuthTarget.Zeus)) {
                List<Resource> resources = securityDriver.getResourceManager().getAllResources(AuthTarget.Employee);

                List<Menu> menuList = new ArrayList<Menu>();
                for (Resource r : resources) {
                    if (ResourceType.Page.equals(r.getResourceType())) {
                        menuList.add(new Menu(r));
                    }
                }

                response.addKey("data_list", menuList);
            }
        } catch (ManagerException e) {
            response.setOk(false);
            response.setMessage(e.getMessage());
        }
        return JSON;
    }

    /**
     * POrlets
     */
    public String portlets() {
        //TODO
        try {
            List<Report> reports = zeusDriver.getReportManager().getBeanList();

            response.addKey("reports", reports);

//            List<Portlet> systemPortlets = portalDriver.getPortletManager().getPortletList(true);
//            Employee employee = (Employee)SessionTool.getSessionDataObject(SessionTool.SESSION_EMPLOYEE);
//            List<Portlet> employeePortlets = portalDriver.getPortletManager().getEmployeePortletList(employee.getId());
//
//            response.addKey("system_portlets", systemPortlets);
//            response.addKey("employee_portlets", employeePortlets);
        } catch (ManagerException e) {
            response.setOk(false);
            response.setMessage(e.getMessage());
        }
        return JSON;
    }

    /**
     * 添加Portlet
     */
    public String addPortlet() {
        String moduleName = ServletActionContext.getRequest().getParameter("moduleName");

        try {
            Employee employee = (Employee) SessionTool.getSessionDataObject(SessionTool.SESSION_EMPLOYEE);

            ModuleConfig mc = zeusDriver.getModuleConfigManager().getModuleConfig(moduleName);
            Portlet p = new Portlet();
            p.setTitle(mc.getName());
            p.setPortletType(PortletType.Module.getType());
            p.setUrl(mc.getModuleName());
            p.setEmployeeId(employee.getId());
            p.setShowWeb(true);


            greatbeeDriver.getPortletManager().addBean(p);
        } catch (ManagerException e) {
            response.setOk(false);
            response.setMessage(e.getMessage());
        }
        return JSON;
    }

    /**
     * 判断是否来自于微信的请求
     *
     * @param request
     * @return
     */
    private boolean _isWeixin(HttpServletRequest request) {
        String ua = request.getHeader("user-agent")
                .toLowerCase();
        if (ua.indexOf("micromessenger") > 0) {// 是微信浏览器
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否来自于移动端的请求
     *
     * @param request
     * @return
     */
    private boolean _isMobile(HttpServletRequest request) {
        String ua = request.getHeader("user-agent")
                .toLowerCase();
        if (ua.indexOf("android") > 0 || ua.indexOf("iphone") > 0 || ua.indexOf("ipad") > 0) {// 是微信浏览器
            return true;
        } else {
            return false;
        }
    }

    public EnterpriseUser getEnterpriseUser() {
        return enterpriseUser;
    }

    public Employee getLoginEmployee() {
        return loginEmployee;
    }

    public AuthTarget getAuthTarget() {
        return authTarget;
    }

    public String getHtmlTitle() {
        return htmlTitle;
    }

    public String getData_im_api() {
        return data_im_api;
    }

    public String getData_im_host() {
        return data_im_host;
    }
}

