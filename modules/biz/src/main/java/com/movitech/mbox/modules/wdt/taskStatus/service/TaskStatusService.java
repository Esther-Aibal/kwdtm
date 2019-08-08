/**
 * 
 */
package com.movitech.mbox.modules.wdt.taskStatus.service;


import com.movitech.mbox.common.config.*;
import com.movitech.mbox.common.utils.DateUtils;
import com.movitech.mbox.common.utils.IdGen;
import com.movitech.mbox.common.utils.StringUtils;
import com.movitech.mbox.modules.sys.entity.User;
import com.movitech.mbox.modules.sys.service.UserService;
import com.movitech.mbox.modules.sys.utils.UserUtils;
import com.movitech.mbox.modules.wdt.dao.*;
import com.movitech.mbox.modules.wdt.dao.ext.ExtWdtTTaskItemDao;
import com.movitech.mbox.modules.wdt.entity.*;
import com.movitech.mbox.modules.wdt.operateLog.service.OperateLogService;
import com.movitech.mbox.modules.wdt.service.MessageService;
import com.movitech.mbox.modules.wdt.service.SendEMailService;
import com.movitech.mbox.modules.wdt.service.WdtTTaskHandoverService;
import com.movitech.mbox.modules.wdt.service.WdtTTaskPersonService;
import com.movitech.mbox.modules.wdt.taskStatus.dao.TaskBanjieMapper;
import com.movitech.mbox.modules.wdt.taskStatus.dao.TaskCancelMapper;
import com.movitech.mbox.modules.wdt.taskStatus.dao.TaskExtensionMapper;
import com.movitech.mbox.modules.wdt.taskStatus.dao.TaskPauseMapper;
import com.movitech.mbox.modules.wdt.taskStatus.dao.ext.TaskStatusMapper;
import com.movitech.mbox.modules.wdt.taskStatus.entity.*;
import com.movitech.mbox.modules.wdt.taskStatus.req.TaskBackReq;
import com.movitech.mbox.modules.wdt.taskStatus.req.TaskStatusReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * 任务状态Service
 * @author felix.jin
 * 2017年8月14日
 */
@Service
@Transactional(readOnly = true)
public class TaskStatusService {
	
	@Autowired
	private TaskBanjieMapper taskBanjieMapper;
	
	@Autowired
	private TaskCancelMapper taskCancelMapper;
	
	@Autowired
	private TaskExtensionMapper taskExtensionMapper;
	
	@Autowired
	private TaskPauseMapper taskPauseMapper;
	
	@Autowired
	private TaskStatusMapper taskStatusMapper;

	@Autowired
	private ExtWdtTTaskItemDao extWdtTTaskItemDao;

	@Autowired
	private UserService userService;
	
	@Autowired
	private WdtTTaskPersonService wdtTTaskPersonService;
	
	@Autowired
	private WdtTTaskHandoverService wdtTTaskHandoverService;

	@Autowired
	private OaService oaService;

	@Autowired
	private MessageDao messageDao;
	
	@Autowired
	private WdtTTaskItemDao wdtTTaskItemDao;
	@Autowired
	private WdtTTaskDao wdtTTaskDao;
	@Autowired
	MessageService  messageService;
	
	@Autowired
	private WdtTTaskPersonDao wdtTTaskPersonDao;
	
	@Autowired
	private OperateLogService operateLogService;
	
	@Autowired
	private WdtTTaskThemeDao wdtTTaskThemeDao;
	
	@Autowired
	private WdtTTaskHandoverDao wdtTTaskHandoverDao;
	
	@Autowired
   	private SendEMailService sendEMailService;
	
