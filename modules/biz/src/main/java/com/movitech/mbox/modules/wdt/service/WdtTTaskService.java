/**
 * 
 */
package com.movitech.mbox.modules.wdt.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.movitech.mbox.common.config.ExecutionStatusEnum;
import com.movitech.mbox.common.config.Global;
import com.movitech.mbox.common.config.PersonTypeEnum;
import com.movitech.mbox.common.config.RelationTaskEnum;
import com.movitech.mbox.common.config.TaskTypeEnum;
import com.movitech.mbox.common.config.YesNoEnum;
import com.movitech.mbox.common.persistence.Page;
import com.movitech.mbox.common.service.CrudService;
import com.movitech.mbox.common.utils.DateUtils;
import com.movitech.mbox.common.utils.IdGen;
import com.movitech.mbox.modules.sys.dao.DictDao;
import com.movitech.mbox.modules.sys.entity.User;
import com.movitech.mbox.modules.sys.utils.UserUtils;
import com.movitech.mbox.modules.wdt.dao.WarningDao;
import com.movitech.mbox.modules.wdt.dao.WdtTFileInfoDao;
import com.movitech.mbox.modules.wdt.dao.WdtTTaskCommentsDao;
import com.movitech.mbox.modules.wdt.dao.WdtTTaskContentDao;
import com.movitech.mbox.modules.wdt.dao.WdtTTaskDao;
import com.movitech.mbox.modules.wdt.dao.WdtTTaskHandoverDao;
import com.movitech.mbox.modules.wdt.dao.WdtTTaskItemDao;
import com.movitech.mbox.modules.wdt.dao.WdtTTaskPersonDao;
import com.movitech.mbox.modules.wdt.dao.WdtTTaskProjectDao;
import com.movitech.mbox.modules.wdt.dao.WdtTTaskRelationDao;
import com.movitech.mbox.modules.wdt.dao.WdtTTaskReponseDao;
import com.movitech.mbox.modules.wdt.dao.WdtTTaskTempleteDao;
import com.movitech.mbox.modules.wdt.dao.ext.ExtWdtTTaskItemDao;
import com.movitech.mbox.modules.wdt.entity.Message;
import com.movitech.mbox.modules.wdt.entity.Warning;
import com.movitech.mbox.modules.wdt.entity.WdtTFileInfo;
import com.movitech.mbox.modules.wdt.entity.WdtTTask;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskContent;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskHandover;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskItem;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskPerson;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskProject;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskRelation;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskTemplete;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskTheme;
import com.movitech.mbox.modules.wdt.entity.WdtTaskH5Vo;
import com.movitech.mbox.modules.wdt.entity.WdtTaskParam;
import com.movitech.mbox.modules.wdt.entity.WdtTaskVo;
import com.movitech.mbox.modules.wdt.entity.ext.ExtWdtTTaskItem;
import com.movitech.mbox.modules.wdt.entity.ext.TaskH5;
import com.movitech.mbox.modules.wdt.taskProject.entity.TaskProject;
import com.movitech.mbox.modules.wdt.taskProject.service.TaskProjectService;
import com.movitech.mbox.modules.wdt.taskStatus.dao.ExtTaskExtensionDao;
import com.movitech.mbox.modules.wdt.taskStatus.dao.TaskBanjieMapper;
import com.movitech.mbox.modules.wdt.taskStatus.dao.TaskCancelMapper;
import com.movitech.mbox.modules.wdt.taskStatus.dao.TaskExtensionMapper;
import com.movitech.mbox.modules.wdt.taskStatus.dao.TaskPauseMapper;
import com.movitech.mbox.modules.wdt.taskStatus.entity.TaskBanjie;
import com.movitech.mbox.modules.wdt.taskStatus.entity.TaskCancel;
import com.movitech.mbox.modules.wdt.taskStatus.entity.TaskExtension;
import com.movitech.mbox.modules.wdt.taskStatus.entity.TaskPause;
import com.movitech.mbox.modules.wdt.taskStatus.entity.WdtTTaskExtension;
import com.movitech.mbox.modules.wdt.utils.TaskUtils;

/**
 * 任务Service
 * @author Aibal.He
 * @version 2017-08-07
 */
@Service
@Transactional(readOnly = true)
public class WdtTTaskService extends CrudService<WdtTTaskDao, WdtTTask> {
	@Autowired
	private ExtWdtTTaskItemDao extWdtTTaskItemDao;
	
	@Autowired
	private WdtTTaskPersonDao wdtTTaskPersonDao;

	@Autowired
	private WdtTTaskProjectDao wdtTTaskProjectDao;

	@Autowired
	private WdtTFileInfoDao wdtTFileInfoDao;

	@Autowired
	private WdtTTaskItemDao wdtTTaskItemDao;
	
	@Autowired
	private WdtTTaskItemService wdtTTaskItemService;
    
    @Autowired
	private WdtTTaskTempleteDao wdtTTaskTempleteDao;
    
    @Autowired
	private WdtTTaskContentDao wdtTTaskContentDao;
    
    @Autowired
	private WdtTTaskRelationDao wdtTTaskRelationDao;
 
    @Autowired
    private WarningDao warningDao;
    
    @Autowired
    private ExtTaskExtensionDao extTaskExtensionDao;
    
    @Autowired
    private WdtTTaskCommentsDao wdtTTaskCommentsDao;
    
    @Autowired
    private TaskProjectService taskProjectService;
    @Autowired
	MessageService messageService;
    
    @Autowired
    private WdtTTaskHandoverDao wdtTTaskHandoverDao;
    @Autowired
	private WdtTTaskReponseDao reponseDao;
    
    @Autowired
	private DictDao dictDao;
    
    @Autowired
	private TaskBanjieMapper taskBanjieMapper;
    @Autowired
   	private TaskCancelMapper taskCancelMapper;
    @Autowired
   	private TaskExtensionMapper taskExtensionMapper;
    @Autowired
   	private TaskPauseMapper taskPauseMapper;
    
    @Autowired
   	private SendEMailService sendEMailService;
    
	public WdtTTask get(String id) {


		return super.get(id);
	}

