package com.zw.util;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.base.util.FileNewName;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : </strong>@一期项目文件上传工具类，迁移至此@<br>
 * <strong>Create on : 2017年03月06日 13:04<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) Vbill Co.,Ltd.<br>
 * </strong>
 * <p>
 *
 * @author department:技术开发部 <br>
 *         username:吴城 <br>
 *         email: <br>
 * @version <strong>zw有限公司-运营平台</strong><br>
 *          <br>
 *          <strong>修改历史:</strong><br>
 *          修改人 修改日期 修改描述<br>
 *          -------------------------------------------<br>
 *          <br>
 *          <br>
 */

public class UploadFileUtil {

    /**
     * 现金贷保存客户端上传文件
     *
     * @param request http request
     * @param saveDir 文件存储的路径
     * @return
     * @throws Exception
     */
    public static Map<String, Object> getFileCash(HttpServletRequest request, String saveDir) throws Exception {

        // 上传配置
        int MEMORY_THRESHOLD = 1024 * 1024 * 3; // 3MB
        int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
        int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

        // 配置上传参数
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // 设置临时存储目录
        File saveDirFile = new File(saveDir);
        // if (saveDirFile.isDirectory()) {
        if (!saveDirFile.exists()) {
            saveDirFile.mkdirs();
        }
        /*
         * } else { saveDir = System.getProperty("java.io.tmpdir"); }
		 */
        factory.setRepository(new File(saveDir));

        ServletFileUpload upload = new ServletFileUpload(factory);

        // 设置最大文件上传值
        upload.setFileSizeMax(MAX_FILE_SIZE);

        // 设置最大请求值 (包含文件和表单数据)
        upload.setSizeMax(MAX_REQUEST_SIZE);
        Map<String, Object> map = new HashMap();
        List<FileItem> formItems = upload.parseRequest(request);
        List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();
        if (formItems != null && formItems.size() > 0) {
            // 迭代表单数据
            for (FileItem item : formItems) {
                // 处理不在表单中的字段
                //获取随机的文件名
                String fileName = FileNewName.gatFileName();
                if (!item.isFormField()) {
                    Map<String, Object> fileModel = new HashMap<String, Object>();
                    String path = saveDir + File.separator + fileName;
                    File file = new File(path);
                    if (file.exists()) {
                        file.delete();
                    }
                    item.write(file);
                    fileModel.put("originalName", item.getName());
                    fileModel.put("file", file);
                    fileModel.put("Name", fileName);
                    fileList.add(fileModel);
                } else {
                    String name = item.getFieldName(); // 获取name属性的值
                    String value = item.getString("utf-8"); // 获取value属性的值
                    map.put(name, value);
                }
                item.delete();
            }
        }
        map.put("fileList", fileList);
        return map;
    }

    /**
     * 保存客户端上传文件
     *
     * @param request http request
     * @param saveDir 文件存储的路径
     * @return
     * @throws Exception
     */
    public static Map<String, Object> getFile(HttpServletRequest request, String saveDir, String fileName) throws Exception {

        // 上传配置
        int MEMORY_THRESHOLD = 1024 * 1024 * 3; // 3MB
        int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
        int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

        // 配置上传参数
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // 设置临时存储目录
        File saveDirFile = new File(saveDir);
        // if (saveDirFile.isDirectory()) {
        if (!saveDirFile.exists()) {
            saveDirFile.mkdirs();
        }
        /*
         * } else { saveDir = System.getProperty("java.io.tmpdir"); }
		 */
        factory.setRepository(new File(saveDir));

        ServletFileUpload upload = new ServletFileUpload(factory);

        // 设置最大文件上传值
        upload.setFileSizeMax(MAX_FILE_SIZE);

        // 设置最大请求值 (包含文件和表单数据)
        upload.setSizeMax(MAX_REQUEST_SIZE);
        Map<String, Object> map = new HashMap();
        List<FileItem> formItems = upload.parseRequest(request);
        List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();
        if (formItems != null && formItems.size() > 0) {
            // 迭代表单数据
            for (FileItem item : formItems) {
                // 处理不在表单中的字段
                if (!item.isFormField()) {
                    Map<String, Object> fileModel = new HashMap<String, Object>();
                    String path = saveDir + File.separator + fileName;
                    File file = new File(path);
                    if (file.exists()) {
                        file.delete();
                    }
                    item.write(file);
                    fileModel.put("originalName", item.getName());
                    fileModel.put("file", file);
                    fileModel.put("Name", fileName);
                    fileList.add(fileModel);
                } else {
                    String name = item.getFieldName(); // 获取name属性的值
                    String value = item.getString("utf-8"); // 获取value属性的值
                    map.put(name, value);
                }
                item.delete();
            }
        }
        map.put("fileList", fileList);
        return map;
    }

