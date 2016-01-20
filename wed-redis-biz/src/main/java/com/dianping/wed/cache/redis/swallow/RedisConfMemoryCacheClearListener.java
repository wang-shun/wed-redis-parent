/**
 * File Created at 15/8/17
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
package com.dianping.wed.cache.redis.swallow;

import com.dianping.swallow.common.message.Message;
import com.dianping.swallow.consumer.BackoutMessageException;
import com.dianping.swallow.consumer.MessageListener;
import com.dianping.wed.cache.redis.api.WeddingRedisKeyConfigurationService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import javax.annotation.Resource;

/**
 * @author bo.lv
 */
public class RedisConfMemoryCacheClearListener implements MessageListener {

    private Logger logger = Logger.getLogger(RedisConfMemoryCacheClearListener.class);

    @Resource
    private WeddingRedisKeyConfigurationService wcsWeddingRedisKeyCfgService;

    @Override
    public void onMessage(Message message) throws BackoutMessageException {
        String content = message.getContent();
        if (StringUtils.isEmpty(content)) {
            logger.error("message content null");
            return;
        }

        //JSONObject contentObj = new JSONObject()
        JSONObject contentObj = new JSONObject(content);
        Object keyObj = contentObj.get("keys");
        if (keyObj == null) {
            logger.error("message content : key null");
            return;
        }

        String key = keyObj.toString();
        wcsWeddingRedisKeyCfgService.clearMemoryCache(key.split(","));
    }

}
