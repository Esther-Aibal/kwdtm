package com.movitech.mbox.modules.wdt.req;
/** 
 * @author 作者 felix.jin: 
 * @version 创建时间：2017年8月9日 上午9:57:29 
 * 类说明 节点请求参数类
 */
public class TaskItemReq extends BaseReq{

	
	private String userId;
	
	private String taskId;
	
	private String taskItemId;
	
	private String content;

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
