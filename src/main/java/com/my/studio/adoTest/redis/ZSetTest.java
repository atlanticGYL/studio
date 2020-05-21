package com.my.studio.adoTest.redis;

import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.params.sortedset.ZAddParams;

import java.util.Map;
import java.util.Set;

public class ZSetTest extends RedisTest {

    public static void main(String args[]) {
        ZSetTest zSetTest = new ZSetTest();
        zSetTest.doTest();
    }

    private final static String SOURCE_KEY = "sourceZSet";
    private final static String DESTINATION_KEY = "destinationZSet";
    private final static String OTHER_KEY_1 = "otherZSet1";
    private final static String OTHER_KEY_2 = "otherZSet2";

    @Override
    void test() {
        test1();
        zaddTest();
        test1();
    }

    void test1() {
        zcard(SOURCE_KEY);
        zcount(SOURCE_KEY, "2", "5");
        zlexcount(SOURCE_KEY, "(1", "[4");

        zrange(SOURCE_KEY, 0, 10);
        zrangebyscore(SOURCE_KEY, "0", "10", 3, 5);
        zrangebyscore(SOURCE_KEY, "0", "10");
        zrangebylex(SOURCE_KEY, "(1", "[4", 3, 5);
        zrangebylex(SOURCE_KEY, "(1", "[4");

        zrevrange(SOURCE_KEY, 4, 6);
        zrevrangeWithScores(SOURCE_KEY, 4, 6);
        zrevrangebyscore(SOURCE_KEY, "0", "10", 3, 5);
        zrevrangebyscore(SOURCE_KEY, "0", "10");
        zrevrangebyscoreWithScores(SOURCE_KEY, "0", "10", 3, 5);
        zrevrangebyscoreWithScores(SOURCE_KEY, "0", "10");

        zrank(SOURCE_KEY, "zrankValue");
        zrevrank(SOURCE_KEY, "zrevrankValue");
        zscore(SOURCE_KEY, "zscoreValue");

        zinterstore(DESTINATION_KEY, SOURCE_KEY, OTHER_KEY_1, OTHER_KEY_2);
        zcard(DESTINATION_KEY);
        zunionstore(DESTINATION_KEY, SOURCE_KEY, OTHER_KEY_1, OTHER_KEY_2);
        zcard(DESTINATION_KEY);

        zremrangebyrank(SOURCE_KEY, 4, 6);
        zremrangebyscore(SOURCE_KEY, "3.2", "4.2");
        zremrangebylex(SOURCE_KEY, "[d", "(f");
        zrem(SOURCE_KEY, "zremValue1", "zremValue2", "zremValue3");

        zscan(SOURCE_KEY, ScanParams.SCAN_POINTER_START, "*", 5);

        zrank(SOURCE_KEY, "zrankValue");
        zincrby(SOURCE_KEY, 3.2, "zincrbyValue+");
        zincrby(SOURCE_KEY, -3.2, "zincrbyValue-");
    }

    void zaddTest() {

    }

    /**
     * 有序集合的成员数量
     * 集合 key 不存在时返回 0
     *
     * @param key 集合 key
     * @return 有序集合的成员数量，类型 Long
     */
    public static Long zcard(String key) {
        Long zcardRst = jedis.zcard(key);
        System.out.println("jedis.zcard:" + zcardRst);
        return zcardRst;
    }

    /**
     * 有序集合中指定分数区间内的成员数量
     * 集合 key 不存在时返回 0
     *
     * @param key 集合 key
     * @param min 区间最小值
     * @param max 区间最大值
     * @return 指定分数区间内的成员数量，类型 Long
     */
    public static Long zcount(String key, String min, String max) {
        Long zcountRst = jedis.zcount(key, min, max);
        System.out.println("jedis.zcount:" + zcountRst);
        return zcountRst;
    }

    /**
     * 有序集合中指定字典区间内的成员数量
     * min 和 max 以 [ 或 ( 开始，标识开闭区间
     * 集合 key 不存在时返回 0
     *
     * @param key 集合 key
     * @param min 字典区间最小值
     * @param max 字典区间最大值
     * @return 指定字典区间内的成员数量，类型 Long
     */
    public static Long zlexcount(String key, String min, String max) {
        Long zlexcountRst = jedis.zlexcount(key, min, max);
        System.out.println("jedis.zlexcount:" + zlexcountRst);
        return zlexcountRst;
    }

