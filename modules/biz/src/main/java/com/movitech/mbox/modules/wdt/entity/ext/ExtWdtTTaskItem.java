/**
 * 
 */
package com.movitech.mbox.modules.wdt.entity.ext;

import java.util.List;

import com.movitech.mbox.modules.sys.entity.User;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskItem;

/**
 * 任务节点补充Entity
 * @author Aibal.He
 * @version 2017-08-08
 */
public class ExtWdtTTaskItem extends WdtTTaskItem{
    
    private static final long serialVersionUID = 1L;
    
	private List<String> readerIds;//知阅人id
	private List<User> readerUsers;//知阅人
	private String owner;//责任人
    private String owerOfficeName;//主责人名称
	private String taskName;//任务名称
	private Boolean istaskItemOwner;//是否节点负责人

	
	public String getOwner() {
		if(owner==null){
			return "";
		}
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public List<String> getReaderIds() {
		return readerIds;
	}

	public void setReaderIds(List<String> readerIds) {
		this.readerIds = readerIds;
	}

	public Boolean getIstaskItemOwner() {
		return istaskItemOwner;
	}

	public void setIstaskItemOwner(Boolean istaskItemOwner) {
		this.istaskItemOwner = istaskItemOwner;
	}

	public List<User> getReaderUsers() {
		return readerUsers;
	}

	public void setReaderUsers(List<User> readerUsers) {
		this.readerUsers = readerUsers;
	}

	public String getOwerOfficeName() {
		return owerOfficeName;
	}

	public void setOwerOfficeName(String owerOfficeName) {
		this.owerOfficeName = owerOfficeName;
	}
	
}