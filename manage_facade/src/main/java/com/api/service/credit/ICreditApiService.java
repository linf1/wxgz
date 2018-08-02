package com.api.service.credit;


import com.api.model.credit.CreditRequest;
import com.api.model.credit.CreditResultAO;
import com.api.model.credit.CreditResultRequest;
import com.api.model.result.ApiResult;
import com.api.model.tongdun.ReportAO;
import com.api.model.tongdun.TongDunRequest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 征信API服务接口
 * @author luochaofang
 */
public interface ICreditApiService {
    String BEAN_ID = "creditApiServiceImpl";
    /**
     * 征信账号验证
     * @throws  IOException 调用异常
     * @return 返回对象
     */
    CreditResultAO getCreditToken(CreditRequest request) throws IOException;

    /**
     * 根据token获取客户id
     * @param token
     * @return
     */
    String  getCustomerByToken(String token);
}
