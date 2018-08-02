package com.zw.service.redis;


import com.base.util.StringUtils;
import com.base.util.reflect.DynamicMethod;
import com.base.util.reflect.ReflectStringUtil;
import com.exception.SystemException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>Title: RedisKeyResolver </p>
 * <p>Description: 根据@RedisKeyPrefix注解 解析</p>
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
public class RedisKeyResolver<T>
{
  private static final Pattern pattern = Pattern.compile("\\{.*?\\}");

  public RedisKeyPrefix getRedisKeyPrefix(Class<? extends Object> cls)
  {
    RedisKeyPrefix prefix = (RedisKeyPrefix)cls.getAnnotation(RedisKeyPrefix.class);

    return prefix;
  }

  public String getRedisKey(Class<? extends Object> cls, String value)
  {
    RedisKeyPrefix keyPrefix = getRedisKeyPrefix(cls);
    return paseKey(keyPrefix, value);
  }

  public String paseKey(RedisKeyPrefix prefix, String value)
  {
    if (StringUtils.isEmpty(prefix.prefixValue())) {
      throw new SystemException("prefixValue can not be null");
    }
    return populateKey(prefix, value);
  }

  public String paseKey(RedisKeyPrefix prefix, T t) {
    String keyStr = prefix.prefixValue();
    if (StringUtils.isEmpty(keyStr)) {
      throw new SystemException("prefixValue can not be null");
    }
    Matcher m = pattern.matcher(keyStr);
    while (m.find()) {
      String keyFiled = m.group();
      String filedName = keyFiled.replace("{", "");
      filedName = filedName.replace("}", "");
      String keyFiledValue = DynamicMethod.invokeMethod(t, ReflectStringUtil.populateGetMethodName(filedName)).toString();
      keyFiledValue = StringUtils.isEmpty(keyFiledValue) ? "" : keyFiledValue;
      keyStr = keyStr.replace(keyFiled, keyFiledValue);
    }

    return keyStr;
  }

  private String populateKey(RedisKeyPrefix prefix, String value) {
    String keyStr = prefix.prefixValue();
    if (StringUtils.isEmpty(keyStr)) {
      throw new SystemException("prefixValue can not be null");
    }
    Matcher m = pattern.matcher(keyStr);
    while (m.find()) {
      String keyFiled = m.group();
      value = StringUtils.isEmpty(value) ? "" : value;
      keyStr = keyStr.replace(keyFiled, value);
    }
    return keyStr;
  }

  public String paseKeyForList(RedisKeyPrefix prefix, String value)
  {
    if (StringUtils.isEmpty(prefix.prefixValue())) {
      throw new SystemException("prefixValue can not be null");
    }
    return populateKeyForList(prefix, value);
  }

  private String populateKeyForList(RedisKeyPrefix prefix, String value)
  {
    String keyStr = populateKey(prefix, value);
    return keyStr + ":list";
  }

  public String paseKeyForList(RedisKeyPrefix prefix, T t) {
    String keyStr = paseKey(prefix, t);
    return keyStr + ":list";
  }
}