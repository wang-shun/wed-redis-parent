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
package com.dianping.wed.cache.redis.biz;


import com.dianping.wed.cache.redis.api.WeddingRedisKeyConfigurationService;
import com.dianping.wed.cache.redis.api.WeddingRedisService;
import com.dianping.wed.cache.redis.dto.WeddingRedisExpireDTO;
import com.dianping.wed.cache.redis.dto.WeddingRedisKeyDTO;
import com.dianping.wed.cache.redis.dto.WeddingRedisPairDTO;
import com.dianping.wed.cache.redis.dto.WeddingRedisTupleDTO;
import com.dianping.wed.cache.redis.enums.RedisActionType;
import com.dianping.wed.cache.redis.util.JedisCallback;
import com.dianping.wed.cache.redis.util.JedisHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Tuple;

import java.util.*;

/**
 * @author bo.lv
 */
public class WeddingRedisServiceImpl implements WeddingRedisService {


    private WeddingRedisKeyConfigurationService wcsWeddingRedisKeyCfgService;

    //************************** String ********************************//
    @Override
    public long incr(WeddingRedisKeyDTO redisKey) {
        final String finalKey = wcsWeddingRedisKeyCfgService.generateKey(redisKey);
        return JedisHelper.doJedisOperation(new JedisCallback<Long>() {
            @Override
            public Long doWithJedis(Jedis jedis) {
                return jedis.incr(finalKey);
            }
        }, finalKey, RedisActionType.WRITE);
    }

    @Override
    public long incrBy(WeddingRedisKeyDTO redisKey, final long increment) {
        final String finalKey = wcsWeddingRedisKeyCfgService.generateKey(redisKey);
        return JedisHelper.doJedisOperation(new JedisCallback<Long>() {
            @Override
            public Long doWithJedis(Jedis jedis) {
                return jedis.incrBy(finalKey, increment);
            }
        }, finalKey, RedisActionType.WRITE);
    }

    @Override
    public long decr(WeddingRedisKeyDTO redisKey) {
        final String finalKey = wcsWeddingRedisKeyCfgService.generateKey(redisKey);
        return JedisHelper.doJedisOperation(new JedisCallback<Long>() {
            @Override
            public Long doWithJedis(Jedis jedis) {
                return jedis.decr(finalKey);
            }
        }, finalKey, RedisActionType.WRITE);
    }

    @Override
    public long decrBy(WeddingRedisKeyDTO redisKey, final long decrement) {
        final String finalKey = wcsWeddingRedisKeyCfgService.generateKey(redisKey);
        return JedisHelper.doJedisOperation(new JedisCallback<Long>() {
            @Override
            public Long doWithJedis(Jedis jedis) {
                return jedis.decrBy(finalKey, decrement);
            }
        }, finalKey, RedisActionType.WRITE);
    }

    @Override
    public String get(WeddingRedisKeyDTO redisKey) {
        final String finalKey = wcsWeddingRedisKeyCfgService.generateKey(redisKey);
        return JedisHelper.doJedisOperation(new JedisCallback<String>() {
            @Override
            public String doWithJedis(Jedis jedis) {
                return jedis.get(finalKey);
            }
        }, finalKey, RedisActionType.READ);
    }

    @Override
    public List<String> mGet(List<WeddingRedisKeyDTO> redisKeyList) {
        //TODO NOTE:这里所有的key在一个节点上可以这么实现，以后支持到多节点必须单独每个key调用redis，然后组装成list返回
        final String[] finalKeyList = wcsWeddingRedisKeyCfgService.generateKeys(redisKeyList);
        return JedisHelper.doJedisOperation(new JedisCallback<List<String>>() {
            @Override
            public List<String> doWithJedis(Jedis jedis) {
                return jedis.mget(finalKeyList);
            }
        }, finalKeyList[0], RedisActionType.READ);
    }

