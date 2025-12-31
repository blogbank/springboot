package com.blogbank.blogbankback.util

import retrofit2.Response

object ResponseUtils {

    // Response가 성공하면 body를 반환하고, 실패하면 null을 반환함
    fun <T> getBodyOrNull(response: Response<T>): T? {
        return if (response.isSuccessful) response.body() else null
    }
}