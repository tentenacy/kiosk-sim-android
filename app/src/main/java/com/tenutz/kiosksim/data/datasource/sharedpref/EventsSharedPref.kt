package com.tenutz.kiosksim.data.datasource.sharedpref

import com.chibatching.kotpref.KotprefModel

object EventsSharedPref: KotprefModel() {
    var existsNewNotification by booleanPref(false)
}