package com.tenutz.kiosksim.data.repository.help

import com.tenutz.kiosksim.data.datasource.api.SMSApi
import com.tenutz.kiosksim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.kiosksim.data.datasource.api.dto.help.HelpsResponse
import com.tenutz.kiosksim.utils.constant.RetryPolicyConstant
import com.tenutz.kiosksim.utils.ext.applyRetryPolicy
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class HelpRepositoryImpl @Inject constructor(
    private val SMSApi: SMSApi,
): HelpRepository {

    override fun helps(commonCond: CommonCondition?): Single<Result<HelpsResponse>> =
        SMSApi.helps(commonCond?.query)
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                    RetryPolicyConstant.ACCESS_TOKEN_EXPIRED,
                ) { Result.failure(it) })
}