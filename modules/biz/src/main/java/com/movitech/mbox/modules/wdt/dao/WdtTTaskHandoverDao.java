/**
 * 
 */
package com.movitech.mbox.modules.wdt.dao;

import org.apache.ibatis.annotations.Param;

import com.movitech.mbox.common.persistence.CrudDao;
import com.movitech.mbox.common.persistence.annotation.MyBatisDao;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskHandover;

/**
 * 任务移交DAO接口
 * @author felix
 * @version 2017-08-09
 */
@MyBatisDao
public interface WdtTTaskHandoverDao extends CrudDao<WdtTTaskHandover> {
    
	WdtTTaskHandover getNewHandOverByTaskId(@Param("taskId")String taskId,@Param("type") String type);
	
	int deleteBy(@Param("id")String id);
}