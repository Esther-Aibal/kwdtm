/**
 * 
 */
package com.movitech.mbox.modules.wdt.entity.ext;

import java.util.List;

/**
 * 任务或节点回复Entity
 * @author Aibal.He
 * @version 2017-08-22
 */
public class AtMessage {
    private String content;        // 回复内容
    private String taskId;        // 任务id
    private String taskItemId;        // 任务节点id
    private List<String> atPerson;//被@的人员
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public List<String> getAtPerson() {
		return atPerson;
	}
	public void setAtPerson(List<String> atPerson) {
		this.atPerson = atPerson;
	}

   
    
}