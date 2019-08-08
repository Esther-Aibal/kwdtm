package com.movitech.mbox.modules.wdt.rest;

import com.google.gson.Gson;
import com.movitech.mbox.common.config.ExecutionStatusEnum;
import com.movitech.mbox.common.config.FqcyEnum;
import com.movitech.mbox.common.config.Global;
import com.movitech.mbox.common.persistence.Page;
import com.movitech.mbox.common.utils.IdGen;
import com.movitech.mbox.common.utils.StringUtils;
import com.movitech.mbox.common.web.BaseController;
import com.movitech.mbox.modules.base.vo.HResult;
import com.movitech.mbox.modules.sys.entity.User;
import com.movitech.mbox.modules.sys.service.UserService;
import com.movitech.mbox.modules.sys.utils.UserUtils;
import com.movitech.mbox.modules.wdt.entity.WdtTTask;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskAttemtion;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskItem;
import com.movitech.mbox.modules.wdt.service.WdtTTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.apache.commons.codec.binary.Base64;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "${adminPath}/rest/token")
public class TokenTaskController extends BaseController {
	@Autowired
	UserService userService;
	@Autowired
	WdtTTaskService  wdtTTaskService;
	@Value("${task_detail_url}")
	private  String TASK_DETAIL_URL;
	@RequestMapping(value = "getToken")
	@ResponseBody
    public HResult<Object> getToken(String loginname) throws  Exception{
		HResult<Object> result = new HResult<>(true);
		String login=new String(Base64.decodeBase64(loginname.getBytes("utf-8")), "utf-8");
		Map<String, Object> map = new HashMap<String, Object>();
		User user = userService._getUserByLoginName(login);
		if(user==null){
			result.setMessage("this the Account does not exist");
			result.setResult(false);
		}else {
			result.setMessage("OK");
			result.setResult(true);
			if(StringUtils.isEmpty(user.getToken())){
				String token= IdGen.uuid();
				user.setToken(token);
				userService.updateUser(user);
				map.put("token",token);
			}else {
				map.put("token",user.getToken());
			}
			result.setUrl(adminPath+"/login");
		}
		result.setValue(map);
		
		return result;
    }

	/**
	 *
	 * @param request
	 * @param response
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "taskList")
	@ResponseBody
	public HResult<Object> taskIndex(HttpServletRequest request,HttpServletResponse response,String token) {
		User user = userService.getUserByToken(token);
		HResult result=new HResult(false);
		if(user!=null){
           String userId=user.getId();
           try {
			   Map<String,Object> map=wdtTTaskService.getTaskForOAHome(userId,null, new Page(0, Global.HOME_PAGE_SHOW_ITEM_COUNT, -1));
			   result.setResult(true);
			   result.setValue(map);
			   result.setUrl(adminPath+"/login");
		   }catch (Exception e){
           	   e.printStackTrace();
		   }
		}else {
			result.setMessage("token is invalid !");
		}
        return  result;
	}
	@RequestMapping(value = "taskAnotation")
	@ResponseBody
	public HResult<Object> taskAnotion(HttpServletRequest request,HttpServletResponse response,String token) {
		User user = userService.getUserByToken(token);
		HResult result=new HResult(false);
		if(user!=null){
			String userId=user.getId();
			Map<String,Object> map=wdtTTaskService.getTaskAnotationForOAHome(userId);
			result.setResult(true);
			result.setValue(map);
			result.setUrl(adminPath+"/login");
		}else {
			result.setMessage("token is invalid !");
		}
		return  result;
	}
	@RequestMapping(value = "taskMesssgeOrtodo")
	@ResponseBody
	public HResult<Object> getMesssgeOrtodo(HttpServletRequest request,HttpServletResponse response,String token){
		User user = userService.getUserByToken(token);
		HResult result=new HResult(false);
		if(user!=null){
			String userId=user.getId();
			Map<String,Object> map=wdtTTaskService.getMesssgeOrtodo(userId);
			result.setResult(true);
			result.setValue(map);
			result.setUrl(adminPath+"/login");
		}else {
			result.setMessage("token is invalid !");
		}
		return  result;
	}
	/**
	 * 迈安获取任务详情的url地址
	 * @param id
	 * @return
	 */
	@RequestMapping("getTaskUrl")
	@ResponseBody
	public HResult taskUrl(@RequestParam(value = "id", required = false) String id,String loginname,HttpServletRequest request,HttpServletResponse response)throws Exception{
		HResult<Object> result=new HResult<Object>(true);
		if(StringUtils.isEmpty(id)|| StringUtils.isEmpty(loginname)){
			result.setResult(false);
			result.setMessage("this loginname or id is not existing or empty id ,try later");
			return result;
		}
		//获得用户id
		String l_name=new String(Base64.decodeBase64(loginname.getBytes("utf-8")), "utf-8");
		List<WdtTTask> taskByUserIdOrOwerId = wdtTTaskService.getTaskByUserIdOrOwerId(null, id);
		if(taskByUserIdOrOwerId!=null&&taskByUserIdOrOwerId.size()>0){
			WdtTTask wdtTTask=taskByUserIdOrOwerId.get(0);
			if(!l_name.equals(wdtTTask.getTaskCreateUser().getId())){
				result.setResult(false);
				result.setMessage("this loginname is not owner id ,try later");
			}else {
				result.setResult(true);
				result.setUrl(TASK_DETAIL_URL+adminPath+"/rest/token/taskDetail?id="+id);
			}
		}
		return  result;

	}
	/**
	 * 任务详情,迈安调用接口详情
	 * @param id
	 * @return
	 */
	@RequestMapping("taskDetail")
	public ModelAndView taskDetail(@RequestParam(value = "id", required = false) String id)throws Exception{
		WdtTTask wdtTTask = wdtTTaskService.getTaskDetailByTaskId(id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("wdtTTask", wdtTTask);
		//查看时增加阅读数
		//wdtTTaskReadCountService.updateReadCountByTaskId(id);
		return new ModelAndView("modules/wdt/wdtTTaskItem/taskDetail2MA", map);
	}

}
