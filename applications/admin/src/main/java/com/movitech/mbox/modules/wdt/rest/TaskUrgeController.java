package com.movitech.mbox.modules.wdt.rest;

import com.movitech.mbox.common.config.Global;
import com.movitech.mbox.common.config.OperateLogEnum;
import com.movitech.mbox.common.utils.CusAccessObjectUtil;
import com.movitech.mbox.common.utils.HttpRequest;
import com.movitech.mbox.common.utils.IdGen;
import com.movitech.mbox.common.utils.JsonUtil;
import com.movitech.mbox.common.web.BaseController;
import com.movitech.mbox.modules.base.vo.HResult;
import com.movitech.mbox.modules.sys.entity.User;
import com.movitech.mbox.modules.sys.utils.UserUtils;
import com.movitech.mbox.modules.wdt.entity.*;
import com.movitech.mbox.modules.wdt.operateLog.service.OperateLogService;
import com.movitech.mbox.modules.wdt.service.*;
import com.movitech.mbox.modules.wdt.taskStatus.req.TaskMailReq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.*;

@Controller
@RequestMapping(value = "${adminPath}/rest/wdt/urgetask")
public class TaskUrgeController extends BaseController {

	@Autowired
	OperateLogService operateLogService;
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private WdtTTaskItemService wdtTTaskItemService;
	
	@Autowired
	ThreadPoolTaskExecutor executor;

	
	/**
	 * 根据任务id，
	 */
	@RequestMapping("getTaskItems")
	@ResponseBody
    public HResult getTaskItems(String id) {
		HResult result=new HResult(true);
		WdtTTaskItem item=new WdtTTaskItem();
		item.setTaskId(id);
		List<WdtTTaskItem> list = wdtTTaskItemService.findByConditions(id,null,null,new Date());
		result.setValue(list);
		return  result;
    }
	/**
	 * 根据任务id，
	 */
	@RequestMapping("saveUrges")
	@ResponseBody
	public HResult list(String taskId,String items,String content,HttpServletRequest request) {
		HResult result=new HResult(true);
		try {
			String[] ids = items.split(",");
			wdtTTaskItemService.addUrge(taskId,items,content);
			for (String string : ids) {
				operateLogService.save(OperateLogEnum.URL20, CusAccessObjectUtil.getIpAddress(request),taskId,string);
			}
			
		}catch (Exception e){
			e.printStackTrace();
			result.setResult(false);
		}
		return  result;
	}

}
