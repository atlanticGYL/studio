package com.my.studio.adoTest.redis;

import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.List;
import java.util.Set;

public class SetTest extends RedisTest {

    public static void main(String args[]) {
        SetTest setTest = new SetTest();
        setTest.doTest();
    }

    private final static String SOURCE_KEY = "sourceSet";
    private final static String DESTINATION_KEY = "destinationSet";
    private final static String OTHER_KEY_1 = "otherSet1";
    private final static String OTHER_KEY_2 = "otherSet2";

    @Override
    void test() {
        test1();
        SetTest.sadd(SOURCE_KEY, new String[]{"sremValue1", "sremValue2", "sremValue3", "sremValue3", "diffValue1", "diffValue2", "diffValue3", "interValue1", "interValue2", "interValue3", "valueIsMem", ""});
        SetTest.smembers(SOURCE_KEY);
        test1();
        SetTest.sadd(SOURCE_KEY, new String[]{"sremValue1", "sremValue2", "sremValue3", "sremValue3", "diffValue1", "diffValue2", "diffValue3", "interValue1", "interValue2", "interValue3", "valueIsMem", ""});
        SetTest.sadd(OTHER_KEY_1, new String[]{"diffValue1", "diffValue2", "interValue1", "interValue2"});
        SetTest.sadd(OTHER_KEY_2, new String[]{"diffValue1", "interValue1", ""});
        SetTest.smembers(SOURCE_KEY);
        SetTest.smembers(OTHER_KEY_1);
        SetTest.smembers(OTHER_KEY_2);
        test1();
    }

    void test1() {
        scard(SOURCE_KEY);
        sismember(SOURCE_KEY, "valueIsMem");
        sismember(SOURCE_KEY, "valueIsMem1");
        sismember(SOURCE_KEY, "");
        srem(SOURCE_KEY, "sremValue1", "sremValue2", "");
        spop(SOURCE_KEY);
        srandommember(SOURCE_KEY);
        srandommember(SOURCE_KEY, 3);
        smove(SOURCE_KEY, DESTINATION_KEY, "smoveValue");
        smembers(SOURCE_KEY);

        sdiff(SOURCE_KEY, OTHER_KEY_1, OTHER_KEY_2);
        sdiffstore(DESTINATION_KEY, SOURCE_KEY, OTHER_KEY_1, OTHER_KEY_2);
        smembers(DESTINATION_KEY);

        sinter(SOURCE_KEY, OTHER_KEY_1, OTHER_KEY_2);
        sinterstore(DESTINATION_KEY, SOURCE_KEY, OTHER_KEY_1, OTHER_KEY_2);
        smembers(DESTINATION_KEY);

        sunion(SOURCE_KEY, OTHER_KEY_1, OTHER_KEY_2);
        sunionstore(DESTINATION_KEY, SOURCE_KEY, OTHER_KEY_1, OTHER_KEY_2);
        smembers(DESTINATION_KEY);

        smembers(SOURCE_KEY);
        sscan(SOURCE_KEY, ScanParams.SCAN_POINTER_START, 3, "*");
    }

    /**
     * 获取集合的成员数
     * 集合 key 不存在时，返回 0
     *
     * @param key 集合 key
     * @return 集合的成员数，类型 Long
     */
    public static Long scard(String key) {
        Long scardRst = jedis.scard(key);
        System.out.println("jedis.scard:" + scardRst);
        return scardRst;
    }

    /**
     * 判断 member 元素是否是集合 key 的成员
     * 集合 key 不存在时返回 false
     * member 为 "" 时可以正常判断，为 null 时 JedisDataException: value sent to redis cannot be null
     *
     * @param key    集合 key
     * @param member 需要判断的元素
     * @return 如果 member 是集合 key 的成员返回 true，否则返回 false，类型 Boolean
     */
    public static boolean sismember(String key, String member) {
        Boolean sismemberRst = jedis.sismember(key, member);
        System.out.println("jedis.sismember:" + sismemberRst);
        return sismemberRst;
    }

