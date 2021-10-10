package com.yu.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

/**
 * @Author:Yuzhiqiang
 * @Description:
 * @Date: Create in 15:18 2021/9/28
 * @Modified By:
 */
public class RedisCacheManager implements CacheManager {
    //参数是认证或者授权的名称
    @Override
    public <K, V> Cache<K, V> getCache(String cacheName) throws CacheException {
        System.out.println(cacheName);
        return new RedisCache<K, V>(cacheName);
    }
}
