package com.my.studio.adoTest.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisConfig {
    private static final String PASSWORD = "";
    private static JedisPool jedisPool;

    private void JedisConfig() {
    }
    public static Jedis getJedis() {
        return jedisPool.getResource();
    }
    public static void closePool() {
        if (null != jedisPool) jedisPool.close();
    }
    static {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 最大连接数, 默认8个,如果赋值为-1，则表示不限制
        jedisPoolConfig.setMaxTotal(2);
        // 最大空闲连接数, 默认8个
        jedisPoolConfig.setMaxIdle(1);
        // 最小空闲连接数, 默认0
        jedisPoolConfig.setMinIdle(0);
        // 是否启用后进先出, 默认true
        jedisPoolConfig.setLifo(true);
        /**
         * 连接耗尽时是否阻塞, false报异常,true阻塞直到超时, 默认true
         * pool要采取的操作默认有三种：
         * WHEN_EXHAUSTED_FAIL --> 表示无jedis实例时，直接抛出NoSuchElementException
         * WHEN_EXHAUSTED_BLOCK --> 则表示阻塞住，或者达到maxWait时抛出JedisConnectionException
         * WHEN_EXHAUSTED_GROW --> 则表示新建一个jedis实例，也就说设置的maxActive无用
         */
        jedisPoolConfig.setBlockWhenExhausted(true);
        /**
         * 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted，即setBlockWhenExhausted(true))，如果超时就抛异常
         * 小于零:阻塞不确定的时间,  默认-1
         */
        jedisPoolConfig.setMaxWaitMillis(10000);
        /**
         * 空闲连接idleObjects使用的是LinkedBlockingDeque
         * 设置阻塞的公平策略，默认false
         */
        jedisPoolConfig.setFairness(false);

        // jedisPoolConfig.setEvictionPolicy(new DefaultEvictionPolicy());
        // 设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)
        jedisPoolConfig.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");
        // 逐出扫描的时间间隔(毫秒) 如果为负数，则不运行逐出线程,，默认-1
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(100);
        // 逐出连接的最小空闲时间，默认1800000毫秒(30分钟)，默认生效的逐出策略
        jedisPoolConfig.setMinEvictableIdleTimeMillis(30000);
        // 逐出连接的最小空闲时间，当空闲时间>该值且空闲连接>最大空闲数时逐出,不再根据MinEvictableIdleTimeMillis判断。默认-1不生效
        jedisPoolConfig.setSoftMinEvictableIdleTimeMillis(30000);
        // 每次逐出检查时逐出的最大数目，如果为负数就是：1/abs(n), 默认3
        jedisPoolConfig.setNumTestsPerEvictionRun(3);
        // jedisPoolConfig.setEvictorShutdownTimeoutMillis();

        // 从池中获取连接时是否测试连接的有效性，默认false
        jedisPoolConfig.setTestOnBorrow(false);
        // 在连接对象创建时测试连接对象的有效性，默认false
        jedisPoolConfig.setTestOnCreate(false);
        // 在连接对象返回时，是否测试对象的有效性，默认false
        jedisPoolConfig.setTestOnReturn(false);
        // 在连接池空闲时是否测试连接对象的有效性，默认false
        jedisPoolConfig.setTestWhileIdle(false);

        // 是否启用pool的jmx管理功能, 默认true
        jedisPoolConfig.setJmxEnabled(true);
        // 设置JMX基础名
        jedisPoolConfig.setJmxNameBase("jedis");
        // 设置JMX前缀名，默认值pool
        jedisPoolConfig.setJmxNamePrefix("pool");

        if (null == PASSWORD || "".equals(PASSWORD.trim())) {
            jedisPool = new JedisPool(jedisPoolConfig, "127.0.0.1", 6379, 10000);
        } else {
            jedisPool = new JedisPool(jedisPoolConfig, "127.0.0.1", 6379, 10000, PASSWORD);
        }
    }
}
