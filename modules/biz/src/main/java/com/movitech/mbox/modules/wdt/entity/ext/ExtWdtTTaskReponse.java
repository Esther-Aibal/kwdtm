package com.movitech.mbox.modules.wdt.entity.ext;

import com.movitech.mbox.modules.wdt.entity.WdtTTaskReponse;

/**
 * 
 * @author Jack.Gong
 *
 */
public class ExtWdtTTaskReponse extends WdtTTaskReponse{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1933977174849059177L;

	private String userName;
	
	private String taskName;
	
	private String type;
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
