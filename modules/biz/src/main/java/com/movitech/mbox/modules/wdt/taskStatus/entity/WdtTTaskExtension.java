/**
 * 
 */
package com.movitech.mbox.modules.wdt.taskStatus.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.movitech.mbox.common.persistence.DataEntity;

/**
 * 任务延期Entity
 * @author Aibal.He
 * @version 2017-08-28
 */
public class WdtTTaskExtension extends DataEntity<WdtTTaskExtension> {
    
    private static final long serialVersionUID = 1L;
    private String type;        // 任务层级（枚举维护： 0：主任务 1：节点）
    private String taskId;        // 任务主键
    private String taskItemId;        // 节点主键
    private Date oldCompletionTime;        // 原要求完成时间
    private Date newCompletionTime;        // 新完成时间
    private String content;        // 延期说明
    private String status;        // 最新状态: （枚举维护：0：待审批 1：审批通过 2：审批不通过）
    private String createrId;        // 发起延期信息的用户的id
    private String approverId;        // 审批该延期信息的人员
    private Date approveTime;        // 审批时间
    private String approveRemark;        // 审批意见
    
    public WdtTTaskExtension() {
        super();
    }

    public WdtTTaskExtension(String id){
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
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getOldCompletionTime() {
        return oldCompletionTime;
    }

    public void setOldCompletionTime(Date oldCompletionTime) {
        this.oldCompletionTime = oldCompletionTime;
    }
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getNewCompletionTime() {
        return newCompletionTime;
    }

    public void setNewCompletionTime(Date newCompletionTime) {
        this.newCompletionTime = newCompletionTime;
    }
    
    @Length(min=0, max=512, message="延期说明长度必须介于 0 和 512 之间")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    @Length(min=0, max=10,message="最新状态: （枚举维护：0：待审批 1：审批通过 2：审批不通过）长度必须介于 0 和 10 之间")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    @Length(min=0, max=64, message="发起延期信息的用户的id长度必须介于 0 和 64 之间")
    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId;
    }
    
    @Length(min=0, max=64, message="审批该延期信息的人员长度必须介于 0 和 64 之间")
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
    
}