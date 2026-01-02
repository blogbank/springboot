package com.blogbank.blogbankback.domain.blogpost.controller

import com.blogbank.blogbankback.domain.blogpost.dto.BlogPostListResponseDto
import com.blogbank.blogbankback.domain.blogpost.service.BlogPostService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/blog-posts")
class BlogPostController(
    private val blogPostService: BlogPostService
) {

    @GetMapping("/rounds/{roundNumber}")
    fun getBlogPostsForRound(@PathVariable roundNumber: Int): ResponseEntity<BlogPostListResponseDto> {
        val response = blogPostService.getBlogPostsForRound(roundNumber)
        return ResponseEntity.ok(response)
    }

    @GetMapping
    fun getAllBlogPosts(): ResponseEntity<BlogPostListResponseDto> {
        val response = blogPostService.getAllBlogPosts()
        return ResponseEntity.ok(response)
    }
}