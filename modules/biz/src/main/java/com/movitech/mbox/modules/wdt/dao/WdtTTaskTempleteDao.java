/**
 * 
 */
package com.movitech.mbox.modules.wdt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.movitech.mbox.common.persistence.CrudDao;
import com.movitech.mbox.common.persistence.annotation.MyBatisDao;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskTemplete;

/**
 * 任务内容模板DAO接口
 * @author Aibal.He
 * @version 2017-08-18
 */
@MyBatisDao
public interface WdtTTaskTempleteDao extends CrudDao<WdtTTaskTemplete> {
	List<WdtTTaskTemplete> getTempletesAndContent(@Param("taskId")String taskId);
	int getCountAboutSort(WdtTTaskTemplete wdtTTaskTemplete);
}