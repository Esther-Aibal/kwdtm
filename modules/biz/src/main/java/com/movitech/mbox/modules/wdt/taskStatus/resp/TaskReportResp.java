package com.movitech.mbox.modules.wdt.taskStatus.resp;

import java.util.List;



/**
 * @author 作者 felix.jin:
 * @version 创建时间：2017年9月5日 下午3:05:05 类说明
 */
public class TaskReportResp {

	private List<TaskReportRow> rows;
	
	private int allFinish;
	private int allOther;
	
	private int monthFinish;
	private int monthOther;
	
	private int weekFinish;
	private int weekOther;
	
	
	public List<TaskReportRow> getRows() {
		return rows;
	}

	public void setRows(List<TaskReportRow> rows) {
		this.rows = rows;
	}

	public int getAllFinish() {
		return allFinish;
	}

	public void setAllFinish(int allFinish) {
		this.allFinish = allFinish;
	}

	public int getAllOther() {
		return allOther;
	}

	public void setAllOther(int allOther) {
		this.allOther = allOther;
	}

	public int getMonthFinish() {
		return monthFinish;
	}

	public void setMonthFinish(int monthFinish) {
		this.monthFinish = monthFinish;
	}

	public int getMonthOther() {
		return monthOther;
	}

	public void setMonthOther(int monthOther) {
		this.monthOther = monthOther;
	}

	public int getWeekFinish() {
		return weekFinish;
	}

	public void setWeekFinish(int weekFinish) {
		this.weekFinish = weekFinish;
	}

	public int getWeekOther() {
		return weekOther;
	}

	public void setWeekOther(int weekOther) {
		this.weekOther = weekOther;
	}
	
	
	
	

}
