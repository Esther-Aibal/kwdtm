package com.movitech.mbox.modules.wdt.req;
/** 
 * @author 作者 felix.jin: 
 * @version 创建时间：2017年8月9日 上午9:57:29 
 * 类说明 请求参数基础类
 */
public class BaseReq  {

	private int start;

	private int length;
    private String search;
    private String executionStatus;
	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLength() {
		return length==0?10:length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getExecutionStatus() {
		return executionStatus;
	}

	public void setExecutionStatus(String executionStatus) {
		this.executionStatus = executionStatus;
	}
	
	
}
