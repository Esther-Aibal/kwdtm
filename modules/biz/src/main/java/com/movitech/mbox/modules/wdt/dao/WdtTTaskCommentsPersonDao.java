/**
 * 
 */
package com.movitech.mbox.modules.wdt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.movitech.mbox.common.persistence.CrudDao;
import com.movitech.mbox.common.persistence.annotation.MyBatisDao;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskCommentsPerson;

/**
 * 进度@人员DAO接口
 * @author Aibal.He
 * @version 2017-10-27
 */
@MyBatisDao
public interface WdtTTaskCommentsPersonDao extends CrudDao<WdtTTaskCommentsPerson> {
	void batchInsert(@Param("list")List<WdtTTaskCommentsPerson> list);
	void deleteByComentOrResponceId(@Param("comentOrResponceId")String comentOrResponceId);
	List<WdtTTaskCommentsPerson> getAtPersonByCOrRId(@Param("comentsId")String comentsId);
}