package com.movitech.mbox.modules.sys.security;

import com.movitech.mbox.common.utils.StringUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by gorden on 2017/8/10.
 */
@Service
public class GetUrlAuthenticationFilter extends AuthenticatingFilter{
    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        String username =servletRequest.getParameter("username");
        String password = username;//getPassword(request);
        boolean rememberMe = isRememberMe(servletRequest);
        String host = StringUtils.getRemoteAddr((HttpServletRequest)servletRequest);
        /*String captcha = getCaptcha(request);
        boolean mobile = isMobileLogin(request);*/
        return new UsernamePasswordToken(username, password.toCharArray(), rememberMe, host, null, false);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        return false;
    }
}
