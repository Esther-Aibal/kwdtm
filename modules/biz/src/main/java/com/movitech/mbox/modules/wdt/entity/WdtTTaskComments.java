/**
 * 
 */
package com.movitech.mbox.modules.wdt.entity;

import java.util.List;

import org.hibernate.validator.constraints.Length;
import com.movitech.mbox.modules.sys.entity.User;

import com.movitech.mbox.common.persistence.DataEntity;

/**
 * 进度汇报Entity
 * @author Aibal.He
 * @version 2017-08-07
 */
public class WdtTTaskComments extends DataEntity<WdtTTaskComments> {
    
    private static final long serialVersionUID = 1L;
    private String type;        // 任务层级（枚举维护： 0：主任务 1：节点）
    private String taskId;        // 任务主键
    private String taskItemId;        // 节点主键
    private User user;        // 汇报人id
    private String content;        // 汇报内容
    private String schedule;        // 汇报进度
    private List<String> atPerson;//被@的人员
    public WdtTTaskComments() {
        super();
    }

    public WdtTTaskComments(String id){
        super(id);
    }

    @Length(min=0, max=10, message="任务层级（枚举维护： 0：主任务 1：节点）长度必须介于 0 和 10 之间")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    @Length(min=0, max=64, message="任务主键长度必须介于 0 和 64 之间")
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    
    @Length(min=0, max=64, message="节点主键长度必须介于 0 和 64 之间")
    public String getTaskItemId() {
        return taskItemId;
    }

    public void setTaskItemId(String taskItemId) {
        this.taskItemId = taskItemId;
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    @Length(min=0, max=11, message="汇报进度长度必须介于 0 和 11 之间")
    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

	public List<String> getAtPerson() {
		return atPerson;
	}

	public void setAtPerson(List<String> atPerson) {
		this.atPerson = atPerson;
	}
    
}