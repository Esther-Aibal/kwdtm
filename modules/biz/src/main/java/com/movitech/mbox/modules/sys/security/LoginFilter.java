package com.movitech.mbox.modules.sys.security;

import com.movitech.mbox.common.utils.CacheUtils;
import com.movitech.mbox.common.utils.StringUtils;
import com.movitech.mbox.modules.sys.dao.RoleDao;
import com.movitech.mbox.modules.sys.entity.Role;
import com.movitech.mbox.modules.sys.entity.User;
import com.movitech.mbox.modules.sys.service.SystemService;
import com.movitech.mbox.modules.sys.service.UserService;
import com.movitech.mbox.modules.sys.utils.UserUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.AdviceFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by gorden on 2017/9/19.
 */
@Service("loginFilter")
public class LoginFilter extends AdviceFilter{
    @Autowired
    UserService userService;
    @Autowired
    RoleDao roleDao;
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req=(HttpServletRequest)request;
        if (req.getRequestURI().contains("login")){
           /* if(UserUtils.getSubject().isAuthenticated()) {*/

               /* systemService.getSessionDao().delete(UserUtils.getSession());*/
              // UserUtils.getSubject().logout();
           /* }*/
          /*  Cookie[] cookies = req.getCookies();
            if(cookies!=null&&cookies.length>0){
                for (Cookie cookie:cookies)
                    cookie.setMaxAge(-1);
            }*/

           /* Collection<Session> sessions = systemService.getSessionDao().getActiveSessions(true, UserUtils.getSubject(), UserUtils.getSession());
            if (sessions.size() > 0) {
                // 如果是登录进来的，则踢出已在线用户
                if (UserUtils.getSubject().isAuthenticated()) {
                    for (Session session : sessions) {
                        systemService.getSessionDao().delete(UserUtils.getSession());
                    }
                }
            }*/
           // systemService.getSessionDao().delete(UserUtils.getSession());
            //ThreadContext.remove();
           String token=req.getParameter("token");
            if(StringUtils.isNotEmpty(token)) {
                User user = userService.getUserByToken(token);
                if (UserUtils.getSubject().isAuthenticated()) {
                    user.setRoleList(roleDao.findList(new Role(user)));
                    CacheUtils.put(UserUtils.USER_CACHE, UserUtils.USER_CACHE_ID_ + UserUtils.getPrincipal().getId(), user);
                    CacheUtils.put(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LOGIN_NAME_ + UserUtils.getPrincipal().getLoginName(), user);
                }
            }
        }
        return super.preHandle(req, response);
    }
}
