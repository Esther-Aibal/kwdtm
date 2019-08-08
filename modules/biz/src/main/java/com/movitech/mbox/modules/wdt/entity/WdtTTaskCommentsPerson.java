/**
 * 
 */
package com.movitech.mbox.modules.wdt.entity;

import org.hibernate.validator.constraints.Length;
import com.movitech.mbox.modules.sys.entity.User;

import com.movitech.mbox.common.persistence.DataEntity;

/**
 * 进度@人员Entity
 * @author Aibal.He
 * @version 2017-10-27
 */
public class WdtTTaskCommentsPerson extends DataEntity<WdtTTaskCommentsPerson> {
    
    private static final long serialVersionUID = 1L;
    private String comentsId;        // 进度反馈与进度汇报表主键
    private User user;        // 人员id
    private String readFlag;// 是否已读（枚举维护：0：未读 1：已读）
    public WdtTTaskCommentsPerson() {
        super();
    }

    public WdtTTaskCommentsPerson(String id){
        super(id);
    }

    @Length(min=0, max=64, message="进度反馈与进度汇报表主键长度必须介于 0 和 64 之间")
    public String getComentsId() {
        return comentsId;
    }

    public void setComentsId(String comentsId) {
        this.comentsId = comentsId;
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

	public String getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(String readFlag) {
		this.readFlag = readFlag;
	}
    
}