package com.blogbank.blogbankback.util.client

import org.springframework.test.web.reactive.server.WebTestClient

/**
 * HTTP 요청 전송을 담당하는 클래스
 */
class HttpRequestClient(private val webTestClient: WebTestClient) {

    // 기본 요청 메소드들
    fun postRequest(uri: String, body: Any? = null): WebTestClient.ResponseSpec {
        return webTestClient.post()
            .uri(uri)
            .apply { if (body != null) bodyValue(body) }
            .exchange()
    }

    fun getRequest(uri: String): WebTestClient.ResponseSpec {
        return webTestClient.get()
            .uri(uri)
            .exchange()
    }

    fun putRequest(uri: String, body: Any): WebTestClient.ResponseSpec {
        return webTestClient.put()
            .uri(uri)
            .bodyValue(body)
            .exchange()
    }

    fun deleteRequest(uri: String): WebTestClient.ResponseSpec {
        return webTestClient.delete()
            .uri(uri)
            .exchange()
    }

    // 200 OK + 응답 객체 반환하는 편의 메소드들
    inline fun <reified T> getOkResponse(uri: String): T {
        return getRequest(uri)
            .expectStatus().isOk
            .expectBody(T::class.java)
            .returnResult()
            .responseBody!!
    }

    // 람다 패턴 버전 - 응답 객체로 람다 실행
    inline fun <reified T> getOkResponse(uri: String, block: (T) -> Unit) {
        val response = getRequest(uri)
            .expectStatus().isOk
            .expectBody(T::class.java)
            .returnResult()
            .responseBody!!

        block(response)
    }

    inline fun <reified T> postOkResponse(uri: String, body: Any? = null): T {
        return postRequest(uri, body)
            .expectStatus().isOk
            .expectBody(T::class.java)
            .returnResult()
            .responseBody!!
    }

    // 람다 패턴 버전 - POST
    inline fun <reified T> postOkResponse(uri: String, body: Any? = null, block: (T) -> Unit) {
        val response = postRequest(uri, body)
            .expectStatus().isOk
            .expectBody(T::class.java)
            .returnResult()
            .responseBody!!

        block(response)
    }

    inline fun <reified T> putOkResponse(uri: String, body: Any): T {
        return putRequest(uri, body)
            .expectStatus().isOk
            .expectBody(T::class.java)
            .returnResult()
            .responseBody!!
    }

    inline fun <reified T> deleteOkResponse(uri: String): T {
        return deleteRequest(uri)
            .expectStatus().isOk
            .expectBody(T::class.java)
            .returnResult()
            .responseBody!!
    }
}