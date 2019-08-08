package com.movitech.mbox.modules.wdt.h5Rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.movitech.mbox.common.config.ErrorMessage;
import com.movitech.mbox.common.config.ExecutionStatusEnum;
import com.movitech.mbox.common.config.Global;
import com.movitech.mbox.common.config.MessageTypeEnum;
import com.movitech.mbox.common.config.OperateLogEnum;
import com.movitech.mbox.common.config.TaskCommentTypeEnum;
import com.movitech.mbox.common.persistence.Page;
import com.movitech.mbox.common.utils.CusAccessObjectUtil;
import com.movitech.mbox.common.utils.StringUtils;
import com.movitech.mbox.common.web.BaseController;
import com.movitech.mbox.modules.base.vo.HResult;
import com.movitech.mbox.modules.sys.entity.User;
import com.movitech.mbox.modules.sys.utils.UserUtils;
import com.movitech.mbox.modules.wdt.entity.WdtTTask;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskAttemtion;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskComments;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskItem;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskReponse;
import com.movitech.mbox.modules.wdt.entity.ext.ExtWdtTTaskComments;
import com.movitech.mbox.modules.wdt.entity.ext.TaskH5;
import com.movitech.mbox.modules.wdt.operateLog.service.OperateLogService;
import com.movitech.mbox.modules.wdt.req.TaskCommentReq;
import com.movitech.mbox.modules.wdt.service.WdtTTaskAttemtionService;
import com.movitech.mbox.modules.wdt.service.WdtTTaskCommentsService;
import com.movitech.mbox.modules.wdt.service.WdtTTaskItemService;
import com.movitech.mbox.modules.wdt.service.WdtTTaskReadCountService;
import com.movitech.mbox.modules.wdt.service.WdtTTaskReponseService;
import com.movitech.mbox.modules.wdt.service.WdtTTaskService;

/**
 * 任务 H5
 * @author Aibal.He
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/h5/task")
public class TaskH5Rest extends BaseController {
	@Autowired
	private WdtTTaskReadCountService wdtTTaskReadCountService;
	
	@Autowired
	private WdtTTaskService wdtTTaskService;
	@Autowired
	private WdtTTaskCommentsService wdtTTaskCommentsService;
	
	@Autowired
	private WdtTTaskReponseService wdtTTaskReponseService;
	
	@Autowired
	private OperateLogService operateLogService;
	
	@Autowired
	private WdtTTaskItemService wdtTTaskItemService;
	
	@Autowired
	private WdtTTaskAttemtionService wdtTTaskAttemtionService;
	
	@RequestMapping(value = "searchTask",method = RequestMethod.POST)
	@ResponseBody
    public HResult<Object> searchH5Task( @RequestBody TaskH5 taskH5, HttpServletRequest request, HttpServletResponse response) {
		taskH5.setUserId(UserUtils.getUser().getId());
		Page<TaskH5> page = wdtTTaskService.findH5PageForMyTask(new Page<TaskH5>(taskH5.getStart()/taskH5.getLength()+1, taskH5.getLength()), taskH5); 
		HResult<Object> result = new HResult<>(true);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("currentUser", UserUtils.getUser());
		result.setValue(map);
		return result;
    }
	@RequestMapping(value = "getTask",method = RequestMethod.POST)
	@ResponseBody
	public HResult<Object> getTask(@RequestBody(required=false) WdtTTask wdtTTask) {
		HResult<Object> result = new HResult<>(true);
		Map<String, Object> map = new HashMap<String, Object>();
		User currentUser = UserUtils.getUser();
        if (StringUtils.isNotBlank(wdtTTask.getId())){
        	 wdtTTask =wdtTTaskService.getShowFront(wdtTTask,currentUser.getId());
            
        }else{
        	wdtTTask = new WdtTTask();
        }
        map.put("currentUser", currentUser);
        map.put("wdtTTask", wdtTTask);
      //是否显示 “完成任务” button
  		WdtTTaskItem searchTaskItem = new WdtTTaskItem();
  		searchTaskItem.setTaskId(wdtTTask.getId());
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
  		wdtTTaskAttemtion.setTaskId(wdtTTask.getId());
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
  		wdtTTaskReadCountService.updateReadCountByTaskId(wdtTTask.getId());
		result.setValue(map);
		return result;
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
			operateLogService.save(OperateLogEnum.URLH551, CusAccessObjectUtil.getIpAddress(request), wdtTTaskComments.getTaskId(), null);
		}else{
			result=wdtTTaskCommentsService.updateComments(wdtTTaskComments);
			operateLogService.save(OperateLogEnum.URLH552, CusAccessObjectUtil.getIpAddress(request), wdtTTaskComments.getTaskId(), null);
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
			operateLogService.save(OperateLogEnum.URLH532, StringUtils.getRemoteAddr(request), null, wdtTTaskReponse.getTaskItemId());
		}else{
			operateLogService.save(OperateLogEnum.URLH533, StringUtils.getRemoteAddr(request), wdtTTaskReponse.getTaskId(), null );
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
}
