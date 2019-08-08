package com.movitech.mbox.modules.wdt.h5Rest;



import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.movitech.mbox.common.config.ErrorMessage;
import com.movitech.mbox.common.config.OperateLogEnum;
import com.movitech.mbox.common.rest.HResult;
import com.movitech.mbox.common.utils.CusAccessObjectUtil;
import com.movitech.mbox.common.web.BaseController;
import com.movitech.mbox.modules.wdt.entity.WdtTTask;
import com.movitech.mbox.modules.wdt.exception.RollBackException;
import com.movitech.mbox.modules.wdt.operateLog.service.OperateLogService;
import com.movitech.mbox.modules.wdt.taskStatus.req.TaskStatusReq;
import com.movitech.mbox.modules.wdt.taskStatus.service.TaskStatusService;

/**
 * 任务状态 H5
 * @author Aibal.He
 *
 */
@RestController
@RequestMapping(value = "${adminPath}/h5/taskStatus")
public class TaskStatusH5Rest extends BaseController {
	
	@Autowired
	private TaskStatusService taskStatusService;

	@Autowired
	private OperateLogService operateLogService;
	
	 /** 
	 * 接受
     **/
	@RequestMapping(value = "receive",method = RequestMethod.POST)
	@ResponseBody
    public HResult<Object> receive(@RequestBody TaskStatusReq req, HttpServletRequest request){
    	int result;
		try {
			result = taskStatusService.receive(req);
		} catch (RollBackException e) {
			 return HResult.fail(ErrorMessage.SYS_ERROR);
		}
    	operateLogService.save(OperateLogEnum.URLH513, CusAccessObjectUtil.getIpAddress(request), req.getTaskId(), null);
        if(-1==result){
            return HResult.fail(ErrorMessage.NO_TASK_ERROR);
        }
        return HResult.success();
    	
    }
    /**
     * 拒绝
     * @param req
     * @return
     */
    @RequestMapping(value = "refuse", method = RequestMethod.POST)
    @ResponseBody
    public HResult<Object> refuse(HttpServletRequest request,@RequestBody TaskStatusReq req) {
    	int result;
		try {
			result = taskStatusService.refuse(req);
		} catch (RollBackException e) {
			 return HResult.fail(ErrorMessage.SYS_ERROR);
		}
    	operateLogService.save(OperateLogEnum.URLH512, CusAccessObjectUtil.getIpAddress(request), req.getTaskId(), null);
        if(-1==result){
            return HResult.fail(ErrorMessage.NO_TASK_ERROR);
        }
        return HResult.success();
    }
    
    /**
     * 完成
     * @param wdtTTask
     * @param request
     * @return
     */
    @RequestMapping(value = "complete",method = RequestMethod.POST)
	@ResponseBody
	public HResult<Object> complete(@RequestBody WdtTTask wdtTTask, HttpServletRequest request){
		int result;
		try {
			result = taskStatusService.complete(wdtTTask);
		} catch (RollBackException e) {
			 return HResult.fail(ErrorMessage.SYS_ERROR);
		}
		
		if(result == -1){
    		return HResult.fail(ErrorMessage.NO_TASK_ERROR);
    	}
		operateLogService.save(OperateLogEnum.URLH517, CusAccessObjectUtil.getIpAddress(request), wdtTTask.getId(), null);
        return HResult.success(ErrorMessage.COMPLETE_TASK_SUCCESS);
	}
    
    /**
     * 移交
     * @param req
     * @return
     */
    @RequestMapping(value = "handover", method = RequestMethod.POST)
    @ResponseBody
    public HResult<Object> handover(HttpServletRequest request,@RequestBody TaskStatusReq req) {
    	int result;
		try {
			result = taskStatusService.handover(req);
		} catch (RollBackException e) {
			 return HResult.fail(ErrorMessage.SYS_ERROR);
		}
    	operateLogService.save(OperateLogEnum.URLH521,CusAccessObjectUtil.getIpAddress(request), req.getTaskId(), null);
        if(-1==result){
            return HResult.fail(ErrorMessage.NO_TASK_ERROR);
        }
        
        return HResult.success();
    }
	/**
	 * 办结
	 * @param req
	 * @return
	 *//*
    @RequestMapping(value = "banjie", method = RequestMethod.POST)
    @ResponseBody
    public HResult<Object> banjie(HttpServletRequest request,@RequestBody TaskStatusReq req) {
    	operateLogService.save(OperateLogEnum.URLH5BANJIE.getDescription(),CusAccessObjectUtil.getIpAddress(request), req.getTaskId(), null);
    	int result;
		try {
			result = taskStatusService.banjie(req);
		} catch (RollBackException e) {
			 return HResult.fail(ErrorMessage.SYS_ERROR);
		}
        if(-1==result){
            return HResult.fail(ErrorMessage.NO_TASK_ERROR);
        }
        return HResult.success();
    }
    
    *//**
     * 取消
     * @param req
     * @return
     *//*
    @RequestMapping(value = "cancel", method = RequestMethod.POST)
    @ResponseBody
    public HResult<Object> cancel(HttpServletRequest request,@RequestBody TaskStatusReq req) {
    	operateLogService.save(OperateLogEnum.URLH5CANCEL.getDescription(),CusAccessObjectUtil.getIpAddress(request), req.getTaskId(), null);
    	int result;
		try {
			result = taskStatusService.cancel(req);
		} catch (RollBackException e) {
			 return HResult.fail(ErrorMessage.SYS_ERROR);
		}
        if(-1==result){
            return HResult.fail(ErrorMessage.NO_TASK_ERROR);
        }
        return HResult.success();
    }
    
    *//**
     * 暂停
     * @param req
     * @return
     *//*
    @RequestMapping(value = "pause", method = RequestMethod.POST)
    @ResponseBody
    public HResult<Object> pause(HttpServletRequest request,@RequestBody TaskStatusReq req) {
    	operateLogService.save(OperateLogEnum.URLH5P.getDescription(),CusAccessObjectUtil.getIpAddress(request), req.getTaskId(), null);
    	int result;
		try {
			result = taskStatusService.pause(req);
		} catch (RollBackException e) {
			 return HResult.fail(ErrorMessage.SYS_ERROR);
		}
        if(-1==result){
            return HResult.fail(ErrorMessage.NO_TASK_ERROR);
        }
        return HResult.success();
    }
    
    *//**
     * 延期
     * @param req
     * @return
     *//*
    @RequestMapping(value = "extension", method = RequestMethod.POST)
    @ResponseBody
    public HResult<Object> extension(HttpServletRequest request,@RequestBody TaskStatusReq req) {
    	operateLogService.save(OperateLogEnum.URLH5E.getDescription(),CusAccessObjectUtil.getIpAddress(request), req.getTaskId(), null);
    	int result;
		try {
			result = taskStatusService.extension(req);
		} catch (RollBackException e) {
			 return HResult.fail(ErrorMessage.SYS_ERROR);
		}
        if(-1==result){
            return HResult.fail(ErrorMessage.NO_TASK_ERROR);
        }
        return HResult.success();
    }
    
    *//**
     * 继续
     * @param req
     * @return
     *//*
    @RequestMapping(value = "continue", method = RequestMethod.POST)
    @ResponseBody
    public HResult<Object> continu(HttpServletRequest request,@RequestBody TaskStatusReq req) {
    	operateLogService.save(OperateLogEnum.URLH5C.getDescription(),CusAccessObjectUtil.getIpAddress(request), req.getTaskId(), null);
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
    }*/
 
}