package com.movitech.mbox.modules.wdt.entity.ext;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.movitech.mbox.common.utils.DateUtils;
import com.movitech.mbox.modules.sys.entity.User;
import com.movitech.mbox.modules.sys.utils.TreeNode;
import com.movitech.mbox.modules.wdt.entity.WdtTTaskCommentsPerson;

public class WdtTTaskReponseTreeNode {
	private String id;
	private User user;        // 回复人id
	private User byUser;        // 被回复人
	private String parentId;        // 父节点id
	private String content;        // 回复内容
	private String comentsId;        // 进度反馈与评论表主键
	private Date createDate;        //
    private List<WdtTTaskCommentsPerson> atPersonList;
    private List<String> atPerson;//被@的人员
	private List<WdtTTaskReponseTreeNode> children = new LinkedList<WdtTTaskReponseTreeNode>();
	
	public boolean getCanDelete(){
    	if(DateUtils.subDate(this.getCreateDate(), new Date())==0){
    		return true;
    	}
    	return false;
    }
	public WdtTTaskReponseTreeNode(){
		
	}
	
	public WdtTTaskReponseTreeNode(String id, String content, String parentId) {  
        this.id = id;  
        this.parentId = parentId;  
        this.content = content;  
    }  
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public User getByUser() {
		return byUser;
	}
	public void setByUser(User byUser) {
		this.byUser = byUser;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<WdtTTaskReponseTreeNode> getChildren() {
		return children;
	}
	public void setChildren(List<WdtTTaskReponseTreeNode> children) {
		this.children = children;
	}

	public String getComentsId() {
		return comentsId;
	}

	public void setComentsId(String comentsId) {
		this.comentsId = comentsId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public List<WdtTTaskCommentsPerson> getAtPersonList() {
		return atPersonList;
	}
	public void setAtPersonList(List<WdtTTaskCommentsPerson> atPersonList) {
		this.atPersonList = atPersonList;
	}
	public List<String> getAtPerson() {
		return atPerson;
	}
	public void setAtPerson(List<String> atPerson) {
		this.atPerson = atPerson;
	}

}
