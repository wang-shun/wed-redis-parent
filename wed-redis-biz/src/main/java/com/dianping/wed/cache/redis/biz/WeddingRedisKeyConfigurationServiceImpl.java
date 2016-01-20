/**
 * File Created at 15/6/30
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

import com.alibaba.fastjson.JSON;
import com.dianping.cat.Cat;
import com.dianping.core.type.PageModel;
import com.dianping.lion.client.ConfigCache;
import com.dianping.lion.client.ConfigChange;
import com.dianping.wed.cache.redis.api.WeddingRedisKeyConfigurationService;
import com.dianping.wed.cache.redis.dao.WeddingRedisKeyConfigurationDao;
import com.dianping.wed.cache.redis.dto.WeddingRedisKeyConfigurationDTO;
import com.dianping.wed.cache.redis.dto.WeddingRedisKeyDTO;
import com.dianping.wed.cache.redis.entity.WeddingRedisKeyConfiguration;
import com.dianping.wed.cache.redis.enums.RedisActionType;
import com.dianping.wed.cache.redis.util.BeanUtils;
import com.dianping.wed.cache.redis.util.JedisCallback;
import com.dianping.wed.cache.redis.util.JedisHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author bo.lv
 */
public class WeddingRedisKeyConfigurationServiceImpl implements WeddingRedisKeyConfigurationService {

    private static Logger logger = Logger.getLogger(WeddingRedisKeyConfigurationServiceImpl.class);
    //WED_RedisKeyConfiguration表的数据存储与redis中，所用的key（用hashmap数据结构）
    private static final String REDIS__KEY_CONFIGURATION = "rediskeyconfiguration";

    //用来清除内存缓存的lion key
    private static final String REDIS_CONFIG_MEMORY_CACHE_CLEAR_SWITCH = "wed-redis-service.redis.config.memory.cache.clear";

    //清除内存缓存开关
    private boolean clearMemoryCache = false;

    //WED_RedisKeyConfiguration表的内存缓存
    private Map<String, WeddingRedisKeyConfigurationDTO> keyCfgCache = new ConcurrentHashMap<String, WeddingRedisKeyConfigurationDTO>();

    private WeddingRedisKeyConfigurationDao weddingRedisKeyConfigurationDao;


    @Override
    public String generateKey(WeddingRedisKeyDTO redisKey) {
        //获取category对应的配置信息
        WeddingRedisKeyConfigurationDTO cfg = getKeyCfg(redisKey);
        StringBuilder redisFinalKeyBuilder = new StringBuilder(cfg.getCategory());
        if (StringUtils.isNotEmpty(cfg.getTemplate())) {
            redisFinalKeyBuilder.append(cfg.getTemplate());
        }
        if (cfg.getVersion() > 0) {
            redisFinalKeyBuilder.append(":").append(cfg.getVersion());
        }

        Object[] params = redisKey.getParams();
        if (params == null) {
            params = new Object[]{};
        }

        String redisFinalKey = redisFinalKeyBuilder.toString();
        for (int i = 0; i < params.length; ++i) {
            redisFinalKey = redisFinalKey.replace("{" + i + "}", params[i].toString());
        }
        return redisFinalKey;
    }

    @Override
    public String[] generateKeys(List<WeddingRedisKeyDTO> redisKeyList) {
        String[] result = new String[redisKeyList.size()];
        if (CollectionUtils.isEmpty(redisKeyList)) {
            return result;
        }

        for (int i = 0; i < redisKeyList.size(); i++) {
            result[i] = generateKey(redisKeyList.get(i));
        }

        return result;
    }

