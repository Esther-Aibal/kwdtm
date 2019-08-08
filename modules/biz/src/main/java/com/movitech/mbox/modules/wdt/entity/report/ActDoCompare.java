package com.movitech.mbox.modules.wdt.entity.report;

import java.util.Date;

public class ActDoCompare {
	private Date leftDate;
	private Date rightDate;
	private int subDays;
	public Date getLeftDate() {
		return leftDate;
	}
	public void setLeftDate(Date leftDate) {
		this.leftDate = leftDate;
	}
	public Date getRightDate() {
		return rightDate;
	}
	public void setRightDate(Date rightDate) {
		this.rightDate = rightDate;
	}
	public int getSubDays() {
		return subDays;
	}
	public void setSubDays(int subDays) {
		this.subDays = subDays;
	}
	
	
}
