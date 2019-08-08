package com.movitech.mbox.modules.wdt.taskStatus.req;



/**
 * @author 作者 felix.jin:
 * @version 创建时间：2017年8月9日 上午9:57:29 类说明 节点请求参数类
 */
public class TaskBackReq {

	private String taskId;
	private String type;
	private String typeId;
	private String approve_status;
	private String token;
	private String approver_id;
	private String approve_time;
	private String approve_remark;
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}



	public String getApprove_status() {
		return approve_status;
	}

	public void setApprove_status(String approve_status) {
		this.approve_status = approve_status;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}



	public String getApprover_id() {
		return approver_id;
	}

	public void setApprover_id(String approver_id) {
		this.approver_id = approver_id;
	}

	public String getApprove_time() {
		return approve_time;
	}

	public void setApprove_time(String approve_time) {
		this.approve_time = approve_time;
	}

	public String getApprove_remark() {
		return approve_remark;
	}

	public void setApprove_remark(String approve_remark) {
		this.approve_remark = approve_remark;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
}
