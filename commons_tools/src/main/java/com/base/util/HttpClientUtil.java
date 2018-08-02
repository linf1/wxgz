package com.base.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年02月13日<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) Vbill Co.,Ltd.<br>
 * </strong>
 * <p>
 *
 * @author department:技术开发部 <br>
 *         username:lijf <br>
 *         email: <br>
 * @version <strong>zw有限公司-运营平台</strong><br>
 *          <br>
 *          <strong>修改历史:</strong><br>
 *          修改人 修改日期 修改描述<br>
 *          -------------------------------------------<br>
 *          <br>
 *          <br>
 */
public class HttpClientUtil {
    private static RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(15000).setConnectTimeout(15000)
            .setConnectionRequestTimeout(15000).build();

    public static RequestConfig getRequestConfig() {
        return requestConfig;
    }

    public static void setRequestConfig(RequestConfig requestConfig1) {
        requestConfig = requestConfig1;
    }

    private static HttpClientUtil instance = new HttpClientUtil();

    private HttpClientUtil() {
    }

    public static HttpClientUtil getInstance() {
        return instance;
    }

    /**
     * 发送 post请求
     *
     * @param httpUrl
     *            地址
     */
    public String sendHttpPost(String httpUrl) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        return sendHttpPost(httpPost);
    }

    /**
     * 发送 post请求
     *
     * @param httpUrl
     *            地址
     * @param params
     *            参数(格式:key1=value1&key2=value2)
     */
    public String sendHttpPost(String httpUrl, String params) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        try {
            // 设置参数
            StringEntity stringEntity = new StringEntity(params, "UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendHttpPost(httpPost);
    }

    /**
     * 发送 post请求
     *
     * @param httpUrl
     *            地址
     * @param params
     *            参数(格式:key1=value1&key2=value2)
     * @param token
     *             请求头信息token
     */
    public String sendHttpPostByXiha(String httpUrl, String params,String token) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        try {
            // 设置参数
            StringEntity stringEntity = new StringEntity(params, "UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            httpPost.setHeader("token",token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendHttpPost(httpPost);
    }

    /**
     * 发送 post请求
     *
     * @param httpUrl
     *            地址
     * @param params
     *            参数(格式:key1=value1&key2=value2)
     */
    public String sendHttpPost(String httpUrl, String params,boolean flag) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        try {
            // 设置参数
            StringEntity stringEntity = new StringEntity(params, "UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendHttpPost(httpPost,flag);
    }

    /**
     * 发送 post请求
     *
     * @param httpUrl
     *            地址
     */
    public String sendHttpPost(String httpUrl, String params, String contentType) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        try {
            // 设置参数
            StringEntity stringEntity = new StringEntity(params, "UTF-8");
            stringEntity.setContentType(contentType);
            httpPost.setEntity(stringEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendHttpPost(httpPost);
    }

    public String sendHttpPost(String httpUrl, Map<String, Object> maps,
                               String contentType) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        // 创建参数队列
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        for (String key : maps.keySet()) {
            nameValuePairs.add(new BasicNameValuePair(key,
                    maps.get(key) == null ? null : maps.get(key).toString()));
        }
        try {
            StringEntity stringEntity = new UrlEncodedFormEntity(
                    nameValuePairs, "UTF-8");
            stringEntity.setContentType(contentType);
            httpPost.setEntity(stringEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendHttpPost(httpPost);
    }

    /**
     * 发送 post请求
     *
     * @param httpUrl
     *            地址
     * @param maps
     *            参数
     */
    public String sendHttpPost(String httpUrl, Map<String, String> maps) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        // 创建参数队列
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        for (String key : maps.keySet()) {
            nameValuePairs.add(new BasicNameValuePair(key, maps.get(key)));
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendHttpPost(httpPost);
    }

    /**
     * 发送 post请求（带文件）
     *
     * @param httpUrl
     *            地址
     * @param maps
     *            参数
     * @param fileLists
     *            附件
     */
    public String sendHttpPost(String httpUrl, Map<String, String> maps,
                               List<File> fileLists) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        MultipartEntityBuilder meBuilder = MultipartEntityBuilder.create();
        for (String key : maps.keySet()) {
            meBuilder.addPart(key, new StringBody(maps.get(key),
                    ContentType.TEXT_PLAIN));
        }
        for (File file : fileLists) {
            FileBody fileBody = new FileBody(file);
            meBuilder.addPart("file", fileBody);
        }
        HttpEntity reqEntity = meBuilder.build();
        httpPost.setEntity(reqEntity);
        return sendHttpPost(httpPost);
    }

    /**
     * 下载文件 方法描述
     *
     * @param url
     *            地址
     * @param destFileName
     *            存储在本地的路径
     * @throws Exception
     */
    public void getFile(String url, String destFileName) throws Exception {
        // 生成一个httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        HttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        InputStream in = entity.getContent();
        File file = new File(destFileName);
        try {
            FileOutputStream fout = new FileOutputStream(file);
            int l = -1;
            byte[] tmp = new byte[1024];
            while ((l = in.read(tmp)) != -1) {
                fout.write(tmp, 0, l);
            }
            fout.flush();
            fout.close();
        } finally {
            // 关闭低层流。
            in.close();
        }
        httpclient.close();
    }

    public static void main(String[] args) throws Exception {

        // Map map = new HashMap<String, String>();
        // map.put("filename", "1234333335.png");
        // map.put("empId", "1");
        // map.put("bgCustId", "4028973157e149f30157e14a6a500002");
        // map.put("paperTypeId", "9");
        // List<File> list = new ArrayList<File>();
        // list.add(new File("d:/123.png"));
        // System.out.println(HttpClientUtil.getInstance().sendHttpPost(
        // "http://116.236.220.212:8081/cf_api/remote/file", map, list));
        // ;

        HttpClientUtil
                .getInstance()
                .getFile(
                        "http://116.236.220.212:8081/cf_api//crm/customer/paper/down?id=2c908a8257fa7e8c01580a127c7d0003",
                        "D:/12345555335.png");

    }

    /**
     * 发送Post请求
     *
     * @param httpPost
     * @return
     */
    private String sendHttpPost(HttpPost httpPost,boolean retryHandler) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            httpClient = HttpClients.createDefault();
            if(retryHandler){
                HttpRequestRetryHandler handler = new HttpRequestRetryHandler() {
                    @Override
                    public boolean retryRequest(IOException arg0, int retryTimes, HttpContext arg2) {
                        return false;
                    }
                };
                httpClient = HttpClients.custom().setRetryHandler(handler).build();
                //httpClient.getParams().setParameter("HttpRequestRetryHandler",new DefaultHttpRequestRetryHandler(0, false));
            }

            httpPost.setConfig(requestConfig);
            httpPost.addHeader("Authorization",
                    SignatureRemindUtil.createSign());
			/*
			 * httpPost.addHeader("Accept-Charset", "UTF-8");
			 * httpPost.addHeader("content-type", "application/json");
			 * httpPost.addHeader("Accept", "application/json");
			 */
            // 执行请求
            response = httpClient.execute(httpPost);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭连接,释放资源
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }

    /**
     * 发送Post请求
     *
     * @param httpPost
     * @return
     */
    private String sendHttpPost(HttpPost httpPost) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            httpClient = HttpClients.createDefault();
            httpPost.setConfig(requestConfig);
            httpPost.addHeader("Authorization",
                    SignatureRemindUtil.createSign());
			/*
			 * httpPost.addHeader("Accept-Charset", "UTF-8");
			 * httpPost.addHeader("content-type", "application/json");
			 * httpPost.addHeader("Accept", "application/json");
			 */
            // 执行请求
            response = httpClient.execute(httpPost);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭连接,释放资源
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }

    /**
     * 发送 get请求
     *
     * @param httpUrl
     */
    public String sendHttpGet(String httpUrl) throws ConnectTimeoutException {
        HttpGet httpGet = new HttpGet(httpUrl);// 创建get请求
        return sendHttpGet(httpGet);
    }

    /**
     * 发送Get请求
     *
     * @param
     * @return
     */
    private String sendHttpGet(HttpGet httpGet) throws ConnectTimeoutException {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            httpClient = HttpClients.createDefault();
            httpGet.addHeader("Authorization", SignatureRemindUtil.createSign());
            httpGet.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(httpGet);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ConnectTimeoutException();
        } finally {
            try {
                // 关闭连接,释放资源
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }



    /**
     *
     * 功能说明：POST 请求得到数据
     * panye  2017-7-18
     * @param 
     * @return   返回得到的结果集，如果出现异常或者连接超时	 则返回空
     * @throws  该方法可能抛出的异常，异常的类型、含义。
     * 最后修改时间：
     * 修改人：panye
     * 修改内容：
     * 修改注意点：
     */
    public static String sendPost(String url,Map<String, Object>paramMap) {

        HttpPost httpRequset = new HttpPost(url);
        // 使用NameValuePair来保存要传递的Post参数
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        //加载参数
        if(paramMap!=null){
            Set<String> keys = paramMap.keySet();
            NameValuePair[] data = new NameValuePair[keys.size()];
            Iterator it = keys.iterator();
            while(it.hasNext()){
                String key = String.valueOf(it.next());
                params.add(new BasicNameValuePair(key, paramMap.get(key).toString()));
            }
        }

        try {
            // 设置字符集
            HttpEntity httpentity = new UrlEncodedFormEntity(params, "utf-8");
            // 请求httpRequset
            httpRequset.setEntity(httpentity);
            // 取得HttpClient
            HttpClient httpClient = new DefaultHttpClient();
            //设置超时时间
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,10000);
            // 取得HttpResponse
            HttpResponse httpResponse = httpClient.execute(httpRequset);
            if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {

                return "";
            }

            HttpEntity entity = httpResponse.getEntity();
            if (null != entity) {

                return EntityUtils.toString(httpResponse.getEntity());
            }

        } catch (Exception e) {
            //logger.warn(e.getMessage(),e);
            return "";
        }

        return "";
    }
}

