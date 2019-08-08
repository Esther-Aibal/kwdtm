/**
 * 
 */
package com.movitech.mbox.modules.wdt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.movitech.mbox.common.persistence.CrudDao;
import com.movitech.mbox.common.persistence.annotation.MyBatisDao;
import com.movitech.mbox.modules.wdt.entity.WdtTFileInfo;

/**
 * 附件DAO接口
 * @author Aibal.He
 * @version 2017-08-09
 */
@MyBatisDao
public interface WdtTFileInfoDao extends CrudDao<WdtTFileInfo> {

	/**
	 * Jack.Gong
	 * @param searchFileInfo
	 * @return
	 */
	public List<WdtTFileInfo> findAllFileListForTask(WdtTFileInfo searchFileInfo);
	 int deleteIds(@Param("ids")List<String> ids);
	public WdtTFileInfo getFileByPath(String path);
}