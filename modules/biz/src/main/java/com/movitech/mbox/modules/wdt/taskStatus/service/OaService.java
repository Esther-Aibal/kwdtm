package com.movitech.mbox.modules.wdt.taskStatus.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;






import javax.xml.namespace.QName;

import com.movitech.mbox.common.config.*;
import com.movitech.mbox.common.utils.DateUtils;
import com.movitech.mbox.modules.sys.entity.User;
import com.movitech.mbox.modules.wdt.entity.WdtTTask;
import com.movitech.mbox.modules.wdt.taskStatus.dao.TaskBanjieMapper;
import com.movitech.mbox.modules.wdt.taskStatus.dao.TaskCancelMapper;
import com.movitech.mbox.modules.wdt.taskStatus.dao.TaskExtensionMapper;
import com.movitech.mbox.modules.wdt.taskStatus.dao.TaskPauseMapper;
import com.movitech.mbox.modules.wdt.taskStatus.dao.ext.TaskStatusMapper;

import com.movitech.mbox.modules.wdt.taskStatus.entity.TaskBanjie;
import com.movitech.mbox.modules.wdt.taskStatus.entity.TaskCancel;
import com.movitech.mbox.modules.wdt.taskStatus.entity.TaskExtension;
import com.movitech.mbox.modules.wdt.taskStatus.entity.TaskPause;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.movitech.mbox.common.utils.JsonUtil;
import com.movitech.mbox.modules.wdt.taskStatus.req.TaskStatusReq;

import org.springframework.transaction.annotation.Transactional;
import org.tempuri.ICommonWorkflowServiceServiceOALocator;
import org.tempuri.ICommonWorkflowServiceServiceOASoap;
import org.tempuri.ReviewHandlerParamter;



@Service
@Transactional(readOnly = true)
public class OaService {

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
	ThreadPoolTaskExecutor executor;
	
	private static final QName SERVICE_NAME = new QName("http://tempuri.org/", "ICommonWorkflowServiceServiceOA");


