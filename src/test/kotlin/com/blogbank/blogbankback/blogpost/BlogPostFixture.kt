package com.blogbank.blogbankback.blogpost

import com.blogbank.blogbankback.domain.blogpost.entity.BlogPostEntity
import com.navercorp.fixturemonkey.FixtureMonkey

class BlogPostFixture(
    private val fixtureMonkey: FixtureMonkey
) {

     // 랜덤 BlogPostEntity 생성
    fun random(): BlogPostEntity {
        return fixtureMonkey.giveMeOne(BlogPostEntity::class.java)
    }
}