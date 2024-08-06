package com.tenutz.kiosksim.data.datasource.paging.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.tenutz.kiosksim.data.datasource.api.SMSApi
import com.tenutz.kiosksim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.kiosksim.data.datasource.paging.entity.PushAlarms
import com.tenutz.kiosksim.data.datasource.paging.source.PushAlarmsPagingSource
import com.tenutz.kiosksim.data.datasource.paging.source.mapper.PushAlarmsMapper
import io.reactivex.rxjava3.core.Flowable

class PushAlarmPagingRepositoryImpl(
    private val SMSApi: SMSApi,
    private val mapper: PushAlarmsMapper,
) : PushAlarmPagingRepository {

    override fun pushAlarms(commonCond: CommonCondition): Flowable<PagingData<PushAlarms.PushAlarm>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                prefetchDistance = 5,
                initialLoadSize = 20,
            ),
            pagingSourceFactory = { PushAlarmsPagingSource(SMSApi, mapper, commonCond) }
        ).flowable
    }
}