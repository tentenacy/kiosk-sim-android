package com.tenutz.kiosksim.ui.order.args

import android.os.Parcelable
import com.tenutz.kiosksim.data.datasource.api.dto.kiosk.payment.MenuPayment
import kotlinx.parcelize.Parcelize

@Parcelize
data class KioskMenuPaymentsArgs(
    val menuPayments: List<MenuPayment>,
    val mainCategoryCode: String,
    val middleCategoryCode: String,
    val orderType: String,
    val totalAmount: Int,
): Parcelable

