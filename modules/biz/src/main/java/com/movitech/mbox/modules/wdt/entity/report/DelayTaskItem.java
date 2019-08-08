package com.movitech.mbox.modules.wdt.entity.report;

import java.util.Date;

import com.movitech.mbox.common.persistence.DataEntity;

public class DelayTaskItem extends DataEntity<DelayTaskItem>{
	private String id;
	private String itemName;
	private String ownerName;
	private Date startDate;
	private Date endDate;
	private Date actEndDate;
	private int delayCount;
    private String delayRate;
    private int planCount;
    private String executionStatus;
    private String fqcy;
    private String days;
    private Date requiredCompletionTime;
    private String taskId;
    private Date currentDate;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getDelayCount() {
		return delayCount;
	}
	public void setDelayCount(int delayCount) {
		this.delayCount = delayCount;
	}
	
	public String getDelayRate() {
		return delayRate;
	}
	public void setDelayRate(String delayRate) {
		this.delayRate = delayRate;
	}
	public int getPlanCount() {
		return planCount;
	}
	public void setPlanCount(int planCount) {
		this.planCount = planCount;
	}
	public String getExecutionStatus() {
		return executionStatus;
	}
	public void setExecutionStatus(String executionStatus) {
		this.executionStatus = executionStatus;
	}
	public Date getActEndDate() {
		return actEndDate;
	}
	public void setActEndDate(Date actEndDate) {
		this.actEndDate = actEndDate;
	}
	public String getFqcy() {
		return fqcy;
	}
	public void setFqcy(String fqcy) {
		this.fqcy = fqcy;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	public Date getRequiredCompletionTime() {
		return requiredCompletionTime;
	}
	public void setRequiredCompletionTime(Date requiredCompletionTime) {
		this.requiredCompletionTime = requiredCompletionTime;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public Date getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}
    
    
}
