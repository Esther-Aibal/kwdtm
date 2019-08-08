/**
 * 
 */
package com.movitech.mbox.modules.wdt.dao;

import org.apache.ibatis.annotations.Param;

import com.movitech.mbox.common.persistence.CrudDao;
import com.movitech.mbox.common.persistence.annotation.MyBatisDao;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskReadCount;

/**
 * 阅读数DAO接口
 * @author Aibal.He
 * @version 2017-08-25
 */
@MyBatisDao
public interface WdtTTaskReadCountDao extends CrudDao<WdtTTaskReadCount> {
	int updateReadCountByTaskId(@Param("taskId")String taskId);
}