	public WdtTTask getLite(WdtTTask task){
		WdtTTask wdtTTask = super.get(task);
		if(wdtTTask!=null){
			if(com.movitech.mbox.common.utils.StringUtils.isNotEmpty(wdtTTask.getIsRelationTask())
					&& wdtTTask.getIsRelationTask().equals(RelationTaskEnum.Status_0.getValue())){
				WdtTTask parent = new WdtTTask();
				parent.setId(wdtTTask.getTaskId());
				parent = dao.getByChildId(task.getId());
				wdtTTask.setParent(parent);
			}
		}
		return wdtTTask;
	}
	public WdtTTask getShowFront(WdtTTask task, String userId) {
		WdtTTask wdtTTask = new WdtTTask();
		if(com.movitech.mbox.common.utils.StringUtils.isEmpty(task.getId())){
			wdtTTask.setTaskCreateUser(UserUtils.getUser());
        	if(com.movitech.mbox.common.utils.StringUtils.isNotEmpty(task.getThemeId())){
        		wdtTTask.setIsTemplete(YesNoEnum.Status_0.getValue());
        		wdtTTask.setThemeId(task.getThemeId());
        	}
		}else{
			wdtTTask.setId(task.getId());
			wdtTTask.setUserId(userId);
			wdtTTask = super.get(wdtTTask);
			WdtTFileInfo searchFileInfo = new WdtTFileInfo();
			searchFileInfo.setBizTableName("wdt_t_task");
			searchFileInfo.setBizTablePkName("id");
			searchFileInfo.setBizTablePkValue(task.getId());
			List<WdtTFileInfo> fileList = wdtTFileInfoDao.findAllFileListForTask(searchFileInfo);
			//fileIds
			if (fileList != null && fileList.size() > 0) {
				List<String> fileIds = new LinkedList<String>();
				for (WdtTFileInfo wdtTFileInfo : fileList) {
					fileIds.add(wdtTFileInfo.getId());
				}
				wdtTTask.setFileIds(fileIds);
				wdtTTask.setFileList(fileList);
			}
			//设置阅知人、管理层、参与人
			WdtTTaskPerson searchWdtTTaskPerson = new WdtTTaskPerson();
			searchWdtTTaskPerson.setTaskId(task.getId());
			searchWdtTTaskPerson.setType("0");
			List<WdtTTaskPerson> taskPersonList = wdtTTaskPersonDao.findList(searchWdtTTaskPerson);

			List<String> subscribers = new ArrayList<String>();
			List<String> managers = new ArrayList<String>();
			List<String> participant = new ArrayList<String>();
			List<WdtTTaskPerson> subscriberPersons= new ArrayList<WdtTTaskPerson>();; //订阅者
		    List<WdtTTaskPerson> managerPersons= new ArrayList<WdtTTaskPerson>();; //管理者
		    List<WdtTTaskPerson> participantPersons= new ArrayList<WdtTTaskPerson>();; //参与者
			StringBuilder subscribersName = new StringBuilder();
			StringBuilder managersName = new StringBuilder();
			StringBuilder participantName = new StringBuilder();
			Map<String,String> participantMap =  new HashMap<String,String>();
			List<String> otherPersonIds = new ArrayList<String>();
			if (taskPersonList != null && taskPersonList.size() > 0) {
				for (WdtTTaskPerson p : taskPersonList) {
					if (PersonTypeEnum.Status_0.getValue().equals(p.getPersonType())) {//阅知人
						subscribers.add(p.getUser().getId());
						if (subscribersName.toString().isEmpty()) {
							subscribersName.append(p.getUser().getName());
						} else {
							subscribersName.append("," + p.getUser().getName());
						}
						subscriberPersons.add(p);
					} else if (PersonTypeEnum.Status_1.getValue().equals(p.getPersonType())) {//管理层
						managers.add(p.getUser().getId());
						if (managersName.toString().isEmpty()) {
							managersName.append(p.getUser().getName());
						} else {
							managersName.append("," + p.getUser().getName());
						}
						managerPersons.add(p);
					} else if (PersonTypeEnum.Status_2.getValue().equals(p.getPersonType())) {//参与者
						participantMap.put(p.getUser().getId(), p.getUser().getName());
						participantPersons.add(p);
					}
					 else if (PersonTypeEnum.Status_3.getValue().equals(p.getPersonType())) {//参与者
							otherPersonIds.add(p.getUser().getId());
						}

				}
				Iterator it = participantMap.keySet().iterator();  
			       while (it.hasNext()) {  
			           String key = it.next().toString();  
			           participant.add(key);  
			           participantName.append(","+com.movitech.mbox.common.utils.StringUtils.getString(participantMap.get(key)));  
			       }  
				wdtTTask.setSubscribers(subscribers);
				wdtTTask.setManagers(managers);
				wdtTTask.setParticipant(participant);
				wdtTTask.setSubscribersName(subscribersName.toString());
				wdtTTask.setManagersName(managersName.toString());
				wdtTTask.setParticipantName(participantName.toString().substring(1));
				wdtTTask.setManagerPersons(managerPersons);
				wdtTTask.setParticipantPersons(participantPersons);
				wdtTTask.setSubscriberPersons(subscriberPersons);
				wdtTTask.setOtherPersonIds(otherPersonIds);
				//获取主任务阅知人
				if(wdtTTask.getIsRelationTask().equals(RelationTaskEnum.Status_0.getValue())){
					WdtTTask parent = new WdtTTask();
					parent = dao.getByChildId(wdtTTask.getId());
					WdtTTaskPerson parentSubscriberPerson = new WdtTTaskPerson();
					parentSubscriberPerson.setTaskId(parent.getId());
					parentSubscriberPerson.setType("0");
					parentSubscriberPerson.setPersonType("0");
					List<WdtTTaskPerson> parentSubscriberPersonList = wdtTTaskPersonDao.findList(parentSubscriberPerson);
					if(parentSubscriberPersonList!= null &&parentSubscriberPersonList.size()>0){
						List<String> parentSubscribers = new ArrayList<String>();
						for (WdtTTaskPerson wdtTTaskPerson : parentSubscriberPersonList) {
							parentSubscribers.add(wdtTTaskPerson.getUser().getId());
						}
						wdtTTask.setParentSubscribers(parentSubscribers);
					}
						
				}
			}
			
			if(wdtTTask.getIsRelationTask().equals(RelationTaskEnum.Status_0.getValue())){
				WdtTTask parent = new WdtTTask();
				parent.setId(wdtTTask.getTaskId());
				parent = dao.getByChildId(wdtTTask.getId());
				wdtTTask.setParent(parent);
			}
			if(wdtTTask.getExecutionStatus().equals(ExecutionStatusEnum.Status_6.getValue())
					|| wdtTTask.getExecutionStatus().equals(ExecutionStatusEnum.Status_3.getValue())){
				//进行中修改时，查看是否有课进行延期操作
				WdtTTaskExtension wdtTTaskExtension= extTaskExtensionDao.getNewBytaskId(wdtTTask.getId(),"0");
				if(wdtTTaskExtension!=null && wdtTTaskExtension.getStatus().equals("1")){
					wdtTTask.setWdtTTaskExtension(wdtTTaskExtension);
				}
			}
			//已拒绝状态 如果有移交操作只有移交者有修改动能，，没有移交操作则是发起人有修改动能
			if(wdtTTask.getExecutionStatus().equals(ExecutionStatusEnum.Status_2.getValue())||wdtTTask.getExecutionStatus().equals(ExecutionStatusEnum.Status_1.getValue())){
				//获取最新移交记录
				WdtTTaskHandover wdtTTaskHandover = wdtTTaskHandoverDao.getNewHandOverByTaskId(wdtTTask.getId(), "0");
				if(wdtTTaskHandover != null ){
					wdtTTask.setHandOverUserId(wdtTTaskHandover.getCreateBy().getId());
				}
				
			}
			//加入标蓝色id
			wdtTTask.setBluePersonIds(wdtTTaskPersonDao.getBluePersonIds(wdtTTask.getId()));
			
			int showColor = 0;
			Warning warning = wdtTTask.getWarning();
			if(warning!=null && warning.getWarningStartDate()!=null 
					&& warning.getDays()!=null 
					&& (wdtTTask.getExecutionStatus().equals(ExecutionStatusEnum.Status_3.getValue())||wdtTTask.getExecutionStatus().equals(ExecutionStatusEnum.Status_6.getValue()))){
				
				Date now = getStartTime();
			
				if((now.getTime() -warning.getWarningStartDate().getTime())/86400000<0){
					showColor = 0;
				}else if((now.getTime() -warning.getWarningStartDate().getTime())/86400000<=warning.getDays()){
					showColor = 2;
				}else if ((now.getTime() -warning.getWarningStartDate().getTime())/86400000>warning.getDays()){
					showColor = 1;
				}
				
			}
			if(wdtTTask.getExecutionStatus().equals(ExecutionStatusEnum.Status_6.getValue())){
				showColor = 1;
			}
			wdtTTask.setShowColor(showColor);
		}
		//有选择分类主题时显示主题信息
		if(!StringUtils.isEmpty(wdtTTask.getThemeId()) && wdtTTask.getIsTemplete()!=null 
				&&wdtTTask.getIsTemplete().equals(YesNoEnum.Status_0.getValue())){
			wdtTTask.setWdtTTaskTheme(TaskUtils.getThemeById(wdtTTask.getThemeId()));
			if(wdtTTask.getWdtTTaskTheme().getIsRelateProject().equals("0")){
				appendProjects(wdtTTask);
			}
		}else{
			wdtTTask.setWdtTTaskTheme(new WdtTTaskTheme());
		}
		//加入 任务内容模板list
		wdtTTask.setWdtTTaskTempleteList(wdtTTaskTempleteDao.getTempletesAndContent(wdtTTask.getId()));
		wdtTTask.setItemOwnerIds(wdtTTaskItemDao.getOwnerIdsByTaskId(wdtTTask.getId()));
		return wdtTTask;
	}

	private void appendProjects(WdtTTask wdtTTask){
		List<TaskProject> projectList = taskProjectService.list(wdtTTask.getId());
		if(projectList!=null && projectList.size()>0){
			StringBuilder projectIds = new StringBuilder();
			StringBuilder projectNames = new StringBuilder();
			for (TaskProject taskProject : projectList) {
				if (projectIds.toString().isEmpty()) {
					projectIds.append(taskProject.getProjectId());
				} else {
					projectIds.append("," + taskProject.getProjectId());
				}
				if (projectNames.toString().isEmpty()) {
					projectNames.append(taskProject.getName());
				} else {
					projectNames.append("," + taskProject.getName());
				}
			}
			wdtTTask.setProjectIds(projectIds.toString());
			
			wdtTTask.setProjectNames(projectNames.toString());
			
		}
	}
	public List<WdtTTask> findList(WdtTTask wdtTTask) {
		return super.findList(wdtTTask);
	}

	public Page<WdtTTask> findPage(Page<WdtTTask> page, WdtTTask wdtTTask) {
		return super.findPage(page, wdtTTask);
	}

	@Transactional(readOnly = false)
	public void save(WdtTTask wdtTTask) {
		
		super.save(wdtTTask);
	}
	@Transactional(readOnly = false)
	public void saveAboutEdit(WdtTTask wdtTTask) {
		if(com.movitech.mbox.common.utils.StringUtils.isEmpty(wdtTTask.getId())){
			wdtTTask.setTaskCreateUser(UserUtils.getUser());
		}
		if(com.movitech.mbox.common.utils.StringUtils.isEmpty(wdtTTask.getIsHaveSplit())){
			wdtTTask.setIsHaveSplit("1");
		}
		if(!StringUtils.isEmpty(wdtTTask.getThemeId())){
			wdtTTask.setIsTemplete(YesNoEnum.Status_0.getValue());
		}
		super.save(wdtTTask);
	}
	@Transactional(readOnly = false)
	public void delete(WdtTTask wdtTTask) {
		super.delete(wdtTTask);
	}

