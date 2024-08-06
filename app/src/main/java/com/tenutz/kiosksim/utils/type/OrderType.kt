package com.tenutz.kiosksim.utils.type

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class OrderType: Parcelable {
    data object TAKE_IN : OrderType()
    data object TAKE_OUT : OrderType()

    val value: String get() = when(this) {
        is TAKE_IN -> "05"
        is TAKE_OUT -> "06"
    }

    companion object {
        fun of(value: String): OrderType? = when(value) {
            "05" -> TAKE_IN
            "06" -> TAKE_OUT
            else -> null
        }
    }
}