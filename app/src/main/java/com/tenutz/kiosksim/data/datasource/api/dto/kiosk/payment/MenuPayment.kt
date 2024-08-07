package com.tenutz.kiosksim.data.datasource.api.dto.kiosk.payment

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MenuPayment(
    var key: String,
    val itemCode: String,
    val itemType: String,
    val itemName: String,
    val itemImageUrl: String?,
    val price: Int,
    val amount: Int,
    val discountAmount: Int,
    val quantity: Int,
    val details: String?,
): Parcelable {
    operator fun plus(menuPayment: MenuPayment): MenuPayment {
        if(menuPayment.key != this.key) return this
        return MenuPayment(
            this.key,
            this.itemCode,
            this.itemType,
            this.itemName,
            this.itemImageUrl,
            this.price,
            this.amount + menuPayment.amount,
            this.discountAmount + menuPayment.discountAmount,
            this.quantity + menuPayment.quantity,
            this.details,
        )
    }
}
