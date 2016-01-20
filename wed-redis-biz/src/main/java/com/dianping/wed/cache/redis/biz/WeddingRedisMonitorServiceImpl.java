/**
 * File Created at 15/9/29
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

import com.dianping.wed.cache.redis.api.WeddingRedisMonitorService;
import com.dianping.wed.cache.redis.dto.WeddingRedisSlowLogDTO;
import com.dianping.wed.cache.redis.util.JedisFactory;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.util.Slowlog;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author bo.lv
 */
public class WeddingRedisMonitorServiceImpl implements WeddingRedisMonitorService {
    private static Logger logger = Logger.getLogger(WeddingRedisMonitorServiceImpl.class);
    private JedisFactory jedisFactory;

    @Override
    public LinkedHashMap<String, String> info(String ipPort) {
        Jedis jedis = jedisFactory.getByIPPort(ipPort);
        if (jedis == null) {
            return null;
        }
        try {
            Object redisMacheInfo = jedis.eval("return redis.call(\"info\")");
            LinkedHashMap<String, String> infoMap = new LinkedHashMap<String, String>();
            if (redisMacheInfo != null) {
                String[] infoArray = redisMacheInfo.toString().split("\n");
                for (String inf : infoArray) {
                    if (StringUtils.isEmpty(inf)) {
                        continue;
                    }
                    String[] kv = inf.split(":");
                    if (kv.length != 2) {
                        continue;
                    }
                    //系统可能换行符不一致
                    infoMap.put(kv[0].replace("\r", "").replace("\n", ""), kv[1].replace("\r", "").replace("\n", ""));
                }
            }
            return infoMap;
        } finally {
            jedis.close();
        }
    }

    @Override
    public List<WeddingRedisSlowLogDTO> findSlowQuery(String ipPort, int maxResult) {
        Jedis jedis = jedisFactory.getByIPPort(ipPort);
        if (jedis == null) {
            return null;
        }
        List<WeddingRedisSlowLogDTO> slowLogList = new ArrayList<WeddingRedisSlowLogDTO>();
        try {
            List<Slowlog> slowlogs = jedis.slowlogGet(maxResult);
            if (CollectionUtils.isNotEmpty(slowlogs)) {
                for (Slowlog slowlog : slowlogs) {
                    WeddingRedisSlowLogDTO slowLogDTO = new WeddingRedisSlowLogDTO();
                    slowLogDTO.setDate(new Date(slowlog.getTimeStamp() * 1000));
                    slowLogDTO.setExecutionTime(slowlog.getExecutionTime());
                    StringBuilder query = new StringBuilder();
                    if (CollectionUtils.isNotEmpty(slowlog.getArgs())) {
                        for (String arg : slowlog.getArgs()) {
                            query.append(arg).append(" ");
                        }
                    }
                    slowLogDTO.setQuery(query.toString());
                    slowLogList.add(slowLogDTO);
                }
            }
        } catch (Exception e) {
            logger.error("call slowlogGet error : ", e);
        } finally {
            jedis.close();
        }
        return slowLogList;
    }

    public void setJedisFactory(JedisFactory jedisFactory) {
        this.jedisFactory = jedisFactory;
    }
}
