package com.my.studio.adoTest.redis;

import redis.clients.jedis.Client;
import redis.clients.jedis.Transaction;

import java.util.List;

public class ListTest extends RedisTest {

    public static void main(String args[]) {
        ListTest listTest = new ListTest();
        listTest.doTest();
    }

    private final static String SOURCE_KEY = "sourceList";
    private final static String DESTINATION_KEY = "destinationList";

    @Override
    void test() {
        lpop(SOURCE_KEY);
        rpop(SOURCE_KEY);
        blpop(SOURCE_KEY, 3);
        brpop(SOURCE_KEY, 3);

        lrem(SOURCE_KEY, 2, "valueML-2");
        ltrim(SOURCE_KEY, 1, 10);

        lpushx(SOURCE_KEY, "valueH");
        rpushx(SOURCE_KEY, "valueT");

        rpoplpush(SOURCE_KEY, DESTINATION_KEY);
        brpoplpush(SOURCE_KEY, DESTINATION_KEY, 3);
        lrange(SOURCE_KEY, 0, ListTest.llen(SOURCE_KEY));
        lrange(DESTINATION_KEY, 0, ListTest.llen(DESTINATION_KEY));

        lpush(SOURCE_KEY, new String[]{"valueML+3", "valueML+2", "valueML+1", "valueML", "valueML-3", "valueML-2", "valueML-1"});
        rpush(SOURCE_KEY, new String[]{"valueMR-1", "valueMR-2", "valueMR-3", "valueMR", "valueMR+1", "valueMR+2", "valueMR+3"});

        lpushx(SOURCE_KEY, "valueH");
        rpushx(SOURCE_KEY, "valueT");
        lrange(SOURCE_KEY, 0, ListTest.llen(SOURCE_KEY));

        lset(SOURCE_KEY, 5, "valueMR-new");
        lrange(SOURCE_KEY, 0, ListTest.llen(SOURCE_KEY));

        linsert(SOURCE_KEY, Client.LIST_POSITION.BEFORE, "valueMR-new", "valueMR-before");
        linsert(SOURCE_KEY, Client.LIST_POSITION.AFTER, "valueMR-new", "valueMR-after");
        linsert(SOURCE_KEY, Client.LIST_POSITION.AFTER, "valueMR-non", "valueMR-after");
        lrange(SOURCE_KEY, 0, ListTest.llen(SOURCE_KEY));

        lpop(SOURCE_KEY);
        rpop(SOURCE_KEY);
        blpop(SOURCE_KEY, 3);
        brpop(SOURCE_KEY, 3);
        lrange(SOURCE_KEY, 0, ListTest.llen(SOURCE_KEY));

        rpoplpush(SOURCE_KEY, DESTINATION_KEY);
        lrange(SOURCE_KEY, 0, ListTest.llen(SOURCE_KEY));
        lrange(DESTINATION_KEY, 0, ListTest.llen(DESTINATION_KEY));
        brpoplpush(SOURCE_KEY, DESTINATION_KEY, 3);
        lrange(SOURCE_KEY, 0, ListTest.llen(SOURCE_KEY));
        lrange(DESTINATION_KEY, 0, ListTest.llen(DESTINATION_KEY));

        lpush(DESTINATION_KEY, new String[]{"valueDST+1", "valueDST", "valueDST-1"});
        rpoplpush(SOURCE_KEY, DESTINATION_KEY);
        lrange(SOURCE_KEY, 0, ListTest.llen(SOURCE_KEY));
        lrange(DESTINATION_KEY, 0, ListTest.llen(DESTINATION_KEY));
        brpoplpush(SOURCE_KEY, DESTINATION_KEY, 3);
        lrange(SOURCE_KEY, 0, ListTest.llen(SOURCE_KEY));
        lrange(DESTINATION_KEY, 0, ListTest.llen(DESTINATION_KEY));

        rpush(SOURCE_KEY, "valueML-2", "valueML-3", "valueML", "valueMR-2", "valueML-2");
        lrange(SOURCE_KEY, 0, ListTest.llen(SOURCE_KEY));
        lrem(SOURCE_KEY, 2, "valueML-2");
        lrange(SOURCE_KEY, 0, ListTest.llen(SOURCE_KEY));

        ltrim(SOURCE_KEY, 1, 10);
        lrange(SOURCE_KEY, 0, ListTest.llen(SOURCE_KEY));
    }

