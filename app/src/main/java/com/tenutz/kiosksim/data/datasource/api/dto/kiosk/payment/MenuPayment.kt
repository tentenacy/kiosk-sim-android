package com.tenutz.kiosksim.data.datasource.api.dto.kiosk.payment

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MenuPayment(
    var key: String,
    val subCategoryCode: String,
    val itemCode: String,
    val itemType: String,
    val itemName: String,
    val itemImageUrl: String?,
    val price: Int,
    val optionPrice: Int,
    val discountPrice: Int,
    val details: String?,
): Parcelable {

    var amount: Int = 0

    var optionAmount: Int = 0

    var discountAmount: Int = 0

    var totalAmount: Int = 0

    var quantity: Int = 0
        get() = field
        set(value) {
            field = value
            this.amount = this.price * value
            this.optionAmount = this.optionPrice * value
            this.discountAmount = this.discountPrice * value
            this.totalAmount = (this.price + this.optionPrice - this.discountPrice) * value
        }

    operator fun plus(menuPayment: MenuPayment): MenuPayment {
        if(menuPayment.key != this.key) return this
        return MenuPayment(
            this.key,
            this.subCategoryCode,
            this.itemCode,
            this.itemType,
            this.itemName,
            this.itemImageUrl,
            this.price,
            this.optionPrice,
            this.discountPrice,
            this.details,
        ).apply { this@apply.quantity = this@MenuPayment.quantity + menuPayment.quantity }
    }
}
