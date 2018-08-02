package com.base.util;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * 功能：MD5签名处理核心文件
 * GAOZHIDONG 2017/08/02
 * 益倍佳公用方法
 */
public class PayUtil {
    /**
     * 得到日期字符串
     *
     * @param format
     * @return
     */
    public static String getDateString(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }

    /**
     * 得到缺省日期字符串
     * @return
     */
    public static String getDefaultDateString() {
        return getDateString("yyyMMddHHmmssSSS");
    }

    /**
     * 得到参数字符串的集合
     * @param parameters
     * @return
     */
    public static Map getParameterMap(String parameters) {
        String [] params = parameters.split("&");
        Map paramMap = new HashMap();
        for(String param : params) {
            String[] keyValue = param.split("=");
            if(keyValue.length >= 2){
                paramMap.put(keyValue[0], keyValue[1]);
            }
            else if(keyValue.length == 1){
                paramMap.put(keyValue[0], null);
            }
        }
        return paramMap;
    }
}