    /**
     * 将一个值插入到已存在的列表头部
     * 如果 key 列表不存在，操作无效，返回0
     * jedis 方法签名中 value 格式为数组，实际只支持单个参数
     *
     * @param key     列表的 key
     * @param strings 插入列表的 value，类型 String
     * @return 列表中元素总数量，类型 Long
     */
    public static Long lpushx(String key, String... strings) {
        Long lpushxRst = jedis.lpushx(key, strings);
        System.out.println("jedis.lpushx:" + lpushxRst);
        return lpushxRst;
    }

    /**
     * 将一个值插入到已存在的列表尾部
     * 如果 key 列表不存在，操作无效，返回0
     * jedis 方法签名中 value 格式为数组，实际只支持单个参数
     *
     * @param key     列表的 key
     * @param strings 插入列表的 value ，类型 String
     * @return 列表中元素总数量，类型 Long
     */
    public static Long rpushx(String key, String... strings) {
        Long rpushxRst = jedis.rpushx(key, strings);
        System.out.println("jedis.rpushx:" + rpushxRst);
        return rpushxRst;
    }

    /**
     * 将一个或多个值插入到列表头部
     * 最右侧的 value 在列表头部
     * 如果 key 列表不存在，则新建 key 列表
     *
     * @param key     列表的 key
     * @param strings 插入列表的 value，类型 String[]
     * @return 列表中元素总数量，类型 Long
     */
    public static Long lpush(String key, String... strings) {
        Long lpushRst = jedis.lpush(key, strings);
        System.out.println("jedis.lpush:" + lpushRst);
        return lpushRst;
    }

    /**
     * 将一个或多个值插入到列表尾部
     * 最右侧的 value 在列表尾部
     * 如果 key 列表不存在，则新建 key 列表
     *
     * @param key     列表的 key
     * @param strings 插入列表的 value，类型 String[]
     * @return 列表中元素总数量，类型 Long
     */
    public static Long rpush(String key, String... strings) {
        Long rpushRst = jedis.rpush(key, strings);
        System.out.println("jedis.rpush:" + rpushRst);
        return rpushRst;
    }

    /**
     * 通过索引设置列表元素的值
     * 如果 index 为负数，从尾部修改，即 -1 为尾部元素
     * 如果 (index > 0 && index >= length) || (index < 0 && abs(index) > length) ，ERR index out of range 异常
     *
     * @param key   列表的 key
     * @param index 修改位置的索引，类型 long
     * @param value 新的 value，类型 String
     * @return 成功返回 "OK"
     */
    public static String lset(String key, long index, String value) {
        String lsetRst = jedis.lset(key, index, value);
        System.out.println("jedis.lset:" + lsetRst);
        return lsetRst;
    }

    /**
     * 在列表指定的元素前或后插入元素
     * 如果指定的元素不存在，操作失败，返回 -1
     *
     * @param key   列表的 key
     * @param where Client.LIST_POSITION.BEFORE(前) 或 Client.LIST_POSITION.AFTER(后)
     * @param pivot 标识位置的元素，类型 String
     * @param value 插入列表的 value，类型 String
     * @return 列表中元素总数量，类型 Long
     */
    public static Long linsert(String key, Client.LIST_POSITION where, String pivot, String value) {
        Long linsert = jedis.linsert(key, where, pivot, value);
        System.out.println("jedis.linsert:" + linsert);
        return linsert;
    }

    /**
     * 移出并获取列表的第一个元素
     * 如果列表中没有元素，返回 null
     * 原子操作
     *
     * @param key 列表的 key
     * @return 列表的第一个元素
     */
    public static String lpop(String key) {
        String lpopRst = jedis.lpop(key);
        System.out.println("jedis.lpop:" + lpopRst);
        return lpopRst;
    }

    /**
     * 移出并获取列表的最后一个元素
     * 如果列表中没有元素，返回 null
     * 原子操作
     *
     * @param key 列表的 key
     * @return 列表的最后一个元素
     */
    public static String rpop(String key) {
        String rpopRst = jedis.rpop(key);
        System.out.println("jedis.rpop:" + rpopRst);
        return rpopRst;
    }

    /**
     * 移出并获取列表的第一个元素，如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     * 如果列表中没有元素，返回 null
     *
     * @param key     列表的 key
     * @param timeout 阻塞超时时间，类型 int，单位：秒
     * @return 列表的第一个元素
     */
    public static List<String> blpop(String key, int timeout) {
        List<String> blpopRst = jedis.blpop(timeout, key);
        System.out.println("jedis.blpop:" + blpopRst);
        return blpopRst;
    }

