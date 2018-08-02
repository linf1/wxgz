package com.zw.api.credit.controller;

import com.alibaba.fastjson.JSONObject;
import com.api.model.credit.CreditRequest;
import com.api.model.credit.CreditResultAO;
import com.api.model.result.ApiResult;
import com.api.service.credit.ICreditApiService;
import com.api.service.result.IApiResultServer;
import com.base.util.AppRouterSettings;
import com.base.util.GeneratePrimaryKeyUtils;
import com.constants.ApiConstants;
import com.enums.EApiChildSourceEnum;
import com.enums.EApiSourceEnum;
import com.zw.web.base.vo.ResultVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 征信API控制器
 *
 * @author luochaofang
 */
@RestController
@RequestMapping(value = AppRouterSettings.VERSION  + "/credit")
public class CreditApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreditApiController.class);

    @Autowired
    private ICreditApiService creditApiService;

    @Autowired
    private IApiResultServer apiResultServer;

    /**
     * 个人征信验证获取token
     * @return 客户端H5
     */
    @PostMapping("/getCreditToken")
    @ResponseBody
    public ResultVO getCreditToken(CreditRequest request) {
        try {
            if (null == request || StringUtils.isBlank(request.getCustomerId()) || StringUtils.isBlank(request.getCallbackUrl())) {
                LOGGER.info("请求参数异常或不存在");
                return ResultVO.error("请求参数异常或不存在");
            }
            CreditResultAO creditResultAO = creditApiService.getCreditToken(request);
            if(null != creditResultAO && StringUtils.isNotBlank(creditResultAO.getToken())) {
                return ResultVO.ok(ApiConstants.STATUS_SUCCESS_MSG,creditResultAO);
            }
        } catch (Exception e) {
            LOGGER.info("个人征信-获取token出错" + e);
            return ResultVO.error(ApiConstants.STATUS_DATASOURCE_INTERNAL_ERROR,ApiConstants.STATUS_DATASOURCE_INTERNAL_ERROR_MSG);
        }
        return  ResultVO.error(ApiConstants.STATUS_SIGN_ERROR, ApiConstants.STATUS_SIGN_ERROR_MSG);
    }

    /**
     * 获取个人征信信息回调入口
     * @param request 参数
     */
    @RequestMapping("/getCreditApiInfo")
    public void getCreditApiInfo(@RequestBody String request){
        if(StringUtils.isNotBlank(request)) {
            LOGGER.info(request);
            LOGGER.info("--------------------------------回调成功   ------------------------");
            JSONObject jsonObject = JSONObject.parseObject(request);
            if(jsonObject.containsKey(ApiConstants.API_TOKEN_KEY)) {
                String token = jsonObject.getString(ApiConstants.API_TOKEN_KEY);
                if(StringUtils.isNotBlank(token)) {
                    String customerId = creditApiService.getCustomerByToken(token);
                    if(StringUtils.isNotBlank(customerId)) {
                        Map<String,Object> map = new HashMap<>();
                        //Map userMap = userService.getCustomerInfoByCustomerId(customerId);
                        Map userMap = new HashMap();
                        if(null == userMap) {
                            LOGGER.info("该客户不存在：{}", customerId);
                        } else {
                            try {
                                //根据客户信息更新征信报告
                                ApiResult oldResult = new ApiResult();
                                oldResult.setSourceCode(EApiSourceEnum.CREDIT.getCode());
                                oldResult.setOnlyKey(customerId);//客户id
                                oldResult.setState(ApiConstants.STATUS_CODE_NO_STATE);//改为无效数据
                                apiResultServer.updateByOnlyKey(oldResult);

                                ApiResult result = new ApiResult();
                                result.setState(ApiConstants.STATUS_CODE_NO_STATE);
                                result.setResultData("");
                                result.setRealName(userMap.get("customerName").toString());
                                result.setUserName(userMap.get("tel").toString());
                                result.setIdentityCode(userMap.get("card").toString());
                                result.setUserMobile(userMap.get("tel").toString());
                                if(jsonObject.containsKey(ApiConstants.API_DATA_KEY)){
                                    String resultData = jsonObject.getString(ApiConstants.API_DATA_KEY);
                                    if(StringUtils.isNotBlank(resultData)) {
                                        result.setResultData(resultData);
                                        result.setState(ApiConstants.STATUS_CODE_STATE);
                                        map.put(ApiConstants.API_MESSAGE_KEY,ApiConstants.STATUS_SUCCESS_MSG);
                                        map.put(ApiConstants.API_CODE_KEY,ApiConstants.STATUS_SUCCESS);
                                    } else {
                                        map.put(ApiConstants.API_MESSAGE_KEY,ApiConstants.STATUS_INFO_NOT_FOUND_MSG);
                                        map.put(ApiConstants.API_CODE_KEY,ApiConstants.STATUS_INFO_NOT_FOUND);
                                    }
                                } else {
                                    map.put(ApiConstants.API_CODE_KEY,ApiConstants.STATUS_CREDIT_INFO_ERROR);
                                    map.put(ApiConstants.API_MESSAGE_KEY,jsonObject.getString(ApiConstants.API_MESSAGE_KEY));
                                }
                                result.setId(GeneratePrimaryKeyUtils.getUUIDKey());
                                result.setOnlyKey(customerId);
                                result.setCode(map.get(ApiConstants.API_CODE_KEY).toString());
                                result.setMessage(map.get(ApiConstants.API_MESSAGE_KEY).toString());
                                result.setSourceName(EApiSourceEnum.CREDIT.getName());
                                result.setSourceCode(EApiSourceEnum.CREDIT.getCode());
                                result.setSourceChildName(EApiChildSourceEnum.CREDIT_CHILD.getName());
                                result.setSourceChildCode(EApiChildSourceEnum.CREDIT_CHILD.getCode());
                                result.setApiReturnId("");
                                LOGGER.info("个人征信--获取报告信息成功{}", result);
                                apiResultServer.insertApiResult(result);
                            } catch (Exception e) {
                                LOGGER.info("个人征信-返回数据解析出错" + e);
                            }
                        }
                    }
                }
            }
        }
    }
}
