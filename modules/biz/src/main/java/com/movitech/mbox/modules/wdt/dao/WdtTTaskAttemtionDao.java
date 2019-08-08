/**
 * 
 */
package com.movitech.mbox.modules.wdt.dao;

import com.movitech.mbox.common.persistence.CrudDao;
import com.movitech.mbox.common.persistence.annotation.MyBatisDao;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskAttemtion;

/**
 * 关注任务DAO接口
 * @author Jack.Gong
 * @version 2017-08-17
 */
@MyBatisDao
public interface WdtTTaskAttemtionDao extends CrudDao<WdtTTaskAttemtion> {

	public WdtTTaskAttemtion getFocusedTaskByTaskIdAndUserId(WdtTTaskAttemtion wdtTTaskAttemtion);
	
	public int cancelAttentionTask(WdtTTaskAttemtion wdtTTaskAttemtion);
    
}