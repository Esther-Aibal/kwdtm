package com.movitech.mbox.modules.wdt.rest;

import java.util.List;
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

import com.movitech.mbox.common.config.ErrorMessage;
import com.movitech.mbox.common.config.Global;
import com.movitech.mbox.common.persistence.Page;
import com.movitech.mbox.common.rest.HResult;
import com.movitech.mbox.common.web.BaseController;
import com.movitech.mbox.modules.sys.entity.User;
import com.movitech.mbox.modules.sys.utils.UserUtils;
import com.movitech.mbox.modules.wdt.entity.WdtTaskVo;
import com.movitech.mbox.modules.wdt.entity.ext.ExtWdtTTaskReponse;
import com.movitech.mbox.modules.wdt.req.BaseReq;
import com.movitech.mbox.modules.wdt.service.MessageService;
import com.movitech.mbox.modules.wdt.service.WdtTTaskCommentsService;
import com.movitech.mbox.modules.wdt.service.WdtTTaskReponseService;
import com.movitech.mbox.modules.wdt.service.WdtTTaskService;

/**
 * 
 * @author Jack.Gong
 * @version 2017.08.14
 *
 */

@Controller
@RequestMapping(value = "${adminPath}/home")
public class HomeController extends BaseController{
	
	@Autowired
	private WdtTTaskService wdtTTaskService;
	
	@Autowired
	private WdtTTaskCommentsService wdtTTaskCommentsService;
	
	@Autowired
	private WdtTTaskReponseService wdtTTaskReponseService;
	
	@Autowired
	private MessageService messageService;
	
