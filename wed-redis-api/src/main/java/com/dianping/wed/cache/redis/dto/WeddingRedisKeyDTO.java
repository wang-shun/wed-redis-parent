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
public class WeddingRedisKeyDTO implements Serializable {

    private static final long serialVersionUID = 2925284013326892610L;
    
    private String category;
    private Object[] params;

    private WeddingRedisKeyDTO() {
    }

    public WeddingRedisKeyDTO(String category, Object... params) {
        this.category = category;
        this.params = params;
    }

    public String getCategory() {
        return category;
    }

    public Object[] getParams() {
        return params;
    }
}