    @Override
    public String set(WeddingRedisKeyDTO redisKey, final String value) {
        final String finalKey = wcsWeddingRedisKeyCfgService.generateKey(redisKey);
        return JedisHelper.doJedisOperation(new JedisCallback<String>() {
            @Override
            public String doWithJedis(Jedis jedis) {
                return jedis.set(finalKey, value);
            }
        }, finalKey, RedisActionType.WRITE);
    }

    @Override
    public String mSet(List<WeddingRedisPairDTO> redisPairList) {
        //TODO NOTE:这里所有的key在一个节点上可以这么实现，以后支持到多节点必须单独每个key调用redis，然后组装成list返回
        if (CollectionUtils.isEmpty(redisPairList)) {
            return "ERROR";
        }
        final String[] param = new String[redisPairList.size() * 2];
        int index = 0;
        for (WeddingRedisPairDTO redisPair : redisPairList) {
            param[index] = wcsWeddingRedisKeyCfgService.generateKey(redisPair.getWeddingRedisKeyDTO());
            param[index + 1] = redisPair.getValue();
            index += 2;
        }
        return JedisHelper.doJedisOperation(new JedisCallback<String>() {
            @Override
            public String doWithJedis(Jedis jedis) {
                return jedis.mset(param);
            }
        }, param[0], RedisActionType.WRITE);
    }

    @Override
    public String setEx(WeddingRedisKeyDTO redisKey, final int seconds, final String value) {
        final String finalKey = wcsWeddingRedisKeyCfgService.generateKey(redisKey);
        return JedisHelper.doJedisOperation(new JedisCallback<String>() {
            @Override
            public String doWithJedis(Jedis jedis) {
                return jedis.setex(finalKey, seconds, value);
            }
        }, finalKey, RedisActionType.WRITE);
    }


    //************************** List ********************************//
    @Override
    public String lIndex(WeddingRedisKeyDTO redisKey, final long index) {
        final String finalKey = wcsWeddingRedisKeyCfgService.generateKey(redisKey);
        return JedisHelper.doJedisOperation(new JedisCallback<String>() {
            @Override
            public String doWithJedis(Jedis jedis) {
                return jedis.lindex(finalKey, index);
            }
        }, finalKey, RedisActionType.READ);
    }

    @Override
    public long lLen(WeddingRedisKeyDTO redisKey) {
        final String finalKey = wcsWeddingRedisKeyCfgService.generateKey(redisKey);
        return JedisHelper.doJedisOperation(new JedisCallback<Long>() {
            @Override
            public Long doWithJedis(Jedis jedis) {
                return jedis.llen(finalKey);
            }
        }, finalKey, RedisActionType.READ);
    }

    @Override
    public String lPop(WeddingRedisKeyDTO redisKey) {
        final String finalKey = wcsWeddingRedisKeyCfgService.generateKey(redisKey);
        return JedisHelper.doJedisOperation(new JedisCallback<String>() {
            @Override
            public String doWithJedis(Jedis jedis) {
                return jedis.lpop(finalKey);
            }
        }, finalKey, RedisActionType.WRITE);
    }

    @Override
    public long lPush(WeddingRedisKeyDTO redisKey, final String... value) {
        if (ArrayUtils.isEmpty(value)) {
            return 0;
        }
        final String finalKey = wcsWeddingRedisKeyCfgService.generateKey(redisKey);
        return JedisHelper.doJedisOperation(new JedisCallback<Long>() {
            @Override
            public Long doWithJedis(Jedis jedis) {
                return jedis.lpush(finalKey, value);
            }
        }, finalKey, RedisActionType.WRITE);
    }

//    @Override
//    public long lPushx(WeddingRedisKeyDTO redisKey, final String... value) {
//        if (ArrayUtils.isEmpty(value)) {
//            return 0;
//        }
//        final String finalKey = wcsRedisKeyConfigurationService.generateKey(redisKey);
//        return JedisHelper.doJedisOperation(new JedisCallback<Long>() {
//            @Override
//            public Long doWithJedis(Jedis jedis) {
//                return jedis.lpushx(finalKey, value);
//            }
//        }, finalKey, RedisActionType.WRITE);
//    }

