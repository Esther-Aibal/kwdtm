/**
 * 
 */
package com.movitech.mbox.modules.wdt.entity;

import org.hibernate.validator.constraints.Length;

import com.movitech.mbox.common.persistence.DataEntity;

/**
 * 任务项目关联Entity
 * @author Jack.Gong
 * @version 2017-08-09
 */
public class WdtTTaskProject extends DataEntity<WdtTTaskProject> {
    
    private static final long serialVersionUID = 1L;
    private String taskId;        // 任务主键
    private String projectId;        // 项目编号
    
    public WdtTTaskProject() {
        super();
    }

    public WdtTTaskProject(String id){
        super(id);
    }

    @Length(min=0, max=64, message="任务主键长度必须介于 0 和 64 之间")
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    
    @Length(min=0, max=64, message="项目编号长度必须介于 0 和 64 之间")
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
    
}