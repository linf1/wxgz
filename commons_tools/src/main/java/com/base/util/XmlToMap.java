package com.base.util;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import java.util.*;

/**
 * Created by Administrator on 2017/7/18.
 */
public class XmlToMap {
    private static Map map = new HashMap();
    public static Map<String,Object> xmlStr2Map(String xmlStr) {
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xmlStr); // 将字符串转为XML
            Element rootElt = doc.getRootElement(); // 获取根节点
            if (rootElt.nodeCount()>0) {
                XmlToMap.getNnode(rootElt);
            }
//            for (int i=0;i<rootElt.nodeCount(); i++){
//                Element t = (Element) rootElt.node(i);
//                int ss=t.nodeCount();
//                Iterator iter = t.elementIterator(); // 获取t节点下的子节点
//                String key = t.getName(); // 拿到t节点的key值
//                String value = t.getText(); // 拿到t节点的value值
//                System.out.println("key:" + key + "value:" + value);
//                map.put(key, value);
//                while (iter.hasNext()) {
//                    key="";
//                    value="";
//                    Element recordEle = (Element) iter.next();
//                     ss=recordEle.nodeCount();
//                    key = recordEle.getName(); // 拿到t节点下的子节点entry的key值
//                    value = recordEle.getText(); // 拿到t节点下的子节点entry的value值
//                    map.put(key, value);
//                }
//            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return map;
    }

    static void  getNnode(Element element) {
        for (int i=0;i<element.nodeCount(); i++){
            try {

            Element t = (Element) element.node(i);
            Iterator iter = t.elementIterator(); // 获取t节点下的子节点
            String key = t.getName(); // 拿到t节点的key值
            String value = t.getText(); // 拿到t节点的value值
            System.out.println("key:" + key + "value:" + value);
            map.put(key, value);
            while (iter.hasNext()) {
                key="";
                value="";
                Element recordEle = (Element) iter.next();
                key = recordEle.getName(); // 拿到t节点下的子节点entry的key值
                value = recordEle.getText(); // 拿到t节点下的子节点entry的value值
                map.put(key, value);
                if(recordEle.nodeCount()>0){
                    int ss=recordEle.attributeCount();
                    XmlToMap.getNnode(recordEle);
                }
            }}catch (Exception e)
            {

            }
        }
    }
}
