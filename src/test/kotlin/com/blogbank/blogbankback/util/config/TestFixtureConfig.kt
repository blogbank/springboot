package com.blogbank.blogbankback.util.config

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class TestFixtureConfig {

    @Bean
    fun fixtureMonkey(): FixtureMonkey {
        return FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .plugin(JakartaValidationPlugin())
            .build()
    }
}