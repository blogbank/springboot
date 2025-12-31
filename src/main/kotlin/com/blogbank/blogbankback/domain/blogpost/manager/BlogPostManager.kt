package com.blogbank.blogbankback.domain.blogpost.manager

import com.blogbank.blogbankback.domain.blogpost.dto.BlogPostDto
import com.blogbank.blogbankback.domain.blogpost.repository.BlogPostRepository
import com.blogbank.blogbankback.domain.blogpost.business.BlogPostManagerMapper
import com.blogbank.blogbankback.domain.blogpost.entity.BlogPostEntity
import com.blogbank.blogbankback.domain.github.client.GitHubClient
import com.blogbank.blogbankback.domain.blogpost.business.BlogPostExtractor
import com.blogbank.blogbankback.domain.blogpost.repository.BlogPostCacheRepository
import com.blogbank.blogbankback.util.ResponseUtils
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Component

@Component
class BlogPostManager(
    private val blogPostRepository: BlogPostRepository,
    private val blogPostCacheRepository: BlogPostCacheRepository,
    private val blogPostManagerMapper: BlogPostManagerMapper,
    private val gitHubClient: GitHubClient,
    private val blogPostExtractor: BlogPostExtractor
) {

    companion object {
        private const val FILE_NAME_PATTERN = "round%02d.md"
    }

    // 블로그 포스트를 수집함
    fun collectBlogPosts(roundNumber: Int): List<BlogPostDto> = runBlocking {
        collectBlogPostsAsync(roundNumber)
    }

    // 모든 블로그 포스트를 조회함
    fun getAll(): List<BlogPostEntity> {
        return blogPostRepository.findAllByOrderBySequenceNumberAsc()
    }


    // 블로그 포스트 리스트를 upsert함 (roundNumber와 authorName 기준으로 없으면 저장, 있으면 업데이트)
    fun upsertBlogPost(roundNumber: Int, entities: List<BlogPostEntity>): List<BlogPostEntity> {
        if (entities.isEmpty()) return emptyList()

        val existingMap = fetchExistingEntitiesByAuthorName(roundNumber)
        val entitiesToSave = mergeEntities(entities, existingMap)

        return blogPostRepository.saveAll(entitiesToSave)
    }

    // 캐시를 활용해서 라운드별 블로그 포스트를 조회함 (캐시 있으면 가져오고, 없으면 수집해서 저장)
    fun getBlogPostsWithCache(roundNumber: Int): List<BlogPostEntity> {
        // 1. 캐시 조회
        val cachedEntities = blogPostCacheRepository.get(roundNumber)
        if (!cachedEntities.isNullOrEmpty()) {
            return cachedEntities
        }

        // 2. GitHub 수집 및 저장
        val collectedEntities = fetchAndSaveFromProvider(roundNumber)
        if (collectedEntities.isNotEmpty()) {
            blogPostCacheRepository.save(roundNumber, collectedEntities)
            return collectedEntities
        }

        // 3. DB 조회 (Fallback)
        return blogPostRepository.findAllByRoundNumberOrderBySequenceNumberAsc(roundNumber)
    }

    /**
     * 내부 활용 메소드
     */

    // 비동기로 블로그 포스트를 수집함
    private suspend fun collectBlogPostsAsync(roundNumber: Int): List<BlogPostDto> {
        val fileName = String.format(FILE_NAME_PATTERN, roundNumber)
        val response = gitHubClient.getFileContent(fileName)

        val gitHubFile = ResponseUtils.getBodyOrNull(response)
        if (gitHubFile == null) return emptyList()

        // 파싱된 데이터를 BlogPostDto로 변환
        val parsedData = blogPostExtractor.parseMarkdownTable(gitHubFile.content)
        return blogPostManagerMapper.toDtoListFromParsedData(parsedData, roundNumber)
    }

    // GitHub에서 데이터를 수집하여 저장함
    private fun fetchAndSaveFromProvider(roundNumber: Int): List<BlogPostEntity> {
        val blogPosts = collectBlogPosts(roundNumber)
        if (blogPosts.isEmpty()) return emptyList()

        val entities = blogPostManagerMapper.toEntityList(blogPosts)
        return upsertBlogPost(roundNumber, entities)
    }

    // 기존 엔티티들을 조회하여 authorName 기준으로 Map 반환함
    private fun fetchExistingEntitiesByAuthorName(roundNumber: Int): Map<String, BlogPostEntity> {
        val existingEntities = blogPostRepository.findAllByRoundNumberOrderBySequenceNumberAsc(roundNumber)
        return existingEntities.associateBy { it.authorName }
    }

    // 새로운 엔티티들과 기존 엔티티들을 병합함
    private fun mergeEntities(
        newEntities: List<BlogPostEntity>,
        existingMap: Map<String, BlogPostEntity>
    ): List<BlogPostEntity> {
        return newEntities.map { newEntity ->
            existingMap[newEntity.authorName]?.apply { updateFrom(newEntity) } ?: newEntity
        }
    }
}