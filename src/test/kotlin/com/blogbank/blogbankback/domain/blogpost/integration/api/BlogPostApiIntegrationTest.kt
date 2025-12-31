package com.blogbank.blogbankback.domain.blogpost.integration.api

import com.blogbank.blogbankback.domain.blogpost.dto.BlogPostListResponseDto
import com.blogbank.blogbankback.domain.blogpost.repository.BlogPostRepository
import com.blogbank.blogbankback.domain.github.client.GitHubClient
import com.blogbank.blogbankback.util.base.BaseIntegrationTest
import com.blogbank.blogbankback.util.fixture.BlogPostFixture
import com.blogbank.blogbankback.util.router.BlogPostRouter
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import com.ninjasquad.springmockk.MockkBean
import kotlin.random.Random
import org.springframework.beans.factory.annotation.Autowired

class BlogPostApiIntegrationTest : BaseIntegrationTest() {

    @MockkBean
    private lateinit var gitHubClient: GitHubClient

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

            `when`("POST /api/blog-posts/rounds/{roundNumber} 호출하면") {
                then("해당 라운드의 블로그 포스트 목록을 반환한다 (GitHub 응답이 없으면 빈 목록)") {
                    // Given
                    val roundNumber = Random.nextInt(1, 100)
                    val uri = BlogPostRouter.getRound(roundNumber)

                    coEvery { gitHubClient.getFileContent(any()) } returns null

                    // When & Then
                    httpClient.postOkResponse<BlogPostListResponseDto>(uri) { body ->

                        body.posts shouldHaveSize 0
                        body.totalCount shouldBe 0
                    }
                }
            }
        }
    }
}