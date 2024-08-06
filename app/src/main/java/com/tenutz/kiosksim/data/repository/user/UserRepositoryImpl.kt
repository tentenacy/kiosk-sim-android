package com.tenutz.kiosksim.data.repository.user

import com.tenutz.kiosksim.data.datasource.api.SMSApi
import com.tenutz.kiosksim.data.datasource.api.dto.common.TokenRequest
import com.tenutz.kiosksim.data.datasource.api.dto.common.TokenResponse
import com.tenutz.kiosksim.data.datasource.api.dto.user.SocialLoginRequest
import com.tenutz.kiosksim.data.datasource.sharedpref.OAuthToken
import com.tenutz.kiosksim.data.datasource.sharedpref.User
import com.tenutz.kiosksim.utils.type.SocialType
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val SMSApi: SMSApi,
) : UserRepository {

    override fun socialSignupOrLogin(
        socialType: SocialType,
    ): Single<TokenResponse> =
        SMSApi.socialSignupOrLogin(
            socialType.name,
            SocialLoginRequest(OAuthToken.accessToken, User.fcmToken),
        )

    override fun reissue(request: TokenRequest): Single<TokenResponse> =
        SMSApi.reissue(request)
}