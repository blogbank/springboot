package com.blogbank.blogbankback.util.fixture

import com.blogbank.blogbankback.domain.blogpost.entity.BlogPostEntity
import com.navercorp.fixturemonkey.FixtureMonkey
import org.springframework.stereotype.Component

@Component
class BlogPostFixture(
    private val fixtureMonkey: FixtureMonkey
) {

    // 기본 BlogPostEntity 생성
    fun createUnSaved(): BlogPostEntity {
        return fixtureMonkey.giveMeBuilder(BlogPostEntity::class.java)
            .set("id", 0L)
            .set("isDeleted", false)
            .sample()
    }

    // 기본 BlogPostEntity 생성
    fun createSaved(): BlogPostEntity {
        return fixtureMonkey.giveMeBuilder(BlogPostEntity::class.java)
            .set("isDeleted", false)
            .sample()
    }

    // 라운드 번호가 지정된 BlogPostEntity 생성
    fun createSavedWithRound(roundNumber: Int): BlogPostEntity {
        return fixtureMonkey.giveMeBuilder(BlogPostEntity::class.java)
            .set("roundNumber", roundNumber)
            .set("isDeleted", false)
            .sample()
    }

    fun createSavedList(count: Int? = null): List<BlogPostEntity> {
        val actualCount = count ?: (0..10).random()
        return (1..actualCount).map { createSaved() }
    }

    // 라운드 번호가 지정된 BlogPostEntity 리스트 생성
    fun createSavedListWithRound(roundNumber: Int, count: Int? = null): List<BlogPostEntity> {
        val actualCount = count ?: (0..10).random()
        return (1..actualCount).map { createSavedWithRound(roundNumber) }
    }


    // 지정된 roundNumber로 BlogPostEntity 생성
    fun createUnSavedWithRound(roundNumber: Int): BlogPostEntity {
        return fixtureMonkey.giveMeBuilder(BlogPostEntity::class.java)
            .set("id", 0L)
            .set("roundNumber", roundNumber)
            .set("isDeleted", false)
            .sample()
    }

    // 지정된 개수만큼 BlogPostEntity 리스트 생성
    fun createUnSavedList(count: Int): List<BlogPostEntity> {
        return (1..count).map { createUnSaved() }
    }

    // 랜덤한 개수(0~10)만큼 BlogPostEntity 리스트 생성
    fun createUnSavedRandomList(): List<BlogPostEntity> {
        return createUnSavedList((0..10).random())
    }

    // 지정된 roundNumber로 랜덤한 개수(0~10)만큼 BlogPostEntity 리스트 생성
    fun createUnSavedRandomListWithRound(roundNumber: Int): List<BlogPostEntity> {
        val count = (0..10).random()
        return (1..count).map { createUnSavedWithRound(roundNumber) }
    }

    // 지정된 개수만큼 유니크한 BlogPostEntity 리스트 생성 (validation 자동 적용)
    fun createUnSavedUniqueList(count: Int): List<BlogPostEntity> {
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
    fun createUnSavedUniqueRandomListAtLeastOne(): List<BlogPostEntity> {
        return createUnSavedUniqueList((1..10).random())
    }
}