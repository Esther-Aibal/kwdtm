
package com.movitech.mbox.modules.wdt.rest;

import com.movitech.mbox.common.config.*;
import com.movitech.mbox.common.persistence.Page;
import com.movitech.mbox.common.utils.CusAccessObjectUtil;
import com.movitech.mbox.common.utils.StringUtils;
import com.movitech.mbox.common.web.BaseController;
import com.movitech.mbox.modules.base.vo.HResult;
import com.movitech.mbox.modules.sys.entity.User;
import com.movitech.mbox.modules.sys.utils.UserUtils;
import com.movitech.mbox.modules.wdt.entity.*;
import com.movitech.mbox.modules.wdt.entity.ext.AtMessage;
import com.movitech.mbox.modules.wdt.entity.ext.ExtWdtTTaskComments;
import com.movitech.mbox.modules.wdt.operateLog.service.OperateLogService;
import com.movitech.mbox.modules.wdt.req.TaskCommentReq;
import com.movitech.mbox.modules.wdt.service.*;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 任务页
 * @author Aibal.He
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/rest/wdt/task")
public class TaskController extends BaseController {

	
	@Autowired
	private WdtTTaskService wdtTTaskService;
	
	@Autowired
	private WdtTTaskItemService wdtTTaskItemService;
	
	@Autowired
	private WdtTTaskAttemtionService wdtTTaskAttemtionService;
	
	@Autowired
	private WdtTTaskCommentsService wdtTTaskCommentsService;
	
	@Autowired
	private WdtTTaskReponseService wdtTTaskReponseService;
	
	@Autowired
	private OperateLogService operateLogService;
	
	@Autowired
	private WdtTTaskReadCountService wdtTTaskReadCountService;
    @ModelAttribute
    public WdtTTask get(@RequestParam(required=false) String id) {
        if (StringUtils.isNotBlank(id)){
        	User currentUser = UserUtils.getUser();
        	WdtTTask wdtTTask =wdtTTaskService.getShowFront(new WdtTTask(id),currentUser.getId());
        	//wdtTTask.setTaskCreateUser(UserUtils.getUser());
            return wdtTTask;
        }else{
            return new WdtTTask();
        }
    }
	
	/**
	 * 任务查询
	 */
	@RequestMapping(value = {"list", ""})
    public String list(WdtTTask wdtTTask, HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/wdt/wdtTTaskList";
    }
	
	@RequestMapping(value = "searchTask",method = RequestMethod.POST)
	@ResponseBody
    public HResult<Object> searchTask( @RequestBody WdtTTask wdtTTask, HttpServletRequest request, HttpServletResponse response) {
		wdtTTask.setUserId(UserUtils.getUser().getId());
		Page<WdtTTask> page = wdtTTaskService.findPageForMyTask(new Page<WdtTTask>(wdtTTask.getStart()/wdtTTask.getLength()+1, wdtTTask.getLength()), wdtTTask); 
		HResult<Object> result = new HResult<>(true);
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("page", page);
		result.setValue(map);
		return result;
    }

