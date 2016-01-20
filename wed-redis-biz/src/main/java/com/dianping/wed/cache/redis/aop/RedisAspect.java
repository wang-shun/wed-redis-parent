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
package com.dianping.wed.cache.redis.aop;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import org.aspectj.lang.ProceedingJoinPoint;


/**
 * @author bo.lv
 */
public class RedisAspect {

    private static final String CAT_NAME = "RedisCall";

    //声明环绕通知
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        Object result = null;
        String op = pjp.getSignature().getName() + ":(RedisKeys)";
        //Cat.logMetricForCount(CAT_NAME.concat(op));
        Transaction transaction = Cat.getProducer().newTransaction(CAT_NAME, op);
        try {
            result = pjp.proceed();
            transaction.setStatus(Transaction.SUCCESS);
        } catch (Exception e) {
            transaction.setStatus(e);
            Cat.logError(e);
            throw e;
        } finally {
            transaction.complete();
        }
        return result;
    }
}
