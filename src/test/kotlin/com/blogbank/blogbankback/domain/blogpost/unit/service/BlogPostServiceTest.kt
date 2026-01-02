package com.blogbank.blogbankback.domain.blogpost.unit.service

import com.blogbank.blogbankback.domain.blogpost.manager.BlogPostManager
import com.blogbank.blogbankback.domain.blogpost.business.BlogPostMapper
import com.blogbank.blogbankback.domain.blogpost.service.BlogPostService
import com.blogbank.blogbankback.util.base.BaseUnitTest
import com.blogbank.blogbankback.util.fixture.BlogPostFixture
import com.blogbank.blogbankback.domain.blogpost.mapper.BlogPostTestMapper
import com.blogbank.blogbankback.util.config.TestFixtureConfig
import io.kotest.matchers.shouldBe
import io.kotest.matchers.collections.shouldHaveSize
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
@Import(TestFixtureConfig::class, BlogPostFixture::class, BlogPostTestMapper::class)
class BlogPostServiceTest(
    private val blogPostFixture: BlogPostFixture,
    private val blogPostTestMapper: BlogPostTestMapper
) : BaseUnitTest() {

    init {
        given("BlogPostService") {
            val blogPostManager = mockk<BlogPostManager>()
            val blogPostMapper = mockk<BlogPostMapper>()
            val blogPostService = BlogPostService(blogPostManager, blogPostMapper)

            `when`("getBlogPostsForRound 호출 시") {
                then("랜덤한 개수의 데이터에 대해 정상 처리한다 (0개부터 10개까지)") {
                    // Given - 0~10개 랜덤 생성
                    val roundNumber = (1..10).random()
                    val mockEntities = blogPostFixture.createRandomListWithRound(roundNumber)
                    val expectedResponse = blogPostTestMapper.toListResponse(mockEntities)

                    every { blogPostManager.getBlogPostsWithCache(roundNumber) } returns mockEntities
                    every { blogPostMapper.toListResponse(mockEntities) } returns expectedResponse

                    // When
                    val result = blogPostService.getBlogPostsForRound(roundNumber)

                    // Then
                    result.posts shouldHaveSize mockEntities.size
                    result.totalCount shouldBe mockEntities.size

                    // 데이터가 있는 경우만 세부 검증
                    if (mockEntities.isNotEmpty()) {
                        result.posts.forEachIndexed { index, post ->
                            post.roundNumber shouldBe mockEntities[index].roundNumber
                            post.sequenceNumber shouldBe mockEntities[index].sequenceNumber
                            post.authorName shouldBe mockEntities[index].authorName
                            post.title shouldBe mockEntities[index].title
                            post.link shouldBe mockEntities[index].link
                            post.memo shouldBe mockEntities[index].memo
                        }
                    }

                    verify { blogPostManager.getBlogPostsWithCache(roundNumber) }
                    verify { blogPostMapper.toListResponse(mockEntities) }
                }
            }

            `when`("getAllBlogPosts 호출 시") {
                then("랜덤한 개수의 데이터에 대해 정상 처리한다 (0개부터 10개까지)") {
                    // Given - 0~10개 랜덤 생성
                    val mockEntities = blogPostFixture.createRandomList()
                    val expectedResponse = blogPostTestMapper.toListResponse(mockEntities)

                    every { blogPostManager.getAll() } returns mockEntities
                    every { blogPostMapper.toListResponse(mockEntities) } returns expectedResponse

                    // When
                    val result = blogPostService.getAllBlogPosts()

                    // Then
                    result.posts shouldHaveSize mockEntities.size
                    result.totalCount shouldBe mockEntities.size

                    // 데이터가 있는 경우만 세부 검증
                    if (mockEntities.isNotEmpty()) {
                        result.posts.forEachIndexed { index, post ->
                            post.roundNumber shouldBe mockEntities[index].roundNumber
                            post.sequenceNumber shouldBe mockEntities[index].sequenceNumber
                            post.authorName shouldBe mockEntities[index].authorName
                            post.title shouldBe mockEntities[index].title
                            post.link shouldBe mockEntities[index].link
                            post.memo shouldBe mockEntities[index].memo
                        }
                    }

                    verify { blogPostManager.getAll() }
                    verify { blogPostMapper.toListResponse(mockEntities) }
                }
            }
        }
    }
}