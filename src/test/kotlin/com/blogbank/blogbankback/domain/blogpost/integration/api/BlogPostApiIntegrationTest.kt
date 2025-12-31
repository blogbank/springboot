package com.blogbank.blogbankback.domain.blogpost.integration.api

import com.blogbank.blogbankback.domain.blogpost.dto.BlogPostListResponseDto
import com.blogbank.blogbankback.domain.blogpost.repository.BlogPostRepository
import com.blogbank.blogbankback.util.base.BaseIntegrationTest
import com.blogbank.blogbankback.util.fixture.BlogPostFixture
import com.blogbank.blogbankback.util.router.BlogPostRouter
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired

class BlogPostApiIntegrationTest : BaseIntegrationTest() {

    @Autowired
    private lateinit var blogPostRepository: BlogPostRepository

    @Autowired
    private lateinit var blogPostFixture: BlogPostFixture

    init {
        given("BlogPost API 통합테스트") {

            `when`("GET /api/blog-posts 호출하면") {
                then("모든 블로그 포스트 목록을 반환한다") {
                    // Given - 랜덤한 개수(0~10)의 테스트 데이터 저장
                    val testPosts = blogPostFixture.createRandomList()
                    val savedPosts = blogPostRepository.saveAll(testPosts)

                    // When & Then
                    httpClient.getOkResponse<BlogPostListResponseDto>(BlogPostRouter.GET_ALL) { body ->
                        body.posts shouldHaveSize savedPosts.size
                        body.totalCount shouldBe savedPosts.size
                    }
                }
            }
        }
    }
}