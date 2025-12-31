package com.blogbank.blogbankback.util.base

import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.clearAllMocks
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
abstract class BaseUnitTest : BehaviorSpec() {

    init {
        afterEach { (testCase, testResult) ->
            clearAllMocks() // 테스트 완료 후 모든 Mock 초기화
        }
    }
}