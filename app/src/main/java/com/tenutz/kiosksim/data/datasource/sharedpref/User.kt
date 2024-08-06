package com.tenutz.kiosksim.data.datasource.sharedpref

import com.chibatching.kotpref.KotprefModel

object User: KotprefModel() {
    var fcmToken by stringPref()
    var kioskCode by stringPref()
}