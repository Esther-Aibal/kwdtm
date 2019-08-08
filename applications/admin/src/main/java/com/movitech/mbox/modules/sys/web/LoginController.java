/**
 * 
 */
package com.movitech.mbox.modules.sys.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.collect.Maps;
import com.movitech.mbox.common.config.Global;
import com.movitech.mbox.common.security.shiro.session.SessionDAO;
import com.movitech.mbox.common.servlet.ValidateCodeServlet;
import com.movitech.mbox.common.utils.CacheUtils;
import com.movitech.mbox.common.utils.CookieUtils;
import com.movitech.mbox.common.utils.IdGen;
import com.movitech.mbox.common.utils.StringUtils;
import com.movitech.mbox.common.web.BaseController;
import com.movitech.mbox.modules.sys.security.FormAuthenticationFilter;
import com.movitech.mbox.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.movitech.mbox.modules.sys.utils.UserUtils;
import com.movitech.mbox.modules.sysurl.service.RxUrlService;

/**
 * 登录Controller
 * @author ThinkGem
 * @version 2013-5-31
 */
@Controller
public class LoginController extends BaseController{
    
    @Autowired
    private SessionDAO sessionDAO;

    
    @SuppressWarnings("unused")
	@Autowired
    private RxUrlService rxUrlService;
    //佳兆业门户跳转控制
    @Value("${h5_redirect}")
    private String h5_redirect;
    /**
     * 管理登录
     */
/*    @RequestMapping(value = "${adminPath}/logout", method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
        Principal principal = UserUtils.getPrincipal();

//        // 默认页签模式
//        String tabmode = CookieUtils.getCookie(request, "tabmode");
//        if (tabmode == null){
//            CookieUtils.setCookie(response, "tabmode", "1");
//        }
        
        if (logger.isDebugEnabled()){
            logger.debug("login, active session size: {}", sessionDAO.getActiveSessions(false).size());
        }
        
        // 如果已登录，再次访问主页，则退出原账号。
        if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))){
            CookieUtils.setCookie(response, "LOGINED", "false");
        }
        
        // 如果已经登录，则跳转到管理首页
        if(principal != null && !principal.isMobileLogin()){
            return "redirect:" + adminPath;
        }
        return "modules/sys/sysLogin";
    }*/

