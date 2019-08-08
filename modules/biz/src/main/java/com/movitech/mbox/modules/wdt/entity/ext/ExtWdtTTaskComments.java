/**
 * 
 */
package com.movitech.mbox.modules.wdt.entity.ext;


import java.util.Date;
import java.util.List;

import com.movitech.mbox.common.utils.DateUtils;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskComments;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskCommentsPerson;


/**
 * 进度汇报补充Entity
 * @author felix.jin
 * 2017年8月10日
 */
public class ExtWdtTTaskComments extends WdtTTaskComments {
    
    private static final long serialVersionUID = 1L;

    private String officeName;

    private List<WdtTTaskReponseTreeNode> wdtTTaskReponseList;

    private String taskName;
    
    private List<WdtTTaskCommentsPerson> atPersonList;

    public boolean getCanDelete(){
    	if(DateUtils.subDate(this.getCreateDate(), new Date())==0){
    		return true;
    	}
    	return false;
    }

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public List<WdtTTaskReponseTreeNode> getWdtTTaskReponseList() {
		return wdtTTaskReponseList;
	}

	public void setWdtTTaskReponseList(List<WdtTTaskReponseTreeNode> wdtTTaskReponseList) {
		this.wdtTTaskReponseList = wdtTTaskReponseList;
	}


	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public List<WdtTTaskCommentsPerson> getAtPersonList() {
		return atPersonList;
	}

	public void setAtPersonList(List<WdtTTaskCommentsPerson> atPersonList) {
		this.atPersonList = atPersonList;
	}

}