/**
 * 
 */
package com.movitech.mbox.modules.wdt.service;

import com.alibaba.druid.util.StringUtils;
import com.movitech.mbox.common.config.ErrorMessage;
import com.movitech.mbox.common.config.ExecutionStatusEnum;
import com.movitech.mbox.common.config.PersonTypeEnum;
import com.movitech.mbox.common.config.YesNoEnum;
import com.movitech.mbox.common.persistence.Page;
import com.movitech.mbox.common.service.CrudService;
import com.movitech.mbox.common.utils.DateUtils;
import com.movitech.mbox.common.utils.IdGen;
import com.movitech.mbox.modules.sys.entity.User;
import com.movitech.mbox.modules.sys.utils.UserUtils;
import com.movitech.mbox.modules.wdt.dao.*;
import com.movitech.mbox.modules.wdt.dao.ext.ExtWdtTTaskItemDao;
import com.movitech.mbox.modules.wdt.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 任务Service
 * @author gorden
 * @version 2017-08-07
 */
@Service
@Transactional(readOnly = false)
public class WdtTTaskWarningService  {
    @Autowired
	WarningDao warningDao;
    public int save(Warning warning){
      return warningDao.insert(warning);
    }
    public Warning get(String id){
    	return warningDao.get(id);
	}
	public int update(Warning warning){
		return warningDao.update(warning);
	}
	public List<Warning> getWarningListByUser(WdtWaringParam page){
    	return  warningDao.getWarningListByUser(page);
	}
	public List<Warning> getBytaskId(String taskId){
		return warningDao.getBytaskId(taskId);
	}
}