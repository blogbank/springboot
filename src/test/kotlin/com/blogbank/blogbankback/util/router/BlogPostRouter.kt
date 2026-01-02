package com.blogbank.blogbankback.util.router

/**
 * BlogPost API URL을 관리하는 라우터
 * API 변경 시 이 클래스만 수정하면 모든 테스트에 반영됨
 */
object BlogPostRouter {

    private const val BASE = "/api/blog-posts"

    // 모든 블로그 포스트 조회
    const val GET_ALL = BASE

    // 특정 라운드 블로그 포스트 조회
    fun getRound(roundNumber: Int) = "$BASE/rounds/$roundNumber"

}