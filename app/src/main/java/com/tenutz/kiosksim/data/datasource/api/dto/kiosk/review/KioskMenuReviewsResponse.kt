package com.tenutz.kiosksim.data.datasource.api.dto.kiosk.review

import java.util.*

data class KioskMenuReviewsResponse(
    val seq: Long,
    val siteCode: String?,
    val storeCode: String?,
    val mainCategoryCode: String?,
    val middleCategoryCode: String?,
    val subCategoryCode: String?,
    val menuCode: String?,
    val menuName: String?,
    val imageUrl: String?,
    val createdBy: String?,
    val createdAt: Date?,
    val content: String?,
    val level: Int,
    val rating: Int,
    val sno: Int,
    val menuReviewReply: MenuReviewReply?,
) {
    data class MenuReviewReply(
        val seq: Long,
        val siteCode: String?,
        val storeCode: String?,
        val mainCategoryCode: String?,
        val middleCategoryCode: String?,
        val subCategoryCode: String?,
        val menuCode: String?,
        val createdBy: String?,
        val createdAt: Date?,
        val content: String?,
    )
}
