/**
 * 
 */
package com.movitech.mbox.modules.wdt.entity;

import org.hibernate.validator.constraints.Length;

import com.movitech.mbox.common.persistence.DataEntity;

/**
 * 任务内容内容Entity
 * @author Aibal.He
 * @version 2017-08-18
 */
public class WdtTTaskContent extends DataEntity<WdtTTaskContent> {
    
    private static final long serialVersionUID = 1L;
    private String taskId;        // 任务主键
    private String templeteId;        // 任务内容模板ID
    private String content;        // 内容
    
    public WdtTTaskContent() {
        super();
    }

    public WdtTTaskContent(String id){
        super(id);
    }

    @Length(min=0, max=64, message="任务主键长度必须介于 0 和 64 之间")
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    
    @Length(min=0, max=64, message="任务内容模板ID长度必须介于 0 和 64 之间")
    public String getTempleteId() {
        return templeteId;
    }

    public void setTempleteId(String templeteId) {
        this.templeteId = templeteId;
    }
    
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
}