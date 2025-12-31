package com.blogbank.blogbankback.domain.blogpost.business

import com.blogbank.blogbankback.domain.blogpost.dto.BlogPostDto
import com.blogbank.blogbankback.domain.blogpost.dto.BlogPostListResponseDto
import com.blogbank.blogbankback.domain.blogpost.entity.BlogPostEntity
import org.springframework.stereotype.Component

@Component
class BlogPostMapper {

    // BlogPostEntity를 BlogPostDto로 변환함
    private fun toDto(entity: BlogPostEntity): BlogPostDto {
        return BlogPostDto(
            roundNumber = entity.roundNumber,
            sequenceNumber = entity.sequenceNumber,
            authorName = entity.authorName,
            title = entity.title,
            link = entity.link,
            memo = entity.memo
        )
    }

    // BlogPostEntity 리스트를 BlogPostDto 리스트로 변환함
    private fun toDtoList(entities: List<BlogPostEntity>): List<BlogPostDto> {
        return entities.map { entity -> toDto(entity) }
    }

    // BlogPostEntity 리스트를 DTO로 변환한 후 응답 DTO를 생성함
    fun toListResponse(posts: List<BlogPostEntity>): BlogPostListResponseDto {
        val dtos = toDtoList(posts)
        return BlogPostListResponseDto(
            posts = dtos,
            totalCount = dtos.size
        )
    }
}