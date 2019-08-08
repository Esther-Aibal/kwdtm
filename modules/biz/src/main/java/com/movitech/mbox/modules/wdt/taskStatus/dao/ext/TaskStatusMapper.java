package com.movitech.mbox.modules.wdt.taskStatus.dao.ext;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.movitech.mbox.common.persistence.annotation.MyBatisDao;
import com.movitech.mbox.modules.wdt.entity.Message;
import com.movitech.mbox.modules.wdt.entity.WdtTTask;
import com.movitech.mbox.modules.wdt.taskStatus.entity.TaskOa;



@MyBatisDao
public interface TaskStatusMapper {

	int deleteTask(String taskId);

	int upTaskStatus(@Param("taskId")String taskId,@Param("status")String status);

	int handover(@Param("taskId")String taskId,@Param("userId")String userId);

	WdtTTask getPTask(String taskId);

	List<WdtTTask> getSTasks(String taskId);

	List<String> getTaskPerson(@Param("taskId")String taskId, @Param("type")String type);

	List<TaskOa> list(String taskId);

	Message getMessage(String taskId);
	
	int recalling(@Param("taskId")String taskId,@Param("userId")String userId,@Param("executionStatus")String executionStatus);
    
}