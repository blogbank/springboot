package com.blogbank.blogbankback.util.casting

/**
 * 안전한 List 캐스팅 유틸리티
 */
object CastingUtils {

    // 안전하게 List<T>로 캐스팅함
    inline fun <reified T> safeListCast(value: Any?): List<T>? {
        return when (value) {
            null -> null
            is List<*> -> value.filterIsInstance<T>()
            else -> null
        }
    }
}