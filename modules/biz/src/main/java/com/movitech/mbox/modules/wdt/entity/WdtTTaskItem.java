/**
 * 
 */
package com.movitech.mbox.modules.wdt.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.movitech.mbox.common.persistence.DataEntity;

/**
 * 任务节点Entity
 * @author Aibal.He
 * @version 2017-08-08
 */
public class WdtTTaskItem extends DataEntity<WdtTTaskItem> {
    
    private static final long serialVersionUID = 1L;
    private String taskId;        // 任务主键
    private String itemName;        // 节点名称
    private String itemContent;        // 节点内容
    private Date startDate;        // 开始日期
    private Date requiredCompletionTime;        // 要求完成日期
    private String progress;        // 节点进度
    private Date infactCompletionTime;        // 实际完成日期
    private String ownerId;        // 任务主责人
    private String isApplyExtension;        // 是否申请过延期（枚举维护：0：申请过 1：未申请过延期）
    private String executionStatus;        // 执行状态（枚举维护：0：进行中 1：已完成）
    private String fqcy;        // 汇报频率（枚举维护：0：每日 1：每周 2：每月 3：每隔天）
    private Integer days;        // 天数
    private String orderIndex;        // 排序字段
    private String operatorId;        // 经办人

    private String owerName;//主责人名称
    private String taskName;

    private List<String> roleIds;
    public String getOwerName() {
        return owerName;
    }

    public void setOwerName(String owerName) {
        this.owerName = owerName;
    }

    public WdtTTaskItem() {
        super();
    }

    public WdtTTaskItem(String id){
        super(id);
    }

    @Length(min=0, max=64, message="任务主键长度必须介于 0 和 64 之间")
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    
    @Length(min=0, max=200, message="节点名称长度必须介于 0 和 200 之间")
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    
    public String getItemContent() {
        return itemContent;
    }

    public void setItemContent(String itemContent) {
        this.itemContent = itemContent;
    }
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getRequiredCompletionTime() {
        return requiredCompletionTime;
    }

    public void setRequiredCompletionTime(Date requiredCompletionTime) {
        this.requiredCompletionTime = requiredCompletionTime;
    }
    
    @Length(min=0, max=64, message="节点进度长度必须介于 0 和 64 之间")
    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getInfactCompletionTime() {
        return infactCompletionTime;
    }

    public void setInfactCompletionTime(Date infactCompletionTime) {
        this.infactCompletionTime = infactCompletionTime;
    }
    
    @Length(min=0, max=64, message="任务主责人长度必须介于 0 和 64 之间")
    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
    
    @Length(min=0, max=10, message="是否申请过延期（枚举维护：0：申请过 1：未申请过延期）长度必须介于 0 和 10 之间")
    public String getIsApplyExtension() {
        return isApplyExtension;
    }

    public void setIsApplyExtension(String isApplyExtension) {
        this.isApplyExtension = isApplyExtension;
    }
    
    @Length(min=0, max=10, message="执行状态（枚举维护：0：进行中 1：已完成）长度必须介于 0 和 10 之间")
    public String getExecutionStatus() {
        return executionStatus;
    }

    public void setExecutionStatus(String executionStatus) {
        this.executionStatus = executionStatus;
    }
    
    @Length(min=0, max=10, message="汇报频率（枚举维护：0：每日 1：每周 2：每月 3：每隔天）长度必须介于 0 和 10 之间")
    public String getFqcy() {
        return fqcy;
    }

    public void setFqcy(String fqcy) {
        this.fqcy = fqcy;
    }
    
    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }
    
    @Length(min=0, max=11, message="排序字段长度必须介于 0 和 11 之间")
    public String getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(String orderIndex) {
        this.orderIndex = orderIndex;
    }
    
    @Length(min=0, max=64, message="经办人长度必须介于 0 和 64 之间")
    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

	public List<String> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
    
}