	@Transactional(readOnly = false)
	public void submit(WdtTTask wdtTTask) {
		wdtTTask.setIsRelationTask(RelationTaskEnum.Status_1.getValue());
		wdtTTask.setExecutionStatus(ExecutionStatusEnum.Status_1.getValue());
		//主表增加
		saveAboutEdit(wdtTTask);
		//加入任务参与者
		addAllPerson(wdtTTask);
		//更新上传文件
		updateFiles(wdtTTask);
		//模板内容保存
		updateTemplateContent(wdtTTask);
		List<String> reciverIds=new LinkedList<>();
		reciverIds.add(wdtTTask.getOwner().getId());
		Message meaasge = messageService.checkSubmitMessage(wdtTTask.getId());
		
		if(meaasge==null){
			messageService.addTaskMessageFormat(TaskTypeEnum.Status_0.getValue(),wdtTTask.getId(),null,reciverIds,wdtTTask.getTaskCreateUser().getId(),"4",null ,null);
		}else{
			meaasge.setReciverId(wdtTTask.getOwner().getId());
			meaasge.setSenderId(wdtTTask.getTaskCreateUser().getId());
			messageService.updateMessage(meaasge);
		}
		
		//发邮件 给主责人
		User u=UserUtils.get(wdtTTask.getOwner().getId());
		String title =wdtTTask.getName()+"待接收";
		sendEMailService.sendEmailForSingle(u.getEmail(), u.getName(),title ,
				"<html><head><title>"+title+"</title></head><body>尊敬的"+u.getName()+"，您有一个任务需接受，请尽快处理</body></html>");
		
	}

	
	/**
	 * 进行中 和逾期未完成修改
	 * @param wdtTTask
	 */
	@Transactional(readOnly = false)
	public void partUpdate(WdtTTask wdtTTask) {
		if(!StringUtils.isEmpty(wdtTTask.getThemeId())){
			wdtTTask.setIsTemplete(YesNoEnum.Status_0.getValue());
		}
		//主表增加
		wdtTTask.preUpdate();
		wdtTTask.setExecutionStatus(ExecutionStatusEnum.Status_3.getValue());
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		if(wdtTTask.getEndDate().getTime()<c.getTime().getTime()){
			wdtTTask.setExecutionStatus(ExecutionStatusEnum.Status_6.getValue());
		}
		dao.updatePart(wdtTTask);
		//加入任务参与者
		addAllPerson(wdtTTask);
		
		//更新上传文件
		updateFiles(wdtTTask);
		//模板内容保存
		updateTemplateContent(wdtTTask);
		//修改人员时，需要为该任务下对应子项目的人员进行变更
		if(wdtTTask.getIsRelationTask().equals(RelationTaskEnum.Status_1.getValue())){
			List<String> parentPersons = new LinkedList<String>();
			if(wdtTTask.getAllPerson()!=null &&wdtTTask.getAllPerson().size()>0){
				for (WdtTTaskPerson p : wdtTTask.getAllPerson()) {
					parentPersons.add(p.getUser().getId());
				}
			}
			WdtTTask someChild = new WdtTTask();
			someChild.setTaskId(wdtTTask.getId());
			List<WdtTTask> childs = dao.findListForChild(someChild);
			wdtTTask.getAllPerson();
			if(childs != null && childs.size()>0){
				for (WdtTTask child : childs) {
					child.setParentPersons(parentPersons);
					changeType3Person(child);
				}
			}
		}
		//修改截至日期时，需要修改对应预警字段
		WdtTTaskExtension extension= extTaskExtensionDao.getNewBytaskId(wdtTTask.getId(),"0");
		if(extension!=null && extension.getStatus().equals("1")){
			List<Warning> warnings = warningDao.getBytaskId(wdtTTask.getId());
			if(warnings!=null &&warnings.size()>0){
				Warning warning = warnings.get(0);
				Date endDate = wdtTTask.getEndDate();
				Date begin = DateUtils.addDays(endDate, 0 - warning.getDays());
				warning.setWarningStartDate(begin);
				warningDao.update(warning);
			}
		}
		
		
		
		
	}
	
	@Transactional(readOnly = false)
	public boolean submitBtn(WdtTTask wdtTTask) {
		WdtTTask entity = dao.get(wdtTTask.getId());
		if(entity!=null){
			if(entity.getExecutionStatus().equals(ExecutionStatusEnum.Status_0.getValue())){
				entity.setExecutionStatus(ExecutionStatusEnum.Status_1.getValue());
				entity.preUpdate();
				dao.update(entity);
				List<String> reciverIds=new LinkedList<>();
				reciverIds.add(entity.getOwner().getId());
				Message meaasge = messageService.checkSubmitMessage(wdtTTask.getId());
				if(meaasge==null){
					messageService.addTaskMessageFormat(TaskTypeEnum.Status_0.getValue(),wdtTTask.getId(),null,reciverIds,wdtTTask.getTaskCreateUser().getId(),"4",null,null );
				}else{
					meaasge.setReciverId(wdtTTask.getOwner().getId());
					meaasge.setSenderId(wdtTTask.getTaskCreateUser().getId());
					messageService.updateMessage(meaasge);
				}
				
				//发邮件 给主责人
				User u=UserUtils.get(entity.getOwner().getId());
				String title =entity.getName()+"待接收";
				sendEMailService.sendEmailForSingle(u.getEmail(), u.getName(),title ,
						"<html><head><title>"+title+"</title></head><body>尊敬的"+u.getName()+"，您有一个任务需接受，请尽快处理</body></html>");
				return true;
			}
			
		}
		return false;
	}
	
	@Transactional(readOnly = false)
	public void submitChild(WdtTTask wdtTTask) {
		wdtTTask.setIsRelationTask(RelationTaskEnum.Status_0.getValue());
		wdtTTask.setExecutionStatus(ExecutionStatusEnum.Status_1.getValue());
		//主表增加
		saveAboutEdit(wdtTTask);
		//加入任务参与者（创建者和主责人）
		List<String> participant = new LinkedList<String>();
		participant.add(wdtTTask.getTaskCreateUser().getId());
		participant.add(wdtTTask.getOwner().getId());
		wdtTTask.setParticipant(participant);
		WdtTTaskPerson searchWdtTTaskPerson = new WdtTTaskPerson();
		searchWdtTTaskPerson.setTaskId(wdtTTask.getTaskId());
		searchWdtTTaskPerson.setType("0");
		List<WdtTTaskPerson> taskPersonList = wdtTTaskPersonDao.findList(searchWdtTTaskPerson);
		if(taskPersonList!=null && taskPersonList.size()>0){
			List<String> parentPersons = new LinkedList<String>();
			for (WdtTTaskPerson wdtTTaskPerson : taskPersonList) {
				parentPersons.add(wdtTTaskPerson.getUser().getId());
			}
			wdtTTask.setParentPersons(parentPersons);
		}
		addAllPerson(wdtTTask);
		changeType3Person(wdtTTask);
		//增加关联表数据
		updateRelation(wdtTTask);
		List<String> reciverIds=new LinkedList<>();
		reciverIds.add(wdtTTask.getOwner().getId());
		messageService.addTaskMessageFormat(TaskTypeEnum.Status_0.getValue(),wdtTTask.getId(),null,reciverIds,wdtTTask.getTaskCreateUser().getId(),"4",null ,null);
	}
	
