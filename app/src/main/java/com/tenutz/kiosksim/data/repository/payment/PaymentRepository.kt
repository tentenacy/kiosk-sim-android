package com.tenutz.kiosksim.data.repository.payment

import com.tenutz.kiosksim.data.datasource.api.dto.kiosk.payment.KioskMenuPaymentCreateRequest
import com.tenutz.kiosksim.data.datasource.api.dto.kiosk.payment.KioskMenuPaymentCreateResponse
import io.reactivex.rxjava3.core.Single

interface PaymentRepository {

    fun createMenusPayments(request: KioskMenuPaymentCreateRequest): Single<Result<KioskMenuPaymentCreateResponse>>
}