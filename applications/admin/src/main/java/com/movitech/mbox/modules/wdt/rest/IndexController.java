package com.movitech.mbox.modules.wdt.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.movitech.mbox.common.config.*;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskComments;
import com.movitech.mbox.modules.wdt.entity.WdtTaskVo;
import com.movitech.mbox.modules.wdt.entity.ext.ExtWdtTTaskComments;
import com.movitech.mbox.modules.wdt.entity.ext.ExtWdtTTaskReponse;
import com.movitech.mbox.modules.wdt.service.WdtTTaskCommentsService;
import com.movitech.mbox.modules.wdt.service.WdtTTaskReponseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.movitech.mbox.common.persistence.Page;
import com.movitech.mbox.common.utils.StringUtils;
import com.movitech.mbox.modules.base.vo.HResult;
import com.movitech.mbox.modules.sys.entity.Dict;
import com.movitech.mbox.modules.sys.entity.User;
import com.movitech.mbox.modules.sys.utils.UserUtils;
import com.movitech.mbox.modules.wdt.entity.WdtTTask;
import com.movitech.mbox.modules.wdt.service.WdtTTaskService;

/**
 * 首页
 * @author Aibal.He
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/rest/wdt")
public class IndexController {

	
	@Autowired
	private WdtTTaskService wdtTTaskService;
	@Autowired
	private WdtTTaskCommentsService wdtTTaskCommentsService;
	
	@Autowired
	private WdtTTaskReponseService wdtTTaskReponseService;
	
	/**
	 * 任务查询
	 */
	@RequestMapping(value = {"index", ""}, method = RequestMethod.GET)
    public String index() {
         return "modules/wdt/index";
    } 
	
	
	/**
	 * 首页
	 */
	@RequestMapping(value = {"homePage", ""}, method = RequestMethod.GET)
    public String homePage(String executionStatus,Model model) {
		try{
			User user = UserUtils.getUser();

			//section 1: 我的待办事项
			//messageType: (0-即将到期， 1-待办催办， 2-待办汇报，4-等待接收)
			List<WdtTaskVo> myWaitingTaskList = wdtTTaskService.getWaitingTaskForOAHome(user.getCurrentUser().getId(), new Page(0, Global.HOME_PAGE_SHOW_ITEM_COUNT, -1));

			//section 2: including 我 - 关注的任务(followtask)、发起的任务(createtask)、主责的任务(ownertask)、延期的任务(dueTask)、即将到期的任务(duesoonTask)、参与的任务(jointask)
			Map<String, Object> myTaskDetails = wdtTTaskService.getTaskForOAHome(user.getCurrentUser().getId(),executionStatus, new Page(0, Global.HOME_PAGE_SHOW_ITEM_COUNT, -1));

			//section 3: top 10 回复
			//取主任务进度反馈的评论和主任务评论的前10条评论
			List<ExtWdtTTaskReponse> commentsTop10 = wdtTTaskReponseService.getResponseTOP10(user.getCurrentUser().getId());

			//将 待办事项、任务详细分类、top10 整合为一个Map返回
			model.addAttribute("todoList",myWaitingTaskList);
			model.addAttribute("taskList",myTaskDetails);
			model.addAttribute("replyList",commentsTop10);
		}catch(Exception e){
			e.printStackTrace();
		}
         return "modules/wdt/task/homePage";
    } 
	

	/**
	 * 任务录入
	 */
	@RequestMapping(value = "taskInput")
    public String taskInput(WdtTTask wdtTTask, Model model) {
		wdtTTask =wdtTTaskService.getShowFront(wdtTTask,UserUtils.getUser().getId());
	   model.addAttribute("wdtTTask", wdtTTask);
	   model.addAttribute("importantTypes", TaskImportantTypeEnum.values());//value description
	   model.addAttribute("fqcys", FqcyEnum.values());//value description
	   StringBuffer sb=new StringBuffer();
	   sb.append("<label><input type=\"radio\" name=\"importantType\" value=\"0\" checked > <span class=\"on\">重要</span></label>")
	   .append("<label><input type=\"radio\" name=\"importantType\" value=\"1\"> <span>普通</span></label> ");
	   model.addAttribute("radiohtml",sb);
	   return "modules/wdt/wdtTTaskForm";
    }
	
}
