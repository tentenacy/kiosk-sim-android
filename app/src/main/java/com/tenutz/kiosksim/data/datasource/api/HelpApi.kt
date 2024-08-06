package com.tenutz.kiosksim.data.datasource.api

import com.tenutz.kiosksim.data.datasource.api.dto.help.HelpsResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface HelpApi {

    @GET("/helps")
    fun helps(
        @Query("query") query: String? = null,
    ): Single<HelpsResponse>
}