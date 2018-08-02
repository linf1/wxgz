package com.base.util;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/5 0005.
 */
public class TokenUtil {
    public static String getToken(String phone){
        String str = phone+System.currentTimeMillis();
        return Hashing.sha1().hashString(str, Charsets.UTF_8).toString().toUpperCase();
    }
    public static boolean isTimeOut(Map map,String timeOut){
        long time =  Long.parseLong(map.get("token_time")+"" == null ? "0" : map.get("token_time")+"") + Integer.parseInt(timeOut) * 60 * 60 *1000;
        long newTime = new Date().getTime();
        if(time < newTime){
            return true;
        }
        return false;
    }
    public static void main(String[] args) {
        System.out.println(getToken("18895365564"));
    }

}
