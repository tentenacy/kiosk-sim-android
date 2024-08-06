package com.tenutz.kiosksim.data.datasource.sharedpref

import com.chibatching.kotpref.KotprefModel

object Settings: KotprefModel() {

    var pushReceived by booleanPref(true)
    var autoLoggedIn by booleanPref(true)
    var autoEntered by booleanPref(false)
}