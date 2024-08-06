package com.tenutz.kiosksim.data.datasource.api.dto.user

data class SignupRequest(
    val businessNumber: String,
    val phoneNumber: String,
    val type1Agreement: Boolean,
    val type2Agreement: Boolean,
)