    /**
     * 返回集合中的所有成员
     * 集合 key 不存在时，返回空的 Set 而不是 null
     *
     * @param key 集合 key
     * @return 集合中的所有成员，类型 Set<String>
     */
    public static Set<String> smembers(String key) {
        Set<String> smembersRst = jedis.smembers(key);
        System.out.println("jedis.smembers:" + smembersRst);
        return smembersRst;
    }

    /**
     * 移除集合中若干成员
     * 集合 key 不存在时，返回 0
     * member 为 "" 时可以移除，为 null 时 JedisDataException: value sent to redis cannot be null
     *
     * @param key     集合 key
     * @param members 移除的元素值
     * @return 被成功移除的元素的数量，类型 Long
     */
    public static Long srem(String key, String... members) {
        Long sremRst = jedis.srem(key, members);
        System.out.println("jedis.srem:" + sremRst);
        return sremRst;
    }

    /**
     * 移除并返回 key 集合中的一个随机元素
     * 集合 key 不存在时，返回 null
     *
     * @param key 集合 key
     * @return 被移除的元素
     */
    public static String spop(String key) {
        String spopRst = jedis.spop(key);
        System.out.println("jedis.spop:" + spopRst);
        return spopRst;
    }

    /**
     * 返回 key 集合中 abs(count) 个随元素（不会从集合中移除），count 可以传入负数
     * count == 0，返回空的 List
     * 集合 key 不存在时，返回空的 List 而不是 null
     * 方法无 count 签名时，返回一个元素，等同于 count 默认为 1
     *
     * @param key   集合 key
     * @param count 返回元素的数量
     * @return 随机的元素，格式 String[] / String
     */
    public static List<String> srandommember(String key, int count) {
        List<String> srandommemberRst = jedis.srandmember(key, count);
        System.out.println("jedis.srandommember:" + srandommemberRst);
        return srandommemberRst;
    }

    public static String srandommember(String key) {
        String srandommemberRst = jedis.srandmember(key);
        System.out.println("jedis.srandommember:" + srandommemberRst);
        return srandommemberRst;
    }

    /**
     * 将值为 member 的元素从 sourceKey 集合移动到 destinationKey 集合
     * 集合 sourceKey 不存在时，返回 0
     * 集合 sourceKey 存在但 member 不存在时，返回 0
     * 集合 destinationKey 不存在时，返回 0
     * 集合 destinationKey 存在且其中已存在 member 时，返回 1
     *
     * @param sourceKey      移出元素集合的 key
     * @param destinationKey 目标集合的 key
     * @param member         要转移元素的值
     * @return 移动成功返回 1，失败返回 0
     */
    public static Long smove(String sourceKey, String destinationKey, String member) {
        Long smoveRst = jedis.smove(sourceKey, destinationKey, member);
        System.out.println("jedis.smove:" + smoveRst);
        return smoveRst;
    }

    /**
     * 返回给定所有集合的差集
     * 此处差集标准是在全部 diffSetKeys 集合中，只有一个集合独有的元素
     * 无返回结果时，返回空的 Set 而不是 null
     *
     * @param diffSetKeys 需要求差集的集合的 key，格式 String[]
     * @return
     */
    public static Set<String> sdiff(String... diffSetKeys) {
        Set<String> sdiffRst = jedis.sdiff(diffSetKeys);
        System.out.println("jedis.sdiff:" + sdiffRst);
        return sdiffRst;
    }

    /**
     * 返回给定所有集合的差集并存储在 destination 中
     * 此处差集标准是在全部 diffSetKeys 集合中，只有一个集合独有的元素
     * 集合 diffSetKeys 都不存在时，返回 0
     * 只有一个集合存在时，返回该集合所有元素
     *
     * @param destinationKey 存储目标集合的 key
     * @param diffSetKeys    需要求差集的集合的 key，格式 String[]
     * @return 结果集中的元素数量
     */
    public static Long sdiffstore(String destinationKey, String... diffSetKeys) {
        Long sdiffstoreRst = jedis.sdiffstore(destinationKey, diffSetKeys);
        System.out.println("jedis.sdiffstore:" + sdiffstoreRst);
        return sdiffstoreRst;
    }

