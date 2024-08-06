package com.tenutz.kiosksim.ui.login.handler

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.tenutz.kiosksim.ui.base.Loginable
import com.tenutz.kiosksim.ui.login.LoginViewModel
import com.tenutz.kiosksim.ui.login.args.SocialProfileArgs
import com.tenutz.kiosksim.utils.type.SocialType

class KakaoOAuthLoginHandler(private val fragment: Fragment): (OAuthToken?, Throwable?) -> Unit {

    private val viewModel: Loginable by lazy {
        ViewModelProvider(fragment).get(LoginViewModel::class.java)
    }

    override fun invoke(token: OAuthToken?, error: Throwable?) {
        error?.let { onError(it) }
        token?.let { onSuccess(it) }
        onCancel()
    }

    private fun onError(error: Throwable?) = fragment.requireContext().run {
        Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT)
    }

    private fun onSuccess(token: OAuthToken) {
        com.tenutz.kiosksim.data.datasource.sharedpref.OAuthToken.save(
            accessToken = token.accessToken,
            refreshToken = token.refreshToken,
            socialType = SocialType.KAKAO.name,
        )

        UserApiClient.instance.me { user, error ->
            if(error != null) {
                viewModel.socialSignupOrLogin(SocialProfileArgs(token.accessToken, SocialType.KAKAO))
            } else if(user != null) {
                viewModel.socialSignupOrLogin(SocialProfileArgs(token.accessToken, SocialType.KAKAO, user.kakaoAccount?.profile?.nickname, user.kakaoAccount?.email, user.kakaoAccount?.profile?.thumbnailImageUrl))
            }
        }
    }

    private fun onCancel() {

    }
}