    @Override
    public List<String> lRange(WeddingRedisKeyDTO redisKey, final long start, final long end) {
        final String finalKey = wcsWeddingRedisKeyCfgService.generateKey(redisKey);
        return JedisHelper.doJedisOperation(new JedisCallback<List<String>>() {
            @Override
            public List<String> doWithJedis(Jedis jedis) {
                return jedis.lrange(finalKey, start, end);
            }
        }, finalKey, RedisActionType.READ);
    }

    @Override
    public String lSet(WeddingRedisKeyDTO redisKey, final long index, final String value) {
        final String finalKey = wcsWeddingRedisKeyCfgService.generateKey(redisKey);
        return JedisHelper.doJedisOperation(new JedisCallback<String>() {
            @Override
            public String doWithJedis(Jedis jedis) {
                return jedis.lset(finalKey, index, value);
            }
        }, finalKey, RedisActionType.WRITE);
    }

    @Override
    public String rPop(WeddingRedisKeyDTO redisKey) {
        final String finalKey = wcsWeddingRedisKeyCfgService.generateKey(redisKey);
        return JedisHelper.doJedisOperation(new JedisCallback<String>() {
            @Override
            public String doWithJedis(Jedis jedis) {
                return jedis.rpop(finalKey);
            }
        }, finalKey, RedisActionType.WRITE);
    }

    @Override
    public long rPush(WeddingRedisKeyDTO redisKey, final String... values) {
        if (ArrayUtils.isEmpty(values)) {
            return 0;
        }
        final String finalKey = wcsWeddingRedisKeyCfgService.generateKey(redisKey);
        return JedisHelper.doJedisOperation(new JedisCallback<Long>() {
            @Override
            public Long doWithJedis(Jedis jedis) {
                return jedis.rpush(finalKey, values);
            }
        }, finalKey, RedisActionType.WRITE);
    }

//    @Override
//    public long rPushx(WeddingRedisKeyDTO redisKey, final String... values) {
//        if (ArrayUtils.isEmpty(values)) {
//            return 0;
//        }
//        final String finalKey = wcsRedisKeyConfigurationService.generateKey(redisKey);
//        return JedisHelper.doJedisOperation(new JedisCallback<Long>() {
//            @Override
//            public Long doWithJedis(Jedis jedis) {
//                return jedis.rpushx(finalKey, values);
//            }
//        }, finalKey, RedisActionType.WRITE);
//    }

    //************************** Set ********************************//

    @Override
    public long sAdd(WeddingRedisKeyDTO redisKey, final String... members) {
        if (ArrayUtils.isEmpty(members)) {
            return 0;
        }
        final String finalKey = wcsWeddingRedisKeyCfgService.generateKey(redisKey);
        return JedisHelper.doJedisOperation(new JedisCallback<Long>() {
            @Override
            public Long doWithJedis(Jedis jedis) {
                return jedis.sadd(finalKey, members);
            }
        }, finalKey, RedisActionType.WRITE);
    }

    @Override
    public long sCard(WeddingRedisKeyDTO redisKey) {
        final String finalKey = wcsWeddingRedisKeyCfgService.generateKey(redisKey);
        return JedisHelper.doJedisOperation(new JedisCallback<Long>() {
            @Override
            public Long doWithJedis(Jedis jedis) {
                return jedis.scard(finalKey);
            }
        }, finalKey, RedisActionType.READ);
    }

    @Override
    public boolean sIsMember(WeddingRedisKeyDTO redisKey, final String member) {
        final String finalKey = wcsWeddingRedisKeyCfgService.generateKey(redisKey);
        return JedisHelper.doJedisOperation(new JedisCallback<Boolean>() {
            @Override
            public Boolean doWithJedis(Jedis jedis) {
                return jedis.sismember(finalKey, member);
            }
        }, finalKey, RedisActionType.READ);
    }

