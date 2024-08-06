package com.tenutz.kiosksim.data.datasource.api

import com.tenutz.kiosksim.data.datasource.api.dto.common.OptionGroupPrioritiesChangeRequest
import com.tenutz.kiosksim.data.datasource.api.dto.common.OptionGroupsDeleteRequest
import com.tenutz.kiosksim.data.datasource.api.dto.common.OptionGroupsMappedByRequest
import com.tenutz.kiosksim.data.datasource.api.dto.option.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import retrofit2.http.*

interface OptionApi {

    @GET("/options")
    fun options(@Query("query") query: String? = null): Single<OptionsResponse>

    @GET("/options/{optionCd}")
    fun option(@Path("optionCd") optionCd: String): Single<OptionResponse>

    @Multipart
    @POST("/options")
    fun createOption(
        @Part image: MultipartBody.Part? = null,
        @Part("optionCode") optionCode: String,
        @Part("optionName") optionName: String,
        @Part("price") price: Int,
        @Part("use") use: Boolean,
    ): Single<Unit>

    @Multipart
    @PUT("/options/{optionCd}")
    fun updateOption(
        @Path("optionCd") optionCd: String,
        @Part image: MultipartBody.Part? = null,
        @Part("optionName") optionName: String,
        @Part("price") price: Int,
        @Part("use") use: Boolean?,
    ): Single<Unit>

    @HTTP(method = "DELETE", path = "/options/{optionCd}", hasBody = true)
    fun deleteOption(@Path("optionCd") optionCd: String): Completable

    @HTTP(method = "DELETE", path = "/options", hasBody = true)
    fun deleteOptions(@Body request: OptionsDeleteRequest): Completable

    @GET("/options/{optionCd}/option-groups")
    fun optionOptionGroups(@Path("optionCd") optionCd: String, @Query("query") query: String? = null): Single<OptionOptionGroupsResponse>

    @GET("/options/{optionCd}/mappers")
    fun optionMappers(@Path("optionCd") optionCd: String): Single<OptionMappersResponse>

    @POST("/options/{optionCd}/mapped-by")
    fun mapToOptionGroups(
        @Path("optionCd") optionCd: String,
        @Body request: OptionGroupsMappedByRequest
    ): Single<Unit>

    @HTTP(method = "DELETE", path = "/options/{optionCd}/mappers", hasBody = true)
    fun deleteOptionMappers(
        @Path("optionCd") optionCd: String,
        @Body request: OptionGroupsDeleteRequest
    ): Completable

    @POST("/options/{optionCd}/mappers/priorities")
    fun changeOptionMapperPriorities(
        @Path("optionCd") optionCd: String,
        @Body request: OptionGroupPrioritiesChangeRequest
    ): Single<Unit>
}