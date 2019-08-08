package com.movitech.mbox.modules.wdt.h5Rest;

import com.movitech.mbox.common.config.ErrorMessage;
import com.movitech.mbox.common.persistence.Page;
import com.movitech.mbox.common.rest.HResult;
import com.movitech.mbox.common.web.BaseController;
import com.movitech.mbox.modules.sys.entity.User;
import com.movitech.mbox.modules.sys.utils.UserUtils;
import com.movitech.mbox.modules.wdt.entity.Message;
import com.movitech.mbox.modules.wdt.entity.WdtTaskVo;
import com.movitech.mbox.modules.wdt.req.BaseReq;
import com.movitech.mbox.modules.wdt.service.MessageService;
import com.movitech.mbox.modules.wdt.service.WdtTTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 
 * @author Jack.Gong
 * @version 2017.08.22
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/messageh5")
public class MessageH5Rest extends BaseController{
	
	@Autowired
	private WdtTTaskService  wdtTTaskService;
	
	@Autowired
	private MessageService messageService;

	/**
	 * 消息,h5所有消息
	 * @param baseReq
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "messagelist")
	@ResponseBody
	public HResult<Object> messageList(@RequestBody BaseReq baseReq, HttpServletRequest request, HttpServletResponse response){
		
		try{
			User user = UserUtils.getUser();
			//user.setId("xiangxd");
			Page<WdtTaskVo> page = null;
			page = wdtTTaskService.findPageWarningH5(user.getId(), new Page(baseReq.getStart()/baseReq.getLength()+1, baseReq.getLength()));
			return new HResult<Object>(page);
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
	@RequestMapping(value = "moreWaitingTask")
	@ResponseBody
	public HResult<Object> moreWaitingTask(@RequestBody BaseReq baseReq, HttpServletRequest request, HttpServletResponse response) {
		try{
			User user = UserUtils.getUser();
			//user.setId("xiangxd");
			Page<WdtTaskVo> page = wdtTTaskService.findPageForWatingH5Task(user.getId(), new Page(baseReq.getStart()/baseReq.getLength()+1, baseReq.getLength()));
			return new HResult<Object>(page);
		}catch(Exception e){
			e.printStackTrace();
			return HResult.fail(ErrorMessage.SYS_ERROR);
		}

	}
	/**
	 * 任务数量 -
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "getTaskCounts")
	@ResponseBody
	public HResult<Object> getTaskCounts( HttpServletRequest request, HttpServletResponse response){
		try{
			User user = UserUtils.getUser();
			//user.setId("xiangxd");
			Map<String,Object> map = wdtTTaskService.getTaskCounts(user.getId());
			return new HResult<Object>(map);
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
	@RequestMapping(value = "moreMyFollowedTask")
	@ResponseBody
	public HResult<Object> moreMyFollowedTask(@RequestBody BaseReq baseReq, HttpServletRequest request, HttpServletResponse response){
		try{
			User user = UserUtils.getUser();
            //user.setId("xiangxd");
			Page<WdtTaskVo> page = wdtTTaskService.findPageForFollowedTask(user.getId(),baseReq.getSearch(), new Page(baseReq.getStart()/baseReq.getLength()+1, baseReq.getLength()));

			return new HResult<Object>(page.getList());

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
	@RequestMapping(value = "moreMyCreatedTask")
	@ResponseBody
	public HResult<Object> moreMyCreatedTask(@RequestBody BaseReq baseReq, HttpServletRequest request, HttpServletResponse response){

		try{

			User user = UserUtils.getUser();
			//user.setId("xiangxd");
			Page<WdtTaskVo> page = wdtTTaskService.findPageForCreatedTask(user.getId(),baseReq.getSearch(), new Page(baseReq.getStart()/baseReq.getLength()+1, baseReq.getLength()));

			return new HResult<Object>(page.getList());

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
	@RequestMapping(value = "moreMyOwnTask")
	@ResponseBody
	public HResult<Object> moreMyOwnTask(@RequestBody BaseReq baseReq, HttpServletRequest request, HttpServletResponse response){

		try{
			User user = UserUtils.getUser();
			//user.setId("xiangxd");
			Page<WdtTaskVo> page = wdtTTaskService.findPageForOwnTask(user.getId(),baseReq.getSearch(), new Page(baseReq.getStart()/baseReq.getLength()+1, baseReq.getLength()));
			return new HResult<Object>(page.getList());
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
	@RequestMapping(value = "moreMyDeferredTask")
	@ResponseBody
	public HResult<Object> moreMyDeferredTask(@RequestBody BaseReq baseReq, HttpServletRequest request, HttpServletResponse response){

		try{

			User user = UserUtils.getUser();
			//user.setId("xiangxd");
			Page<WdtTaskVo> page = wdtTTaskService.findPageForDeferredTask(user.getId(),baseReq.getSearch(), new Page(baseReq.getStart()/baseReq.getLength()+1, baseReq.getLength()));

			return new HResult<Object>(page.getList());

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
	@RequestMapping(value = "moreMyNearingDueTask")
	@ResponseBody
	public HResult<Object> moreMyNearingDueTask(@RequestBody BaseReq baseReq, HttpServletRequest request, HttpServletResponse response){

		try{

			User user = UserUtils.getUser();
			//user.setId("xiangxd");
			Page<WdtTaskVo> page = wdtTTaskService.findPageForNearingDueTask(user.getId(),baseReq.getSearch(), new Page(baseReq.getStart()/baseReq.getLength()+1, baseReq.getLength()));

			return new HResult<Object>(page.getList());

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
	@RequestMapping(value = "moreMyParticipantTask")
	@ResponseBody
	public HResult<Object> moreMyParticipantTask(@RequestBody BaseReq baseReq, HttpServletRequest request, HttpServletResponse response){

		try{

			User user = UserUtils.getUser();
			//user.setId("xiangxd");
			Page<WdtTaskVo> page = wdtTTaskService.findPageForParticipantTask(user.getId(),baseReq.getSearch(), new Page(baseReq.getStart()/baseReq.getLength()+1, baseReq.getLength()));

			return new HResult<Object>(page.getList());

		}catch(Exception e){
			e.printStackTrace();
			return HResult.fail(ErrorMessage.SYS_ERROR);
		}

	}
	
	/**
	 * 首页之消息列表
	 */
	@RequestMapping(value = "readMsg")
	@ResponseBody
	public HResult<Object> readMsg(@RequestBody Message message) {
		messageService.updateRead(message.getId());
		return new HResult<Object>(true);
	}
}
