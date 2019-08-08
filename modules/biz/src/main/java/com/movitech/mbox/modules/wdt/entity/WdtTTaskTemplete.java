/**
 * 
 */
package com.movitech.mbox.modules.wdt.entity;

import org.hibernate.validator.constraints.Length;

import com.movitech.mbox.common.persistence.DataEntity;

/**
 * 任务内容模板Entity
 * @author Aibal.He
 * @version 2017-08-18
 */
public class WdtTTaskTemplete extends DataEntity<WdtTTaskTemplete> {
    
    private static final long serialVersionUID = 1L;
    private String themeId;        // 任务主题主键
    private String templeteColumn;        // 任务内容字段
    private Integer sortNum;
    // ------------------非持久化字段-------------------------------
    private String content;        // 任务内容字段 内容
    public WdtTTaskTemplete() {
        super();
    }

    public WdtTTaskTemplete(String id){
        super(id);
    }

    @Length(min=0, max=64, message="任务主题主键长度必须介于 0 和 64 之间")
    public String getThemeId() {
        return themeId;
    }

    public void setThemeId(String themeId) {
        this.themeId = themeId;
    }
    
    @Length(min=0, max=128, message="任务内容字段长度必须介于 0 和 128 之间")
    public String getTempleteColumn() {
        return templeteColumn;
    }

    public void setTempleteColumn(String templeteColumn) {
        this.templeteColumn = templeteColumn;
    }

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}
    
}