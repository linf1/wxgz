package com.base.util.reflect;
/**
 * <p>Title: ReflectStringUtil </p>
 * <p>Description: 反射工具类</p>
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
public class ReflectStringUtil {

	/**
	 * 获取get方法名称
	 * @param fieldName
	 * @return
	 */
	public static String populateGetMethodName(String fieldName){
		return "get" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
	}
	
	/**
	 * 获取set方法名称
	 * @param fieldName
	 * @return
	 */
	public static String populateSetMethodName(String fieldName){
		return "set" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
	}

}
