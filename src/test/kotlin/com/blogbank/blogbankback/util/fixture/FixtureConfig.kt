package com.blogbank.blogbankback.util.fixture

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin

object FixtureConfig {

    val fixtureMonkey: FixtureMonkey = FixtureMonkey.builder()
        .plugin(JakartaValidationPlugin())
        .build()
}