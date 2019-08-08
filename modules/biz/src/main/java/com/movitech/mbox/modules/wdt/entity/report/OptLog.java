package com.movitech.mbox.modules.wdt.entity.report;

import java.util.Date;

public class OptLog {
	private String content;
	private String logeType;
	private Date createDate;
	private String taskId;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getLogeType() {
		return logeType;
	}
	public void setLogeType(String logeType) {
		this.logeType = logeType;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	
	
}
