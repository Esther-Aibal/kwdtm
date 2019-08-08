package com.movitech.mbox.modules.base.vo;





public class HResult<T> {
	
	
	public final static int TOKEN_ERROR=1;
	
	private Boolean result;
	
	private String message;
	
	private T value;
	
	private int type;
	
	private String url;
	public HResult(Boolean result, String message) {
		this.result = result;
		this.message = message == null ? "" : message;
	}
	
	public HResult(Boolean result,int type, String message) {
		this.result = result;
		this.type = type;
		this.message = message == null ? "" : message;
	}
	
	public HResult(Boolean result,int type) {
		this.result = result;
		this.type = type;
	}

	public HResult(Boolean result) {
		this.result = result;
	}

	public HResult(Boolean result, String message, T value) {
		this.result = result;
		this.message = message == null ? "" : message;
		this.value = value;
	}
	
	public HResult(Boolean result, int type,String message, T value) {
		this.result = result;
		this.type = type;
		this.message = message == null ? "" : message;
		this.value = value;
	}
	
	public HResult(T value) {
		this.result = true;
		this.message = "";
		this.value = value;
	}

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
}
