package com.movitech.mbox.modules.wdt.req;
/** 
 * @author 作者 felix.jin: 
 * @version 创建时间：2017年8月9日 上午9:57:29 
 * 类说明 节点回复请求参数类
 */
public class TaskCommentReq extends BaseReq{

	
	private String userId;
	
	private String taskId;
	
	private String taskItemId;
	
	private String type;
	
	private String pinId;
	
	private String messageType;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskItemId() {
		return taskItemId;
	}

	public void setTaskItemId(String taskItemId) {
		this.taskItemId = taskItemId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPinId() {
		return pinId;
	}

	public void setPinId(String pinId) {
		this.pinId = pinId;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	
	
}
