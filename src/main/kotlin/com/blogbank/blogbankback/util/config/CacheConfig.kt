package com.blogbank.blogbankback.util.config

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration

/**
 * Caffeine 캐시 설정
 */
@Configuration
@EnableCaching
class CacheConfig {

    @Bean
    fun cacheManager(): CacheManager {
        val cacheManager = CaffeineCacheManager()
        cacheManager.setCaffeine(
            Caffeine.newBuilder()
                .maximumSize(1000)                        // 최대 1000개 항목
                .expireAfterWrite(Duration.ofMinutes(10)) // 10분 TTL
                .recordStats()                            // 통계 기록
        )
        return cacheManager
    }
}