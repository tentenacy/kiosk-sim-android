package com.tenutz.kiosksim.data.datasource.api.dto.kiosk.option

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class KioskMenusResponse(
    val menusCategories: List<MenusCategory>
): Parcelable {
    @Parcelize
    data class MenusCategory(
        val categoryMenus: List<CategoryMenu>?,
        val storeCode: String,
        val mainCategoryCode: String,
        val middleCategoryCode: String,
        val categoryCode: String,
        val categoryName: String,
        val use: Boolean?,
        val order: Int,
        val creator: String?,
        val createdAt: Date?,
        val lastModifier: String?,
        val lastModifiedAt: Date?,
    ): Parcelable {
        @Parcelize
        data class CategoryMenu(
            val menuCode: String,
            val menuName: String,
            val price: Int,
            val discountedPrice: Int,
            val additionalPackagingPrice: Int,
            val packaging: String,
            val outOfStock: Boolean,
            val use: Boolean?,
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
            val imageUrl: String?,
        ): Parcelable
    }
}
