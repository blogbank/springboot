package com.blogbank.blogbankback.util.base

import com.blogbank.blogbankback.util.config.TestFixtureConfig
import com.blogbank.blogbankback.util.fixture.BlogPostFixture
import io.kotest.core.spec.style.BehaviorSpec
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@ActiveProfiles("test")
@Import(TestFixtureConfig::class, BlogPostFixture::class)
abstract class BaseRepositoryTest : BehaviorSpec() {
}