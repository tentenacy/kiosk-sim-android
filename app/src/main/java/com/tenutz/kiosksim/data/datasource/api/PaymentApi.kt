package com.tenutz.kiosksim.data.datasource.api

import com.tenutz.kiosksim.data.datasource.api.dto.kiosk.payment.KioskMenuPaymentCreateRequest
import com.tenutz.kiosksim.data.datasource.api.dto.kiosk.payment.KioskMenuPaymentCreateResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface PaymentApi {

    @POST("{kioskCode}/users/main-menus/payments")
    fun createMenusPayments(
        @Path("kioskCode") kioskCode: String,
        @Body request: KioskMenuPaymentCreateRequest
    ): Single<KioskMenuPaymentCreateResponse>
}