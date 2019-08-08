/**
 * 
 */
package com.movitech.mbox.modules.wdt.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;


import com.movitech.mbox.common.persistence.Page;
import com.movitech.mbox.modules.wdt.entity.WdtTaskH5Vo;
import com.movitech.mbox.modules.wdt.entity.WdtTaskParam;
import com.movitech.mbox.modules.wdt.entity.WdtTaskVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.movitech.mbox.common.persistence.CrudDao;
import com.movitech.mbox.common.persistence.annotation.MyBatisDao;
import com.movitech.mbox.modules.wdt.entity.WdtTTask;
import com.movitech.mbox.modules.wdt.entity.WdtTaskParam;
import com.movitech.mbox.modules.wdt.entity.WdtTaskVo;
import com.movitech.mbox.modules.wdt.entity.ext.TaskH5;

/**
 * 任务DAO接口
 * @author Aibal.He
 * @version 2017-08-07
 */
@MyBatisDao
public interface WdtTTaskDao extends CrudDao<WdtTTask> {
	
	/**
	 * Jack.Gong
	 * @param wdtTTask
	 * @return
	 */
	@Update("update wdt_t_task set execution_status = #{executionStatus} where id = #{id}")
	int updateTask(WdtTTask wdtTTask);
	
	int insertTaskPersons(WdtTTask wdtTTask);
	
	int removeTaskPersonsNoType3(WdtTTask wdtTTask);
	/**
	 * 删除persontype=3 的子任务参与者
	 * @param wdtTTask
	 * @return
	 */
	int removeTaskPersonsType3(WdtTTask wdtTTask);

	/**
	 * Jack.Gong
	 * @param taskId
	 * @return
	 */
	public WdtTTask getTask(String taskId);
	
	List<String> selectUnUsedFile(WdtTTask wdtTTask);
	
	int updateUsedFiles(WdtTTask wdtTTask);

	/**
	 * 条件查询
	 * @param ownerId 主责
	 * @param createId 发起
	 * @param userId 我关注
	 * @param joinId 我参与
	 * @return
	 */
	public List<WdtTaskVo> getByOwnerOrCreateId(WdtTaskParam page);

	/**
	 * 查询延期任务
	 * @param userId
	 * @return
	 */
	public List<WdtTaskVo> getByDueTask(WdtTaskParam page);

	/**
	 * 查询即将过期任务
	 * @param userId
	 * @return
	 */
	public List<WdtTaskVo> getBySoonDueTask(WdtTaskParam page);

    public List<WdtTTask>  findListByExecutionStatus(@Param("executionStatuslist") List<String> executionStatuslist,@Param("now") Date now);
	/**
	 * 查询预警的任务
	 * @param userId
	 * @return
	 */
	public List<WdtTTask> getByWarningTask(@Param("now")Date now);

	/**
	 * 查询即将过期任务
	 * @param userId
	 * @return
	 */
	public List<WdtTaskVo> findMessageBytype(WdtTaskParam page);

	public int findUnReadMessageBytype(WdtTaskParam wdtTaskParam);
	
	public int deleteById(@Param("id")String id);

	// <!--查看汇报记录数量-->

	public int getTaskCommentAmount(@Param("itemId")String itemId,@Param("startTime") Date startTime,@Param("endTime") Date endTime);
    	/**
	 * 获取对应主任务的子任务
	 * @param wdtTTask
	 * @return
	 */
	public List<WdtTTask> findListForChild(WdtTTask wdtTTask);
	
	public List<WdtTaskVo> getWaitingTaskForOAHome(WdtTaskParam wdtTaskParam);    
	public List<WdtTTask> getTaskByUserIdOrOwerId(@Param("userId") String userId,@Param("id")String id);

	/**
	 * 
	 * 查询各任务的总数
	 * @param ownerId 主责
	 * @param createId 发起
	 * @param userId 我关注
	 * @param joinId 我参与
	 * @return
	 */
	public int getCountsByOwnerOrCreateId(WdtTaskParam ownerParm);

	/**
	 * 查询延期任务的总数
	 * @param wdtTaskParam
	 * @return
	 */
	public int getCountsByDueTask(WdtTaskParam wdtTaskParam);

	/**
	 * 查询即将过期任务的总数
	 * @param wdtTaskParam
	 * @return
	 */
	public int getCountsBySoonDueTask(WdtTaskParam wdtTaskParam);
	
	/**
	 * 查询提醒任务的数量
	 * @param wdtTaskParam
	 * @return
	 */
	public int getMessageCounts(WdtTaskParam wdtTaskParam);

	/**
	 * 获取子任务
	 * @param wdtTTask
	 * @return
	 */
	public List<WdtTTask> getSubTaskListByTaskIdAndStatus(WdtTTask wdtTTask);
	
	public WdtTTask getByChildId(@Param("id")String id );
	
	void updatePart(WdtTTask wdtTTask);

	public void batchUpdateDueStatus(@Param("exestatus") String exestatus,@Param("list")List<String> list);
	
	public List<TaskH5> findH5List(TaskH5 taskH5);

	/**
	 * 查询消息forH5
	 */
	public List<WdtTaskH5Vo> getWaitingTaskForH5(WdtTaskParam wdtTaskParam);



	/**
	 * 查询消息
	 * @param
	 * @return
	 */
	public List<Map<String,Object>> findMessageMapH5(WdtTaskParam page);

	public List<WdtTaskH5Vo> findMessageBytypeH5(WdtTaskParam page);
	
	public int getTaskChildCountByTaskId(@Param("taskId")String taskId);

}