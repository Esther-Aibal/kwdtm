/**
 * 
 */
package com.movitech.mbox.modules.wdt.rest;



import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.movitech.mbox.modules.wdt.exception.RollBackException;
import com.movitech.mbox.modules.wdt.operateLog.service.OperateLogService;
import com.movitech.mbox.modules.wdt.taskStatus.entity.TaskOa;
import com.movitech.mbox.modules.wdt.taskStatus.req.TaskBackReq;
import com.movitech.mbox.modules.wdt.taskStatus.req.TaskStatusReq;
import com.movitech.mbox.modules.wdt.taskStatus.service.TaskStatusService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.movitech.mbox.common.config.ErrorMessage;
import com.movitech.mbox.common.config.OperateLogEnum;
import com.movitech.mbox.common.rest.HResult;
import com.movitech.mbox.common.utils.CusAccessObjectUtil;
import com.movitech.mbox.common.web.BaseController;

/**
 * 任务状态rest
 * @author felix.jin
 * 2017年8月14日
 */
@RestController
@RequestMapping(value = "${adminPath}/rest/task")
public class WdtTTaskStatusRest extends BaseController {
	
	@Autowired
	private TaskStatusService taskStatusService;
	@Autowired
	private OperateLogService operateLogService;

	/**
	 * 办结
	 * @param req
	 * @return
	 */
    @RequestMapping(value = "banjie", method = RequestMethod.POST)
    public HResult<Object> banjie(HttpServletRequest request,@RequestBody TaskStatusReq req) {
    	int result;
		try {
			result = taskStatusService.banjie(req,CusAccessObjectUtil.getIpAddress(request));
		} catch (RollBackException e) {
			 return HResult.fail(ErrorMessage.SYS_ERROR);
		}
        if(-1==result){
            return HResult.fail(ErrorMessage.NO_TASK_ERROR);
        }
        return HResult.success(result ==2?"办结成功":"办结申请已提交，请等待OA审批；您可在任务详情—审批记录中查看审批详情");
    }
    
    /**
     * 取消
     * @param req
     * @return
     */
    @RequestMapping(value = "cancel", method = RequestMethod.POST)
    public HResult<Object> cancel(HttpServletRequest request,@RequestBody TaskStatusReq req) {
    	int result;
		try {
			result = taskStatusService.cancel(req,CusAccessObjectUtil.getIpAddress(request));
		} catch (RollBackException e) {
			 return HResult.fail(ErrorMessage.SYS_ERROR);
		}
        if(-1==result){
            return HResult.fail(ErrorMessage.NO_TASK_ERROR);
        }
        return HResult.success(result ==2?"取消成功":"取消申请已提交，请等待OA审批；您可在任务详情—审批记录中查看审批详情");
    }
    
    /**
     * 暂停
     * @param req
     * @return
     */
    @RequestMapping(value = "pause", method = RequestMethod.POST)
    public HResult<Object> pause(HttpServletRequest request,@RequestBody TaskStatusReq req) {
    	int result;
		try {
			result = taskStatusService.pause(req, CusAccessObjectUtil.getIpAddress(request));
		} catch (RollBackException e) {
			 return HResult.fail(ErrorMessage.SYS_ERROR);
		}
        if(-1==result){
            return HResult.fail(ErrorMessage.NO_TASK_ERROR);
        }
        
        return HResult.success(result ==2?"暂停成功":"暂停申请已提交，请等待OA审批；您可在任务详情—审批记录中查看审批详情");
    }
    
    /**
     * 延期
     * @param req
     * @return
     */
    @RequestMapping(value = "extension", method = RequestMethod.POST)
    public HResult<Object> extension(HttpServletRequest request,@RequestBody TaskStatusReq req) {
        
    	int result;
		try {
			result = taskStatusService.extension(req,CusAccessObjectUtil.getIpAddress(request));
		} catch (RollBackException e) {
			 return HResult.fail(ErrorMessage.SYS_ERROR);
		}
        if(-1==result){
            return HResult.fail(ErrorMessage.NO_TASK_ERROR);
        }
        return HResult.success(result ==2?"延期成功":"延期申请已提交，请等待OA审批；您可在任务详情—审批记录中查看审批详情");
    }


