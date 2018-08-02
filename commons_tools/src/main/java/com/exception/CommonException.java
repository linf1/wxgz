package com.exception;
/**
 * <p>Title: CommonException </p>
 * <p>Description: 公共异常</p>
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
public class CommonException extends RuntimeException{

	private static final long serialVersionUID = -315349147655555522L;
	private static final String DEFAULT_ERROR_CODE="-1";

	private final String errorCode;
	
	public CommonException(String message){
		super(message);
		this.errorCode = DEFAULT_ERROR_CODE;
	}
	
	public CommonException(String message, String errorCode){
		super(message);
		this.errorCode = errorCode;
	}
	
	public CommonException(String message, String errorCode, Throwable throwable){
		super(message, throwable);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}
}