    public static Map<String, Object> getFileNew(HttpServletRequest request, String saveDir, String fileName) throws Exception {
        // 上传配置
        int MEMORY_THRESHOLD = 1024 * 1024 * 3; // 3MB
        int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
        int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

        // 配置上传参数
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // 设置临时存储目录
        File saveDirFile = new File(saveDir);
        if (saveDirFile.isDirectory()) {
            if (!saveDirFile.exists()) {
                saveDirFile.mkdirs();
            }
        } else {
            saveDir = System.getProperty("java.io.tmpdir");
        }
        factory.setRepository(new File(saveDir));

        ServletFileUpload upload = new ServletFileUpload(factory);

        // 设置最大文件上传值
        upload.setFileSizeMax(MAX_FILE_SIZE);

        // 设置最大请求值 (包含文件和表单数据)
        upload.setSizeMax(MAX_REQUEST_SIZE);
        Map<String, Object> map = new HashMap();
        List<FileItem> formItems = upload.parseRequest(request);
        List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();
        if (formItems != null && formItems.size() > 0) {
            // 迭代表单数据
            for (FileItem item : formItems) {
                // 处理不在表单中的字段
                if (!item.isFormField()) {
                    String picName = item.getName();
                    picName = picName.substring(picName.lastIndexOf("\\") + 1);
                    String extName = picName
                            .substring(picName.lastIndexOf("."));

                    fileName = fileName.replace("-", "") + extName;

                    String path = saveDir + File.separator + fileName;

                    FileUtils.copyInputStreamToFile(item.getInputStream(),
                            new File(path));

                    Map<String, Object> fileModel = new HashMap<String, Object>();
                    fileModel.put("originalName", item.getName());
                    fileModel.put("Name", fileName);
                    fileList.add(fileModel);
                } else {
                    String name = item.getFieldName(); // 获取name属性的值
                    String value = item.getString("utf-8"); // 获取value属性的值
                    map.put(name, value);
                }
            }
        }
        map.put("fileList", fileList);
        return map;
    }

    /**
     * 保存客户端上传文件,默认先保存在临时目录中
     *
     * @param request http request
     * @return
     * @throws Exception
     */
    public static Map<String, Object> getFile(HttpServletRequest request)
            throws Exception {
        String saveDir = System.getProperty("java.io.tmpdir");
        String fileName = UUID.randomUUID().toString();
        return getFile(request, saveDir, fileName);
    }


    public static Map<String, Object> getFilelist(HttpServletRequest request)
            throws Exception {
        String saveDir = System.getProperty("java.io.tmpdir");
        String fileName = UUID.randomUUID().toString();
        return getFilelist(request, saveDir);
    }

