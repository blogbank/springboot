package com.blogbank.blogbankback.util.fixture

import com.blogbank.blogbankback.domain.github.dto.GitHubFileDto
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class GitHubClientMockResponses {

    // GitHubClient.getFileContent()의 다양한 응답값들을 정의함
    private val mockResponses: List<Response<GitHubFileDto>> = listOf(
        Response.error(404, "{}".toResponseBody()), // GitHub에서 파일을 찾을 수 없는 경우
        Response.success(GitHubFileDto(
            name = "round01.md",
            content = "IyBCbG9nQmFuayBTZWFzb24gMyAxcyBSb3VuZAoKIyMgVGVzdCBEYXRhCgp8IE5vLiB8IOygk+yekCB8IOuegOungCDso7zsoIwgfCDrp6TrqqggLyDsvZTrqZjtirggfApbLS0tLS18LS0tLS18LS0tLS0tLS0tLS0tLS0tLS0tLS0tLS18LS0tLS0tLS18CnwgMSB8IOydtOyLreyViCB8IFtKT09RIERTTCDsg53shLFdKGh0dHA6Ly9leGFtcGxlLmNvbSkgfCB8CnwgMiB8IOyehOyngOydgCB8IFtXZWIgQVBJIOy5tOyKpOydmCDslYjsoJXshLFdKGh0dHA6Ly9leGFtcGxlLmNvbSkgfCB8",
            encoding = "base64",
            type = "file"
        ))
    )

    // 랜덤한 응답값을 반환함
    fun getRandomResponse(): Response<GitHubFileDto> {
        return mockResponses.random()
    }

    // 특정 라운드에 해당하는 응답값을 반환함 (테스트 시나리오별로 사용)
    fun getResponseByRound(roundNumber: Int): Response<GitHubFileDto> {
        return when (roundNumber) {
            1 -> mockResponses[1] // round01.md
            else -> mockResponses[0] // 404 (파일 없음)
        }
    }

    // 404 응답을 반환함 (파일을 찾을 수 없는 경우)
    fun getNotFoundResponse(): Response<GitHubFileDto> {
        return mockResponses[0]
    }

    // 빈 내용의 응답을 반환함
    fun getEmptyResponse(): Response<GitHubFileDto> {
        return Response.success(GitHubFileDto(
            name = "empty.md",
            content = "",
            encoding = "utf-8",
            type = "file"
        ))
    }

    // 모든 응답값 목록을 반환함 (테스트에서 전체 확인용)
    fun getAllResponses(): List<Response<GitHubFileDto>> {
        return mockResponses
    }
}