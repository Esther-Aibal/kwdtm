/**
 * 
 */
package com.movitech.mbox.modules.wdt.dao.ext;


import java.util.List;

import com.movitech.mbox.common.persistence.annotation.MyBatisDao;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskComments;
import com.movitech.mbox.modules.wdt.entity.ext.ExtWdtTTaskComments;

/**
 *任务干系人DAO补充
 * @author felix.jin
 * 2017年8月8日
 */
@MyBatisDao
public interface ExtWdtTTaskCommentsDao {

	List<ExtWdtTTaskComments> findList(WdtTTaskComments wdtTTaskComments);

	void deleteItemMessage(String id);
	
	int findPinNo(WdtTTaskComments wdtTTaskComments);

}