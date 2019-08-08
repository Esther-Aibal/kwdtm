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

import com.movitech.mbox.common.config.OperateLogEnum;
import com.movitech.mbox.common.persistence.Page;
import com.movitech.mbox.common.utils.StringUtils;
import com.movitech.mbox.modules.base.vo.HResult;
import com.movitech.mbox.modules.sys.utils.UserUtils;
import com.movitech.mbox.modules.wdt.entity.WdtTTask;
import com.movitech.mbox.modules.wdt.operateLog.service.OperateLogService;
import com.movitech.mbox.modules.wdt.service.WdtTTaskItemService;
import com.movitech.mbox.modules.wdt.service.WdtTTaskService;
@Controller
@RequestMapping(value = "${adminPath}/rest/wdt/taskChild")
public class TaskChildController {

	@Autowired
	private WdtTTaskService wdtTTaskService;
	
	@Autowired
	private OperateLogService operateLogService;
	
	@Autowired
	private WdtTTaskItemService wdtTTaskItemService;
	/**
	 * 子任务列表
	 */
	@RequestMapping(value = "list")
    public String list(WdtTTask wdtTTask, HttpServletRequest request, HttpServletResponse response, Model model) {
		WdtTTask task = wdtTTaskService.getTask(wdtTTask.getTaskId());
		if(task.getOwner().getId().equals(UserUtils.getUser().getId())){
			model.addAttribute("isTaskOwner", true);
		}else{
			model.addAttribute("isTaskOwner", false);
		}
		model.addAttribute("itemCount",wdtTTaskItemService.getTaskItemByTaskId(wdtTTask.getTaskId()));
		model.addAttribute("childCount",wdtTTaskService.getTaskChildCountByTaskId(wdtTTask.getTaskId()));
		model.addAttribute("task",task);
		return "modules/wdt/wdtTTaskItem/taskChildList";
    }
	
	@RequestMapping(value = "searchChildTask",method = RequestMethod.POST)
	@ResponseBody
    public HResult<Object> searchTask(@RequestBody WdtTTask wdtTTask, HttpServletRequest request, HttpServletResponse response) {
		wdtTTask.setUserId(UserUtils.getUser().getId());
		Page<WdtTTask> page = wdtTTaskService.findPageForChildMyTask(new Page<WdtTTask>(wdtTTask.getStart()/wdtTTask.getLength()+1, wdtTTask.getLength()), wdtTTask); 
		HResult<Object> result = new HResult<>(true);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		result.setValue(map);
		return result;
    }
	/**
	 * 子任务保存
	 */
	@RequestMapping(value = "save")
	@ResponseBody
    public HResult<Object> save(@RequestBody WdtTTask wdtTTask, HttpServletRequest request, HttpServletResponse response, Model model) {
		HResult<Object> result = new HResult<>(true);
		wdtTTaskService.submitChild(wdtTTask);
		operateLogService.save(OperateLogEnum.URL41, StringUtils.getRemoteAddr(request), wdtTTask.getTaskId(), null );
		return result;
    }
	
}
