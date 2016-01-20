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
package com.dianping.wed.cache.redis;

import com.dianping.lion.client.ConfigCache;
import com.dianping.wed.cache.redis.api.WeddingRedisMonitorService;
import com.dianping.wed.cache.redis.dto.WeddingRedisSlowLogDTO;
import com.dianping.wed.cache.redis.util.JedisFactory;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.LinkedHashMap;
import java.util.List;


/**
 * @author bo.lv
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:config/spring/common/appcontext-*.xml",
        "classpath*:config/spring/local/appcontext-*.xml"})
public class WeddingRedisMonitorServiceImplTest {
    @Autowired
    private WeddingRedisMonitorService weddingRedisMonitorService;

    @Test
    public void testInfo() {
        String nodeListString = ConfigCache.getInstance().getProperty(JedisFactory.REDIS_NODE_LIST_KEY);
        String[] nodeListArr = nodeListString.split(";");
        int nodeListSize = nodeListArr.length;
        for (int i = 0; i < nodeListSize; ++i) {
            String[] machines = nodeListArr[i].split(",");
            for (String machine : machines) {
                LinkedHashMap<String, String> infoMap = weddingRedisMonitorService.info(machine);
                System.out.println(infoMap);
            }
        }
    }

    @Test
    public void testFindSlowQuery() {
        String nodeListString = ConfigCache.getInstance().getProperty(JedisFactory.REDIS_NODE_LIST_KEY);
        String[] nodeListArr = nodeListString.split(";");
        int nodeListSize = nodeListArr.length;
        for (int i = 0; i < nodeListSize; ++i) {
            String[] machines = nodeListArr[i].split(",");
            for (String machine : machines) {
                List<WeddingRedisSlowLogDTO> slowLogList = weddingRedisMonitorService.findSlowQuery(machine, 100);
                for (WeddingRedisSlowLogDTO slowLogDTO : slowLogList) {
                    System.out.println(DateFormatUtils.format(slowLogDTO.getDate(), "yyyy-MM-dd HH:mm:ss") + " " +
                            slowLogDTO.getExecutionTime() / 1000 + "ms " + slowLogDTO.getQuery());
                }
            }
        }
    }
}
