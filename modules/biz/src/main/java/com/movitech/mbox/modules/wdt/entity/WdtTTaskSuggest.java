/**
 * 
 */
package com.movitech.mbox.modules.wdt.entity;

import org.hibernate.validator.constraints.Length;
import com.movitech.mbox.modules.sys.entity.User;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.movitech.mbox.common.persistence.DataEntity;

/**
 * 意见收集Entity
 * @author Aibal.He
 * @version 2018-01-11
 */
public class WdtTTaskSuggest extends DataEntity<WdtTTaskSuggest> {
    
    private static final long serialVersionUID = 1L;
    private String suggest;        // 意见
    private User inputPerson;        // 提交人
    private Date inputDate;        // 提交时间
    private Date beginInputDate;        // 开始 提交时间
    private Date endInputDate;        // 结束 提交时间
    private String type;        // 类型（0：优化 1：BUG 2：其他）
    public WdtTTaskSuggest() {
        super();
    }

    public WdtTTaskSuggest(String id){
        super(id);
    }

    @Length(min=0, max=1000, message="意见长度必须介于 0 和 1000 之间")
    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }
    
    public User getInputPerson() {
        return inputPerson;
    }

    public void setInputPerson(User inputPerson) {
        this.inputPerson = inputPerson;
    }
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getInputDate() {
        return inputDate;
    }

    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }
    
    public Date getBeginInputDate() {
        return beginInputDate;
    }

    public void setBeginInputDate(Date beginInputDate) {
        this.beginInputDate = beginInputDate;
    }
    
    public Date getEndInputDate() {
        return endInputDate;
    }

    public void setEndInputDate(Date endInputDate) {
        this.endInputDate = endInputDate;
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
        
}