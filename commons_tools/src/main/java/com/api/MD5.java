
package com.api;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * ClassName:MD5 <br/>
 * Description: md5加密工具类. <br/>
 * Date:     2015年6月30日 上午10:30:23 <br/>
 * @author   caowenyu 
 * @since    JDK 1.7
 * @see 	 
 */
public class MD5 {
	/**
	 * 全局数组
	 */
    private static final  String[] DIGITS = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "H", "i", 
            "j", "k", "l", "m", "n", "~", "$", "@", "%", "*", "#", "&", "!" };

    public MD5() {
    }

    /**
     * byteToArrayString:(返回形式为数字跟字符串). <br/>
     * @author wz
     * @param bByte byte
     * @return 返回形式为数字跟字符串
     */
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 32;
        int iD2 = iRet % 32;
        return DIGITS[iD1] + DIGITS[iD2];
    }

    /**
     * byteToNum:(返回形式只为数字). <br/>
     * @param bByte byte
     * @return 返回形式只为数字
     */
    private static String byteToNum(byte bByte) {
        int iRet = bByte;
        if (iRet < 0) {
            iRet += 256;
        }
        return String.valueOf(iRet);
    }

    /**
     * byteToString:(转换字节数组为16进制字串). <br/>
     * @param bByte byte数组
     * @return 返回转换字节数组为16进制字串
     */
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }
    
    /**
     * GetMD5Code:(md5加密). <br/>
     * @author wz
     * @param param 需要加密的字段
     * @return 加密后的字段
     */
    public static String GetMD5Code(String param) {
        String resultString = null;
        try {
            resultString = new String(param);
            MessageDigest md = MessageDigest.getInstance("MD5");
            // md.digest() 该函数返回值为存放哈希值结果的byte数组
            resultString = byteToString(md.digest(param.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return resultString;
    }
	
	private static  final String key = "5m2c70102l122*2H7&692#7!356%390c"; //sztIntegration的MD5值  key我们统一用这个值
	
	/**
	 * 外部数据源普通验证
	 * param mobileNo 电话号码
	 * param d 时间戳
	 */
	public static Boolean validateOrdinarySign(String  mobileNo,String d,String sign){
		StringBuffer sb=new StringBuffer();
		sb.append(mobileNo);
        sb.append("&");
        sb.append(d);
		sb.append("&");
		sb.append(key);
		System.out.println("sb:"+MD5.GetMD5Code(sb.toString()));
		return MD5.GetMD5Code(sb.toString()).equalsIgnoreCase(sign);
	}

    /**
     * 获取手机号验签
     * @param mobile
     * @param d
     * @return
     */
	public static String getSign(String mobile, String d){
        StringBuffer sb=new StringBuffer();
        sb.append(mobile);
        sb.append("&");
        sb.append(d);
        sb.append("&");
        sb.append(key);
        return MD5.GetMD5Code(sb.toString());
    }

    public static void main(String[] args) {
        long d = System.currentTimeMillis();
        System.out.println(d);
        System.out.println(getSign("18100628955",String.valueOf(d)));
    }

}