    /**
     * 返回有序集合指定排名区间内的成员
     * 集合 key 不存在时，返回空 Set 而不是 null
     * 无结果集时返回空 Set
     *
     * @param key   集合 key
     * @param start 排名区间最小值
     * @param stop  排名区间最大值
     * @return 指定排名区间内的成员，类型 Set<String>
     */
    public static Set<String> zrange(String key, long start, long stop) {
        Set<String> zrangeRst = jedis.zrange(key, start, stop);
        System.out.println("jedis.zrange:" + zrangeRst);
        return zrangeRst;
    }

    /**
     * 返回有序集合指定分数区间内的成员
     * 集合 key 不存在时，返回空 Set 而不是 null
     * 无结果集时返回空 Set
     *
     * @param key    集合 key
     * @param min    分数区间最小值
     * @param max    分数区间最大值
     * @param offset
     * @param count
     * @return 指定分数区间内的成员，类型 Set<String>
     */
    public static Set<String> zrangebyscore(String key, String min, String max, int offset, int count) {
        Set<String> zrangebyscoreRst = jedis.zrangeByScore(key, min, max, offset, count);
        System.out.println("jedis.zrangeByScore:" + zrangebyscoreRst);
        return zrangebyscoreRst;
    }

    public static Set<String> zrangebyscore(String key, String min, String max) {
        Set<String> zrangebyscoreRst = jedis.zrangeByScore(key, min, max);
        System.out.println("jedis.zrangeByScore:" + zrangebyscoreRst);
        return zrangebyscoreRst;
    }

    /**
     * 返回有序集合指定字典区间内的成员
     * min 和 max 以 [ 或 ( 开始，标识开闭区间
     * 集合 key 不存在时，返回空 Set 而不是 null
     * 无结果集时返回空 Set
     *
     * @param key    集合 key
     * @param min    字典区间最小值
     * @param max    字典区间最大值
     * @param offset
     * @param count
     * @return 指定字典区间内的成员，类型 Set<String>
     */
    public static Set<String> zrangebylex(String key, String min, String max, int offset, int count) {
        Set<String> zrangebylexRst = jedis.zrangeByLex(key, min, max, offset, count);
        System.out.println("jedis.zrangeByLex:" + zrangebylexRst);
        return zrangebylexRst;
    }

    public static Set<String> zrangebylex(String key, String min, String max) {
        Set<String> zrangebylexRst = jedis.zrangeByLex(key, min, max);
        System.out.println("jedis.zrangeByLex:" + zrangebylexRst);
        return zrangebylexRst;
    }

    /**
     * 返回有序集中指定排名区间内的成员
     * 集合 key 不存在时，返回空 Set 而不是 null
     * 无结果集时返回空 Set
     *
     * @param key   集合 key
     * @param start 排名区间最小值
     * @param stop  排名区间最大值
     * @return 指定排名区间内的成员，类型 Set<String>
     */
    public static Set<String> zrevrange(String key, long start, long stop) {
        Set<String> zrevrangeRst = jedis.zrevrange(key, start, stop);
        System.out.println("jedis.zrevrange:" + zrevrangeRst);
        return zrevrangeRst;
    }

    /**
     *
     * 集合 key 不存在时，返回空 Set 而不是 null
     * 无结果集时返回空 Set
     *
     * @param key   集合 key
     * @param start 排名区间最小值
     * @param stop  排名区间最大值
     * @return 指定排名区间内的成员，类型 Set<Tuple>
     */
    public static Set<Tuple> zrevrangeWithScores(String key, long start, long stop) {
        Set<Tuple> zrevrangeWithScoresRst = jedis.zrevrangeWithScores(key, start, stop);
        System.out.println("jedis.zrevrangeWithScores:" + zrevrangeWithScoresRst);
        return zrevrangeWithScoresRst;
    }

    /**
     * 返回有序集中指定分数区间内的成员
     * 集合 key 不存在时，返回空 Set 而不是 null
     * 无结果集时返回空 Set
     *
     * @param key    集合 key
     * @param min    分数区间最小值
     * @param max    分数区间最大值
     * @param offset
     * @param count
     * @return 指定分数区间内的成员，类型 Set<String>
     */
    public static Set<String> zrevrangebyscore(String key, String min, String max, int offset, int count) {
        Set<String> zrevrangebyscoreRst = jedis.zrevrangeByScore(key, min, max, offset, count);
        System.out.println("jedis.zrevrangeByScore:" + zrevrangebyscoreRst);
        return zrevrangebyscoreRst;
    }

