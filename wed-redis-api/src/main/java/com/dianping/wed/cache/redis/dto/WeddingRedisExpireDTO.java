/**
 * File Created at 15/7/13
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
public class WeddingRedisExpireDTO implements Serializable {

    private static final long serialVersionUID = 2967434921537973693L;

    private WeddingRedisKeyDTO key;
    private int seconds;

    public WeddingRedisExpireDTO() {
    }

    public WeddingRedisExpireDTO(WeddingRedisKeyDTO key, int seconds) {
        this.key = key;
        this.seconds = seconds;
    }

    public WeddingRedisKeyDTO getKey() {
        return key;
    }

    public void setKey(WeddingRedisKeyDTO key) {
        this.key = key;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}