    /**
     * 返回给定所有集合的交集
     * 此处交集标准是在全部 diffSetKeys 集合中都共有的元素
     * 无交集时，返回空的 Set 而不是 null
     *
     * @param interKeys 需要求交集的集合的 key，格式 String[]
     * @return
     */
    public static Set<String> sinter(String... interKeys) {
        Set<String> sinterRst = jedis.sinter(interKeys);
        System.out.println("jedis.sinter:" + sinterRst);
        return sinterRst;
    }

    /**
     * 返回给定所有集合的交集并存储在 destination 中
     * 此处交集标准是在全部 diffSetKeys 集合中都共有的元素
     * 无交集时，返回 0
     *
     * @param destinationKey 存储目标集合的 key
     * @param interKeys      需要求交集的集合的 key，格式 String[]
     * @return 结果集中的元素数量
     */
    public static Long sinterstore(String destinationKey, String... interKeys) {
        Long sinterstoreRst = jedis.sinterstore(destinationKey, interKeys);
        System.out.println("jedis.sinterstore:" + sinterstoreRst);
        return sinterstoreRst;
    }

    /**
     * 返回给定所有集合的并集
     * 并集为空时，返回空的 Set 而不是 null
     *
     * @param unionKeys 需要求并集的集合的 key，格式 String[]
     * @return
     */
    public static Set<String> sunion(String... unionKeys) {
        Set<String> sunionRst = jedis.sunion(unionKeys);
        System.out.println("jedis.sunion:" + sunionRst);
        return sunionRst;
    }

    /**
     * 返回给定所有集合的并集并存储在 destination 中
     * 并集为空时，返回 0
     *
     * @param destinationKey 存储目标集合的 key
     * @param unionKeys      需要求并集的集合的 key，格式 String[]
     * @return 结果集中的元素数量
     */
    public static Long sunionstore(String destinationKey, String... unionKeys) {
        Long sunionstoreRst = jedis.sunionstore(destinationKey, unionKeys);
        System.out.println("jedis.sunionstore:" + sunionstoreRst);
        return sunionstoreRst;
    }

    /**
     * 向集合添加若干成员
     * member 为 "" 时可以插入，为 null 时 JedisDataException: value sent to redis cannot be null
     *
     * @param key     集合 key
     * @param members 需要添加的元素，格式 String[]
     * @return 成功添加的元素数量（已去重）
     */
    public static Long sadd(String key, String... members) {
        Long saddRst = jedis.sadd(key, members);
        System.out.println("jedis.sadd:" + saddRst);
        return saddRst;
    }

    /**
     * 迭代集合中的元素
     * 只有 scanResult1.getStringCursor() 为 0 时才能表示遍历结束
     * count <= 0 会报错 JedisDataException: ERR syntax error
     * 感觉 cursor 和 count 没什么作用 ？？？
     *
     * @param key 集合 key
     * @param cursor 游标位置
     * @param count 每次查询数量
     * @param parten 过滤表达式，默认 *
     * @return 查询的结果集
     */
    public static List<String> sscan(String key, String cursor, int count, String parten) {
        ScanParams scanParams = new ScanParams();
        scanParams.count(count);
        scanParams.match(parten);
        ScanResult scanResult1;
        List<String> sscanRst;
        while (true) {
            scanResult1 = jedis.sscan(key, cursor, scanParams);
            sscanRst = scanResult1.getResult();
            cursor = scanResult1.getStringCursor();
            System.out.println("jedis.sscan:" + sscanRst + ".cursor:" + cursor);
            if ("0".equals(cursor)) {
                break;
            }
        }
        return sscanRst;
    }
}
