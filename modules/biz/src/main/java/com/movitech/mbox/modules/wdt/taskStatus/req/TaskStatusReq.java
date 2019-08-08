package com.movitech.mbox.modules.wdt.taskStatus.req;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author 作者 felix.jin:
 * @version 创建时间：2017年8月9日 上午9:57:29 类说明 节点请求参数类
 */
public class TaskStatusReq {

	private String id;
	private String taskId;
	private String content;
	private String userId;
	private Date newTime;

	private String type;
	private String typeId;
	private Date startTime;
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getNewTime() {
		return newTime;
	}

	public void setNewTime(Date newTime) {
		this.newTime = newTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
}
