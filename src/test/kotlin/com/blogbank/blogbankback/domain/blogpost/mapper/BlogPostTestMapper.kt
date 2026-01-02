package com.blogbank.blogbankback.domain.blogpost.mapper

import com.blogbank.blogbankback.domain.blogpost.dto.BlogPostDto
import com.blogbank.blogbankback.domain.blogpost.dto.BlogPostListResponseDto
import com.blogbank.blogbankback.domain.blogpost.entity.BlogPostEntity
import org.springframework.stereotype.Component

@Component
class BlogPostTestMapper {

    // BlogPostEntity를 BlogPostDto로 변환
    fun toDto(entity: BlogPostEntity): BlogPostDto {
        return BlogPostDto(
            roundNumber = entity.roundNumber,
            sequenceNumber = entity.sequenceNumber,
            authorName = entity.authorName,
            title = entity.title,
            link = entity.link,
            memo = entity.memo
        )
    }

    // BlogPostEntity 리스트를 BlogPostListResponseDto로 변환
    fun toListResponse(entities: List<BlogPostEntity>): BlogPostListResponseDto {
        return BlogPostListResponseDto(
            posts = entities.map { toDto(it) },
            totalCount = entities.size
        )
    }
}