package com.blogbank.blogbankback.domain.blogpost.repository

import com.blogbank.blogbankback.domain.blogpost.entity.BlogPostEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BlogPostRepository : JpaRepository<BlogPostEntity, Long> {
    // 삭제되지 않은 블로그 포스트를 순서대로 조회함
    fun findAllByIsDeletedFalseOrderBySequenceNumberAsc(): List<BlogPostEntity>

    // 라운드별로 삭제되지 않은 블로그 포스트를 순서대로 조회함
    fun findAllByRoundNumberAndIsDeletedFalseOrderBySequenceNumberAsc(roundNumber: Int): List<BlogPostEntity>
}