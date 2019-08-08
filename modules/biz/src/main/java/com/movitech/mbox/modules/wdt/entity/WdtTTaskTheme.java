/**
 * 
 */
package com.movitech.mbox.modules.wdt.entity;

import java.util.List;

import com.movitech.mbox.common.utils.StringUtils;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.movitech.mbox.common.persistence.DataEntity;

/**
 * 任务主题Entity
 * @author Aibal.He
 * @version 2017-08-18
 */
public class WdtTTaskTheme extends DataEntity<WdtTTaskTheme> {
    
    private static final long serialVersionUID = 1L;
    private String categoryId;        // 模板分类ID
    private String categoryName;        // 模板分类名称
    private String parentCategoryId;        // 父模板分类ID
    private String isRelateProject;
    private WdtTTaskTheme parent;
    private String isTotalApprove;//是否需要统计和审批（0：需要 1：不需要）
    public WdtTTaskTheme() {
        super();
    }

    public WdtTTaskTheme(String id){
        super(id);
    }

    public String getCategoryId() {
        if(StringUtils.isEmpty(categoryId)){
            return id;
        }
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
    
    @Length(min=0, max=128, message="模板分类名称长度必须介于 0 和 128 之间")
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    @Length(min=0, max=64, message="父模板分类ID长度必须介于 0 和 64 之间")
    public String getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(String parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

	public String getIsRelateProject() {
        if(StringUtils.isEmpty(isRelateProject)){
            return "1";
        }
		return isRelateProject;
	}

	public void setIsRelateProject(String isRelateProject) {
		this.isRelateProject = isRelateProject;
	}
    
	@JsonIgnore
    public static String getRootId(){
        return "1";
    }

    public WdtTTaskTheme getParent() {
        return parent;
    }

    public void setParent(WdtTTaskTheme parent) {
        this.parent = parent;
    }

    public String getIsTotalApprove() {
		return isTotalApprove;
	}

	public void setIsTotalApprove(String isTotalApprove) {
		this.isTotalApprove = isTotalApprove;
	}

	@JsonIgnore
    public static void sortList(List<WdtTTaskTheme> list, List<WdtTTaskTheme> sourcelist, String parentId, boolean cascade){
        for (int i=0; i<sourcelist.size(); i++){
            WdtTTaskTheme e = sourcelist.get(i);
            if (e.getParent()!=null && e.getParent().getCategoryId()!=null
                    && e.getParent().getCategoryId().equals(parentId)){
                list.add(e);
                if (cascade){
                    // 判断是否还有子节点, 有则继续获取子节点
                    for (int j=0; j<sourcelist.size(); j++){
                        WdtTTaskTheme child = sourcelist.get(j);
                        if (child.getParent()!=null && child.getParent().getCategoryId()!=null
                                && child.getParent().getCategoryId().equals(e.getCategoryId())){
                            sortList(list, sourcelist, e.getCategoryId(), true);
                            break;
                        }
                    }
                }
            }
        }
    }
}