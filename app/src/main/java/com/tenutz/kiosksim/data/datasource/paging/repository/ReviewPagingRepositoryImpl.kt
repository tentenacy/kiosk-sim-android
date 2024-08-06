package com.tenutz.kiosksim.data.datasource.paging.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.tenutz.kiosksim.data.datasource.api.SMSApi
import com.tenutz.kiosksim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.kiosksim.data.datasource.paging.entity.MenuReviews
import com.tenutz.kiosksim.data.datasource.paging.entity.StoreReviews
import com.tenutz.kiosksim.data.datasource.paging.source.MenuReviewsPagingSource
import com.tenutz.kiosksim.data.datasource.paging.source.StoreReviewsPagingSource
import com.tenutz.kiosksim.data.datasource.paging.source.mapper.ReviewsMapper
import io.reactivex.rxjava3.core.Flowable

class ReviewPagingRepositoryImpl(
    private val SMSApi: SMSApi,
    private val mapper: ReviewsMapper,
): ReviewPagingRepository {

    override fun storeReviews(commonCond: CommonCondition): Flowable<PagingData<StoreReviews.StoreReview>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                prefetchDistance = 5,
                initialLoadSize = 20,
            ),
            pagingSourceFactory = { StoreReviewsPagingSource(SMSApi, mapper, commonCond) }
        ).flowable
    }

    override fun menuReviews(commonCond: CommonCondition): Flowable<PagingData<MenuReviews.MenuReview>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                prefetchDistance = 5,
                initialLoadSize = 20,
            ),
            pagingSourceFactory = { MenuReviewsPagingSource(SMSApi, mapper, commonCond) }
        ).flowable
    }
}