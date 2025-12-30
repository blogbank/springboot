package com.blogbank.blogbankback.domain.blogpost.business

import com.blogbank.blogbankback.domain.blogpost.dto.BlogPostDto
import com.blogbank.blogbankback.domain.blogpost.entity.BlogPostEntity
import org.springframework.stereotype.Component

@Component
class BlogPostManagerMapper {

    fun toEntity(dto: BlogPostDto): BlogPostEntity {
        return BlogPostEntity(
            sequenceNumber = dto.sequenceNumber,
            authorName = dto.authorName,
            title = dto.title,
            link = dto.link,
            memo = dto.memo
        )
    }

    fun toEntityList(dtos: List<BlogPostDto>): List<BlogPostEntity> {
        return dtos.map { dto -> toEntity(dto) }
    }

    fun toDto(entity: BlogPostEntity): BlogPostDto {
        return BlogPostDto(
            sequenceNumber = entity.sequenceNumber,
            authorName = entity.authorName,
            title = entity.title,
            link = entity.link,
            memo = entity.memo
        )
    }

    fun toDtoList(entities: List<BlogPostEntity>): List<BlogPostDto> {
        return entities.map { entity -> toDto(entity) }
    }
}