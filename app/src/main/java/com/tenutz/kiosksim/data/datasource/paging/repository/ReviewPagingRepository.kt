package com.tenutz.kiosksim.data.datasource.paging.repository

import androidx.paging.PagingData
import com.tenutz.kiosksim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.kiosksim.data.datasource.paging.entity.MenuReviews
import com.tenutz.kiosksim.data.datasource.paging.entity.StoreReviews
import io.reactivex.rxjava3.core.Flowable

interface ReviewPagingRepository {
    fun storeReviews(commonCond: CommonCondition): Flowable<PagingData<StoreReviews.StoreReview>>
    fun menuReviews(commonCond: CommonCondition): Flowable<PagingData<MenuReviews.MenuReview>>
}