    /**
     * 移出并获取列表的最后一个元素，如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     * 如果列表中没有元素，返回 null
     *
     * @param key     列表的 key
     * @param timeout 阻塞超时时间，类型 int，单位：秒
     * @return 列表的最后一个元素
     */
    public static List<String> brpop(String key, int timeout) {
        List<String> brpopRst = jedis.brpop(timeout, key);
        System.out.println("jedis.brpop:" + brpopRst);
        return brpopRst;
    }

    /**
     * 移除 sourceKey 列表的最后一个元素，将该元素添加到 destinationKey 列表的头部并返回
     * 如果 sourceKey 列表不存在，返回 null
     * 如果 destinationKey 列表不存在，新增 destinationKey 列表并插入值（同 lpush 操作），返回 value
     * 原子操作
     *
     * @param sourceKey      移出元素列表的 key
     * @param destinationKey 目标列表的 key
     * @return 从 sourceKey 列表尾部移出并添加到 destinationKey 列表头部的元素
     */
    public static String rpoplpush(String sourceKey, String destinationKey) {
        String rpoplpushRst = jedis.rpoplpush(sourceKey, destinationKey);
        System.out.println("jedis.rpoplpush:" + rpoplpushRst);
        return rpoplpushRst;
    }

    /**
     * 移除 sourceKey 列表的最后一个元素，将该元素添加到 destinationKey 列表的头部并返回
     * 如果 sourceKey 列表没有元素，会阻塞列表直到等待超时或发现可弹出元素为止
     * 如果 sourceKey 列表不存在，返回 null
     * 如果 destinationKey 列表不存在，新增 destinationKey 列表并插入值（同 lpush 操作），返回 value
     *
     * @param sourceKey      移出元素列表的 key
     * @param destinationKey 目标列表的 key
     * @param timeout        阻塞超时时间，类型 int，单位：秒
     * @return 从 sourceKey 列表尾部移出并添加到 destinationKey 列表头部的元素
     */
    public static String brpoplpush(String sourceKey, String destinationKey, int timeout) {
        String brpoplpushRst = jedis.brpoplpush(sourceKey, destinationKey, timeout);
        System.out.println("jedis.brpoplpush:" + brpoplpushRst);
        return brpoplpushRst;
    }

    /**
     * 移除列表元素
     * 根据参数 count 的值，移除列表中与参数 value 相等的元素
     * count > 0 : 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count
     * count < 0 : 从表尾开始向表头搜索，移除与 value 相等的元素，数量为 count 的绝对值
     * count = 0 : 移除表中所有与 value 相等的值
     *
     * @param key   列表的 key
     * @param count 移除元素的个数
     * @param value 要移除的元素
     * @return 被移除元素的数量，列表不存在时返回 0
     */
    public static Long lrem(String key, long count, String value) {
        Long lremRst = jedis.lrem(key, count, value);
        System.out.println("jedis.lrem:" + lremRst);
        return lremRst;
    }

    /**
     * 对一个列表进行修剪，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除
     * 保留区间为 [start, stop] 左右闭合区间
     * stop < 0 时表示从列表尾部向头部计数，-1 表示最后一个元素
     *
     * @param key   列表的 key
     * @param start 保留元素的开始索引
     * @param stop  保留元素的结束索引
     * @return 成功返回 "OK"，即使列表不存在，也返回 "OK"
     */
    public static String ltrim(String key, long start, long stop) {
        String ltrimRst = jedis.ltrim(key, start, stop);
        System.out.println("jedis.ltrim:" + ltrimRst);
        return ltrimRst;
    }

    /**
     * 获取列表长度
     *
     * @param key 列表的 key
     * @return 列表元素数量，类型 Long
     */
    public static Long llen(String key) {
        Long length = jedis.llen(key);
        System.out.println("jedis.llen:" + length);
        return length;
    }

    /**
     * 获取列表指定范围内的元素
     * 索引从 0 开始，取 [start, stop] 左右闭合区间
     *
     * @param key   列表的 key
     * @param start 索引开始位
     * @param stop  索引结束位
     * @return 指定范围内的元素列表，类型 List<String>
     */
    public static List<String> lrange(String key, long start, long stop) {
        List<String> lrangeRst = jedis.lrange(key, start, stop);
        System.out.println("jedis.lrange:" + lrangeRst);
        return lrangeRst;
    }
}
