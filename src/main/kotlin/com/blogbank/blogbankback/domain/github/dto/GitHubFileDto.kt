package com.blogbank.blogbankback.domain.github.dto

data class GitHubFileDto(
    val name: String,
    val content: String,
    val encoding: String,
    val type: String
)