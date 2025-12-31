package com.blogbank.blogbankback.domain.blogpost.manager

import com.blogbank.blogbankback.domain.blogpost.dto.BlogPostDto
import com.blogbank.blogbankback.domain.blogpost.repository.BlogPostRepository
import com.blogbank.blogbankback.domain.blogpost.business.BlogPostManagerMapper
import org.springframework.stereotype.Component

@Component
class BlogPostManager(
    private val blogPostRepository: BlogPostRepository,
    private val blogPostManagerMapper: BlogPostManagerMapper
) {

    fun saveAll(blogPosts: List<BlogPostDto>): List<BlogPostDto> {
        blogPostRepository.deleteAll()

        val entities = blogPostManagerMapper.toEntityList(blogPosts)
        blogPostRepository.saveAll(entities)

        return blogPosts
    }

    fun getAll(): List<BlogPostDto> {
        val entities = blogPostRepository.findAllByOrderBySequenceNumberAsc()

        return blogPostManagerMapper.toDtoList(entities)
    }
}