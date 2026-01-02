package com.blogbank.blogbankback.util.fixture

import com.blogbank.blogbankback.domain.github.dto.GitHubFileDto
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class GitHubClientMockResponses {

    // GitHubClient.getFileContent()의 다양한 응답값들을 정의함
    private val mockResponses: List<Response<GitHubFileDto>> = listOf(
        // 에러 응답들
        Response.error(400, """{"message": "Bad Request"}""".toResponseBody()), // 잘못된 요청
        Response.error(401, """{"message": "Unauthorized"}""".toResponseBody()), // 인증 실패
        Response.error(403, """{"message": "Forbidden"}""".toResponseBody()), // 권한 없음
        Response.error(404, """{"message": "Not Found"}""".toResponseBody()), // 파일 없음
        Response.error(500, """{"message": "Internal Server Error"}""".toResponseBody()), // 서버 에러
        Response.error(503, """{"message": "Service Unavailable"}""".toResponseBody()), // 서비스 이용 불가

        // 성공 응답들
        Response.success(GitHubFileDto( // 정상적인 블로그 데이터
            name = "round01.md",
            content = "IyBCbG9nQmFuayBTZWFzb24gMyAxcyBSb3VuZAoKIyMgVGVzdCBEYXRhCgp8IE5vLiB8IOygk+yekCB8IOuegOungCDso7zsoIwgfCDrp6TrqqggLyDsvZTrqZjtirggfApbLS0tLS18LS0tLS18LS0tLS0tLS0tLS0tLS0tLS0tLS0tLS18LS0tLS0tLS18CnwgMSB8IOydtOyLreyViCB8IFtKT09RIERTTCDsg53shLFdKGh0dHA6Ly9leGFtcGxlLmNvbSkgfCB8CnwgMiB8IOyehOyngOydgCB8IFtXZWIgQVBJIOy5tOyKpOydmCDslYjsoJXshLFdKGh0dHA6Ly9leGFtcGxlLmNvbSkgfCB8",
            encoding = "base64",
            type = "file"
        )),
        Response.success(GitHubFileDto( // 빈 파일
            name = "round02.md",
            content = "",
            encoding = "utf-8",
            type = "file"
        )),
        Response.success(GitHubFileDto( // 헤더만 있는 파일
            name = "round03.md",
            content = "IyBCbG9nQmFuayBTZWFzb24gMw==", // "# BlogBank Season 3" in base64
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
            1 -> mockResponses[6] // round01.md (정상 데이터)
            2 -> mockResponses[7] // round02.md (빈 파일)
            3 -> mockResponses[8] // round03.md (헤더만)
            else -> mockResponses[3] // 404 (파일 없음)
        }
    }

    // 에러 응답들을 반환하는 메소드들
    fun getBadRequestResponse(): Response<GitHubFileDto> = mockResponses[0] // 400
    fun getUnauthorizedResponse(): Response<GitHubFileDto> = mockResponses[1] // 401
    fun getForbiddenResponse(): Response<GitHubFileDto> = mockResponses[2] // 403
    fun getNotFoundResponse(): Response<GitHubFileDto> = mockResponses[3] // 404
    fun getServerErrorResponse(): Response<GitHubFileDto> = mockResponses[4] // 500
    fun getServiceUnavailableResponse(): Response<GitHubFileDto> = mockResponses[5] // 503

    // 성공 응답들을 반환하는 메소드들
    fun getSuccessResponse(): Response<GitHubFileDto> = mockResponses[6] // 정상 데이터
    fun getEmptyResponse(): Response<GitHubFileDto> = mockResponses[7] // 빈 파일
    fun getHeaderOnlyResponse(): Response<GitHubFileDto> = mockResponses[8] // 헤더만

    // 에러 응답만 반환 (랜덤)
    fun getRandomErrorResponse(): Response<GitHubFileDto> {
        return mockResponses.take(6).random() // 인덱스 0-5 (에러 응답들)
    }

    // 성공 응답만 반환 (랜덤)
    fun getRandomSuccessResponse(): Response<GitHubFileDto> {
        return mockResponses.drop(6).random() // 인덱스 6-8 (성공 응답들)
    }

    // 모든 응답값 목록을 반환함 (테스트에서 전체 확인용)
    fun getAllResponses(): List<Response<GitHubFileDto>> {
        return mockResponses
    }
}