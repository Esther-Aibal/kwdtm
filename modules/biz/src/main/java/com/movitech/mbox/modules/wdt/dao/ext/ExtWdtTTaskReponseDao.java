package com.movitech.mbox.modules.wdt.dao.ext;

import java.util.List;

import com.movitech.mbox.common.persistence.annotation.MyBatisDao;
import com.movitech.mbox.modules.wdt.entity.ext.ExtWdtTTaskReponse;

/**
 * 
 * @author Jack.Gong
 *
 */
@MyBatisDao
public interface ExtWdtTTaskReponseDao {
	
	public List<ExtWdtTTaskReponse> getResponseTOP10(String userId);

}
