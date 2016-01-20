/**
 * File Created at 15/3/24
 * <p/>
 * Copyright 2014 dianping.com.
 * All rights reserved.
 * <p/>
 * This software is the confidential and proprietary information of
 * Dianping Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with dianping.com.
 */
package com.dianping.wed.cache.redis.api;


import com.dianping.wed.cache.redis.dto.WeddingRedisExpireDTO;
import com.dianping.wed.cache.redis.dto.WeddingRedisKeyDTO;
import com.dianping.wed.cache.redis.dto.WeddingRedisPairDTO;
import com.dianping.wed.cache.redis.dto.WeddingRedisTupleDTO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author bo.lv
 */
public interface WeddingRedisService {
    //************************** String ********************************//

    /**
     * 将 key 中储存的数字值增一。
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。
     * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
     * <strong>注意：这是一个针对字符串的操作，因为 Redis 没有专用的整数类型，所以 key 内储存的字符串被解释为十进制 64 位有符号整数来执行 INCR 操作。</strong>
     *
     * @param redisKey
     * @return 执行 INCR 命令之后 key 的值
     */
    public long incr(WeddingRedisKeyDTO redisKey);


    /**
     * 将 key 所储存的值加上增量 increment 。
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCRBY 命令。
     * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
     *
     * @param redisKey
     * @param increment
     * @return 加上 increment 之后， key 的值。
     */
    public long incrBy(WeddingRedisKeyDTO redisKey, long increment);

    /**
     * 将 key 中储存的数字值减一。
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 DECR 操作。
     * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
     *
     * @param redisKey
     * @return 执行 DECR 命令之后 key 的值
     */
    public long decr(WeddingRedisKeyDTO redisKey);


    /**
     * 将 key 所储存的值减去减量 decrement 。
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 DECRBY 操作。
     * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
     *
     * @param redisKey
     * @param decrement
     * @return 减去 decrement 之后， key 的值
     */
    public long decrBy(WeddingRedisKeyDTO redisKey, long decrement);

    /**
     * 返回 key 所关联的字符串值。
     * 如果 key 不存在那么返回特殊值 null 。
     * 假如 key 储存的值不是字符串类型，返回一个错误，因为 GET 只能用于处理字符串值。
     *
     * @param redisKey
     * @return 当 key 不存在时，返回 null ，否则，返回 key 的值。如果 key 不是字符串类型，那么返回一个错误。
     */
    public String get(WeddingRedisKeyDTO redisKey);

    /**
     * 返回所有(一个或多个)给定 key 的值。
     * 如果给定的 key 里面，有某个 key 不存在，那么这个 key 返回特殊值 null 。因此，该命令永不失败。
     *
     * @param redisKeyList
     * @return
     */
    public List<String> mGet(List<WeddingRedisKeyDTO> redisKeyList);

    /**
     * 将字符串值 value 关联到 key 。
     * 如果 key 已经持有其他值， SET 就覆写旧值，无视类型。
     * 对于某个原本带有生存时间（TTL）的键来说， 当 SET 命令成功在这个键上执行时， 这个键原有的 TTL 将被清除。
     *
     * @param redisKey
     * @param value
     * @return 在设置操作成功完成时，才返回 OK
     */
    public String set(WeddingRedisKeyDTO redisKey, String value);

    /**
     * 同时设置一个或多个 key-value 对。
     * 如果某个给定 key 已经存在，那么 MSET 会用新值覆盖原来的旧值，如果这不是你所希望的效果，请考虑使用 MSETNX 命令：它只会在所有给定 key 都不存在的情况下进行设置操作。
     *
     * @param redisPairList
     * @return 参数为空则返回ERROR，其他情况总是返回 OK
     */
    public String mSet(List<WeddingRedisPairDTO> redisPairList);

    /**
     * 将值 value 关联到 key ，并将 key 的生存时间设为 seconds (以秒为单位)。
     * 如果 key 已经存在， SETEX 命令将覆写旧值。
     *
     * @param redisKey
     * @param seconds
     * @param value
     * @return 设置成功时返回 OK 。当 seconds 参数不合法时，返回一个错误。
     */
    public String setEx(WeddingRedisKeyDTO redisKey, int seconds, String value);

    //************************** List ********************************//

    /**
     * 返回列表 key 中，下标为 index 的元素。
     * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。
     * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
     *
     * @param redisKey
     * @param index
     * @return 列表中下标为 index 的元素。如果 index 参数的值不在列表的区间范围内(out of range)，返回 null 。
     */
    public String lIndex(WeddingRedisKeyDTO redisKey, long index);