	private boolean isNeedApply(String taskId){
		WdtTTaskTheme theme = wdtTTaskThemeDao.getThemeByTaskId(taskId);
		if(theme!=null){
			if(StringUtils.isNotEmpty(theme.getIsTotalApprove()) && theme.getIsTotalApprove().equals("1")){
				return false;
			}
		}
		return true;
	}
	@Transactional(readOnly = false)
	public int banjie(TaskStatusReq req,String ipAddress) {
			WdtTTask task = extWdtTTaskItemDao.getTask(req.getTaskId());
			if(task==null){
				return -1;
			}
			//非督办任务不需要进行审核申请
			boolean flag = isNeedApply(task.getId());
			
			TaskBanjie record=new TaskBanjie();
			record.setId(IdGen.uuid());
			record.setType(TaskTypeEnum.Status_0.getValue());
			record.setTaskId(task.getId());
			if(flag){
				record.setStatus(ExamineStatusEnum.Status_0.getValue());
			}else{
				record.setStatus(ExamineStatusEnum.Status_1.getValue());
			}
			
			record.setContent(req.getContent());
			User u= UserUtils.getUser();
			if(u!=null){
				record.setCreaterId(u.getId());
				record.setCreateBy(u.getId());
				record.setUpdateBy(u.getId());
			}
			record.setCreateDate(new Date());
			record.setUpdateDate(record.getCreateDate());
			record.setDelFlag(DeleteStatusEnum.Status_0.getValue());
			
			taskBanjieMapper.insertSelective(record);
			if(flag){
				
				req.setId(record.getId());
				req.setTypeId(record.getId());
				req.setType(TaskStatusEnum.Status_1.getValue());
				oaService.sendOa(task,u,req);
				operateLogService.save(OperateLogEnum.URL12, ipAddress, req.getTaskId(), null);
				return 1;
			}else{
				//更新任务状态
				extWdtTTaskItemDao.upTaskStatus(task.getId(),ExecutionStatusEnum.Status_8.getValue());
				extWdtTTaskItemDao.upTaskItemStatus(task.getId(),ExecutionStatusEnum.Status_8.getValue());
				operateLogService.save(OperateLogEnum.URLN12, null, req.getTaskId(), null);
				
				return 2;
			}

	}
	@Transactional(readOnly = false)
	public int cancel(TaskStatusReq req,String ipAddress) {
			WdtTTask task = extWdtTTaskItemDao.getTask(req.getTaskId());
			if(task==null){
				return -1;
			}
			//非督办任务不需要进行审核申请
			boolean flag = isNeedApply(task.getId());
			
			TaskCancel record=new TaskCancel();
			record.setId(IdGen.uuid());
			record.setType(TaskTypeEnum.Status_0.getValue());
			record.setTaskId(task.getId());
			if(flag){
				record.setStatus(ExamineStatusEnum.Status_0.getValue());
			}else{
				record.setStatus(ExamineStatusEnum.Status_1.getValue());
			}
			
			record.setContent(req.getContent());
			User u= UserUtils.getUser();
			if(u!=null){
				record.setCreaterId(u.getId());
				record.setCreateBy(u.getId());
				record.setUpdateBy(u.getId());
			}
			record.setCreateDate(new Date());
			record.setUpdateDate(record.getCreateDate());
			record.setDelFlag(DeleteStatusEnum.Status_0.getValue());
			
			taskCancelMapper.insertSelective(record);
			if(flag){
				
				req.setId(record.getId());
				req.setTypeId(record.getId());
				req.setType(TaskStatusEnum.Status_3.getValue());
				oaService.sendOa(task,u,req);
				operateLogService.save(OperateLogEnum.URL13, ipAddress, req.getTaskId(), null);
				return 1;
			}else{
				//更新任务状态
				extWdtTTaskItemDao.upTaskStatus(task.getId(),ExecutionStatusEnum.Status_5.getValue());
				extWdtTTaskItemDao.upTaskItemStatus(task.getId(),ExecutionStatusEnum.Status_5.getValue());
				operateLogService.save(OperateLogEnum.URLN13, null, req.getTaskId(), null);
				return 2;
			}

	}