	private void updateRelation (WdtTTask wdtTTask){
		WdtTTaskRelation entity = new WdtTTaskRelation();
		entity.setRelationTaskId(wdtTTask.getId());
		entity.setTaskId(wdtTTask.getTaskId());
		WdtTTaskRelation WdtTTaskRelation = wdtTTaskRelationDao.get(entity);
		if(WdtTTaskRelation == null){
			entity.preInsert();
			wdtTTaskRelationDao.insert(entity);
		}else{
			entity.setId(WdtTTaskRelation.getId());
			entity.preUpdate();
			wdtTTaskRelationDao.update(entity);
		}
		
		
	}
	private void updateFiles(WdtTTask wdtTTask) {
		if (wdtTTask.getFileIds() != null && wdtTTask.getFileIds().size() > 0) {
			List<String> unUsedFile = dao.selectUnUsedFile(wdtTTask);
			if (unUsedFile != null && unUsedFile.size() > 0) {
				wdtTFileInfoDao.deleteIds(unUsedFile);
			}
			if (wdtTTask.getFileIds() != null && wdtTTask.getFileIds().size() > 0) {
				dao.updateUsedFiles(wdtTTask);
			}
		}

	}
	/**
	 * 更新任务内容模板内容
	 * @param wdtTTask
	 */
	private void updateTemplateContent(WdtTTask wdtTTask){
		if(!StringUtils.isEmpty(wdtTTask.getThemeId()) && wdtTTask.getIsTemplete()!=null 
				&&wdtTTask.getIsTemplete().equals(YesNoEnum.Status_0.getValue())){
			wdtTTaskContentDao.deleteOld(wdtTTask.getTaskId());
			if (wdtTTask.getWdtTTaskTempleteList() != null && wdtTTask.getWdtTTaskTempleteList().size() > 0) {
				for (WdtTTaskTemplete wdtTTaskTemplete : wdtTTask.getWdtTTaskTempleteList()) {
					if(wdtTTask.getThemeId().equals(wdtTTaskTemplete.getThemeId())){
						WdtTTaskContent entity = new WdtTTaskContent();
						entity.setTaskId(wdtTTask.getId());
						entity.setTempleteId(wdtTTaskTemplete.getId());
						entity.setContent(wdtTTaskTemplete.getContent());
						WdtTTaskContent wdtTTaskContent = wdtTTaskContentDao.getUsedContent(entity);
						if(wdtTTaskContent == null){
							entity.preInsert();
							wdtTTaskContentDao.insert(entity);
						}else{
							entity.setId(wdtTTaskContent.getId());
							entity.preUpdate();
							wdtTTaskContentDao.update(entity);
						}
					}
					
				}
			}
			
		}
		//更新关联项目
		taskProjectService.add(wdtTTask.getId(), wdtTTask.getProjectIds(), wdtTTask.getProjectNames());
		
	}
	private void addAllPerson(WdtTTask wdtTTask) {
		List<WdtTTaskPerson> allPersons = new LinkedList<WdtTTaskPerson>();
		if (wdtTTask.getManagers() != null && wdtTTask.getManagers().size() > 0) {
			for (int i = 0; i < wdtTTask.getManagers().size(); i++) {
				addPerson(wdtTTask.getId(), PersonTypeEnum.Status_1.getValue()
						, wdtTTask.getManagers().get(i), allPersons);
			}
		}
		if (wdtTTask.getSubscribers() != null && wdtTTask.getSubscribers().size() > 0) {
			for (int i = 0; i < wdtTTask.getSubscribers().size(); i++) {
				addPerson(wdtTTask.getId(), PersonTypeEnum.Status_0.getValue()
						, wdtTTask.getSubscribers().get(i), allPersons);
			}
		}
		if (wdtTTask.getParticipant() != null && wdtTTask.getParticipant().size() > 0) {
			for (int i = 0; i < wdtTTask.getParticipant().size(); i++) {
				addPerson(wdtTTask.getId(), PersonTypeEnum.Status_2.getValue()
						, wdtTTask.getParticipant().get(i), allPersons);
			}
		}
		/*if(wdtTTask.getParentPersons()!=null && wdtTTask.getParentPersons().size()>0){
			for (int i = 0; i < wdtTTask.getParentPersons().size(); i++) {
				addPerson(wdtTTask.getId(), PersonTypeEnum.Status_3.getValue()
						, wdtTTask.getParentPersons().get(i), allPersons);
			}
		}*/
		/*addPerson(wdtTTask.getId(), PersonTypeEnum.Status_2.getValue()
				, wdtTTask.getTaskCreateUser().getId(), allPersons);
		addPerson(wdtTTask.getId(), PersonTypeEnum.Status_2.getValue()
				, wdtTTask.getOwner().getId(), allPersons);*/
		wdtTTask.setAllPerson(allPersons);
		dao.removeTaskPersonsNoType3(wdtTTask);
		dao.insertTaskPersons(wdtTTask);
	}
	private void changeType3Person(WdtTTask wdtTTask) {
		List<WdtTTaskPerson> allPersons = new LinkedList<WdtTTaskPerson>();
		if(wdtTTask.getParentPersons()!=null && wdtTTask.getParentPersons().size()>0){
			for (int i = 0; i < wdtTTask.getParentPersons().size(); i++) {
				addPerson(wdtTTask.getId(), PersonTypeEnum.Status_3.getValue()
						, wdtTTask.getParentPersons().get(i), allPersons);
			}
		}
		wdtTTask.setAllPerson(allPersons);
		dao.removeTaskPersonsType3(wdtTTask);
		dao.insertTaskPersons(wdtTTask);
	}
	private void addPerson(String taskId, String personType, String personId, List<WdtTTaskPerson> allPersons) {
		WdtTTaskPerson person = new WdtTTaskPerson();
		person.setId(IdGen.uuid());
		person.setTaskId(taskId);
		person.preInsert();
		person.setType("0");
		person.setPersonType(personType);
		person.setUser(new User(personId));
		allPersons.add(person);
	}

	@Transactional(readOnly = false)
	public void saveDraftTask(WdtTTask wdtTTask) {
		wdtTTask.setIsRelationTask(RelationTaskEnum.Status_1.getValue());
		wdtTTask.setExecutionStatus(ExecutionStatusEnum.Status_0.getValue());
		saveAboutEdit(wdtTTask);
		//加入任务参与者
		addAllPerson(wdtTTask);
		//更新上传文件
		updateFiles(wdtTTask);
		//模板内容保存
		updateTemplateContent(wdtTTask);
	}

	public Page<WdtTTask> findPageForMyTask(Page<WdtTTask> page, WdtTTask wdtTTask) {
		wdtTTask.setRoleIds(dictDao.findValueListByType("bus_admin"));
		Page<WdtTTask> result = super.findPage(page, wdtTTask);
		List<WdtTTask> list = result.getList();
		if (list != null && list.size() > 0) {
			for (WdtTTask task : list) {
				if (!StringUtils.isEmpty(task.getIsHaveSplit()) && task.getIsHaveSplit().equals(YesNoEnum.Status_0.getValue())) {
					/*if (task.getExecutionStatus() != null && task.getExecutionStatus().equals(ExecutionStatusEnum.Status_7)
							|| task.getExecutionStatus().equals(ExecutionStatusEnum.Status_8)) {
						task.setOverCount(1);
					} else {
						task.setOverCount(0);
					}*/
					task.setOverCount(task.getItemOverCount());
				} else {
					task.setOverCount(0);
					task.setTotalCount(0);
					//task.setOverCount(task.getItemOverCount());
				}
				//加入进度反馈
				task.setNewCommentsInfo(wdtTTaskCommentsDao.getNewCommentsByTaskId(task.getId()));
				int showColor = 0;
				Warning warning = task.getWarning();
				if(warning!=null && warning.getWarningStartDate()!=null 
						&& warning.getDays()!=null 
						&& (task.getExecutionStatus().equals(ExecutionStatusEnum.Status_3.getValue())||task.getExecutionStatus().equals(ExecutionStatusEnum.Status_6.getValue()))){
					Date now = getStartTime();
					if((now.getTime() -warning.getWarningStartDate().getTime())/86400000<0){
						showColor = 0;
					}else if((now.getTime() -warning.getWarningStartDate().getTime())/86400000<=warning.getDays()){
						showColor = 2;
					}else if ((now.getTime() -warning.getWarningStartDate().getTime())/86400000>warning.getDays()){
						showColor = 1;
					}
					
				
				}
				if(task.getExecutionStatus().equals(ExecutionStatusEnum.Status_6.getValue())){
					showColor = 1;
				}
				task.setShowColor(showColor);
				//已拒绝状态 如果有移交操作只有移交者有修改动能，，没有移交操作则是发起人有修改动能
				if(task.getExecutionStatus().equals(ExecutionStatusEnum.Status_2.getValue())||task.getExecutionStatus().equals(ExecutionStatusEnum.Status_1.getValue())){
					//获取最新移交记录
					WdtTTaskHandover wdtTTaskHandover = wdtTTaskHandoverDao.getNewHandOverByTaskId(task.getId(), "0");
					if(wdtTTaskHandover != null ){
						task.setHandOverUserId(wdtTTaskHandover.getCreateBy().getId());
					}
					
				}
				//判断办结按钮是否显示  0显示  1不显示
				TaskBanjie banjie=taskBanjieMapper.selectByTaskId(task.getId());
				if(banjie!=null) {
					String status=banjie.getStatus();
					if("2".equals(status)) {
						task.setBanjieShow("0");
					}else {
						task.setBanjieShow("1");
					}
				}else {
					task.setBanjieShow("0");
				}
				//判断取消按钮是否显示  0显示  1不显示
				TaskCancel cancel=taskCancelMapper.selectByTaskId(task.getId());
				if(cancel!=null) {
					String status=cancel.getStatus();
					if("2".equals(status)) {
						task.setCancalShow("0");
					}else {
						task.setCancalShow("1");
					}
				}else {
					task.setCancalShow("0");
				}
				//判断延期按钮是否显示  0显示  1不显示
				TaskExtension extension=taskExtensionMapper.selectByTaskId(task.getId());
				if(extension!=null) {
					String status=extension.getStatus();
					if("2".equals(status)||"1".equals(status)) {
						task.setExtensionShow("0");
					}else {
						task.setExtensionShow("1");
					}
				}else {
					task.setExtensionShow("0");
				}
				//判断暂停按钮是否显示  0显示  1不显示
				TaskPause pause=taskPauseMapper.selectByTaskId(task.getId());
				if(pause!=null) {
					String status=pause.getStatus();
					if("2".equals(status)||"1".equals(status)) {
						task.setPauseShow("0");
					}else {
						task.setPauseShow("1");
					}
				}else {
					task.setPauseShow("0");
				}
	
			}
			
		}
		return result;
	}
	
	public  Date getStartTime(){
        Calendar todayStart = Calendar.getInstance();
         todayStart.set(Calendar.HOUR_OF_DAY, 0);
         todayStart.set(Calendar.MINUTE, 0);
         todayStart.set(Calendar.SECOND, 0);
         todayStart.set(Calendar.MILLISECOND, 0);
         return todayStart.getTime();
}
	/**
	 * 查询子任务
	 * @param page
	 * @param wdtTTask
	 * @return
	 * @author Aibal.He
	 */
	public Page<WdtTTask> findPageForChildMyTask(Page<WdtTTask> page, WdtTTask wdtTTask) {
		wdtTTask.setPage(page);
		page.setList(dao.findListForChild(wdtTTask));
		return page;
	}
	

