package com.movitech.mbox.modules.wdt.dao;


import com.movitech.mbox.common.persistence.CrudDao;
import com.movitech.mbox.common.persistence.annotation.MyBatisDao;
import com.movitech.mbox.modules.wdt.entity.ProjectInfo;

import java.util.List;

@MyBatisDao
public interface ProjectInfoDao extends CrudDao<ProjectInfo>{
   public int batchInsert(List<ProjectInfo> list);

   public int deleteAll();
}