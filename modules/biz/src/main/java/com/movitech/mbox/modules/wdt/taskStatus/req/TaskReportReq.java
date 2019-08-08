package com.movitech.mbox.modules.wdt.taskStatus.req;

import com.movitech.mbox.common.utils.DateReportUtils;
import com.movitech.mbox.common.utils.StringUtils;

import java.util.Date;

/**
 * @author 作者 felix.jin:
 * @version 创建时间：2017年9月5日 下午3:05:05 类说明
 */
public class TaskReportReq {

	private String officeId;
	private String officeName;
	private Date endDay;

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public Date getEndDay() {
		if(endDay!=null){
			return DateReportUtils.getDayEnd(endDay);
		}
		return endDay;
	}

	public void setEndDay(Date endDay) {
		this.endDay = endDay;
	}

	public String getOfficeName() {
		if(StringUtils.isEmpty(officeName)){
			return  "";
		}
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
}
