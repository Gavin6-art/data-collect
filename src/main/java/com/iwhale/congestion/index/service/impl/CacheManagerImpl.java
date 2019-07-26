package com.iwhale.congestion.index.service.impl;

import com.iwhale.congestion.index.service.ICacheManager;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The type Cache manager.
 */
@Service
public class CacheManagerImpl implements ICacheManager {

    /**
     * The constant caches.
     */
    private static Map<String, EntityCache> caches = new ConcurrentHashMap<String, EntityCache>();

    /**
     * Put cache.
     *
     * @param key   the key
     * @param cache the cache
     */
    public void putCache(String key, EntityCache cache) {
        caches.put(key, cache);
    }

    /**
     * Gets cache data by key.
     *
     * @param key the key
     * @return the cache data by key
     */
    public Object getCacheDataByKey(String key) {
        if (this.isContains(key)) {
            return caches.get(key).getDatas();
        }
        return null;
    }

    /**
     * Is contains boolean.
     *
     * @param key the key
     * @return the boolean
     */
    public boolean isContains(String key) {
        return caches.containsKey(key);
    }

}
