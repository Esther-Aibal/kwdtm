/**
 * 
 */
package com.movitech.mbox.modules.wdt.entity;

import org.hibernate.validator.constraints.Length;

import com.movitech.mbox.common.persistence.DataEntity;

/**
 * 阅读数Entity
 * @author Aibal.He
 * @version 2017-08-25
 */
public class WdtTTaskReadCount extends DataEntity<WdtTTaskReadCount> {
    
    private static final long serialVersionUID = 1L;
    private String taskId;        // 任务主键
    private String readCount;        // 回复阅读数量
    
    public WdtTTaskReadCount() {
        super();
    }

    public WdtTTaskReadCount(String id){
        super(id);
    }

    @Length(min=0, max=64, message="任务主键长度必须介于 0 和 64 之间")
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    
    @Length(min=0, max=11, message="回复阅读数量长度必须介于 0 和 11 之间")
    public String getReadCount() {
        return readCount;
    }

    public void setReadCount(String readCount) {
        this.readCount = readCount;
    }
    
}