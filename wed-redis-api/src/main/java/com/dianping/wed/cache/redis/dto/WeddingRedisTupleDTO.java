/**
 * File Created at 15/7/3
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
public class WeddingRedisTupleDTO implements Serializable {
    /**
     * 元素对应的分数
     */
    private Double score;

    /**
     * 元素
     */
    private String element;

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }
}
