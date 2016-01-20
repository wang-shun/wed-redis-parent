/**
 * File Created at 15/10/8
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
import java.util.Date;

/**
 * @author bo.lv
 */
public class WeddingRedisSlowLogDTO implements Serializable {
    private static final long serialVersionUID = -6866324210303325687L;

    /**
     * 慢查询产生时间
     */
    private Date date;

    /**
     * 慢查询执行时间(单位：微秒)
     */
    private long executionTime;

    /**
     * 查询语句
     */
    private String query;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
