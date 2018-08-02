package com.api.service.tongdun;


import com.api.model.tongdun.ReportAO;
import com.api.model.tongdun.TongDunRequest;

import java.io.IOException;

/**
 * 同盾API服务接口
 * @author 陈清玉
 */
public interface ITongDunApiService {
    String BEAN_ID = "tongDunApiServiceImpl";
    /**
     * 获取审߿报告编号
     * @param request 请求对象 {@link TongDunRequest}
     * @throws  IOException 调用异常
     * @return 返回对象
     */
    ReportAO queryReportId(TongDunRequest request) throws IOException;

    /**
     * 获取审߿报告编号
     * @param reportId {@link ReportAO}
     * @throws  Exception 调用异常
     * @return 返回接口数据
     */
    String queryReportInfo(ReportAO reportId) throws Exception;

}
