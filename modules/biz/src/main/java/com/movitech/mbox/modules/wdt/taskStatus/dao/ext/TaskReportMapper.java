package com.movitech.mbox.modules.wdt.taskStatus.dao.ext;


import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.movitech.mbox.common.persistence.annotation.MyBatisDao;
import com.movitech.mbox.modules.wdt.taskStatus.req.TaskReportReq;
import com.movitech.mbox.modules.wdt.taskStatus.resp.TaskReportRow;



@MyBatisDao
public interface TaskReportMapper {


	List<TaskReportRow> getReportToTal(TaskReportReq req);

	List<Map<String,Integer>> getReportDetail(@Param("userId")String userId,@Param("startDay") Date startDay,@Param("endDay") Date endDay);

    
}