	/**
	 * 初始化首页
	 * @param wdtTTask
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "homeDetails")
	@ResponseBody
    public HResult<Object> homeDetails(String executionStatus,HttpServletRequest request, HttpServletResponse response) {
		
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
			myTaskDetails.put("myWaitingTaskList", myWaitingTaskList);
			myTaskDetails.put("commentsTop10", commentsTop10);
			
			return new HResult(true, "首页列表查询成功", myTaskDetails);
		}catch(Exception e){
			e.printStackTrace();
			return HResult.fail(ErrorMessage.SYS_ERROR);
		}
		
    }
	
	
	/**
	 * 我的待办事项 - more
	 * @param baseReq
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "moreWaitingTask", method = RequestMethod.POST)
	@ResponseBody
	public HResult<Object> moreWaitingTask(@RequestBody BaseReq baseReq, HttpServletRequest request, HttpServletResponse response) {
		
		try{
			
			User user = UserUtils.getUser();
			
			Page<WdtTaskVo> page = wdtTTaskService.findPageForWatingTask(user.getCurrentUser().getId(), new Page(baseReq.getStart()/baseReq.getLength()+1, baseReq.getLength())); 
			
			return new HResult<Object>(page);
			
		}catch(Exception e){
			e.printStackTrace();
			return HResult.fail(ErrorMessage.SYS_ERROR);
		}
		
	}
	
	/**
	 * 我关注的任务 - more
	 * @param baseReq
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "moreMyFollowedTask", method = RequestMethod.POST)
	@ResponseBody
	public HResult<Object> moreMyFollowedTask(@RequestBody BaseReq baseReq, HttpServletRequest request, HttpServletResponse response){
		
		try{
			
			User user = UserUtils.getUser();
			
			Page<WdtTaskVo> page = wdtTTaskService.findPageForFollowedTask(user.getCurrentUser().getId(),baseReq.getExecutionStatus(), new Page(baseReq.getStart()/baseReq.getLength()+1, baseReq.getLength()));
			
			return new HResult<Object>(page);
			
		}catch(Exception e){
			e.printStackTrace();
			return HResult.fail(ErrorMessage.SYS_ERROR);
		}
		
	}
	
	/**
	 * 我发起的任务 - more
	 * @param baseReq
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "moreMyCreatedTask", method = RequestMethod.POST)
	@ResponseBody
	public HResult<Object> moreMyCreatedTask(@RequestBody BaseReq baseReq, HttpServletRequest request, HttpServletResponse response){
		
		try{
			
			User user = UserUtils.getUser();
			
			Page<WdtTaskVo> page = wdtTTaskService.findPageForCreatedTask(user.getCurrentUser().getId(),baseReq.getExecutionStatus(), new Page(baseReq.getStart()/baseReq.getLength()+1, baseReq.getLength()));
			
			return new HResult<Object>(page);
			
		}catch(Exception e){
			e.printStackTrace();
			return HResult.fail(ErrorMessage.SYS_ERROR);
		}
		
	}
	
	/**
	 * 我主责的任务 - more
	 * @param baseReq
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "moreMyOwnTask", method = RequestMethod.POST)
	@ResponseBody
	public HResult<Object> moreMyOwnTask(@RequestBody BaseReq baseReq, HttpServletRequest request, HttpServletResponse response){
		
		try{
			
			User user = UserUtils.getUser();
			
			Page<WdtTaskVo> page = wdtTTaskService.findPageForOwnTask(user.getCurrentUser().getId(),baseReq.getExecutionStatus(), new Page(baseReq.getStart()/baseReq.getLength()+1, baseReq.getLength()));
			
			return new HResult<Object>(page);
			
		}catch(Exception e){
			e.printStackTrace();
			return HResult.fail(ErrorMessage.SYS_ERROR);
		}
		
	}
	
	/**
	 * 延期任务 - more
	 * @param baseReq
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "moreMyDeferredTask", method = RequestMethod.POST)
	@ResponseBody
	public HResult<Object> moreMyDeferredTask(@RequestBody BaseReq baseReq, HttpServletRequest request, HttpServletResponse response){
		
		try{
			
			User user = UserUtils.getUser();
			
			Page<WdtTaskVo> page = wdtTTaskService.findPageForDeferredTask(user.getCurrentUser().getId(),baseReq.getExecutionStatus(), new Page(baseReq.getStart()/baseReq.getLength()+1, baseReq.getLength()));
			
			return new HResult<Object>(page);
			
		}catch(Exception e){
			e.printStackTrace();
			return HResult.fail(ErrorMessage.SYS_ERROR);
		}
		
	}
	
	/**
	 * 即将到期任务 - more
	 * @param baseReq
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "moreMyNearingDueTask", method = RequestMethod.POST)
	@ResponseBody
	public HResult<Object> moreMyNearingDueTask(@RequestBody BaseReq baseReq, HttpServletRequest request, HttpServletResponse response){
		
		try{
			
			User user = UserUtils.getUser();
			
			Page<WdtTaskVo> page = wdtTTaskService.findPageForNearingDueTask(user.getCurrentUser().getId(),baseReq.getExecutionStatus(), new Page(baseReq.getStart()/baseReq.getLength()+1, baseReq.getLength()));
			
			return new HResult<Object>(page);
			
		}catch(Exception e){
			e.printStackTrace();
			return HResult.fail(ErrorMessage.SYS_ERROR);
		}
		
	}
	
	
	/**
	 * 我参与的任务 - more
	 * @param baseReq
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "moreMyParticipantTask", method = RequestMethod.POST)
	@ResponseBody
	public HResult<Object> moreMyParticipantTask(@RequestBody BaseReq baseReq, HttpServletRequest request, HttpServletResponse response){
		
		try{
			
			User user = UserUtils.getUser();
			
			Page<WdtTaskVo> page = wdtTTaskService.findPageForParticipantTask(user.getCurrentUser().getId(),baseReq.getExecutionStatus(), new Page(baseReq.getStart()/baseReq.getLength()+1, baseReq.getLength()));
			
			return new HResult<Object>(page);
			
		}catch(Exception e){
			e.printStackTrace();
			return HResult.fail(ErrorMessage.SYS_ERROR);
		}
		
	}

	/**
	 * 首页之待办列表
	 */
	@RequestMapping(value = {"todoList", ""}, method = RequestMethod.GET)
	public String todoList(Model model) {
		try{
			User user = UserUtils.getUser();

			//section 1: 我的待办事项
			//messageType: (0-即将到期， 1-待办催办， 2-待办汇报，4-等待接收)
			List<WdtTaskVo> myWaitingTaskList = wdtTTaskService.getWaitingTaskForOAHome(user.getCurrentUser().getId(), new Page(0, Global.HOME_PAGE_SHOW_ITEM_COUNT, -1));

			model.addAttribute("todoList",myWaitingTaskList);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "modules/wdt/home/todoList";
	}
	/**
	 * 首页之任务列表
	 */
	@RequestMapping(value = {"taskList", ""}, method = RequestMethod.GET)
	public String taskList(String executionStatus,Model model) {
		try{
			User user = UserUtils.getUser();

			//我 - 关注的任务(followtask)、发起的任务(createtask)、主责的任务(ownertask)、延期的任务(dueTask)、即将到期的任务(duesoonTask)、参与的任务(jointask)
			Map<String, Object> myTaskDetails = wdtTTaskService.getTaskForOAHome(user.getCurrentUser().getId(),executionStatus, new Page(0, Global.HOME_PAGE_SHOW_ITEM_COUNT, -1));

			model.addAttribute("taskList",myTaskDetails);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "modules/wdt/home/taskList";
	}
	/**
	 * 首页之回复列表
	 */
	@RequestMapping(value = {"replyList", ""}, method = RequestMethod.GET)
	public String replayList(Model model) {
		try{
			User user = UserUtils.getUser();

			//section 3: top 10 回复
			//取主任务进度反馈的评论和主任务评论的前10条评论
			List<ExtWdtTTaskReponse> commentsTop10 = wdtTTaskReponseService.getResponseTOP10(user.getCurrentUser().getId());

			model.addAttribute("replyList",commentsTop10);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "modules/wdt/home/replyList";
	}
	/**
	 * 首页之待办列表 - more
	 */
	@RequestMapping(value = {"todoListAll", ""}, method = RequestMethod.GET)
	public String todoListAll() {
		return "modules/wdt/home/todoListAll";
	}
	/**
	 * 首页之任务列表 - more
	 */
	@RequestMapping(value = {"taskListAll", ""}, method = RequestMethod.GET)
	public String taskListAll() {
		return "modules/wdt/home/taskListAll";
	}
	/**
	 * 首页之消息列表
	 */
	@RequestMapping(value = {"msgListAll", ""}, method = RequestMethod.GET)
	public String msgListAll( Model model) {
		User user = UserUtils.getUser();
		int unReadCountDefferedWarning = wdtTTaskService.findUnReadCountForDefferedWarning(user.getId());
		int unReadCountScheduleWarning = wdtTTaskService.findUnReadCountForScheduleWarning(user.getId());
		int unReadCountTaskWarning = wdtTTaskService.findUnReadCountForTaskWarning(user.getId());
		int unReadCountReplyWarning = wdtTTaskService.findUnReadCountForReplyWarning(user.getId());
		int unReadCountAtWarning = wdtTTaskService.findUnReadCountForAtWarning(user.getId());
		model.addAttribute("unReadCountDefferedWarning", unReadCountDefferedWarning);
		model.addAttribute("unReadCountScheduleWarning", unReadCountScheduleWarning);
		model.addAttribute("unReadCountTaskWarning", unReadCountTaskWarning);
		model.addAttribute("unReadCountReplyWarning", unReadCountReplyWarning);
		model.addAttribute("unReadCountAtWarning", unReadCountAtWarning);
		return "modules/wdt/home/msgListAll";
	}
	
	/**
	 * 首页之消息列表
	 */
	@RequestMapping(value = "readMsg")
	@ResponseBody
	public HResult<Object> readMsg(String id) {
		messageService.updateRead(id);
		return new HResult<Object>(true);
	}
}
