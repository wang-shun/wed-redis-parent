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
package com.dianping.wed.cache.redis;

import com.dianping.core.type.PageModel;
import com.dianping.wed.cache.redis.api.WeddingRedisKeyConfigurationService;
import com.dianping.wed.cache.redis.dto.WeddingRedisKeyConfigurationDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author bo.lv
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:config/spring/common/appcontext-*.xml",
        "classpath*:config/spring/local/appcontext-*.xml"})
public class WeddingRedisKeyConfigurationServiceImplTest {

    @Autowired
    private WeddingRedisKeyConfigurationService weddingRedisKeyConfigurationService;

    @Test
    public void testLoadRedisKeyCfgByCategory() {
        WeddingRedisKeyConfigurationDTO weddingRedisKeyConfigurationDTO = weddingRedisKeyConfigurationService.loadRedisKeyCfgByCategory("wedcoupo");
        System.out.println(weddingRedisKeyConfigurationDTO);
    }

    @Test
    public void testSyncCfg() {

        String[] keys ={"rediskeyconfiguration", "wedlifecycle:tmp", "wedpredictheight:attendNumber",
                "wedcoup", "wedcoupo", "wedcoupon","wedBabydaily","wedBabydaily:sms","13wedusercallbac","13wedusercallback"
        ,"wedbroadcastdpid","hourlyGroup","singlePhone","wedsybu","wedsybc","wedsybc:b"
        ,"cpccharge","cpcalarm","wedpsbl","wedpsblcnt","wedpscnt:h","wedpscnt:d","wedpsrk","wedhomedesign:tagclick","weddsbd"
        ,"OfflineEventIdMapping","wedhomedesign:viewcount","sourceGroup","mzcfqjCoupon","test","test1"};
        String synResult = weddingRedisKeyConfigurationService.syncCfg(keys);
        Assert.assertTrue("OK".equalsIgnoreCase(synResult));
    }

    private WeddingRedisKeyConfigurationDTO generateWeddingRedisKeyConfigurationDTO(){
        WeddingRedisKeyConfigurationDTO weddingRedisKeyConfigurationDTO = new WeddingRedisKeyConfigurationDTO();
        weddingRedisKeyConfigurationDTO.setCategory("test1");
        weddingRedisKeyConfigurationDTO.setDuration(0);
        weddingRedisKeyConfigurationDTO.setDataType("list");
        weddingRedisKeyConfigurationDTO.setTemplate("n:id:{0}:date:{1}:userid:{2}");
        weddingRedisKeyConfigurationDTO.setTemplateDesc("哈哈哈哈哈");
        weddingRedisKeyConfigurationDTO.setVersion(0);
        return weddingRedisKeyConfigurationDTO;
    }

    @Test
    public void testAddRedisKeyConfiguration(){
        WeddingRedisKeyConfigurationDTO weddingRedisKeyConfigurationDTO = generateWeddingRedisKeyConfigurationDTO();
        int result = weddingRedisKeyConfigurationService.addRedisKeyConfiguration(weddingRedisKeyConfigurationDTO);
        Assert.assertTrue(result > 0);
    }

    @Test
    public void testUpdateRedisKeyConfiguration(){
        WeddingRedisKeyConfigurationDTO weddingRedisKeyConfigurationDTO = weddingRedisKeyConfigurationService.loadRedisKeyCfgByCategory("test");
        weddingRedisKeyConfigurationDTO.setDuration(122);
        weddingRedisKeyConfigurationDTO.setDataType("hash");
        weddingRedisKeyConfigurationDTO.setTemplateDesc("gaiyigai");
        weddingRedisKeyConfigurationDTO.setTemplate("n:id:{0}:date:{1");
        weddingRedisKeyConfigurationDTO.setVersion(1);
        int result = weddingRedisKeyConfigurationService.updateRedisKeyConfiguration(weddingRedisKeyConfigurationDTO);
        Assert.assertTrue(result > 0);
    }

    @Test
    public void testDeleteRedisKeyConfiguration(){
        int result = weddingRedisKeyConfigurationService.deleteRedisKeyConfiguration("test1");
        weddingRedisKeyConfigurationService.syncCfg("test1");
        Assert.assertTrue(result > 0);
    }

    @Test
    public void testPaginateRedisKeyConfiguration(){
        WeddingRedisKeyConfigurationDTO weddingRedisKeyConfigurationDTO = new WeddingRedisKeyConfigurationDTO();
        weddingRedisKeyConfigurationDTO.setCategory("sourceGroup");
        PageModel pageModel = weddingRedisKeyConfigurationService.paginateRedisKeyConfiguration(1,10,weddingRedisKeyConfigurationDTO);
        Assert.assertNotNull(pageModel);
    }

    @Test
    public void testClearMemoryCache() {
        Assert.assertTrue(weddingRedisKeyConfigurationService.clearMemoryCache("test"));
        Assert.assertTrue(weddingRedisKeyConfigurationService.clearMemoryCache("all"));
    }

    @Test
    public void testQueryMemoryCache() {
        Assert.assertNotNull(weddingRedisKeyConfigurationService.queryMemoryCache("test"));
    }

}