    /**
   	 * 任务编辑页
   	 */
	@RequestMapping(value = "editTaskPage")
	public String editTaskPage(WdtTTask wdtTTask, Model model) {
		model.addAttribute("wdtTTask", wdtTTask);
		model.addAttribute("importantTypes", TaskImportantTypeEnum.values());//value description
	   model.addAttribute("fqcys", FqcyEnum.values());//value description
	   StringBuffer sb=new StringBuffer();
	   String imptype=wdtTTask.getImportantType();
	   if("0".equals(imptype)) {
		   sb.append("<label><input type=\"radio\" name=\"importantType\" value=\"0\" checked > <span class=\"on\">重要</span></label>")
		   .append("<label><input type=\"radio\" name=\"importantType\" value=\"1\"> <span>普通</span></label> ");
	   }else {
		   sb.append("<label><input type=\"radio\" name=\"importantType\" value=\"0\"  > <span>重要</span></label>")
		   .append("<label><input type=\"radio\" name=\"importantType\" value=\"1\" checked> <span class=\"on\">普通</span></label> ");  
	   }
	
	   model.addAttribute("radiohtml",sb);
	   return "modules/wdt/wdtTTaskForm";
   }
    /**
	 * 任务新增/编辑
	 */
	@RequestMapping(value = "saveTask")
    public String saveTask(WdtTTask wdtTTask, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		setHtmlContent(wdtTTask);
		wdtTTaskService.submit(wdtTTask);
	   operateLogService.save(OperateLogEnum.URL111, StringUtils.getRemoteAddr(request), wdtTTask.getId(), null);
	   return "redirect:" + adminPath + "/rest/wdt/task/?repage";
    }
	/**
	 * 任务新增/编辑
	 */
	@RequestMapping(value = "submit")
	@ResponseBody
    public HResult<Boolean> submit(WdtTTask wdtTTask, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if(wdtTTaskService.submitBtn(wdtTTask)){
			operateLogService.save(OperateLogEnum.URL113, StringUtils.getRemoteAddr(request), wdtTTask.getId(), null);
			return new HResult<>(true, "提交任务成功");
		}else{
			return new HResult<>(false, "提交任务失败");
		}
    }
	/**
	 * 任务进行中 进行修改
	 */
	@RequestMapping(value = "partUpdate")
    public String partUpdate(WdtTTask wdtTTask, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		setHtmlContent(wdtTTask);
		wdtTTaskService.partUpdate(wdtTTask);
	   operateLogService.save(OperateLogEnum.URL115, StringUtils.getRemoteAddr(request), wdtTTask.getId(), null);
	   return "redirect:" + adminPath + "/rest/wdt/task/?repage";
    }
   /**
	 * 保存草稿
	 */
   @RequestMapping(value ="saveDraftTask")
    public String saveDraftTask(WdtTTask wdtTTask, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
	   setHtmlContent(wdtTTask);
	   wdtTTaskService.saveDraftTask(wdtTTask);
	   operateLogService.save(OperateLogEnum.URL11, StringUtils.getRemoteAddr(request), wdtTTask.getId(), null);
       return "redirect:" + adminPath + "/rest/wdt/task/?repage";
    }
    
    
    /**
     * Jack.Gong
     * @param wdtTTask
     * @return
     */
	@RequestMapping(value = "receiveTask",method = RequestMethod.POST)
	@ResponseBody
    public HResult<Object> receiveTask(@RequestBody WdtTTask wdtTTask, HttpServletRequest request){
    	
		wdtTTask = wdtTTaskService.get(wdtTTask);
    	wdtTTask.setExecutionStatus(ExecutionStatusEnum.Status_3.getValue());
    	int i = wdtTTaskService.updateTask(wdtTTask);
    	operateLogService.save(OperateLogEnum.URL18, CusAccessObjectUtil.getIpAddress(request), wdtTTask.getId(), null);
    	HResult<Object> result = null;
    	
    	if(i > 0){
    		result = new HResult<Object>(true, "接收成功");
    	}else{
    		result = new HResult<Object>(true, "接收失败");
    	}
    	
    	return result;
    }
	
	
	/**
	 * 任务详情
	 * @param id
	 * @return
	 */
	@RequestMapping("taskDetail")
	public ModelAndView taskDetail(@RequestParam(value = "id", required = false) String id){
		
		WdtTTask wdtTTask = wdtTTaskService.getTaskDetailByTaskId(id);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("wdtTTask", wdtTTask);
		
		//是否显示 “完成任务” button
		WdtTTaskItem searchTaskItem = new WdtTTaskItem();
		searchTaskItem.setTaskId(id);
		searchTaskItem.setDelFlag("0");
		List<WdtTTaskItem> unCompletedTaskItems = wdtTTaskItemService.findUnCompletedTaskItems(searchTaskItem);
		
		if((unCompletedTaskItems != null && unCompletedTaskItems.size() > 0)
				|| (!ExecutionStatusEnum.Status_3.getValue().equals(wdtTTask.getExecutionStatus())
						&& !ExecutionStatusEnum.Status_4.getValue().equals(wdtTTask.getExecutionStatus())
						&& !ExecutionStatusEnum.Status_6.getValue().equals(wdtTTask.getExecutionStatus()))){
			map.put("canComplete", false);
		}else{
			map.put("canComplete", true);
		}
		
		//是否显示“接收” button
		if(!ExecutionStatusEnum.Status_1.getValue().equals(wdtTTask.getExecutionStatus())){
			map.put("canReceive", false);
		}else{
			map.put("canReceive", true);
		}
		
		//该用户是否已经关注改任务
		User user = UserUtils.getUser();
		WdtTTaskAttemtion wdtTTaskAttemtion = new WdtTTaskAttemtion();
		wdtTTaskAttemtion.setTaskId(id);
		wdtTTaskAttemtion.setUser(user);
		WdtTTaskAttemtion hasAttentionTask = wdtTTaskAttemtionService.getFocusedTaskByTaskIdAndUserId(wdtTTaskAttemtion);
		if(hasAttentionTask != null){
			map.put("canPayAttention", false);
		}else{
			map.put("canPayAttention", true);
		}
		//oa详情url
		map.put("oaInfoUrl", Global.getConfig("oa_info_url"));
		//查看时增加阅读数
		wdtTTaskReadCountService.updateReadCountByTaskId(id);
		map.put("itemCount",wdtTTaskItemService.getTaskItemByTaskId(id));
		map.put("childCount",wdtTTaskService.getTaskChildCountByTaskId(id));
		return new ModelAndView("modules/wdt/wdtTTaskItem/taskDetail", map);
	}

