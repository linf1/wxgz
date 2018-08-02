package com.zw.miaofuspd.api.shujumohe;

import com.api.HttpUtil;
import com.api.model.shujumohe.ShujumoheRequest;
import com.api.model.shujumohe.ShujumoheSettings;
import com.api.service.shujumohe.IShujumoheServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 魔盒服务实现类
 * @author 陈清玉
 */
@Service(IShujumoheServer.BEAN_KEY)
public class ShujumoheServerImpl  implements IShujumoheServer {

    @Autowired
    private ShujumoheSettings shujumoheSettings;

    @Override
    public String callbackShujumohe(ShujumoheRequest request) {
        String url = shujumoheSettings.getRequestUrl() + shujumoheSettings.getOperatorApi();
            url += "?partner_code=" + shujumoheSettings.getPartnerCode() + "&partner_key=" +shujumoheSettings.getPartnerKey();
        Map<String,Object> parameterMap = new HashMap<>(1);
        parameterMap.put("task_id",request.getTask_id());
        final String result = HttpUtil.doPostSSL(url, parameterMap);
        return result;
    }
}
