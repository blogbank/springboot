package com.blogbank.blogbankback.domain.blogpost.integration.repository

import com.blogbank.blogbankback.domain.blogpost.entity.BlogPostEntity
import com.blogbank.blogbankback.domain.blogpost.repository.BlogPostCacheRepository
import com.blogbank.blogbankback.util.base.BaseIntegrationTest
import com.blogbank.blogbankback.util.fixture.BlogPostFixture
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.springframework.cache.CacheManager

class BlogPostCacheRepositoryIntegrationTest(
    private val cacheManager: CacheManager,
    private val blogPostCacheRepository: BlogPostCacheRepository,
    private val blogPostFixture: BlogPostFixture
) : BaseIntegrationTest() {

    init {

        given("BlogPostCacheRepository 캐시 저장 기능") {
            `when`("라운드별 블로그 포스트 엔티티 리스트를 캐시에 저장할 때") {
                then("실제 캐시에 정상적으로 저장됨") {
                    // given
                    val roundNumber = (1..100).random()
                    val count = (0..10).random()
                    val blogPostEntities = blogPostFixture.createSavedListWithRound(roundNumber, count)

                    // when
                    blogPostCacheRepository.save(roundNumber, blogPostEntities)

                    // then - 실제 캐시에서 확인
                    val entities = getEntitiesFromCache(roundNumber)
                    entities shouldBe blogPostEntities
                    entities?.shouldHaveSize(count)

                    // 빈 리스트인 경우 추가 검증
                    if (count == 0) {
                        entities?.shouldBeEmpty()
                    } else {
                        // 비어있지 않은 경우 roundNumber 값 검증
                        entities?.forEach { entity ->
                            entity.roundNumber shouldBe roundNumber
                        }
                    }
                }
            }

            `when`("동일한 라운드에 데이터를 덮어쓸 때") {
                then("기존 데이터가 새로운 데이터로 교체됨") {
                    // given
                    val roundNumber = (1..100).random()
                    val originalCount = (0..10).random()
                    val updatedCount = (0..10).random()
                    val originalEntities = blogPostFixture.createSavedListWithRound(roundNumber, originalCount)
                    val updatedEntities = blogPostFixture.createSavedListWithRound(roundNumber, updatedCount)

                    // when
                    blogPostCacheRepository.save(roundNumber, originalEntities)
                    blogPostCacheRepository.save(roundNumber, updatedEntities)

                    // then
                    val cachedData = blogPostCacheRepository.get(roundNumber)
                    cachedData shouldBe updatedEntities
                    cachedData?.shouldHaveSize(updatedCount)
                }
            }

        }

        given("BlogPostCacheRepository 캐시 조회 기능") {
            `when`("캐시에 저장된 데이터를 조회할 때") {
                then("저장된 데이터가 정확히 반환됨") {
                    // given
                    val roundNumber = (1..100).random()
                    val count = (0..10).random()
                    val blogPostEntities = blogPostFixture.createSavedListWithRound(roundNumber, count)
                    blogPostCacheRepository.save(roundNumber, blogPostEntities)

                    // when
                    val result = blogPostCacheRepository.get(roundNumber)

                    // then
                    result shouldBe blogPostEntities
                    result?.shouldHaveSize(count)
                }
            }

            `when`("캐시에 저장되지 않은 라운드를 조회할 때") {
                then("null이 반환됨") {
                    // given
                    val nonExistentRoundNumber = (10000..99999).random()

                    // when
                    val result = blogPostCacheRepository.get(nonExistentRoundNumber)

                    // then
                    result shouldBe null
                }
            }

        }

        given("BlogPostCacheRepository 데이터 무결성") {
            `when`("캐시된 엔티티의 필드값을 확인할 때") {
                then("모든 필드가 정확히 유지됨") {
                    // given
                    val roundNumber = (1..100).random()
                    val originalEntity = blogPostFixture.createSavedWithRound(roundNumber)

                    // when
                    blogPostCacheRepository.save(roundNumber, listOf(originalEntity))
                    val cachedEntity = blogPostCacheRepository.get(roundNumber)?.first()

                    // then
                    cachedEntity?.id shouldBe originalEntity.id
                    cachedEntity?.roundNumber shouldBe originalEntity.roundNumber
                    cachedEntity?.sequenceNumber shouldBe originalEntity.sequenceNumber
                    cachedEntity?.authorName shouldBe originalEntity.authorName
                    cachedEntity?.title shouldBe originalEntity.title
                    cachedEntity?.link shouldBe originalEntity.link
                    cachedEntity?.memo shouldBe originalEntity.memo
                    cachedEntity?.isDeleted shouldBe originalEntity.isDeleted
                }
            }
        }
    }

    // 캐시에서 직접 엔티티 리스트를 가져오는 헬퍼 메소드
    private fun getEntitiesFromCache(roundNumber: Int): List<BlogPostEntity>? {
        val cachedData = cacheManager.getCache("blogPostRounds")?.get(roundNumber)?.get()
        return (cachedData as? List<*>)?.filterIsInstance<BlogPostEntity>()
    }
}