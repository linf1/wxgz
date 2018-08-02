package com.zw.api.shujumohe.controller;

import com.api.model.result.ApiResult;
import com.api.model.shujumohe.ShujumoheRequest;
import com.api.service.result.IApiResultServer;
import com.api.service.shujumohe.IShujumoheServer;
import com.base.util.AppRouterSettings;
import com.base.util.GeneratePrimaryKeyUtils;
import com.base.util.StringUtils;
import com.constants.ApiConstants;
import com.enums.EApiChildSourceEnum;
import com.enums.EApiSourceEnum;
import com.zw.web.base.vo.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 魔盒服务控制
 * @author 陈清玉
 */
@RestController
@RequestMapping(AppRouterSettings.VERSION +"/shujumohe" )
public class ShujumoheController {

    private final Logger LOGGER = LoggerFactory.getLogger(ShujumoheController.class);

    @Autowired
    private IShujumoheServer shujumoheServerImpl;

    @Autowired
    private IApiResultServer apiResultServerImpl;

    /**
     * 数据魔盒回调接口
     * @param request 数据 custId - 个人信息ID
     *                     task_id - 魔盒数据查询ID
     *                      phone - 登录手机
     * @return
     */
    @RequestMapping("/callBackShujumohe")
    public ResultVO callBackShujumohe(ShujumoheRequest request){
        if(null == request || StringUtils.isBlank(request.getTask_id())){
            LOGGER.info("=======数据魔盒回调接参数taskId:为空");
            return ResultVO.error("参数taskId缺失");
        }
        LOGGER.info("=======数据魔盒回调接参数request:{}",request.toString());
        try {
            ApiResult resultParameter = new ApiResult();
            resultParameter.setOnlyKey(request.getCustomerId());
            resultParameter.setSourceCode(EApiSourceEnum.MOHE.getCode());
            resultParameter.setState(ApiConstants.STATUS_CODE_NO_STATE);
            //把以前的数据更新成为失效
            apiResultServerImpl.updateByOnlyKey(resultParameter);
            //Map custInfoMap = userService.getCustomerInfoByCustomerId(request.getCustomerId());
            Map custInfoMap = new HashMap();
            if(custInfoMap != null) {
                Map<String, Object> param = new HashMap<>(5);
                param.put("identity_code", custInfoMap.get("card"));
                param.put("channel_type", "");
                param.put("real_name", custInfoMap.get("customerName"));
                param.put("user_mobile", custInfoMap.get("tel"));
                param.put("task_data", "");
                //默认数据成功
                saveMoheInfo(request,param);
                //异步更新数据
                asyncExecutor(request);
            }else{
                LOGGER.info("--------------请先实名认证!---------------");
                return   ResultVO.error("请先实名认证!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            ResultVO.error(e.getMessage());
        }
        return  ResultVO.ok("验证成功！",null);
    }

    /**
     * 持久化魔盒数据
     * @param request 数据请求数据
     * @param data 魔盒数据
     * @throws Exception
     */
    private void saveMoheInfo(ShujumoheRequest request, Map data) throws Exception {
        ApiResult apiResult = new ApiResult();
        apiResult.setId(GeneratePrimaryKeyUtils.getUUIDKey());
        apiResult.setCode(ApiConstants.STATUS_SUCCESS);
        apiResult.setIdentityCode(data.get("identity_code").toString());
        apiResult.setMessage(ApiConstants.STATUS_SUCCESS_MSG);
        apiResult.setSourceChildName(EApiChildSourceEnum.MOHE_YYS.getName());
        apiResult.setSourceChildCode(EApiChildSourceEnum.MOHE_YYS.getCode());
        apiResult.setOnlyKey(request.getCustomerId());
        apiResult.setRealName(data.get("real_name").toString());
        apiResult.setSourceName(EApiSourceEnum.MOHE.getName());
        apiResult.setSourceCode(EApiSourceEnum.MOHE.getCode());
        apiResult.setUserMobile(data.get("user_mobile").toString());
        apiResult.setUserName(request.getPhone());
        apiResult.setResultData(data.get("task_data").toString());
        apiResult.setApiReturnId(request.getTask_id());
        apiResult.setState(ApiConstants.STATUS_CODE_STATE);
        apiResultServerImpl.insertApiResult(apiResult);
    }

    /**
     * 异步执行数据并且更新到数据库
     * @param request 请求参数对象
     *                orderId - 订单id
     *                phone - 手机号码
     *                taskId - 数据魔盒数据查询id
     *
     */
    private void asyncExecutor(ShujumoheRequest request){
        final BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>();
        final ThreadFactory threadFactory = Thread::new;
        final ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS, blockingQueue, threadFactory);
        poolExecutor.submit(new ShujumoheRunable(this.apiResultServerImpl,this.shujumoheServerImpl,request));
        poolExecutor.shutdown();

    }
}
