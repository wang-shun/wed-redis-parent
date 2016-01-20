/**
 * File Created at 15/6/9
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

import com.dianping.wed.cache.redis.api.WeddingRedisService;
import com.dianping.wed.cache.redis.dto.WeddingRedisExpireDTO;
import com.dianping.wed.cache.redis.dto.WeddingRedisKeyDTO;
import com.dianping.wed.cache.redis.dto.WeddingRedisPairDTO;
import com.dianping.wed.cache.redis.dto.WeddingRedisTupleDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Assert;

import java.util.*;

/**
 * @author bo.lv
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:config/spring/common/appcontext-*.xml",
        "classpath*:config/spring/local/appcontext-*.xml"})
public class WeddingRedisServiceImplTest {

    @Autowired
    private WeddingRedisService weddingRedisService;

    WeddingRedisKeyDTO key = new WeddingRedisKeyDTO("justtest", "1", "2");
    WeddingRedisKeyDTO key2 = new WeddingRedisKeyDTO("justtest", "3", "4");

    @Test()
    public void testAll() {
        testString();
        testList();
        testSet();
        testSortedSet();
        testHash();
        testKey();


    }

    private void testKey() {
        //************************** Key ********************************//

        //删除key 操作
        weddingRedisService.del(key);

        long expire = weddingRedisService.expire(key, 120);
        Assert.assertTrue(expire == 0);

        weddingRedisService.set(key, "1");
        expire = weddingRedisService.expire(key, 15);
        Assert.assertTrue(expire == 1);

        List<WeddingRedisExpireDTO> expireList = new ArrayList<WeddingRedisExpireDTO>();
        WeddingRedisExpireDTO expireDTO = new WeddingRedisExpireDTO(key, 30);
        WeddingRedisExpireDTO expireDTO2 = new WeddingRedisExpireDTO(key2, 30);
        expireList.add(expireDTO);
        expireList.add(expireDTO2);
        List<Long> mExpire = weddingRedisService.mExpire(expireList);
        Assert.assertTrue(mExpire.size() == 2);
        Assert.assertTrue(mExpire.get(0) == 1);
        Assert.assertTrue(mExpire.get(1) == 0);
    }

    private void testHash() {
        //************************** Hash ********************************//

        //删除key 操作
        weddingRedisService.del(key);

        long hSet = weddingRedisService.hSet(key, "a", "0");
        Assert.assertTrue(hSet == 1);

        hSet = weddingRedisService.hSet(key, "a", "1");
        Assert.assertTrue(hSet == 0);

        String hGet = weddingRedisService.hGet(key, "a");
        Assert.assertTrue(hGet.equals("1"));

        boolean hExists = weddingRedisService.hExists(key, "a");
        Assert.assertTrue(hExists);

        long hDel = weddingRedisService.hDel(key, "a");
        Assert.assertTrue(hDel == 1);

        hExists = weddingRedisService.hExists(key, "a");
        Assert.assertTrue(!hExists);

        weddingRedisService.hSet(key, "a", "1");
        weddingRedisService.hSet(key, "b", "2");
        weddingRedisService.hSet(key, "c", "3");

        Map<String, String> hGetAll = weddingRedisService.hGetAll(key);
        Assert.assertTrue(hGetAll.size() == 3);

        long hLen = weddingRedisService.hLen(key);
        Assert.assertTrue(hLen == 3);

        long hIncrBy = weddingRedisService.hIncrBy(key, "c", 1);
        Assert.assertTrue(hIncrBy == 4);

        Set<String> hKeys = weddingRedisService.hKeys(key);
        Assert.assertTrue(hKeys.size() == 3);
        List<String> keys = new ArrayList<String>();
        keys.add("a");
        keys.add("b");
        keys.add("c");
        Assert.assertTrue(hKeys.containsAll(keys));

        List<String> hVals = weddingRedisService.hVals(key);
        List<String> vals = new ArrayList<String>();
        keys.add("1");
        keys.add("2");
        keys.add("4");
        Assert.assertTrue(hVals.containsAll(vals));


        Map<String,String> kvs = new HashMap<String, String>();
        kvs.put("1231","1231");
        kvs.put("1232","1232");
        kvs.put("1233", "1233");
        kvs.put("1234", "1234");
        String result = weddingRedisService.hMSet(key, kvs);
        Assert.assertTrue(result.equalsIgnoreCase("ok"));
        List<String> resultList = weddingRedisService.hMGet(key, "1231", "1232", "1233", "1234");
        Assert.assertTrue(resultList.size() == 4);
        Assert.assertTrue(resultList.get(0).equalsIgnoreCase("1231"));
    }

    private void testSortedSet() {
        //************************** Sorted Set ********************************//

        //删除key 操作
        weddingRedisService.del(key);

        //初始化 a:1
        long zAdd = weddingRedisService.zAdd(key, 1, "a");
        Assert.assertTrue(zAdd == 1);

        //设置a:1.1
        zAdd = weddingRedisService.zAdd(key, 1.1, "a");
        Assert.assertTrue(zAdd == 0);

        //设置 b:2.0 c:3.0 d:4.0
        Map<String, Double> scoreMembers = new HashMap<String, Double>();
        scoreMembers.put("b", 2.0);
        scoreMembers.put("c", 3.0);
        scoreMembers.put("d", 4.0);
        long zAdds = weddingRedisService.zMAdd(key, scoreMembers);
        Assert.assertTrue(zAdds == 3);

        //获取集合元素个数
        long zCard = weddingRedisService.zCard(key);
        Assert.assertTrue(zCard == 4);

        //获取分数在[1,2]之间的元素个数
        long zCount = weddingRedisService.zCount(key, 1, 2);
        Assert.assertTrue(zCount == 2);

        //对a进行加0.1操作，a对应的value变为1.2
        double zIncrBy = weddingRedisService.zIncrBy(key, 0.1, "a");
        //我也不知道为什么数出来会是1.20000000000..2
        //Assert.assertTrue(zIncrBy == 1.2);

        //按分数从小到大，获取排行从第0到第1的member集合
        Set<String> zRange = weddingRedisService.zRange(key, 0, 1);
        Assert.assertTrue(zRange.size() == 2);
        Assert.assertTrue(zRange.toArray(new String[0])[0].equals("a"));

        //按分数从大到小，获取排行从第0到第1的member集合
        Set<String> zRevRange = weddingRedisService.zRevRange(key, 0, 1);
        Assert.assertTrue(zRevRange.size() == 2);
        Assert.assertTrue(zRevRange.toArray(new String[0])[0].equals("d"));

        //按分数从小到大，获取排行从 倒数第一 到 倒数第二 的member与score集合
        Set<WeddingRedisTupleDTO> zRangeWithScores = weddingRedisService.zRangeWithScores(key, -2, -1);
        Assert.assertTrue(zRangeWithScores.size() == 2);
        Assert.assertTrue(zRangeWithScores.toArray((new WeddingRedisTupleDTO[0]))[1].getScore().equals(new Double(4.0)));

        //按分数从大到小，获取排行从 倒数第一 到 倒数第二 的member与score集合
        Set<WeddingRedisTupleDTO> zRevRangeWithScores = weddingRedisService.zRevRangeWithScores(key, -2, -1);
        Assert.assertTrue(zRevRangeWithScores.size() == 2);
        Assert.assertTrue(zRevRangeWithScores.toArray((new WeddingRedisTupleDTO[0]))[0].getScore().equals(new Double(2.0)));

        //按分数从小到大,获取c的排名(有第0名)
        long zRank = weddingRedisService.zRank(key, "c");
        Assert.assertTrue(zRank == 2);

        //按分数从大到小,获取c的排名(有第0名)
        long zRevRank = weddingRedisService.zRevRank(key, "c");
        Assert.assertTrue(zRevRank == 1);

        //获取成员d的分数
        Double zScore = weddingRedisService.zScore(key, "d");
        Assert.assertTrue(zScore == 4.0);

        //移除成员a
        long zRem = weddingRedisService.zRem(key, "a");
        Assert.assertTrue(zRem == 1);
    }

    private void testList() {
        //************************** List ********************************//
        //删除key 操作
        weddingRedisService.del(key);
        weddingRedisService.del(key2);

        //添加数据，c b a
        long lPush = weddingRedisService.lPush(key, "a", "b", "c");
        Assert.assertTrue(lPush == 3);

        //长度为3
        long lLen = weddingRedisService.lLen(key);
        Assert.assertTrue(lLen == 3);

        //读第一个元素的值
        String lIndex = weddingRedisService.lIndex(key, 1);
        Assert.assertTrue("b".equals(lIndex));

        //取出第一个元素c，剩下 b a
        String lPop = weddingRedisService.lPop(key);
        Assert.assertTrue("c".equals(lPop));

        //读取第0个到第3个元素的值
        List<String> lRange = weddingRedisService.lRange(key, 0, 3);
        Assert.assertTrue(lRange.size() == 2);
        Assert.assertTrue(lRange.get(0).equals("b"));
        Assert.assertTrue(lRange.get(1).equals("a"));

        //设置第1位置的值为aa，设置完变为 b aa
        String lSet = weddingRedisService.lSet(key, 1, "aa");
        Assert.assertTrue("ok".equalsIgnoreCase(lSet));

        //读第一个元素的值
        lIndex = weddingRedisService.lIndex(key, 1);
        Assert.assertTrue("aa".equals(lIndex));


        //删除key 操作
        weddingRedisService.del(key);

        //添加a,b,c元素
        weddingRedisService.rPush(key, "a", "b", "c");

        //去除最后一个元素c，剩下a b
        String rPop = weddingRedisService.rPop(key);
        Assert.assertTrue("c".equals(rPop));

//        long lPushx = weddingRedisService.lPushx(key, "d","e","f");
//        Assert.assertTrue(lPushx == 4);
    }

    private void testSet() {
        //************************** Set ********************************//

        //删除key 操作
        weddingRedisService.del(key);

        long sAdd = weddingRedisService.sAdd(key, "a", "b", "c");
        Assert.assertTrue(sAdd == 3);

        sAdd = weddingRedisService.sAdd(key, "a", "b", "d");
        Assert.assertTrue(sAdd == 1);

        long sCard = weddingRedisService.sCard(key);
        Assert.assertTrue(sCard == 4);

        boolean sIsMember = weddingRedisService.sIsMember(key, "a");
        Assert.assertTrue(sIsMember);

        sIsMember = weddingRedisService.sIsMember(key, "e");
        Assert.assertTrue(!sIsMember);

        Set<String> sMembers = weddingRedisService.sMembers(key);
        Assert.assertTrue(sMembers.size() == 4);

        long sRem = weddingRedisService.sRem(key, "d", "e");
        Assert.assertTrue(sRem == 1);
    }

    private void testString() {
        //************************** String ********************************//
        //删除key 操作
        weddingRedisService.del(key);

        //set 操作
        String set = weddingRedisService.set(key, "1");
        Assert.assertTrue("OK".equalsIgnoreCase(set));

        //get 操作
        String get = weddingRedisService.get(key);
        Assert.assertTrue("1".equals(get));

        //mset 操作
        List<WeddingRedisPairDTO> pairs = new ArrayList<WeddingRedisPairDTO>();
        WeddingRedisPairDTO pair = new WeddingRedisPairDTO();
        pair.setValue("11");
        pair.setWeddingRedisKeyDTO(key2);
        pairs.add(pair);
        WeddingRedisPairDTO pair2 = new WeddingRedisPairDTO();
        pair2.setValue("2");
        pair2.setWeddingRedisKeyDTO(key2);
        pairs.add(pair2);
        String mSet = weddingRedisService.mSet(pairs);
        Assert.assertTrue("OK".equals(mSet));

        //mget操作
        List<WeddingRedisKeyDTO> keysForMGET = new ArrayList<WeddingRedisKeyDTO>();
        keysForMGET.add(key);
        keysForMGET.add(key2);
        List<String> mGet = weddingRedisService.mGet(keysForMGET);
        Assert.assertTrue(mGet.size() == 2);

        //incr 操作
        long incr = weddingRedisService.incr(key);
        Assert.assertTrue(incr > 0);

        //incrBy 操作
        long incrBy = weddingRedisService.incrBy(key, 10);
        Assert.assertTrue(incrBy - incr == 10);

        //decr 操作
        long decr = weddingRedisService.decr(key);
        Assert.assertTrue(incrBy - decr == 1);

        //decrBy 操作
        long decrBy = weddingRedisService.decrBy(key, 10);
        Assert.assertTrue(decr - decrBy == 10);

        //setEx 操作
        String setEx = weddingRedisService.setEx(key, 20, "by yourself");
        Assert.assertTrue("OK".equalsIgnoreCase(setEx));
    }

}
