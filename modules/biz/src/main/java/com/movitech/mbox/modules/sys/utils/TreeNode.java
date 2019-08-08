package com.movitech.mbox.modules.sys.utils;

import java.io.Serializable;
import java.util.List;

public class TreeNode implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;  
	  
    private String parentId;  
  
    private String name;  
    private String isRelateProject;
    private List<TreeNode> children;  
    public TreeNode(){
    	
    }
    public TreeNode(String id, String name, String parentId) {  
        this.id = id;  
        this.parentId = parentId;  
        this.name = name;  
    }  
    public TreeNode(String id, String name, TreeNode parent) {  
        this.id = id;  
        this.parentId = parent.getId();  
        this.name = name;  
    }  
  
  
    public String getParentId() {  
        return parentId;  
    }  
  
    public void setParentId(String parentId) {  
        this.parentId = parentId;  
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
  
    public List<TreeNode> getChildren() {  
        return children;  
    }  
  
    public void setChildren(List<TreeNode> children) {  
        this.children = children;  
    }  
  
    public String getIsRelateProject() {
		return isRelateProject;
	}
	public void setIsRelateProject(String isRelateProject) {
		this.isRelateProject = isRelateProject;
	}
	@Override  
    public String toString() {  
        return "{" +  
                "id='" + id + '\'' +  
                ", parentId='" + parentId + '\'' +  
                ", name='" + name + '\'' +  
                ", isRelateProject='" + isRelateProject + '\'' + 
                ", children=" + children +  
                '}';  
    }  
}