    private WeddingRedisKeyConfigurationDTO getKeyCfg(WeddingRedisKeyDTO redisKey) {
        final String category = redisKey.getCategory();
        WeddingRedisKeyConfigurationDTO keyConfiguration = null;
        if (!clearMemoryCache) {
            keyConfiguration = keyCfgCache.get(category);
            if (keyConfiguration != null) {
                return keyConfiguration;
            }
        }

        String json = JedisHelper.doJedisOperation(new JedisCallback<String>() {
            @Override
            public String doWithJedis(Jedis jedis) {
                return jedis.hget(REDIS__KEY_CONFIGURATION, category);
            }
        }, REDIS__KEY_CONFIGURATION, RedisActionType.READ);


        if (StringUtils.isEmpty(json)) {
            throw new IllegalArgumentException("table WED_RedisKeyConfiguration does not contain category :" +
                    redisKey.getCategory() + ",please add it to table WED_RedisKeyConfiguration or contact the admin");
        }

        keyConfiguration = JSON.parseObject(json, WeddingRedisKeyConfigurationDTO.class);
        keyCfgCache.put(category, keyConfiguration);
        return keyConfiguration;
    }

    @Override
    public WeddingRedisKeyConfigurationDTO loadRedisKeyCfgByCategory(String category) {
        WeddingRedisKeyConfiguration weddingRedisKeyConfiguration = weddingRedisKeyConfigurationDao.loadRedisKeyCfgByCategory(category);
        return BeanUtils.copyProperties(weddingRedisKeyConfiguration, WeddingRedisKeyConfigurationDTO.class);
    }

    @Override
    public String syncCfg(String... categories) {
        try {
            String resultMsg = "";
            if (ArrayUtils.isEmpty(categories)) {
                resultMsg = "categories cannot be empty or null";
            } else if ("all".equals(categories[0])) {
                //TODO
                resultMsg = "operation unsupported now";
            } else {
                for (final String category : categories) {
                    WeddingRedisKeyConfigurationDTO configurationDTO = this.loadRedisKeyCfgByCategory(category);
                    if(configurationDTO == null){
                        resultMsg += "category:" + category + "不存在，无法同步；";
                        JedisHelper.doJedisOperation(new JedisCallback<Long>() {
                            @Override
                            public Long doWithJedis(Jedis jedis) {
                                return jedis.hdel(REDIS__KEY_CONFIGURATION, category);
                            }
                        }, REDIS__KEY_CONFIGURATION, RedisActionType.WRITE);
                        continue;
                    }
                    final String cfgString = JSON.toJSONString(configurationDTO);
                    JedisHelper.doJedisOperation(new JedisCallback<Long>() {
                        @Override
                        public Long doWithJedis(Jedis jedis) {
                            return jedis.hset(REDIS__KEY_CONFIGURATION, category, cfgString);
                        }
                    }, REDIS__KEY_CONFIGURATION, RedisActionType.WRITE);
                }
            }
            //部分 或 全部 key未同步成功
            if(StringUtils.isNotEmpty(resultMsg)){
                return resultMsg;
            }

        } catch (Exception e) {
            logger.error(e);
            Cat.logError(e);
            return e.getMessage();
        }
        return "OK";
    }

    @Override
    public boolean clearMemoryCache(String... categories) {
        if (ArrayUtils.isEmpty(categories)) {
            return false;
        }
        if ("all".equals(categories[0])) {
            keyCfgCache.clear();
            return true;
        }

        for (String category : categories) {
            keyCfgCache.remove(category);
        }
        return true;
    }

    @Override
    public Map<String, Object> queryMemoryCache(String... categories) {
        Map<String, Object> results = new LinkedHashMap<String, Object>();
        for (String category : categories) {
            results.put(category, keyCfgCache.get(category));
        }
        InetAddress localHost = null;
        try {
            localHost = InetAddress.getLocalHost();
        } catch (Exception e) {
            logger.error("call InetAddress.getLocalHost() error", e);
        }

        if (localHost == null) {
            results.put("HostName", "UNKNOWN");
        } else {
            results.put("HostName", localHost.getHostName());
        }
        return results;
    }

    @Override
    public int addRedisKeyConfiguration(WeddingRedisKeyConfigurationDTO weddingRedisKeyConfigurationDTO) {
        if (weddingRedisKeyConfigurationDTO == null) {
            return 0;
        }
        WeddingRedisKeyConfiguration weddingRedisKeyConfiguration = BeanUtils.copyProperties(weddingRedisKeyConfigurationDTO, WeddingRedisKeyConfiguration.class);
        weddingRedisKeyConfigurationDao.addRedisKeyConfiguration(weddingRedisKeyConfiguration);
        return 1;
    }

