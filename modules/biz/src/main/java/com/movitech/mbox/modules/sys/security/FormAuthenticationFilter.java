/**
 * 
 */
package com.movitech.mbox.modules.sys.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.movitech.mbox.modules.sys.entity.User;
import com.movitech.mbox.modules.sys.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.movitech.mbox.common.utils.StringUtils;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 表单验证（包含验证码）过滤类
 * @author ThinkGem
 * @version 2014-5-19
 */
@Service
public class FormAuthenticationFilter extends org.apache.shiro.web.filter.authc.FormAuthenticationFilter {


    public static final String DEFAULT_CAPTCHA_PARAM = "validateCode";
    public static final String DEFAULT_MOBILE_PARAM = "mobileLogin";
    public static final String DEFAULT_MESSAGE_PARAM = "Message";
    public static final String DEFAULT_TOKEN_PARAM = "token";

    private String captchaParam = DEFAULT_CAPTCHA_PARAM;
    private String mobileLoginParam = DEFAULT_MOBILE_PARAM;
    private String messageParam = DEFAULT_MESSAGE_PARAM;
    @Value("${adminPath}")
    protected String adminPath;
    @Value("${h5_redirect}")
    private String h5_redirect;
    @Autowired
    private UserService userService;
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String username="";
        String password="";//getPassword(request);
        String token=request.getParameter(DEFAULT_TOKEN_PARAM);
        if(StringUtils.isNotEmpty(token)){
            User user = userService.getUserByToken(token);
            if(user!=null) {
                username = user.getLoginName();
                password = user.getLoginName();
            }
        }else {
            username=getUsername(request);
            password=getPassword(request);
        }
      /*  if (password==null){
            password = "";
        }*/

        boolean rememberMe = isRememberMe(request);
        String host = StringUtils.getRemoteAddr((HttpServletRequest)request);
        String captcha = getCaptcha(request);
        boolean mobile = isMobileLogin(request);
        return new UsernamePasswordToken(username, password.toCharArray(), rememberMe, host, captcha, mobile);
    }

    public String getCaptchaParam() {
        return captchaParam;
    }

    protected String getCaptcha(ServletRequest request) {
        return WebUtils.getCleanParam(request, getCaptchaParam());
    }

    public String getMobileLoginParam() {
        return mobileLoginParam;
    }
    
    protected boolean isMobileLogin(ServletRequest request) {
        return WebUtils.isTrue(request, getMobileLoginParam());
    }
    
    public String getMessageParam() {
        return messageParam;
    }
    
    /**
     * 登录成功之后跳转URL
     */
    public String getSuccessUrl() {
        return super.getSuccessUrl();
    }
    
    @Override
    protected void issueSuccessRedirect(ServletRequest request,
            ServletResponse response) throws Exception {
//        Principal p = UserUtils.getPrincipal();
//        if (p != null && !p.isMobileLogin()){
             WebUtils.issueRedirect(request, response, getSuccessUrl(), null, true);
//        }else{
//            super.issueSuccessRedirect(request, response);
//        }
    }

    /**
     * 登录失败调用事件
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token,
            AuthenticationException e, ServletRequest request, ServletResponse response) {
        String className = e.getClass().getName(), message = "";
        if (IncorrectCredentialsException.class.getName().equals(className)
                || UnknownAccountException.class.getName().equals(className)){
            message = "用户或密码错误, 请重试.";
        }
        else if (e.getMessage() != null && StringUtils.startsWith(e.getMessage(), "msg:")){
            message = StringUtils.replace(e.getMessage(), "msg:", "");
        }
        else{
            message = "系统出现点问题，请稍后再试！";
            e.printStackTrace(); // 输出到控制台
        }
        request.setAttribute(getFailureKeyAttribute(), className);
        request.setAttribute(getMessageParam(), message);
        return true;
    }

    @Override
    protected boolean isLoginSubmission(ServletRequest request, ServletResponse response) {
        String token=request.getParameter(DEFAULT_TOKEN_PARAM);
        if(StringUtils.isNotEmpty(token))return true;
        else return  super.isLoginSubmission(request,response);
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        //第一次登录成功

        String url=adminPath+"/login";
        String page=request.getParameter("page");
        String h5=request.getParameter("mobile");
        UsernamePasswordToken usertoken=(UsernamePasswordToken)token;
        if("1".equals(page)){
            //跳转到首页
            url= adminPath+"/rest/wdt/homePage";
        }else if("2".equals(page)){
            //跳转到任务详情
            String taskId=(String)request.getParameter("taskId");
            url= adminPath+"/rest/wdt/task/taskDetail?id="+taskId;

        }else if("3".equals(page)){
            //跳转到代办列表
            url= adminPath+"/home/todoListAll";
        }else if("4".equals(page)){
            //跳转到消息列表
            url= adminPath+"/home/msgListAll";
        }else if("5".equals(page)){
            //跳转任务列表根据不同的type跳转
            String type=request.getParameter("taskType");
            url=  adminPath+"/home/taskListAll?taskType="+type;
        }
        if("h5".equals(h5)){
            url=h5_redirect;
        }
        WebUtils.getAndClearSavedRequest(request);
        WebUtils.redirectToSavedRequest(request,response,url);
        return false;
    }
}