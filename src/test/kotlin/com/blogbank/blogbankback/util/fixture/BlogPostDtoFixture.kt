package com.blogbank.blogbankback.util.fixture

import com.blogbank.blogbankback.domain.blogpost.dto.BlogPostDto
import com.navercorp.fixturemonkey.FixtureMonkey
import org.springframework.stereotype.Component

@Component
class BlogPostDtoFixture(
    private val fixtureMonkey: FixtureMonkey
) {

    // 기본 BlogPostDto 생성
    fun create(): BlogPostDto {
        return fixtureMonkey.giveMeBuilder(BlogPostDto::class.java)
            .sample()
    }

    // 지정된 개수만큼 BlogPostDto 리스트 생성
    fun createList(count: Int): List<BlogPostDto> {
        return fixtureMonkey.giveMeBuilder(BlogPostDto::class.java)
            .sampleList(count)
    }

    // 랜덤한 개수(0~10)만큼 BlogPostDto 리스트 생성
    fun createRandomList(): List<BlogPostDto> {
        return createList((0..10).random())
    }

    // 랜덤한 개수(1~10)만큼 BlogPostDto 리스트 생성 (최소 1개 보장)
    fun createRandomListAtLeastOne(): List<BlogPostDto> {
        return createList((1..10).random())
    }
}