	/**
	 * 获取任务详细数据
	 * @param wdtTTask
	 * @return
     */
	@RequestMapping(value="taskDetailById",method=RequestMethod.POST)
	@ResponseBody
	public com.movitech.mbox.common.rest.HResult<Object> taskDetailById(@RequestBody WdtTTask wdtTTask){

		try{
			wdtTTask = wdtTTaskService.getLite(wdtTTask);
			return new com.movitech.mbox.common.rest.HResult<Object>(true, "获取任务详细成功", wdtTTask);
		}catch(Exception e){
			e.printStackTrace();
			return new com.movitech.mbox.common.rest.HResult<Object>(true, "获取任务详细失败", null);
		}

	}
	
	
	@RequestMapping(value = "completeTask",method = RequestMethod.POST)
	@ResponseBody
	public com.movitech.mbox.common.rest.HResult<Object> completeTask(@RequestBody WdtTTask wdtTTask, HttpServletRequest request){

		if(StringUtils.isEmpty(wdtTTask.getId())){
			return com.movitech.mbox.common.rest.HResult.fail(ErrorMessage.NO_ERROR);
		}
		int result = wdtTTaskService.completeTask(wdtTTask);
		if(result == -1){
    		return com.movitech.mbox.common.rest.HResult.fail(ErrorMessage.NO_TASK_ERROR);
    	}else if(result == -2){
    		return com.movitech.mbox.common.rest.HResult.fail(ErrorMessage.NO_TASK_ITEM_ERROR5);
    	}
		operateLogService.save(OperateLogEnum.URL19, CusAccessObjectUtil.getIpAddress(request), wdtTTask.getId(), null);
        
        return com.movitech.mbox.common.rest.HResult.success(ErrorMessage.COMPLETE_TASK_SUCCESS);
	}
	
    @RequestMapping(value = "delete")
    @ResponseBody
    public HResult<Boolean> delete(WdtTTask wdtTTask, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if (StringUtils.isEmpty(wdtTTask.getId())){
        	return new HResult<>(false, "删除任务失败, 请联系管理员");
        }else{
        	if(wdtTTaskService.deleteTask(wdtTTask)){
        		operateLogService.save(OperateLogEnum.URL0, StringUtils.getRemoteAddr(request), wdtTTask.getId(), null);
        		return new HResult<>(true,  "删除任务成功");
        	}else{
        		return new HResult<>(false,  "删除任务失败,请刷新页面");
        	}
           
        }  
    }
    
