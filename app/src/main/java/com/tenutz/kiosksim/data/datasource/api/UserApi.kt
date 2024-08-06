package com.tenutz.kiosksim.data.datasource.api

import com.tenutz.kiosksim.data.datasource.api.dto.common.TokenRequest
import com.tenutz.kiosksim.data.datasource.api.dto.common.TokenResponse
import com.tenutz.kiosksim.data.datasource.api.dto.user.SocialLoginRequest
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApi {

    @POST("users/social/{socialType}/token")
    fun socialSignupOrLogin(
        @Path("socialType") socialType: String,
        @Body request: SocialLoginRequest
    ): Single<TokenResponse>

    @POST("users/token/expiration")
    fun reissue(@Body request: TokenRequest): Single<TokenResponse>

    @GET("users/existing-stores/kiosk/{kioskCode}")
    fun storeExists(
        @Path("kioskCode") kioskCode: String,
    ): Single<Unit>
}