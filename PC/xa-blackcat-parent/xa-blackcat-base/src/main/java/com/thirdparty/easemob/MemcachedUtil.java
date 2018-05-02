package com.thirdparty.easemob;

import java.io.IOException;
import java.util.Properties;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;
import net.spy.memcached.transcoders.SerializingTranscoder;

public class MemcachedUtil {

	/**
	 * 缓存默认保存7200s
	 */
	private static int expiredTime = 60 * 60 * 2;
	private static MemcachedClient memcachedClient;
	static {
		try {
			ConnectionFactoryBuilder cfb = new ConnectionFactoryBuilder();
			SerializingTranscoder transcoder = new SerializingTranscoder();
			transcoder.setCompressionThreshold(4096);
			cfb.setTranscoder(transcoder);

			Properties config = new Properties();
			config.load(MemcachedUtil.class.getResourceAsStream("/cache.properties"));
			memcachedClient = new MemcachedClient(cfb.build(),
					AddrUtil.getAddresses(config.get("server").toString()));

		} catch (IOException e) {
		}
	}

	public static Object get(String key) {
		Object result = null;
		try {
			result = memcachedClient.get(key);
		} catch (Exception e) {
		}
		return result;
	}

	public static void put(String key, Object value) {
		put(key, value, expiredTime);
	}

	public static void put(String key, Object value, int expiredTime) {
		try {
			memcachedClient.set(key, expiredTime, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void remove(String key) {
		try {
			memcachedClient.delete(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void cleanAll(String... cacheKeys) {
		for (String cacheKey : cacheKeys) {
			memcachedClient.delete(cacheKey);
		}
	}

	public static boolean add(String key, Object value, int expiredTime) {
		boolean rt = false;
		try {
			OperationFuture<Boolean> operationFuture = memcachedClient.add(key,
					expiredTime, value);
			rt = operationFuture.get();
		} catch (Exception e) {
		}
		return rt;
	}

	public static boolean add(String key, Object value) {
		boolean rt = false;
		try {
			OperationFuture<Boolean> operationFuture = memcachedClient.add(key,
					expiredTime, value);
			rt = operationFuture.get();
		} catch (Exception e) {
		}
		return rt;
	}
}
