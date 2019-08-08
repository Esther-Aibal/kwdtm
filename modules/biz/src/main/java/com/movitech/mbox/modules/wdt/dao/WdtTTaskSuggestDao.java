/**
 * 
 */
package com.movitech.mbox.modules.wdt.dao;

import com.movitech.mbox.common.persistence.CrudDao;
import com.movitech.mbox.common.persistence.annotation.MyBatisDao;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskSuggest;

/**
 * 意见收集DAO接口
 * @author Aibal.He
 * @version 2018-01-11
 */
@MyBatisDao
public interface WdtTTaskSuggestDao extends CrudDao<WdtTTaskSuggest> {
    
}