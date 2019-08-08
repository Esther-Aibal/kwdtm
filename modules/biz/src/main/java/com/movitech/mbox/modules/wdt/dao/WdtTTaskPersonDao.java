/**
 * 
 */
package com.movitech.mbox.modules.wdt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.movitech.mbox.common.persistence.CrudDao;
import com.movitech.mbox.common.persistence.annotation.MyBatisDao;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskPerson;

/**
 * 任务干系人DAO接口
 * @author Aibal.He
 * @version 2017-08-07
 */
@MyBatisDao
public interface WdtTTaskPersonDao extends CrudDao<WdtTTaskPerson> {
	List<String> findIds(WdtTTaskPerson wdtTTaskPerson);
	List<String> getBluePersonIds(@Param("taskId")String taskId);
	int deleteBy(WdtTTaskPerson wdtTTaskPerson);
} 