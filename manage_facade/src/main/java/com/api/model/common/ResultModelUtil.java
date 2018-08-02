package com.api.model.common;

import com.constants.ApiConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 处理返回结果
 * @author 陈清玉
 *
 */
public class ResultModelUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(ResultModelUtil.class);
	
	public static ResultModel formatResult(ApiCommonResponse response, String apiName, String interfaceName){
		try {
			if(StringUtils.equals(response.getResponseCode(),ApiConstants.STATUS_SUCCESS)
					|| StringUtils.equals(response.getResponseCode(),ApiConstants.STATUS_INFO_NOT_FOUND)
							||StringUtils.equals(response.getResponseCode(),"2000")){
				return new ResultModel(ResultModel.R.SUCCESS.getCode(), apiName + "--" + interfaceName + "接口正常", response, apiName);
			}else if (StringUtils.equals(response.getResponseCode(),ApiConstants.STATUS_DATASOURCE_TIME_OUT)
					|| StringUtils.equals(response.getResponseCode(),ApiConstants.STATUS_DATASOURCE_ERROR)){
				return new ResultModel(ResultModel.R.FAIL.getCode(), apiName + "--" + interfaceName + "接口异常", response, apiName);
			}
		} catch (Exception e) {
			LOGGER.error("数据源集成平台处理返回结果出错:"+e.getMessage());
			return new ResultModel(ResultModel.R.FAIL.getCode(), apiName + "--" + interfaceName + "接口出错", ApiConstants.STATUS_ERROR_MSG, apiName);
		}
		
		return new ResultModel(ResultModel.R.FAIL.getCode(), apiName + "--" + interfaceName + ":"+ response.getResponseMsg(), response, apiName);
	}
}
