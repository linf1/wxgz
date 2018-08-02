package com.zw.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

public class UploadFile {


    /**
     * 保存客户端上传文件
     *
     * @param request http request
     * @param saveDir 文件存储的路径
     * @return
     * @throws Exception
     */
    public static Map<String, Object> getFile(HttpServletRequest request,
                                              String saveDir, String fileName) throws Exception {

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
            for (int i = 0; i < formItems.size(); i++) {
                FileItem item = formItems.get(i);
                // 处理不在表单中的字段
                if (!item.isFormField()) {
                    Map<String, Object> fileModel = new HashMap<String, Object>();
                    String path = saveDir + File.separator + fileName + i;
                    File file = new File(path);
                    if (file.exists()) {
                        file.delete();
                    }
                    item.write(file);
                    fileModel.put("originalName", item.getName());
                    fileModel.put("file", file);
                    fileModel.put("Name", fileName + i);
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

    public static Map<String, Object> getFileNew(HttpServletRequest request,
                                                 String saveDir, String fileName) throws Exception {
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


    /**
     * 保存客户端上传文件
     *
     * @param request http request
     * @param saveDir 文件存储的路径
     * @return
     * @throws Exception
     */
    public static Map<String, Object> getFileContract(HttpServletRequest request,
                                                      String saveDir, String fileName) throws Exception {

        // 上传配置
        int MEMORY_THRESHOLD = 1024 * 1024 * 3; // 3MB
        int MAX_FILE_SIZE = 1024 * 1024 * 300; // 40MB
        int MAX_REQUEST_SIZE = 1024 * 1024 * 400; // 50MB

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
                    String suffixName = item.getName();
                    suffixName = suffixName.substring(suffixName.lastIndexOf(".") + 1);
                    fileName = fileName + "." + suffixName;
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

  /*
    private static OSSClient ossClient=null;

    //初始化OSSClient
    private static void initializeOSSClient(){
        //aliyun oss  endpoint
        String endpoint="";
        //aliyun oss  accessKeyId
        String accessKeyId="";
        //aliyun oss  accessKeySecret
        String accessKeySecret="";
        ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }


    *//**
     * 上传文件OSS
     *
     * @param request
     *            http request
     * @param saveDir
     *            文件临时存储的根目录
     * @param saveDirOSS
     *            OSS根目录名
     * @param fileName
     *            文件名称(包括目录/文件名)
     * @param endpoint
     *            oss访问域名和数据中心
     * @param accessKeyId
     *            OSS:accessKeyId
     * @param accessKeySecret
     *            OSS:acessKeySecretc
     * @param catalog
     *            OSS:二级目录
     * @return
     * @throws Exception
     *//*
    public static Map<String, Object> getFileOSS(HttpServletRequest request,String saveDir, String fileName,String catalog,String saveDirOSS,String endpoint,String accessKeyId,String accessKeySecret) throws Exception {

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
		*//*
		 * } else { saveDir = System.getProperty("java.io.tmpdir"); }
		 *//*
        factory.setRepository(new File(saveDir));

        ServletFileUpload upload = new ServletFileUpload(factory);

        // 设置最大文件上传值
        upload.setFileSizeMax(MAX_FILE_SIZE);

        // 设置最大请求值 (包含文件和表单数据)
        upload.setSizeMax(MAX_REQUEST_SIZE);
        Map<String, Object> map = new HashMap();
        List<FileItem> formItems = upload.parseRequest(request);
        List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();
        ossClient = new OSSClient("https://"+endpoint, accessKeyId, accessKeySecret);
        if (formItems != null && formItems.size() > 0) {
            // 迭代表单数据
            for (int i=0;i<formItems.size();i++){
                FileItem item = formItems.get(i);
                // 处理不在表单中的字段
                if (!item.isFormField()) {
                    //获取后缀名
                    String suffixName=item.getName();
                    suffixName = suffixName.substring(suffixName.lastIndexOf(".") + 1);
                    fileName=fileName+i+"."+suffixName;
                    Map<String, Object> fileModel = new HashMap<String, Object>();
                    String path = saveDir + File.separator + fileName;
                    File file = new File(path);
                    if (file.exists()) {
                        file.delete();
                    }
                    //写到本地
                    item.write(file);
                    //上传的OSS
                    ossClient.putObject(saveDirOSS,catalog+fileName,file);
                    fileModel.put("originalName", item.getName());
                    fileModel.put("file", file);
                    fileModel.put("Name", "https://"+saveDirOSS+"."+endpoint+"/"+catalog+fileName);
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

    *//**
     * 微信上传文件OSS
     *
     * @param saveDir
     *            文件临时存储的根目录
     * @param saveDirOSS
     *            OSS根目录名
     * @param fileName
     *            文件名称(包括目录/文件名)
     * @param endpoint
     *            oss访问域名和数据中心
     * @param accessKeyId
     *            OSS:accessKeyId
     * @param accessKeySecret
     *            OSS:acessKeySecretc
     * @param catalog
     *            OSS:二级目录
     * @return
     * @throws Exception
     */
    public static Map<String, Object> getWechatFileOSS(String imgUrl,String saveDir, String fileName,String catalog,String saveDirOSS,String endpoint,String accessKeyId,String accessKeySecret) throws Exception {

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
        List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();
        OSSClient ossClient = new OSSClient("https://"+endpoint, accessKeyId, accessKeySecret);
            // 迭代表单数据
                    File file = new File(imgUrl);
                    //上传的OSS
                    ossClient.putObject(saveDirOSS,catalog+fileName,file);
        Map<String, Object> fileModel = new HashMap<String, Object>();
                    fileModel.put("originalName", imgUrl.lastIndexOf("\\"));
                    fileModel.put("file", file);
                    fileModel.put("Name", "https://"+saveDirOSS+"."+endpoint+"/"+catalog+fileName);
                    fileList.add(fileModel);
                    //删除本地文件
                    file.delete();
        // 关闭client
        ossClient.shutdown();
        map.put("fileList", fileList);
        return map;
    }

    /**
     * 上传网络流
     * @param inputStream 网络流
     * @param catalog
     * @param bucketName
     * @param endpoint
     * @param accessKeyId
     * @param accessKeySecret
     * @return
     * @throws Exception
     *//*
    public static Map<String, Object> getFileOSS(InputStream inputStream, String catalog, String bucketName, String endpoint, String accessKeyId, String accessKeySecret)throws Exception{
        Map<String, Object> result = new HashMap<>();
        //创建ossClient对象
        ossClient = new OSSClient("https://"+endpoint, accessKeyId, accessKeySecret);
        PutObjectResult putObjectResult = ossClient.putObject(bucketName,catalog,inputStream);
        result.put("src", "https://"+bucketName+"."+endpoint+"/"+catalog);
        // 关闭client
        ossClient.shutdown();
        return result;
    }
//    */

    /**
     * //     * 删除OSS文件
     * //     * @param saveDirOSS
     * //     *            OSS根目录名
     * //     * @param fileName
     * //     *            文件名称(包括目录/文件名)
     * //     * @param endpoint
     * //     *            oss访问域名和数据中心
     * //     * @param accessKeyId
     * //     *            OSS:accessKeyId
     * //     * @param accessKeySecret
     * //     *            OSS:acessKeySecretc
     * //     * @return
     * //     * @throws Exception
     * //
     *//*
    public static boolean deleteFilePathOSS(String fileName,String saveDirOSS,String endpoint,String accessKeyId,String accessKeySecret){
        try {
            ossClient = new OSSClient("https://"+endpoint, accessKeyId, accessKeySecret);
            // 删除Object
            ossClient.deleteObject(saveDirOSS, fileName);
            // 关闭client
            ossClient.shutdown();
            return true;
        }catch (Exception e){
            // 关闭client
            ossClient.shutdown();
            return false;
        }
        finally {
            return false;
        }
    }
    */






        /**
         * 获取阿里云OSS客户端对象
         *
         * @return ossClient
         */
        public  OSSClient getOSSClient(String endpoint,String accessKeyId,String accessKeySecret) {
            return new OSSClient(endpoint,accessKeyId,accessKeySecret);
        }

        /**
         * 创建存储空间
         *
         * @param ossClient  OSS连接
         * @param bucketName 存储空间
         * @return
         */
        public String createBucketName(OSSClient ossClient, String bucketName) {
            //存储空间
            final String bucketNames = bucketName;
            if (!ossClient.doesBucketExist(bucketName)) {
                //创建存储空间
                Bucket bucket = ossClient.createBucket(bucketName);
                System.out.println("创建存储空间成功");
                return bucket.getName();
            }
            return bucketNames;
        }

        /**
         * 删除存储空间buckName
         *
         * @param ossClient  oss对象
         * @param bucketName 存储空间
         */
        public void deleteBucket(OSSClient ossClient, String bucketName) {
            ossClient.deleteBucket(bucketName);
            System.out.println("删除" + bucketName + "Bucket成功");
        }

        /**
         * 创建模拟文件夹
         * @param ossClient  oss连接
         * @param bucketName 存储空间
         * @param folder     模拟文件夹名如"qj_nanjing/"
         * @return 文件夹名
         */
        public String createFolder(OSSClient ossClient, String bucketName, String folder) {
            //文件夹名
            final String keySuffixWithSlash = folder;
            //判断文件夹是否存在，不存在则创建
            if (!ossClient.doesObjectExist(bucketName, keySuffixWithSlash)) {
                //创建文件夹
                ossClient.putObject(bucketName, keySuffixWithSlash, new ByteArrayInputStream(new byte[0]));
                System.out.println("创建文件夹成功");
                //得到文件夹名
                OSSObject object = ossClient.getObject(bucketName, keySuffixWithSlash);
                String fileDir = object.getKey();
                return fileDir;
            }
            return keySuffixWithSlash;
        }

        /**
         * 根据key删除OSS服务器上的文件
         *
         * @param ossClient  oss连接
         * @param bucketName 存储空间
         * @param folder     模拟文件夹名 如"qj_nanjing/"
         * @param key        Bucket下的文件的路径名+文件名 如："upload/cake.jpg"
         */
        public void deleteFile(OSSClient ossClient, String bucketName, String folder, String key) {
            ossClient.deleteObject(bucketName, folder + key);
            System.out.println("删除" + bucketName + "下的文件" + folder + key + "成功");
        }

        /**
         * 上传图片至OSS
         *
         * @param ossClient  oss连接
         * @param file       上传文件（文件全路径如：D:\\image\\cake.jpg）
         * @param bucketName 存储空间
         * @param folder     模拟文件夹名 如"qj_nanjing/"
         * @return String 返回的唯一MD5数字签名
         */
        public String uploadObject2OSS(OSSClient ossClient, File file, String bucketName, String folder) {
            String resultStr = null;
            try {
                //以输入流的形式上传文件
                InputStream is = new FileInputStream(file);
                //文件名
                String fileName = file.getName();
                //文件大小
                Long fileSize = file.length();
                //创建上传Object的Metadata
                ObjectMetadata metadata = new ObjectMetadata();
                //上传的文件的长度
                metadata.setContentLength(is.available());
                //指定该Object被下载时的网页的缓存行为
                metadata.setCacheControl("no-cache");
                //指定该Object下设置Header
                metadata.setHeader("Pragma", "no-cache");
                //指定该Object被下载时的内容编码格式
                metadata.setContentEncoding("utf-8");
                //文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，
                //如果没有扩展名则填默认值application/octet-stream
                metadata.setContentType(getContentType(fileName));
                //指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）
                metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");
                //上传文件   (上传文件流的形式)
                PutObjectResult putResult = ossClient.putObject(bucketName, folder + fileName, is, metadata);
                //解析结果
                resultStr = putResult.getETag();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("上传阿里云OSS服务器异常." + e.getMessage());
            }
            return resultStr;
        }

        /**
         * 通过文件名判断并获取OSS服务文件上传时文件的contentType
         *
         * @param fileName 文件名
         * @return 文件的contentType
         */
        public String getContentType(String fileName) {
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
    }
