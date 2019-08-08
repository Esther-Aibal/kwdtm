package com.movitech.mbox.modules.wdt.rest;

import com.movitech.mbox.common.config.ErrorMessage;
import com.movitech.mbox.common.config.ExecutionStatusEnum;
import com.movitech.mbox.common.config.FqcyEnum;
import com.movitech.mbox.common.config.TaskImportantTypeEnum;
import com.movitech.mbox.common.persistence.Page;
import com.movitech.mbox.common.utils.DateUtils;
import com.movitech.mbox.common.utils.IdGen;
import com.movitech.mbox.common.utils.StringUtils;
import com.movitech.mbox.common.web.BaseController;
import com.movitech.mbox.modules.base.vo.DataTableResult;
import com.movitech.mbox.modules.base.vo.HResult;
import com.movitech.mbox.modules.sys.entity.User;
import com.movitech.mbox.modules.sys.utils.UserUtils;
import com.movitech.mbox.modules.wdt.entity.*;
import com.movitech.mbox.modules.wdt.service.WdtTTaskAttemtionService;
import com.movitech.mbox.modules.wdt.service.WdtTTaskItemService;
import com.movitech.mbox.modules.wdt.service.WdtTTaskService;
import com.movitech.mbox.modules.wdt.service.WdtTTaskWarningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping(value = "${adminPath}/rest/wdt/taskWarning")
public class TaskWarningController extends BaseController {
    @Autowired
	WdtTTaskWarningService wdtTTaskWarningService;
    @Autowired
	WdtTTaskService tTaskService;
	/**
	 * 预警查询
	 */
	@RequestMapping(value = {"list", ""})
    public String list() {
		return "modules/wdt/warning/wdtWariningList";
    }
	@RequestMapping(value = "searchWarning")
	@ResponseBody
    public DataTableResult<Object> searchTask(Integer start,Integer length,String name) {
		String userId=UserUtils.getUser().getId();
		DataTableResult result=new DataTableResult();
		Integer pageno=start/length;
		Page page=new  Page(pageno,length);
		WdtWaringParam param=new WdtWaringParam();
		param.setUserId(userId);
		param.setTaskName(name);
		param.setPage(page);
		List<Warning> list=wdtTTaskWarningService.getWarningListByUser(param);
		result.setRecordsTotal(param.getPage().getCount());
		result.setRecordsFiltered(list.size());
		result.setData(list);
		return result;
    }
	@RequestMapping(value ="editform")
	public String edit(Model model,String id) {
		String userId=UserUtils.getUser().getId();
		if(StringUtils.isNotEmpty(id)){
			Warning warning = wdtTTaskWarningService.get(id);
			model.addAttribute("warning",warning);
			List<WdtTTask> taskByUserIdOrOwerIds = tTaskService.getTaskByUserIdOrOwerId(null, warning.getTaskId());
			model.addAttribute("tasks",taskByUserIdOrOwerIds);
		}else {
			model.addAttribute("tasks",tTaskService.getTaskByUserIdOrOwerId(userId,null));
			model.addAttribute("warning",new Warning());
		}
		return "modules/wdt/warning/wdtWariningAdd";
	}
	@RequestMapping(value ="editSave")
	public String saveWaring(String taskId,Integer days,String remark,String status,String warningId ) {
		if(StringUtils.isEmpty(warningId)) {
			List<WdtTTask> task = tTaskService.getTaskByUserIdOrOwerId(null, taskId);
			Warning warning = new Warning();
			String userId = UserUtils.getUser().getId();
			if (task != null && task.size() > 0) {
				WdtTTask tTask = task.get(0);
				Date endDate = tTask.getEndDate();
				Date begin = DateUtils.addDays(endDate, 0 - days);
				warning.setWarningStartDate(begin);
			}
			warning.setId(IdGen.uuid());
			warning.setTaskId(taskId);
			warning.setDays(days);
			warning.setRemark(remark);
			warning.setStatus(status);
			warning.setCreateDate(new Date());
			warning.setCreateBy(userId);
			warning.setUpdateBy(userId);
			warning.setUpdateDate(new Date());
			warning.setDelFlag("0");
			wdtTTaskWarningService.save(warning);
		}else {
			Warning warning=wdtTTaskWarningService.get(warningId);
			List<WdtTTask> task = tTaskService.getTaskByUserIdOrOwerId(null, taskId);
			if (task != null && task.size() > 0) {
				WdtTTask tTask = task.get(0);
				Date endDate = tTask.getEndDate();
				Date begin = DateUtils.addDays(endDate, 0 - days);
				warning.setWarningStartDate(begin);
				warning.setUpdateDate(new Date());
			}
			warning.setDays(days);
			warning.setRemark(remark);
			warning.setStatus(status);
			wdtTTaskWarningService.update(warning);
		}
		return "redirect:" + adminPath + "/rest/wdt/taskWarning/list";
	}
	@RequestMapping(value = "getcounts")
	@ResponseBody
	public HResult<Object> getcounts(String taskId) {
		HResult result=new HResult(true);
		List list=wdtTTaskWarningService.getBytaskId(taskId);
		result.setValue(list.size());
		return result;
	}
}
