package com.api.model.common;


import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * 返回信息model
 * @author 陈清玉
 *
 */
public class ResultModel implements Serializable{
	
	private static final long serialVersionUID = 430079929748057320L;
	/**
	 * 信息码
	 */
	private int code;
	/**
	 * 信息
	 */
	private String message;
	/**
	 * 内容
	 */
	private Object info;
	
	private String apiName;
	
	public ResultModel() {
		super();
		setR(R.SUCCESS);
	}

	public ResultModel(R r) {
		this(r,new JSONObject());
	}

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public ResultModel(R r, Object info) {
		this.code = r.getCode();
		this.message = r.getValue();
		this.info = info;
	}
	
	public ResultModel(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public ResultModel(int code, String message, Object info, String apiName) {
		this.code = code;
		this.message = message;
		this.info = info;
		this.apiName = apiName;
	}
	public void setR(R r){

		this.code = r.getCode();
		this.message = r.getValue();
	}
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getInfo() {
		return info;
	}

	public void setInfo(Object info) {
		this.info = info;
	}

	public enum R {
		/**
		 * 成功
		 */
		SUCCESS(0,"success"),
		/**
		 * 失败
		 */
		FAIL(1,"fail");
		private int code;

		private String value;

		R(int code, String value) {
			this.code = code;
			this.value = value;
		}

		public int getCode() {
			return code;
		}

		public String getValue() {
			return value;
		}
	}



}
