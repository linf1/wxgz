package com.zw.service.redis;

import com.base.util.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>Title: WdRedisDao </p>
 * <p>Description: 根据@RedisKeyPrefix注解 操作redis</p>
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
public class WdRedisDao<T>
{
  protected static final Log logger = LogFactory.getLog(WdRedisDao.class);
  public static final long DEFAULT_TIME_OUT = 30L;
  public RedisService redisService;
  private RedisKeyResolver<T> keyResolver = new RedisKeyResolver();

  public void set(T t)
  {
    RedisKeyPrefix prefix = this.keyResolver.getRedisKeyPrefix(t.getClass());
    if (prefix == null) {
      return;
    }
    String key = this.keyResolver.paseKey(prefix, t);
    logger.info("put into redis --- key eq : " + key + " and value eq " + t);
    redisService.set(key.getBytes(), ObjectUtils.ObjectToByte(t));
  }

  public void set(T t, long timeout)
  {
    RedisKeyPrefix prefix = this.keyResolver.getRedisKeyPrefix(t.getClass());
    if (prefix == null) {
      return;
    }
    String key = this.keyResolver.paseKey(prefix, t);
    logger.info("put into redis --- key eq : " + key + " and value eq " + t);
    redisService.set(key.getBytes(), ObjectUtils.ObjectToByte(t), timeout, TimeUnit.MINUTES);
  }

  public void setAsDefaultTimeOut(T t)
  {
    set(t, 30L);
  }

  public T get(String keyValue, Class<?> cls)
  {
    RedisKeyPrefix prefix = this.keyResolver.getRedisKeyPrefix(cls);
    if (prefix == null) {
      return null;
    }
    String key = this.keyResolver.paseKey(prefix, keyValue);
    logger.info("get from redis --- key eq : " + key);
    return (T) ObjectUtils.ByteToObject(redisService.get(key.getBytes()));
  }


  public void setToList(List<T> list) {
    if (CollectionUtils.isEmpty(list)) {
      return;
    }

    for (Iterator i$ = list.iterator(); i$.hasNext(); ) {
    	Object record = i$.next();
    }
     // setToList(record); }
  }


  public T get(T dto, Class<?> cls)
  {
    RedisKeyPrefix prefix = this.keyResolver.getRedisKeyPrefix(cls);
    if (prefix == null) {
      return null;
    }
    String key = this.keyResolver.paseKey(prefix, dto);
    logger.info("get from redis --- key eq : " + key);

    return (T) ObjectUtils.ByteToObject(redisService.get(key.getBytes()));
  }


  public void delete(T dto, Class<?> cls)
  {
    RedisKeyPrefix prefix = this.keyResolver.getRedisKeyPrefix(cls);
    if (prefix == null) {
      return;
    }
    String key = this.keyResolver.paseKey(prefix, dto);
    logger.info("delete redis --- key eq : " + key);
    redisService.delete(key);
  }

  public void delete(String key) {
	  redisService.delete(key);
  }

  public Boolean hasKey(String key) {
	  return redisService.exists(key);
  }

  public RedisService getRedisService()
  {
    return this.redisService; 
  }
  
  public void setRedisService(RedisService redisService) 
  { 
	  this.redisService = redisService; 
  }

}
