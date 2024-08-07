package com.tenutz.kiosksim.ui.order

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class KioskMenuPaymentCreateArgs(
    val mainCategoryCode: String,
    val middleCategoryCode: String,
    val subCategoryCode: String,
    val orderType: String,
    val totalAmount: Int,
): Parcelable
