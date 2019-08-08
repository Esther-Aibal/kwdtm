/**
 * 
 */
package com.movitech.mbox.modules.wdt.entity;

import org.hibernate.validator.constraints.Length;

import com.movitech.mbox.common.persistence.DataEntity;

/**
 * 子任务关联Entity
 * @author Aibal.He
 * @version 2017-08-21
 */
public class WdtTTaskRelation extends DataEntity<WdtTTaskRelation> {
    
    private static final long serialVersionUID = 1L;
    private String taskId;        // 任务主键
    private String relationTaskId;        // 关联任务主键
    
    public WdtTTaskRelation() {
        super();
    }

    public WdtTTaskRelation(String id){
        super(id);
    }

    @Length(min=0, max=64, message="任务主键长度必须介于 0 和 64 之间")
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    
    @Length(min=0, max=64, message="关联任务主键长度必须介于 0 和 64 之间")
    public String getRelationTaskId() {
        return relationTaskId;
    }

    public void setRelationTaskId(String relationTaskId) {
        this.relationTaskId = relationTaskId;
    }
    
}