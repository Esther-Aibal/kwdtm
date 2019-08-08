/**
 * 
 */
package com.movitech.mbox.modules.wdt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.movitech.mbox.common.persistence.CrudDao;
import com.movitech.mbox.common.persistence.annotation.MyBatisDao;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskRemind;

/**
 * 自定义提醒DAO接口
 * @author Aibal.He
 * @version 2018-01-24
 */
@MyBatisDao
public interface WdtTTaskRemindDao extends CrudDao<WdtTTaskRemind> {
	int doneRemind(WdtTTaskRemind wdtTTaskRemind);
	
	List<WdtTTaskRemind> getUndoRemindkForCalendar(@Param("userId") String userId,@Param("endDate")String endDate);
}