    /**
     * 登录失败，真正登录的POST请求由Filter完成
     */
    @RequestMapping(value = "${adminPath}/login")
    public String loginFail(HttpServletRequest request, HttpServletResponse response, Model model) {
        Principal principal = UserUtils.getPrincipal();
        
        // 如果已经登录，则跳转到管理首页
        if(principal != null){
            String page=request.getParameter("page");
            String h5=request.getParameter("mobile");
            //logger.info("LOGIN_LOG"+principal.getLoginName() +"已成功登陆。page="+page+"mobile="+h5+"taskType="+request.getParameter("taskType"));
            if("1".equals(page)){
                //跳转到首页
                return "redirect:" + adminPath+"/rest/wdt/homePage";
            }else if("2".equals(page)){
                //跳转到任务详情
                String taskId=request.getParameter("taskId");
                return "redirect:" +adminPath+"/rest/wdt/task/taskDetail?id="+taskId;

            }else if("3".equals(page)){
                //跳转到代办列表
                return "redirect:" +adminPath+"/home/todoListAll";
            }else if("4".equals(page)){
                //跳转到消息列表
                return "redirect:" +adminPath+"/home/msgListAll";
            }else if("5".equals(page)){
                //跳转任务列表根据不同的type跳转
                String type=request.getParameter("taskType");
                return "redirect:" +adminPath+"/home/taskListAll?taskType="+type;
            }
            if("h5".equals(h5)){
               return  "redirect:"+h5_redirect;
            }
            return "forward:" + adminPath+"/rest/wdt/homePage";
           // return "redirect:http://127.0.0.1:8080/manager";
        }
        
        String username = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
        boolean rememberMe = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM);
        boolean mobile = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_MOBILE_PARAM);
        String exception = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        String message = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM);
        
        if (StringUtils.isBlank(message) || StringUtils.equals(message, "null")){
            message = "用户或密码错误, 请重试.";
        }

        model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
        model.addAttribute(FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM, rememberMe);
        model.addAttribute(FormAuthenticationFilter.DEFAULT_MOBILE_PARAM, mobile);
        model.addAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, exception);
        model.addAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM, message);
        
        if (logger.isDebugEnabled()){
            logger.debug("login fail, active session size: {}, Message: {}, exception: {}",
                    sessionDAO.getActiveSessions(false).size(), message, exception);
        }
        
        // 非授权异常，登录失败，验证码加1。
        if (!UnauthorizedException.class.getName().equals(exception)){
            model.addAttribute("isValidateCodeLogin", isValidateCodeLogin(username, true, false));
        }
        
        // 验证失败清空验证码
        request.getSession().setAttribute(ValidateCodeServlet.VALIDATE_CODE, IdGen.uuid());
        //logger.info("LOGIN_LOG登陆失败。message="+message+"page="+request.getParameter("page")+"mobile="+request.getParameter("mobile")+"taskType="+request.getParameter("taskType"));
        // 如果是手机登录，则返回JSON字符串
        if (mobile){
            return renderString(response, model);
        }
        
        return "modules/sys/sysLogin";
    }

    /**
     * 登录成功，进入管理首页
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "${adminPath}")
    public String index(HttpServletRequest request, HttpServletResponse response) {
        Principal principal = UserUtils.getPrincipal();

        // 登录成功后，验证码计算器清零
        isValidateCodeLogin(principal.getLoginName(), false, true);
        
        if (logger.isDebugEnabled()){
            logger.debug("show index, active session size: {}", sessionDAO.getActiveSessions(false).size());
        }
        
        // 如果已登录，再次访问主页，则退出原账号。
        if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))){
            String logined = CookieUtils.getCookie(request, "LOGINED");
            if (StringUtils.isBlank(logined) || "false".equals(logined)){
                CookieUtils.setCookie(response, "LOGINED", "true");
            }else if (StringUtils.equals(logined, "true")){
                UserUtils.getSubject().logout();
                return "redirect:" + adminPath + "/login";
            }
        }
        
        // 如果是手机登录，则返回JSON字符串
        if (principal.isMobileLogin()){
            if (request.getParameter("login") != null){
                return renderString(response, principal);
            }
            if (request.getParameter("index") != null){
                return "modules/sys/sysIndex";
            }
            return "redirect:" + adminPath + "/login";
        }
        
//        // 登录成功后，获取上次登录的当前站点ID
//        UserUtils.putCache("siteId", StringUtils.toLong(CookieUtils.getCookie(request, "siteId")));

//        System.out.println("==========================a");
//        try {
//            byte[] bytes = com.movitech.mbox.common.utils.FileUtils.readFileToByteArray(
//                    com.movitech.mbox.common.utils.FileUtils.getFile("c:\\sxt.dmp"));
//            UserUtils.getSession().setAttribute("kkk", bytes);
//            UserUtils.getSession().setAttribute("kkk2", bytes);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
////        for (int i=0; i<1000000; i++){
////            //UserUtils.getSession().setAttribute("a", "a");
////            request.getSession().setAttribute("aaa", "aa");
////        }
//        System.out.println("==========================b");
        return "modules/sys/sysIndex";
    }
    
    /**
     * 获取主题方案
     */
    @RequestMapping(value = "/theme/{theme}")
    public String getThemeInCookie(@PathVariable String theme, HttpServletRequest request, HttpServletResponse response){
        if (StringUtils.isNotBlank(theme)){
            CookieUtils.setCookie(response, "theme", theme);
        }else{
        	String temp = CookieUtils.getCookie(request, "theme");
            theme = temp;
        }
        return "redirect:"+request.getParameter("url");
    }
    
    /**
     * 是否是验证码登录
     * @param useruame 用户名
     * @param isFail 计数加1
     * @param clean 计数清零
     * @return
     */
    @SuppressWarnings("unchecked")
    public static boolean isValidateCodeLogin(String useruame, boolean isFail, boolean clean){
        Map<String, Integer> loginFailMap = (Map<String, Integer>)CacheUtils.get("loginFailMap");
        if (loginFailMap==null){
            loginFailMap = Maps.newHashMap();
            CacheUtils.put("loginFailMap", loginFailMap);
        }
        Integer loginFailNum = loginFailMap.get(useruame);
        if (loginFailNum==null){
            loginFailNum = 0;
        }
        if (isFail){
            loginFailNum++;
            loginFailMap.put(useruame, loginFailNum);
        }
        if (clean){
            loginFailMap.remove(useruame);
        }
        return loginFailNum >= 3;
    }




}
