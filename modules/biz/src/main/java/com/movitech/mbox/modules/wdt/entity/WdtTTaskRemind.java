/**
 * 
 */
package com.movitech.mbox.modules.wdt.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.movitech.mbox.common.persistence.DataEntity;

/**
 * 自定义提醒Entity
 * @author Aibal.He
 * @version 2018-01-24
 */
public class WdtTTaskRemind extends DataEntity<WdtTTaskRemind> {
    
    private static final long serialVersionUID = 1L;
    private String title;        // 标题
    private String content;        // 内容
    private Date remindDate;        // 提醒日期
    private String doFlag;        // 办理情况（0：未办 1：已办）
    private int start;
    private int length;
    private String inputPerson;
    public WdtTTaskRemind() {
        super();
    }

    public WdtTTaskRemind(String id){
        super(id);
    }

    @Length(min=0, max=256, message="标题长度必须介于 0 和 256 之间")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    @Length(min=0, max=1000, message="内容长度必须介于 0 和 1000 之间")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    public Date getRemindDate() {
        return remindDate;
    }

    public void setRemindDate(Date remindDate) {
        this.remindDate = remindDate;
    }
    
    @Length(min=0, max=10, message="办理情况（0：未办 1：已办）长度必须介于 0 和 10 之间")
    public String getDoFlag() {
        return doFlag;
    }

    public void setDoFlag(String doFlag) {
        this.doFlag = doFlag;
    }

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getInputPerson() {
		return inputPerson;
	}

	public void setInputPerson(String inputPerson) {
		this.inputPerson = inputPerson;
	}
    
}