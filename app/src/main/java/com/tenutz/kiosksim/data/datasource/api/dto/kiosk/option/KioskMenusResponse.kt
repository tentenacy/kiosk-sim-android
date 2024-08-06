package com.tenutz.kiosksim.data.datasource.api.dto.kiosk.option

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class KioskMenuOptionsResponse(
    val menuOptionGroups: List<MenuOptionGroup>,
    val storeCode: String,
    val mainCategoryCode: String,
    val middleCategoryCode: String,
    val subCategoryCode: String,
    val menuCode: String,
    val menuName: String,
    val price: Int,
    val discountedPrice: Int,
    val additionalPackagingPrice: Int,
    val packaging: String?,
    val outOfStock: Boolean,
    val imageName: String?,
    val highlightType: String?,
    val eventDateFrom: String?,
    val eventDateTo: String?,
    val eventTimeFrom: String?,
    val eventTimeTo: String?,
    val eventDayOfWeek: String?,
    val imageUrl: String?,
): Parcelable {
    @Parcelize
    data class MenuOptionGroup(
        val optionGroupOptions: List<OptionGroupOption>?,
        val optionGroupCode: String,
        val optionGroupName: String,
        val required: Boolean,
        val priority: Int,
        val creator: String?,
        val createdAt: Date?,
        val lastModifier: String?,
        val lastModifiedAt: Date?,
    ): Parcelable {
        @Parcelize
        data class OptionGroupOption(
            val optionCode: String,
            val optionName: String,
            val price: Int,
        ): Parcelable
    }
}
