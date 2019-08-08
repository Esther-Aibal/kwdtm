/**
 * 
 */
package com.movitech.mbox.modules.wdt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.movitech.mbox.common.persistence.CrudDao;
import com.movitech.mbox.common.persistence.annotation.MyBatisDao;
import com.movitech.mbox.modules.sys.utils.TreeNode;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskTheme;

/**
 * 任务主题DAO接口
 * @author Aibal.He
 * @version 2017-08-18
 */
@MyBatisDao
public interface WdtTTaskThemeDao extends CrudDao<WdtTTaskTheme> {
	/**
	 * 所有可使用主题，用于组装成树
	 * @return
	 */
	List<TreeNode> findAllUsedTheme();
	
	/**
	 * 验证是否已被使用
	 * @param categoryId
	 * @return
	 */
	int checkInUsedCount(@Param("categoryId")String categoryId);
	
	WdtTTaskTheme getThemeByTaskId(String taskId);
}