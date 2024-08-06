package com.tenutz.kiosksim.data.datasource.api.dto.user

data class SocialLoginRequest(
    val accessToken: String,
    val fcmToken: String,
)
