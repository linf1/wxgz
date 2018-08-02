package com.zw.service.redis.lock;

import com.google.common.collect.Maps;
import com.zw.service.redis.RedisService;
import org.apache.log4j.Logger;

import java.util.Random;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * <p>Title: RedisLock </p>
 * <p>Description: redis锁</p>
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
public class RedisLock implements Lock {

	protected RedisService redisService;

	private static final Logger logger = Logger.getLogger(RedisLock.class);

    /**
     *lock flag stored in redis
     */
	private static final String LOCKED = "TRUE";

    /**
     * timeout(ms)
     */
	private static final long TIME_OUT = 30000;

    /**
     * lock expire time(s)
     */
	public static final int EXPIRE = 60;

	private String key;

    /**
     * state flag
     */
	private volatile boolean locked = false;

	private static ConcurrentMap<String, RedisLock> map = Maps.newConcurrentMap();

	public RedisLock(String key, RedisService redisService) {
		this.key = "_LOCK_" + key;
		this.redisService = redisService;
	}

	public static RedisLock getInstance(String key) {
		return map.get(key);
	}

	public void lock(long timeout) {
		long nano = System.nanoTime();
		timeout *= 1000000;
		final Random r = new Random();
		try {
			while ((System.nanoTime() - nano) < timeout) {
				if (redisService.setNX(key.getBytes(), LOCKED.getBytes(), EXPIRE, TimeUnit.SECONDS)) {
					locked = true;
					logger.debug("add RedisLock[" + key + "].");
					break;
				}
				Thread.sleep(3, r.nextInt(500));
			}
		} catch (Exception e) {
			unlock();
			e.printStackTrace();
		}
	}

	@Override
	public void unlock() {
		if (locked) {
			logger.debug("release RedisLock[" + key + "].");
			redisService.delete(key);
		}
	}

	@Override
	public void lock() {
		lock(TIME_OUT);
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {

	}

	@Override
	public Condition newCondition() {
		return null;
	}

	@Override
	public boolean tryLock() {
		return false;
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return false;
	}
}