	@Transactional(readOnly = false)
	public int pause(TaskStatusReq req,String ipAddress) {
			WdtTTask task = extWdtTTaskItemDao.getTask(req.getTaskId());
			if(task==null){
				return -1;
			}
			//非督办任务不需要进行审核申请
			boolean flag = isNeedApply(task.getId());
			
			TaskPause record=new TaskPause();
			record.setId(IdGen.uuid());
			record.setType(TaskTypeEnum.Status_0.getValue());
			record.setTaskId(task.getId());
			if(flag){
				record.setStatus(ExamineStatusEnum.Status_0.getValue());
			}else{
				record.setStatus(ExamineStatusEnum.Status_1.getValue());
			}
			record.setContent(req.getContent());
			User u= UserUtils.getUser();
			if(u!=null){
				record.setCreaterId(u.getId());
				record.setCreateBy(u.getId());
				record.setUpdateBy(u.getId());
			}
			record.setPauseEndTime(req.getNewTime());
			record.setCreateDate(new Date());
			record.setUpdateDate(record.getCreateDate());
			record.setPauseStartTime(record.getCreateDate());
			req.setStartTime(record.getCreateDate());
			record.setDelFlag(DeleteStatusEnum.Status_0.getValue());
			taskPauseMapper.insertSelective(record);
			if(flag){
				
				req.setId(record.getId());
				req.setTypeId(record.getId());
				req.setType(TaskStatusEnum.Status_2.getValue());
				oaService.sendOa(task,u,req);
				operateLogService.save(OperateLogEnum.URL14, ipAddress, req.getTaskId(), null);
				return 1;
			}else{
				//更新任务状态
				extWdtTTaskItemDao.upTaskStatus(task.getId(),ExecutionStatusEnum.Status_4.getValue());
				extWdtTTaskItemDao.upTaskItemStatus(task.getId(),ExecutionStatusEnum.Status_4.getValue());
				operateLogService.save(OperateLogEnum.URLN14, ipAddress, req.getTaskId(), null);
				return 2;
			}

	}
	@Transactional(readOnly = false)
	public int extension(TaskStatusReq req,String ipAddress) {
			WdtTTask task = extWdtTTaskItemDao.getTask(req.getTaskId());
			if(task==null){
				return -1;
			}
			//非督办任务不需要进行审核申请
			boolean flag = isNeedApply(task.getId());
			TaskExtension  record=new TaskExtension();
			record.setId(IdGen.uuid());
			record.setType(TaskTypeEnum.Status_0.getValue());
			record.setTaskId(task.getId());
			
			if(flag){
				record.setStatus(ExamineStatusEnum.Status_0.getValue());
			}else{
				record.setStatus(ExamineStatusEnum.Status_1.getValue());
			}
			
			record.setOldCompletionTime(task.getEndDate());
			req.setStartTime(task.getEndDate());
			record.setNewCompletionTime(req.getNewTime());
			record.setContent(req.getContent());
			User u= UserUtils.getUser();
			if(u!=null){
				record.setCreaterId(u.getId());
				record.setCreateBy(u.getId());
				record.setUpdateBy(u.getId());
			}
			record.setCreateDate(new Date());
			record.setUpdateDate(record.getCreateDate());
			record.setDelFlag(DeleteStatusEnum.Status_0.getValue());
			taskExtensionMapper.insertSelective(record);
			if(flag){
				
				
				req.setId(record.getId());
				req.setTypeId(record.getId());
				req.setType(TaskStatusEnum.Status_0.getValue());
				oaService.sendOa(task,u,req);
				operateLogService.save(OperateLogEnum.URL15, ipAddress, req.getTaskId(), null);
				return 1;
			}else{
				//更新任务状态
				//extWdtTTaskItemDao.upTaskStatus(task.getId(),ExecutionStatusEnum.Status_3.getValue(),0);
				//extWdtTTaskItemDao.upTaskItemStatus(task.getId(),ExecutionStatusEnum.Status_3.getValue());
				operateLogService.save(OperateLogEnum.URLN15, ipAddress, req.getTaskId(), null);
				return 2;
			}
	}


