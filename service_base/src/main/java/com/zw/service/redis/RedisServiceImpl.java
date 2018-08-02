package com.zw.service.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * <p>Title: RedisServiceImpl </p>
 * <p>Description: 类描述:封装redis 缓存服务器服务接口</p>
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
@Service
public class RedisServiceImpl implements RedisService {

	private static String redisCode = "utf-8";
	@Autowired
	public RedisTemplate<String, Object> redisTemplate;

	@Override
	public long delete(final String... keys) {
		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				long result = 0;
				for (int i = 0; i < keys.length; i++) {
					result = connection.del(keys[i].getBytes());
				}
				return result;
			}
		});
	}

	@Override
	public boolean setNX(final byte[] key, final byte[] value, final long liveTime, final TimeUnit unit) {
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				boolean re = connection.setNX(key, value);
				if (liveTime > 0) {
					connection.expire(key, TimeoutUtils.toSeconds(liveTime, unit));
				}
				return re;
			}
		});
	}

	@Override
	public void set(final byte[] key, final byte[] value, final long liveTime, final TimeUnit unit) {
		redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				connection.set(key, value);
				if (liveTime > 0) {
					connection.expire(key, TimeoutUtils.toSeconds(liveTime, unit));
				}
				return 1L;
			}
		});
	}

	@Override
	public void set(String key, final byte[] value, final long liveTime, final TimeUnit unit) {
		try {
			this.set(key.getBytes(redisCode), value, liveTime, unit);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void set(String key, String value, long liveTime, final TimeUnit unit) {
		try {
			this.set(key.getBytes(redisCode), value.getBytes(redisCode), liveTime, unit);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void set(String key, String value) {
		this.set(key, value, 0L, TimeUnit.SECONDS);
	}

	@Override
	public void set(String key, byte[] value) {
		this.set(key, value, 0L, TimeUnit.SECONDS);
	}

	@Override
	public void set(byte[] key, byte[] value) {
		this.set(key, value, 0L, TimeUnit.SECONDS);
	}

	@Override
	public String get(final String key) {
		byte[] by = get(key.getBytes());
		if (by == null) {
			return null;
		}
		try {
			String sb = new String(by, redisCode);
			return sb;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public byte[] getByte(String key) {
		byte[] by = get(key.getBytes());
		if (by == null) {
			return null;
		}
		return by;
	}

	@Override
	public byte[] get(final byte[] key) {
		return redisTemplate.execute(new RedisCallback<byte[]>() {
			@Override
			public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.get(key);
			}
		});
	}

	@Override
	public Set<String> keys(String pattern) {
		return redisTemplate.keys(pattern);
	}

	@Override
	public boolean exists(final String key) {
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.exists(key.getBytes());
			}
		});
	}

	@Override
	public String flushDB() {
		return redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				connection.flushDb();
				return "ok";
			}
		});
	}

	@Override
	public long dbSize() {
		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.dbSize();
			}
		});
	}

	@Override
	public String ping() {
		return redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {

				return connection.ping();
			}
		});
	}

	public RedisTemplate<String, Object> getRedisTemplate() {
		return this.redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void setIncr(String key, String value,long incrementValue, long timeout, TimeUnit unit) {
		this.set(key, value, timeout, unit);
		RedisAtomicLong counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
		counter.set(incrementValue);
		counter.expire(timeout, unit);
	}
	
	@Override
	public Long getIncrValue(String key) {
		RedisAtomicLong counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
		//返回旧值
		return counter.get();
	}

	@Override
	public Long getAndIncrement(String key) {
		RedisAtomicLong counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
		//返回旧值并且加一
		return counter.getAndIncrement();
	}

}