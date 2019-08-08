/**
 * 
 */
package com.movitech.mbox.modules.wdt.dao.report;


import java.util.List;

import com.movitech.mbox.common.persistence.annotation.MyBatisDao;
import com.movitech.mbox.modules.wdt.entity.report.ActDoCompare;
import com.movitech.mbox.modules.wdt.entity.report.DelayTask;
import com.movitech.mbox.modules.wdt.entity.report.DelayTaskItem;
import com.movitech.mbox.modules.wdt.entity.report.OptLog;

@MyBatisDao
public interface DelayReportDao {
	List<DelayTask> findTaskList(DelayTask delayTask);
	DelayTask getTask(DelayTask delayTask);
	List<DelayTaskItem> getTaskItemList(DelayTask delayTask);
	Integer getActualCount(DelayTaskItem delayTaskItem);
	List<OptLog> getOptLogList(OptLog optLog);
	Integer getActualCountBySpecial(DelayTaskItem delayTaskItem);
	List<DelayTaskItem> getAllIngTaskItemList(DelayTaskItem delayTaskItem);
	Integer getActualCurrentCount(DelayTaskItem delayTaskItem);
	List<DelayTaskItem> getAllOverTodayTaskItemList(DelayTaskItem delayTaskItem);
}