package com.dianping.wed.cache.redis.util;

import com.dianping.wed.cache.redis.enums.RedisActionType;
import redis.clients.jedis.Jedis;

public class JedisHelper {

    private static JedisFactory jedisFactory = SpringLocator.getBean("jedisFactory");

    public static <T> T doJedisOperation(JedisCallback<T> jedisCallback, String key, RedisActionType actionType) {
        Jedis jedis = jedisFactory.get(actionType, 0);
        T result = null;

        if (jedis == null) {
            return result;
        }

        try {
            result = jedisCallback.doWithJedis(jedis);
        } finally {
            jedis.close();
        }
        return result;
    }

}
