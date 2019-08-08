package com.movitech.mbox.modules.wdt.taskStatus.dao;

import org.apache.ibatis.annotations.Param;

import com.movitech.mbox.common.persistence.CrudDao;
import com.movitech.mbox.common.persistence.annotation.MyBatisDao;
import com.movitech.mbox.modules.wdt.taskStatus.entity.WdtTTaskExtension;

@MyBatisDao
public interface ExtTaskExtensionDao extends CrudDao<WdtTTaskExtension> {
    
	WdtTTaskExtension getNewBytaskId(@Param("taskId") String taskId,@Param("type") String type);
}
