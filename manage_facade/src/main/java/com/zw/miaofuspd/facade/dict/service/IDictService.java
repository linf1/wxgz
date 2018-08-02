package com.zw.miaofuspd.facade.dict.service;


import java.util.List;

/**
 * Created by wangmin on 2017/2/20.
 */
public interface IDictService {

    /**
     * 根据字典大类名称得到字典明细集合
     * @param
     * @return
     * @throws Exception
     */
    List getDetailList(String key) throws Exception;

    /**
     * 根据key和name获取对应的code
     * @param
     * @param
     * @return
     * @throws Exception
     */
     String getDictCode(String key, String name) throws Exception;

    /**
     * 根据key和code获取对应的value
     * @param
     * @param
     * @return
     * @throws Exception
     */
     String getDictInfo(String key, String code) throws  Exception;

    /**
     * 根据key得到value集合
     * @param
     * @return
     * @throws Exception
     */
     List getDictJson(String key) throws  Exception;

    List getDict(String key) throws  Exception;

    /**
     * 根据条件查询结果
     * @param query
     * @return
     * @throws Exception
     */
     List getDictList(String query) throws  Exception;

}
