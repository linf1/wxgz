package com.zw.service.redis;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 实体类注解 @RedisKeyPrefix(prefixValue = "xx:xx:{xxx}")
 * @author 陈清玉
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.TYPE})
public @interface RedisKeyPrefix
{
  String prefixValue();
}