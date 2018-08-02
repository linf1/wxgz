package com.api.service.shujumohe;

import com.api.model.shujumohe.ShujumoheRequest;

/**
 * 魔盒服务
 * @author 陈清玉
 */
public interface IShujumoheServer {
    String BEAN_KEY = "shujumoheServerImpl";
    /**
     * 回调数据
     * @param request
     * @return
     */
   String callbackShujumohe(ShujumoheRequest request);


}
