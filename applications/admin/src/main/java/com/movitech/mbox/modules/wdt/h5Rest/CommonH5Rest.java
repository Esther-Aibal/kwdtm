package com.movitech.mbox.modules.wdt.h5Rest;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.movitech.mbox.common.web.BaseController;
import com.movitech.mbox.modules.base.vo.HResult;
import com.movitech.mbox.modules.sys.entity.User;
import com.movitech.mbox.modules.sys.service.SystemService;
import com.movitech.mbox.modules.sys.utils.UserUtils;
import com.movitech.mbox.modules.wdt.entity.ext.OfficeUser;

import net.sf.json.JSONArray;
@Controller
public class CommonH5Rest extends BaseController {

	
	/**
	 * 选择部门
	 */
	
	@Autowired
	private SystemService systemService;
	
	@RequestMapping("${adminPath}/h5/common/getPersonByOffice")
	@ResponseBody
	public HResult<Map<String,Object>> getPersonByOffice(@RequestBody OfficeUser officeUser){
		List<User> list = systemService.findUserByOfficeId(officeUser.getOfficeId(),officeUser.getUseId(),officeUser.getType());
		HResult<Map<String,Object>> result = new HResult<Map<String,Object>>(true);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("users", list);
		result.setValue(map);
		return result;
		
	}
	
	@RequestMapping("${adminPath}/h5/common/getPersonByName")
	@ResponseBody
	public HResult<Map<String,Object>> getPersonByName(@RequestBody OfficeUser officeUser){
		List<Map<String,Object>> list = systemService.findOfficeByUsername(officeUser.getUserName(),officeUser.getUseId(),officeUser.getType());
		HResult<Map<String,Object>> result = new HResult<Map<String,Object>>(true);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("users", list);
		result.setValue(map);
		return result;
		
	}
	@RequestMapping("${adminPath}/h5/common/findAllOffice")
	@ResponseBody
	public HResult<Map<String,Object>> findAllOffice(){
		JSONArray array = UserUtils.findAllOffice();
		HResult<Map<String,Object>> result = new HResult<Map<String,Object>>(true);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("offices", array);
		result.setValue(map);
		return result;
		
	}
	
	@RequestMapping("${adminPath}/h5/common/menus")
	@ResponseBody
	public HResult<Map<String,Object>> menus(){
		HResult<Map<String,Object>> result = new HResult<Map<String,Object>>(true);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("menus", UserUtils.getH5MenuList());
		result.setValue(map);
		return result;
		
	}
}
