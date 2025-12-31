package com.blogbank.blogbankback.domain.blogpost.dto

data class BlogPostListResponseDto(
    val posts: List<BlogPostDto>,
    val totalCount: Int
)