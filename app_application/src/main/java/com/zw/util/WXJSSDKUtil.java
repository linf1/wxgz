package com.zw.util;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

/**
 * @Author xiahaiyang
 * @Create 2017年11月8日10:04:16
 **/
public class WXJSSDKUtil {

    /**
     * 从微信获取临时素材，并保存到自己的服务器
     * @param request
     * @param accessToken
     * @param mediaId
     * @return
     */
    public static String saveImageToDisk(HttpServletRequest request, String accessToken, String mediaId) {
        String saveUrl = "";
        InputStream inputStream = getMedia(accessToken,mediaId);
        if (inputStream == null){
            return saveUrl;
        }
        byte[] data = new byte[1024];
        int len = 0;
        FileOutputStream fileOutputStream = null;
        try {
            //获取根目录
            String root = request.getSession().getServletContext().getRealPath("/wechat_file");
            //文件名
            String filename = UUID.randomUUID().toString() + ".jpg";
            //先创建文件夹，避免fileOutputStream报错
            File file = new File(root + File.separator + "customer_order_img");
            if (!file.exists()){
                file.mkdirs();
            }
            String url = root + "/" + "customer_order_img"+ "/" + filename;
            fileOutputStream = new FileOutputStream(url);
            while ((len = inputStream.read(data)) != -1) {
                fileOutputStream.write(data, 0, len);
            }
            saveUrl = "/wechat_file/" + "customer_order_img" + "/" +filename;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return saveUrl;
        }
    }

    /**
     * 从微信获取图片
     * @param request
     * @param accessToken
     * @param mediaId
     * @return
     */
    public static InputStream getImageFromWx(HttpServletRequest request, String accessToken, String mediaId){
        InputStream inputStream = getMedia(accessToken,mediaId);
        return inputStream;
    }


    /**
     * 从微信获取临时素材
     * @param accessToken
     * @param mediaId
     * @return
     */
    private static InputStream getMedia(String accessToken, String mediaId) {
        String url = "https://api.weixin.qq.com/cgi-bin/media/get";
        String params = "access_token=" + accessToken + "&media_id=" + mediaId;
        InputStream is = null;
        try {
            String urlNameString = url + "?" + params;
            URL urlGet = new URL(urlNameString);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            http.connect();
            // 获取文件转化为byte流
            is = http.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
            return is;
        }
        return is;
    }
}
