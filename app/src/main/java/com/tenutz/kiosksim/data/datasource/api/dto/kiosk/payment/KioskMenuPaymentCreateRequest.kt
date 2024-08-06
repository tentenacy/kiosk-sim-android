package com.tenutz.kiosksim.data.datasource.api.dto.kiosk.payment

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class KioskMenuPaymentCreateRequest(
    val menuPayments: List<MenuPayment>,
    val mainCategoryCode: String,
    val middleCategoryCode: String,
    val subCategoryCode: String,
    val orderType: String,
    val paymentCode: String,
    val creditCardCompanyCode: String?,
    val totalAmount: Int,
): Parcelable {
    @Parcelize
    data class MenuPayment(
        val itemCode: String,
        val itemType: String,
        val itemName: String,
        val price: Int,
        val amount: Int,
        val discountAmount: Int,
        val quantity: Int,
        val details: String?,
    ): Parcelable
}
