/**
 * 
 */
package com.movitech.mbox.modules.wdt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.movitech.mbox.common.persistence.CrudDao;
import com.movitech.mbox.common.persistence.annotation.MyBatisDao;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskComments;

/**
 * 进度汇报DAO接口
 * @author Aibal.He
 * @version 2017-08-07
 */
@MyBatisDao
public interface WdtTTaskCommentsDao extends CrudDao<WdtTTaskComments> {
	String getNewCommentsByTaskId(@Param("taskId") String taskId);
	WdtTTaskComments getNewCommentsByTaskItemId(@Param("taskItemId") String taskItemId);
}