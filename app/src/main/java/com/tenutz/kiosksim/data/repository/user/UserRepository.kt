package com.tenutz.kiosksim.data.repository.user

import com.tenutz.kiosksim.data.datasource.api.dto.common.TokenRequest
import com.tenutz.kiosksim.data.datasource.api.dto.common.TokenResponse
import com.tenutz.kiosksim.utils.type.SocialType
import io.reactivex.rxjava3.core.Single

interface UserRepository {

    fun socialSignupOrLogin(
        socialType: SocialType,
    ): Single<TokenResponse>

    fun reissue(request: TokenRequest): Single<TokenResponse>

}