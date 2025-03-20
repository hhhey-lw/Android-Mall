package com.wei.diploma_project.service;

import org.springframework.data.redis.core.ListOperations;

import java.util.List;
import java.util.Set;


/**
 * @author wusw
 * @date 2020/4/16 13:11
 */
public interface RedisService {


    /**
     * 添加 key:string 缓存
     *
     * @param key    key
     * @param value    value
     * @param time time
     * @return
     */
    boolean cacheValue(String key, String value, long time);


    /**
     * 添加 key:string 缓存
     *
     * @param key   key
     * @param value value
     * @return
     */
    boolean cacheValue(String key, String value);


    /**
     * 根据 key:string 判断缓存是否存在
     *
     * @param key key
     * @return boolean
     */
    boolean containsValueKey(String key);


    /**
     * 判断缓存 key:set集合 是否存在
     *
     * @param key key
     * @return
     */
    boolean containsSetKey(String key);


    /**
     * 判断缓存 key:list集合 是否存在
     *
     * @param key key
     * @return boolean
     */
    boolean containsListKey(String key);


    /**
     * 查询缓存 key 是否存在
     * @param key key
     * @return true/false
     */
    boolean containsKey(String key);


    /**
     * 根据 key 获取缓存value
     *
     * @param key key
     * @return value
     */
    String getValue(String key);


    /**
     * 根据 key 移除 value 缓存
     *
     * @param key key
     * @return true/false
     */
    boolean removeValue(String key);


    /**
     * 根据 key 移除 set 缓存
     *
     * @param key key
     * @return true/false
     */
    boolean removeSet(String key);


    /**
     * 根据 key 移除 list 缓存
     *
     * @param key key
     * @return true/false
     */
    boolean removeList(String key);


    /**
     * 缓存set操作
     *
     * @param key    key
     * @param value    value
     * @param time time
     * @return boolean
     */
    boolean cacheSet(String key, String value, long time);


    /**
     * 添加 set 缓存
     *
     * @param key   key
     * @param value value
     * @return true/false
     */
    boolean cacheSet(String key, String value);


    /**
     * 添加 缓存 set
     *
     * @param k    key
     * @param v    value
     * @param time 时间
     * @return
     */
    boolean cacheSet(String k, Set<String> v, long time);


    /**
     * 缓存 set
     * @param k key
     * @param v value
     * @return
     */
    boolean cacheSet(String k, Set<String> v);


    /**
     * 获取缓存set数据
     * @param k key
     * @return set集合
     */
    Set<String> getSet(String k);


    /**
     * list 缓存
     * @param k key
     * @param v value
     * @param time 时间
     * @return true/false
     */
    boolean cacheList(String k, String v, long time);


    /**
     * 缓存 list
     * @param k key
     * @param v value
     * @return true/false
     */
    boolean cacheList(String k, String v);


    /**
     * 缓存 list 集合
     * @param k key
     * @param v value
     * @param time 时间
     * @return
     */
    boolean cacheList(String k, List<String> v, long time);


    /**
     *  缓存 list
     * @param k key
     * @param v value
     * @return true/false
     */
    boolean cacheList(String k, List<String> v);


    /**
     * 根据 key 获取 list 缓存
     * @param k key
     * @param start 开始
     * @param end 结束
     * @return 获取缓存区间内 所有value
     */
    List<String> getList(String k, long start, long end);


    /**
     * 根据 key 获取总条数 用于分页
     * @param key key
     * @return 条数
     */
    long getListSize(String key);


    /**
     * 获取总条数 用于分页
     * @param listOps =redisTemplate.opsForList();
     * @param k key
     * @return size
     */
    long getListSize(ListOperations<String, String> listOps, String k);


    /**
     * 根据 key 移除 list 缓存
     * @param k key
     * @return
     */
    boolean removeOneOfList(String k);
}

