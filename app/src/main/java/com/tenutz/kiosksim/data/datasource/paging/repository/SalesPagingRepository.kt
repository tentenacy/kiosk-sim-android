package com.tenutz.kiosksim.data.datasource.paging.repository

import androidx.paging.PagingData
import com.tenutz.kiosksim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.kiosksim.data.datasource.api.dto.store.SalesRequest
import com.tenutz.kiosksim.data.datasource.paging.entity.MenuSalesList
import com.tenutz.kiosksim.data.datasource.paging.entity.SalesList
import io.reactivex.rxjava3.core.Flowable

interface SalesPagingRepository {

    fun salesList(commonCond: CommonCondition, request: SalesRequest): Flowable<PagingData<SalesList.Sales>>

    fun menuSalesList(commonCond: CommonCondition): Flowable<PagingData<MenuSalesList.MenuSales>>
}