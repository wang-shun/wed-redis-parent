/**
 * File Created at 15/6/9
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
package com.dianping.wed.cache.redis.util;

import com.dianping.lion.client.ConfigCache;
import com.dianping.lion.client.ConfigChange;
import com.dianping.lion.client.LionException;
import com.dianping.wed.cache.redis.enums.RedisActionType;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author bo.lv
 */
public class JedisFactory {

    private static Logger logger = Logger.getLogger(JedisFactory.class);
    public static final String REDIS_NODE_LIST_KEY = "wed-redis-service.redis.node.list";
    private static final String REDIS_TIMEOUT = "wed-redis-service.redis.timeout";

    //redis 超时时间
    private int redisTimeout;

    // 数组的index 表示 第几个节点
    // List 表示 每个节点包含的 master 和 slave
    private List<JedisPool>[] jedisPoolListArr;
    private Map<String,JedisPool> jedisPoolMap;

    private JedisFactory(){
    }


    public Jedis get(RedisActionType rat, int node) {
        Jedis jedis;
        if (rat == null) {
            jedis = jedisPoolListArr[0].get(0).getResource();
        } else if (rat == RedisActionType.WRITE) {
            jedis = jedisPoolListArr[0].get(0).getResource();
        } else {
            int nodeSize = jedisPoolListArr.length;
            int index = RandomUtils.nextInt(nodeSize);
            jedis = jedisPoolListArr[0].get(index).getResource();
        }
        return jedis;
    }

    public Jedis getByIPPort(String ipPort) {
        if (StringUtils.isEmpty(ipPort)) {
            return null;
        }
        return jedisPoolMap.get(ipPort).getResource();
    }

    public void init() {
        redisTimeout = ConfigCache.getInstance().getIntProperty(REDIS_TIMEOUT);
        logger.info("Lion Config redisTimeout:【" + redisTimeout + "】");

        //初始化redis pool
        buildJedisPool();

        //监听lion对应的value的变化，随时改变
        addLionListener();
    }

    private void buildJedisPool() {
        ConfigCache configCache = ConfigCache.getInstance();
        String nodeList = configCache.getProperty(REDIS_NODE_LIST_KEY);
        logger.info("Lion Config nodeList:【" + nodeList + "】");
        String[] nodeListArr = nodeList.split(";");
        int nodeListSize = nodeListArr.length;
        List<JedisPool>[]  jedisPoolListArrTmp = new List[nodeListSize];
        Map<String,JedisPool> jedisPoolMapTmp = new HashMap<String, JedisPool>();
        for (int i = 0; i < nodeListSize; ++i) {
            jedisPoolListArrTmp[i] = Lists.newArrayList();
            String[] machines = nodeListArr[i].split(",");
            for (String machine : machines) {
                String[] hostAndPortArr = machine.split(":");
                JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), hostAndPortArr[0], Integer.parseInt(hostAndPortArr[1]), redisTimeout);
                jedisPoolListArrTmp[i].add(jedisPool);
                jedisPoolMapTmp.put(machine, jedisPool);
            }
        }
        jedisPoolListArr = jedisPoolListArrTmp;
        jedisPoolMap = jedisPoolMapTmp;
    }

    private void addLionListener() {
        try {
            ConfigCache.getInstance().addChange(new ConfigChange() {
                @Override
                public void onChange(String key, String value) {
                    if(StringUtils.isEmpty(key)){
                        return ;
                    }
                    if (key.equalsIgnoreCase(REDIS_NODE_LIST_KEY)) {
                        buildJedisPool();
                    } else if (key.equalsIgnoreCase(REDIS_TIMEOUT)) {
                        redisTimeout = ConfigCache.getInstance().getIntProperty(REDIS_TIMEOUT);
                        buildJedisPool();
                    }
                }
            });
        } catch (LionException ex) {
            logger.error("update jedis pool list error", ex);
        }
    }

}
