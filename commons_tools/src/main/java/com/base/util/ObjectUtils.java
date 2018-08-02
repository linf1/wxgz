package com.base.util;

import com.google.common.base.Objects;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.util.*;
/**
 * <p>Title: ObjectUtils </p>
 * <p>Description: 对象转换帮助类</p>
 * <p>Copyright (c) </p>
 * <p>Company: </p>
 *
 * @author 陈清玉
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 * @date 2018年6月6日下午10:20:08
 */
public class ObjectUtils {

    protected static final Log logger = LogFactory.getLog(ObjectUtils.class);

    /**
     * 检查数组中是否包含特定值
     *
     * @param arr
     * @param targetValue
     * @return
     */
    public static boolean useLoop(Object[] arr, Object targetValue) {
        for (Object s : arr) {
            System.out.println(s + "====" + targetValue);
            if (Objects.equal(s, targetValue) && targetValue != null) {
                return true;
            }
        }
        return false;
    }

    public static String[] array_unique(String[] a) {
        // array_unique
        List<String> list = new LinkedList<String>();
        for (int i = 0; i < a.length; i++) {
            if (!list.contains(a[i])) {
                list.add(a[i]);
            }
        }
        return (String[]) list.toArray(new String[list.size()]);
    }

    /**
     * 此方法描述的是：List to Map
     *
     * @author: cwftalus@163.com
     * @version: 2015年4月7日 下午6:11:56
     */
    // public static <T> HashMap<T,T> ListToMap(List<T> result,T key){
    // HashMap<T,T> hashMap = new HashMap<T, T>();
    // Iterator<T> its = (Iterator<T>) result.iterator();
    // while(its.hasNext()){
    // T t = its.next();
    // hashMap.put(key, t);
    // }
    // return hashMap;
    // }
    public static <T> Map<Integer, T> ListToMapI(String keyName, List<T> list) {
        Map<Integer, T> m = new HashMap<Integer, T>();
        try {
            for (T t : list) {
                PropertyDescriptor pd = new PropertyDescriptor(keyName, t.getClass());
                Method getMethod = pd.getReadMethod();// 获得get方法
                Object o = getMethod.invoke(t);// 执行get方法返回一个Object
                if (!Objects.equal(o, null)) {
                    m.put(Integer.valueOf(o.toString()), t);
                }
            }
            return m;
        } catch (Exception e) {
            logger.error("Convert List to Map failed");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 用于把List&lt;Object>转换成Map&lt;String,Object>形式，便于存入缓存
     *
     * @param keyName 主键属性
     * @param list    集合
     * @return 返回对象
     * @author cwftalus@163.com
     */
    public static <T> Map<String, T> ListToMap(String keyName, List<T> list) {
        Map<String, T> m = new HashMap<String, T>();
        try {
            for (T t : list) {
                PropertyDescriptor pd = new PropertyDescriptor(keyName, t.getClass());
                Method getMethod = pd.getReadMethod();// 获得get方法
                Object o = getMethod.invoke(t);// 执行get方法返回一个Object
                if (!Objects.equal(o, null)) {
                    m.put(o.toString(), t);
                }
            }
        } catch (Exception e) {
            logger.error("Convert List to Map failed");
            e.printStackTrace();
        }
        return m;
    }

    public static <T> Map<String, T> ListToMap(String keyName, String keyName2, List<T> list) {
        Map<String, T> m = new HashMap<String, T>();
        try {
            for (T t : list) {
                PropertyDescriptor pd = new PropertyDescriptor(keyName, t.getClass());
                Method getMethod = pd.getReadMethod();// 获得get方法
                Object o = getMethod.invoke(t);// 执行get方法返回一个Object

                PropertyDescriptor pd2 = new PropertyDescriptor(keyName2, t.getClass());
                Method getMethod2 = pd2.getReadMethod();// 获得get方法
                Object o2 = getMethod2.invoke(t);// 执行get方法返回一个Object

                if (!Objects.equal(o, null) && !Objects.equal(o2, null)) {
                    String keyCode = o.toString() + "-" + o2.toString();
                    m.put(keyCode, t);
                }
            }
        } catch (Exception e) {
            logger.error("Convert List to Map failed");
            e.printStackTrace();
        }
        return m;
    }

    /**
     * list转化map,参数个数为三
     * @param firstKeyName 第一个T中的属性名
     * @param secondKeyName 第二个T中的属性名
     * @param threeKeyName 第三个T中的属性名
     * @param list T的列表
     * @param <T> 类
     * @return 转化后的map
     * @author barnet_shen@ssic.cn
     * @date 2017/12/27
     */
    public static <T> Map<String, T> ListToMap(String firstKeyName, String secondKeyName, String threeKeyName, List<T> list) {
        Map<String, T> m = new HashMap<String, T>();
        try {
            for (T t : list) {
                PropertyDescriptor firstPropertyDescriptor = new PropertyDescriptor(firstKeyName, t.getClass());
                Method getMethodFirst = firstPropertyDescriptor.getReadMethod();// 获得get方法
                Object firstObject = getMethodFirst.invoke(t);// 执行get方法返回一个Object

                PropertyDescriptor secondPropertyDescriptor = new PropertyDescriptor(secondKeyName, t.getClass());
                Method getMethodSecond = secondPropertyDescriptor.getReadMethod();// 获得get方法
                Object secondObject = getMethodSecond.invoke(t);// 执行get方法返回一个Object

                PropertyDescriptor threePropertyDescriptor = new PropertyDescriptor(threeKeyName, t.getClass());
                Method getMethodThree = threePropertyDescriptor.getReadMethod();// 获得get方法
                Object threeObject = getMethodThree.invoke(t);// 执行get方法返回一个Object

                if (!Objects.equal(firstObject, null) && !Objects.equal(secondObject, null) && !Objects.equal(threeObject, null)) {
                    String keyCode = firstObject.toString() + "-" + secondObject.toString() + "-" + threeObject.toString();
                    m.put(keyCode, t);
                }
            }
        } catch (Exception e) {
            logger.error("Convert List to Map failed (There are three parameters)");
            e.printStackTrace();
        }
        return m;
    }

    public static <T> Map<String, String> ListToMap(List<String> list) {
        Map<String, String> m = new HashMap<String, String>();
        try {
            for (String t : list) {
                m.put(t, t);
            }
            return m;
        } catch (Exception e) {
            logger.error("Convert List to Map failed");
            e.printStackTrace();
        }
        return null;
    }

    public static <T> Map<String, List<T>> listToMapList(String keyName, List<T> list) {
        Map<String, List<T>> m = new HashMap<String, List<T>>();
        try {
            List<T> mapList = null;
            for (T t : list) {
                PropertyDescriptor pd = new PropertyDescriptor(keyName, t.getClass());
                Method getMethod = pd.getReadMethod();// 获得get方法
                Object o = getMethod.invoke(t);// 执行get方法返回一个Object
                if (!Objects.equal(o, null)) {
                    String keyCode = o.toString();
                    if (m.containsKey(keyCode)) {
                        mapList = m.get(keyCode);
                    } else {
                        mapList = new ArrayList<>();
                    }
                    mapList.add(t);
                    m.put(keyCode, mapList);
                }
            }
            return m;
        } catch (Exception e) {
            logger.error("Convert List to Map failed");
            e.printStackTrace();
        }
        return null;
    }

    public static <T> Map<String, List<T>> listToLinkedHashMapList(String keyName, List<T> list) {
        Map<String, List<T>> m = new LinkedHashMap<String, List<T>>();
        try {
            List<T> mapList = null;
            for (T t : list) {
                PropertyDescriptor pd = new PropertyDescriptor(keyName, t.getClass());
                Method getMethod = pd.getReadMethod();// 获得get方法
                Object o = getMethod.invoke(t);// 执行get方法返回一个Object
                if (!Objects.equal(o, null)) {
                    String keyCode = o.toString();
                    if (m.containsKey(keyCode)) {
                        mapList = m.get(keyCode);
                    } else {
                        mapList = new ArrayList<>();
                    }
                    mapList.add(t);
                    m.put(keyCode, mapList);
                }
            }
            return m;
        } catch (Exception e) {
            logger.error("Convert List to Map failed");
            e.printStackTrace();
        }
        return null;
    }

    public static <T> Map<Integer, List<T>> listIntegerToMapList(String keyName, List<T> list) {
        Map<Integer, List<T>> m = new HashMap<Integer, List<T>>();
        try {
            List<T> mapList = null;
            for (T t : list) {
                PropertyDescriptor pd = new PropertyDescriptor(keyName, t.getClass());
                Method getMethod = pd.getReadMethod();// 获得get方法
                Object o = getMethod.invoke(t);// 执行get方法返回一个Object
                if (!Objects.equal(o, null)) {
                    Integer keyCode = Integer.valueOf(o.toString());
                    if (m.containsKey(keyCode)) {
                        mapList = m.get(keyCode);
                    } else {
                        mapList = new ArrayList<>();
                    }
                    mapList.add(t);
                    m.put(keyCode, mapList);
                }
            }
            return m;
        } catch (Exception e) {
            logger.error("Convert List to Map failed");
            e.printStackTrace();
        }
        return null;
    }


    public static <T> Map<String, List<T>> ListToTMapList(String keyName1, String keyName2, List<T> list) {
        Map<String, List<T>> m = new HashMap<String, List<T>>();
        try {
            List<T> mapList = null;
            for (T t : list) {
                PropertyDescriptor pd = new PropertyDescriptor(keyName1, t.getClass());
                Method getMethod = pd.getReadMethod();// 获得get方法
                Object o = getMethod.invoke(t);// 执行get方法返回一个Object

                PropertyDescriptor pd2 = new PropertyDescriptor(keyName2, t.getClass());
                Method getMethod2 = pd2.getReadMethod();// 获得get方法
                Object o2 = getMethod2.invoke(t);// 执行get方法返回一个Object

                if (!Objects.equal(o, null) && !Objects.equal(o2, null)) {
                    String keyCode = o.toString() + "#" + o2.toString();
                    if (m.containsKey(keyCode)) {
                        mapList = m.get(keyCode);
                    } else {
                        mapList = new ArrayList<>();
                    }
                    mapList.add(t);
                    m.put(keyCode, mapList);
                }
            }
            return m;
        } catch (Exception e) {
            logger.error("Convert List to Map failed");
            e.printStackTrace();
        }
        return null;
    }

    public static <T> Map<String, List<T>> ListToTMapListV2(String keyName1, String keyName2, List<T> list) {
        Map<String, List<T>> m = new HashMap<String, List<T>>();
        try {
            List<T> mapList = null;
            for (T t : list) {
                PropertyDescriptor pd = new PropertyDescriptor(keyName1, t.getClass());
                // 获得get方法
                Method getMethod = pd.getReadMethod();
                // 执行get方法返回一个Object
                Object o = getMethod.invoke(t);

                PropertyDescriptor pd2 = new PropertyDescriptor(keyName2, t.getClass());
                // 获得get方法
                Method getMethod2 = pd2.getReadMethod();
                // 执行get方法返回一个Object
                Object o2 = getMethod2.invoke(t);

                if (!Objects.equal(o, null) && !Objects.equal(o2, null)) {
                    String keyCode = o.toString() + "-" + o2.toString();
                    if (m.containsKey(keyCode)) {
                        mapList = m.get(keyCode);
                    } else {
                        mapList = new ArrayList<>();
                    }
                    mapList.add(t);
                    m.put(keyCode, mapList);
                }
            }
            return m;
        } catch (Exception e) {
            logger.error("Convert List to Map failed");
            e.printStackTrace();
        }
        return null;
    }

    public static <T> Map<String, List<T>> ListToTLinkedHashMapList(String keyName1, String keyName2, List<T> list) {
        Map<String, List<T>> m = new LinkedHashMap<String, List<T>>();
        try {
            List<T> mapList = null;
            for (T t : list) {
                PropertyDescriptor pd = new PropertyDescriptor(keyName1, t.getClass());
                // 获得get方法
                Method getMethod = pd.getReadMethod();
                // 执行get方法返回一个Object
                Object o = getMethod.invoke(t);

                PropertyDescriptor pd2 = new PropertyDescriptor(keyName2, t.getClass());
                // 获得get方法
                Method getMethod2 = pd2.getReadMethod();
                // 执行get方法返回一个Object
                Object o2 = getMethod2.invoke(t);

                if (!Objects.equal(o, null) && !Objects.equal(o2, null)) {
                    String keyCode = o.toString() + "#" + o2.toString();
                    if (m.containsKey(keyCode)) {
                        mapList = m.get(keyCode);
                    } else {
                        mapList = new ArrayList<>();
                    }
                    mapList.add(t);
                    m.put(keyCode, mapList);
                }
            }
            return m;
        } catch (Exception e) {
            logger.error("Convert List to Map failed");
            e.printStackTrace();
        }
        return null;
    }

    public static <T> Map<String, T> ListToTKeyMap(String keyName1, String keyName2, List<T> list) {
        Map<String, T> m = new HashMap<String, T>();
        try {
            for (T t : list) {
                PropertyDescriptor pd = new PropertyDescriptor(keyName1, t.getClass());
                Method getMethod = pd.getReadMethod();// 获得get方法
                Object o = getMethod.invoke(t);// 执行get方法返回一个Object

                PropertyDescriptor pd2 = new PropertyDescriptor(keyName2, t.getClass());
                Method getMethod2 = pd2.getReadMethod();// 获得get方法
                Object o2 = getMethod2.invoke(t);// 执行get方法返回一个Object

                if (!Objects.equal(o, null) && !Objects.equal(o2, null)) {
                    String keyCode = o.toString() + "-" + o2.toString();
                    m.put(keyCode, t);
                }
            }
            return m;
        } catch (Exception e) {
            logger.error("Convert List to Map failed");
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> copyIterator(Iterator<T> iter) {
        List<T> copy = new ArrayList<T>();
        while (iter.hasNext()) {
            System.out.println("----");
            copy.add(iter.next());
        }

        return copy;
    }

    //将字节转换为对象
    public static Object ByteToObject(byte[] bytes) {
        /*   Object obj = null;
		   try {  
		       // bytearray to object  
		       ByteArrayInputStream bi = new ByteArrayInputStream(bytes);  
		       ObjectInputStream oi = new ObjectInputStream(bi);  
		     
		       obj = oi.readObject();  
		       bi.close();  
		       oi.close();  
		   } catch (Exception e) {  
		       System.out.println("translation" + e.getMessage());  
		       e.printStackTrace();  
		   }  
		   return obj;  */
        Object obj = null;
        ByteArrayInputStream byteArrayInputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            byteArrayInputStream = new ByteArrayInputStream(bytes);
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            obj = objectInputStream.readObject();
        } catch (Exception e) {
            System.out.println("translation" + e.getMessage());
            e.printStackTrace();
        }
        return obj;
    }

    //将对像转换为字节
    public static byte[] ObjectToByte(Object obj) {
        byte[] bytes = null;
        try {
            // object to bytearray
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);

            bytes = bo.toByteArray();

            bo.close();
            oo.close();
        } catch (Exception e) {
            System.out.println("translation" + e.getMessage());
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * @param @param  key T 的成员变量名称
     * @param @param  value  T的成员变量名称
     * @param @param  list
     * @param @return
     * @return List<Map<String,Object>>    返回类型
     * @throws
     * @Title: ListToSelectList
     * @Description: TODO(去除list 中对象多余属性)   主要用于解决原前端select下拉数据中多余的字段属性
     */
    public static <T> List<Map<String, Object>> ListToSelectList(String key, String value, List<T> list) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
            return null;
        }
        try {
            List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
            for (T t : list) {
                PropertyDescriptor pd = new PropertyDescriptor(key, t.getClass());
                Method getMethod = pd.getReadMethod();// 获得get方法
                Object o = getMethod.invoke(t);// 执行get方法返回一个Object

                PropertyDescriptor pd2 = new PropertyDescriptor(value, t.getClass());
                Method getMethod2 = pd2.getReadMethod();// 获得get方法
                Object o2 = getMethod2.invoke(t);// 执行get方法返回一个Object
                Map<String, Object> temp = new HashMap<String, Object>();//不好 循环内
                temp.put(key, o);
                temp.put(value, o2);
                resultList.add(temp);
            }
            return resultList;
        } catch (Exception e) {
            logger.error("Convert List to SelectList failed");
            e.printStackTrace();
        }
        return null;
    }

}
