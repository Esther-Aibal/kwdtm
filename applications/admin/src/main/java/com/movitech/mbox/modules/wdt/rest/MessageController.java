package com.movitech.mbox.modules.wdt.rest;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.movitech.mbox.common.config.ErrorMessage;
import com.movitech.mbox.common.config.TaskCommentTypeEnum;
import com.movitech.mbox.common.persistence.Page;
import com.movitech.mbox.common.rest.HResult;
import com.movitech.mbox.common.utils.StringUtils;
import com.movitech.mbox.common.web.BaseController;
import com.movitech.mbox.modules.sys.entity.User;
import com.movitech.mbox.modules.sys.utils.UserUtils;
import com.movitech.mbox.modules.wdt.entity.Message;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskComments;
import com.movitech.mbox.modules.wdt.entity.WdtTaskVo;
import com.movitech.mbox.modules.wdt.entity.ext.ExtWdtTTaskComments;
import com.movitech.mbox.modules.wdt.req.BaseReq;
import com.movitech.mbox.modules.wdt.req.MessageReq;
import com.movitech.mbox.modules.wdt.req.TaskCommentReq;
import com.movitech.mbox.modules.wdt.service.MessageService;
import com.movitech.mbox.modules.wdt.service.WdtTTaskCommentsService;
import com.movitech.mbox.modules.wdt.service.WdtTTaskService;

/**
 * 
 * @author Jack.Gong
 * @version 2017.08.22
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/message")
public class MessageController extends BaseController{
	
	@Autowired
	private WdtTTaskService  wdtTTaskService;
	
	@Autowired
	private WdtTTaskCommentsService wdtTTaskCommentsService;
	
	@Autowired
	private MessageService messageService;
	
	@RequestMapping(value = "defferedWarning")
	@ResponseBody
	public HResult<Object> defferedWarning(@RequestBody BaseReq baseReq, HttpServletRequest request, HttpServletResponse response){
		
		try{
			
			User user = UserUtils.getUser();
			
			Page<WdtTaskVo> page = null;
			
			if(baseReq.getLength() == 0){//第一次进入消息界面，默认进入延时提醒页面
				page = wdtTTaskService.findPageForDefferedWarning(user.getCurrentUser().getId(), new Page(1, 10));
			}else{
				page = wdtTTaskService.findPageForDefferedWarning(user.getCurrentUser().getId(), new Page(baseReq.getStart()/baseReq.getLength()+1, baseReq.getLength()));
			}
			return new HResult<Object>(page);
		}catch(Exception e){
			e.printStackTrace();
			return HResult.fail(ErrorMessage.SYS_ERROR);
		}
		
	}
	@RequestMapping(value = "scheduleWarning")
	@ResponseBody
	public HResult<Object> scheduleWarning(@RequestBody BaseReq baseReq, HttpServletRequest request, HttpServletResponse response){
		
		try{
			
			User user = UserUtils.getUser();
			
			Page<WdtTaskVo> page = wdtTTaskService.findPageForScheduleWarning(user.getCurrentUser().getId(), new Page(baseReq.getStart()/baseReq.getLength()+1, baseReq.getLength()));
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("page", page);
			map.put("unReadCount", wdtTTaskService.findUnReadCountForScheduleWarning(user.getId()));
			return new HResult<Object>(page);
		}catch(Exception e){
			e.printStackTrace();
			return HResult.fail(ErrorMessage.SYS_ERROR);
		}
		
	}

	@RequestMapping(value = "taskWarning")
	@ResponseBody
	public HResult<Object> taskWarning(@RequestBody BaseReq baseReq, HttpServletRequest request, HttpServletResponse response){
		
		try{
			
			User user = UserUtils.getUser();	
			Page<WdtTaskVo> page = wdtTTaskService.findPageForTaskWarning(user.getCurrentUser().getId(), new Page(baseReq.getStart()/baseReq.getLength()+1, baseReq.getLength()));
			return new HResult<Object>(page);
		}catch(Exception e){
			e.printStackTrace();
			return HResult.fail(ErrorMessage.SYS_ERROR);
		}
		
	}

	@RequestMapping(value = "replyWarning")
	@ResponseBody
	public HResult<Object> replyWarning(@RequestBody BaseReq baseReq, HttpServletRequest request, HttpServletResponse response){
		try{
			User user = UserUtils.getUser();

			Page<WdtTaskVo> page = wdtTTaskService.findPageForReplyWarning(user.getId(), new Page(baseReq.getStart()/baseReq.getLength()+1, baseReq.getLength()));

			return new HResult<Object>(page);
		}catch(Exception e){
			e.printStackTrace();
			return HResult.fail(ErrorMessage.SYS_ERROR);
		}

	}

	@RequestMapping(value = "atWarning")
	@ResponseBody
	public HResult<Object> atWarning(@RequestBody BaseReq baseReq, HttpServletRequest request, HttpServletResponse response){
		try{
			User user = UserUtils.getUser();

			Page<WdtTaskVo> page = wdtTTaskService.findPageForAtWarning(user.getId(), new Page(baseReq.getStart()/baseReq.getLength()+1, baseReq.getLength()));
			return new HResult<Object>(page);
		}catch(Exception e){
			e.printStackTrace();
			return HResult.fail(ErrorMessage.SYS_ERROR);
		}

	}

	/**
	 * @ 弹框
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "atComment", method = RequestMethod.POST)
	@ResponseBody
	public com.movitech.mbox.common.rest.HResult<Object> progressFeedbackList(@RequestBody TaskCommentReq req) {
		WdtTTaskComments wdtTTaskComments=new WdtTTaskComments();
		wdtTTaskComments.setType(TaskCommentTypeEnum.Status_0.getValue());
		if(StringUtils.isEmpty(req.getPinId())||StringUtils.isEmpty(req.getMessageType())){
			return HResult.fail(ErrorMessage.SYS_ERROR);
		}else{
			ExtWdtTTaskComments extWdtTTaskComments = wdtTTaskCommentsService.getCommentsByATPinId(req.getPinId(),req.getMessageType());
			if(extWdtTTaskComments == null){
				return HResult.fail(ErrorMessage.SYS_ERROR);
			}else{
				return new HResult<Object>(extWdtTTaskComments);
			}
			
		}
		
	}
	/**
	 * @ 弹框
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "atMessage", method = RequestMethod.POST)
	@ResponseBody
	public com.movitech.mbox.common.rest.HResult<Object> atMessage(@RequestBody Message message) {
		if(StringUtils.isEmpty(message.getId())){
			return HResult.fail(ErrorMessage.SYS_ERROR);
		}else{
			Message current = messageService.getById(message.getId());
			if(current == null){
				return HResult.fail(ErrorMessage.SYS_ERROR);
			}else{
				return new HResult<Object>(current);
			}
			
		}
		
	}
	
	/**
	 * 首页之消息列表
	 */
	@RequestMapping(value = "readMsgIds")
	@ResponseBody
	public HResult<Object> readMsg(@RequestBody MessageReq req) {
		boolean flag = messageService.updateReadIds(req.getIds());
		if(flag){
			return new HResult<Object>(true);
		}else{
			return HResult.fail(ErrorMessage.SYS_ERROR);
		}
		
	}
}
