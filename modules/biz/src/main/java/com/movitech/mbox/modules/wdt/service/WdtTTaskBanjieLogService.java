/**
 * 
 */
package com.movitech.mbox.modules.wdt.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movitech.mbox.common.config.ErrorMessage;
import com.movitech.mbox.common.config.ExecutionStatusEnum;
import com.movitech.mbox.common.config.OperateLogEnum;
import com.movitech.mbox.common.persistence.Page;
import com.movitech.mbox.common.service.CrudService;
import com.movitech.mbox.modules.wdt.entity.WdtTTask;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskBanjieLog;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskItem;
import com.movitech.mbox.modules.wdt.operateLog.service.OperateLogService;
import com.movitech.mbox.modules.sys.entity.User;
import com.movitech.mbox.modules.sys.utils.UserUtils;
import com.movitech.mbox.modules.wdt.dao.WdtTTaskBanjieLogDao;

/**
 * 任务办结Service
 * @author Jack.Gong
 * @version 2017-08-11
 */
@Service
@Transactional(readOnly = true)
public class WdtTTaskBanjieLogService extends CrudService<WdtTTaskBanjieLogDao, WdtTTaskBanjieLog> {
	
	@Autowired
	private WdtTTaskItemService wdtTTaskItemService;
	
	@Autowired
	private WdtTTaskService wdtTTaskService;
	
	@Autowired
	private OperateLogService operateLogService;

    public WdtTTaskBanjieLog get(String id) {
        return super.get(id);
    }
    
    public List<WdtTTaskBanjieLog> findList(WdtTTaskBanjieLog wdtTTaskBanjieLog) {
        return super.findList(wdtTTaskBanjieLog);
    }
    
    public Page<WdtTTaskBanjieLog> findPage(Page<WdtTTaskBanjieLog> page, WdtTTaskBanjieLog wdtTTaskBanjieLog) {
        return super.findPage(page, wdtTTaskBanjieLog);
    }
    
    @Transactional(readOnly = false)
    public void save(WdtTTaskBanjieLog wdtTTaskBanjieLog) {
        super.save(wdtTTaskBanjieLog);
    }
    
    @Transactional(readOnly = false)
    public void delete(WdtTTaskBanjieLog wdtTTaskBanjieLog) {
        super.delete(wdtTTaskBanjieLog);
    }

    @Transactional(readOnly = false)
	public String closeTask(WdtTTaskBanjieLog wdtTTaskBanjieLog) {
    	User user = UserUtils.getUser();
    		//1. 更新子节点表
        	WdtTTaskItem updateTaskItem = new WdtTTaskItem();
        	
        	updateTaskItem.setTaskId(wdtTTaskBanjieLog.getTaskId());
        	
        	List<WdtTTaskItem> taskItemList = wdtTTaskItemService.findList(updateTaskItem);
        	if(taskItemList != null && taskItemList.size() > 0){
        		updateTaskItem.setExecutionStatus(ExecutionStatusEnum.Status_8.getValue());//办结
        		updateTaskItem.setUpdateBy(user);
        		updateTaskItem.setUpdateDate(new Date());
        		
            	int updateItemCount = wdtTTaskItemService.updateTaskItem(updateTaskItem);
            	
            	if(updateItemCount < 0 || updateItemCount != taskItemList.size()){
            		return ErrorMessage.TASK_ITEM_UPDATE_ERROR;
            	}
        	}
        	
        	
        	//2. 更新主任务表
        	WdtTTask updateWdtTTask = new WdtTTask();
        	updateWdtTTask.setId(wdtTTaskBanjieLog.getTaskId());
        	
        	updateWdtTTask = wdtTTaskService.get(updateWdtTTask);
        	
        	updateWdtTTask.setExecutionStatus(ExecutionStatusEnum.Status_8.getValue());//办结
        	
        	int updateTaskCount = wdtTTaskService.updateTask(updateWdtTTask);
        	
        	if(!(updateTaskCount > 0)){
        		return ErrorMessage.TASK_UPDATE_ERROR;
        	}
        	
	        	//3. 更新任务办结表
        		wdtTTaskBanjieLog.setCreateBy(user);
        		wdtTTaskBanjieLog.setCreateDate(new Date());
        		wdtTTaskBanjieLog.setUpdateBy(user);
        		wdtTTaskBanjieLog.setUpdateDate(new Date());
	        	this.save(wdtTTaskBanjieLog);

        	
        	operateLogService.save(OperateLogEnum.URL31, wdtTTaskBanjieLog.getIpAddr(), wdtTTaskBanjieLog.getTaskId(), null);
        	
        	return ErrorMessage.SUCCESS;

    	
	}
    
}