    @Override
    public Set<String> sMembers(WeddingRedisKeyDTO redisKey) {
        final String finalKey = wcsWeddingRedisKeyCfgService.generateKey(redisKey);
        return JedisHelper.doJedisOperation(new JedisCallback<Set<String>>() {
            @Override
            public Set<String> doWithJedis(Jedis jedis) {
                return jedis.smembers(finalKey);
            }
        }, finalKey, RedisActionType.READ);
    }

    @Override
    public long sRem(WeddingRedisKeyDTO redisKey, final String... members) {
        if (ArrayUtils.isEmpty(members)) {
            return -1;
        }
        final String finalKey = wcsWeddingRedisKeyCfgService.generateKey(redisKey);
        return JedisHelper.doJedisOperation(new JedisCallback<Long>() {
            @Override
            public Long doWithJedis(Jedis jedis) {
                return jedis.srem(finalKey, members);
            }
        }, finalKey, RedisActionType.WRITE);
    }

    //************************** Sorted Set ********************************//

    @Override
    public long zAdd(WeddingRedisKeyDTO redisKey, final double score, final String member) {
        final String finalKey = wcsWeddingRedisKeyCfgService.generateKey(redisKey);
        return JedisHelper.doJedisOperation(new JedisCallback<Long>() {
            @Override
            public Long doWithJedis(Jedis jedis) {
                return jedis.zadd(finalKey, score, member);
            }
        }, finalKey, RedisActionType.WRITE);
    }

    @Override
    public long zMAdd(WeddingRedisKeyDTO redisKey, final Map<String, Double> scoreMembers) {
        if (MapUtils.isEmpty(scoreMembers)) {
            return -1;
        }
        final String finalKey = wcsWeddingRedisKeyCfgService.generateKey(redisKey);
        return JedisHelper.doJedisOperation(new JedisCallback<Long>() {
            @Override
            public Long doWithJedis(Jedis jedis) {
                return jedis.zadd(finalKey, scoreMembers);
            }
        }, finalKey, RedisActionType.WRITE);
    }

    @Override
    public long zCard(WeddingRedisKeyDTO redisKey) {
        final String finalKey = wcsWeddingRedisKeyCfgService.generateKey(redisKey);
        return JedisHelper.doJedisOperation(new JedisCallback<Long>() {
            @Override
            public Long doWithJedis(Jedis jedis) {
                return jedis.zcard(finalKey);
            }
        }, finalKey, RedisActionType.READ);
    }

    @Override
    public long zCount(WeddingRedisKeyDTO redisKey, final double min, final double max) {
        final String finalKey = wcsWeddingRedisKeyCfgService.generateKey(redisKey);
        return JedisHelper.doJedisOperation(new JedisCallback<Long>() {
            @Override
            public Long doWithJedis(Jedis jedis) {
                return jedis.zcount(finalKey, min, max);
            }
        }, finalKey, RedisActionType.READ);
    }

    @Override
    public double zIncrBy(WeddingRedisKeyDTO redisKey, final double score, final String member) {
        final String finalKey = wcsWeddingRedisKeyCfgService.generateKey(redisKey);
        return JedisHelper.doJedisOperation(new JedisCallback<Double>() {
            @Override
            public Double doWithJedis(Jedis jedis) {
                return jedis.zincrby(finalKey, score, member);
            }
        }, finalKey, RedisActionType.WRITE);
    }

    @Override
    public Set<String> zRange(WeddingRedisKeyDTO redisKey, final long start, final long end) {
        final String finalKey = wcsWeddingRedisKeyCfgService.generateKey(redisKey);
        return JedisHelper.doJedisOperation(new JedisCallback<Set<String>>() {
            @Override
            public Set<String> doWithJedis(Jedis jedis) {
                return jedis.zrange(finalKey, start, end);
            }
        }, finalKey, RedisActionType.READ);
    }

    @Override
    public Set<String> zRevRange(WeddingRedisKeyDTO redisKey, final long start, final long end) {
        final String finalKey = wcsWeddingRedisKeyCfgService.generateKey(redisKey);
        return JedisHelper.doJedisOperation(new JedisCallback<Set<String>>() {
            @Override
            public Set<String> doWithJedis(Jedis jedis) {
                return jedis.zrevrange(finalKey, start, end);
            }
        }, finalKey, RedisActionType.READ);
    }

