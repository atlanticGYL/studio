package com.my.studio.adoTest.distributed.lock;

public interface IDistributedLock {
    /**
     *
     * @param key
     * @param requireTimeOut 获取锁超时时间 单位ms
     * @param lockTimeOut 锁过期时间，一定要大于业务执行时间 单位ms
     * @param retries 尝试获取锁的最大次数
     * @return
     */
    boolean lock(String key, long requireTimeOut, long lockTimeOut, int retries);

    /**
     * 释放锁
     * @param key
     * @return
     */
    boolean release(String key);
}
