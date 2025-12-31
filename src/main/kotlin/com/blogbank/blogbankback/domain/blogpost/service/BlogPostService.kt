package com.blogbank.blogbankback.domain.blogpost.service

import com.blogbank.blogbankback.domain.blogpost.dto.BlogPostListResponseDto
import com.blogbank.blogbankback.domain.blogpost.manager.BlogPostManager
import com.blogbank.blogbankback.domain.blogpost.business.BlogPostMapper
import com.blogbank.blogbankback.domain.blogpost.entity.BlogPostEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class BlogPostService(
    private val blogPostManager: BlogPostManager,
    private val blogPostMapper: BlogPostMapper
) {

    fun getBlogPostsForRound(roundNumber: Int): BlogPostListResponseDto {

        val savedPosts = getAndSaveBlogPosts(roundNumber)
        return blogPostMapper.toListResponse(savedPosts)
    }

    fun getAllBlogPosts(): BlogPostListResponseDto {
        val posts = blogPostManager.getAll()
        return blogPostMapper.toListResponse(posts)
    }

    private fun getAndSaveBlogPosts(roundNumber: Int): List<BlogPostEntity> {

        val blogPosts = blogPostManager.collectBlogPosts(roundNumber)
        return blogPostManager.saveAll(blogPosts)

    }
}