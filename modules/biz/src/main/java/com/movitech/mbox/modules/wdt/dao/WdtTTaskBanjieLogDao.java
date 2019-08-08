/**
 * 
 */
package com.movitech.mbox.modules.wdt.dao;

import com.movitech.mbox.common.persistence.CrudDao;
import com.movitech.mbox.common.persistence.annotation.MyBatisDao;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskBanjieLog;

/**
 * 任务办结DAO接口
 * @author Jack.Gong
 * @version 2017-08-11
 */
@MyBatisDao
public interface WdtTTaskBanjieLogDao extends CrudDao<WdtTTaskBanjieLog> {
    
}