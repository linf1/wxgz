package com.exception;
/**
 * <p>Title: CommonExceptionBuilder </p>
 * <p>Description: 公共异常Builder</p>
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
public class CommonExceptionBuilder {
    public static CommonException create(String errorCode){
		return new CommonException(TextProperties.instance().get(errorCode), errorCode);
	}
}
