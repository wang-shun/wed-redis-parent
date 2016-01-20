/**
 * File Created at 15/7/8
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
package com.dianping.wed.cache.redis.util;

import org.apache.commons.lang.math.RandomUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author bo.lv
 */
public class TestDataUtil {

    static Map<String, String> opAndKey = new LinkedHashMap<String, String>();

    static {
        opAndKey.put("set", "key:string");
        opAndKey.put("incr", "key:int");
        opAndKey.put("get", "key:string");

        opAndKey.put("lIndex", "key:list");
        opAndKey.put("lPush", "key:list");
        opAndKey.put("lRange", "key:list");

        opAndKey.put("sAdd", "key:set");
        opAndKey.put("sIsMember", "key:set");
        opAndKey.put("sRem", "key:set");

        opAndKey.put("zAdd", "key:zset");
        opAndKey.put("zRangeWithScores", "key:zset");

        opAndKey.put("hGet", "key:hash");
        opAndKey.put("hSet", "key:hash");
        opAndKey.put("hExists", "key:hash");
    }

    public static void main(String[] args) {
        //第一列 操作，第二列及以后为参数

        //&method=incr&parameterTypes=com.dianping.wed.cache.redis.dto.WeddingRedisKeyDTO&parameters={"category":"justtest","params":["1","2"]}
        List<String> opList = new ArrayList<String>(opAndKey.keySet());
        for (int i = 0; i < 1000; i++) {
            int index = RandomUtils.nextInt(opList.size());
            String op = opList.get(index);
            String key = opAndKey.get(op);
            StringBuilder result = new StringBuilder();
            int params = buildTestUrl(result, op, key);

            String fileName = "/Users/Bob/Desktop/data/data-" + params + ".csv";
            try {
                //使用这个构造函数时，如果存在kuka.txt文件，
                //则直接往kuka.txt中追加字符串
                FileWriter writer = new FileWriter(fileName, true);
                writer.write(result.toString());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("done");
    }

    private static int buildTestUrl(StringBuilder result, String op, String redisKey) {
        int resultCount = 0;
        StringBuilder data = new StringBuilder("");
        int paramCount = 2;
        data.append(op).append("\t");
        //data.append("&parameterTypes=com.dianping.wed.cache.redis.dto.WeddingRedisKeyDTO");
        data.append("{\"category\":\"" + redisKey + "\",\"params\":[\"" + RandomUtils.nextInt(10) + "\",\"" + RandomUtils.nextInt(10) + "\"]}").append("\t");
        if (op.equals("set")) {
            data.append("java.lang.String").append("\t");
            data.append(toMD5(String.valueOf(new Date().getTime())));
            paramCount += 2;
        } else if (op.equals("get")) {
            //nothing
            paramCount += 0;
        } else if (op.equals("incr")) {
            //nothing
            paramCount += 0;
        } else if (op.equals("lIndex")) {
            data.append("long").append("\t");
            data.append(RandomUtils.nextInt(100)).append("\t");
            paramCount += 2;
        } else if (op.equals("lPush")) {
            data.append("[Ljava.lang.String;").append("\t");
            data.append("[\"" + RandomUtils.nextInt(100) + "\",\"" + RandomUtils.nextInt(100) + "\",\"" + RandomUtils.nextInt(100) + "\"]").append("\t");
            paramCount += 2;
        } else if (op.equals("lRange")) {
            data.append("long").append("\t");
            data.append(RandomUtils.nextInt(10)).append("\t");
            data.append("long").append("\t");
            data.append(RandomUtils.nextInt(100)).append("\t");
            paramCount += 4;
        } else if (op.equals("sAdd")) {
            data.append("[Ljava.lang.String;").append("\t");
            data.append("[\"" + RandomUtils.nextInt(100) + "\",\"" + RandomUtils.nextInt(100) + "\",\"" + RandomUtils.nextInt(100) + "\"]").append("\t");
            paramCount += 2;
        } else if (op.equals("sIsMember")) {
            data.append("java.lang.String").append("\t");
            data.append(RandomUtils.nextInt(100)).append("\t");
            paramCount += 2;
        } else if (op.equals("sRem")) {
            data.append("[Ljava.lang.String;").append("\t");
            data.append("&parameters=[\"" + RandomUtils.nextInt(100) + "\",\"" + RandomUtils.nextInt(100) + "\",\"" + RandomUtils.nextInt(100) + "\"]").append("\t");
            paramCount += 2;
        } else if (op.equals("zAdd")) {
            data.append("double").append("\t");
            data.append("&parameters=").append(RandomUtils.nextDouble()).append("\t");
            data.append("java.lang.String").append("\t");
            data.append(RandomUtils.nextInt(100)).append("\t");
            paramCount = 4;
        } else if (op.equals("zRangeWithScores")) {
            data.append("long").append("\t");
            data.append(RandomUtils.nextInt(10)).append("\t");
            data.append("long").append("\t");
            data.append(RandomUtils.nextInt(100)).append("\t");
            paramCount += 4;
        } else if (op.equals("hGet")) {
            data.append("java.lang.String").append("\t");
            data.append(RandomUtils.nextInt(100)).append("\t");
            paramCount += 2;
        } else if (op.equals("hSet")) {
            data.append("java.lang.String").append("\t");
            data.append(RandomUtils.nextInt(100)).append("\t");
            data.append("java.lang.String").append("\t");
            data.append(RandomUtils.nextInt(100)).append("\t");
            paramCount += 4;
        } else if (op.equals("hExists")) {
            data.append("java.lang.String").append("\t");
            data.append(RandomUtils.nextInt(100)).append("\t");
            paramCount += 2;
        }
        result.append(data).append("\n");
        resultCount = paramCount;
        return resultCount;
    }


    /**
     * MD5加密类
     *
     * @param str 要加密的字符串
     * @return 加密后的字符串
     */
    public static String toMD5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] byteDigest = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < byteDigest.length; offset++) {
                i = byteDigest[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
            // 16位的加密
            //return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }

}
