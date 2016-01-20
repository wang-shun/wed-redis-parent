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
package com.dianping.wed.cache.redis.dao;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.core.type.PageModel;
import com.dianping.wed.cache.redis.entity.WeddingRedisKeyConfiguration;

/**
 * @author bo.lv
 */
public interface WeddingRedisKeyConfigurationDao extends GenericDao {

    /**
     * 获取redis key 配置信息
     *
     * @param category
     * @return
     */
    @DAOAction(action = DAOActionType.LOAD)
    public WeddingRedisKeyConfiguration loadRedisKeyCfgByCategory(@DAOParam("category") String category);

    /**
     * 添加redis配置信息
     *
     * @param weddingRedisKeyConfiguration
     * @return
     */
    @DAOAction(action = DAOActionType.INSERT)
    public int addRedisKeyConfiguration(@DAOParam("weddingRedisKeyConfiguration") WeddingRedisKeyConfiguration weddingRedisKeyConfiguration);

    /**
     * 修改redis配置信息
     *
     * @param weddingRedisKeyConfiguration
     * @return
     */
    @DAOAction(action = DAOActionType.UPDATE)
    public int updateRedisKeyConfiguration(@DAOParam("weddingRedisKeyConfiguration") WeddingRedisKeyConfiguration weddingRedisKeyConfiguration);

    /**
     * 删除redis配置信息
     *
     * @param category
     * @return
     */
    @DAOAction(action = DAOActionType.DELETE)
    public int deleteRedisKeyConfiguration(@DAOParam("category") String category);

    /**
     * 分页显示redis配置信息
     *
     * @param page
     * @param max
     * @param weddingRedisKeyConfiguration
     * @return
     */
    @DAOAction(action = DAOActionType.PAGE)
    public PageModel paginateRedisKeyConfiguration(@DAOParam("page") int page,
                                                   @DAOParam("max") int max,
                                                   @DAOParam("weddingRedisKeyConfiguration") WeddingRedisKeyConfiguration weddingRedisKeyConfiguration);
}
