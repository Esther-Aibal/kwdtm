/**
 * 
 */
package com.movitech.mbox.modules.wdt.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.movitech.mbox.common.persistence.DataEntity;

/**
 * 任务办结Entity
 * @author Jack.Gong
 * @version 2017-08-11
 */
public class WdtTTaskBanjieLog extends DataEntity<WdtTTaskBanjieLog> {
    
    private static final long serialVersionUID = 1L;
    private String type;        // 任务层级（枚举维护： 0：主任务 1：节点）
    private String taskId;        // 任务主键
    private String taskItemId;        // 节点主键
    private String content;        // 办结原因
    private String summary;        // 任务总结
    private String status;        // 最新状态: （枚举维护：0：待审批 1：审批通过 2：审批不通过）
    private String createrId;        // 发起办结信息的用户的id
    private String approverId;        // 审批该办结任务的人员
    private Date approveTime;        // 审批时间
    private String approveRemark;        // 审批意见
    
    private String creater; //发起人
    private String office; //请办部门
    private String taskName; //任务名称
    
    private String ipAddr;
    
    public WdtTTaskBanjieLog() {
        super();
    }

    public WdtTTaskBanjieLog(String id){
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
    
    @Length(min=0, max=512, message="办结原因长度必须介于 0 和 512 之间")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    @Length(min=0, max=512, message="任务总结长度必须介于 0 和 512 之间")
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
    
    @Length(min=0, max=10, message="最新状态: （枚举维护：0：待审批 1：审批通过 2：审批不通过）长度必须介于 0 和 10 之间")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    @Length(min=0, max=64, message="发起办结信息的用户的id长度必须介于 0 和 64 之间")
    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId;
    }
    
    @Length(min=0, max=64, message="审批该办结任务的人员长度必须介于 0 和 64 之间")
    public String getApproverId() {
        return approverId;
    }

    public void setApproverId(String approverId) {
        this.approverId = approverId;
    }
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }
    
    @Length(min=0, max=512, message="审批意见长度必须介于 0 和 512 之间")
    public String getApproveRemark() {
        return approveRemark;
    }

    public void setApproveRemark(String approveRemark) {
        this.approveRemark = approveRemark;
    }

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
    
}