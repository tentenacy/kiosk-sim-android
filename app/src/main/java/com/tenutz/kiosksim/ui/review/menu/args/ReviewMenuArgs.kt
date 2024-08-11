package com.tenutz.kiosksim.ui.review.menu.args

import android.os.Parcelable
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
): Parcelable