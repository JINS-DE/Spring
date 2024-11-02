package com.jungle.kotlinboard.common.authority

data class TokenInfo(
    val grantType: String,
    val accessToken: String, // 실제 검증할 토큰
)