	public void sendOa(WdtTTask task, User user, TaskStatusReq req) {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					// 调用oa审批流程提交接口
					Map<String, Object> formValueMap = new HashMap<String, Object>();
					WdtTTask ptask = taskStatusMapper.getPTask(req.getTaskId());
					int taskType=0;
					if (null != ptask) {
						taskType=1;
						formValueMap.put("SJRW",ptask.getName());//上级任务
						formValueMap.put("SJRWZZR",ptask.getThemeId());//主任务负责人:
					} else {
						List<WdtTTask> stasks = taskStatusMapper.getSTasks(req
								.getTaskId());
						StringBuilder sb=new StringBuilder();
						if (stasks != null && stasks.size() > 0) {
							for (WdtTTask wdtTTask : stasks) {
								sb.append(wdtTTask.getName()+"("+ ExecutionStatusEnum.getDescription(wdtTTask.getExecutionStatus())+")|");
							}
							formValueMap.put("ZRW",sb.substring(0,sb.length()-1));//子任务
						}
					}
					List<String> names=taskStatusMapper.getTaskPerson(req.getTaskId(),PersonTypeEnum.Status_1.getValue());
					if(names != null && names.size() > 0){
						StringBuilder sb=new StringBuilder();
						for (String name : names) {
							sb.append("'"+name+"',");
						}
						formValueMap.put("GLC", sb.substring(0,sb.length()-1));//管理层
					}
					formValueMap.put("STYPE", req.getType());//类别
					formValueMap.put("STYPEID", req.getTypeId());//类别ID
					formValueMap.put("NGR",user.getName());//拟稿人
					if(null!=user.getOffice()){
						formValueMap.put("JBBM",user.getOffice().getName());//经办部门
					}
					if(taskType==0){
						formValueMap.put("RWMC", task.getName()+"(主任务)");//任务名称
						formValueMap.put("taskType", "0");
					}else{
						formValueMap.put("taskType", "1");
						formValueMap.put("RWMC", task.getName()+"(子任务)");//任务名称
					}

					if (TaskStatusEnum.Status_0.getValue().equals(req.getType())) {
						formValueMap.put("JHWCSJ",DateUtils.formatDate(req.getStartTime(),"yyyy-MM-dd HH:mm:ss"));//计划完成时间
						formValueMap.put("YCWCSJ", DateUtils.formatDate(req.getNewTime(),"yyyy-MM-dd HH:mm:ss"));//延迟完成时间
						formValueMap.put("SQYSYY", req.getContent());//申请延时原因
					} else if (TaskStatusEnum.Status_1.getValue().equals(req.getType())) {
						formValueMap.put("BJMS", req.getContent());//办结描述
					}else if (TaskStatusEnum.Status_2.getValue().equals(req.getType())) {
						formValueMap.put("SQZTYY", req.getContent());//申请暂停原因
						formValueMap.put("ZTKSSJ", DateUtils.formatDate(req.getStartTime(),"yyyy-MM-dd HH:mm:ss"));//暂停开始时间
						if(req.getNewTime()!=null){
							formValueMap.put("ZTCQSJ", DateUtils.formatDate(req.getNewTime(),"yyyy-MM-dd HH:mm:ss"));//暂停重启时间
						}
					}else if (TaskStatusEnum.Status_3.getValue().equals(req.getType())) {
						formValueMap.put("QXYY", req.getContent());//取消原因
					}
					


					String formValueJsonStr = JsonUtil.toJson(formValueMap);
					System.out.println(formValueJsonStr);
					ReviewHandlerParamter rhpObj = new ReviewHandlerParamter();
					rhpObj.setDocCreator(user.getId());
					rhpObj.setDocStatus("0"); // 0 表示流转中 1
												// 表示结束(数据在HfWorkFlowHist)
												// 11表示作废(数据在HfWorkFlowHist)
					rhpObj.setDocSubject( new String("oa审批流程发起".getBytes(),"utf-8"));
					rhpObj.setFdId("");
					rhpObj.setFdOtherSystemId(req.getTaskId());
					if("prod".equals(Global.getConfig("environment"))){
						rhpObj.setFdTemplateKeyword(FdProdTemplateKeywordEnum.getValue(req.getType(),""+taskType));
					}else{
						rhpObj.setFdTemplateKeyword(FdTemplateKeywordEnum.getValue(req.getType(),""+taskType));
					}

					rhpObj.setFlowParam("");
					rhpObj.setFormValues(formValueJsonStr);
					
					ICommonWorkflowServiceServiceOALocator o=new ICommonWorkflowServiceServiceOALocator(Global.getConfig("oa_submitted_url"),SERVICE_NAME);
					ICommonWorkflowServiceServiceOASoap po=o.getICommonWorkflowServiceServiceOASoap();
					String result=po.addReview(rhpObj);
					System.out.println(result);
					Map<String, String> map = JsonUtil.parseToMap(result);
					if("1".equals(map.get("code"))){
						if(TaskStatusEnum.Status_0.getValue().equals(req.getType())){
							TaskExtension record=taskExtensionMapper.selectByPrimaryKey(req.getTypeId());
							record.setProcessId(map.get("processId"));
							taskExtensionMapper.updateByPrimaryKeySelective(record);
						}else if(TaskStatusEnum.Status_1.getValue().equals(req.getType())){
							TaskBanjie record=taskBanjieMapper.selectByPrimaryKey(req.getTypeId());
							record.setProcessId(map.get("processId"));
							taskBanjieMapper.updateByPrimaryKeySelective(record);
						}else if(TaskStatusEnum.Status_2.getValue().equals(req.getType())){
							TaskPause record=taskPauseMapper.selectByPrimaryKey(req.getTypeId());
							record.setProcessId(map.get("processId"));
							taskPauseMapper.updateByPrimaryKeySelective(record);
						}else if(TaskStatusEnum.Status_3.getValue().equals(req.getType())){
							TaskCancel record=taskCancelMapper.selectByPrimaryKey(req.getTypeId());
							record.setProcessId(map.get("processId"));
							taskCancelMapper.updateByPrimaryKeySelective(record);
						}
					}else{
						if(TaskStatusEnum.Status_0.getValue().equals(req.getType())){
							TaskExtension record=taskExtensionMapper.selectByPrimaryKey(req.getTypeId());
							record.setDelFlag(DeleteStatusEnum.Status_1.getValue());
							taskExtensionMapper.updateByPrimaryKeySelective(record);
						}else if(TaskStatusEnum.Status_1.getValue().equals(req.getType())){
							TaskBanjie record=taskBanjieMapper.selectByPrimaryKey(req.getTypeId());
							record.setDelFlag(DeleteStatusEnum.Status_1.getValue());
							taskBanjieMapper.updateByPrimaryKeySelective(record);
						}else if(TaskStatusEnum.Status_2.getValue().equals(req.getType())){
							TaskPause record=taskPauseMapper.selectByPrimaryKey(req.getTypeId());
							record.setDelFlag(DeleteStatusEnum.Status_1.getValue());
							taskPauseMapper.updateByPrimaryKeySelective(record);
						}else if(TaskStatusEnum.Status_3.getValue().equals(req.getType())){
							TaskCancel record=taskCancelMapper.selectByPrimaryKey(req.getTypeId());
							record.setDelFlag(DeleteStatusEnum.Status_1.getValue());
							taskCancelMapper.updateByPrimaryKeySelective(record);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	

}
