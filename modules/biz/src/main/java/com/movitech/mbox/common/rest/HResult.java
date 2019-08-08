package com.movitech.mbox.common.rest;





public class HResult<T> {
	
	private Boolean result;
	
	private String message;
	
	private T data;
	
	public HResult(Boolean result, String message) {
		this.result = result;
		this.message = message == null ? "" : message;
	}


	public HResult(Boolean result) {
		this.result = result;
	}

	public HResult(Boolean result, String message, T data) {
		this.result = result;
		this.message = message == null ? "" : message;
		this.data = data;
	}
	
	public HResult(T data) {
		this.result = true;
		this.message = "";
		this.data = data;
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

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public static HResult<Object> success(){
		return new HResult<Object>(true);
	}

	public static HResult<Object> success(String message){
		return new HResult<Object>(true,message);
	}

	public static HResult<Object> fail(String message){
		return new HResult<Object>(false , message);
	}

}
