package com.tenutz.kiosksim.data.datasource.api

import com.tenutz.kiosksim.data.datasource.api.dto.common.PageResponse
import com.tenutz.kiosksim.data.datasource.api.dto.kiosk.review.KioskMenuReviewsResponse
import com.tenutz.kiosksim.data.datasource.api.dto.kiosk.review.KioskStoreReviewsResponse
import com.tenutz.kiosksim.data.datasource.api.dto.store.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface StoreApi {

    @GET("/stores/sales")
    fun sales(
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("sort") sort: String? = "saleDt,desc",
        @Query("dateFrom") dateFrom: String? = null,
        @Query("dateTo") dateTo: String? = null,
        @Query("query") query: String? = null,
        @Query("queryType") queryType: String? = null,
        @Query("paymentType") paymentType: String? = null,
        @Query("approvalType") approvalType: String? = null,
        @Query("orderType") orderType: String? = null,
    ): Single<PageResponse<SalesResponse>>

    @GET("/stores/sales/total")
    fun salesTotal(
        @Query("dateFrom") dateFrom: String? = null,
        @Query("dateTo") dateTo: String? = null,
        @Query("query") query: String? = null,
        @Query("queryType") queryType: String? = null,
        @Query("paymentType") paymentType: String? = null,
        @Query("approvalType") approvalType: String? = null,
        @Query("orderType") orderType: String? = null,
    ): Single<SalesTotalResponse>

    @GET("/stores/statistics/sales-by-menu")
    fun statisticsSalesByMenu(
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("sort") sort: String? = null,
        @Query("dateFrom") dateFrom: String? = null,
        @Query("dateTo") dateTo: String? = null,
        @Query("query") query: String? = null,
        @Query("queryType") queryType: String? = null,
        @Query("mainCategoryCode") mainCategoryCode: String? = null,
    ): Single<PageResponse<StatisticsSalesByMenusResponse>>

    @GET("/stores/statistics/sales-by-menu/total")
    fun statisticsSalesTotalByMenu(
        @Query("dateFrom") dateFrom: String? = null,
        @Query("dateTo") dateTo: String? = null,
        @Query("query") query: String? = null,
        @Query("queryType") queryType: String? = null,
        @Query("mainCategoryCode") mainCategoryCode: String? = null,
    ): Single<StatisticsSalesTotalByMenusResponse>

    @GET("/stores/statistics/sales-by-menu/today")
    fun statisticsSalesByMenuToday(): Single<StatisticsSalesByMenusTodayResponse>

    @GET("/stores/statistics/sales-by-card")
    fun statisticsSalesByCreditCard(
        @Query("dateFrom") dateFrom: String? = null,
        @Query("dateTo") dateTo: String? = null,
        @Query("query") query: String? = null,
        @Query("queryType") queryType: String? = null,
    ): Single<StatisticsSalesByCreditCardResponse>

    @GET("/stores/statistics/sales-by-card/total")
    fun statisticsSalesTotalByCreditCard(
        @Query("dateFrom") dateFrom: String? = null,
        @Query("dateTo") dateTo: String? = null,
        @Query("query") query: String? = null,
        @Query("queryType") queryType: String? = null,
    ): Single<StatisticsSalesTotalByCreditCardResponse>

    @GET("/stores/statistics/sales-by-time")
    fun statisticsSalesByTime(
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("sort") sort: String? = null,
        @Query("dateFrom") dateFrom: String? = null,
        @Query("dateTo") dateTo: String? = null,
        @Query("query") query: String? = null,
        @Query("queryType") queryType: String? = null,
    ): Single<StatisticsSalesByTimeResponse>

    @GET("/stores/statistics/sales-by-time/total")
    fun statisticsSalesTotalByTime(
        @Query("dateFrom") dateFrom: String? = null,
        @Query("dateTo") dateTo: String? = null,
        @Query("query") query: String? = null,
        @Query("queryType") queryType: String? = null,
    ): Single<StatisticsSalesTotalByTimeResponse>

    @GET("{kioskCode}/reviews")
    fun storeReviews(
        @Path("kioskCode") kioskCode: String,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("sort") sort: String? = "createdDttm,desc",
        @Query("dateFrom") dateFrom: String? = null,
        @Query("dateTo") dateTo: String? = null,
        @Query("query") query: String? = null,
        @Query("queryType") queryType: String? = null,
    ): Single<PageResponse<KioskStoreReviewsResponse>>

    @POST("/stores/reviews/{reviewSeq}/replies")
    fun createStoreReviewReply(
        @Path("reviewSeq") reviewSeq: Long,
        @Body request: ReviewReplyCreateRequest
    ): Single<Unit>

    @PUT("/stores/reviews/replies/{replySeq}")
    fun updateStoreReviewReply(
        @Path("replySeq") replySeq: Long,
        @Body request: ReviewReplyUpdateRequest
    ): Single<Unit>

    @HTTP(method = "DELETE", path = "/stores/reviews/replies/{replySeq}", hasBody = true)
    fun deleteStoreReviewReply(@Path("replySeq") replySeq: Long): Completable

    @GET("{kioskCode}/main-menus/reviews")
    fun menuReviews(
        @Path("kioskCode") kioskCode: String,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("sort") sort: String? = "createdDttm,desc",
        @Query("dateFrom") dateFrom: String? = null,
        @Query("dateTo") dateTo: String? = null,
        @Query("query") query: String? = null,
        @Query("queryType") queryType: String? = null,
    ): Single<PageResponse<KioskMenuReviewsResponse>>

    @POST("/stores/menus/reviews/{reviewSeq}/replies")
    fun createMenuReviewReply(
        @Path("reviewSeq") reviewSeq: Long,
        @Body request: ReviewReplyCreateRequest
    ): Single<Unit>

    @PUT("/stores/menus/reviews/replies/{replySeq}")
    fun updateMenuReviewReply(
        @Path("replySeq") replySeq: Long,
        @Body request: ReviewReplyUpdateRequest
    ): Single<Unit>

    @HTTP(method = "DELETE", path = "/stores/menus/reviews/replies/{replySeq}", hasBody = true)
    fun deleteMenuReviewReply(@Path("replySeq") replySeq: Long): Completable

    @GET("/stores/main")
    fun storeMain(): Single<StoreMainResponse>
}