	/**
	 * Jack.Gong
	 *
	 * @param wdtTTask
	 * @return
	 */
	@Transactional(readOnly = false)
	public int updateTask(WdtTTask wdtTTask) {
		wdtTTask.preUpdate();
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		if(wdtTTask.getEndDate().getTime()<c.getTime().getTime()){
			wdtTTask.setExecutionStatus(ExecutionStatusEnum.Status_6.getValue());
		}
		int i = dao.update(wdtTTask);
		
		
		
		return i;
	}

	/**
	 * Jack.Gong
	 *
	 * @param
	 * @return
	 */
	public WdtTTask getTaskDetailByTaskId(String taskId) {

		WdtTTask task = dao.getTask(taskId);
		task.setLoginUserId(UserUtils.getUser().getId());
		List<WdtTTaskProject> taskProjectList = wdtTTaskProjectDao.retrieveTaskProjectByTaskId(taskId);

		if (task != null) {

			//设置关联项目编号
			List<String> relatedProjectList = new ArrayList<String>();
			if (taskProjectList != null && taskProjectList.size() > 0) {
				for (WdtTTaskProject taskProject : taskProjectList) {
					relatedProjectList.add(taskProject.getProjectId());
				}

				task.setRelatedProject(relatedProjectList);
			}

			//设置阅知人、管理层、参与人
			WdtTTaskPerson searchWdtTTaskPerson = new WdtTTaskPerson();
			searchWdtTTaskPerson.setTaskId(taskId);
			searchWdtTTaskPerson.setType("0");
			List<WdtTTaskPerson> taskPersonList = wdtTTaskPersonDao.findList(searchWdtTTaskPerson);

			List<String> subscribers = new ArrayList<String>();
			List<String> managers = new ArrayList<String>();
			List<WdtTTaskPerson> subscriberPersons = new ArrayList<WdtTTaskPerson>();
			List<WdtTTaskPerson> managerPersons = new ArrayList<WdtTTaskPerson>();
			List<String> participant = new ArrayList<String>();
			List<WdtTTaskPerson> allParticipant = new ArrayList<WdtTTaskPerson>();
			StringBuilder participantName = new StringBuilder();
			Map<String,WdtTTaskPerson> participantMap =  new HashMap<String,WdtTTaskPerson>();
			List<String> otherPersonIds = new ArrayList<String>();
			List<String> subscriberPersonsList = new ArrayList<String>();
			if (taskPersonList != null && taskPersonList.size() > 0) {
				for (WdtTTaskPerson p : taskPersonList) {

					if (PersonTypeEnum.Status_0.getValue().equals(p.getPersonType())) {//阅知人
						subscribers.add(p.getUser().getId());
						subscriberPersons.add(p);
						subscriberPersonsList.add(p.getUser().getId());
					} else if (PersonTypeEnum.Status_1.getValue().equals(p.getPersonType())) {//管理层
						managers.add(p.getUser().getName());
						managerPersons.add(p);
					} else if (PersonTypeEnum.Status_2.getValue().equals(p.getPersonType())) {//参与者
						participantMap.put(p.getUser().getId(), p);
						//participant.add(p.getUser().getName());
						
					}else if (PersonTypeEnum.Status_3.getValue().equals(p.getPersonType())) {//参与者
						otherPersonIds.add(p.getUser().getId());
					}

				}
				allParticipant.addAll(new ArrayList(participantMap.values()));
				//参与人携带 发起人 + 主责人, 任务录入已处理
//				if(task.getTaskCreateUser() != null){
//					participant.add(task.getTaskCreateUser().getName());
//				}
//				
//				if(task.getOwner() != null){
//					participant.add(task.getOwner().getName());
//				}
				Iterator it = participantMap.keySet().iterator();  
			       while (it.hasNext()) {  
			           String key = it.next().toString();  
			           participant.add(key);  
			           participantName.append(","+participantMap.get(key).toString());  
			       }  
				task.setSubscribers(subscribers);
				task.setManagers(managers);
				task.setSubscriberPersons(subscriberPersons);
				task.setManagerPersons(managerPersons);
				task.setParticipant(participant);
				task.setAllPerson(allParticipant);
				task.setParticipantName(participantName.toString().substring(1));
				task.setOtherPersonIds(otherPersonIds);
				//获取主任务阅知人
				if(task.getIsRelationTask().equals(RelationTaskEnum.Status_0.getValue())){
					WdtTTask parent = new WdtTTask();
					parent = dao.getByChildId(task.getId());
					WdtTTaskPerson parentSubscriberPerson = new WdtTTaskPerson();
					parentSubscriberPerson.setTaskId(parent.getId());
					parentSubscriberPerson.setType("0");
					parentSubscriberPerson.setPersonType("0");
					List<WdtTTaskPerson> parentSubscriberPersonList = wdtTTaskPersonDao.findList(parentSubscriberPerson);
					if(parentSubscriberPersonList!= null &&parentSubscriberPersonList.size()>0){
						List<String> parentSubscribers = new ArrayList<String>();
						for (WdtTTaskPerson wdtTTaskPerson : parentSubscriberPersonList) {
							parentSubscribers.add(wdtTTaskPerson.getUser().getId());
						}
						task.setParentSubscribers(parentSubscribers);
					}
						
				}
			}

			//计算 计划时间 和 剩余时间
			if (task.getStartDate() != null && task.getEndDate() != null) {
				task.setPlanDays((int)(DateUtils.getDistanceOfTwoDate(task.getStartDate(), task.getEndDate()) + 1));
				//if (!new Date().after(task.getEndDate())) {
					int leavingDays=(int)(DateUtils.getDistanceOfTwoDate(DateUtils.parseDate(DateUtils.formatDate(new Date())), DateUtils.parseDate(DateUtils.formatDate(task.getEndDate()))) + 1);
					if(String.valueOf(leavingDays).contains("-")) {
						leavingDays=0;
					}
					task.setLeavingDays(leavingDays);
				//}
			}

			//设置附件信息
			WdtTFileInfo searchFileInfo = new WdtTFileInfo();
			searchFileInfo.setBizTableName("wdt_t_task");
			searchFileInfo.setBizTablePkName("id");
			searchFileInfo.setBizTablePkValue(taskId);
			List<WdtTFileInfo> fileList = wdtTFileInfoDao.findAllFileListForTask(searchFileInfo);

			task.setFileList(fileList);
			
			//有选择分类主题时显示主题信息
			if(!StringUtils.isEmpty(task.getThemeId()) && task.getIsTemplete()!=null 
					&&task.getIsTemplete().equals(YesNoEnum.Status_0.getValue())){
				task.setWdtTTaskTheme(TaskUtils.getThemeById(task.getThemeId()));
				if(task.getWdtTTaskTheme().getIsRelateProject().equals("0")){
					appendProjects(task);
				}
			}else{
				task.setWdtTTaskTheme(new WdtTTaskTheme());
			}
			//加入 任务内容模板list
			task.setWdtTTaskTempleteList(wdtTTaskTempleteDao.getTempletesAndContent(task.getId()));
			//已拒绝状态 如果有移交操作只有移交者有修改动能，，没有移交操作则是发起人有修改动能
			if(task.getExecutionStatus().equals(ExecutionStatusEnum.Status_2.getValue())||task.getExecutionStatus().equals(ExecutionStatusEnum.Status_1.getValue())){
				//获取最新移交记录
				WdtTTaskHandover wdtTTaskHandover = wdtTTaskHandoverDao.getNewHandOverByTaskId(task.getId(), "0");
				if(wdtTTaskHandover != null ){
					task.setHandOverUserId(wdtTTaskHandover.getCreateBy().getId());
				}
				
			}
			//加入标蓝色id
			task.setBluePersonIds(wdtTTaskPersonDao.getBluePersonIds(task.getId()));
			task.setSubscribersIds(subscriberPersonsList);
			int showColor = 0;
			Warning warning = task.getWarning();
			if(warning!=null && warning.getWarningStartDate()!=null 
					&& warning.getDays()!=null 
					&& (task.getExecutionStatus().equals(ExecutionStatusEnum.Status_3.getValue())||task.getExecutionStatus().equals(ExecutionStatusEnum.Status_6.getValue()))){
				Date now = getStartTime();
			
				if((now.getTime() -warning.getWarningStartDate().getTime())/86400000<0){
					showColor = 0;
				}else if((now.getTime() -warning.getWarningStartDate().getTime())/86400000<=warning.getDays()){
					showColor = 2;
				}else if ((now.getTime() -warning.getWarningStartDate().getTime())/86400000>warning.getDays()){
					showColor = 1;
				}	
				
			}
			if(task.getExecutionStatus().equals(ExecutionStatusEnum.Status_6.getValue())){
				showColor = 1;
			}
			task.setShowColor(showColor);
			//判断办结按钮是否显示  0显示  1不显示
			TaskBanjie banjie=taskBanjieMapper.selectByTaskId(task.getId());
			if(banjie!=null) {
				String status=banjie.getStatus();
				if("2".equals(status)) {
					task.setBanjieShow("0");
				}else {
					task.setBanjieShow("1");
				}
			}else {
				task.setBanjieShow("0");
			}
			//判断取消按钮是否显示  0显示  1不显示
			TaskCancel cancel=taskCancelMapper.selectByTaskId(task.getId());
			if(cancel!=null) {
				String status=cancel.getStatus();
				if("2".equals(status)) {
					task.setCancalShow("0");
				}else {
					task.setCancalShow("1");
				}
			}else {
				task.setCancalShow("0");
			}
			//判断延期按钮是否显示  0显示  1不显示
			TaskExtension extension=taskExtensionMapper.selectByTaskId(task.getId());
			if(extension!=null) {
				String status=extension.getStatus();
				if("2".equals(status)||"1".equals(status)) {
					task.setExtensionShow("0");
				}else {
					task.setExtensionShow("1");
				}
			}else {
				task.setExtensionShow("0");
			}
			//判断暂停按钮是否显示  0显示  1不显示
			TaskPause pause=taskPauseMapper.selectByTaskId(task.getId());
			if(pause!=null) {
				String status=pause.getStatus();
				if("2".equals(status)||"1".equals(status)) {
					task.setPauseShow("0");
				}else {
					task.setPauseShow("1");
				}
			}else {
				task.setPauseShow("0");
			}
		}

		return task;

	}

