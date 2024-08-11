package com.tenutz.kiosksim.ui.review.menu.args

import android.os.Parcelable
import com.tenutz.kiosksim.data.datasource.api.dto.kiosk.menu.KioskReviewMenusResponse
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class ReviewMenuArgs(
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
): Parcelable {

    @IgnoredOnParcel
    var rating: Int = 0

    companion object {
        fun of(response: KioskReviewMenusResponse.OrderMenu) = ReviewMenuArgs(
            response.storeCode,
            response.mainCategoryCode,
            response.middleCategoryCode,
            response.categoryCode,
            response.categoryName,
            response.menuCode,
            response.menuName,
            response.price,
            response.discountedPrice,
            response.imageName,
            response.imageUrl,
            response.creator,
            response.createdAt,
            response.lastModifier,
            response.lastModifiedAt,
        )
    }
}