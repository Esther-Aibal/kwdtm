/**
 *
 */
package com.movitech.mbox.modules.wdt.taskProject.service;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movitech.mbox.common.config.DeleteStatusEnum;
import com.movitech.mbox.common.utils.IdGen;
import com.movitech.mbox.modules.sys.entity.User;
import com.movitech.mbox.modules.sys.utils.UserUtils;
import com.movitech.mbox.modules.wdt.exception.RollBackException;
import com.movitech.mbox.modules.wdt.taskProject.dao.TaskProjectMapper;
import com.movitech.mbox.modules.wdt.taskProject.entity.TaskProject;
import com.movitech.mbox.modules.wdt.taskProject.entity.TaskProjectExample;




/**
 * 操作日志service
 * @author felix.jin
 * 2017年8月22日
 */
@Service
@Transactional(readOnly = true)
public class TaskProjectService {
	
	@Autowired
	private TaskProjectMapper taskProjectMapper;
	
	public List<TaskProject> list(String taskId) {
		TaskProjectExample example=new TaskProjectExample();
		example.createCriteria().andTaskIdEqualTo(taskId);
		example.setOrderByClause("project_id asc");
		return taskProjectMapper.selectByExample(example); 
	}
	
	@Transactional(readOnly = false)
	public int add(String taskId,String projectIds,String projectNames) {
			if(projectIds!=null&&projectNames!=null){
				String[] projectId=projectIds.split(",");
				String[] projectName=projectNames.split(",");
				if(projectId.length>0&&projectName.length>0&&projectId.length==projectName.length){
					TaskProjectExample example=new TaskProjectExample();
					example.createCriteria().andTaskIdEqualTo(taskId);
					taskProjectMapper.deleteByExample(example); 
					TaskProject tp=new TaskProject();
					User u= UserUtils.getUser();
					if(u!=null){
						tp.setCreateBy(u.getId());
						tp.setUpdateBy(u.getId());
					}
					tp.setCreateDate(new Date());
					tp.setUpdateDate(tp.getCreateDate());
					tp.setDelFlag(DeleteStatusEnum.Status_0.getValue());
					tp.setTaskId(taskId);
					for (int i = 0; i < projectId.length; i++) {
						tp.setId(IdGen.uuid());
						tp.setProjectId(projectId[i]);
						tp.setName(projectName[i]);
						taskProjectMapper.insertSelective(tp);
					}
				}
			}
			return 0;
	}

	
}