	@Transactional(readOnly = false)
	public int completeTask(WdtTTask wdtTTask) {
			//更新 子节点
			WdtTTask current = dao.getTask(wdtTTask.getId());
			if(current == null){
				return -1;
			}
			if(current.getExecutionStatus().equals(ExecutionStatusEnum.Status_3.getValue())
					||current.getExecutionStatus().equals(ExecutionStatusEnum.Status_6.getValue())){
				WdtTTaskItem searchTaskItem = new WdtTTaskItem();
				searchTaskItem.setTaskId(wdtTTask.getId());
				List<WdtTTaskItem> taskItemList = wdtTTaskItemService.findList(searchTaskItem);
				if (taskItemList != null && taskItemList.size() > 0) {
					for(WdtTTaskItem item : taskItemList){
						if(item.getExecutionStatus().equals(ExecutionStatusEnum.Status_6.getValue())){
							return -2;
						}else if (item.getExecutionStatus().equals(ExecutionStatusEnum.Status_3.getValue())){
							return -2;
						}
					}
					
				}
				if(current.getExecutionStatus().equals(ExecutionStatusEnum.Status_6.getValue())){
					current.setExecutionStatus(ExecutionStatusEnum.Status_9.getValue());
				}else if (current.getExecutionStatus().equals(ExecutionStatusEnum.Status_3.getValue())){
					current.setExecutionStatus(ExecutionStatusEnum.Status_7.getValue());
				}
				current.setProgress("100");
				current.setInfactCompletionTime(new Date());
				current.preUpdate();
				dao.update(current);
			}
			return 0;

	}

	public WdtTTask getTask(String taskId) {
		return dao.getTask(taskId);
	}


	/**
	 * 获得门户的任务数量
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getTaskForOAHome(String userId,String executionStatus, Page page) {
		Map<String, Object> map = new HashMap<>();
//		Page page = new Page(1, 10, -1);
		//主责
		WdtTaskParam ownerParm = new WdtTaskParam();

		ownerParm.setOwnerId(userId);
		ownerParm.setPage(page);
		ownerParm.setExecutionStatus(executionStatus);
		ownerParm.setNewTaskConfigDays(Global.NEW_TASK_CONFIG_DAYS);
		List<WdtTaskVo> ownertask = formateList(dao.getByOwnerOrCreateId(ownerParm));
		
		ownerParm.setPage(null);
		int ownertaskCounts = dao.getCountsByOwnerOrCreateId(ownerParm);
		
		//发起
		WdtTaskParam createParm = new WdtTaskParam();
		createParm.setCreateId(userId);
		createParm.setPage(page);
		createParm.setExecutionStatus(executionStatus);
		createParm.setNewTaskConfigDays(Global.NEW_TASK_CONFIG_DAYS);
		List<WdtTaskVo> createtask = formateList(dao.getByOwnerOrCreateId(createParm));
		
		createParm.setPage(null);
		int createtaskCounts = dao.getCountsByOwnerOrCreateId(createParm);
		
		//关注
		WdtTaskParam followParm = new WdtTaskParam();
		followParm.setUserId(userId);
		followParm.setPage(page);
		followParm.setExecutionStatus(executionStatus);
		followParm.setNewTaskConfigDays(Global.NEW_TASK_CONFIG_DAYS);
		List<WdtTaskVo> followtask = formateList(dao.getByOwnerOrCreateId(followParm));
		
		followParm.setPage(null);
		int followtaskCounts = dao.getCountsByOwnerOrCreateId(followParm);
		
		//参与
		WdtTaskParam joinParm = new WdtTaskParam();
		joinParm.setJoinId(userId);
		joinParm.setPage(page);
		joinParm.setExecutionStatus(executionStatus);
		joinParm.setNewTaskConfigDays(Global.NEW_TASK_CONFIG_DAYS);
		List<WdtTaskVo> jointask =formateList( dao.getByOwnerOrCreateId(joinParm));
		
		joinParm.setPage(null);
		int jointaskCounts = dao.getCountsByOwnerOrCreateId(joinParm);
		
		List<WdtTaskVo> dueTask = formateList(dao.getByDueTask(new WdtTaskParam(userId,executionStatus, Global.NEW_TASK_CONFIG_DAYS, page)));
		int dueTaskCounts = dao.getCountsByDueTask(new WdtTaskParam(userId,executionStatus, Global.NEW_TASK_CONFIG_DAYS, null));
		
		List<WdtTaskVo> duesoonTask = formateList(dao.getBySoonDueTask(new WdtTaskParam(userId, executionStatus,Global.NEW_TASK_CONFIG_DAYS, page)));
		int duesoonTaskCounts = dao.getCountsBySoonDueTask(new WdtTaskParam(userId,executionStatus, Global.NEW_TASK_CONFIG_DAYS, null));
		
		map.put("ownertask", ownertask);
		map.put("createtask", createtask);
		map.put("followtask", followtask);
		map.put("jointask", jointask);
		map.put("dueTask", dueTask);
		map.put("duesoonTask", duesoonTask);
		
		map.put("ownertaskCounts", ownertaskCounts);
		map.put("createtaskCounts", createtaskCounts);
		map.put("followtaskCounts", followtaskCounts);
		map.put("jointaskCounts", jointaskCounts);
		map.put("dueTaskCounts", dueTaskCounts);
		map.put("duesoonTaskCounts", duesoonTaskCounts);
		
		return map;
	}

	@Transactional(readOnly = false)
	public boolean deleteTask(WdtTTask wdtTTask){
		WdtTTask entity = dao.get(wdtTTask.getId());
		if(entity!=null){
			if(entity.getExecutionStatus().equals(ExecutionStatusEnum.Status_0.getValue())){
				dao.deleteById(wdtTTask.getId());
				return true;
			}
		}
		return false;
	}

	/**
	 * OA门户任务提醒
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getTaskAnotationForOAHome(String userId) {
		Map<String, Object> map = new HashMap<>();
		Page page = new Page(1, 10, -1);
		//主责
		WdtTaskParam param = new WdtTaskParam();
		param.setPage(page);
		param.setUserId(userId);
        param.setMessageTag("0");
		map.put("warningAnotation", dao.findMessageBytype(param));
		param.setMessageTag("1");
		map.put("schudeAnotation", dao.findMessageBytype(param));
		param.setMessageTag("2");
		map.put("taskAnotation",  dao.findMessageBytype(param));
		param.setMessageTag("3");
		map.put("replyAnotation", dao.findMessageBytype(param));
		param.setMessageTag("4");
		map.put("atAnotation", dao.findMessageBytype(param));
		
		map.put("unReadCountDefferedWarning", findUnReadCountForDefferedWarning(userId));
		map.put("unReadCountScheduleWarning", findUnReadCountForScheduleWarning(userId));
		map.put("unReadCountTaskWarning", findUnReadCountForTaskWarning(userId));
		map.put("unReadCountReplyWarning", findUnReadCountForReplyWarning(userId));
		map.put("unReadCountAtWarning", findUnReadCountForAtWarning(userId));
		return map;
	}
	public Map<String, Object> getMesssgeOrtodo(String userId) {
		Map<String, Object> map = new HashMap<>();
		//
		WdtTaskParam param=new WdtTaskParam();
		param.setUserId(userId);
		map.put("messsgeAmount", dao.getMessageCounts(param));
		WdtTaskParam wait=new WdtTaskParam();
		wait.setUserId(userId);
		List list=dao.getWaitingTaskForOAHome(wait);
		map.put("toDoAmount",list.size());
		return map;
	}

	 public List<WdtTTask> getTaskByUserIdOrOwerId(String userId,String id){
		return dao.getTaskByUserIdOrOwerId(userId,id);
	 }

	
	
	public List<WdtTaskVo> getWaitingTaskForOAHome(String userId, Page page) {
		
		List<WdtTaskVo> myWaitingTaskList = dao.getWaitingTaskForOAHome(new WdtTaskParam(userId,null, Global.NEW_TASK_CONFIG_DAYS, page));
		
		return myWaitingTaskList;
	}

	/**
	 * 我的待办事项 - 分页
	 * @param userId
	 * @param page
	 * @return
	 */
	public Page findPageForWatingTask(String userId, Page page) {
		
		page.setList(dao.getWaitingTaskForOAHome(new WdtTaskParam(userId, null,Global.NEW_TASK_CONFIG_DAYS, page)));
		
		return page;
	}

