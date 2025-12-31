package com.blogbank.blogbankback.util.cache

import org.springframework.cache.Cache
import org.springframework.cache.CacheManager
import org.springframework.stereotype.Component

@Component
class CacheCleaner(
    private val cacheManager: CacheManager
) {

    // 모든 캐시를 초기화함
    fun clearAllCaches() {
        cacheManager.cacheNames
            .mapNotNull(cacheManager::getCache)
            .forEach(Cache::clear)
    }

    // 특정 캐시만 초기화함
    fun clearCache(cacheName: String) {
        cacheManager.getCache(cacheName)?.clear()
    }
}