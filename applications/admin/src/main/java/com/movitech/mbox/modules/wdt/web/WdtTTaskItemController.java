/**
 * 
 */
package com.movitech.mbox.modules.wdt.web;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.movitech.mbox.common.utils.DateUtils;
import com.movitech.mbox.common.web.BaseController;
import com.movitech.mbox.modules.sys.entity.User;
import com.movitech.mbox.modules.sys.utils.UserUtils;
import com.movitech.mbox.modules.wdt.entity.WdtTTask;
import com.movitech.mbox.modules.wdt.entity.ext.ExtWdtTTaskItem;
import com.movitech.mbox.modules.wdt.service.WdtTTaskItemService;
import com.movitech.mbox.modules.wdt.service.WdtTTaskService;

/**
 * 节点Controller
 * 
 * @author felix
 * @version 2017-07-25
 */
@Controller
@RequestMapping(value = "${adminPath}/wdtTTaskItem")
public class WdtTTaskItemController extends BaseController {

	@Autowired
	private WdtTTaskItemService wdtTTaskItemService;
	
	@Autowired
	private WdtTTaskService wdtTTaskService;

	@RequestMapping(value = { "list", "" })
	public String list(@RequestParam String taskId, Model model) {
		User user = UserUtils.getUser();
		WdtTTask task=wdtTTaskItemService.getTask(taskId);
		model.addAttribute("isTaskOwner", wdtTTaskItemService.isOwner(taskId,user.getId()));
		if(task!=null){
			model.addAttribute("taskId", task.getId());
			model.addAttribute("taskName", task.getName());
			model.addAttribute("taskStatus", task.getExecutionStatus());
			model.addAttribute("startDay",DateUtils.formatDate(task.getStartDate()) );
			model.addAttribute("endDay", DateUtils.formatDate(task.getEndDate()));
			model.addAttribute("isParent", wdtTTaskItemService.isParent(task.getId()));
			model.addAttribute("itemCount",wdtTTaskItemService.getTaskItemByTaskId(task.getId()));
			model.addAttribute("childCount",wdtTTaskService.getTaskChildCountByTaskId(task.getId()));
			wdtTTaskItemService.upOvertimeTaskItem(task.getId());
			wdtTTaskItemService.upInprogressTaskItem(task.getId());
		}
		return "modules/wdt/wdtTTaskItem/taskNodeList";
	}

	@RequestMapping(value = "info")
	public String info(@RequestParam String id, Model model) {
		User user = UserUtils.getUser();
		ExtWdtTTaskItem wdtTTaskItem = wdtTTaskItemService.getInfo(id);
		int planDay = DateUtils.subDate(wdtTTaskItem.getStartDate(),wdtTTaskItem.getRequiredCompletionTime()) + 1;
		int surplusDay = DateUtils.subDate(new Date(),wdtTTaskItem.getRequiredCompletionTime()) + 1;
		if(String.valueOf(surplusDay).contains("-")) {
			surplusDay=0;
		}
		model.addAttribute("wdtTTaskItem", wdtTTaskItem);
		model.addAttribute("planDay", planDay);
		model.addAttribute("surplusDay", surplusDay);
		model.addAttribute("isTaskOwner", wdtTTaskItemService.isOwner(wdtTTaskItem.getTaskId(),user.getId()));
		model.addAttribute("isTaskItemOwner", user.getId().equals(wdtTTaskItem.getOwnerId()));
		WdtTTask task=wdtTTaskItemService.getTask(wdtTTaskItem.getTaskId());
		model.addAttribute("taskStatus", task.getExecutionStatus());
		model.addAttribute("taskName",task.getName());
		
		User currentUser = UserUtils.getUser();
		WdtTTask wdtTTask =wdtTTaskService.getTaskDetailByTaskId(wdtTTaskItem.getTaskId());
		model.addAttribute("wdtTTask",wdtTTask);
		return "modules/wdt/wdtTTaskItem/taskNodeDetail";
	}

}