    @Override
    public Set<WeddingRedisTupleDTO> zRangeWithScores(WeddingRedisKeyDTO redisKey, final long start, final long end) {
        final String finalKey = wcsWeddingRedisKeyCfgService.generateKey(redisKey);
        Set<Tuple> tuples = JedisHelper.doJedisOperation(new JedisCallback<Set<Tuple>>() {
            @Override
            public Set<Tuple> doWithJedis(Jedis jedis) {
                return jedis.zrangeWithScores(finalKey, start, end);
            }
        }, finalKey, RedisActionType.READ);

        Set<WeddingRedisTupleDTO> resultTuples = new LinkedHashSet<WeddingRedisTupleDTO>();
        if (CollectionUtils.isNotEmpty(tuples)) {
            for (Tuple tuple : tuples) {
                WeddingRedisTupleDTO wedTuple = new WeddingRedisTupleDTO();
                wedTuple.setElement(tuple.getElement());
                wedTuple.setScore(tuple.getScore());
                resultTuples.add(wedTuple);
            }
        }
        return resultTuples;
    }

    @Override
    public Set<WeddingRedisTupleDTO> zRevRangeWithScores(WeddingRedisKeyDTO redisKey, final long start, final long end) {
        final String finalKey = wcsWeddingRedisKeyCfgService.generateKey(redisKey);
        Set<Tuple> tuples = JedisHelper.doJedisOperation(new JedisCallback<Set<Tuple>>() {
            @Override
            public Set<Tuple> doWithJedis(Jedis jedis) {
                return jedis.zrevrangeWithScores(finalKey, start, end);
            }
        }, finalKey, RedisActionType.READ);

        Set<WeddingRedisTupleDTO> resultTuples = new LinkedHashSet<WeddingRedisTupleDTO>();
        if (CollectionUtils.isNotEmpty(tuples)) {
            for (Tuple tuple : tuples) {
                WeddingRedisTupleDTO wedTuple = new WeddingRedisTupleDTO();
                wedTuple.setElement(tuple.getElement());
                wedTuple.setScore(tuple.getScore());
                resultTuples.add(wedTuple);
            }
        }
        return resultTuples;
    }

    @Override
    public long zRank(WeddingRedisKeyDTO redisKey, final String member) {
        final String finalKey = wcsWeddingRedisKeyCfgService.generateKey(redisKey);
        Long rank = JedisHelper.doJedisOperation(new JedisCallback<Long>() {
            @Override
            public Long doWithJedis(Jedis jedis) {
                return jedis.zrank(finalKey, member);
            }
        }, finalKey, RedisActionType.READ);

        return rank == null ? -1 : rank;
    }

    @Override
    public long zRevRank(WeddingRedisKeyDTO redisKey, final String member) {
        final String finalKey = wcsWeddingRedisKeyCfgService.generateKey(redisKey);
        Long rank = JedisHelper.doJedisOperation(new JedisCallback<Long>() {
            @Override
            public Long doWithJedis(Jedis jedis) {
                return jedis.zrevrank(finalKey, member);
            }
        }, finalKey, RedisActionType.READ);

        return rank == null ? -1 : rank;
    }

    @Override
    public Double zScore(WeddingRedisKeyDTO redisKey, final String member) {
        final String finalKey = wcsWeddingRedisKeyCfgService.generateKey(redisKey);
        return JedisHelper.doJedisOperation(new JedisCallback<Double>() {
            @Override
            public Double doWithJedis(Jedis jedis) {
                return jedis.zscore(finalKey, member);
            }
        }, finalKey, RedisActionType.READ);
    }

    @Override
    public long zRem(WeddingRedisKeyDTO redisKey, final String... members) {
        if (ArrayUtils.isEmpty(members)) {
            return 0L;
        }
        final String finalKey = wcsWeddingRedisKeyCfgService.generateKey(redisKey);
        return JedisHelper.doJedisOperation(new JedisCallback<Long>() {
            @Override
            public Long doWithJedis(Jedis jedis) {
                return jedis.zrem(finalKey, members);
            }
        }, finalKey, RedisActionType.WRITE);
    }

