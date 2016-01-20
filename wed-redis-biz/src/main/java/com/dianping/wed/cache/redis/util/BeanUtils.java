package com.dianping.wed.cache.redis.util;

import com.alibaba.fastjson.JSON;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Beanlist处理工具类
 * 
 * @author pumaboyd
 * 
 */
public class BeanUtils {

	/**
	 * list对象Copy，主要满足 DTO多Entity的List对象copy
	 * @param source
	 * @param target
	 * @return
	 */
	public static <T, E> List<E> copyPropertiesArrayList(List<T> source, Class<E> target) {

		List<E> es = new ArrayList<E>();

		try {

			if (!CollectionUtils.isEmpty(source)) {

				for (T t : source) {
					E e = target.newInstance();
					org.springframework.beans.BeanUtils.copyProperties(t, e);
					es.add(e);
				}
			}

		} catch (Exception e) {

		}

		return es;

	}

    public static <T, E> E copyProperties(T source, Class<E> target) {
        if (source == null) {
            return null;
        }

        try {
            return JSON.parseObject(JSON.toJSONString(source), target);
        } catch (Exception e) {
        }
        return null;
    }

}
