package com.tenutz.kiosksim.data.datasource.sharedpref

import com.chibatching.kotpref.KotprefModel

object Order: KotprefModel() {
    var callNumber by stringPref()
    var orderedAt by longPref(0L)
}