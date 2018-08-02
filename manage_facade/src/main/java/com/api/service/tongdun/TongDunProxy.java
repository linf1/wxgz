package com.api.service.tongdun;

import com.alibaba.fastjson.JSONObject;
import com.api.model.common.ApiCommonResponse;
import com.api.model.result.ApiResult;
import com.api.model.tongdun.ReportAO;
import com.api.model.tongdun.TongDunRequest;
import com.api.service.result.IApiResultServer;
import com.base.util.GeneratePrimaryKeyUtils;
import com.constants.ApiConstants;
import com.enums.EApiChildSourceEnum;
import com.enums.EApiSourceEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 同盾代理层
 * @author 陈清玉
 */
public class TongDunProxy implements ITongDunApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TongDunProxy.class);

    private ITongDunApiService tongDunApiService;

    private IApiResultServer apiResultServerImpl;

    public TongDunProxy(ITongDunApiService tongDunApiService,IApiResultServer apiResultServerImpl) {
        this.tongDunApiService = tongDunApiService;
        this.apiResultServerImpl = apiResultServerImpl;
    }

    @Override
    public ReportAO queryReportId(TongDunRequest request)  throws IOException {
        return tongDunApiService.queryReportId(request);
    }

    @Override
    public String queryReportInfo(ReportAO reportId) throws Exception {
        return tongDunApiService.queryReportInfo(reportId);
    }

    public  ApiCommonResponse invokeTongDunApi(TongDunRequest request) throws Exception {
        LOGGER.info("同盾--获取报告信息 API调用开始.");
        long startTime = System.currentTimeMillis();
        long costTime = 0;
        ApiCommonResponse response = new ApiCommonResponse();
        ApiResult resultParameter = new ApiResult();
        resultParameter.setSourceCode(EApiSourceEnum.TODONG.getCode());
        resultParameter.setOnlyKey(request.getCustomerId());
        resultParameter.setState(ApiConstants.STATUS_CODE_NO_STATE);
        //吧老数据更新为失效
        apiResultServerImpl.updateByOnlyKey(resultParameter);
        try {
            //获取报告ID
            final ReportAO reportAO = queryReportId(request);
            if (!reportAO.isSuccess()) {
                LOGGER.info("同盾-查询获取reportId失败或参数验证不通过");
                response.setResponseCode(reportAO.getReasonCode());
                response.setResponseMsg(reportAO.getReasonDesc());
                return response;
            }
            Thread.sleep(3000);
            LOGGER.info("同盾--获取报告信息 API调用参数：{}", request.toString());
            request.setReportId(reportAO.getReportId());
            final String result = queryReportInfo(reportAO);
            costTime = System.currentTimeMillis() - startTime;
            LOGGER.info("同盾--获取报告信息{}", result);
            final JSONObject jsonObject = JSONObject.parseObject(result);
            //查到数据
            if (jsonObject.getBoolean(ApiConstants.REPORT_SUCCESS_KEY)) {
                response.setResponseCode(ApiConstants.STATUS_SUCCESS);
                response.setResponseMsg(ApiConstants.STATUS_SUCCESS_MSG);
                response.setOriginalData(jsonObject);
                //保存数据到数据库
                saveTongDunInfo(request, jsonObject.toString());
            } else {
                response.setResponseCode(jsonObject.getString(ApiConstants.REASON_CODE_KEY));
                response.setResponseMsg(jsonObject.getString(ApiConstants.REASON_DESC_KEY));
            }
        } catch (Exception e) {
            LOGGER.info("同盾-返回数据解析出错" + e);
            response.setResponseCode(ApiConstants.STATUS_DATASOURCE_INTERNAL_ERROR);
            response.setResponseMsg(ApiConstants.STATUS_DATASOURCE_INTERNAL_ERROR_MSG);
        }
        LOGGER.info("同盾-获取报告信息.[耗时:{}毫秒]", costTime);
        return response;
    }
    /**
     * 持久化调用API的数据
     * @param request 请求参数 {@link TongDunRequest}
     * @param jsonStr API请求到的数据
     * @return 影响行数
     */
    private void saveTongDunInfo(TongDunRequest request, String jsonStr) throws Exception {
        ApiResult apiResult = new ApiResult();
        apiResult.setId(GeneratePrimaryKeyUtils.getUUIDKey());
        apiResult.setCode(ApiConstants.STATUS_SUCCESS);
        apiResult.setIdentityCode(request.getIdNo());
        apiResult.setMessage(ApiConstants.STATUS_SUCCESS_MSG);
        apiResult.setSourceChildName(EApiChildSourceEnum.TODONG_CHILD.getName());
        apiResult.setSourceChildCode(EApiChildSourceEnum.TODONG_CHILD.getCode());
        apiResult.setOnlyKey(request.getCustomerId());
        apiResult.setRealName(request.getName());
        apiResult.setSourceName(EApiSourceEnum.TODONG.getName());
        apiResult.setSourceCode(EApiSourceEnum.TODONG.getCode());
        apiResult.setUserMobile(request.getPhone());
        apiResult.setUserName(request.getPhone());
        apiResult.setResultData(jsonStr);
        apiResult.setApiReturnId(request.getReportId());
        apiResult.setState(ApiConstants.STATUS_CODE_STATE);
        apiResultServerImpl.insertApiResult(apiResult);
    }


}
