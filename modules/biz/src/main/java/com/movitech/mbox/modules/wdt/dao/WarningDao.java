package com.movitech.mbox.modules.wdt.dao;

import com.movitech.mbox.common.persistence.CrudDao;
import com.movitech.mbox.common.persistence.annotation.MyBatisDao;
import com.movitech.mbox.modules.wdt.entity.Warning;
import com.movitech.mbox.modules.wdt.entity.WdtWaringParam;

import java.util.List;

@MyBatisDao
public interface WarningDao extends CrudDao<Warning>{
   public List<Warning> getWarningListByUser(WdtWaringParam page);
   public List<Warning> getBytaskId(String taskId);
}