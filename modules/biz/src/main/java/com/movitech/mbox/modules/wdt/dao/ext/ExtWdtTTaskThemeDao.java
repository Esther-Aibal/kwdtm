package com.movitech.mbox.modules.wdt.dao.ext;

import java.util.List;

import com.movitech.mbox.common.persistence.annotation.MyBatisDao;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskTheme;

/**
 * 
 * @author felix.jin
 * 2017年9月4日
 */
@MyBatisDao
public interface ExtWdtTTaskThemeDao {


	List<WdtTTaskTheme> findAll();
	
	

}
