package com.tenutz.kiosksim.ui.base

import com.tenutz.kiosksim.ui.login.args.SocialProfileArgs

interface Loginable {

    fun socialLogin(args: SocialProfileArgs)
    fun logout()
}