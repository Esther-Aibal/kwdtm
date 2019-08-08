package com.movitech.mbox.modules.wdt.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.movitech.mbox.common.web.BaseController;
import com.movitech.mbox.modules.base.vo.HResult;
import com.movitech.mbox.modules.sys.entity.User;
import com.movitech.mbox.modules.sys.service.SystemService;
@Controller
public class CommonController extends BaseController {

	
	/**
	 * 选择部门
	 */
	
	@Autowired
	private SystemService systemService;
	
	@RequestMapping("${adminPath}/common/getPersonByOffice")
	@ResponseBody
	public HResult<Map<String,Object>> getPersonByOffice(String officeId,String useId,String type){
		List<User> list = systemService.findUserByOfficeId(officeId,useId,type);
		HResult<Map<String,Object>> result = new HResult<Map<String,Object>>(true);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("users", list);
		result.setValue(map);
		return result;
		
	}
	
	@RequestMapping("${adminPath}/common/getPersonByName")
	@ResponseBody
	public HResult<Map<String,Object>> getPersonByName(String name,String useId,String type){
		List<Map<String,Object>> list = systemService.findOfficeByUsername(name,useId,type);
		HResult<Map<String,Object>> result = new HResult<Map<String,Object>>(true);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("users", list);
		result.setValue(map);
		return result;
		
	}
	@RequestMapping("${adminPath}/common/getPersonByNameOrOfficeName")
	@ResponseBody
	public HResult<Map<String,Object>> getPersonByNameOrOfficeName(String name,String userIds){
		HResult<Map<String,Object>> result = new HResult<Map<String,Object>>(true);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("users", systemService.findUsersByUsername(name,userIds));
		map.put("offices", systemService.findOfficeByOfficeName(name, userIds));
		result.setValue(map);
		return result;
		
	}
}
