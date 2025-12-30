package com.blogbank.blogbankback.domain.blogpost.manager

import com.blogbank.blogbankback.domain.blogpost.dto.BlogPostDto
import com.blogbank.blogbankback.domain.github.client.GitHubClient
import com.blogbank.blogbankback.domain.blogpost.business.BlogPostExtractor
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Component
import java.util.*

@Component
class BlogPostCollectManager(
    private val gitHubClient: GitHubClient,
    private val blogPostExtractor: BlogPostExtractor
) {

    companion object {
        private const val FILE_NAME_PATTERN = "round%02d.md"
    }

    fun collectBlogPosts(roundNumber: Int): List<BlogPostDto> = runBlocking {
        collectBlogPostsAsync(roundNumber)
    }

    private suspend fun collectBlogPostsAsync(roundNumber: Int): List<BlogPostDto> {
        val fileName = String.format(FILE_NAME_PATTERN, roundNumber)
        val gitHubFile = gitHubClient.getFileContent(fileName)

        if (gitHubFile == null) return emptyList()

        val cleanedBase64 = gitHubFile.content.replace("\n", "").replace("\r", "").replace(" ", "")
        val decodedContent = String(Base64.getDecoder().decode(cleanedBase64))
        return blogPostExtractor.parseMarkdownTable(decodedContent)
    }
}