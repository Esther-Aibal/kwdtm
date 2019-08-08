package com.movitech.mbox.modules.wdt.operateLog.dao.ext;

import com.movitech.mbox.common.persistence.annotation.MyBatisDao;
import com.movitech.mbox.modules.wdt.operateLog.entity.OperateLog;
import com.movitech.mbox.modules.wdt.req.TaskItemReq;

import java.util.List;

@MyBatisDao
public interface OperateLogMapperExt {

	List<OperateLog> getList(TaskItemReq req);
    
}