    @Override
    public int updateRedisKeyConfiguration(WeddingRedisKeyConfigurationDTO weddingRedisKeyConfigurationDTO) {
        if (weddingRedisKeyConfigurationDTO == null) {
            return 0;
        }
        WeddingRedisKeyConfiguration weddingRedisKeyConfiguration = BeanUtils.copyProperties(weddingRedisKeyConfigurationDTO, WeddingRedisKeyConfiguration.class);
        weddingRedisKeyConfigurationDao.updateRedisKeyConfiguration(weddingRedisKeyConfiguration);
        return 1;
    }

    @Override
    public int deleteRedisKeyConfiguration(String category){
        if (category == null){
            return 0;
        }
        weddingRedisKeyConfigurationDao.deleteRedisKeyConfiguration(category);
        return 1;
    }

    @Override
    public PageModel paginateRedisKeyConfiguration(int page, int maxResults, WeddingRedisKeyConfigurationDTO weddingRedisKeyConfigurationDTO) {
        WeddingRedisKeyConfiguration weddingRedisKeyCfg = new WeddingRedisKeyConfiguration();
        if (weddingRedisKeyConfigurationDTO != null) {
            org.springframework.beans.BeanUtils.copyProperties(weddingRedisKeyConfigurationDTO, weddingRedisKeyCfg);
        }
        PageModel pageModel = weddingRedisKeyConfigurationDao.paginateRedisKeyConfiguration(page, maxResults, weddingRedisKeyCfg);

        if (pageModel == null || CollectionUtils.isEmpty(pageModel.getRecords())) {
            pageModel = new PageModel();
            pageModel.setRecords(new ArrayList<WeddingRedisKeyConfigurationDTO>());
            return pageModel;
        }
        List<WeddingRedisKeyConfiguration> weddingRedisKeyConfigurations = (List<WeddingRedisKeyConfiguration>) pageModel.getRecords();
        List<WeddingRedisKeyConfigurationDTO> weddingRedisKeyConfigurationDTOs = new ArrayList<WeddingRedisKeyConfigurationDTO>();

        for (WeddingRedisKeyConfiguration weddingRedisKeyConfiguration : weddingRedisKeyConfigurations) {
            WeddingRedisKeyConfigurationDTO weddingRedisKeyCfgDTO = new WeddingRedisKeyConfigurationDTO();
            org.springframework.beans.BeanUtils.copyProperties(weddingRedisKeyConfiguration, weddingRedisKeyCfgDTO);
            weddingRedisKeyConfigurationDTOs.add(weddingRedisKeyCfgDTO);
        }
        pageModel.setRecords(weddingRedisKeyConfigurationDTOs);
        return pageModel;
    }

    public void init() {
        //spring容器启动的时候，会调用该方法
        //监听lion的value变化，来控制是否清理内存缓存
        ConfigCache configCache = ConfigCache.getInstance();
        String clear = configCache.getProperty(REDIS_CONFIG_MEMORY_CACHE_CLEAR_SWITCH);
        clearMemoryCache = "y".equals(clear);

        ConfigCache.getInstance().addChange(new ConfigChange() {
            @Override
            public void onChange(String key, String value) {
                if (StringUtils.isEmpty(key)) {
                    return;
                }
                if (key.equalsIgnoreCase(REDIS_CONFIG_MEMORY_CACHE_CLEAR_SWITCH)) {
                    clearMemoryCache = "y".equals(value);
                    if (clearMemoryCache) {
                        keyCfgCache.clear();
                    }
                }
            }
        });
    }

    public void setWeddingRedisKeyConfigurationDao(WeddingRedisKeyConfigurationDao weddingRedisKeyConfigurationDao) {
        this.weddingRedisKeyConfigurationDao = weddingRedisKeyConfigurationDao;
    }

}
