package com.movitech.mbox.modules.wdt.taskStatus.entity;

import java.util.Date;

/** 
 * @author 作者 felix.jin: 
 * @version 创建时间：2017年8月30日 下午9:02:21 
 * 类说明 
 */
public class TaskOa {

	private Date createDate;
	
	private String processId;

	private String userId;
	
	private String name;
	
	private int status;
	
	private int type;

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
