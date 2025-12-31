package com.blogbank.blogbankback.util.validator

import org.springframework.test.web.reactive.server.WebTestClient

/**
 * HTTP 응답 검증을 위한 확장 함수들
 */

// 201 Created 상태 코드 및 응답 본문 검증
fun <T : Any> WebTestClient.ResponseSpec.expectCreated(clazz: Class<T>): WebTestClient.BodySpec<T, *> {
    return this.expectStatus().isCreated.expectBody(clazz)
}

// 200 OK 상태 코드 및 응답 본문 검증
fun <T : Any> WebTestClient.ResponseSpec.expectOk(clazz: Class<T>): WebTestClient.BodySpec<T, *> {
    return this.expectStatus().isOk.expectBody(clazz)
}

// 404 Not Found 상태 코드 검증
fun WebTestClient.ResponseSpec.expectNotFound() {
    this.expectStatus().isNotFound
}

// 400 Bad Request 상태 코드 검증
fun WebTestClient.ResponseSpec.expectBadRequest() {
    this.expectStatus().isBadRequest
}