    public static Map<String, Object> getFilelist(HttpServletRequest request, String saveDir) throws Exception {

        // 上传配置
        int MEMORY_THRESHOLD = 1024 * 1024 * 3; // 3MB
        int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
        int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

        // 配置上传参数
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // 设置临时存储目录
        File saveDirFile = new File(saveDir);
        // if (saveDirFile.isDirectory()) {
        if (!saveDirFile.exists()) {
            saveDirFile.mkdirs();
        }
        /*
         * } else { saveDir = System.getProperty("java.io.tmpdir"); }
		 */
        factory.setRepository(new File(saveDir));

        ServletFileUpload upload = new ServletFileUpload(factory);

        // 设置最大文件上传值
        upload.setFileSizeMax(MAX_FILE_SIZE);

        // 设置最大请求值 (包含文件和表单数据)
        upload.setSizeMax(MAX_REQUEST_SIZE);
        Map<String, Object> map = new HashMap();
        List<FileItem> formItems = upload.parseRequest(request);
        List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();
        if (formItems != null && formItems.size() > 0) {
            String fileName;
            // 迭代表单数据
            for (FileItem item : formItems) {
                // 处理不在表单中的字段
                fileName = UUID.randomUUID().toString();
                if (!item.isFormField()) {
                    Map<String, Object> fileModel = new HashMap<String, Object>();
                    String path = saveDir + File.separator + fileName;
                    File file = new File(path);
                    if (file.exists()) {
                        file.delete();
                    }
                    item.write(file);
                    fileModel.put("originalName", item.getName());
                    fileModel.put("file", file);
                    fileModel.put("Name", fileName);
                    fileList.add(fileModel);
                } else {
                    String name = item.getFieldName(); // 获取name属性的值
                    String value = item.getString("utf-8"); // 获取value属性的值
                    map.put(name, value);
                }
                item.delete();
            }
        }
        map.put("fileList", fileList);
        return map;
    }

    private static OSSClient ossClient = null;

    /**
     * 上传文件OSS
     *
     * @param request         http request
     * @param saveDir         文件临时存储的根目录
     * @param saveDirOSS      OSS根目录名
     * @param fileName        文件名称(包括目录/文件名)
     * @param endpoint        oss访问域名和数据中心
     * @param accessKeyId     OSS:accessKeyId
     * @param accessKeySecret OSS:acessKeySecretc
     * @param catalog         OSS:二级目录
     * @return
     * @throws Exception
     */
    public static Map<String, Object> getFileOSS(HttpServletRequest request, String saveDir, String fileName, String catalog, String saveDirOSS, String endpoint, String accessKeyId, String accessKeySecret) throws Exception {

        // 上传配置
        int MEMORY_THRESHOLD = 1024 * 1024 * 3; // 3MB
        int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
        int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

        // 配置上传参数
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // 设置临时存储目录
        File saveDirFile = new File(saveDir);
        // if (saveDirFile.isDirectory()) {
        if (!saveDirFile.exists()) {
            saveDirFile.mkdirs();
        }
        /*
		 * } else { saveDir = System.getProperty("java.io.tmpdir"); }
		 */
        factory.setRepository(new File(saveDir));

        ServletFileUpload upload = new ServletFileUpload(factory);

        // 设置最大文件上传值
        upload.setFileSizeMax(MAX_FILE_SIZE);

        // 设置最大请求值 (包含文件和表单数据)
        upload.setSizeMax(MAX_REQUEST_SIZE);
        Map<String, Object> map = new HashMap();
        List<FileItem> formItems = upload.parseRequest(request);
        List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();
        ossClient = new OSSClient("https://" + endpoint, accessKeyId, accessKeySecret);
        if (formItems != null && formItems.size() > 0) {
            // 迭代表单数据
            for (int i = 0; i < formItems.size(); i++) {
                FileItem item = formItems.get(i);
                // 处理不在表单中的字段
                if (!item.isFormField()) {
                    //获取后缀名
                    String suffixName = item.getName();
                    suffixName = suffixName.substring(suffixName.lastIndexOf(".") + 1);
                    fileName = fileName + i + "." + suffixName;
                    Map<String, Object> fileModel = new HashMap<String, Object>();
                    String path = saveDir + File.separator + fileName;
                    File file = new File(path);
                    if (file.exists()) {
                        file.delete();
                    }
                    //写到本地
                    item.write(file);
                    //上传的OSS
                    ossClient.putObject(saveDirOSS, catalog + fileName, file);
                    fileModel.put("originalName", item.getName());
                    fileModel.put("file", file);
                    fileModel.put("Name", "https://" + saveDirOSS + "." + endpoint + "/" + catalog + fileName);
                    fileList.add(fileModel);
                    //删除本地文件
                    file.delete();
                } else {
                    String name = item.getFieldName(); // 获取name属性的值
                    String value = item.getString("utf-8"); // 获取value属性的值
                    map.put(name, value);
                }
                item.delete();
            }
        }
        // 关闭client
        ossClient.shutdown();
        map.put("fileList", fileList);
        return map;
    }

