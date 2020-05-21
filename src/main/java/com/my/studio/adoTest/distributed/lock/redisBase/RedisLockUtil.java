package com.my.studio.adoTest.distributed.lock.redisBase;

import com.my.studio.adoTest.redis.JedisConfig;
import redis.clients.jedis.Jedis;

public class RedisLockUtil {
    // 默认超时时间，单位秒
    private static final int DEFAULT_EXPIRE = 1;

    private void RedisLockUtil() {
    }
    /**
     * @param key    redis key
     * @param expire 过期时间，单位秒
     * @return true:加锁成功，false，加锁失败
     */
    public static boolean lock(String key, int expire) {
        Jedis jedis = JedisConfig.getJedis();
        try {
            long status = jedis.setnx(key, "1");
            if (status == 1) {
                jedis.expire(key, expire);
                return true;
            }
            return false;
        } finally {
            jedis.close();
        }
    }
    /**
     * @param key    redis key
     * @param expire 过期时间，单位秒
     * @return true:加锁成功，false，加锁失败
     */
    public static boolean lock2(String key, int expire) {
        System.out.println(Thread.currentThread().getName());
        boolean isSuccess = false;
        Jedis jedis = JedisConfig.getJedis();
        try {
            long value = System.currentTimeMillis() + expire * 1000;
            long status = jedis.setnx(key, String.valueOf(value));
            if (status == 1) {
                return true;
            } else {
                long oldExpireTime = Long.parseLong(jedis.get(key));
                if (oldExpireTime < System.currentTimeMillis()) {
                    // 超时
                    value = System.currentTimeMillis() + expire * 1000;
                    long currentExpireTime = Long.parseLong(jedis.getSet(key, String.valueOf(value)));
                    if (currentExpireTime == oldExpireTime) {
                        return true;
                    }
                }
            }
        } finally {
            jedis.close();
        }
        return isSuccess;
    }

    /**
     * 释放锁
     * @param key    redis key
     */
    public static void unLock1(String key) {
        Jedis jedis = JedisConfig.getJedis();
        jedis.del(key);
        jedis.close();
    }
    /**
     * 释放锁
     * @param key    redis key
     */
    public static void unLock2(String key) {
        Jedis jedis = JedisConfig.getJedis();
        String expire = jedis.get(key);
        if (null == expire || "".equals(expire.trim())) {
            jedis.del(key);
        } else {
            long oldExpireTime = Long.parseLong(expire);
            if(oldExpireTime > System.currentTimeMillis()) {
                jedis.del(key);
            }
        }
        jedis.close();
    }
}
