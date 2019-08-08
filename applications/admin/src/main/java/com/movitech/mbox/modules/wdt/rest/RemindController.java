package com.movitech.mbox.modules.wdt.rest;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.movitech.mbox.common.config.ErrorMessage;
import com.movitech.mbox.common.config.OperateLogEnum;
import com.movitech.mbox.common.persistence.Page;
import com.movitech.mbox.common.utils.StringUtils;
import com.movitech.mbox.modules.base.vo.HResult;
import com.movitech.mbox.modules.sys.utils.UserUtils;
import com.movitech.mbox.modules.wdt.entity.WdtTTask;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskRemind;
import com.movitech.mbox.modules.wdt.entity.ext.AtMessage;
import com.movitech.mbox.modules.wdt.service.WdtTTaskRemindService;

@Controller
@RequestMapping(value = "${adminPath}/remind")
public class RemindController {

	@Autowired
	private WdtTTaskRemindService wdtTTaskRemindService;
	/**
	 * 首页之任务列表
	 */
	@RequestMapping(value = "remindListAll", method = RequestMethod.GET)
	public String remindListAll(String endDate,Model model) {
		return "modules/wdt/home/remindListAll";
	}
	
	@RequestMapping(value = "searchRemind",method = RequestMethod.POST)
	@ResponseBody
    public HResult<Object> searchRemind( @RequestBody WdtTTaskRemind wdtTTaskRemind, HttpServletRequest request, HttpServletResponse response) {
		wdtTTaskRemind.setInputPerson(UserUtils.getUser().getId());
		Page<WdtTTaskRemind> page = wdtTTaskRemindService.findPage(new Page<WdtTTaskRemind>(wdtTTaskRemind.getStart()/wdtTTaskRemind.getLength()+1, wdtTTaskRemind.getLength()), wdtTTaskRemind); 
		HResult<Object> result = new HResult<>(true);
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("page", page);
		result.setValue(map);
		return result;
    }
	
	@RequestMapping(value = "saveRemind", method = RequestMethod.POST)
    @ResponseBody
    public com.movitech.mbox.common.rest.HResult<Object> saveRemind(@RequestBody WdtTTaskRemind wdtTTaskRemind, HttpServletRequest request) {
		wdtTTaskRemind.setInputPerson(UserUtils.getUser().getId());
		wdtTTaskRemindService.save(wdtTTaskRemind);
    	return com.movitech.mbox.common.rest.HResult.success();
    }
	
/*	@RequestMapping(value = "doneRemind", method = RequestMethod.POST)
    @ResponseBody
    public com.movitech.mbox.common.rest.HResult<Object> doneRemind(@RequestBody WdtTTaskRemind wdtTTaskRemind, HttpServletRequest request) {

		int flag = wdtTTaskRemindService.doneRemind(wdtTTaskRemind);
		if(flag>0){
			
			return com.movitech.mbox.common.rest.HResult.success();
		}else{
			return com.movitech.mbox.common.rest.HResult.fail("该提醒已办");	
		}
    }*/
}
