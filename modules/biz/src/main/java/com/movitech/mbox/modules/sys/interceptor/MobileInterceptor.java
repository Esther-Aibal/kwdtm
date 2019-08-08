/**
 * 
 */
package com.movitech.mbox.modules.sys.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.movitech.mbox.common.utils.CacheUtils;
import com.movitech.mbox.modules.base.vo.HResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.movitech.mbox.common.service.BaseService;
import com.movitech.mbox.common.utils.StringUtils;
import com.movitech.mbox.common.utils.UserAgentUtils;

/**
 * 手机端拦截器用于权限认证
 * @author ThinkGem
 * @version 2014-9-1
 */
public class MobileInterceptor extends BaseService implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
            Object handler) throws Exception {
        //获取请求头中的token,用于权限验证
      /*  String token = request.getHeader("Authorization_Token");
        if(StringUtils.isEmpty(token)){
            Gson gson=new Gson();
            response.getWriter().write(gson.toJson(new HRe));
        }*/
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, 
            ModelAndView modelAndView) throws Exception {
        if (modelAndView != null){
            // 如果是手机或平板访问的话，则跳转到手机视图页面。
            if(UserAgentUtils.isMobileOrTablet(request) && !StringUtils.startsWithIgnoreCase(modelAndView.getViewName(), "redirect:")){
                modelAndView.setViewName("mobile/" + modelAndView.getViewName());
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
            Object handler, Exception ex) throws Exception {
        
    }

}
