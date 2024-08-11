package com.tenutz.kiosksim.data.repository.payment

import com.tenutz.kiosksim.data.datasource.api.SMSApi
import com.tenutz.kiosksim.data.datasource.api.dto.kiosk.payment.KioskMenuPaymentCreateRequest
import com.tenutz.kiosksim.data.datasource.api.dto.kiosk.payment.KioskMenuPaymentCreateResponse
import com.tenutz.kiosksim.data.datasource.sharedpref.User
import com.tenutz.kiosksim.utils.constant.RetryPolicyConstant
import com.tenutz.kiosksim.utils.ext.applyRetryPolicy
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(
    private val SMSApi: SMSApi,
): PaymentRepository {

    override fun createMenusPayments(request: KioskMenuPaymentCreateRequest): Single<Result<KioskMenuPaymentCreateResponse>> =
        SMSApi.createMenusPayments(User.kioskCode, request)
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                    RetryPolicyConstant.ACCESS_TOKEN_EXPIRED,
                ) { Result.failure(it) })

    override fun deleteMenusPayments(callNumber: String): Single<Result<Unit>> =
        SMSApi.deleteMenusPayments(User.kioskCode, callNumber.takeUnless { it.isEmpty() } ?: "0000")
            .toSingle {  }
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                    RetryPolicyConstant.ACCESS_TOKEN_EXPIRED,
                ) { Result.failure(it) })
}