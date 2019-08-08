/**
 * 
 */
package com.movitech.mbox.modules.wdt.dao.ext;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.movitech.mbox.common.persistence.annotation.MyBatisDao;
import com.movitech.mbox.modules.wdt.entity.WdtTTask;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskItem;
import com.movitech.mbox.modules.wdt.entity.ext.ExtWdtTTaskItem;

/**
 * 任务节点补充dao
 * @author felix.jin
 * 2017年8月8日
 */
@MyBatisDao
public interface ExtWdtTTaskItemDao {

	ExtWdtTTaskItem getInfo(String id);

	List<ExtWdtTTaskItem> findList(WdtTTaskItem wdtTTaskItem);

	WdtTTask getTask(String taskId);

	int isOwner(@Param("taskId")String taskId, @Param("userId")String userId);

	@Update("update wdt_t_task_item set execution_status = #{executionStatus} where task_id = #{taskId}")
	public int updateTaskItem(WdtTTaskItem taskItem);

	int upOvertimeTaskItem(String taskId);
	
	int upInprogressTaskItem(String taskId);

	int upTaskStatus(@Param("taskId")String taskId, @Param("status")String status);

	int upTaskItemStatus(@Param("taskId")String taskId, @Param("status")String status);

	int upTaskSplit(String taskId);

	/**
	 * 根据主任务查询子节点
	 * @param taskId
	 * @return
	 */
	public List<WdtTTaskItem> getTaskItemByTaskId(String taskId);

	
	/**
	 * 查询是否有节点的消息
	 * @param id
	 * @param type
	 * @return
	 */
	int getItemMessage(@Param("taskItemId")String taskItemId,@Param("type") int type);

	int getItemCountByTaskId(@Param("taskId")String taskId);
}