package com.tenutz.kiosksim.data.datasource.api.dto.kiosk.menu

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

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
): Parcelable