package com.tenutz.kiosksim.data.datasource.api.dto.category

data class MainCategoryCreateRequest(
    val categoryCode: String,
    val categoryName: String,
    val use: Boolean,
)