	/**
	 * 我关注的任务 - 分页
	 * @param userId
	 * @param page
	 * @return
	 */
	public Page<WdtTaskVo> findPageForFollowedTask(String userId,String search, Page page) {
		
		WdtTaskParam followParm = new WdtTaskParam();

		followParm.setUserId(userId);
		followParm.setPage(page);
        followParm.setSearch(search);
		followParm.setNewTaskConfigDays(Global.NEW_TASK_CONFIG_DAYS);
		
		page.setList(formateList(dao.getByOwnerOrCreateId(followParm)));
		
		return page;
	}
	private List<WdtTaskVo> formateList(List<WdtTaskVo> list){
		if(list!=null && list.size()>0){
			for (WdtTaskVo wdtTaskVo : list) {
				int showColor = 0;
				Warning warning = wdtTaskVo.getWarning();
				if(warning!=null && warning.getWarningStartDate()!=null 
						&& warning.getDays()!=null 
						&& (wdtTaskVo.getExecutionStatus().equals(ExecutionStatusEnum.Status_3.getValue())||wdtTaskVo.getExecutionStatus().equals(ExecutionStatusEnum.Status_6.getValue()))){
					Date now = getStartTime();
				
					if((now.getTime() -warning.getWarningStartDate().getTime())/86400000<0){
						showColor = 0;
					}else if((now.getTime() -warning.getWarningStartDate().getTime())/86400000<=warning.getDays()){
						showColor = 2;
					}else if ((now.getTime() -warning.getWarningStartDate().getTime())/86400000>warning.getDays()){
						showColor = 1;
					}	
					
				}
				if(wdtTaskVo.getExecutionStatus().equals(ExecutionStatusEnum.Status_6.getValue())){
					showColor = 1;
				}
				wdtTaskVo.setShowColor(showColor);
			}
		}
		return list;
	}

	/**
	 * 我发起的任务 - 分页
	 * @param userId
	 * @param page
	 * @return
	 */
	public Page<WdtTaskVo> findPageForCreatedTask(String userId,String search, Page page) {
		
		WdtTaskParam createParm = new WdtTaskParam();
		createParm.setCreateId(userId);
		createParm.setPage(page);
		createParm.setSearch(search);
		createParm.setNewTaskConfigDays(Global.NEW_TASK_CONFIG_DAYS);
		
		page.setList(formateList(dao.getByOwnerOrCreateId(createParm)));
		
		return page;
	}
	
	/**
	 * 我主责的任务 - 分页
	 * @param userId
	 * @param page
	 * @return
	 */
	public Page<WdtTaskVo> findPageForOwnTask(String userId, String search,Page page) {
		
		WdtTaskParam ownerParm = new WdtTaskParam();

		ownerParm.setOwnerId(userId);
		ownerParm.setPage(page);
		ownerParm.setSearch(search);
		ownerParm.setNewTaskConfigDays(Global.NEW_TASK_CONFIG_DAYS);
		
		page.setList(formateList(dao.getByOwnerOrCreateId(ownerParm)));
		
		return page;
	}
	
	/**
	 * 延期任务 - 分页
	 * @param userId
	 * @param page
	 * @return
	 */
	public Page<WdtTaskVo> findPageForDeferredTask(String userId,String search, Page page) {
		
		WdtTaskParam dueParm = new WdtTaskParam();
		dueParm.setUserId(userId);
		dueParm.setPage(page);
		dueParm.setSearch(search);
		dueParm.setNewTaskConfigDays(Global.NEW_TASK_CONFIG_DAYS);
		
		page.setList(formateList(dao.getByDueTask(dueParm)));
		
		return page;
	}
	
	/**
	 * 即将到期任务 - 分页
	 * @param userId
	 * @param page
	 * @return
	 */
	public Page<WdtTaskVo> findPageForNearingDueTask(String userId,String search, Page page) {
		
		WdtTaskParam nearingDueParm = new WdtTaskParam();
		nearingDueParm.setUserId(userId);
		nearingDueParm.setPage(page);
		nearingDueParm.setSearch(search);
		nearingDueParm.setNewTaskConfigDays(Global.NEW_TASK_CONFIG_DAYS);
		
		page.setList(formateList(dao.getBySoonDueTask(nearingDueParm)));
		
		return page;
	}
	
	/**
	 * 我参与的任务 - 分页
	 * @param userId
	 * @param page
	 * @return
	 */
	public Page<WdtTaskVo> findPageForParticipantTask(String userId,String search, Page page) {
		
		WdtTaskParam participantParm = new WdtTaskParam();
		participantParm.setJoinId(userId);
		participantParm.setPage(page);
		participantParm.setSearch(search);
		participantParm.setNewTaskConfigDays(Global.NEW_TASK_CONFIG_DAYS);
		
		page.setList(formateList(dao.getByOwnerOrCreateId(participantParm)));
		
		return page;
	}

	/**
	 * 延时提醒  - 分页
	 * @param userId
	 * @param page
	 * @return
	 */
	public Page<WdtTaskVo> findPageForDefferedWarning(String userId, Page page) {
		WdtTaskParam param = new WdtTaskParam();
		param.setPage(page);
		param.setUserId(userId);
        param.setMessageTag("0");
		page.setList(formateMessageList(dao.findMessageBytype(param)));
		
		return page;
	}
	public int findUnReadCountForDefferedWarning(String userId) {
		WdtTaskParam param = new WdtTaskParam();
		param.setUserId(userId);
        param.setMessageTag("0");
        return dao.findUnReadMessageBytype(param);
	}
	/**
	 * 按时提醒 - 分页
	 * @param userId
	 * @param page
	 * @return
	 */
	public Page<WdtTaskVo> findPageForScheduleWarning(String userId, Page page) {
		WdtTaskParam param = new WdtTaskParam();
		param.setPage(page);
		param.setUserId(userId);
        param.setMessageTag("1");
        page.setList(formateMessageList(dao.findMessageBytype(param)));
		
		return page;
	}
	public int findUnReadCountForScheduleWarning(String userId) {
		WdtTaskParam param = new WdtTaskParam();
		param.setUserId(userId);
        param.setMessageTag("1");
        return dao.findUnReadMessageBytype(param);
	}
	/**
	 * 任务提醒 - 分页
	 * @param userId
	 * @param page
	 * @return
	 */
	public Page<WdtTaskVo> findPageForTaskWarning(String userId, Page page) {
		WdtTaskParam param = new WdtTaskParam();
		param.setPage(page);
		param.setUserId(userId);
		param.setMessageTag("2");
		List<WdtTaskVo> vos = dao.findMessageBytype(param);
		for(WdtTaskVo vo:vos){
			if("5".equals(vo.getMessageType())||"6".equals(vo.getMessageType())) {
				int count=reponseDao.getResponseCountsBytaskIdOrItemId(vo.getId(),vo.getItemId(),vo.getType().equals("1")?"2":"0",userId,vo.getPinId());
				if(count>0)vo.setReplay(true);
			}
		}
		page.setList(formateMessageList(vos));
		return page;
	}
	
	public int findUnReadCountForTaskWarning(String userId) {
		WdtTaskParam param = new WdtTaskParam();
		param.setUserId(userId);
        param.setMessageTag("2");
        return dao.findUnReadMessageBytype(param);
	}
	/**
	 * 回复提醒 - 分页
	 * @param userId
	 * @param page
	 * @return
	 */
	public Page<WdtTaskVo> findPageForReplyWarning(String userId, Page page) {
		WdtTaskParam param = new WdtTaskParam();
		param.setPage(page);
		param.setUserId(userId);
		param.setMessageTag("3");
		List<WdtTaskVo> vos=dao.findMessageBytype(param);

		page.setList(formateMessageList(vos));
		return page;
	}
	public int findUnReadCountForReplyWarning(String userId) {
		WdtTaskParam param = new WdtTaskParam();
		param.setUserId(userId);
        param.setMessageTag("3");
        return dao.findUnReadMessageBytype(param);
	}
	/**
	 * @提醒 - 分页
	 * @param userId
	 * @param page
	 * @return
	 */
	public Page<WdtTaskVo> findPageForAtWarning(String userId, Page page) {
		WdtTaskParam param = new WdtTaskParam();
		param.setPage(page);
		param.setUserId(userId);
		param.setMessageTag("4");
		List<WdtTaskVo> vos=dao.findMessageBytype(param);
		page.setList(formateMessageList(vos));
		return page;
	}
	private List<WdtTaskVo> formateMessageList(List<WdtTaskVo> vos){
		List<WdtTaskVo> finalList =new LinkedList<WdtTaskVo>();
		String titleName="";
		for (WdtTaskVo wdtTaskVo : vos) {
			if(finalList.size()==0 || !titleName.equals(wdtTaskVo.getTitleName())){
				WdtTaskVo e = new WdtTaskVo();
				titleName = wdtTaskVo.getTitleName();
				e.setName(titleName);
				e.setRowType(0);
				finalList.add(e);
			}
			finalList.add(wdtTaskVo);
		}
		return finalList;
	}
	public int findUnReadCountForAtWarning(String userId) {
		WdtTaskParam param = new WdtTaskParam();
		param.setUserId(userId);
        param.setMessageTag("4");
        return dao.findUnReadMessageBytype(param);
	}
	/**
	 * 查询子任务
	 * @param taskId
	 * @param status
	 * @return
	 */
	public List<WdtTTask> getSubTaskListByTaskIdAndStatus(String taskId, String status) {
		
		WdtTTask wdtTTask = new WdtTTask();
		wdtTTask.setId(taskId);
		wdtTTask.setExecutionStatus(status);
		
		return dao.getSubTaskListByTaskIdAndStatus(wdtTTask);
	}

