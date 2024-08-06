package com.tenutz.kiosksim.ui.login

import com.orhanobut.logger.Logger
import com.tenutz.kiosksim.application.manager.OAuthLoginManagerSubject
import com.tenutz.kiosksim.data.datasource.sharedpref.OAuthToken
import com.tenutz.kiosksim.data.datasource.sharedpref.SocialProfile
import com.tenutz.kiosksim.data.datasource.sharedpref.Token
import com.tenutz.kiosksim.data.datasource.sharedpref.User
import com.tenutz.kiosksim.data.repository.user.UserRepository
import com.tenutz.kiosksim.ui.base.BaseViewModel
import com.tenutz.kiosksim.ui.base.Loginable
import com.tenutz.kiosksim.ui.login.args.SocialProfileArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val oauthLoginManagerMap: Map<String, @JvmSuppressWildcards OAuthLoginManagerSubject>,
): BaseViewModel(), Loginable {

    companion object {

        const val EVENT_NAVIGATE_TO_ACCESS = 1000
    }

    override fun socialSignupOrLogin(args: SocialProfileArgs) {
        userRepository.socialSignupOrLogin(args.socialType)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->

                Token.save(
                    grantType = response.grantType,
                    accessToken = response.accessToken,
                    refreshToken = response.refreshToken,
                    accessTokenExpireDate = response.accessTokenExpiresIn,
                )

                SocialProfile.save(
                    args.accessToken,
                    args.name,
                    args.email,
                    args.profileImageUrl,
                    args.socialType.name
                )

                viewEvent(Pair(EVENT_NAVIGATE_TO_ACCESS, args))
            }) { t ->

                Logger.e("${t}")

                logout()

            }.addTo(compositeDisposable)
    }

    override fun logout() {
        oauthLoginManagerMap[OAuthToken.socialType]?.logout()
        OAuthToken.clear()
        Token.clear()
        SocialProfile.clear()
        User.clear()
    }
}