    //************************** hash ********************************//

    @Override
    public String hGet(WeddingRedisKeyDTO redisKey, final String field) {
        final String finalKey = wcsWeddingRedisKeyCfgService.generateKey(redisKey);
        return JedisHelper.doJedisOperation(new JedisCallback<String>() {
            @Override
            public String doWithJedis(Jedis jedis) {
                return jedis.hget(finalKey, field);
            }
        }, finalKey, RedisActionType.READ);
    }

    @Override
    public List<String> hMGet(WeddingRedisKeyDTO redisKey, final String... fields) {
        if (ArrayUtils.isEmpty(fields)) {
            return new ArrayList<String>();
        }
        final String finalKey = wcsWeddingRedisKeyCfgService.generateKey(redisKey);
        return JedisHelper.doJedisOperation(new JedisCallback<List<String>>() {
            @Override
            public List<String> doWithJedis(Jedis jedis) {
                return jedis.hmget(finalKey, fields);
            }
        }, finalKey, RedisActionType.READ);
    }

    @Override
    public long hSet(WeddingRedisKeyDTO redisKey, final String field, final String value) {
        final String finalKey = wcsWeddingRedisKeyCfgService.generateKey(redisKey);
        return JedisHelper.doJedisOperation(new JedisCallback<Long>() {
            @Override
            public Long doWithJedis(Jedis jedis) {
                return jedis.hset(finalKey, field, value);
            }
        }, finalKey, RedisActionType.WRITE);
    }

    @Override
    public String hMSet(WeddingRedisKeyDTO redisKey, final Map<String, String> fieldValues) {
        if (MapUtils.isEmpty(fieldValues)) {
            return null;
        }
        final String finalKey = wcsWeddingRedisKeyCfgService.generateKey(redisKey);
        return JedisHelper.doJedisOperation(new JedisCallback<String>() {
            @Override
            public String doWithJedis(Jedis jedis) {
                return jedis.hmset(finalKey, fieldValues);
            }
        }, finalKey, RedisActionType.WRITE);
    }

    @Override
    public Map<String, String> hGetAll(WeddingRedisKeyDTO redisKey) {
        final String finalKey = wcsWeddingRedisKeyCfgService.generateKey(redisKey);
        return JedisHelper.doJedisOperation(new JedisCallback<Map<String, String>>() {
            @Override
            public Map<String, String> doWithJedis(Jedis jedis) {
                return jedis.hgetAll(finalKey);
            }
        }, finalKey, RedisActionType.READ);
    }

    @Override
    public List<String> hVals(WeddingRedisKeyDTO redisKey) {
        final String finalKey = wcsWeddingRedisKeyCfgService.generateKey(redisKey);
        return JedisHelper.doJedisOperation(new JedisCallback<List<String>>() {
            @Override
            public List<String> doWithJedis(Jedis jedis) {
                return jedis.hvals(finalKey);
            }
        }, finalKey, RedisActionType.READ);
    }

    @Override
    public long hIncrBy(WeddingRedisKeyDTO redisKey, final String field, final long value) {
        final String finalKey = wcsWeddingRedisKeyCfgService.generateKey(redisKey);
        return JedisHelper.doJedisOperation(new JedisCallback<Long>() {
            @Override
            public Long doWithJedis(Jedis jedis) {
                return jedis.hincrBy(finalKey, field, value);
            }
        }, finalKey, RedisActionType.WRITE);
    }

    @Override
    public Set<String> hKeys(WeddingRedisKeyDTO redisKey) {
        final String finalKey = wcsWeddingRedisKeyCfgService.generateKey(redisKey);
        return JedisHelper.doJedisOperation(new JedisCallback<Set<String>>() {
            @Override
            public Set<String> doWithJedis(Jedis jedis) {
                return jedis.hkeys(finalKey);
            }
        }, finalKey, RedisActionType.READ);
    }

