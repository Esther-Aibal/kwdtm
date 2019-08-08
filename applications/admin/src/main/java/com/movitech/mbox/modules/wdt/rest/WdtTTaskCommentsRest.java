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
import com.movitech.mbox.common.config.MessageTypeEnum;
import com.movitech.mbox.common.config.OperateLogEnum;
import com.movitech.mbox.common.persistence.Page;
import com.movitech.mbox.common.rest.HResult;
import com.movitech.mbox.common.utils.CusAccessObjectUtil;
import com.movitech.mbox.common.utils.StringUtils;
import com.movitech.mbox.common.web.BaseController;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskComments;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskReponse;
import com.movitech.mbox.modules.wdt.entity.ext.ExtWdtTTaskComments;
import com.movitech.mbox.modules.wdt.operateLog.service.OperateLogService;
import com.movitech.mbox.modules.wdt.req.TaskCommentReq;
import com.movitech.mbox.modules.wdt.service.WdtTTaskCommentsService;
import com.movitech.mbox.modules.wdt.service.WdtTTaskReponseService;

/**
 * 进度汇报Rest
 * @author felix
 * @version 2017-07-25
 */
@RestController
@RequestMapping(value = "${adminPath}/rest/wdtTTaskComment")
public class WdtTTaskCommentsRest extends BaseController {
	
	@Autowired
	private WdtTTaskCommentsService wdtTTaskCommentsService;
	@Autowired
	private OperateLogService operateLogService;
	@Autowired
	private WdtTTaskReponseService wdtTTaskReponseService;

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public HResult<Object> save(HttpServletRequest request,@RequestBody WdtTTaskComments wdtTTaskComments) {
    	int result=0;
    	if(StringUtils.isEmpty(wdtTTaskComments.getId())){
			result=wdtTTaskCommentsService.saveComments(wdtTTaskComments);
		}else{
			result=wdtTTaskCommentsService.updateComments(wdtTTaskComments);
		}
		if(-1==result){
			return HResult.fail(ErrorMessage.NO_TASK_ERROR);
		}else if(-2==result){
			return HResult.fail(ErrorMessage.NO_TASK_ITEM_ERROR);
		}else if(-3==result){
			return HResult.fail(ErrorMessage.NO_TASK_ITEM_ERROR4);
		}
		if(StringUtils.isEmpty(wdtTTaskComments.getId())){
			operateLogService.save(OperateLogEnum.URL24,CusAccessObjectUtil.getIpAddress(request),wdtTTaskComments.getTaskId(), wdtTTaskComments.getTaskItemId());
		}else{
			operateLogService.save(OperateLogEnum.URL25, CusAccessObjectUtil.getIpAddress(request), wdtTTaskComments.getTaskId(), wdtTTaskComments.getTaskItemId());
		}
    	return HResult.success();
    }



	@RequestMapping(value = "list", method = RequestMethod.POST)
	public HResult<Object> list(@RequestBody TaskCommentReq req) {
		WdtTTaskComments wdtTTaskComments=new WdtTTaskComments();
		wdtTTaskComments.setTaskItemId(req.getTaskItemId());
		wdtTTaskComments.setType(req.getType());
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
    
   

}