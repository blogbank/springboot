package com.blogbank.blogbankback.domain.blogpost.entity

import com.blogbank.blogbankback.domain.util.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "blog_posts")
data class BlogPostEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val sequenceNumber: Int,

    @Column(nullable = false)
    val authorName: String,

    @Column(nullable = true)
    val title: String?,

    @Column(nullable = true, length = 1000)
    val link: String?,

    @Column(nullable = true)
    val memo: String?
) : BaseEntity()