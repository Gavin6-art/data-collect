package com.iwhale.congestion.index.service;

import com.iwhale.congestion.index.service.impl.EntityCache;

import java.util.Map;
import java.util.Set;

/**
 * The interface Cache manager.
 */
public interface ICacheManager {
    /**
     * Put cache.
     *
     * @param key   the key
     * @param cache the cache
     */
    void putCache(String key, EntityCache cache);

    /**
     * Gets cache data by key.
     *
     * @param key the key
     * @return the cache data by key
     */
    Object getCacheDataByKey(String key);

    /**
     * Is contains boolean.
     *
     * @param key the key
     * @return the boolean
     */
    boolean isContains(String key);

}
