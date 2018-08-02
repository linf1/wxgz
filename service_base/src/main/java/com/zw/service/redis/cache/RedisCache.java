package com.zw.service.redis.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.*;
import java.util.concurrent.Callable;

/**
 * <p>Title: RedisCache </p>
 * <p>Description: redis缓存/p>
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
public class RedisCache  implements Cache
{
  protected static final Log logger = LogFactory.getLog(RedisCache.class);
  private RedisTemplate<String, Object> redisTemplate;
  private String name;

  public void setName(String name)
  {
    this.name = name;
  }

  @Override
  public String getName()
  {
    return this.name;
  }

  @Override
  public Object getNativeCache()
  {
    return this.redisTemplate;
  }

  @Override
  public ValueWrapper get(Object key)
  {
    logger.info("get ---  param : key - " + key);
    final String keyf = (String)key;
    Object object = null;
    object = this.redisTemplate.execute(new RedisCallback()
    {
      @Override
      public Object doInRedis(RedisConnection connection) throws DataAccessException
      {
        byte[] key = keyf.getBytes();
        byte[] value = connection.get(key);
        if (value == null) {
          return null;
        }
        return RedisCache.this.toObject(value);
      }
    });
    return object != null ? new SimpleValueWrapper(object) : null;
  }

  @Override
  public void put(Object key, Object value)
  {
    if (value == null) {
      return;
    }
    logger.info("put ---  param : key - " + key + " value - " + value);
    final String keyf = (String)key;
    final Object valuef = value;
    long liveTime = 864000L;

    this.redisTemplate.execute(new RedisCallback()
    {
      @Override
      public Long doInRedis(RedisConnection connection) throws DataAccessException {
        byte[] keyb = keyf.getBytes();
        byte[] valueb = RedisCache.this.toByteArray(valuef);
        connection.set(keyb, valueb);

        connection.expire(keyb, 864000L);

        return Long.valueOf(1L);
      }
    });
  }

  @Override
  public ValueWrapper putIfAbsent(Object key, Object value) {
    return null;
  }

  @Override
  public <T> T get(Object key, Class<T> type)
  {
    logger.info("get ---  param : key - " + key + " type - " + type.getName());

    return null;
  }

  @Override
  public <T> T get(Object key, Callable<T> valueLoader) {
    return null;
  }

  @Override
  public void evict(Object key)
  {
    logger.info("evict ---  param : key - " + key);
    final String keyf = (String)key;
    this.redisTemplate.execute(new RedisCallback()
    {
      @Override
      public Long doInRedis(RedisConnection connection) throws DataAccessException {
        return connection.del(new byte[][] { keyf.getBytes() });
      }
    });
  }

  @Override
  public void clear()
  {
    logger.info("clear");
    this.redisTemplate.execute(new RedisCallback()
    {
      @Override
      public String doInRedis(RedisConnection connection) throws DataAccessException {
        connection.flushDb();
        return "ok";
      }
    });
  }

  private byte[] toByteArray(Object obj)
  {
    byte[] bytes = null;
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    try {
      ObjectOutputStream oos = new ObjectOutputStream(bos);
      oos.writeObject(obj);
      oos.flush();
      bytes = bos.toByteArray();
      oos.close();
      bos.close();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return bytes;
  }

  private Object toObject(byte[] bytes)
  {
    Object obj = null;
    try {
      ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
      ObjectInputStream ois = new ObjectInputStream(bis);
      obj = ois.readObject();
      ois.close();
      bis.close();
    } catch (IOException ex) {
      ex.printStackTrace();
    } catch (ClassNotFoundException ex) {
      ex.printStackTrace();
    }
    return obj;
  }

  public RedisTemplate<String, Object> getRedisTemplate()
  {
    return this.redisTemplate; } 
  public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) { this.redisTemplate = redisTemplate; }

}