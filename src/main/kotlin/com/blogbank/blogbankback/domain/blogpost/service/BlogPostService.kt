package com.blogbank.blogbankback.domain.blogpost.service

import com.blogbank.blogbankback.domain.blogpost.dto.BlogPostListResponseDto
import com.blogbank.blogbankback.domain.blogpost.manager.BlogPostCollectManager
import com.blogbank.blogbankback.domain.blogpost.manager.BlogPostManager
import com.blogbank.blogbankback.domain.blogpost.business.BlogPostMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class BlogPostService(
    private val blogPostCollectManager: BlogPostCollectManager,
    private val blogPostManager: BlogPostManager,
    private val blogPostMapper: BlogPostMapper
) {

    fun collectAndSaveBlogPosts(roundNumber: Int): BlogPostListResponseDto {
        val blogPosts = blogPostCollectManager.collectBlogPosts(roundNumber)

        val savedPosts = blogPostManager.saveAll(blogPosts)

        return blogPostMapper.toListResponse(savedPosts)
    }

    fun getAllBlogPosts(): BlogPostListResponseDto {
        val posts = blogPostManager.getAll()

        return blogPostMapper.toListResponse(posts)
    }
}