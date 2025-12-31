package com.blogbank.blogbankback.domain.blogpost.dto

data class BlogPostDto(
    val roundNumber: Int,
    val sequenceNumber: Int,
    val authorName: String,
    val title: String?,
    val link: String?,
    val memo: String?
)