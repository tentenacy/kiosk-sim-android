package com.tenutz.kiosksim.data.datasource.api.dto.common

data class TokenRequest(
    val accessToken: String,
    val refreshToken: String,
)
