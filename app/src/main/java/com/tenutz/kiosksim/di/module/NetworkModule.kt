package com.tenutz.kiosksim.di.module

import com.google.gson.GsonBuilder
import com.tenutz.kiosksim.BuildConfig
import com.tenutz.kiosksim.data.datasource.api.SMSApi
import com.tenutz.kiosksim.data.datasource.api.UserApi
import com.tenutz.kiosksim.network.authenticator.TokenAuthenticator
import com.tenutz.kiosksim.network.interceptor.TokenInterceptor
import com.tenutz.kiosksim.utils.converter.DateDeserializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import java.util.*
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Singleton
    @Provides
    fun provideTokenInterceptor(): TokenInterceptor {
        return TokenInterceptor()
    }

    @Singleton
    @Provides
    fun provideTokenAuthenticator(userApi: UserApi): TokenAuthenticator {
        return TokenAuthenticator(userApi)
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        tokenInterceptor: TokenInterceptor,
        authenticator: TokenAuthenticator,
    ): Retrofit.Builder {

        val client = OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .authenticator(authenticator)
            .build()

        val gson = GsonBuilder()
            .registerTypeAdapter(Date::class.java, DateDeserializer())
            .create()

        return Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(client)
    }

    @Singleton
    @Provides
    fun provideUserApi(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): UserApi {
        return Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(httpLoggingInterceptor)
                    .build()
            )
            .baseUrl(BuildConfig.API_URL_BASE+"/app/kiosk/")
            .build()
            .create()
    }

    @Singleton
    @Provides
    fun provideOMSApi(retrofitBuilder: Retrofit.Builder): SMSApi {
        return retrofitBuilder
            .baseUrl(BuildConfig.API_URL_BASE+"/app/kiosk/")
            .build()
            .create()
    }
}