    /**
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "token/back", method = RequestMethod.POST)
    public HResult<Object> back(@RequestBody TaskBackReq req) {
        int result;
		try {
			result = taskStatusService.back(req);
		} catch (RollBackException e) {
			 return HResult.fail(ErrorMessage.SYS_ERROR);
		}
        if(-1==result){
            return HResult.fail(ErrorMessage.NO_TASK_ERROR);
        }else if(-2==result){
        	 return HResult.fail(ErrorMessage.NO_TASK_TEPE_ERROR);
        }else if(-3==result){
            return HResult.fail(ErrorMessage.TASK_OA_ERROR3);
        }else if(-4==result){
            return HResult.fail(ErrorMessage.TASK_OA_ERROR4);
        }
        return HResult.success();
    }

    /**
     * 删除
     * @param req
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public HResult<Object> delete(HttpServletRequest request,@RequestBody TaskStatusReq req) {
        try {
			taskStatusService.delete(req);
		}catch (RollBackException e) {
			 return HResult.fail(ErrorMessage.SYS_ERROR);
		}
        return HResult.success();
    }
    
    /**
     * 拒绝
     * @param req
     * @return
     */
    @RequestMapping(value = "refuse", method = RequestMethod.POST)
    public HResult<Object> refuse(HttpServletRequest request,@RequestBody TaskStatusReq req) {
    	int result;
		try {
			result = taskStatusService.refuse(req);
		}catch (RollBackException e) {
			 return HResult.fail(ErrorMessage.SYS_ERROR);
		}
    	operateLogService.save(OperateLogEnum.URL17, CusAccessObjectUtil.getIpAddress(request), req.getTaskId(), null);
        if(-1==result){
            return HResult.fail(ErrorMessage.NO_TASK_ERROR);
        }
        return HResult.success();
    }
    
    /**
     * 移交
     * @param req
     * @return
     */
    @RequestMapping(value = "handover", method = RequestMethod.POST)
    public HResult<Object> handover(HttpServletRequest request,@RequestBody TaskStatusReq req) {
    	int result;
		try {
			result = taskStatusService.handover(req);
		} catch (RollBackException e) {
			 return HResult.fail(ErrorMessage.SYS_ERROR);
		}
    	operateLogService.save(OperateLogEnum.URL112, CusAccessObjectUtil.getIpAddress(request), req.getTaskId(), null);
        if(-1==result){
            return HResult.fail(ErrorMessage.NO_TASK_ERROR);
        }
        return HResult.success();
    }
    
    /**
     * 继续
     * @param req
     * @return
     */
    @RequestMapping(value = "continue", method = RequestMethod.POST)
    public HResult<Object> continu(HttpServletRequest request,@RequestBody TaskStatusReq req) {
    	 operateLogService.save(OperateLogEnum.URL114, CusAccessObjectUtil.getIpAddress(request), req.getTaskId(), null);
    	 int result;
		try {
			result = taskStatusService.continu(req);
		} catch (RollBackException e) {
			 return HResult.fail(ErrorMessage.SYS_ERROR);
		}
    	 if(-1==result){
             return HResult.fail(ErrorMessage.NO_TASK_ERROR);
         }
        return HResult.success();
    }
    
    /**
     * oa列表
     * @param req
     * @return
     */
    @RequestMapping(value = "oa/list", method = RequestMethod.POST)
    public HResult<Object> list(@RequestBody TaskStatusReq req) {
    	List<TaskOa> list=taskStatusService.list(req.getTaskId());
        return new HResult<Object>(list);
    }
    
    /**
     * 撤回
     * @param req
     * @return
     */
    @RequestMapping(value = "recall", method = RequestMethod.POST)
    public HResult<Object> recall(HttpServletRequest request,@RequestBody TaskStatusReq req) {
    	int result;
		try {
			result = taskStatusService.recall(req);
		} catch (RollBackException e) {
			 return HResult.fail(ErrorMessage.SYS_ERROR);
		}
        if(-1==result){
            return HResult.fail(ErrorMessage.NO_TASK_ERROR);
        }
        operateLogService.save(OperateLogEnum.URL71, CusAccessObjectUtil.getIpAddress(request), req.getTaskId(), null);
        return HResult.success(result ==2?"暂停成功":"暂停申请已提交，请等待OA审批；您可在任务详情—审批记录中查看审批详情");
    }
    
    /**
     * 回退
     * @param req
     * @return
     */
    @RequestMapping(value = "turnBack", method = RequestMethod.POST)
    public HResult<Object> turnBack(HttpServletRequest request,@RequestBody TaskStatusReq req) {
    	int result;
		try {
			result = taskStatusService.turnBack(req);
		} catch (RollBackException e) {
			 return HResult.fail(ErrorMessage.SYS_ERROR);
		}
        if(-1==result){
            return HResult.fail(ErrorMessage.NO_TASK_ERROR);
        }else if (-2==result){
        	return HResult.fail(ErrorMessage.TASK_TURNBACK_ERROR);
        }
        operateLogService.save(OperateLogEnum.URL72, CusAccessObjectUtil.getIpAddress(request), req.getTaskId(), null);
        return HResult.success(ErrorMessage.TASK_TURNBACK_OK);
    }
}