    /**
     * 返回列表 key 的长度。
     * 如果 key 不存在，则 key 被解释为一个空列表，返回 0 .
     * 如果 key 不是列表类型，返回一个错误。
     *
     * @param redisKey
     * @return 列表 key 的长度
     */
    public long lLen(WeddingRedisKeyDTO redisKey);

    /**
     * 移除并返回列表 key 的头元素。
     *
     * @param redisKey
     * @return 列表的头元素。当 key 不存在时，返回 null 。
     */
    public String lPop(WeddingRedisKeyDTO redisKey);

    /**
     * 将一个或多个值 value 插入到列表 key 的表头
     * 如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表头：
     * 比如说，对空列表 mylist 执行命令 LPUSH mylist a b c ，列表的值将是 c b a ，
     * 这等同于原子性地执行 LPUSH mylist a 、 LPUSH mylist b 和 LPUSH mylist c 三个命令。
     * 如果 key 不存在，一个空列表会被创建并执行 LPUSH 操作。
     * 当 key 存在但不是列表类型时，返回一个错误。
     *
     * @param redisKey
     * @param value
     * @return 执行 LPUSH 命令后，列表的长度。
     */
    public long lPush(WeddingRedisKeyDTO redisKey, String... value);

    /**
     * 将值 value 插入到列表 key 的表头，当且仅当 key 存在并且是一个列表。
     * 和 LPUSH 命令相反，当 key 不存在时， LPUSHX 命令什么也不做。
     *
     * @param redisKey
     * @param value
     * @return LPUSHX 命令执行之后，表的长度
     */
    //public long lPushx(WeddingRedisKeyDTO redisKey, String... value);

    /**
     * 返回列表 key 中指定区间内的元素(闭区间)，区间以偏移量 start 和 stop 指定。
     * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。
     * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
     *
     * @param redisKey
     * @param start
     * @param end
     * @return 一个列表，包含指定区间内的元素
     */
    public List<String> lRange(WeddingRedisKeyDTO redisKey, long start, long end);

    /**
     * 将列表 key 下标为 index 的元素的值设置为 value 。
     * 当 index 参数超出范围，或对一个空列表( key 不存在)进行 LSET 时，返回一个错误。
     *
     * @param redisKey
     * @param index
     * @param value
     * @return 操作成功返回 ok ，否则返回错误信息。
     */
    public String lSet(WeddingRedisKeyDTO redisKey, long index, String value);

    /**
     * 移除并返回列表 key 的尾元素
     *
     * @param redisKey
     * @return 列表的尾元素。当 key 不存在时，返回 null 。
     */
    public String rPop(WeddingRedisKeyDTO redisKey);

    /**
     * 将一个或多个值 value 插入到列表 key 的表尾(最右边)。
     * 如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表尾：比如对一个空列表 mylist 执行 RPUSH mylist a b c ，得出的结果列表为 a b c ，等同于执行命令 RPUSH mylist a 、 RPUSH mylist b 、 RPUSH mylist c 。
     * 如果 key 不存在，一个空列表会被创建并执行 RPUSH 操作。
     * 当 key 存在但不是列表类型时，返回一个错误。
     *
     * @param redisKey
     * @param values
     * @return 执行 RPUSH 操作后，表的长度。
     */
    public long rPush(WeddingRedisKeyDTO redisKey, String... values);


    /**
     * 将值 value 插入到列表 key 的表尾，当且仅当 key 存在并且是一个列表。
     * 和 RPUSH 命令相反，当 key 不存在时， RPUSHX 命令什么也不做。
     *
     * @param redisKey
     * @param values
     * @return RPUSHX 命令执行之后，表的长度。
     */
    //public long rPushx(WeddingRedisKeyDTO redisKey, String... values);

    //************************** Set ********************************//

    /**
     * 将一个或多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略。
     * 假如 key 不存在，则创建一个只包含 member 元素作成员的集合。
     * 当 key 不是集合类型时，返回一个错误。
     *
     * @param redisKey
     * @param members
     * @return 被添加到集合中的新元素的数量，不包括被忽略的元素。
     */
    public long sAdd(WeddingRedisKeyDTO redisKey, String... members);

    /**
     * 返回集合 key 的基数(集合中元素的数量)。
     *
     * @param redisKey
     * @return 集合的基数。当 key 不存在时，返回 0 。
     */
    public long sCard(WeddingRedisKeyDTO redisKey);

    /**
     * 判断 member 元素是否集合 key 的成员
     *
     * @param redisKey
     * @param member
     * @return 如果 member 元素是集合的成员，返回 true 。如果 member 元素不是集合的成员，或 key 不存在，返回 false 。
     */
    public boolean sIsMember(WeddingRedisKeyDTO redisKey, String member);

