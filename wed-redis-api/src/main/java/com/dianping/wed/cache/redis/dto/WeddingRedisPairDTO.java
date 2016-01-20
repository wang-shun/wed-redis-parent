/**
 * File Created at 15/6/23
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
package com.dianping.wed.cache.redis.dto;

import java.io.Serializable;

/**
 * @author bo.lv
 */
public class WeddingRedisPairDTO implements Serializable {

    private static final long serialVersionUID = 8020277670186536152L;

    private WeddingRedisKeyDTO weddingRedisKeyDTO;
    private String value;

    public WeddingRedisKeyDTO getWeddingRedisKeyDTO() {
        return weddingRedisKeyDTO;
    }

    public void setWeddingRedisKeyDTO(WeddingRedisKeyDTO weddingRedisKeyDTO) {
        this.weddingRedisKeyDTO = weddingRedisKeyDTO;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
