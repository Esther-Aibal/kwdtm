/**
 * 
 */
package com.movitech.mbox.modules.com.dao;

import com.movitech.mbox.common.persistence.CrudDao;
import com.movitech.mbox.common.persistence.annotation.MyBatisDao;
import com.movitech.mbox.modules.com.entity.ComDocument;

/**
 * 文章DAO接口
 * @author Aibal.He
 * @version 2017-06-23
 */
@MyBatisDao
public interface ComDocumentDao extends CrudDao<ComDocument> {
    
}