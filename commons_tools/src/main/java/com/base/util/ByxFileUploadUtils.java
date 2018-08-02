package com.base.util;

import com.exception.TextProperties;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;

public class ByxFileUploadUtils {
    public static final String ENCODING="UTF-8";

    public static String uploadFile(String path, MultipartFile file) {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setCharset(Charset.forName(HTTP.UTF_8));//设置请求的编码格式
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        //HttpEntity entity= builder.addPart("file", new FileBody(file)).build();

        String result = null;
        HttpClient httpClient = HttpClients.createDefault();
        try {
            HttpEntity entity= builder.addBinaryBody("file",file.getInputStream(),ContentType.DEFAULT_BINARY,file.getOriginalFilename()).build();
            HttpPost httpRequest = new HttpPost(path);
            String userAgent = "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.87 Safari/537.36";
            httpRequest.setHeader("User-Agent",userAgent);
            if(entity!=null){
                httpRequest.setEntity(entity);
            }
            HttpResponse httpResponse = httpClient.execute(httpRequest);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = httpResponse.getEntity();
                if (httpEntity != null) {
                    result = EntityUtils.toString(httpEntity, ENCODING);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return result;
    }

    public static boolean deleteFile(String path){
        HttpDelete httpRequest = new HttpDelete(path);
        HttpClient httpClient = HttpClients.createDefault();
        try {
            HttpResponse httpResponse = httpClient.execute(httpRequest);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return true;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        String result=ByxFileUploadUtils.uploadFile(TextProperties.instance().get("upload.url"),null);
        System.out.println(result);
    }
}
