/**
 * 
 */
package com.movitech.mbox.modules.wdt.entity;

import org.hibernate.validator.constraints.Length;
import com.movitech.mbox.modules.sys.entity.User;

import com.movitech.mbox.common.persistence.DataEntity;

/**
 * 关注任务Entity
 * @author Jack.Gong
 * @version 2017-08-17
 */
public class WdtTTaskAttemtion extends DataEntity<WdtTTaskAttemtion> {
    
    private static final long serialVersionUID = 1L;
    private String taskId;        // 任务主键
    private User user;        // 人员id
    private String orgId;        // 机构id
    
    public WdtTTaskAttemtion() {
        super();
    }

    public WdtTTaskAttemtion(String id){
        super(id);
    }

    @Length(min=0, max=64, message="任务主键长度必须介于 0 和 64 之间")
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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
    
}