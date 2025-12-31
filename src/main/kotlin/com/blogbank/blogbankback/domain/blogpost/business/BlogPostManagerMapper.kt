package com.blogbank.blogbankback.domain.blogpost.business

import com.blogbank.blogbankback.domain.blogpost.dto.BlogPostDto
import com.blogbank.blogbankback.domain.blogpost.entity.BlogPostEntity
import org.springframework.stereotype.Component

@Component
class BlogPostManagerMapper {

    fun toEntity(dto: BlogPostDto): BlogPostEntity {
        return BlogPostEntity(
            roundNumber = dto.roundNumber,
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

    // ParsedBlogPostData를 BlogPostDto로 변환함
    fun toDto(parsedData: ParsedBlogPostData, roundNumber: Int): BlogPostDto {
        return BlogPostDto(
            roundNumber = roundNumber,
            sequenceNumber = parsedData.sequenceNumber,
            authorName = parsedData.authorName,
            title = parsedData.title,
            link = parsedData.link,
            memo = parsedData.memo
        )
    }

    // ParsedBlogPostData 리스트를 BlogPostDto 리스트로 변환함
    fun toDtoListFromParsedData(parsedDataList: List<ParsedBlogPostData>, roundNumber: Int): List<BlogPostDto> {
        return parsedDataList.map { parsedData -> toDto(parsedData, roundNumber) }
    }
}