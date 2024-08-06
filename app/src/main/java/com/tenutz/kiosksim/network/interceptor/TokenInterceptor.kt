package com.tenutz.kiosksim.network.interceptor

import com.tenutz.kiosksim.data.datasource.sharedpref.Token
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(
                "Authorization",
                if (Token.accessToken.isNotBlank()) "Bearer ${Token.accessToken}" else ""
            )
            .build()

        val proceed = chain.proceed(request)
        return proceed
    }
}