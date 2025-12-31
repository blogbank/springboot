package com.blogbank.blogbankback.domain.blogpost.repository

import com.blogbank.blogbankback.domain.blogpost.entity.BlogPostEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BlogPostRepository : JpaRepository<BlogPostEntity, Long> {
    fun findAllByOrderBySequenceNumberAsc(): List<BlogPostEntity>
}