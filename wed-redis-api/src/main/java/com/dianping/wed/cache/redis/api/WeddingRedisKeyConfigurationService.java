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
package com.dianping.wed.cache.redis.api;

import com.dianping.core.type.PageModel;
import com.dianping.wed.cache.redis.dto.WeddingRedisKeyConfigurationDTO;
import com.dianping.wed.cache.redis.dto.WeddingRedisKeyDTO;

import java.util.List;
import java.util.Map;

/**
 * @author bo.lv
 */
public interface WeddingRedisKeyConfigurationService {

    /**
     * 根据category和模板参数，生成存储于redis的key
     *
     * @param weddingRedisKeyDTO
     * @return
     */
    public String generateKey(WeddingRedisKeyDTO weddingRedisKeyDTO);

    /**
     *
     * @param redisKeyList
     * @return
     */
    public String[] generateKeys(List<WeddingRedisKeyDTO> redisKeyList);

    /**
     * 获取redis key 配置信息
     *
     * @param category
     * @return
     */
    public WeddingRedisKeyConfigurationDTO loadRedisKeyCfgByCategory(String category);

    /**
     * 同步配置信息到redis。若categories为空，则同步所有category；若不为空，则同步指定category
     *
     * @param categories
     * @return 成功返回SUCCESS，失败返回错误信息
     */
    public String syncCfg(String... categories);

    /**
     * 清除本机内存缓存
     *
     * @param categories
     * @return
     */
    public boolean clearMemoryCache(String... categories);


    /**
     * 查询category对应的内容，并且返回当前的主机名
     *
     * @param categories
     * @return map的value 1、WeddingRedisKeyConfigurationDTO(内存内容)  2、String（主机名）这里key为HostName
     */
    public Map<String, Object> queryMemoryCache(String... categories);

    /**
     * 添加redis配置
     *
     * @param weddingRedisKeyConfigurationDTO
     * @return 返回1表示配置已存在，返回2说明成功添加
     */
    public int addRedisKeyConfiguration(WeddingRedisKeyConfigurationDTO weddingRedisKeyConfigurationDTO);

    /**
     * 修改redis配置
     *
     * @param weddingRedisKeyConfigurationDTO
     * @return
     */
    public int updateRedisKeyConfiguration(WeddingRedisKeyConfigurationDTO weddingRedisKeyConfigurationDTO);

    /**
     * 删除redis配置
     *
     * @param category
     * @return
     */
    public int deleteRedisKeyConfiguration(String category);

    /**
     * 分页显示redis配置
     *
     * @param page
     * @param maxResults
     * @param weddingRedisKeyConfigurationDTO
     * @return
     */
    public PageModel paginateRedisKeyConfiguration(int page, int maxResults, WeddingRedisKeyConfigurationDTO weddingRedisKeyConfigurationDTO);
}
