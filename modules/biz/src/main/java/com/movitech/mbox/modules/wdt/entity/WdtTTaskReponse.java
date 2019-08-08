/**
 * 
 */
package com.movitech.mbox.modules.wdt.entity;

import java.util.List;

import org.hibernate.validator.constraints.Length;
import com.movitech.mbox.modules.sys.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;

import com.movitech.mbox.common.persistence.DataEntity;

/**
 * 任务或节点回复Entity
 * @author Aibal.He
 * @version 2017-08-22
 */
public class WdtTTaskReponse extends DataEntity<WdtTTaskReponse> {
    
    private static final long serialVersionUID = 1L;
    private String comentsId;        // 进度反馈与评论表主键
    private User user;        // 回复人id
    private String content;        // 回复内容
    private String parentId;        // 父节点id
    private String byUserId;        // 被回复人
    private String taskId;        // 任务id
    private String taskItemId;        // 任务节点id
    private List<String> atPerson;//被@的人员
    private String contentChar;
    public WdtTTaskReponse() {
        super();
    }

    public WdtTTaskReponse(String id){
        super(id);
    }

    @Length(min=0, max=64, message="进度反馈与评论表主键长度必须介于 0 和 64 之间")
    public String getComentsId() {
        return comentsId;
    }

    public void setComentsId(String comentsId) {
        this.comentsId = comentsId;
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
    public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Length(min=0, max=64, message="被回复人长度必须介于 0 和 64 之间")
    public String getByUserId() {
        return byUserId;
    }

    public void setByUserId(String byUserId) {
        this.byUserId = byUserId;
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

	public String getContentChar() {
		return contentChar;
	}

	public void setContentChar(String contentChar) {
		this.contentChar = contentChar;
	}
    
}