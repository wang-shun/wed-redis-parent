package com.dianping.wed.cache.redis.util;

import redis.clients.jedis.Jedis;

public interface JedisCallback<T> {
    T doWithJedis(Jedis jedis);
}