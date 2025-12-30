package com.blogbank.blogbankback.domain.blogpost.business

import com.blogbank.blogbankback.domain.blogpost.dto.BlogPostDto
import com.blogbank.blogbankback.domain.blogpost.dto.BlogPostListResponseDto
import org.springframework.stereotype.Component

@Component
class BlogPostMapper {

    fun toListResponse(posts: List<BlogPostDto>): BlogPostListResponseDto {
        return BlogPostListResponseDto(
            posts = posts,
            totalCount = posts.size
        )
    }
}