package com.blogbank.blogbankback.domain.blogpost.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/blog")
class BlogPostViewController {

    @GetMapping
    fun showBlogPostList(): String {
        return "blog-page"
    }

    @GetMapping("/list")
    fun showBlogList(): String {
        return "blog-page"
    }
}