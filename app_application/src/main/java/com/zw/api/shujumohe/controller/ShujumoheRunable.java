package com.zw.api.shujumohe.controller;

import com.alibaba.fastjson.JSONObject;
import com.api.model.result.ApiResult;
import com.api.model.shujumohe.ShujumoheRequest;
import com.api.service.result.IApiResultServer;
import com.api.service.shujumohe.IShujumoheServer;
import com.base.util.StringUtils;
import com.constants.ApiConstants;
import com.enums.EApiSourceEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 数据魔盒异步保存数据
 * @author 陈清玉
 */
public class ShujumoheRunable implements Runnable {

    private final Logger LOGGER = LoggerFactory.getLogger(ShujumoheRunable.class);

    private IApiResultServer apiResultServer;
    private IShujumoheServer shujumoheServer;
    private ShujumoheRequest request;
    /**
     * 是否完成
     */
    private boolean done = false;

    public ShujumoheRunable(IApiResultServer apiResultServer, IShujumoheServer shujumoheServer,ShujumoheRequest request) {
        this.apiResultServer = apiResultServer;
        this.shujumoheServer = shujumoheServer;
        this.request = request;
    }

    /**
     * 获取魔盒数据
     * @throws Exception 异常
     */
    private void doRun() throws Exception {
        String  result = shujumoheServer.callbackShujumohe(request);
        if (StringUtils.isNotEmpty(result)) {
            final JSONObject jsonObject = JSONObject.parseObject(result);
            final String code = jsonObject.getString("code");
            if(ApiConstants.STATUS_SUCCESS.equals(code)){
                final Map data = (Map) jsonObject.get("data");
                if(data != null) {
                    ApiResult apiResult = new ApiResult();
                    apiResult.setOnlyKey(request.getCustomerId());
                    apiResult.setSourceCode(EApiSourceEnum.MOHE.getCode());
                    apiResult.setResultData(data.get("task_data").toString());
                    //吧获取的数据更新到数据库
                    apiResultServer.updateByOnlyKey(apiResult);
                    //数据获取成功就不在轮询
                    this.done = true;
                    LOGGER.info("----异步调用数据魔盒远程API获取数据成功并且更新数据----");
                }
            }
        }
        if(!this.done) {
            //每次如果没有查询到数据就休眠
            doSleep();
        }
    }

    /**
     * 休眠
     * @throws InterruptedException 休眠异常
     */
    private void doSleep() throws InterruptedException {
        //休眠时间
        final int sleepMillis  = 5000;
        Thread.sleep(sleepMillis);
    }

    @Override
    public void run()  {
        //重试次数
        final int countNum = 20;
        int i = 0;
        //如果没有获得数据就继续查询数据
        do {
            LOGGER.info("----异步调用数据魔盒远程API获取数据：{}次----", (i+1));
            if(i == countNum){return;}
            try {
                doRun();
            } catch (Exception e) {
                e.printStackTrace();
            }
            i++;
        }while (!done);
    }
}
