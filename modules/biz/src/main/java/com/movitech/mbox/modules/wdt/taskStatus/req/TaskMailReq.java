package com.movitech.mbox.modules.wdt.taskStatus.req;

import com.movitech.mbox.common.config.Global;
import com.movitech.mbox.common.utils.JsonUtil;



/**
 * 对接exchange发送邮件实体对象
 * @author felix.jin
 * 2017年8月28日
 */
/**
 * @author felix.jin
 * 2017年8月29日 
 */
public class TaskMailReq {
	private String tomail;
	private String toname;
	private String title;
	private String content;
	private String keycode="gzrc.com";
	
	public TaskMailReq() {
		super();
	}
	
	public TaskMailReq(String tomail, String toname) {
		super();
		this.tomail = tomail;
		this.toname = toname;
	}
	public String getTomail() {
		return tomail;
	}
	public void setTomail(String tomail) {
		this.tomail = tomail;
	}
	public String getToname() {
		return toname;
	}
	public void setToname(String toname) {
		this.toname = toname;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getKeycode() {
		return keycode;
	}
	public void setKeycode(String keycode) {
		this.keycode = keycode;
	}

	
	
}
