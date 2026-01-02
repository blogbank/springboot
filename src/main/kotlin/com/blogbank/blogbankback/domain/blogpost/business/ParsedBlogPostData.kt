package com.blogbank.blogbankback.domain.blogpost.business

// 마크다운 테이블에서 파싱한 원시 데이터
data class ParsedBlogPostData(
    val sequenceNumber: Int,
    val authorName: String,
    val title: String?,
    val link: String?,
    val memo: String?
)