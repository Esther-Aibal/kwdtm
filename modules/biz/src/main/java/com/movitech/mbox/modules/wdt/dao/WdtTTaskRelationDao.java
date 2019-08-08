/**
 * 
 */
package com.movitech.mbox.modules.wdt.dao;

import com.movitech.mbox.common.persistence.CrudDao;
import com.movitech.mbox.common.persistence.annotation.MyBatisDao;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskRelation;

/**
 * 子任务关联DAO接口
 * @author Aibal.He
 * @version 2017-08-21
 */
@MyBatisDao
public interface WdtTTaskRelationDao extends CrudDao<WdtTTaskRelation> {
    
}