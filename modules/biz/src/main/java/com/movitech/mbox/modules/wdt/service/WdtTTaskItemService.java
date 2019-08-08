/**
 * 
 */
package com.movitech.mbox.modules.wdt.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movitech.mbox.common.config.ExecutionStatusEnum;
import com.movitech.mbox.common.config.PersonTypeEnum;
import com.movitech.mbox.common.config.TaskTypeEnum;
import com.movitech.mbox.common.config.YesNoEnum;
import com.movitech.mbox.common.persistence.Page;
import com.movitech.mbox.common.service.CrudService;
import com.movitech.mbox.common.utils.ArrayUtils;
import com.movitech.mbox.common.utils.IdGen;
import com.movitech.mbox.modules.sys.dao.DictDao;
import com.movitech.mbox.modules.sys.entity.User;
import com.movitech.mbox.modules.sys.service.UserService;
import com.movitech.mbox.modules.sys.utils.UserUtils;
import com.movitech.mbox.modules.wdt.dao.MessageDao;
import com.movitech.mbox.modules.wdt.dao.WdtTTaskCommentsDao;
import com.movitech.mbox.modules.wdt.dao.WdtTTaskItemDao;
import com.movitech.mbox.modules.wdt.dao.WdtTTaskPersonDao;
import com.movitech.mbox.modules.wdt.dao.ext.ExtWdtTTaskItemDao;
import com.movitech.mbox.modules.wdt.dao.ext.ExtWdtTTaskPersonDao;
import com.movitech.mbox.modules.wdt.entity.Message;
import com.movitech.mbox.modules.wdt.entity.WdtTTask;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskComments;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskHandover;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskItem;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskPerson;
import com.movitech.mbox.modules.wdt.entity.ext.ExtWdtTTaskItem;
import com.movitech.mbox.modules.wdt.operateLog.service.OperateLogService;
import com.movitech.mbox.modules.wdt.req.TaskItemReq;
import com.movitech.mbox.modules.wdt.taskStatus.dao.ext.TaskStatusMapper;
import com.movitech.mbox.modules.wdt.taskStatus.req.TaskMailReq;

/**
 * 任务节点Service
 * 
 * @author Aibal.He
 * @version 2017-08-07
 */
