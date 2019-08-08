/**
 * 
 */
package com.movitech.mbox.modules.wdt.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.movitech.mbox.common.config.DeleteStatusEnum;
import com.movitech.mbox.common.config.ErrorMessage;
import com.movitech.mbox.common.config.ExamineStatusEnum;
import com.movitech.mbox.common.config.ExecutionStatusEnum;
import com.movitech.mbox.common.config.Global;
import com.movitech.mbox.common.config.TaskTypeEnum;
import com.movitech.mbox.common.persistence.Page;
import com.movitech.mbox.common.rest.HResult;
import com.movitech.mbox.common.web.BaseController;
import com.movitech.mbox.common.utils.CusAccessObjectUtil;
import com.movitech.mbox.common.utils.StringUtils;
import com.movitech.mbox.modules.sys.dao.UserDao;
import com.movitech.mbox.modules.sys.entity.User;
import com.movitech.mbox.modules.sys.service.UserService;
import com.movitech.mbox.modules.sys.utils.UserUtils;
import com.movitech.mbox.modules.wdt.entity.WdtTTask;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskBanjieLog;
import com.movitech.mbox.modules.wdt.service.WdtTTaskBanjieLogService;
import com.movitech.mbox.modules.wdt.service.WdtTTaskService;

/**
 * 任务办结Controller
 * @author Jack.Gong
 * @version 2017-08-11
 */
@Controller
@RequestMapping(value = "${adminPath}/wdt/wdtTTaskBanjieLog")
public class WdtTTaskBanjieLogController extends BaseController {

    @Autowired
    private WdtTTaskBanjieLogService wdtTTaskBanjieLogService;
    
    @Autowired
    private WdtTTaskService wdtTTaskService;
    
    @Autowired
    private UserService userService;
    
    @ModelAttribute
    public WdtTTaskBanjieLog get(@RequestParam(required=false) String id) {
        WdtTTaskBanjieLog entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = wdtTTaskBanjieLogService.get(id);
        }
        if (entity == null){
            entity = new WdtTTaskBanjieLog();
        }
        return entity;
    }
    
    @RequestMapping(value = "form", method = RequestMethod.POST)
    @ResponseBody
    public HResult<Object> form(@RequestBody WdtTTaskBanjieLog wdtTTaskBanjieLog) {
    	
    	User user = UserUtils.getUser();
    	
    	user = userService.getUserFromDb(user.getId());
    	
    	
    	WdtTTask wdtTTask = wdtTTaskService.getTask(wdtTTaskBanjieLog.getTaskId());
    	
    	wdtTTaskBanjieLog.setCreater(user.getName());
    	wdtTTaskBanjieLog.setOffice(user.getOffice().getName());
    	wdtTTaskBanjieLog.setTaskName(wdtTTask.getName());
    	
    	return new HResult<Object>(wdtTTaskBanjieLog);
    	
    }

    /**
     * 接收值：taskId, content. taskName
     * @param wdtTTaskBanjieLog
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public HResult<Object> save(@RequestBody WdtTTaskBanjieLog wdtTTaskBanjieLog, HttpServletRequest request) {
//        wdtTTaskBanjieLogService.save(wdtTTaskBanjieLog);
    	User user = UserUtils.getUser();
    	user = userService.getUserFromDb(user.getId());
    	
    	wdtTTaskBanjieLog.setType(TaskTypeEnum.Status_0.getValue());//主任务
    	wdtTTaskBanjieLog.setStatus(ExamineStatusEnum.Status_1.getValue());//默认：审核通过
    	wdtTTaskBanjieLog.setCreaterId(user.getId());
//    	wdtTTaskBanjieLog.setDelFlag(DeleteStatusEnum.Status_0.getValue());//未删除
    	
    	wdtTTaskBanjieLog.setIpAddr(CusAccessObjectUtil.getIpAddress(request));
    	
    	String result = wdtTTaskBanjieLogService.closeTask(wdtTTaskBanjieLog);
    	
    	if(ErrorMessage.TASK_ITEM_UPDATE_ERROR.equals(result)){
    		return HResult.fail(ErrorMessage.TASK_ITEM_UPDATE_ERROR);
    	}else if(ErrorMessage.TASK_UPDATE_ERROR.equals(result)){
    		return HResult.fail(ErrorMessage.TASK_UPDATE_ERROR);
    	}else if(ErrorMessage.SYS_ERROR.equals(result)){
    		return HResult.fail(ErrorMessage.SYS_ERROR);
    	}else if(ErrorMessage.BANJIE_TASK_SAVE_ERROR.equals(result)){
    		return HResult.fail(ErrorMessage.BANJIE_TASK_SAVE_ERROR);
    	}
        
        return HResult.success(ErrorMessage.BANJIE_SUCCESS);
    }
    
    
    /**
     * 验证是否有子任务未办结
     * @param wdtTTaskBanjieLog
     * @return
     */
    @RequestMapping(value = "existUnclearSubTask", method = RequestMethod.POST)
    @ResponseBody
    public HResult<Object> existUnclearSubTask(@RequestBody WdtTTaskBanjieLog wdtTTaskBanjieLog) {
    	
    	//获取未办结子任务
    	List<WdtTTask> wdtTTaskList = wdtTTaskService.getSubTaskListByTaskIdAndStatus(wdtTTaskBanjieLog.getTaskId(), ExecutionStatusEnum.Status_8.getValue());
    	
    	if(wdtTTaskList != null && wdtTTaskList.size() > 0){//存在未办结子任务
    		return new HResult<Object>(false, "存在未办结子任务", wdtTTaskList);
    	}

    	return new HResult<Object>(true, "不存在未办结任务", null);
    	
    }

}