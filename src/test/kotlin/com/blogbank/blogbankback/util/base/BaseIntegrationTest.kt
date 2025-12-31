package com.blogbank.blogbankback.util.base

import com.blogbank.blogbankback.util.cache.CacheCleaner
import com.blogbank.blogbankback.util.client.HttpRequestClient
import io.kotest.core.spec.style.BehaviorSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.transaction.annotation.Transactional

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
abstract class BaseIntegrationTest : BehaviorSpec() {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Autowired
    private lateinit var cacheCleaner: CacheCleaner

    // webTestClient가 Spring에 의해 주입된 이후에 사용하기 위해 lazy 초기화
    // TODO: 더 깔끔한 방법은 없을까?
    protected val httpClient: HttpRequestClient by lazy {
        HttpRequestClient(webTestClient)
    }

    init {
        beforeEach {
            // 매 테스트 실행 전에 모든 캐시를 초기화함
            cacheCleaner.clearAllCaches()
        }
    }
}