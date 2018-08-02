package com.zw.util;

/**
 * Created by Guoqing on 2017/4/24.
 * 获取登录，注册的数字验证码
 */
public class IdentifyCodeUtil {

    /**获取验证码*/
    public static String getIdentifyCode(){
        String str="0123456789";
        StringBuilder sb=new StringBuilder(6);
        for(int i=0;i<6;i++)
        {
            int len = Integer.parseInt(String.valueOf(Math.random()*str.length()).split("\\.")[0]);
            char ch=str.charAt(len);
            sb.append(ch);
        }
        return sb.toString();
    }
    /**获取验证码*/
    public static String getIdentifyCode4(){
        String str="0123456789";
        StringBuilder sb=new StringBuilder(4);
        for(int i=0;i<4;i++)
        {
            int len = Integer.parseInt(String.valueOf(Math.random()*str.length()).split("\\.")[0]);
            char ch=str.charAt(len);
            sb.append(ch);
        }
        return sb.toString();
    }
    /**
     * 获取30位随机数
     * @return
     */
    public static String getFlowCode(){
        String str="0123456789";
        StringBuilder sb=new StringBuilder(6);
        for(int i=0;i<30;i++)
        {
            int len = Integer.parseInt(String.valueOf(Math.random()*str.length()).split("\\.")[0]);
            char ch=str.charAt(len);
            sb.append(ch);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(getIdentifyCode());
    }

}
