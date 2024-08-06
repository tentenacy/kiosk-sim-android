package com.tenutz.kiosksim.ui.base

import com.tenutz.kiosksim.ui.login.args.SocialProfileArgs

interface Loginable {

    fun socialSignupOrLogin(args: SocialProfileArgs)
    fun logout()
}