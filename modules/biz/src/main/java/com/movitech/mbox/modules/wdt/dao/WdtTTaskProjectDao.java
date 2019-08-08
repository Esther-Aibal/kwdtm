/**
 * 
 */
package com.movitech.mbox.modules.wdt.dao;

import java.util.List;

import com.movitech.mbox.common.persistence.CrudDao;
import com.movitech.mbox.common.persistence.annotation.MyBatisDao;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskProject;

/**
 * 任务项目关联DAO接口
 * @author Jack.Gong
 * @version 2017-08-09
 */
@MyBatisDao
public interface WdtTTaskProjectDao extends CrudDao<WdtTTaskProject> {

	public List<WdtTTaskProject> retrieveTaskProjectByTaskId(String taskId);
    
}