	public Page<TaskH5> findH5PageForMyTask(Page<TaskH5> page, TaskH5 entity) {
		entity.setPage(page);
		page.setList(dao.findH5List(entity));
		List<TaskH5> list = page.getList();
		if (list != null && list.size() > 0) {
			for (TaskH5 task : list) {
				//节点信息加入
				ExtWdtTTaskItem extWdtTTaskItem=new ExtWdtTTaskItem();
		        User user = UserUtils.getUser();
		        extWdtTTaskItem.setTaskId(task.getId());
		        extWdtTTaskItem.setCurrentUser(user);
		        List<WdtTTaskItem> items = wdtTTaskItemDao.findList(extWdtTTaskItem);
		        
		        if(items!=null&&items.size()>0){
					int a=0,b=0;
					for (WdtTTaskItem one:items) {
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
		        
		        
				task.setItems(items);
				
				int showColor = 0;
				Warning warning = task.getWarning();
				if(warning!=null && warning.getWarningStartDate()!=null 
						&& warning.getDays()!=null 
						&& (task.getExecutionStatus().equals(ExecutionStatusEnum.Status_3.getValue())||task.getExecutionStatus().equals(ExecutionStatusEnum.Status_6.getValue()))){
					Date now = getStartTime();
					if((now.getTime() -warning.getWarningStartDate().getTime())/86400000<0){
						showColor = 0;
					}else if((now.getTime() -warning.getWarningStartDate().getTime())/86400000<=warning.getDays()){
						showColor = 2;
					}else if ((now.getTime() -warning.getWarningStartDate().getTime())/86400000>warning.getDays()){
						showColor = 1;
					}
					
					
				}
				if(task.getExecutionStatus().equals(ExecutionStatusEnum.Status_6.getValue())){
					showColor = 1;
				}
				task.setShowColor(showColor);	
				
			}
		}
		return page;
	}
	/**
	 * 我的待办事项 - 分页
	 * @param userId
	 * @param page
	 * @return
	 */
	public Page findPageForWatingH5Task(String userId, Page page) {
		List<WdtTaskH5Vo> taskForH5 =dao.getWaitingTaskForH5(new WdtTaskParam(userId,null, Global.NEW_TASK_CONFIG_DAYS, page));
		/*if(taskForH5!=null&&taskForH5.size()>0){
			for(WdtTaskH5Vo task:taskForH5){
                if("1".equals(task.getMessageType()) ){//催办提醒
					List<String> List=messageDao.getTaskItemName("1",task.getId());
					String subTile="";
					for(String s:List)subTile+=s+",";
                    task.setSubTitle(subTile);
				}else if("2".equals(task.getMessageType())){//进度提醒
					List<String> scheList=messageDao.getTaskItemName("2",task.getId());
					String subTile="";
					for(String s:scheList)subTile+=s+",";
					task.setSubTitle(subTile);
				}
			}
		}*/
		page.setList(taskForH5);
		return page;
	}
	public Map<String,Object> getTaskCounts(String userId){
		Map<String, Object> map = new HashMap<>();
//		Page page = new Page(1, 10, -1);
		//主责
		WdtTaskParam ownerParm = new WdtTaskParam();

		ownerParm.setOwnerId(userId);
		ownerParm.setNewTaskConfigDays(Global.NEW_TASK_CONFIG_DAYS);
		int ownertaskCounts = dao.getCountsByOwnerOrCreateId(ownerParm);

		//发起
		WdtTaskParam createParm = new WdtTaskParam();
		createParm.setCreateId(userId);
		createParm.setNewTaskConfigDays(Global.NEW_TASK_CONFIG_DAYS);
		int createtaskCounts = dao.getCountsByOwnerOrCreateId(createParm);

		//关注
		WdtTaskParam followParm = new WdtTaskParam();
		followParm.setUserId(userId);
		followParm.setNewTaskConfigDays(Global.NEW_TASK_CONFIG_DAYS);
		int followtaskCounts = dao.getCountsByOwnerOrCreateId(followParm);

		//参与
		WdtTaskParam joinParm = new WdtTaskParam();
		joinParm.setJoinId(userId);
		joinParm.setNewTaskConfigDays(Global.NEW_TASK_CONFIG_DAYS);
		int jointaskCounts = dao.getCountsByOwnerOrCreateId(joinParm);
		int dueTaskCounts = dao.getCountsByDueTask(new WdtTaskParam(userId, null,Global.NEW_TASK_CONFIG_DAYS, null));
		int duesoonTaskCounts = dao.getCountsBySoonDueTask(new WdtTaskParam(userId,null, Global.NEW_TASK_CONFIG_DAYS, null));
		map.put("ownertaskCounts", ownertaskCounts);
		map.put("createtaskCounts", createtaskCounts);
		map.put("followtaskCounts", followtaskCounts);
		map.put("jointaskCounts", jointaskCounts);
		map.put("dueTaskCounts", dueTaskCounts);
		map.put("duesoonTaskCounts", duesoonTaskCounts);
		return map;
	}

	/**
	 * 延时提醒  - 分页
	 * @param userId
	 * @param page
	 * @return
	 */
	public Page findPageWarningH5(String userId, Page page) {
		WdtTaskParam wdtTaskParam=new WdtTaskParam();
		wdtTaskParam.setUserId(userId);
		wdtTaskParam.setPage(page);
		List<WdtTaskH5Vo> h5Vos = dao.findMessageBytypeH5(wdtTaskParam);
		for(WdtTaskH5Vo vo:h5Vos){
			if("5".equals(vo.getMessageType())||"6".equals(vo.getMessageType())) {
				int count=reponseDao.getResponseCountsBytaskIdOrItemId(vo.getId(),vo.getItemId(),vo.getType().equals("1")?"2":"0",userId,vo.getPinId());
				if(count>0)vo.setReplay(true);
			}

		}
		page.setList(h5Vos);
		return page;
		/*WdtTaskParam param = new WdtTaskParam();
		param.setPage(page);
		param.setUserId(userId);
		param.setType(null);
		List<Map<String, Object>> list = dao.findMessageMapH5(param);
		List<String> messagetypes = new ArrayList<>();
		List<String> taskIds = new ArrayList<>();
		for (Map<String, Object> map : list) {
			messagetypes.add((String) map.get("messageType"));
			taskIds.add((String) map.get("taskId"));
		}
		if(taskIds==null||taskIds.size()<1)return page;
		List<WdtTaskH5Vo> h5Vos = dao.findMessageBytypeH5(taskIds, userId);
		Map<String, List<WdtTaskH5Vo>> group_1 = new HashMap<>();
		Map<String, List<WdtTaskH5Vo>> group_2 = new HashMap<>();
		List<WdtTaskH5Vo> reList = new ArrayList<>();
		for (WdtTaskH5Vo vo : h5Vos) {
			if ("1".equals(vo.getMessageType())) {
				if (group_1.get(vo.getId()) == null) {
					List<WdtTaskH5Vo> temList = new ArrayList<>();
					temList.add(vo);
					group_1.put(vo.getId(), temList);
				} else {
					group_1.get(vo.getId()).add(vo);
				}
			} else if ("2".equals(vo.getMessageType())) {
				if (group_2.get(vo.getId()) == null) {
					List<WdtTaskH5Vo> temList = new ArrayList<>();
					temList.add(vo);
					group_2.put(vo.getId(), temList);
				} else {
					group_2.get(vo.getId()).add(vo);
				}
			} else {
				reList.add(vo);
			}
		}
		if (group_1.size() > 0){
			for (Map.Entry<String, List<WdtTaskH5Vo>> entry : group_1.entrySet()) {
				List<WdtTaskH5Vo> teList = entry.getValue();
				if (teList != null&&teList.size()>0){
					WdtTaskH5Vo  singeleTask=teList.get(0);
					String str="";
					for(WdtTaskH5Vo h5Vo:teList){
						str+=h5Vo.getItemName()+",";
					}
					singeleTask.setSubTitle(str);
					reList.add(singeleTask);
				}
			}
	   }
		if (group_2.size() > 0){
			for (Map.Entry<String, List<WdtTaskH5Vo>> entry : group_2.entrySet()) {
				List<WdtTaskH5Vo> teList = entry.getValue();
				if (teList != null&&teList.size()>0){
					WdtTaskH5Vo  singeleTask=teList.get(0);
					String str="";
					for(WdtTaskH5Vo h5Vo:teList){
						str+=h5Vo.getItemName()+",";
					}
					singeleTask.setSubTitle(str);
					reList.add(singeleTask);
				}
			}
		}
		Collections.sort(reList, new Comparator<WdtTaskH5Vo>() {
			@Override
			public int compare(WdtTaskH5Vo o1, WdtTaskH5Vo o2) {
				return o1.getCreateDate().compareTo(o2.getCreateDate());
			}
		});
		page.setList(reList);
		return page;
		*/
	}
	
	public int getTaskChildCountByTaskId(String taskId){
		return dao.getTaskChildCountByTaskId(taskId);
	}
}