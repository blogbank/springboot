package com.blogbank.blogbankback.util.fixture

import com.blogbank.blogbankback.domain.blogpost.entity.BlogPostEntity
import com.navercorp.fixturemonkey.FixtureMonkey
import org.springframework.stereotype.Component

@Component
class BlogPostFixture(
    private val fixtureMonkey: FixtureMonkey
) {

    // 기본 BlogPostEntity 생성
    fun create(): BlogPostEntity {
        return fixtureMonkey.giveMeBuilder(BlogPostEntity::class.java)
            .set("id", 0L)
            .set("isDeleted", false)
            .sample()
    }

    // 지정된 roundNumber로 BlogPostEntity 생성
    fun createWithRound(roundNumber: Int): BlogPostEntity {
        return fixtureMonkey.giveMeBuilder(BlogPostEntity::class.java)
            .set("id", 0L)
            .set("roundNumber", roundNumber)
            .set("isDeleted", false)
            .sample()
    }

    // 지정된 개수만큼 BlogPostEntity 리스트 생성
    fun createList(count: Int): List<BlogPostEntity> {
        return fixtureMonkey.giveMeBuilder(BlogPostEntity::class.java)
            .set("id", 0L)
            .set("isDeleted", false)
            .sampleList(count)
    }

    // 랜덤한 개수(0~10)만큼 BlogPostEntity 리스트 생성
    fun createRandomList(): List<BlogPostEntity> {
        return createList((0..10).random())
    }

    // 지정된 roundNumber로 랜덤한 개수(0~10)만큼 BlogPostEntity 리스트 생성
    fun createRandomListWithRound(roundNumber: Int): List<BlogPostEntity> {
        val count = (0..10).random()
        return (1..count).map { createWithRound(roundNumber) }
    }

    // 지정된 개수만큼 유니크한 BlogPostEntity 리스트 생성 (validation 자동 적용)
    fun createUniqueList(count: Int): List<BlogPostEntity> {
        return (1..count).map { index ->
            fixtureMonkey.giveMeBuilder(BlogPostEntity::class.java)
                .set("id", 0L)
                .set("isDeleted", false)
                .set("roundNumber", index)  // 유니크한 roundNumber 설정
                .set("sequenceNumber", index)  // 유니크한 sequenceNumber 설정
                .sample()  // validation 어노테이션 자동 적용
        }
    }

    // 랜덤한 개수(1~10)만큼 유니크한 BlogPostEntity 리스트 생성
    fun createUniqueRandomListAtLeastOne(): List<BlogPostEntity> {
        return createUniqueList((1..10).random())
    }
}