	@Transactional(readOnly = false)
	public int back(TaskBackReq req) {
			WdtTTask task = extWdtTTaskItemDao.getTask(req.getTaskId());
			if(task==null){
				return -1;
			}
			String status=null;
			Boolean flag=false;
			String executionStatus=null;
			if("1".equals(req.getApprove_status())){
				status=ExamineStatusEnum.Status_1.getValue();
				flag=true;
			}else{
				status=ExamineStatusEnum.Status_2.getValue();
			}
			if(TaskStatusEnum.Status_0.getValue().equals(req.getType())){
				TaskExtension record=taskExtensionMapper.selectByPrimaryKey(req.getTypeId());
				if(record==null){
					return -3;
				}else if(!ExamineStatusEnum.Status_0.getValue().equals(record.getStatus())){
					return -4;
				}
				record.setStatus(status);
				record.setApproveTime(DateUtils.parseDate(req.getApprove_time()));
				record.setApproverId(req.getApprover_id());
				record.setApproveRemark(req.getApprove_remark());
				record.setUpdateDate(new Date());
				taskExtensionMapper.updateByPrimaryKeySelective(record);
			}else if(TaskStatusEnum.Status_1.getValue().equals(req.getType())){
				TaskBanjie record=taskBanjieMapper.selectByPrimaryKey(req.getTypeId());
				if(record==null){
					return -3;
				}else if(!ExamineStatusEnum.Status_0.getValue().equals(record.getStatus())){
					return -4;
				}
				record.setStatus(status);
				record.setApproveTime(DateUtils.parseDate(req.getApprove_time()));
				record.setApproverId(req.getApprover_id());
				record.setApproveRemark(req.getApprove_remark());
				record.setUpdateDate(new Date());
				taskBanjieMapper.updateByPrimaryKeySelective(record);
				executionStatus= ExecutionStatusEnum.Status_8.getValue();
				if(flag){
					extWdtTTaskItemDao.upTaskStatus(task.getId(),executionStatus);
					extWdtTTaskItemDao.upTaskItemStatus(task.getId(),executionStatus);
				}
			}else if(TaskStatusEnum.Status_2.getValue().equals(req.getType())){
				TaskPause record=taskPauseMapper.selectByPrimaryKey(req.getTypeId());
				if(record==null){
					return -3;
				}else if(!ExamineStatusEnum.Status_0.getValue().equals(record.getStatus())){
					return -4;
				}
				record.setStatus(status);
				record.setApproveTime(DateUtils.parseDate(req.getApprove_time()));
				record.setApproverId(req.getApprover_id());
				record.setApproveRemark(req.getApprove_remark());
				record.setUpdateDate(new Date());
				taskPauseMapper.updateByPrimaryKeySelective(record);
				executionStatus= ExecutionStatusEnum.Status_4.getValue();
				//暂停
				if(flag){
					extWdtTTaskItemDao.upTaskStatus(task.getId(),executionStatus);
					extWdtTTaskItemDao.upTaskItemStatus(task.getId(),executionStatus);
				}
				operateLogService.save(OperateLogEnum.URLB4, null, req.getTaskId(), null);
			}else if(TaskStatusEnum.Status_3.getValue().equals(req.getType())){
				TaskCancel record=taskCancelMapper.selectByPrimaryKey(req.getTypeId());
				if(record==null){
					return -3;
				}else if(!ExamineStatusEnum.Status_0.getValue().equals(record.getStatus())){
					return -4;
				}
				record.setStatus(status);
				record.setApproveTime(DateUtils.parseDate(req.getApprove_time()));
				record.setApproverId(req.getApprover_id());
				record.setApproveRemark(req.getApprove_remark());
				record.setUpdateDate(new Date());
				taskCancelMapper.updateByPrimaryKeySelective(record);
				executionStatus= ExecutionStatusEnum.Status_5.getValue();
				if(flag){
					extWdtTTaskItemDao.upTaskStatus(task.getId(),executionStatus);
					extWdtTTaskItemDao.upTaskItemStatus(task.getId(),executionStatus);
				}
			}else{
				return -2;
			}
			
			return 1;
	}
	@Transactional(readOnly = false)
	public int delete(TaskStatusReq req) {
		return taskStatusMapper.deleteTask(req.getTaskId());

	}
	@Transactional(readOnly = false)
	public int refuse(TaskStatusReq req) {
			WdtTTask task = extWdtTTaskItemDao.getTask(req.getTaskId());
			if(task==null){
				return -1;
			}
			taskStatusMapper.upTaskStatus(req.getTaskId(),ExecutionStatusEnum.Status_2.getValue());
			return 1;
	}
	