    /**
     * 返回集合 key 中的所有成员。
     * 不存在的 key 被视为空集合。
     *
     * @param redisKey
     * @return 集合中的所有成员。
     */
    public Set<String> sMembers(WeddingRedisKeyDTO redisKey);

    /**
     * 移除集合 key 中的一个或多个 member 元素，不存在的 member 元素会被忽略。
     * 当 key 不是集合类型，返回一个错误。
     *
     * @param redisKey
     * @param members
     * @return 被成功移除的元素的数量，不包括被忽略的元素。
     */
    public long sRem(WeddingRedisKeyDTO redisKey, String... members);


    //************************** Sorted Set ********************************//

    /**
     * 将一个 member 元素及其 score 值加入到有序集 key 当中。
     * 如果某个 member 已经是有序集的成员，那么更新这个 member 的 score 值，并通过重新插入这个 member 元素，来保证该 member 在正确的位置上。
     * score 值可以是整数值或双精度浮点数。
     * 如果 key 不存在，则创建一个空的有序集并执行 ZADD 操作。
     * 当 key 存在但不是有序集类型时，返回一个错误。
     *
     * @param redisKey
     * @param score
     * @param member
     * @return 被成功添加的新成员的数量，不包括那些被更新的、已经存在的成员。
     */
    public long zAdd(WeddingRedisKeyDTO redisKey, double score, String member);

    /**
     * 将多个 member 元素及其 score 值加入到有序集 key 当中。
     * 如果某个 member 已经是有序集的成员，那么更新这个 member 的 score 值，并通过重新插入这个 member 元素，来保证该 member 在正确的位置上。
     * score 值可以是整数值或双精度浮点数。
     * 如果 key 不存在，则创建一个空的有序集并执行 ZADD 操作。
     * 当 key 存在但不是有序集类型时，返回一个错误。
     *
     * @param redisKey
     * @param scoreMembers
     * @return 被成功添加的新成员的数量，不包括那些被更新的、已经存在的成员。
     */
    public long zMAdd(WeddingRedisKeyDTO redisKey, Map<String, Double> scoreMembers);

    /**
     * 返回有序集 key 的基数。
     *
     * @param redisKey
     * @return 当 key 存在且是有序集类型时，返回有序集的基数。当 key 不存在时，返回 0 。
     */
    public long zCard(WeddingRedisKeyDTO redisKey);

    /**
     * 返回有序集 key 中， score 值在 min 和 max 之间(默认包括 score 值等于 min 或 max )的成员的数量。
     *
     * @param redisKey
     * @param min
     * @param max
     * @return score 值在 min 和 max 之间的成员的数量。
     */
    public long zCount(WeddingRedisKeyDTO redisKey, double min, double max);

    /**
     * 为有序集 key 的成员 member 的 score 值加上增量 increment 。
     * 可以通过传递一个负数值 increment ，让 score 减去相应的值，比如 ZINCRBY key -5 member ，就是让 member 的 score 值减去 5 。
     * 当 key 不存在，或 member 不是 key 的成员时， ZINCRBY key increment member 等同于 ZADD key increment member 。
     * 当 key 不是有序集类型时，返回一个错误。
     * score 值可以是整数值或双精度浮点数。
     *
     * @param redisKey
     * @param score
     * @param member
     * @return member 成员的新 score 值
     */
    public double zIncrBy(WeddingRedisKeyDTO redisKey, double score, String member);

    /**
     * 返回有序集 key 中，指定区间内的成员。
     * 其中成员的位置按 score 值递增(从小到大)来排序。
     * 具有相同 score 值的成员按字典序(lexicographical order )来排列。
     * 下标参数 start 和 stop 都以 0 为底，也就是说，以 0 表示有序集第一个成员，以 1 表示有序集第二个成员，以此类推。
     * 你也可以使用负数下标，以 -1 表示最后一个成员， -2 表示倒数第二个成员，以此类推。
     *
     * @param redisKey
     * @param start
     * @param end
     * @return 指定区间内，有序集成员的列表。
     */
    public Set<String> zRange(WeddingRedisKeyDTO redisKey, long start, long end);


    /**
     * 返回有序集 key 中，指定区间内的成员。
     * 其中成员的位置按 score 值递减(从大到小)来排列。
     * 具有相同 score 值的成员按字典序的逆序(reverse lexicographical order)排列。
     * 除了成员按 score 值递减的次序排列这一点外， ZREVRANGE 命令的其他方面和 ZRANGE 命令一样。
     *
     * @param redisKey
     * @param start
     * @param end
     * @return 指定区间内，有序集成员的列表。
     */
    public Set<String> zRevRange(WeddingRedisKeyDTO redisKey, long start, long end);

