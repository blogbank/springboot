package com.blogbank.blogbankback.util

import java.util.*

object Base64Utils {

    // base64 인코딩된 컨텐츠를 디코딩함
    fun decodeGitHubContent(base64Content: String): String {
        val cleanedBase64 = base64Content.replace("\n", "").replace("\r", "").replace(" ", "")
        return String(Base64.getDecoder().decode(cleanedBase64))
    }
}