	@Transactional(readOnly = false)
	public int receive(TaskStatusReq req) {
			WdtTTask task = extWdtTTaskItemDao.getTask(req.getTaskId());
			if(task==null){
				return -1;
			}
			Calendar c = Calendar.getInstance();
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			if(task.getEndDate().getTime()<c.getTime().getTime()){
				taskStatusMapper.upTaskStatus(req.getTaskId(),ExecutionStatusEnum.Status_6.getValue());
			}else{
				taskStatusMapper.upTaskStatus(req.getTaskId(),ExecutionStatusEnum.Status_3.getValue());
			}
			return 1;
	}
	@Transactional(readOnly = false)
	public int handover(TaskStatusReq req) {
			WdtTTask task = extWdtTTaskItemDao.getTask(req.getTaskId());
			if(task==null){
				return -1;
			}
			//将负责人添加到参与者
			WdtTTaskPerson wdtTTaskPerson = new WdtTTaskPerson();
			User u=userService.getUser(req.getUserId());
			wdtTTaskPerson.setUser(u);
			wdtTTaskPerson.setPersonType(PersonTypeEnum.Status_2.getValue());
			wdtTTaskPerson.setType(TaskTypeEnum.Status_0.getValue());
			wdtTTaskPerson.setTaskId(req.getTaskId());
			wdtTTaskPersonService.save(wdtTTaskPerson);
			
			//主任务有上级任务的话继续插入
			WdtTTask parentTask=taskStatusMapper.getPTask(task.getId());
			if(parentTask!=null){
				wdtTTaskPerson.setId(IdGen.uuid());
				wdtTTaskPerson.setPersonType(PersonTypeEnum.Status_3.getValue());
				wdtTTaskPerson.setTaskId(parentTask.getId());
				wdtTTaskPersonService.save(wdtTTaskPerson);
			}

			//移交
			taskStatusMapper.handover(req.getTaskId(),req.getUserId());
			// 保存移交记录
			WdtTTaskHandover wdtTTaskHandover = new WdtTTaskHandover();
			wdtTTaskHandover.setContent(req.getContent());
			wdtTTaskHandover.setUserId(req.getUserId());
			wdtTTaskHandover.setTaskId(req.getTaskId());
			wdtTTaskHandover.setType(TaskTypeEnum.Status_0.getValue());
			wdtTTaskHandover.preInsert();
			wdtTTaskHandover.setCreateBy(task.getOwner());
			wdtTTaskHandoverDao.insert(wdtTTaskHandover);
			//插入消息
			Message message =taskStatusMapper.getMessage(task.getId());
			if(message==null) {
                message = new Message();
                message.setType(TaskTypeEnum.Status_0.getValue());
                message.setTaskId(task.getId());
                message.setReciverId(req.getUserId());
                message.setMessageType("4");//新任务提醒
                message.setIsFromSystem("1");//枚举维护：0：自动催办 1：人工催办
                message.setId(IdGen.uuid());
                message.setSourceId(task.getId());
                message.setSenderId(UserUtils.getUser().getId());
                message.setCreateDate(new Date());
                message.setCreateBy(message.getSenderId());
                message.setUpdateBy(message.getSenderId());
                message.setUpdateDate(message.getCreateDate());
                message.setIsRead("0");
                message.setDelFlag("0");
                messageDao.insert(message);
                //推送消息
				Set<String> set=new HashSet<>();
				set.add(message.getReciverId());
				messageService.sendMessage(set);
            }else{
            	message.setSenderId(UserUtils.getUser().getId());
                message.setReciverId(req.getUserId());
                message.setUpdateBy(message.getSenderId());
                message.setUpdateDate(new Date());
                message.setIsRead("0");
                messageDao.update(message);
                Set<String> set=new HashSet<>();
				set.add(message.getReciverId());
				messageService.sendMessage(set);
            }
			//发邮件 给主责人
			String title =task.getName()+"-"+"待接收";
			sendEMailService.sendEmailForSingle(u.getEmail(), u.getName(),title ,
					"<html><head><title>"+title+"</title></head><body>尊敬的"+u.getName()+"，您有一个节点需接受，请尽快处理</body></html>");
			return 1;
	}
	@Transactional(readOnly = false)
	public int continu(TaskStatusReq req) {
			WdtTTask task = extWdtTTaskItemDao.getTask(req.getTaskId());
			if(task==null){
				return -1;
			}
			Calendar c = Calendar.getInstance();
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			String executionStatus = ExecutionStatusEnum.Status_3.getValue();
			if(task.getEndDate().getTime()<c.getTime().getTime()){
				executionStatus = ExecutionStatusEnum.Status_6.getValue();
			}
			if(task.getExecutionStatus().equals(ExecutionStatusEnum.Status_4.getValue())){
				extWdtTTaskItemDao.upTaskStatus(task.getId(),executionStatus);
				extWdtTTaskItemDao.upTaskItemStatus(task.getId(),executionStatus);
			}
			return 1;
	}
	
