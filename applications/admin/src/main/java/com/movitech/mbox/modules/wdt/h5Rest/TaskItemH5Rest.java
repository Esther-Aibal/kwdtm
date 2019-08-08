package com.movitech.mbox.modules.wdt.h5Rest;



import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.movitech.mbox.common.config.ErrorMessage;
import com.movitech.mbox.common.config.MessageTypeEnum;
import com.movitech.mbox.common.config.OperateLogEnum;
import com.movitech.mbox.common.config.TaskCommentTypeEnum;
import com.movitech.mbox.common.persistence.Page;
import com.movitech.mbox.common.rest.HResult;
import com.movitech.mbox.common.utils.CusAccessObjectUtil;
import com.movitech.mbox.common.utils.DateUtils;
import com.movitech.mbox.common.utils.StringUtils;
import com.movitech.mbox.common.web.BaseController;
import com.movitech.mbox.modules.sys.entity.User;
import com.movitech.mbox.modules.sys.utils.UserUtils;
import com.movitech.mbox.modules.wdt.entity.WdtTTask;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskComments;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskReponse;
import com.movitech.mbox.modules.wdt.entity.ext.ExtWdtTTaskComments;
import com.movitech.mbox.modules.wdt.entity.ext.ExtWdtTTaskItem;
import com.movitech.mbox.modules.wdt.exception.RollBackException;
import com.movitech.mbox.modules.wdt.operateLog.service.OperateLogService;
import com.movitech.mbox.modules.wdt.req.TaskCommentReq;
import com.movitech.mbox.modules.wdt.req.TaskItemReq;
import com.movitech.mbox.modules.wdt.service.WdtTTaskCommentsService;
import com.movitech.mbox.modules.wdt.service.WdtTTaskItemService;
import com.movitech.mbox.modules.wdt.service.WdtTTaskReponseService;
import com.movitech.mbox.modules.wdt.service.WdtTTaskService;
import com.movitech.mbox.modules.wdt.taskStatus.service.TaskStatusService;

/**
 * 任务状态 H5
 * @author Aibal.He
 *
 */
@RestController
@RequestMapping(value = "${adminPath}/h5/taskItem")
public class TaskItemH5Rest extends BaseController {

	@Autowired
	private OperateLogService operateLogService;
	
	@Autowired
	private WdtTTaskItemService wdtTTaskItemService;
	
	@Autowired
	private WdtTTaskCommentsService wdtTTaskCommentsService;
	
	@Autowired
	private WdtTTaskReponseService wdtTTaskReponseService;
	
	@Autowired
	private WdtTTaskService wdtTTaskService;
	
	/*@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
    public HResult<Object> add(HttpServletRequest request,@RequestBody ExtWdtTTaskItem wdtTTaskItem) {
    	int result=0;
        try {
			if(StringUtils.isEmpty(wdtTTaskItem.getId())){
				 result=wdtTTaskItemService.saveExt(wdtTTaskItem);
				 operateLogService.save(OperateLogEnum.URLH5AI.getDescription(), CusAccessObjectUtil.getIpAddress(request), wdtTTaskItem.getTaskId(), wdtTTaskItem.getId());
			}else{
			     result=wdtTTaskItemService.update(wdtTTaskItem);
			     operateLogService.save(OperateLogEnum.URLH5UI.getDescription(), CusAccessObjectUtil.getIpAddress(request), wdtTTaskItem.getTaskId(), wdtTTaskItem.getId());
			}
		} catch (RollBackException e) {
			 return HResult.fail(ErrorMessage.SYS_ERROR);
		}
    	if(-1==result){
            return HResult.fail(ErrorMessage.NO_TASK_ERROR);
        }else if(-2==result){
            return HResult.fail(ErrorMessage.NO_TASK_ITEM_ERROR);
        }
    	return HResult.success();
    }*/
    
    /**
     * 节点移交
     * @param req
     * @return
     */
    @RequestMapping(value = "handover", method = RequestMethod.POST)
    @ResponseBody
    public HResult<Object> handover(HttpServletRequest request,@RequestBody TaskItemReq req) {
    	int result;
		try {
			result = wdtTTaskItemService.handover(req);
		} catch (RollBackException e) {
			 return HResult.fail(ErrorMessage.SYS_ERROR);
		}
    	operateLogService.save(OperateLogEnum.URLH5MI, CusAccessObjectUtil.getIpAddress(request), req.getTaskId(), req.getTaskItemId());
		if(-1==result){
            return HResult.fail(ErrorMessage.NO_TASK_ITEM_ERROR);
        }
    	return HResult.success();
    }
    
    /**
     * 节点完成
     * @param req
     * @return
     */
    @RequestMapping(value = "finish", method = RequestMethod.POST)
    @ResponseBody
    public HResult<Object> finish(HttpServletRequest request,@RequestBody TaskItemReq req) {
    	int result;
		try {
			result = wdtTTaskItemService.finish(req);
		} catch (RollBackException e) {
			 return HResult.fail(ErrorMessage.SYS_ERROR);
		}
    	operateLogService.save(OperateLogEnum.URLH5CI, CusAccessObjectUtil.getIpAddress(request), req.getTaskId(), req.getTaskItemId());
        if(-1==result){
            return HResult.fail(ErrorMessage.NO_TASK_ITEM_ERROR);
        }else if(-2==result){
            return HResult.fail(ErrorMessage.NO_TASK_ITEM_ERROR2);
        }else if(-3==result){
            return HResult.fail(ErrorMessage.NO_TASK_ITEM_ERROR3);
        }
    	return HResult.success();
    }
    
