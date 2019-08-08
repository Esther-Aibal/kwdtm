/**
 * 
 */
package com.movitech.mbox.modules.wdt.dao;

import org.apache.ibatis.annotations.Param;

import com.movitech.mbox.common.persistence.CrudDao;
import com.movitech.mbox.common.persistence.annotation.MyBatisDao;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskContent;

/**
 * 任务内容内容DAO接口
 * @author Aibal.He
 * @version 2017-08-18
 */
@MyBatisDao
public interface WdtTTaskContentDao extends CrudDao<WdtTTaskContent> {
	
	WdtTTaskContent getUsedContent(WdtTTaskContent wdtTTaskContent);
	
	void deleteOld(@Param("taskId")String taskId);
}