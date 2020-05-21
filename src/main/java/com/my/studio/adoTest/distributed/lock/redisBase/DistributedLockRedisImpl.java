/*
package com.my.studio.adoTest.distributed.lock.redisBase;

import com.my.studio.adoTest.distributed.lock.IDistributedLock;
import com.my.studio.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.Charset;

public class DistributedLockRedisImpl implements IDistributedLock {
    */
/**
     * key前缀
     *//*

    public static final String PREFIX = "Lock:";
    */
/**
     * 保存锁的value
     *//*

    private ThreadLocal<String> threadLocal = new ThreadLocal<>();

    private static final Charset UTF8 = Charset.forName("UTF-8");
    */
/**
     * 释放锁脚本
     *//*

    private static final String UNLOCK_LUA;

    */
/*
     * 释放锁脚本，原子操作
     *//*

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("if redis.call(\"get\",KEYS[1]) == ARGV[1] ");
        sb.append("then ");
        sb.append("    return redis.call(\"del\",KEYS[1]) ");
        sb.append("else ");
        sb.append("    return 0 ");
        sb.append("end ");
        UNLOCK_LUA = sb.toString();
    }

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean lock(String key, long requireTimeOut, long lockTimeOut, int retries) {
        //可重入锁判断
        String originValue = threadLocal.get();
        if (!StringUtils.isEmpty(originValue) && isReentrantLock(key, originValue)) {
            return true;
        }
        String value = UUID.randomUUID().toString();
        long end = System.currentTimeMillis() + requireTimeOut;
        int retryTimes = 1;

        try {
            while (System.currentTimeMillis() < end) {
                if (retryTimes > retries) {
                    log.error(" require lock failed,retry times [{}]", retries);
                    return false;
                }
                if (setNX(wrapLockKey(key), value, lockTimeOut)) {
                    threadLocal.set(value);
                    return true;
                }
                // 休眠10ms
                Thread.sleep(10);

                retryTimes++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean setNX(String key, String value, long expire) {
        */
/**
         * List设置lua的keys
         *//*

        List<String> keyList = new ArrayList<>();
        keyList.add(key);
        return (boolean) redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            Boolean result = connection
                    .set(key.getBytes(UTF8),
                            value.getBytes(UTF8),
                            Expiration.milliseconds(expire),
                            SetOption.SET_IF_ABSENT);
            return result;
        });

    }

    */
/**
     * 是否为重入锁
     *//*

    private boolean isReentrantLock(String key, String originValue) {
        String v = (String) redisTemplate.opsForValue().get(key);
        return v != null && originValue.equals(v);
    }

    @Override
    public boolean release(String key) {
        String originValue = threadLocal.get();
        if (StringUtils.isBlank(originValue)) {
            return false;
        }
        return (boolean) redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            return connection
                    .eval(UNLOCK_LUA.getBytes(UTF8), ReturnType.BOOLEAN, 1, wrapLockKey(key).getBytes(UTF8),
                            originValue.getBytes(UTF8));
        });
    }


    private String wrapLockKey(String key) {
        return PREFIX + key;
    }
}
*/