    @Override
    public long hLen(WeddingRedisKeyDTO redisKey) {
        final String finalKey = wcsWeddingRedisKeyCfgService.generateKey(redisKey);
        return JedisHelper.doJedisOperation(new JedisCallback<Long>() {
            @Override
            public Long doWithJedis(Jedis jedis) {
                return jedis.hlen(finalKey);
            }
        }, finalKey, RedisActionType.READ);
    }

    @Override
    public long hDel(WeddingRedisKeyDTO redisKey, final String... fields) {
        if(ArrayUtils.isEmpty(fields)){
            return 0;
        }
        final String finalKey = wcsWeddingRedisKeyCfgService.generateKey(redisKey);
        return JedisHelper.doJedisOperation(new JedisCallback<Long>() {
            @Override
            public Long doWithJedis(Jedis jedis) {
                return jedis.hdel(finalKey, fields);
            }
        }, finalKey, RedisActionType.WRITE);
    }

    @Override
    public boolean hExists(WeddingRedisKeyDTO redisKey, final String field) {
        final String finalKey = wcsWeddingRedisKeyCfgService.generateKey(redisKey);
        return JedisHelper.doJedisOperation(new JedisCallback<Boolean>() {
            @Override
            public Boolean doWithJedis(Jedis jedis) {
                return jedis.hexists(finalKey, field);
            }
        }, finalKey, RedisActionType.READ);
    }

    //************************** key ********************************//

    @Override
    public long expire(WeddingRedisKeyDTO redisKey, final int seconds) {
        final String finalKey = wcsWeddingRedisKeyCfgService.generateKey(redisKey);
        return JedisHelper.doJedisOperation(new JedisCallback<Long>() {
            @Override
            public Long doWithJedis(Jedis jedis) {
                return jedis.expire(finalKey, seconds);
            }
        }, finalKey, RedisActionType.WRITE);
    }

    @Override
    public List<Long> mExpire(final List<WeddingRedisExpireDTO> expireList) {
        List<Long> result = new ArrayList<Long>();
        if (CollectionUtils.isEmpty(expireList)) {
            return result;
        }

        List<WeddingRedisKeyDTO> keys = new ArrayList<WeddingRedisKeyDTO>();
        for (WeddingRedisExpireDTO weddingRedisExpireDTO : expireList) {
            keys.add(weddingRedisExpireDTO.getKey());
        }
        final String[] finalKeys = wcsWeddingRedisKeyCfgService.generateKeys(keys);
        return JedisHelper.doJedisOperation(new JedisCallback<List<Long>>() {
            @Override
            public List<Long> doWithJedis(Jedis jedis) {
                Pipeline pipelined = jedis.pipelined();
                for (int index = 0; index < finalKeys.length; index++) {
                    pipelined.expire(finalKeys[index], expireList.get(index).getSeconds());
                }

                List<Object> pipelinedResults = pipelined.syncAndReturnAll();
                List<Long> result = new ArrayList<Long>(pipelinedResults.size());
                for (Object pipelinedResult : pipelinedResults) {
                    try {
                        result.add(Long.valueOf(pipelinedResult.toString()));
                    } catch (Exception e) {
                        result.add(0L);
                    }
                }
                return result;
            }
        }, finalKeys[0], RedisActionType.WRITE);
    }

    @Override
    public long del(WeddingRedisKeyDTO redisKey) {
        final String finalKey = wcsWeddingRedisKeyCfgService.generateKey(redisKey);
        return JedisHelper.doJedisOperation(new JedisCallback<Long>() {
            @Override
            public Long doWithJedis(Jedis jedis) {
                return jedis.del(finalKey);
            }
        }, finalKey, RedisActionType.WRITE);
    }

    public void setWcsWeddingRedisKeyCfgService(WeddingRedisKeyConfigurationService wcsWeddingRedisKeyCfgService) {
        this.wcsWeddingRedisKeyCfgService = wcsWeddingRedisKeyCfgService;
    }
}
