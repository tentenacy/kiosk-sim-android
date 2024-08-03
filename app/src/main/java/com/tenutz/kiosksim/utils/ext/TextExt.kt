package com.tenutz.kiosksim.utils.ext

import java.text.DecimalFormat
import java.text.NumberFormat

val Int.toCurrency: String get() = run {
    val formatter: NumberFormat = DecimalFormat("#,###")
    formatter.format(this)
}

fun randomNumber(n: Int, charRange: CharRange=('0'..'9')): String {
    val allowedChars = (charRange)
    return (1..n)
        .map { allowedChars.random() }
        .joinToString("")
}

val String?.toCouponNumber: String get() = takeIf { !isNullOrBlank() }?.run {

    var substring = substring(0, 4)

    if(substring.startsWith("0"))
        substring = "${randomNumber(1, ('1'..'9'))}${substring(1, 4)}"

    return "$substring-${substring(4,8)}-${substring(8,12)}-${substring(12,16)}"
} ?: ""

val Long.toBusinessNumber: String get() = run {
    val arg1 = "$this".substring(0 until 3)
    val arg2 = "$this".substring(3 until 5)
    val arg3 = "$this".substring(5 until 10)
    "${arg1}-${arg2}-${arg3}"
}

val String?.toBusinessNumber: String get() = takeIf { !isNullOrBlank() }?.run {
    val arg1 = this.substring(0 until 3)
    val arg2 = this.substring(3 until 5)
    val arg3 = this.substring(5 until 10)
    "${arg1}-${arg2}-${arg3}"
} ?: ""

val String?.orEmpty: String get() = this ?: ""