package com.blogbank.blogbankback.domain.blogpost.manager

import com.blogbank.blogbankback.domain.blogpost.dto.BlogPostDto
import com.blogbank.blogbankback.domain.blogpost.repository.BlogPostRepository
import com.blogbank.blogbankback.domain.blogpost.business.BlogPostManagerMapper
import com.blogbank.blogbankback.domain.blogpost.entity.BlogPostEntity
import com.blogbank.blogbankback.domain.github.client.GitHubClient
import com.blogbank.blogbankback.domain.blogpost.business.BlogPostExtractor
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Component
import java.util.*

@Component
class BlogPostManager(
    private val blogPostRepository: BlogPostRepository,
    private val blogPostManagerMapper: BlogPostManagerMapper,
    private val gitHubClient: GitHubClient,
    private val blogPostExtractor: BlogPostExtractor
) {

    companion object {
        private const val FILE_NAME_PATTERN = "round%02d.md"
    }

    // 블로그 포스트를 수집함
    fun collectBlogPosts(roundNumber: Int): List<BlogPostDto> = runBlocking {
        collectBlogPostsAsync(roundNumber)
    }

    // 비동기로 블로그 포스트를 수집함
    private suspend fun collectBlogPostsAsync(roundNumber: Int): List<BlogPostDto> {
        val fileName = String.format(FILE_NAME_PATTERN, roundNumber)
        val gitHubFile = gitHubClient.getFileContent(fileName)

        if (gitHubFile == null) return emptyList()

        val cleanedBase64 = gitHubFile.content.replace("\n", "").replace("\r", "").replace(" ", "")
        val decodedContent = String(Base64.getDecoder().decode(cleanedBase64))
        return blogPostExtractor.parseMarkdownTable(decodedContent)
    }

    // 블로그 포스트를 저장함
    fun saveAll(blogPosts: List<BlogPostDto>): List<BlogPostEntity> {
        blogPostRepository.deleteAll()

        val entities = blogPostManagerMapper.toEntityList(blogPosts)
        return blogPostRepository.saveAll(entities)
    }

    // 모든 블로그 포스트를 조회함
    fun getAll(): List<BlogPostEntity> {
        return blogPostRepository.findAllByOrderBySequenceNumberAsc()
    }
}