	/**
	 * 任务查询
	 */
	@RequestMapping(value = "taskComments")
    public String taskComments(WdtTTask wdtTTask, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		return "modules/wdt/wdtTTaskItem/taskComments";
    }
	
	
	/**
	 * 关注任务
	 * @param wdtTTaskAttemtion
	 * @return
	 */
	@RequestMapping(value = "payAttentionTask",method = RequestMethod.POST)
	@ResponseBody
	public com.movitech.mbox.common.rest.HResult<Object> payAttentionTask(@RequestBody WdtTTaskAttemtion wdtTTaskAttemtion, HttpServletRequest request){
		User user = UserUtils.getUser();
		
		wdtTTaskAttemtion.setUser(user);
		
		try{
			WdtTTaskAttemtion hasAttentionTask = wdtTTaskAttemtionService.get(wdtTTaskAttemtion);
			if(hasAttentionTask == null){
				wdtTTaskAttemtionService.save(wdtTTaskAttemtion);
			}
			
			operateLogService.save(OperateLogEnum.URL16, CusAccessObjectUtil.getIpAddress(request), wdtTTaskAttemtion.getTaskId(), null);
			
			return com.movitech.mbox.common.rest.HResult.success(ErrorMessage.PAY_ATTENTION_TASK_SUCCESS);
		}catch(Exception e){
			e.printStackTrace();
			return com.movitech.mbox.common.rest.HResult.fail(ErrorMessage.SYS_ERROR);
		}
		
	}
	/**
	 * 取消关注任务
	 * @param wdtTTaskAttemtion
	 * @return
	 */
	@RequestMapping(value = "cancelAttentionTask",method = RequestMethod.POST)
	@ResponseBody
	public com.movitech.mbox.common.rest.HResult<Object> cancelAttentionTask(@RequestBody WdtTTaskAttemtion wdtTTaskAttemtion, HttpServletRequest request){
		User user = UserUtils.getUser();
		
		wdtTTaskAttemtion.setUser(user);
		
		try{
			wdtTTaskAttemtionService.cancelAttentionTask(wdtTTaskAttemtion);
			operateLogService.save(OperateLogEnum.URL161, CusAccessObjectUtil.getIpAddress(request), wdtTTaskAttemtion.getTaskId(), null);
			
			return com.movitech.mbox.common.rest.HResult.success(ErrorMessage.CANCEL_ATTENTION_TASK_SUCCESS);
		}catch(Exception e){
			e.printStackTrace();
			return com.movitech.mbox.common.rest.HResult.fail(ErrorMessage.SYS_ERROR);
		}
		
	}
	/**
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "progressFeedbackList", method = RequestMethod.POST)
	@ResponseBody
	public com.movitech.mbox.common.rest.HResult<Object> progressFeedbackList(@RequestBody TaskCommentReq req) {
		WdtTTaskComments wdtTTaskComments=new WdtTTaskComments();
		wdtTTaskComments.setTaskId(req.getTaskId());
		wdtTTaskComments.setType(TaskCommentTypeEnum.Status_0.getValue());
		if(StringUtils.isNotEmpty(req.getPinId())){
			if(req.getMessageType().equals(MessageTypeEnum.Status_7.getValue())||req.getMessageType().equals(MessageTypeEnum.Status_10.getValue())){
				WdtTTaskReponse wdtTTaskReponse = wdtTTaskReponseService.get(req.getPinId());
				wdtTTaskComments.setId(wdtTTaskReponse.getComentsId());
				int no = wdtTTaskCommentsService.findPinNo(wdtTTaskComments);
				req.setStart(no-1);
			}else if (req.getMessageType().equals(MessageTypeEnum.Status_5.getValue())){
				wdtTTaskComments.setId(req.getPinId());
				int no = wdtTTaskCommentsService.findPinNo(wdtTTaskComments);
				req.setStart(no-1);
			}
			wdtTTaskComments.setId(null);
		}
		Page<ExtWdtTTaskComments> page= wdtTTaskCommentsService.findPage(new Page<WdtTTaskComments>(req.getStart()/req.getLength()+1, req.getLength()),wdtTTaskComments);
		return new com.movitech.mbox.common.rest.HResult<Object>(page);
	}
    @RequestMapping(value = "saveProgressFeedback", method = RequestMethod.POST)
    @ResponseBody
    public com.movitech.mbox.common.rest.HResult<Object> saveProgressFeedback(@RequestBody WdtTTaskComments wdtTTaskComments, HttpServletRequest request) {
    	int result=0;
    	if(StringUtils.isEmpty(wdtTTaskComments.getId())){
    		wdtTTaskComments.setType(TaskCommentTypeEnum.Status_0.getValue());
			result=wdtTTaskCommentsService.saveComments(wdtTTaskComments);
			operateLogService.save(OperateLogEnum.URL51, StringUtils.getRemoteAddr(request), wdtTTaskComments.getTaskId(), null);
		}else{
			result=wdtTTaskCommentsService.updateComments(wdtTTaskComments);
			operateLogService.save(OperateLogEnum.URL52,StringUtils.getRemoteAddr(request),wdtTTaskComments.getTaskId(), null);
		}
    	if(-1==result){
    		return com.movitech.mbox.common.rest.HResult.fail(ErrorMessage.NO_TASK_PROCESS_ERROR);
    	}else if(-2==result){
			return com.movitech.mbox.common.rest.HResult.fail(ErrorMessage.NO_TASK_ERROR);
		}else if(-3==result){
			return com.movitech.mbox.common.rest.HResult.fail(ErrorMessage.TASK_USER_ERROR);
		}else if(-4==result){
			return com.movitech.mbox.common.rest.HResult.fail(ErrorMessage.TASK_STATE_ERROR);
		}
    	return com.movitech.mbox.common.rest.HResult.success();
    }
    
    @RequestMapping(value = "deleteProgressFeedback", method = RequestMethod.POST)
    @ResponseBody
    public com.movitech.mbox.common.rest.HResult<Object> deleteProgressFeedback(@RequestBody WdtTTaskComments wdtTTaskComments, HttpServletRequest request) {
    	int result=0;
    	if(StringUtils.isNotEmpty(wdtTTaskComments.getId())){
			result=wdtTTaskCommentsService.deleteComments(wdtTTaskComments);
		}
    	if(-1==result){
    		return com.movitech.mbox.common.rest.HResult.fail(ErrorMessage.DELETE_ERROR);
    	}else if(-2==result){
			return com.movitech.mbox.common.rest.HResult.fail(ErrorMessage.DELETE_CURRENTDAT_ERROR);
		}else if(-3==result){
			return com.movitech.mbox.common.rest.HResult.fail(ErrorMessage.TASK_USER_ERROR);
		}else if(-4==result){
			return com.movitech.mbox.common.rest.HResult.fail(ErrorMessage.TASK_STATE_ERROR);
		}else if(0==result){
			return com.movitech.mbox.common.rest.HResult.fail(ErrorMessage.NO_ERROR);
			
		}
    	return com.movitech.mbox.common.rest.HResult.success();
    }
    
    @RequestMapping(value = "saveProgressReponse", method = RequestMethod.POST)
    @ResponseBody
    public com.movitech.mbox.common.rest.HResult<Object> saveProgressReponse(@RequestBody WdtTTaskReponse wdtTTaskReponse, HttpServletRequest request) {
    	if(StringUtils.isEmpty(wdtTTaskReponse.getComentsId())){
    		return com.movitech.mbox.common.rest.HResult.fail(ErrorMessage.NO_ERROR);
		}
    	wdtTTaskReponse.setUser(UserUtils.getUser());
		int result = wdtTTaskReponseService.saveReponse(wdtTTaskReponse);
		
		if(-1==result){
			return com.movitech.mbox.common.rest.HResult.fail(ErrorMessage.NO_TASK_ERROR);
		}else if(-2==result){
			return com.movitech.mbox.common.rest.HResult.fail(ErrorMessage.NO_TASK_ITEM_ERROR);
		}else if(-3==result){
			return com.movitech.mbox.common.rest.HResult.fail(ErrorMessage.NO_TASK_ITEM_ERROR4);
		}
		if(StringUtils.isEmpty(wdtTTaskReponse.getTaskId())){
			operateLogService.save(OperateLogEnum.URL32, StringUtils.getRemoteAddr(request), null, wdtTTaskReponse.getTaskItemId());
		}else{
			operateLogService.save(OperateLogEnum.URL33, StringUtils.getRemoteAddr(request), wdtTTaskReponse.getTaskId(), null );
		}
    	return com.movitech.mbox.common.rest.HResult.success();
    }
    
    @RequestMapping(value = "deleteProgressReponse", method = RequestMethod.POST)
    @ResponseBody
    public com.movitech.mbox.common.rest.HResult<Object> deleteProgressReponse(@RequestBody WdtTTaskReponse wdtTTaskReponse, HttpServletRequest request) {
    	int result=0;
    	if(StringUtils.isNotEmpty(wdtTTaskReponse.getId())){
			result=wdtTTaskReponseService.deleteReponse(wdtTTaskReponse);
		}
    	if(-1==result){
    		return com.movitech.mbox.common.rest.HResult.fail(ErrorMessage.DELETE_ERROR);
    	}else if(-2==result){
			return com.movitech.mbox.common.rest.HResult.fail(ErrorMessage.DELETE_CURRENTDAT_ERROR);
		}else if(-3==result){
			return com.movitech.mbox.common.rest.HResult.fail(ErrorMessage.TASK_USER_ERROR);
		}else if(-4==result){
			return com.movitech.mbox.common.rest.HResult.fail(ErrorMessage.TASK_STATE_ERROR);
		}else if(0==result){
			return com.movitech.mbox.common.rest.HResult.fail(ErrorMessage.NO_ERROR);
			
		}
    	return com.movitech.mbox.common.rest.HResult.success();
    }    
	public void setHtmlContent(WdtTTask wdtTTask) {
		String target = StringEscapeUtils.unescapeHtml4(wdtTTask.getTarget());
		String content = StringEscapeUtils.unescapeHtml4(wdtTTask.getContent());
		wdtTTask.setTarget(target);
		wdtTTask.setContent(content);
		List<WdtTTaskTemplete> list = wdtTTask.getWdtTTaskTempleteList();
		for (int i = 0; i < list.size(); i++) {
			WdtTTaskTemplete tmp = list.get(i);
			tmp.setContent(StringEscapeUtils.unescapeHtml4(tmp.getContent()));
		}
	}
	
	
	@RequestMapping(value = "saveAtMessage", method = RequestMethod.POST)
    @ResponseBody
    public com.movitech.mbox.common.rest.HResult<Object> saveMessage(@RequestBody AtMessage atMessage, HttpServletRequest request) {
    	if(atMessage.getAtPerson() == null || atMessage.getAtPerson().size()==0 
    			|| (StringUtils.isEmpty(atMessage.getTaskId()) && StringUtils.isEmpty(atMessage.getTaskItemId())  )){
    		return com.movitech.mbox.common.rest.HResult.fail(ErrorMessage.NO_ERROR);
		}
		int result = wdtTTaskReponseService.saveAtMessage(atMessage);
		
		if(-1==result){
			return com.movitech.mbox.common.rest.HResult.fail(ErrorMessage.NO_TASK_ERROR);
		}else if(-2==result){
			return com.movitech.mbox.common.rest.HResult.fail(ErrorMessage.NO_TASK_ITEM_ERROR);
		}
		if(StringUtils.isNotEmpty(atMessage.getTaskItemId())){
			operateLogService.save(OperateLogEnum.URL6, StringUtils.getRemoteAddr(request), null, atMessage.getTaskItemId());
		}else{
			operateLogService.save(OperateLogEnum.URL6, StringUtils.getRemoteAddr(request), atMessage.getTaskId(), null );
		}
    	return com.movitech.mbox.common.rest.HResult.success();
    }
}