@Service
@Transactional(readOnly = true)
public class WdtTTaskItemService extends
		CrudService<WdtTTaskItemDao, WdtTTaskItem> {

	@Autowired
	private ExtWdtTTaskItemDao extWdtTTaskItemDao;
	
	@Autowired
	private ExtWdtTTaskPersonDao extWdtTTaskPersonDao;

	@Autowired
	private WdtTTaskHandoverService wdtTTaskHandoverService;

	@Autowired
	private WdtTTaskPersonService wdtTTaskPersonService;

	@Autowired
	private WdtTTaskService wdtTTaskService;

	@Autowired
	private UserService userService;

	@Autowired
	private WdtTTaskPersonDao wdtTTaskPersonDao;
	
	@Autowired
	private WdtTTaskItemDao wdtTTaskItemDao;
	
	@Autowired
	private OperateLogService operateLogService;
	
	@Autowired
	private TaskStatusMapper taskStatusMapper;
	
	@Autowired
	private MessageDao messageDao;
	
	@Autowired
    MessageService messageService;
	
	@Autowired
	private WdtTTaskCommentsDao wdtTTaskCommentsDao;
	
	@Autowired
   	private SendEMailService sendEMailService;
	@Autowired
	private DictDao dictDao;
	public WdtTTaskItem get(String id) {
		return super.get(id);
	}

	public List<WdtTTaskItem> findList(WdtTTaskItem wdtTTaskItem) {
		return super.findList(wdtTTaskItem);
	}

	public Page findPage(Page page, WdtTTaskItem wdtTTaskItem) {
		wdtTTaskItem.setRoleIds(dictDao.findValueListByType("bus_admin"));
		wdtTTaskItem.setPage(page);
		List<ExtWdtTTaskItem>list =extWdtTTaskItemDao.findList(wdtTTaskItem);
		if(list!=null&&list.size()>0){
			int a=0,b=0;
			for (ExtWdtTTaskItem one:list) {
				if(ExecutionStatusEnum.Status_3.getValue().equals(one.getExecutionStatus())||ExecutionStatusEnum.Status_6.getValue().equals(one.getExecutionStatus())){
					a=extWdtTTaskItemDao.getItemMessage(one.getId(),1);
					b=extWdtTTaskItemDao.getItemMessage(one.getId(),2);
					if(a>0&&b>0){
						one.setItemName(one.getItemName()+"(催办，待汇报)");
					}else if(a>0&&b==0){
						one.setItemName(one.getItemName()+"(催办)");
					}else if(b>0&&a==0){
						one.setItemName(one.getItemName()+"(待汇报)");
					}
				}
			}
		}
		page.setList(list);
		return page;
	}

	@Transactional(readOnly = false)
	public void save(WdtTTaskItem wdtTTaskItem) {
		super.save(wdtTTaskItem);
	}

	/**
	 * 节点保存
	 * 
	 * @param wdtTTaskItem
	 */
	@Transactional(readOnly = false)
	public int saveExt(ExtWdtTTaskItem wdtTTaskItem) {
			WdtTTask task = extWdtTTaskItemDao.getTask(wdtTTaskItem.getTaskId());
			if (task != null) {
				// 插入节点
				User user=userService.getUser(wdtTTaskItem.getOwnerId());
				if(user == null){
					return -3;
				}
				wdtTTaskItem.setIsApplyExtension(YesNoEnum.Status_1.getValue());
				wdtTTaskItem.setExecutionStatus(ExecutionStatusEnum.Status_3
						.getValue());
				wdtTTaskItem.setFqcy(task.getFqcy());
				wdtTTaskItem.setProgress("0");
				wdtTTaskItem.setTaskId(task.getId());
				wdtTTaskItem.preInsert();
				wdtTTaskItem.setOperatorId(wdtTTaskItem.getCreateBy().getId());
				dao.insert(wdtTTaskItem);
				//更新任务是否有节点
				if(YesNoEnum.Status_1.getValue().equals(task.getIsHaveSplit())){
					extWdtTTaskItemDao.upTaskSplit(task.getId());
				}
				//插入参与人
				WdtTTaskPerson wdtTTaskPerson = new WdtTTaskPerson();
				wdtTTaskPerson.setType(TaskTypeEnum.Status_1.getValue());
				wdtTTaskPerson.setPersonType(PersonTypeEnum.Status_2.getValue());
				wdtTTaskPerson.setTaskId(task.getId());
				wdtTTaskPerson.setTaskItemId(wdtTTaskItem.getId());
				wdtTTaskPerson.preInsert();
				
				wdtTTaskPerson.setUser(user);
				wdtTTaskPerson.setId(IdGen.uuid());
				wdtTTaskPersonDao.insert(wdtTTaskPerson);
				//再插一次主任务
				wdtTTaskPerson.setId(IdGen.uuid());
				wdtTTaskPerson.setType(TaskTypeEnum.Status_0.getValue());
				wdtTTaskPerson.setTaskItemId(null);
				wdtTTaskPersonDao.insert(wdtTTaskPerson);
				//主任务有上级任务的话继续插入
				WdtTTask parentTask=taskStatusMapper.getPTask(task.getId());
				if(parentTask!=null){
					wdtTTaskPerson.setId(IdGen.uuid());
					wdtTTaskPerson.setPersonType(PersonTypeEnum.Status_3.getValue());
					wdtTTaskPerson.setTaskId(parentTask.getId());
					wdtTTaskPersonDao.insert(wdtTTaskPerson);
				}else{
					List<WdtTTask> sontasks=taskStatusMapper.getSTasks(task.getId());
					if(sontasks!=null&&sontasks.size()>0){
						for(WdtTTask one:sontasks){
							wdtTTaskPerson.setId(IdGen.uuid());
							wdtTTaskPerson.setPersonType(PersonTypeEnum.Status_3.getValue());
							wdtTTaskPerson.setTaskId(one.getId());
							wdtTTaskPersonDao.insert(wdtTTaskPerson);
						}
					}
				}
				// 插入阅知人
				wdtTTaskPerson.setPersonType(PersonTypeEnum.Status_0.getValue());
				if(wdtTTaskItem.getReaderIds()!=null&&wdtTTaskItem.getReaderIds().size()>0){
					for (String userId:wdtTTaskItem.getReaderIds()) {
						User u=userService.getUser(userId);
						wdtTTaskPerson.setUser(u);
						if(null!=u.getOffice()){
							wdtTTaskPerson.setOrgId(u.getOffice().getId());
						}
						wdtTTaskPerson.setId(IdGen.uuid());
						wdtTTaskPersonDao.insert(wdtTTaskPerson);
					}
				}
				//发邮件 给主责人
				String title =task.getName()+"-"+wdtTTaskItem.getItemName()+"待接收";
				sendEMailService.sendEmailForSingle(user.getEmail(), user.getName(),title ,
						"<html><head><title>"+title+"</title></head><body>尊敬的"+user.getName()+"，您有一个节点需接受，请尽快处理</body></html>");
				return 1;
			}
			return -1;
	}

	/**
	 * 节点移交
	 * 
	 * @param req
	 */
	@Transactional(readOnly = false)
	public int handover(TaskItemReq req) {
			WdtTTaskItem item = dao.get(req.getTaskItemId());
			if (item != null) {
				//将负责人添加到参与者
				WdtTTaskPerson wdtTTaskPerson = new WdtTTaskPerson();
				User u=userService.getUser(req.getUserId());
				wdtTTaskPerson.setUser(u);
				wdtTTaskPerson.setPersonType(PersonTypeEnum.Status_2.getValue());
				wdtTTaskPerson.setType(TaskTypeEnum.Status_1.getValue());
				wdtTTaskPerson.setTaskId(item.getTaskId());
				wdtTTaskPerson.setTaskItemId(item.getId());
				wdtTTaskPersonService.save(wdtTTaskPerson);
				//再插一次主任务
				wdtTTaskPerson.setId(IdGen.uuid());
				wdtTTaskPerson.setType(TaskTypeEnum.Status_0.getValue());
				wdtTTaskPerson.setTaskItemId(null);
				wdtTTaskPersonDao.insert(wdtTTaskPerson);
				//主任务有上级任务的话继续插入
				WdtTTask parentTask=taskStatusMapper.getPTask(item.getTaskId());
				if(parentTask!=null){
					wdtTTaskPerson.setId(IdGen.uuid());
					wdtTTaskPerson.setPersonType(PersonTypeEnum.Status_3.getValue());
					wdtTTaskPerson.setTaskId(parentTask.getId());
					wdtTTaskPersonDao.insert(wdtTTaskPerson);
				}else{
					List<WdtTTask> sontasks=taskStatusMapper.getSTasks(item.getTaskId());
					if(sontasks!=null&&sontasks.size()>0){
						for(WdtTTask one:sontasks){
							wdtTTaskPerson.setId(IdGen.uuid());
							wdtTTaskPerson.setPersonType(PersonTypeEnum.Status_3.getValue());
							wdtTTaskPerson.setTaskId(one.getId());
							wdtTTaskPersonDao.insert(wdtTTaskPerson);
						}
					}
				}
				//移交
				item.setOwnerId(req.getUserId());
				item.preUpdate();
				dao.update(item);
				// 保存移交记录
				WdtTTaskHandover wdtTTaskHandover = new WdtTTaskHandover();
				wdtTTaskHandover.setContent(req.getContent());
				wdtTTaskHandover.setUserId(req.getUserId());
				wdtTTaskHandover.setTaskId(item.getTaskId());
				wdtTTaskHandover.setTaskItemId(item.getId());
				wdtTTaskHandover.setType(TaskTypeEnum.Status_1.getValue());
				wdtTTaskHandoverService.save(wdtTTaskHandover);
				//移交时需要把代办催办都移交
				Message message = new Message();
				message.setTaskId(item.getTaskId());
				message.setTaskItemId(item.getId());
				message.setReciverId(req.getUserId());
				messageDao.updateUrgeMessage(message);
				
				
				//发邮件 给主责人
				String title =item.getTaskName()+"-"+ item.getItemName()+"待接收";
				sendEMailService.sendEmailForSingle(u.getEmail(), u.getName(),title ,
						"<html><head><title>"+title+"</title></head><body>尊敬的"+u.getName()+"，您有一个节点需接受，请尽快处理</body></html>");
				return 1;
			}
			return -1;
	}

	public ExtWdtTTaskItem getInfo(String id) {
		ExtWdtTTaskItem item=extWdtTTaskItemDao.getInfo(id);
		item.setReaderUsers(extWdtTTaskPersonDao.getReaderUsers(id));
		return item;
	}

	/**
	 * 节点更新
	 * 
	 * @param wdtTTaskItem
	 */
	@Transactional(readOnly = false)
	public int update(ExtWdtTTaskItem wdtTTaskItem) {
			// 更新节点
			WdtTTaskItem item = dao.get(wdtTTaskItem.getId());
			if(item!=null){
				item.setItemName(wdtTTaskItem.getItemName());
				item.setStartDate(wdtTTaskItem.getStartDate());
				item.setRequiredCompletionTime(wdtTTaskItem.getRequiredCompletionTime());
				item.setOwnerId(wdtTTaskItem.getOwnerId());
				item.setItemContent(wdtTTaskItem.getItemContent());
				item.preUpdate();
				dao.update(item);
				// 更新阅知人
				List<WdtTTaskPerson> readers=extWdtTTaskPersonDao.getReaders(wdtTTaskItem.getId());
				List<String> olds = new ArrayList<String>();
				for (WdtTTaskPerson reader : readers) {
					olds.add(reader.getUser().getId());
				}
				//传入数据库没有的阅知人，增加
				List<String> list1=ArrayUtils.compare(olds, wdtTTaskItem.getReaderIds());
				if(list1.size()>0){
					WdtTTaskPerson wdtTTaskPerson = new WdtTTaskPerson();
					wdtTTaskPerson.setType(TaskTypeEnum.Status_1.getValue());
					wdtTTaskPerson.setPersonType(PersonTypeEnum.Status_0.getValue());
					wdtTTaskPerson.setTaskId(item.getTaskId());
					wdtTTaskPerson.setTaskItemId(item.getId());
					wdtTTaskPerson.preInsert();
					for (String userId : list1) {
						//add
						User u=userService.getUser(userId);
						wdtTTaskPerson.setUser(u);
						wdtTTaskPerson.setOrgId(u.getOffice().getId());
						wdtTTaskPerson.setId(IdGen.uuid());
						wdtTTaskPersonDao.insert(wdtTTaskPerson);
					}
				}
				//数据库没有包含传入的阅知人，删除
				List<String> list2=ArrayUtils.compare(wdtTTaskItem.getReaderIds(),olds);
				if(list2.size()>0){
					List<String> deletes = new ArrayList<String>();
					for (WdtTTaskPerson reader : readers) {
						if(list2.contains(reader.getUser().getId())){
							deletes.add(reader.getId());
						}
					}
					extWdtTTaskPersonDao.deletePerson(deletes);
				}
				
				return 1;
			}
			return -2;
	}

	/**
	 * 节点完成
	 * 
	 * @param req
	 */
	@Transactional(readOnly = false)
	public int finish(TaskItemReq req) {
			WdtTTaskItem item = dao.get(req.getTaskItemId());
			if (item != null) {
				if(!(ExecutionStatusEnum.Status_3.getValue().equals(item.getExecutionStatus())||ExecutionStatusEnum.Status_6.getValue().equals(item.getExecutionStatus()))){
					return -2;
				}
				if(!"100".equals(item.getProgress())){
					return  -3;
				}
				if(ExecutionStatusEnum.Status_6.getValue().equals(item.getExecutionStatus())){
					item.setExecutionStatus(ExecutionStatusEnum.Status_9.getValue());
				}else {
					item.setExecutionStatus(ExecutionStatusEnum.Status_7.getValue());
				}

				item.preUpdate();
				item.setInfactCompletionTime(new Date());
				dao.update(item);
				//节点进度消息删除
				messageDao.deleteByTypeAndItemId("2", req.getTaskItemId());
				return 1;
			}
			return -1;
	}

	public WdtTTask getTask(String taskId) {
		return extWdtTTaskItemDao.getTask(taskId);
	}

	public  Boolean isOwner(String taskId, String userId) {
		return extWdtTTaskItemDao.isOwner(taskId,userId)==1;
	}

	public List<WdtTTaskItem> findUnCompletedTaskItems(WdtTTaskItem searchTaskItem) {
		return wdtTTaskItemDao.findUnCompletedTaskItems(searchTaskItem);
	}

	@Transactional(readOnly = false)
	public int updateTaskItem(WdtTTaskItem taskItem) {
		return extWdtTTaskItemDao.updateTaskItem(taskItem);
	}
	
	public int getTaskItemByTaskId(String taskId) {
		return extWdtTTaskItemDao.getItemCountByTaskId(taskId);
	}
	/**
	 * 更新过期节点
	 * @param taskId
	 */
	@Transactional(readOnly = false)
	public int upOvertimeTaskItem(String taskId) {
		return extWdtTTaskItemDao.upOvertimeTaskItem(taskId);
	}

	/**
	 * 更新延期的节点到进行中
	 * @param taskId
	 */
	@Transactional(readOnly = false)
	public int upInprogressTaskItem(String taskId) {
		return extWdtTTaskItemDao.upInprogressTaskItem(taskId);
	}
	public List<WdtTTaskItem> findByConditions(String taskId,String ownerId,
								   List<String> list,Date now){
		return dao.findByConditions(taskId,ownerId,list,now);
	}
	@Transactional(readOnly = false)
	public void addUrge(String taskId,String items,String content){
		String userId=UserUtils.getUser().getId();
		String[] ids = items.split(",");
		List<String> list = new ArrayList<>();
		for (String s : ids) list.add(s);
		List<WdtTTaskItem> itemlist = findByConditions(taskId, null, list,new Date());
		List<Message> messages = new ArrayList<>();
		for (WdtTTaskItem item : itemlist) {
			Message message = new Message();
			message.setId(IdGen.uuid());
			message.setType("1");
			message.setTaskId(taskId);
			message.setTaskItemId(item.getId());
			message.setReciverId(item.getOwnerId());
			message.setSenderId(userId);
			message.setMessageType("1");
			message.setIsRead("0");
			message.setIsFromSystem("0");
			message.setCreateDate(new Date());
			message.setUpdateDate(new Date());
			message.setIsFromSystem("1");
			message.setDelFlag("0");
			messages.add(message);
			//加入进度汇报
			WdtTTaskComments wdtTTaskComments = new WdtTTaskComments();
			wdtTTaskComments.preInsert();
			wdtTTaskComments.setType("4");
			wdtTTaskComments.setTaskId(taskId);
			wdtTTaskComments.setTaskItemId(item.getId());
			wdtTTaskComments.setContent(content);
			wdtTTaskComments.setUser(wdtTTaskComments.getCreateBy());
			wdtTTaskCommentsDao.insert(wdtTTaskComments);
		}
		messageService.batchInsert(messages);
		
		List<TaskMailReq> reqs= new LinkedList<TaskMailReq>();
		for (WdtTTaskItem item : itemlist) {
			TaskMailReq e = new TaskMailReq();
			User u=UserUtils.get(item.getOwnerId());
			e.setTomail(u.getEmail());
			e.setToname(u.getName());
			WdtTTask task =getTask(item.getTaskId());
			e.setTitle(task.getName()+"-"+item.getItemName()+"的催办");
			e.setContent("<html><head><title>"+e.getTitle()+"</title></head><body>尊敬的"+u.getName()+"，您有一个催办需尽快处理</body></html>");
			reqs.add(e);
			

		}
		//发送邮件提醒 异步
		sendEMailService.sendEmailForMult(reqs);
		//业务推送消息
		Set<String> set=new HashSet<>();
		if(messages!=null&&messages.size()>0){
			for(Message message:messages){
				set.add(message.getReciverId());
			}
		}
		messageService.sendMessage(set);
	
	}
	public Boolean isParent(String taskId) {
		return taskStatusMapper.getPTask(taskId)!=null;
	}
}