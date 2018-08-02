package com.zw.miaofuspd.facade.dict.service;

import java.util.List;

/**
 * Created by wangmin on 2017/2/20.
 */
public interface ISystemDictService {

    /**
     *根据dict_kind 和 dict_key 得到dict_info
     * @param
     * @return
     * @throws Exception
     */
    String getInfo(String dict_kind, String dict_key) throws Exception;

    /**
     * 根据dict_kind 获取其对应的所有dict_info 和 key
     * @param
     * @return
     * @throws Exception
     */
    List getJsonDict(String dict_kind) throws  Exception;


    String getInfo(String key) throws Exception;

}
