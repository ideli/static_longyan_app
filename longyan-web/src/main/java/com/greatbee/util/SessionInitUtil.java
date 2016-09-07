package com.greatbee.util;

import com.greatbee.security.util.SecurityTool;
import com.lanchui.commonBiz.bean.RedstarEmployee;
import com.lanchui.commonBiz.bean.RedstarSession;
import com.lanchui.commonBiz.manager.DispatchDriver;
import com.xiwa.base.cmd.SessionTimeoutException;
import com.xiwa.base.manager.ManagerException;
import com.xiwa.base.util.ArrayUtil;
import com.xiwa.base.util.StringUtil;
import com.xiwa.security.bean.Authorized;
import com.xiwa.security.bean.Resource;
import com.xiwa.security.bean.Role;
import com.xiwa.security.bean.constant.AuthTarget;
import com.xiwa.security.driver.SecurityDriver;
import com.xiwa.zeus.trinity.bean.Employee;
import com.xiwa.zeus.trinity.bean.EnterpriseUser;
import com.xiwa.zeus.trinity.manager.TrinityDriver;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by lenovo on 2016/5/5.
 */
public class SessionInitUtil  {

   public  static  void sessionInit(SecurityDriver securityDriver,TrinityDriver trinityDriver,DispatchDriver dispatchDriver,RedstarSession session,HttpServletResponse resp) throws ManagerException {

       Authorized authorized = securityDriver.getAuthorizedManager().getAuthorized(10944,session.getObjectId(),"employee");

       if(authorized==null||(!authorized.isActive())){
           throw new SessionTimeoutException("error_session_timeout");
       }

       Employee employee = (Employee) trinityDriver.getBasicEmployeeManager().getBean(authorized.getObjectId());

       if(employee==null){
           throw new SessionTimeoutException("error_session_timeout");
       }

       if (!StringUtil.isValid(authorized.getRoles())) {
           //throw new ManagerException("没有对应的角色");
           throw new SessionTimeoutException("error_session_timeout");
       } else {
           int[] roleIds = StringUtil.toIntArray(authorized.getRoles(), ',');
           List roles =securityDriver.getRoleManager().getBeanList(roleIds);
           boolean isSuper = false;
           Iterator resources = roles.iterator();

           while (resources.hasNext()) {
               Role ent = (Role) resources.next();
               if (ent.isSupperRole()) {
                   isSuper = true;
                   break;
               }
           }

           new ArrayList();
           List reources;
           if (isSuper) {
               reources = securityDriver.getResourceManager().getAllResources(AuthTarget.Employee);
           } else {
               ArrayList var18 = new ArrayList();
               Iterator entIC = roles.iterator();

               while (entIC.hasNext()) {
                   Role isEntIC = (Role) entIC.next();
                   int[] shelfResources = StringUtil.toIntArray(isEntIC.getResources(), ',');
                   if (ArrayUtil.isValid(shelfResources)) {
                       int[] i$ = shelfResources;
                       int r = shelfResources.length;

                       for (int i$1 = 0; i$1 < r; ++i$1) {
                           int rId = i$[i$1];
                           if (!var18.contains(Integer.valueOf(rId))) {
                               var18.add(Integer.valueOf(rId));
                           }
                       }
                   }
               }

               reources = securityDriver.getResourceManager().getResourceList(ArrayUtil.toArray(var18));
           }

           EnterpriseUser enterpriseUser = (EnterpriseUser) trinityDriver.getEnterpriseUserManager().getBean(authorized.getBelongedId());
           String industryCode = enterpriseUser.getIndustryCode();
           boolean valid = StringUtil.isValid(industryCode);
           ArrayList loginResources = new ArrayList();
           Iterator var22 = reources.iterator();

           while (var22.hasNext()) {
               Resource resource = (Resource) var22.next();
               if (resource.isShelf() && AuthTarget.Employee.getTarget().equalsIgnoreCase(resource.getAuthTarget())) {
                   loginResources.add(resource);
               }
           }
           SecurityTool.initSecuritySession(AuthTarget.Employee, authorized.getBelongedId(), loginResources, employee, employee.getId(),null,null,null, dispatchDriver.getRedstarSessionManager(), resp);

           //计算活跃时间逻辑===START====
           try{
               RedstarEmployee redstarEmployee= (RedstarEmployee) dispatchDriver.getRedstarEmployeeManager().getBean(employee.getId());
               redstarEmployee.setLastActiveTime(new Date(System.currentTimeMillis()));
               dispatchDriver.getRedstarEmployeeManager().updateBean(redstarEmployee);
           }catch (ManagerException e){
               e.printStackTrace();
           }
           //计算活跃时间逻辑====END====
           /*LogUtil.log("login", (String) null, "用户登陆", (OperationType) null, securityDriver.getOperationLogManager());trinityDriver.getBasicEmployeeManager().updateEmployeeActiveTime(employee.getId());*/
       }

   }


}
