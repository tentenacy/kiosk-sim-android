package com.tenutz.kiosksim.data.datasource.api.dto.user

data class LoginRequest(
    val id: String,
    val password: String,
    val provider: String? = null,
)
