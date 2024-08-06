package com.tenutz.kiosksim.data.datasource.api.dto.kiosk.menu

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class KioskMenusResponse(
    val menusCategories: List<MenusCategory>
): Parcelable
