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
package com.dianping.wed.cache.redis.api;

import com.dianping.wed.cache.redis.dto.WeddingRedisSlowLogDTO;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author bo.lv
 */
public interface WeddingRedisMonitorService {

    /**
     * 根据IP和端口号获取redis机器的信息
     *
     * @param ipPort IP和端口号用英文冒号分隔 例：192.168.7.41:6383
     * @return
     */
    public LinkedHashMap<String, String> info(String ipPort);


    /**
     * 查询redis慢查询
     *
     * @param ipPort    IP和端口号用英文冒号分隔 例：192.168.7.41:6383
     * @param maxResult 返回结果集合个数
     * @return
     */
    public List<WeddingRedisSlowLogDTO> findSlowQuery(String ipPort, int maxResult);
}
