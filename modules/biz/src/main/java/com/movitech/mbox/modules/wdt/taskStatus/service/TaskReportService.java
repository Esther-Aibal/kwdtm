/**
 * 
 */
package com.movitech.mbox.modules.wdt.taskStatus.service;




import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movitech.mbox.common.utils.DateReportUtils;
import com.movitech.mbox.modules.wdt.taskStatus.dao.ext.TaskReportMapper;
import com.movitech.mbox.modules.wdt.taskStatus.req.TaskReportReq;
import com.movitech.mbox.modules.wdt.taskStatus.resp.TaskReport;
import com.movitech.mbox.modules.wdt.taskStatus.resp.TaskReportResp;
import com.movitech.mbox.modules.wdt.taskStatus.resp.TaskReportRow;





/**
 * 任务报表
 * @author felix.jin
 * 2017年9月5日
 */
@Service
@Transactional(readOnly = true)
public class TaskReportService {
	
	@Autowired
	private TaskReportMapper taskReportMapper;

	/**
	 * 
	 * @param req
	 */
	public TaskReportResp getReport(TaskReportReq req) {
		int allFinish=0,allOther=0,monthFinish=0,monthOther=0,weekFinish=0,weekOther=0;
		Date now=new Date();
		//得到本月第一天
		Date monthstartDay=DateReportUtils.getMonthFirstDay(now);
		//得到本周第一天
		Date weekstartDay=DateReportUtils.getNearMonday(now);
		//获得搜索的截止时间
		if(req.getEndDay()==null){
			req.setEndDay(DateReportUtils.getDayEnd(now));
		}else{
			req.setEndDay(DateReportUtils.getDayEnd(req.getEndDay()));
		}
		//获得任务主责人的id,姓名，部门,部门id
		List<TaskReportRow> rows=taskReportMapper.getReportToTal(req);
		List<Map<String,Integer>> list=null;
		if(rows!=null&&rows.size()>0){
			for (TaskReportRow one : rows) {
				list=taskReportMapper.getReportDetail(one.getUserId(),null,req.getEndDay());
				TaskReport all=new TaskReport(list);
				allFinish+=all.getFinishInTime()+all.getFinishOutTime();
				allOther+=all.getTotal()-all.getFinishInTime()-all.getFinishOutTime();
				one.setAll(all);
				
				list=taskReportMapper.getReportDetail(one.getUserId(),monthstartDay,req.getEndDay());
				
				TaskReport month=new TaskReport(list);
				monthFinish+=month.getFinishInTime()+month.getFinishOutTime();
				monthOther+=month.getTotal()-month.getFinishInTime()-month.getFinishOutTime();
				one.setMonth(month);
				
				list=taskReportMapper.getReportDetail(one.getUserId(),weekstartDay,req.getEndDay());
				
				TaskReport week=new TaskReport(list);
				weekFinish+=week.getFinishInTime()+week.getFinishOutTime();
				weekOther+=week.getTotal()-week.getFinishInTime()-week.getFinishOutTime();
				one.setWeek(week);
			}
		}
		TaskReportResp resp=new TaskReportResp();
		resp.setRows(rows);
		resp.setAllFinish(allFinish);
		if(allFinish == 0){
			allOther =1;
		}
		resp.setAllOther(allOther);
		resp.setMonthFinish(monthFinish);
		if(monthFinish == 0){
			monthOther =1;
		}
		resp.setMonthOther(monthOther);
		resp.setWeekFinish(weekFinish);
		if(weekFinish == 0){
			weekOther =1;
		}
		resp.setWeekOther(weekOther);
		return resp;
	}
	

}