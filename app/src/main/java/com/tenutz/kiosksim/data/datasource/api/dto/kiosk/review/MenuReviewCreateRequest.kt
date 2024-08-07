package com.tenutz.kiosksim.data.datasource.api.dto.kiosk.review

data class MenuReviewCreateRequest(
    val mainCategoryCode: String,
    val middleCategoryCode: String,
    val subCategoryCode: String,
    val menuCode: String,
    val name: String,
    val contents: String,
    val rating: Int,
)
