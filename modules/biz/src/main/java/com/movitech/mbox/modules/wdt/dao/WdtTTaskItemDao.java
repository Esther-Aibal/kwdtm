/**
 * 
 */
package com.movitech.mbox.modules.wdt.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.movitech.mbox.common.persistence.CrudDao;
import com.movitech.mbox.common.persistence.annotation.MyBatisDao;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskItem;

/**
 * 任务节点DAO接口
 * @author Aibal.He
 * @version 2017-08-07
 */
@MyBatisDao
public interface WdtTTaskItemDao extends CrudDao<WdtTTaskItem> {
	
	public List<WdtTTaskItem> findUnCompletedTaskItems(WdtTTaskItem searchTaskItem);

	public List<WdtTTaskItem> findByConditions(@Param("taskId") String taskId, @Param("ownerId")String ownerId,
											   @Param("list")List<String> list,@Param("now")Date now);
	
	public List<String> getOwnerIdsByTaskId(@Param("taskId") String taskId);
}