    /**
     * 返回有序集 key 中，指定区间内的成员。其中成员的位置按 score 值递增(从小到大)来排序。具有相同 score 值的成员按字典序(lexicographical order )来排列。
     * 如果你需要成员按 score 值递减(从大到小)来排列，请使用 zRevRangeWithScores 方法。
     * 下标参数 start 和 stop 都以 0 为底，也就是说，以 0 表示有序集第一个成员，以 1 表示有序集第二个成员，以此类推。
     * 你也可以使用负数下标，以 -1 表示最后一个成员， -2 表示倒数第二个成员，以此类推。超出范围的下标并不会引起错误。
     * 比如说，当 start 的值比有序集的最大下标还要大，或是 start > stop 时， ZRANGE 命令只是简单地返回一个空列表。
     * 另一方面，假如 stop 参数的值比有序集的最大下标还要大，那么 Redis 将 stop 当作最大下标来处理。
     * 可以通过使用 WITHSCORES 选项，来让成员和它的 score 值一并返回，返回列表以 value1,score1, ..., valueN,scoreN 的格式表示。
     * 客户端库可能会返回一些更复杂的数据类型，比如数组、元组等。
     *
     * @param redisKey
     * @param start
     * @param end
     * @return 指定区间内，带有 score 值的有序集成员的列表
     */
    public Set<WeddingRedisTupleDTO> zRangeWithScores(WeddingRedisKeyDTO redisKey, long start, long end);


    /**
     * 返回有序集 key 中，指定区间内的成员。其中成员的位置按 score 值递减(从大到小)来排列。
     * 具有相同 score 值的成员按字典序的逆序(reverse lexicographical order)排列。
     *
     * @param redisKey
     * @param start
     * @param end
     * @return 指定区间内，带有 score 值的有序集成员的列表
     */
    public Set<WeddingRedisTupleDTO> zRevRangeWithScores(WeddingRedisKeyDTO redisKey, long start, long end);

    /**
     * 返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递增(从小到大)顺序排列。
     * 排名以 0 为底，也就是说， score 值最小的成员排名为 0 。
     * 使用 ZREVRANK 命令可以获得成员按 score 值递减(从大到小)排列的排名。
     *
     * @param redisKey
     * @param member
     * @return 如果 member 是有序集 key 的成员，返回 member 的排名。如果 member 不是有序集 key 的成员，返回 -1 。
     */
    public long zRank(WeddingRedisKeyDTO redisKey, String member);

    /**
     * 返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递减(从大到小)排序。
     * 排名以 0 为底，也就是说， score 值最大的成员排名为 0 。
     * 使用 ZRANK 命令可以获得成员按 score 值递增(从小到大)排列的排名。
     *
     * @param redisKey
     * @param member
     * @return 如果 member 是有序集 key 的成员，返回 member 的排名。如果 member 不是有序集 key 的成员，返回 -1。
     */
    public long zRevRank(WeddingRedisKeyDTO redisKey, String member);

    /**
     * 返回有序集 key 中，成员 member 的 score 值。
     * 如果 member 元素不是有序集 key 的成员，或 key 不存在，返回 null 。
     *
     * @param redisKey
     * @param member
     * @return member 成员的 score 值
     */
    public Double zScore(WeddingRedisKeyDTO redisKey, String member);

    /**
     * 移除有序集 key 中的一个或多个成员，不存在的成员将被忽略。
     * 当 key 存在但不是有序集类型时，返回一个错误。
     *
     * @param redisKey
     * @param members
     * @return 被成功移除的成员的数量，不包括被忽略的成员。
     */
    public long zRem(WeddingRedisKeyDTO redisKey, String... members);


    //************************** hash ********************************//

    /**
     * 返回哈希表 key 中给定域 field 的值
     *
     * @param redisKey
     * @param field
     * @return 给定域的值。当给定域不存在或是给定 key 不存在时，返回 null
     */
    public String hGet(WeddingRedisKeyDTO redisKey, String field);

    /**
     * 返回哈希表 key 中，一个或多个给定域的值。
     * 如果给定的域不存在于哈希表，那么返回一个 null 值。
     * 因为不存在的 key 被当作一个空哈希表来处理，所以对一个不存在的 key 进行 HMGET 操作将返回一个只带有 null 值的表。
     *
     * @param redisKey
     * @param fields
     * @return 一个包含多个给定域的关联值的表，表值的排列顺序和给定域参数的请求顺序一样。
     */
    public List<String> hMGet(WeddingRedisKeyDTO redisKey, String... fields);

