package com.blogbank.blogbankback.domain.blogpost.entity

import com.blogbank.blogbankback.domain.util.entity.BaseEntity
import jakarta.persistence.*
import jakarta.validation.constraints.*

@Entity
@Table(name = "blog_posts")
data class BlogPostEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    @NotNull
    @Min(1)
    val roundNumber: Int,

    @Column(nullable = false)
    @NotNull
    @Min(1)
    val sequenceNumber: Int,

    @Column(nullable = false)
    @NotBlank
    @Size(min = 2, max = 50)
    val authorName: String,

    @Column(nullable = true)
    @Size(max = 200)
    val title: String?,

    @Column(nullable = true, length = 1000)
    @Size(max = 1000)
    val link: String?,

    @Column(nullable = true)
    @Size(max = 500)
    val memo: String?
) : BaseEntity() {

    // 다른 엔티티의 데이터로 현재 엔티티를 업데이트함 (ID는 유지)
    fun updateFrom(other: BlogPostEntity): BlogPostEntity {
        return this.copy(
            sequenceNumber = other.sequenceNumber,
            title = other.title,
            link = other.link,
            memo = other.memo
        )
    }
}