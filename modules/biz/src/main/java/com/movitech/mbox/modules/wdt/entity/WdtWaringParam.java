package com.movitech.mbox.modules.wdt.entity;

import com.movitech.mbox.common.persistence.DataEntity;
import com.movitech.mbox.common.persistence.Page;

/**
 * Created by gorden on 2017/8/12.
 */
public class WdtWaringParam extends DataEntity<WdtWaringParam> {
    private String taskName;
    private String userId;
    public String getTaskName() {
        return taskName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