    /**
     * 将哈希表 key 中的域 field 的值设为 value 。
     * 如果 key 不存在，一个新的哈希表被创建并进行 HSET 操作。
     * 如果域 field 已经存在于哈希表中，旧值将被覆盖。
     *
     * @param redisKey
     * @param field
     * @param value
     * @return 如果 field 是哈希表中的一个新建域，并且值设置成功，返回 1 。如果哈希表中域 field 已经存在且旧值已被新值覆盖，返回 0 。
     */
    public long hSet(WeddingRedisKeyDTO redisKey, String field, String value);



    /**
     * 同时将多个 field-value (域-值)对设置到哈希表 key 中。
     * 此命令会覆盖哈希表中已存在的域。
     * 如果 key 不存在，一个空哈希表被创建并执行 HMSET 操作。
     *
     * @param redisKey
     * @param fieldValues
     * @return 如果命令执行成功，返回 OK 。当 key 不是哈希表(hash)类型时，返回一个错误。
     */
    public String hMSet(WeddingRedisKeyDTO redisKey, Map<String, String> fieldValues);

    /**
     * 返回哈希表 key 中，所有的域和值。
     * 在返回值里，紧跟每个域名(field name)之后是域的值(value)，所以返回值的长度是哈希表大小的两倍。
     *
     * @param redisKey
     * @return 以列表形式返回哈希表的域和域的值。若 key 不存在，返回空列表。
     */
    public Map<String, String> hGetAll(WeddingRedisKeyDTO redisKey);

    /**
     * 返回哈希表 key 中所有域的值
     *
     * @param redisKey
     * @return 一个包含哈希表中所有值的表。当 key 不存在时，返回一个空表。
     */
    public List<String> hVals(WeddingRedisKeyDTO redisKey);

    /**
     * 为哈希表 key 中的域 field 的值加上增量 increment 。
     * 增量也可以为负数，相当于对给定域进行减法操作。
     * 如果 key 不存在，一个新的哈希表被创建并执行 HINCRBY 命令。
     * 如果域 field 不存在，那么在执行命令前，域的值被初始化为 0 。
     * 对一个储存字符串值的域 field 执行 HINCRBY 命令将造成一个错误。
     *
     * @param redisKey
     * @param field
     * @param value
     * @return 执行 HINCRBY 命令之后，哈希表 key 中域 field 的值
     */
    public long hIncrBy(WeddingRedisKeyDTO redisKey, String field, long value);

    /**
     * 返回哈希表 key 中的所有域
     *
     * @param redisKey
     * @return 一个包含哈希表中所有域的表。当 key 不存在时，返回一个空表。
     */
    public Set<String> hKeys(WeddingRedisKeyDTO redisKey);

    /**
     * 返回哈希表 key 中域的数量
     *
     * @param redisKey
     * @return 哈希表中域的数量。当 key 不存在时，返回 0 。
     */
    public long hLen(WeddingRedisKeyDTO redisKey);

    /**
     * 删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略。
     *
     * @param redisKey
     * @param fields
     * @return 被成功移除的域的数量，不包括被忽略的域。
     */
    public long hDel(WeddingRedisKeyDTO redisKey, String... fields);

    /**
     * 查看哈希表 key 中，给定域 field 是否存在。
     *
     * @param redisKey
     * @param field
     * @return 如果哈希表含有给定域，返回 true 。如果哈希表不含有给定域，或 key 不存在，返回 false 。
     */
    public boolean hExists(WeddingRedisKeyDTO redisKey, String field);

    //************************** key ********************************//

    /**
     * 为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除
     *
     * @param redisKey
     * @param seconds
     * @return 设置成功返回 1 ;当 key 不存在或者不能为 key 设置生存时间时，返回 0 。
     */
    public long expire(WeddingRedisKeyDTO redisKey, int seconds);


    /**
     * 为给定 key list 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除
     *
     * @param expireList
     * @return expireList为空，返回空的list
     * 对应位置的key，设置成功返回 1 ;对应位置的 key 不存在或者不能为 key 设置生存时间时，返回 0 。
     */
    public List<Long> mExpire(List<WeddingRedisExpireDTO> expireList);

    /**
     * 删除给定的一个key
     *
     * @param redisKey
     * @return 被删除 key 的数量
     */
    public long del(WeddingRedisKeyDTO redisKey);
}
