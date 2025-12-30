package com.blogbank.blogbankback.util.client

import org.springframework.test.web.reactive.server.WebTestClient

/**
 * HTTP 요청 전송을 담당하는 클래스
 */
class HttpRequestClient(private val webTestClient: WebTestClient) {

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
}