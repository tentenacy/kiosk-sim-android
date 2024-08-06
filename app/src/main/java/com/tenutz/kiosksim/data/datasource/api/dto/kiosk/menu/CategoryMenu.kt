package com.tenutz.kiosksim.data.datasource.api.dto.kiosk.menu

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryMenu(
    val menuCode: String,
    val menuName: String,
    val price: Int,
    val discountedPrice: Int,
    val additionalPackagingPrice: Int,
    val packaging: String?,
    val outOfStock: Boolean,
    val use: Boolean?,
    val ingredientDisplay: Boolean,
    val imageName: String?,
    val highlightType: String?,
    val showDateFrom: String?,
    val showDateTo: String?,
    val showTimeFrom: String?,
    val showTimeTo: String?,
    val showDayOfWeek: String?,
    val eventDateFrom: String?,
    val eventDateTo: String?,
    val eventTimeFrom: String?,
    val eventTimeTo: String?,
    val eventDayOfWeek: String?,
    val memoKor: String?,
    val priority: Int,
    val ingredientDetails: String?,
    val imageUrl: String?,
): Parcelable