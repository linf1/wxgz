package com.zw.service.redis;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * <p>Title: RedisService </p>
 * <p>Description: 类描述:redis 的操作开放接口</p>
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
public interface RedisService {

    /**
     * 通过key删除
     *
     * @param keys
     * @return
     * 2015年12月1日下午1:21:47
     */
    long delete(String... keys);

    /**
     * 添加key value 并且设置存活时间(byte)
     *
     * @param key
     * @param value
     * @param liveTime
     * 2015年12月1日下午1:21:58
     */
    boolean setNX(byte[] key, byte[] value, long liveTime, TimeUnit unit);

    /**
     * 添加key value 并且设置存活时间(byte)
     *
     * @param key
     * @param value
     * @param liveTime
     * 2015年12月1日下午1:21:58
     */
    void set(byte[] key, byte[] value, long liveTime, TimeUnit unit);

    /**
     * @param key
     * @param value
     * @param liveTime
     * @param unit
     */
    void set(String key, byte[] value, long liveTime, TimeUnit unit);

    /**
     * 添加key value 并且设置存活时间
     *
     * @param key
     * @param value
     * @param liveTime 单位秒
     * 2015年12月1日下午1:22:11
     */
    void set(String key, String value, long liveTime, TimeUnit unit);

    /**
     * 添加key value
     *
     * @param key
     * @param value
     * 2015年12月1日下午1:22:30
     */
    void set(String key, String value);


    void set(String key, byte[] value);

    /**
     * 添加key value (字节)(序列化)
     *
     * @param key
     * @param value
     * 2015年12月1日下午1:22:40
     */
    void set(byte[] key, byte[] value);

    /**
     * 获取redis value (String)
     *
     * @param key
     * @return
     * 2015年12月1日下午1:22:49
     */
    String get(String key);

    byte[] getByte(String key);

    /**
     * 获取redis value (String)
     *
     * @param key
     * @return
     * 2015年12月1日下午1:22:49
     */
    byte[] get(byte[] key);

    /**
     * 通过正则匹配keys
     *
     * @param pattern
     * @return
     * 2015年12月1日下午1:22:58
     */
    Set<String> keys(String pattern);

    /**
     * 检查key是否已经存在
     *
     * @param key
     * @return
     * 2015年12月1日下午1:23:45
     */
    boolean exists(String key);

    /**
     * 清空redis 所有数据
     *
     * @return
     * 2015年12月1日下午1:23:55
     */
    String flushDB();

    /**
     * 查看redis里有多少数据
     *
     * @return
     * 2015年12月1日下午1:24:05
     */
    long dbSize();

    /**
     * 检查是否连接成功
     *
     * @return
     * 2015年12月1日下午1:24:12
     */
    String ping();

    /**
     * 添加key-value,自增数
     */
    void setIncr(String key, String value, long incrementValue, long timeout, TimeUnit unit);

    /**
     * 原有的值加1
     */
    Long getIncrValue(String key);

    /**
     * 原有递增返回后加一
     *
     * @param key
     * @return
     */
    Long getAndIncrement(String key);

}