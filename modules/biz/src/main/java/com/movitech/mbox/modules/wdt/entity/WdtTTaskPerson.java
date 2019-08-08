/**
 * 
 */
package com.movitech.mbox.modules.wdt.entity;

import org.hibernate.validator.constraints.Length;
import com.movitech.mbox.modules.sys.entity.User;

import com.movitech.mbox.common.persistence.DataEntity;

/**
 * 任务干系人Entity
 * @author Aibal.He
 * @version 2017-08-07
 */
public class WdtTTaskPerson extends DataEntity<WdtTTaskPerson> {
    
    private static final long serialVersionUID = 1L;
    private String type;        // 任务层级（枚举维护 0-主任务 1-节点）
    private String taskId;        // 任务主键
    private String taskItemId;        // 节点主键
    private User user;        // 人员id
    private String orgId;        // 机构id
    private String personType;        // 类型（枚举维护：0：阅知人 1：管理层）
    
    public WdtTTaskPerson() {
        super();
    }

    public WdtTTaskPerson(String id){
        super(id);
    }

    @Length(min=0, max=10, message="任务层级（枚举维护 0-主任务 1-节点）长度必须介于 0 和 10 之间")
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
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    @Length(min=0, max=64, message="机构id长度必须介于 0 和 64 之间")
    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
    
    @Length(min=0, max=10, message="类型（枚举维护：0：阅知人 1：管理层）长度必须介于 0 和 10 之间")
    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }
    
}