    public static Set<String> zrevrangebyscore(String key, String min, String max) {
        Set<String> zrevrangebyscoreRst = jedis.zrevrangeByScore(key, min, max);
        System.out.println("jedis.zrevrangeByScore:" + zrevrangebyscoreRst);
        return zrevrangebyscoreRst;
    }

    /**
     *
     * 集合 key 不存在时，返回空 Set 而不是 null
     * 无结果集时返回空 Set
     *
     * @param key 集合 key
     * @param min 分数区间最小值
     * @param max 分数区间最大值
     * @param offset
     * @param count
     * @return 指定分数区间内的成员，类型 Set<Tuple>
     */
    public static Set<Tuple> zrevrangebyscoreWithScores(String key, String min, String max, int offset, int count) {
        Set<Tuple> zrevrangebyscorewithscoresRst = jedis.zrevrangeByScoreWithScores(key, min, max, offset, count);
        System.out.println("jedis.zrevrangebyscoreWithScores:" + zrevrangebyscorewithscoresRst);
        return zrevrangebyscorewithscoresRst;
    }

    public static Set<Tuple> zrevrangebyscoreWithScores(String key, String min, String max) {
        Set<Tuple> zrevrangebyscorewithscoresRst = jedis.zrevrangeByScoreWithScores(key, min, max);
        System.out.println("jedis.zrevrangebyscoreWithScores:" + zrevrangebyscorewithscoresRst);
        return zrevrangebyscorewithscoresRst;
    }

    /**
     * 移除有序集合中给定排名区间的所有成员
     * 集合 key 不存在或无可移除元素时返回 0
     *
     * @param key   集合 key
     * @param start 排名区间最小值
     * @param stop  排名区间最大值
     * @return 移除元素数量，类型 Long
     */
    public static Long zremrangebyrank(String key, long start, long stop) {
        Long zremrangebyrankRst = jedis.zremrangeByRank(key, start, stop);
        System.out.println("jedis.zremrangeByRank:" + zremrangebyrankRst);
        return zremrangebyrankRst;
    }

    /**
     * 移除有序集合中给定分数区间的所有成员
     * 集合 key 不存在或无可移除元素时返回 0
     *
     * @param key 集合 key
     * @param min 分数区间最小值
     * @param max 分数区间最大值
     * @return 移除元素数量，类型 Long
     */
    public static Long zremrangebyscore(String key, String min, String max) {
        Long zremrangebyscoreRst = jedis.zremrangeByScore(key, min, max);
        System.out.println("jedis.zremrangeByScore:" + zremrangebyscoreRst);
        return zremrangebyscoreRst;
    }

    /**
     * 移除有序集合中给定字典区间的所有成员
     * min 和 max 以 [ 或 ( 开始，标识开闭区间
     * 集合 key 不存在或无可移除元素时返回 0
     *
     * @param key 集合 key
     * @param min 字典区间最小值
     * @param max 字典区间最大值
     * @return 移除元素数量，类型 Long
     */
    public static Long zremrangebylex(String key, String min, String max) {
        Long zremrangebyscoreRst = jedis.zremrangeByLex(key, min, max);
        System.out.println("jedis.zremrangeByLex:" + zremrangebyscoreRst);
        return zremrangebyscoreRst;
    }

    /**
     * 移除有序集合中指定的若干成员
     * 集合 key 不存在或无可移除元素时返回 0
     *
     * @param key     集合 key
     * @param members 指定的成员
     * @return 移除元素数量，类型 Long
     */
    public static Long zrem(String key, String... members) {
        Long zremRst = jedis.zrem(key, members);
        System.out.println("jedis.zrem:" + zremRst);
        return zremRst;
    }

    /**
     * 返回有序集合中指定成员的排名
     * 集合 key 不存在时返回 null
     *
     * @param key    集合 key
     * @param member 指定的成员
     * @return member 的排名
     */
    public static Long zrank(String key, String member) {
        Long zrankRst = jedis.zrank(key, member);
        System.out.println("jedis.zrank:" + zrankRst);
        return zrankRst;
    }

    /**
     * 返回有序集合中指定成员的排名
     * 集合 key 不存在时返回 null
     *
     * @param key 集合 key
     * @param member 指定的成员
     * @return 指定成员的排名，类型 Long
     */
    public static Long zrevrank(String key, String member) {
        Long zrevrankRst = jedis.zrevrank(key, member);
        System.out.println("jedis.zrevrank:" + zrevrankRst);
        return zrevrankRst;
    }

