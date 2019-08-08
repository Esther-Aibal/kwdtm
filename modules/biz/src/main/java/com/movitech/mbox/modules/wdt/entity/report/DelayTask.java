package com.movitech.mbox.modules.wdt.entity.report;

import java.util.Date;
import java.util.List;

import com.movitech.mbox.common.persistence.DataEntity;


/**
 * 
 * @author Aibal.He
 *
 */
public class DelayTask extends DataEntity<DelayTask>{

	private String officeId;
	private String name;
	private Date startDate;
	private Date endDate;
    private int start;
    private int length;
    
    private String id;
    private String officeName;
    private String ownerName;
    private int delayCount;
    private String delayRate;
    private Date currentDate = new Date();
    private List<DelayTaskItem> delayTaskItemList;
    private String fqcy;
    private String days;
	public String getOfficeName() {
		return officeName;
	}
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public String getOfficeId() {
		return officeId;
	}
	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
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
	public List<DelayTaskItem> getDelayTaskItemList() {
		return delayTaskItemList;
	}
	public void setDelayTaskItemList(List<DelayTaskItem> delayTaskItemList) {
		this.delayTaskItemList = delayTaskItemList;
	}
	public Date getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
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
	
}
