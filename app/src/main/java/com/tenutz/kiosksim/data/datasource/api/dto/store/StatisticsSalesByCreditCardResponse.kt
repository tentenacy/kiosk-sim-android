package com.tenutz.kiosksim.data.datasource.api.dto.store

import java.util.*

data class StatisticsSalesByCreditCardResponse(
    val contents: List<StatisticsSalesByCreditCard>,
) {
    data class StatisticsSalesByCreditCard(
        val date: Date?,
        val creditCardCode: String?,
        val creditCardCompany: String?,
        val salesAmount: Int,
        val salesCount: Int,
        val creditCardSalesAmount: Int,
        val creditCardSalesCount: Int,
        val creditCardSalesCancelAmount: Int,
        val creditCardSalesCancelCount: Int,
    )
}
