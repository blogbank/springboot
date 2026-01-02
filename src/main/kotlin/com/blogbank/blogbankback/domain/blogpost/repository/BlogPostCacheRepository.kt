package com.blogbank.blogbankback.domain.blogpost.repository

import com.blogbank.blogbankback.domain.blogpost.entity.BlogPostEntity
import com.blogbank.blogbankback.util.casting.CastingUtils
import org.springframework.cache.CacheManager
import org.springframework.stereotype.Repository

/**
 * BlogPost 데이터를 캐시하는 Repository
 */
@Repository
class BlogPostCacheRepository(
    private val cacheManager: CacheManager
) {

    companion object {
        private const val CACHE_NAME = "blogPostRounds"
    }

    // 라운드별 블로그 포스트 엔티티 리스트를 캐시에 저장함
    fun save(roundNumber: Int, entities: List<BlogPostEntity>) {
        cacheManager.getCache(CACHE_NAME)?.put(roundNumber, entities)
    }

    // 라운드별 블로그 포스트 엔티티 리스트를 캐시에서 조회함
    fun get(roundNumber: Int): List<BlogPostEntity>? {
        val rawValue = cacheManager.getCache(CACHE_NAME)?.get(roundNumber)?.get()
        return CastingUtils.safeListCast<BlogPostEntity>(rawValue)
    }
}