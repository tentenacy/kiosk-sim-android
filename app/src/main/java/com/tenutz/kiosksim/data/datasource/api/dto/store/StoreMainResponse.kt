package com.tenutz.kiosksim.data.datasource.api.dto.store

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class StoreMainResponse(
    val siteCode: String,
    val storeCode: String,
    val storeName: String?,
    val storeManagerName: String?,
    val kioskCode: String,
    val todaySalesAmountTotal: Int?,
    val yesterdaySalesAmountTotal: Int?,
    val thisMonthSalesAmountTotal: Int?,

    val mainCategoryCode: String,
    val middleCategoryCode: String,
    val imageName: String?,
    val imageUrl: String?,
    val tel: String?,
    val address: String?,
    val createdAt: Date?,
    val lastModifiedAt: Date?,
): Parcelable