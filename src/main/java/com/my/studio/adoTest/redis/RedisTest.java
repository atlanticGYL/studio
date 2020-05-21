package com.my.studio.adoTest.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class RedisTest {
    //连接本地的 Redis 服务
    static Jedis jedis;
    void doTest() {
        jedis = JedisConfig.getJedis();
        jedis.flushAll();
        try {
            test();
        } catch ( Exception e ) {
            System.out.println(e);
        } finally {
            jedis.close();
            JedisConfig.closePool();
        }
    }
    abstract void test();

    private static void testString() throws InterruptedException {
        jedis.set("stringKey1", "testStringValue1");
        jedis.set("stringKey2", "testStringValue2");
        System.out.println("jedis.get:" + jedis.get("stringKey1"));
        System.out.println("jedis.mget:" + jedis.mget("stringKey2", "stringKey", "stringKey1"));
        System.out.println("jedis.getrange:" + jedis.getrange("stringKey1", 2,6));
        System.out.println("jedis.getset:" + jedis.getSet("stringKey1", "newValue"));
        System.out.println("jedis.getbit:" + jedis.getbit("stringKey1", 2));
        jedis.setbit("stringKey1", 1, true);

        jedis.setex("stringKey1ex", 1, "value1Ex");
        Thread.sleep(500);
        System.out.println("jedis.get:" + jedis.get("stringKey1ex"));
        Thread.sleep(500);
        System.out.println("jedis.get:" + jedis.get("stringKey1ex"));

        jedis.setnx("stringKey2", "testStringValue2nx");
        jedis.setnx("stringKey3", "testStringValue3nx");
        System.out.println("jedis.mget:" + jedis.mget("stringKey2", "stringKey3"));

        jedis.setrange("stringKey3", 2, "WTF");
        System.out.println("jedis.get:" + jedis.get("stringKey3"));
        System.out.println("jedis.strlen:" + jedis.strlen("stringKey3"));

        jedis.mset("stringKey2", "testStringValue2","stringKey3", "testStringValue3");
        System.out.println("jedis.mget:" + jedis.mget("stringKey2", "stringKey3"));

        jedis.msetnx("stringKey4", "testStringValue4", "stringKey5", "5");
        System.out.println("jedis.mget:" + jedis.mget("stringKey4", "stringKey5"));

        jedis.psetex("stringKey6", 500, "testStringValue6");
        Thread.sleep(200);
        System.out.println("jedis.get:" + jedis.get("stringKey6"));
        Thread.sleep(500);
        System.out.println("jedis.get:" + jedis.get("stringKey6"));

        jedis.incr("stringKey5");
        System.out.println("jedis.get:" + jedis.get("stringKey5"));
        jedis.incrBy("stringKey5", 2);
        System.out.println("jedis.get:" + jedis.get("stringKey5"));
        jedis.decr("stringKey5");
        System.out.println("jedis.get:" + jedis.get("stringKey5"));
        jedis.decrBy("stringKey5", 5);
        System.out.println("jedis.get:" + jedis.get("stringKey5"));
        // int型value可以incrByFloat，之后转为float，再次做int型操作会报错 ERR value is not an integer or out of range
        jedis.incrByFloat("stringKey5", 2.23);
        System.out.println("jedis.get:" + jedis.get("stringKey5"));

        jedis.append("stringKey5", "apd5");
        jedis.append("stringKey4", "apd4");
        System.out.println("jedis.mget:" + jedis.mget("stringKey4", "stringKey5"));

        // incr和append操作，如果key不存在，会设置一个值
        jedis.incrBy("stringKey7", 2);
        System.out.println("jedis.get:" + jedis.get("stringKey7"));
        jedis.append("stringKey8", "apd8");
        System.out.println("jedis.get:" + jedis.get("stringKey8"));
    }

    private static void hashTest() {
        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");
        System.out.println("jedis.hmset:" + jedis.hmset("testMap", map));

        System.out.println("jedis.hdel:" + jedis.hdel("testMap", "key3"));
        System.out.println("jedis.hset:" + jedis.hset("testMap", "key4", "4"));
        System.out.println("jedis.hget:" + jedis.hget("testMap", "key4"));
        System.out.println("jedis.hgetAll:" + jedis.hgetAll("testMap"));
        System.out.println("jedis.hkeys:" + jedis.hkeys("testMap"));
        System.out.println("jedis.hvals:" + jedis.hvals("testMap"));
        System.out.println("jedis.hexists:" + jedis.hexists("testMap1", "key1"));
        System.out.println("jedis.hlen:" + jedis.hlen("testMap"));
        System.out.println("jedis.hincrBy:" + jedis.hincrBy("testMap", "key4", 3));
        System.out.println("jedis.hincrBy:" + jedis.hincrBy("testMap", "key5", 5));
        System.out.println("jedis.hmget:" + jedis.hmget("testMap", "key5", "key4"));
        System.out.println("jedis.hincrByFloat:" + jedis.hincrByFloat("testMap", "key4", 3.35));
        System.out.println("jedis.hincrByFloat:" + jedis.hincrByFloat("testMap", "key7", 5.333));
        System.out.println("jedis.hmget:" + jedis.hmget("testMap", "key7", "key4"));
        System.out.println("jedis.hsetnx:" + jedis.hsetnx("testMap", "key5", "2"));
        System.out.println("jedis.hsetnx:" + jedis.hsetnx("testMap", "key6", "6"));
        // 数据量小于508时count不生效
        for (int i = 1; i < 508; i++) {
            jedis.hsetnx("testMap", i + "key" + i, String.valueOf(i));
        }

        ScanResult scanResult1 = jedis.hscan("testMap", "0");
        System.out.println("jedis.scanResult1.stringCursor:" + scanResult1.getStringCursor());
        List<Map.Entry<String, String>> result = scanResult1.getResult();
        for (Map.Entry<String, String> entry : result) {
            System.out.println("jedis.scanResult1.entry1, key:" + entry.getKey() + ",value:" + entry.getValue());
        }

        String cursor = ScanParams.SCAN_POINTER_START;
        ScanParams scanParams = new ScanParams();
        scanParams.count(1);
        scanParams.match("*key*");
        scanResult1 = jedis.hscan("testMap", cursor, scanParams);
        result = scanResult1.getResult();
        System.out.println("jedis.scanResult2.stringCursor:" + scanResult1.getStringCursor());

        for (Map.Entry<String, String> entry : result) {
            System.out.println("jedis.scanResult2.entry2, key:" + entry.getKey() + ",value:" + entry.getValue());
        }
    }

}
