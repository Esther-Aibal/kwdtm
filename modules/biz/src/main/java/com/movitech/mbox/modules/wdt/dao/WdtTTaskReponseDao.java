/**
 * 
 */
package com.movitech.mbox.modules.wdt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.movitech.mbox.common.persistence.CrudDao;
import com.movitech.mbox.common.persistence.annotation.MyBatisDao;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskReponse;
import com.movitech.mbox.modules.wdt.entity.ext.WdtTTaskReponseTreeNode;

/**
 * 任务或节点回复DAO接口
 * @author Aibal.He
 * @version 2017-08-22
 */
@MyBatisDao
public interface WdtTTaskReponseDao extends CrudDao<WdtTTaskReponse> {
	List<WdtTTaskReponseTreeNode> findByCommentsId(@Param("comentsId")String comentsId);
	void deleteByCommentsId(@Param("comentsId")String comentsId);
	List<String> getIdsByCommentsId(@Param("comentsId")String comentsId);
	public int getResponseCountsBytaskIdOrItemId(@Param("taskId")String taskId,@Param("taskItemId")String taskItemId,@Param("type")String type,@Param("userId")String userId,@Param("pinId")String pinId);
	List<String> getIdsByReponseId(@Param("reponseId")String reponseId);
	List<WdtTTaskReponseTreeNode> findByResponseId(@Param("responseId")String responseId);
	WdtTTaskReponseTreeNode getByResponseId(@Param("responseId")String responseId);
}