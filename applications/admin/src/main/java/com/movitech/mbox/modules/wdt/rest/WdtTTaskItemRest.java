/**
 * 
 */
package com.movitech.mbox.modules.wdt.rest;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.movitech.mbox.common.config.ErrorMessage;
import com.movitech.mbox.common.config.OperateLogEnum;
import com.movitech.mbox.common.persistence.Page;
import com.movitech.mbox.common.rest.HResult;
import com.movitech.mbox.common.utils.CusAccessObjectUtil;
import com.movitech.mbox.common.utils.StringUtils;
import com.movitech.mbox.common.web.BaseController;
import com.movitech.mbox.modules.sys.entity.User;
import com.movitech.mbox.modules.sys.utils.UserUtils;
import com.movitech.mbox.modules.wdt.entity.ext.ExtWdtTTaskItem;
import com.movitech.mbox.modules.wdt.exception.RollBackException;
import com.movitech.mbox.modules.wdt.operateLog.service.OperateLogService;
import com.movitech.mbox.modules.wdt.req.TaskItemReq;
import com.movitech.mbox.modules.wdt.service.WdtTTaskItemService;

/**
 * 节点Rest
 * @author felix
 * @version 2017-07-25
 */
@RestController
@RequestMapping(value = "${adminPath}/rest/wdtTTaskItem")
public class WdtTTaskItemRest extends BaseController {
	
	@Autowired
	private WdtTTaskItemService wdtTTaskItemService;

	@Autowired
	private OperateLogService operateLogService;
    @RequestMapping(value = {"list", ""}, method = RequestMethod.POST)
    public HResult<Object> list(@RequestBody TaskItemReq req) {
        ExtWdtTTaskItem extWdtTTaskItem=new ExtWdtTTaskItem();
        User user = UserUtils.getUser();
        extWdtTTaskItem.setTaskId(req.getTaskId());
        extWdtTTaskItem.setCurrentUser(user);
    	Page<ExtWdtTTaskItem> page= wdtTTaskItemService.findPage(new Page<ExtWdtTTaskItem>(req.getStart()/req.getLength()+1, req.getLength()),extWdtTTaskItem);
        return new HResult<Object>(page);
    }
    
    @RequestMapping(value = "info", method = RequestMethod.POST)
    public HResult<Object> info(@RequestBody TaskItemReq req) {
    	ExtWdtTTaskItem wdtTTaskItem = wdtTTaskItemService.getInfo(req.getTaskItemId());
        return new HResult<Object>(wdtTTaskItem);
    }
    
  
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public HResult<Object> save(HttpServletRequest request,@RequestBody ExtWdtTTaskItem wdtTTaskItem) {
    	int result=0;
        try {
			if(StringUtils.isEmpty(wdtTTaskItem.getId())){
				 result=wdtTTaskItemService.saveExt(wdtTTaskItem);
				 operateLogService.save(OperateLogEnum.URL21, CusAccessObjectUtil.getIpAddress(request), wdtTTaskItem.getTaskId(), wdtTTaskItem.getId());
			}else{
			     result=wdtTTaskItemService.update(wdtTTaskItem);
			     operateLogService.save(OperateLogEnum.URL211, CusAccessObjectUtil.getIpAddress(request), wdtTTaskItem.getTaskId(), wdtTTaskItem.getId());
			}
		} catch (RollBackException e) {
			 return HResult.fail(ErrorMessage.SYS_ERROR);
		}
    	if(-1==result){
            return HResult.fail(ErrorMessage.NO_TASK_ERROR);
        }else if(-2==result){
            return HResult.fail(ErrorMessage.NO_TASK_ITEM_ERROR);
        }else if(-3==result){
            return HResult.fail(ErrorMessage.NO_TASK_ITEM_PERSON_ERROR);
        }
    	return HResult.success();
    }
    
    /**
     * 节点移交
     * @param req
     * @return
     */
    @RequestMapping(value = "handover", method = RequestMethod.POST)
    public HResult<Object> handover(HttpServletRequest request,@RequestBody TaskItemReq req) {
    	int result;
		try {
			result = wdtTTaskItemService.handover(req);
		} catch (RollBackException e) {
			 return HResult.fail(ErrorMessage.SYS_ERROR);
		}
    	operateLogService.save(OperateLogEnum.URL23, CusAccessObjectUtil.getIpAddress(request),req.getTaskId(), req.getTaskItemId());
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
    public HResult<Object> finish(HttpServletRequest request,@RequestBody TaskItemReq req) {
    	int result;
		try {
			result = wdtTTaskItemService.finish(req);
		} catch (RollBackException e) {
			 return HResult.fail(ErrorMessage.SYS_ERROR);
		}
    	operateLogService.save(OperateLogEnum.URL22, CusAccessObjectUtil.getIpAddress(request), req.getTaskId(), req.getTaskItemId());
        if(-1==result){
            return HResult.fail(ErrorMessage.NO_TASK_ITEM_ERROR);
        }else if(-2==result){
            return HResult.fail(ErrorMessage.NO_TASK_ITEM_ERROR2);
        }else if(-3==result){
            return HResult.fail(ErrorMessage.NO_TASK_ITEM_ERROR3);
        }
    	return HResult.success();
    }

    
    
   

}