package com.blogbank.blogbankback.domain.github.client

import com.blogbank.blogbankback.domain.github.dto.GitHubFileDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubClient {

    @GET("repos/blogbank/season3/contents/{fileName}")
    suspend fun getFileContent(@Path("fileName") fileName: String): Response<GitHubFileDto>
}