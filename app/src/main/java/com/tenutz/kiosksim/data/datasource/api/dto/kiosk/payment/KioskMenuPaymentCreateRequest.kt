package com.tenutz.kiosksim.data.datasource.api.dto.kiosk.payment

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class KioskMenuPaymentCreateRequest(
    val menuPayments: List<MenuPayment>,
    val mainCategoryCode: String,
    val middleCategoryCode: String,
    val orderType: String,
    val paymentCode: String,
    val creditCardCompanyCode: String?,
    val totalAmount: Int,
): Parcelable
