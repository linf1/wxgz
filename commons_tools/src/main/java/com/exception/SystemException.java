package com.exception;

/**
 * <p>Title: SystemException </p>
 * <p>Description: 系统自己定义异常</p>
 * <p>Copyright (c) </p>
 * <p>Company: </p>
 *
 * @author 陈清玉
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 * @date 2018年6月6日下午10:20:08
 */
public class SystemException extends RuntimeException {
	private static final long serialVersionUID = 8248605426118786012L;

	private String message;

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public SystemException(String message){
		super(message);
		this.message = message;
	}
	
	public SystemException(Throwable cause) {  
        super(cause);  
      this.message = cause.getMessage();
    }
	
	public SystemException(String message, Throwable cause) {  
        super(message, cause);  
      this.message = message;
    } 
}
