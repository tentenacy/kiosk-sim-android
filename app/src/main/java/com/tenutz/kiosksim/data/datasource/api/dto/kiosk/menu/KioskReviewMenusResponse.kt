package com.tenutz.kiosksim.data.datasource.api.dto.kiosk.menu

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class KioskReviewMenusResponse(
    val orderMenus: List<OrderMenu>,
): Parcelable {
    @Parcelize
    data class OrderMenu(
        val storeCode: String,
        val mainCategoryCode: String,
        val middleCategoryCode: String,
        val categoryCode: String,
        val categoryName: String,
        val menuCode: String,
        val menuName: String,
        val price: Int,
        val discountedPrice: Int,
        val imageName: String?,
        val imageUrl: String?,
        val creator: String?,
        val createdAt: Date?,
        val lastModifier: String?,
        val lastModifiedAt: Date?,
    ): Parcelable
}