	public List<TaskOa> list(String taskId) {
		return taskStatusMapper.list(taskId);
	}
	
	@Transactional(readOnly = false)
	public int complete(WdtTTask wdtTTask) {
			
			//更新 任务		
			wdtTTask = wdtTTaskDao.get(wdtTTask);
			if(wdtTTask == null){
				return -1;
			}
			//更新 子节点
			extWdtTTaskItemDao.upTaskItemStatus(wdtTTask.getId(),ExecutionStatusEnum.Status_7.getValue());
			wdtTTask.setExecutionStatus(ExecutionStatusEnum.Status_7.getValue());
			wdtTTask.setProgress("100");
			wdtTTask.preUpdate();		
			wdtTTaskDao.update(wdtTTask);

		return 1;

	}

	@Transactional(readOnly = false)
	public int recall(TaskStatusReq req) {
			WdtTTask task = extWdtTTaskItemDao.getTask(req.getTaskId());
			if(task==null){
				return -1;
			}
			//获取最新移交记录
			WdtTTaskHandover wdtTTaskHandover = wdtTTaskHandoverDao.getNewHandOverByTaskId(req.getTaskId(), "0");
			if(wdtTTaskHandover != null ){
				//有移交操作，状态改为进行时，并归还给移交人
				Calendar c = Calendar.getInstance();
				c.set(Calendar.HOUR_OF_DAY, 0);
				c.set(Calendar.MINUTE, 0);
				c.set(Calendar.SECOND, 0);
				c.set(Calendar.MILLISECOND, 0);
				String executionStatus = ExecutionStatusEnum.Status_3.getValue();
				if(task.getEndDate().getTime()<c.getTime().getTime()){
					executionStatus = ExecutionStatusEnum.Status_6.getValue();
				}
				taskStatusMapper.recalling(req.getTaskId(),wdtTTaskHandover.getCreateBy().getId(),executionStatus);
				//移交记录删除
				wdtTTaskHandoverDao.deleteBy(wdtTTaskHandover.getId());
			}else{
				//状态改为草稿中
				taskStatusMapper.upTaskStatus(req.getTaskId(),ExecutionStatusEnum.Status_0.getValue());
			}
			Message message =taskStatusMapper.getMessage(task.getId());
			if(message!=null){
				messageDao.deleteById(message.getId());
			}
			
			return 1;

	}
	@Transactional(readOnly = false)
	public int turnBack(TaskStatusReq req) {
			WdtTTask task = extWdtTTaskItemDao.getTask(req.getTaskId());
			if(task==null){
				return -1;
			}
			if(task.getExecutionStatus().equals(ExecutionStatusEnum.Status_7.getValue())
					||task.getExecutionStatus().equals(ExecutionStatusEnum.Status_9.getValue())){
				Calendar c = Calendar.getInstance();
				c.set(Calendar.HOUR_OF_DAY, 0);
				c.set(Calendar.MINUTE, 0);
				c.set(Calendar.SECOND, 0);
				c.set(Calendar.MILLISECOND, 0);
				if(task.getEndDate().getTime()<c.getTime().getTime()){
					taskStatusMapper.upTaskStatus(req.getTaskId(),ExecutionStatusEnum.Status_6.getValue());
				}else{
					taskStatusMapper.upTaskStatus(req.getTaskId(),ExecutionStatusEnum.Status_3.getValue());
				}
			}else{
				return -2;
			}
			
			
			return 1;

	}
	
}