    /**
     * 节点详情
     */
    @RequestMapping(value = "info", method = RequestMethod.POST)
    @ResponseBody
	public HResult<Object> info(@RequestBody ExtWdtTTaskItem entity) {
    	HResult<Object> result = new HResult<Object>(true);
		User currentUser = UserUtils.getUser();
		ExtWdtTTaskItem wdtTTaskItem = wdtTTaskItemService.getInfo(entity.getId());
		int planDay = DateUtils.subDate(wdtTTaskItem.getStartDate(),wdtTTaskItem.getRequiredCompletionTime()) + 1;
		int surplusDay = DateUtils.subDate(new Date(),wdtTTaskItem.getRequiredCompletionTime()) + 1;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("wdtTTaskItem", wdtTTaskItem);
		map.put("planDay", planDay);
		map.put("surplusDay", surplusDay);
		map.put("isTaskOwner", wdtTTaskItemService.isOwner(wdtTTaskItem.getTaskId(),currentUser.getId()));
		map.put("isTaskItemOwner", currentUser.getId().equals(wdtTTaskItem.getOwnerId()));
		WdtTTask task=wdtTTaskItemService.getTask(wdtTTaskItem.getTaskId());
		map.put("taskStatus", task.getExecutionStatus());
		map.put("currentUser", currentUser);
		WdtTTask wdtTTask =wdtTTaskService.getTaskDetailByTaskId(wdtTTaskItem.getTaskId());
		map.put("wdtTTask",wdtTTask);
		result.setData(map);
		return result;
	}
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public HResult<Object> list(@RequestBody TaskItemReq req) {
        ExtWdtTTaskItem extWdtTTaskItem=new ExtWdtTTaskItem();
        User user = UserUtils.getUser();
        extWdtTTaskItem.setTaskId(req.getTaskId());
        extWdtTTaskItem.setCurrentUser(user);
    	Page<ExtWdtTTaskItem> page= wdtTTaskItemService.findPage(new Page<ExtWdtTTaskItem>(req.getStart()/req.getLength()+1, req.getLength()),extWdtTTaskItem);
        return new HResult<Object>(page);
    }
	@RequestMapping(value = "progressCommentlist", method = RequestMethod.POST)
	public HResult<Object> list(@RequestBody TaskCommentReq req) {
		WdtTTaskComments wdtTTaskComments=new WdtTTaskComments();
		wdtTTaskComments.setTaskItemId(req.getTaskItemId());
		wdtTTaskComments.setType(TaskCommentTypeEnum.Status_2.getValue());
		if(StringUtils.isNotEmpty(req.getPinId())){
			if(req.getMessageType().equals(MessageTypeEnum.Status_8.getValue())){
				WdtTTaskReponse wdtTTaskReponse = wdtTTaskReponseService.get(req.getPinId());
				wdtTTaskComments.setId(wdtTTaskReponse.getComentsId());
				int no = wdtTTaskCommentsService.findPinNo(wdtTTaskComments);
				req.setStart(no-1);
			}else if (req.getMessageType().equals(MessageTypeEnum.Status_6.getValue())){
				wdtTTaskComments.setId(req.getPinId());
				int no = wdtTTaskCommentsService.findPinNo(wdtTTaskComments);
				req.setStart(no-1);
			}
			wdtTTaskComments.setId(null);
		}
		Page<ExtWdtTTaskComments> page= wdtTTaskCommentsService.findPage(new Page<WdtTTaskComments>(req.getStart()/req.getLength()+1, req.getLength()),wdtTTaskComments);
		return new HResult<Object>(page);
	}
    
    @RequestMapping(value = "saveProgressComment", method = RequestMethod.POST)
    public HResult<Object> saveProgressComment(HttpServletRequest request,@RequestBody WdtTTaskComments wdtTTaskComments) {
    	int result=0;
    	if(StringUtils.isEmpty(wdtTTaskComments.getId())){
        	wdtTTaskComments.setType(TaskCommentTypeEnum.Status_2.getValue());
			result=wdtTTaskCommentsService.saveComments(wdtTTaskComments);
			operateLogService.save(OperateLogEnum.URLH524, CusAccessObjectUtil.getIpAddress(request), wdtTTaskComments.getTaskId(), wdtTTaskComments.getTaskItemId());
		}else{
			result=wdtTTaskCommentsService.updateComments(wdtTTaskComments);
			operateLogService.save(OperateLogEnum.URLH525,CusAccessObjectUtil.getIpAddress(request),wdtTTaskComments.getTaskId(), wdtTTaskComments.getTaskItemId());
		}
		if(-1==result){
			return HResult.fail(ErrorMessage.NO_TASK_ERROR);
		}else if(-2==result){
			return HResult.fail(ErrorMessage.NO_TASK_ITEM_ERROR);
		}else if(-3==result){
			return HResult.fail(ErrorMessage.NO_TASK_ITEM_ERROR4);
		}
    	return HResult.success();
    }
}