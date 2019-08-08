package com.movitech.mbox.modules.wdt.exception;
/** 
 * @author 作者 felix.jin: 
 * @version 创建时间：2017年9月6日 下午4:34:52 
 * 类说明 
 */
public class RollBackException extends RuntimeException{

	public RollBackException(String message) {
		super(message);
	}
	
	public RollBackException() {
		super();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
