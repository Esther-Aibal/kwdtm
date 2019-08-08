package com.movitech.mbox.modules.wdt.taskStatus.resp;



/**
 * @author 作者 felix.jin:
 * @version 创建时间：2017年9月5日 下午3:05:05 类说明
 */
public class TaskReportRow {

	private String userId;
	
	private String userName;
	
	private String officeId;
	
	private String officeName;
	
	private TaskReport all;
	
	private TaskReport month;
	
	private TaskReport week;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public TaskReport getAll() {
		return all;
	}

	public void setAll(TaskReport all) {
		this.all = all;
	}

	public TaskReport getMonth() {
		return month;
	}

	public void setMonth(TaskReport month) {
		this.month = month;
	}

	public TaskReport getWeek() {
		return week;
	}

	public void setWeek(TaskReport week) {
		this.week = week;
	}
	
	
	
	

}
