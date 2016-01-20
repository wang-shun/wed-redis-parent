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
package com.dianping.wed.cache.redis.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author bo.lv
 */
public class WeddingRedisKeyConfigurationDTO implements Serializable{

    private static final long serialVersionUID = 2925284013326892610L;
    /**
     * 自增id
     */
    private int id;

    /**
     * 缓存key的一部分(唯一)
     */
    private String category;

    /**
     * 缓存key类型：string，list，hashmap，set，sortedset
     */
    private String dataType;

    /**
     * 缓存有效时间(单位秒)，0表示永久
     */
    private int duration;

    /**
     * 缓存项键值生成模板
     */
    private String template;

    /**
     * 模板描述
     */
    private String templateDesc;

    /**
     * 版本号,用于批量缓存失效
     */
    private int version;

    /**
     * 新增时间
     */
    private Date addTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getTemplateDesc() {
        return templateDesc;
    }

    public void setTemplateDesc(String templateDesc) {
        this.templateDesc = templateDesc;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
