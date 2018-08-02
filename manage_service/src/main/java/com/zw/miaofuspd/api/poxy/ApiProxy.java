package com.zw.miaofuspd.api.poxy;

import com.api.HttpUtil;
import com.api.model.common.BaseSettings;
import com.base.util.StringUtils;
import org.apache.http.Header;

import java.util.List;

/**
 * api接口调用代理
 * @author 陈淸玉
 */
public class ApiProxy {
    private final String GET = "get";
    private final String POST = "post";

    private BaseSettings settings;

    public ApiProxy(BaseSettings settings){
        this.settings = settings;
    }

    /**
     * API调用方法
     * @param param 请求参数
     * @param headerList 请求头
     * @return 返回信息
     */
    public String  apiInvoke(String param,List<Header> headerList){
        if(StringUtils.isNotBlank(settings.getRequestUrl()) && StringUtils.isNotBlank(settings.getRequestType())){
            if (POST.equalsIgnoreCase(settings.getRequestType())) {
                if (isHttps(settings.getRequestUrl())) {
                    return HttpUtil.postSSL(settings.getRequestUrl(), param, headerList);
                } else {
                    return HttpUtil.post(settings.getRequestUrl(), param, headerList);
                }
            } else if (GET.equalsIgnoreCase(settings.getRequestType())) {
                return HttpUtil.getSSL(settings.getRequestUrl(), headerList);
            }
        }
        return "";
    }

    /**
    * 检测是否https
    * @param url 请求地址
    */
     private  boolean isHttps(String url) {
         return url.startsWith("https");
     }



}
