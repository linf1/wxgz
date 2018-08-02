package com.zw.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

public class PdfToHtml {

    public static void main(String[] args) {
        File file=new File("/Users/van/work/zwkj/byx/files/loan_agreement_pdf/683095e25ec64505b408e7d4393337fc_jiekuanxieyi.pdf");
        PdfToImage(file, "http://localhost:8080/file/get?saveAddress=","/loan_agreement_pdf/683095e25ec64505b408e7d4393337fc_jiekuanxieyi.pdf");
        //传入PDF地址
    }


    public static void PdfToImage(File file, String baseUrl, String saveAddress){
        StringBuffer buffer = new StringBuffer();
        FileOutputStream fos;
        PDDocument document;
        File pdfFile;
        int size;
        BufferedImage image;
        FileOutputStream out;
        //PDF转换成HTML保存的文件夹
        String path = file.getAbsolutePath().replace(".pdf", "");
        File htmlsDir = new File(path);
        if(!htmlsDir.exists()){
            htmlsDir.mkdirs();
        }
        try{
            //遍历处理pdf附件
            buffer.append("<!doctype html>\r\n");
            buffer.append("<head>\r\n");
            buffer.append("<meta charset=\"UTF-8\">\r\n");
            buffer.append("<style>\r\n");
            buffer.append(".navigation-Header {background-color: #fff;width: 100%;height: 64px;position: fixed;top:0;left: 0; }\r\n");
            buffer.append(".left{margin-left: 15px;}\r\n");
            buffer.append(".hr{height:8px;background-color: grey;}\r\n");
            buffer.append(".content{margin-top: 64px;}\r\n");
            buffer.append("img {background-color:#fff; text-align:center; width:100%; max-width:100%;margin-top:6px;}\r\n");
            buffer.append("</style>\r\n");
            buffer.append("</head>\r\n");
            buffer.append("<body style=\"background-color:gray;\">\r\n");
            buffer.append("<div class='navigation-Header'>\r\n");
            buffer.append("<h3 class='left' onclick='history.back();' >返回</h3>\r\n");
            buffer.append("<div class='hr'></div>\r\n");
            buffer.append("</div>\r\n");
            buffer.append("<div class='content'>\r\n");
            document = new PDDocument();
            //pdf附件
            pdfFile = file;
            document = PDDocument.load(pdfFile, (String) null);
            size = document.getNumberOfPages();
            Long start = System.currentTimeMillis(), end = null;
            System.out.println("===>pdf : " + pdfFile.getName() +" , size : " + size);
            PDFRenderer reader = new PDFRenderer(document);
            for(int i=0 ; i < size; i++){
                //image = new PDFRenderer(document).renderImageWithDPI(i,130,ImageType.RGB);
                image = reader.renderImage(i, 1.5f);
                //生成图片,保存位置
                out = new FileOutputStream(path + "/"+ "image" + "_" + i + ".jpg");
                ImageIO.write(image, "png", out); //使用png的清晰度
                //将图片路径追加到网页文件里
                String htmlSaveAddress=saveAddress.replace(".pdf", "");
                buffer.append("<img src='" + baseUrl+ htmlSaveAddress +"/"+ "image" + "_" + i + ".jpg'/>\r\n");
                image = null; out.flush(); out.close();
            }
            reader = null;
            document.close();
            buffer.append("</div>\r\n");
            buffer.append("</body>\r\n");
            buffer.append("</html>");
            end = System.currentTimeMillis() - start;
            System.out.println("===> Reading pdf times: " + (end/1000));
            start = end = null;
            //生成网页文件
            fos = new FileOutputStream(path+".html");
            System.out.println(path+".html");
            fos.write(buffer.toString().getBytes());
            fos.flush(); fos.close();
            buffer.setLength(0);



        }catch(Exception e){
            System.out.println("===>Reader parse pdf to jpg error : " + e.getMessage());
            e.printStackTrace();
        }
    }

}
