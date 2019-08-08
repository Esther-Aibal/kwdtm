package com.movitech.mbox.modules.sys.security;

import com.movitech.mbox.modules.sys.utils.UserUtils;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Created by gorden on 2017/8/11.
 */
@Service("logoutFilter")
public class LogoutFilter extends org.apache.shiro.web.filter.authc.LogoutFilter{
    /**
     * 管理基础路径
     */
    private static final Logger log = LoggerFactory.getLogger(org.apache.shiro.web.filter.authc.LogoutFilter.class);
    @Value("${adminPath}")
    protected String adminPath;
    @Override
    public String getRedirectUrl() {
        return adminPath+"/login";
    }
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = this.getSubject(request, response);
        String redirectUrl = this.getRedirectUrl(request, response, subject);

        try {
            //添加业务业务逻辑
            UserUtils.clearCache();
            subject.logout();
        } catch (SessionException var6) {
            log.debug("Encountered session exception during logout.  This can generally safely be ignored.", var6);
        }
        this.issueRedirect(request, response, redirectUrl);
        return false;
    }

}