    /**
     * 返回有序集中指定成员的分数值
     * 集合 key 不存在时返回 null
     *
     * @param key 集合 key
     * @param member 指定的成员
     * @return 指定成员的分数值，类型 Double
     */
    public static Double zscore(String key, String member) {
        Double zscoreRst = jedis.zscore(key, member);
        System.out.println("jedis.zscore:" + zscoreRst);
        return zscoreRst;
    }

    /**
     * 计算给定的若干个有序集合 interZSetKeys 的交集并将结果集存储在新的有序集合 destinationKey 中
     * 集合 interZSetKeys 不存在时返回 0
     *
     * @param destinationKey 存储交集的集合
     * @param zinterZSetKeys 给定的集合
     * @return 交集元素数量，类型 Long
     */
    public static Long zinterstore(String destinationKey, String ...zinterZSetKeys) {
        Long zinterstoreRst = jedis.zinterstore(destinationKey, zinterZSetKeys);
        System.out.println("jedis.zinterstore:" + zinterstoreRst);
        return zinterstoreRst;
    }

    /**
     * 计算给定的若干个有序集合 zunionZSetKeys 的并集并将结果集存储在新的有序集合 destinationKey 中
     * 集合 interZSetKeys 不存在时返回 0
     *
     * @param destinationKey 存储并集的集合
     * @param zunionZSetKeys 给定的集合
     * @return 并集元素数量，类型 Long
     */
    public static Long zunionstore(String destinationKey, String ...zunionZSetKeys) {
        Long zunionstoreRst = jedis.zunionstore(destinationKey, zunionZSetKeys);
        System.out.println("jedis.zunionstore:" + zunionstoreRst);
        return zunionstoreRst;
    }

    /**
     * 有序集合中对指定成员的分数加上增量 increment
     * increment 可以为负数，表示减分
     * 集合 key 不存在或 member 元素不存在时，添加 key 集合或 member 元素，在默认 0 分的基础上 increment
     *
     * @param key       集合 key
     * @param increment 增加的增量
     * @param member    指定的成员
     * @return member 成员的新分数值，类型 Double
     */
    public static Double zincrby(String key, double increment, String member) {
        Double zincrbyRst = jedis.zincrby(key, increment, member);
        System.out.println("jedis.zincrby:" + zincrbyRst);
        return zincrbyRst;
    }

    /**
     * 迭代有序集合中的元素（包括元素成员和元素分值）
     * @param key
     * @param cursor
     * @param pattern
     * @param count
     */
    public static void zscan(String key, String cursor, String pattern, int count) {
        ScanParams scanParams = new ScanParams();
        scanParams.count(count);
        scanParams.match(pattern);
        while (true) {
            ScanResult<Tuple> scanResult = jedis.zscan(key, cursor, scanParams);
            System.out.println("jedis.zscan:" + scanResult.getResult());
            cursor = scanResult.getStringCursor();
            if ("0".equals(cursor)) {
                break;
            }
        }
    }

    /**
     * 向有序集合添加若干个成员，或者更新已存在成员的分数
     *
     * @param key 集合 key
     * @param member 新增或更新的成员
     * @param score 分数
     * @return 被成功添加的新成员的数量，不包括被更新的、已经存在的成员，类型 Long
     */
    public static Long zadd(String key, String member, double score) {
        Long zaddRst = jedis.zadd(key, score, member);
        System.out.println("jedis.zadd:" + zaddRst);
        return zaddRst;
    }

    /**
     *
     * @param key
     * @param zaddMap
     * @return
     */
    public static Long zadd(String key, Map<String, Double> zaddMap) {
        Long zaddRst = jedis.zadd(key, zaddMap);
        System.out.println("jedis.zadd:" + zaddRst);
        return zaddRst;
    }

    /**
     *
     * @param key
     * @param member
     * @param score
     * @param zAddParams
     * @return
     */
    public static Long zadd(String key, String member, double score, ZAddParams zAddParams) {
        Long zaddRst = jedis.zadd(key, score, member, zAddParams);
        System.out.println("jedis.zadd:" + zaddRst);
        return zaddRst;
    }

    /**
     *
     * @param key
     * @param zaddMap
     * @param zAddParams
     * @return
     */
    public static Long zadd(String key, Map<String, Double> zaddMap, ZAddParams zAddParams) {
        Long zaddRst = jedis.zadd(key, zaddMap, zAddParams);
        System.out.println("jedis.zadd:" + zaddRst);
        return zaddRst;
    }
}