    /**
     * app上传图片至OSS
     */
    public static Map uploadToOSS(Map<String , String> srcMap, String imgPath, String bucketName, String endpoint, String accessKeyId, String accessKeySecret) throws Exception {
        //解密
        Map<String , String> photoMap = new HashMap<String , String>();
        BASE64Decoder decoder = new BASE64Decoder();
        for(Map.Entry<String , String> entry : srcMap.entrySet()){
            String str = entry.getValue();
            String key = entry.getKey();
            str = str.replace("data:image/png;base64,", "").replace("}", "");
            byte[] b = decoder.decodeBuffer(str);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            String id = UUID.randomUUID().toString();
            String imgFilePath = imgPath + id + ".jpg";//新生成的图片
            File file = new File(imgFilePath);
            OutputStream out = new FileOutputStream(file);
            out.write(b);
            out.flush();
            out.close();
            String appUrl = appOss(file, bucketName, endpoint, accessKeyId, accessKeySecret);
            photoMap.put(key,appUrl);
        }
        return photoMap;
    }
    /**
     * app上传图片至OSS
     */
    public static String uploadFaceToOSS(String faceSrc, String imgPath, String bucketName, String endpoint, String accessKeyId, String accessKeySecret) throws Exception {
        //解密
        BASE64Decoder decoder = new BASE64Decoder();
        String str = faceSrc;
        str = str.replace("data:image/png;base64,", "").replace("}", "");
        byte[] b = decoder.decodeBuffer(str);
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {
                b[i] += 256;
            }
        }
        String id = UUID.randomUUID().toString();
        String imgFilePath = imgPath+ id + ".jpg";//新生成的图片
        File file = new File(imgFilePath);
        OutputStream out = new FileOutputStream(file);
        out.write(b);
        out.flush();
        out.close();
        String appUrl = appOss(file, bucketName, endpoint, accessKeyId, accessKeySecret);
        return appUrl;
    }
    /**
     * app上传图片至OSS
     *
     * @param bucketName 存储空间
     * @return String 返回的唯一MD5数字签名
     */
    public static String imageToOss(MultipartFile MFile,String path, String bucketName, String endpoint, String accessKeyId, String accessKeySecret){
        ossClient = new OSSClient("https://" + endpoint, accessKeyId, accessKeySecret);
        String resultStr = null;
        try {
            File file = new File(path);
            MFile.transferTo(file);
            String fileName =file.getName();
            PutObjectResult putResult = ossClient.putObject(bucketName, fileName, file);
            //删除本地文件
            resultStr = "https://" + bucketName + "." + endpoint + "/" + fileName;
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultStr;
    }

    /**
     * app上传图片至OSS
     *
     * @param bucketName 存储空间
     * @return String 返回的唯一MD5数字签名
     */
    public static String appOss(File file, String bucketName, String endpoint, String accessKeyId, String accessKeySecret) {
        ossClient = new OSSClient("https://" + endpoint, accessKeyId, accessKeySecret);
        Map resultMap = new HashMap();
        String resultStr = null;
        try {
            String fileName =file.getName();
            String url = "image";
            String catalog="fintecher_file"+"/"+url+"/";
            PutObjectResult putResult = ossClient.putObject(bucketName, catalog+fileName, file);
            //删除本地文件
            resultStr = "https://" + bucketName + "." + endpoint + "/" + catalog+fileName;
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultStr;
    }

    /**
     * 微信上传图片至OSS
     *
     * @param bucketName 存储空间
     * @return String 返回的唯一MD5数字签名
     */
    public static Map wechatToOSS(File file, String bucketName, String endpoint, String accessKeyId, String accessKeySecret) {
        ossClient = new OSSClient("https://" + endpoint, accessKeyId, accessKeySecret);
        Map resultMap = new HashMap();
        try {
            String resultStr = null;
            String fileName = "wechat_file/" + file.getName();
            PutObjectResult putResult = ossClient.putObject(bucketName, fileName, file);
            //删除本地文件
            resultStr = "https://" + bucketName + "." + endpoint + "/" + fileName;
            Map map = new HashMap();
            map.put("Name", resultStr);
            List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();
            fileList.add(map);
            resultMap.put("fileList", fileList);
            file.delete();
            ossClient.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * 微信上传pdf至OSS
     *
     * @param bucketName 存储空间
     * @return String 返回的唯一MD5数字签名
     */
    public static Map wechatPDFToOSS(File file, String bucketName, String endpoint, String accessKeyId, String accessKeySecret) {
        ossClient = new OSSClient("https://" + endpoint, accessKeyId, accessKeySecret);
        Map resultMap = new HashMap();
        try {
            String resultStr = null;
            String fileName = "wechat_file/" + file.getName();
            PutObjectResult putResult = ossClient.putObject(bucketName, fileName, file);
            //删除本地文件
            resultStr = "https://" + bucketName + "." + endpoint + "/" + fileName;
            Map map = new HashMap();
            map.put("Name", resultStr);
            List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();
            fileList.add(map);
            resultMap.put("fileList", fileList);
            ossClient.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }


    /**
     * 通过文件名判断并获取OSS服务文件上传时文件的contentType
     *
     * @param fileName 文件名
     * @return 文件的contentType
     */
    public static String getContentType(String fileName) {
        //文件的后缀名
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        if (".bmp".equalsIgnoreCase(fileExtension)) {
            return "image/bmp";
        }
        if (".gif".equalsIgnoreCase(fileExtension)) {
            return "image/gif";
        }
        if (".jpeg".equalsIgnoreCase(fileExtension) || ".jpg".equalsIgnoreCase(fileExtension) || ".png".equalsIgnoreCase(fileExtension)) {
            return "image/jpeg";
        }
        if (".html".equalsIgnoreCase(fileExtension)) {
            return "text/html";
        }
        if (".txt".equalsIgnoreCase(fileExtension)) {
            return "text/plain";
        }
        if (".vsd".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.visio";
        }
        if (".ppt".equalsIgnoreCase(fileExtension) || "pptx".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.ms-powerpoint";
        }
        if (".doc".equalsIgnoreCase(fileExtension) || "docx".equalsIgnoreCase(fileExtension)) {
            return "application/msword";
        }
        if (".xml".equalsIgnoreCase(fileExtension)) {
            return "text/xml";
        }
        //默认返回类型
        return "image/jpeg";
    }

    /**
     *  根据key删除OSS服务器上的文件
     * @param bucketName
     * @param url
     * @param endpoint
     * @param accessKeyId
     * @param accessKeySecret
     */
    public static void deleteFile( String bucketName, String url, String endpoint, String accessKeyId, String accessKeySecret){
        ossClient = new OSSClient("https://" + endpoint, accessKeyId, accessKeySecret);
        String fileName = url.replace("https://" + bucketName + "." + endpoint + "/","");
        ossClient.deleteObject(bucketName, fileName);
    }

}
