/**
 * 
 */
package com.movitech.mbox.modules.wdt.entity;

import org.hibernate.validator.constraints.Length;

import com.movitech.mbox.common.persistence.DataEntity;

/**
 * 任务移交Entity
 * @author felix
 * @version 2017-08-09
 */
public class WdtTTaskHandover extends DataEntity<WdtTTaskHandover> {
    
    private static final long serialVersionUID = 1L;
    private String type;        // 任务层级（枚举维护： 0：主任务 1：节点）
    private String taskId;        // 任务主键
    private String taskItemId;        // 节点主键
    private String content;        // 移交说明
    private String userId;        // 移交至某人员
    
    public WdtTTaskHandover() {
        super();
    }

    public WdtTTaskHandover(String id){
        super(id);
    }

    @Length(min=0, max=10, message="任务层级（枚举维护： 0：主任务 1：节点）长度必须介于 0 和 10 之间")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    @Length(min=0, max=64, message="任务主键长度必须介于 0 和 64 之间")
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    
    @Length(min=0, max=64, message="节点主键长度必须介于 0 和 64 之间")
    public String getTaskItemId() {
        return taskItemId;
    }

    public void setTaskItemId(String taskItemId) {
        this.taskItemId = taskItemId;
    }
    
    @Length(min=0, max=512, message="移交说明长度必须介于 0 和 512 之间")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    @Length(min=0, max=64, message="移交至某人员长度必须介于 0 和 64 之间")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
}