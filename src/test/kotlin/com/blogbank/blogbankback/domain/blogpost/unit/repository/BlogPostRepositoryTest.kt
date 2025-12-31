package com.blogbank.blogbankback.domain.blogpost.unit.repository

import com.blogbank.blogbankback.domain.blogpost.repository.BlogPostRepository
import com.blogbank.blogbankback.util.base.BaseRepositoryTest
import com.blogbank.blogbankback.util.fixture.BlogPostFixture
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

class BlogPostRepositoryTest(
    private val blogPostRepository: BlogPostRepository,
    private val blogPostFixture: BlogPostFixture,
) : BaseRepositoryTest() {

    init {
        given("블로그 포스트 여러 개가 저장되어 있을 때") {
            `when`("순서번호 오름차순으로 조회하면") {
                then("정렬되어 반환된다") {
                    val blogPosts = blogPostFixture.createRandomListAtLeastOne()
                    val savedPosts = blogPostRepository.saveAll(blogPosts)

                    val result = blogPostRepository.findAllByOrderBySequenceNumberAsc()

                    result shouldHaveSize savedPosts.size
                    val sortedPosts = savedPosts.sortedBy { it.sequenceNumber }
                    result.forEachIndexed { index, post ->
                        post.sequenceNumber shouldBe sortedPosts[index].sequenceNumber
                        post.authorName shouldBe sortedPosts[index].authorName
                        post.title shouldBe sortedPosts[index].title
                        post.link shouldBe sortedPosts[index].link
                        post.memo shouldBe sortedPosts[index].memo
                    }
                }
            }
        }

        given("블로그 포스트가 없을 때") {
            `when`("순서번호 오름차순으로 조회하면") {
                then("빈 리스트를 반환한다") {
                    val result = blogPostRepository.findAllByOrderBySequenceNumberAsc()
                    result.shouldBeEmpty()
                }
            }
        }

        given("여러 블로그 포스트가 저장되어 있을 때") {
            `when`("모든 데이터를 삭제하면") {
                then("빈 리스트가 된다") {
                    val posts = blogPostFixture.createRandomList()
                    blogPostRepository.saveAll(posts)

                    blogPostRepository.deleteAll()

                    val result = blogPostRepository.findAllByOrderBySequenceNumberAsc()
                    result.shouldBeEmpty()
                }
            }

            `when`("하나의 포스트만 삭제하면") {
                then("나머지 포스트들은 정상적으로 남아있다") {
                    val posts = blogPostFixture.createRandomListAtLeastOne()
                    val savedPosts = blogPostRepository.saveAll(posts)
                    val postToDelete = savedPosts.random()

                    blogPostRepository.delete(postToDelete)

                    val remainingPosts = blogPostRepository.findAllByOrderBySequenceNumberAsc()
                    remainingPosts shouldHaveSize (savedPosts.size - 1)
                    remainingPosts.none { it.id == postToDelete.id }
                }
            }
        }
    }
}