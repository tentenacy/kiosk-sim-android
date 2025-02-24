package com.tenutz.kiosksim.data.datasource.api

import com.tenutz.kiosksim.data.datasource.api.dto.category.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import retrofit2.http.*

interface CategoryApi {


    @GET("/categories/main")
    fun mainCategories(
        @Query("query") query: String? = null,
    ): Single<MainCategoriesResponse>

    @GET("/categories/main/{mainCateCd}")
    fun mainCategory(
        @Path("mainCateCd") mainCateCd: String,
    ): Single<MainCategoryResponse>

    @POST("/categories/main")
    fun createMainCategory(
        @Body request: MainCategoryCreateRequest,
    ): Single<Unit>

    @PUT("/categories/main/{mainCateCd}")
    fun updateMainCategory(
        @Path("mainCateCd") mainCateCd: String,
        @Body request: MainCategoryUpdateRequest,
    ): Single<Unit>

    @HTTP(method = "DELETE", path = "/categories/main/{mainCateCd}", hasBody = true)
    fun deleteMainCategory(
        @Path("mainCateCd") mainCateCd: String,
    ): Completable

    @HTTP(method = "DELETE", path = "/categories/main", hasBody = true)
    fun deleteMainCategories(
        @Body request: CategoriesDeleteRequest,
    ): Completable

    @POST("/categories/main/priorities")
    fun changeMainCategoryPriorities(
        @Body request: CategoryPrioritiesChangeRequest,
    ): Single<Unit>

    @GET("/categories/main/{mainCateCd}/middle")
    fun middleCategories(
        @Path("mainCateCd") mainCateCd: String,
    ): Single<MiddleCategoriesResponse>

    @GET("/categories/main/{mainCateCd}/middle/{middleCateCd}")
    fun middleCategory(
        @Path("mainCateCd") mainCateCd: String,
        @Path("middleCateCd") middleCateCd: String,
    ): Single<MiddleCategoryResponse>

    @Multipart
    @POST("/categories/main/{mainCateCd}/middle")
    fun createMiddleCategory(
        @Path("mainCateCd") mainCateCd: String,
        @Part image: MultipartBody.Part? = null,
        @Part("categoryCode") categoryCode: String,
        @Part("categoryName") categoryName: String,
        @Part("use") use: Boolean,
        @Part("businessNumber") businessNumber: String? = null,
        @Part("representativeName") representativeName: String? = null,
        @Part("tel") tel: String? = null,
        @Part("address") address: String? = null,
        @Part("tid") tid: String? = null,
    ): Single<Unit>

    @Multipart
    @PUT("/categories/main/{mainCateCd}/middle/{middleCateCd}")
    fun updateMiddleCategory(
        @Path("mainCateCd") mainCateCd: String,
        @Path("middleCateCd") middleCateCd: String,
        @Part image: MultipartBody.Part? = null,
        @Part("categoryName") categoryName: String,
        @Part("use") use: Boolean,
        @Part("businessNumber") businessNumber: String? = null,
        @Part("representativeName") representativeName: String? = null,
        @Part("tel") tel: String? = null,
        @Part("address") address: String? = null,
        @Part("tid") tid: String? = null,
    ): Single<Unit>

    @HTTP(method = "DELETE", path = "/categories/main/{mainCateCd}/middle/{middleCateCd}", hasBody = true)
    fun deleteMiddleCategory(
        @Path("mainCateCd") mainCateCd: String,
        @Path("middleCateCd") middleCateCd: String,
    ): Completable

    @HTTP(method = "DELETE", path = "/categories/main/{mainCateCd}/middle", hasBody = true)
    fun deleteMiddleCategories(
        @Path("mainCateCd") mainCateCd: String,
        @Body request: CategoriesDeleteRequest,
    ): Completable

    @POST("/categories/main/{mainCateCd}/middle/priorities")
    fun changeMiddleCategoryPriorities(
        @Path("mainCateCd") mainCateCd: String,
        @Body request: CategoryPrioritiesChangeRequest,
    ): Single<Unit>

    @GET("/categories/main/{mainCateCd}/middle/{middleCateCd}/sub")
    fun subCategories(
        @Path("mainCateCd") mainCateCd: String,
        @Path("middleCateCd") middleCateCd: String,
    ): Single<SubCategoriesResponse>

    @GET("/categories/main/{mainCateCd}/middle/{middleCateCd}/sub/{subCateCd}")
    fun subCategory(
        @Path("mainCateCd") mainCateCd: String,
        @Path("middleCateCd") middleCateCd: String,
        @Path("subCateCd") subCateCd: String,
    ): Single<SubCategoryResponse>

    @POST("/categories/main/{mainCateCd}/middle/{middleCateCd}/sub")
    fun createSubCategory(
        @Path("mainCateCd") mainCateCd: String,
        @Path("middleCateCd") middleCateCd: String,
        @Body request: SubCategoryCreateRequest,
    ): Single<Unit>

    @PUT("/categories/main/{mainCateCd}/middle/{middleCateCd}/sub/{subCateCd}")
    fun updateSubCategory(
        @Path("mainCateCd") mainCateCd: String,
        @Path("middleCateCd") middleCateCd: String,
        @Path("subCateCd") subCateCd: String,
        @Body request: SubCategoryUpdateRequest,
    ): Single<Unit>

    @HTTP(method = "DELETE", path = "/categories/main/{mainCateCd}/middle/{middleCateCd}/sub/{subCateCd}", hasBody = true)
    fun deleteSubCategory(
        @Path("mainCateCd") mainCateCd: String,
        @Path("middleCateCd") middleCateCd: String,
        @Path("subCateCd") subCateCd: String,
    ): Completable

    @HTTP(method = "DELETE", path = "/categories/main/{mainCateCd}/middle/{middleCateCd}/sub", hasBody = true)
    fun deleteSubCategories(
        @Path("mainCateCd") mainCateCd: String,
        @Path("middleCateCd") middleCateCd: String,
        @Body request: CategoriesDeleteRequest,
    ): Completable

    @POST("/categories/main/{mainCateCd}/middle/{middleCateCd}/sub/priorities")
    fun changeSubCategoryPriorities(
        @Path("mainCateCd") mainCateCd: String,
        @Path("middleCateCd") middleCateCd: String,
        @Body request: CategoryPrioritiesChangeRequest,
    ): Single<Unit>

}