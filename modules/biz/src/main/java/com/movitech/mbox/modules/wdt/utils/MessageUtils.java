package com.movitech.mbox.modules.wdt.utils;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.movitech.mbox.common.utils.IdGen;
import com.movitech.mbox.common.utils.SpringContextHolder;
import com.movitech.mbox.modules.sys.entity.User;
import com.movitech.mbox.modules.sys.utils.UserUtils;
import com.movitech.mbox.modules.wdt.dao.MessageDao;
import com.movitech.mbox.modules.wdt.dao.WdtTTaskDao;
import com.movitech.mbox.modules.wdt.entity.Message;
import com.movitech.mbox.modules.wdt.entity.WdtTaskParam;

/**
 * 
 * @author Jack.Gong
 * @version 2017.08.24
 *
 */
public class MessageUtils extends UserUtils{
	
	private static WdtTTaskDao wdtTTaskDao = SpringContextHolder.getBean(WdtTTaskDao.class);
	public static int getMessageCounts(){
		User user = getUser();
		
		WdtTaskParam param = new WdtTaskParam();
		param.setUserId(user.getId());
		
		return wdtTTaskDao.getMessageCounts(param);
	}	
}
