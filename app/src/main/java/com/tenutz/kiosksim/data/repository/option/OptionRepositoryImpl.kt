package com.tenutz.kiosksim.data.repository.option

import com.tenutz.kiosksim.data.datasource.api.SMSApi
import com.tenutz.kiosksim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.kiosksim.data.datasource.api.dto.common.OptionGroupPrioritiesChangeRequest
import com.tenutz.kiosksim.data.datasource.api.dto.common.OptionGroupsDeleteRequest
import com.tenutz.kiosksim.data.datasource.api.dto.common.OptionGroupsMappedByRequest
import com.tenutz.kiosksim.data.datasource.api.dto.kiosk.option.KioskMenuOptionsResponse
import com.tenutz.kiosksim.data.datasource.api.dto.option.*
import com.tenutz.kiosksim.data.datasource.sharedpref.User
import com.tenutz.kiosksim.utils.constant.RetryPolicyConstant
import com.tenutz.kiosksim.utils.ext.applyRetryPolicy
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class OptionRepositoryImpl @Inject constructor(
    private val SMSApi: SMSApi,
) : OptionRepository {

    override fun menuOptions(
        menuCode: String,
        subCategoryCode: String
    ): Single<Result<KioskMenuOptionsResponse>> =
        SMSApi.menuOptions(User.kioskCode, menuCode, subCategoryCode)
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                    RetryPolicyConstant.ACCESS_TOKEN_EXPIRED,
                ) { Result.failure(it) })

    override fun options(commonCond: CommonCondition?): Single<Result<OptionsResponse>> =
        SMSApi.options(commonCond?.query)
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                    RetryPolicyConstant.ACCESS_TOKEN_EXPIRED,
                ) { Result.failure(it) })

    override fun option(optionCd: String): Single<Result<OptionResponse>> =
        SMSApi.option(optionCd)
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                    RetryPolicyConstant.ACCESS_TOKEN_EXPIRED,
                ) { Result.failure(it) })

    override fun createOption(request: OptionCreateRequest): Single<Result<Unit>> =
        SMSApi.createOption(
            request.image,
            request.optionCode,
            request.optionName,
            request.price,
            request.use,
        )
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                    RetryPolicyConstant.ACCESS_TOKEN_EXPIRED,
                ) { Result.failure(it) })

    override fun updateOption(optionCd: String, request: OptionUpdateRequest): Single<Result<Unit>> =
        SMSApi.updateOption(
            optionCd,
            request.image,
            request.optionName,
            request.price,
            request.use,
        )
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                    RetryPolicyConstant.ACCESS_TOKEN_EXPIRED,
                ) { Result.failure(it) })

    override fun deleteOption(optionCd: String): Single<Result<Unit>> =
        SMSApi.deleteOption(optionCd)
            .toSingle { }
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                    RetryPolicyConstant.ACCESS_TOKEN_EXPIRED,
                ) { Result.failure(it) })

    override fun deleteOptions(request: OptionsDeleteRequest): Single<Result<Unit>> =
        SMSApi.deleteOptions(request)
            .toSingle { }
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                    RetryPolicyConstant.ACCESS_TOKEN_EXPIRED,
                ) { Result.failure(it) })

    override fun optionOptionGroups(optionCd: String, commonCond: CommonCondition?): Single<Result<OptionOptionGroupsResponse>> =
        SMSApi.optionOptionGroups(optionCd, commonCond?.query)
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                    RetryPolicyConstant.ACCESS_TOKEN_EXPIRED,
                ) { Result.failure(it) })

    override fun optionMappers(optionCd: String): Single<Result<OptionMappersResponse>> =
        SMSApi.optionMappers(optionCd)
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                    RetryPolicyConstant.ACCESS_TOKEN_EXPIRED,
                ) { Result.failure(it) })

    override fun mapToOptionGroups(
        optionCd: String,
        request: OptionGroupsMappedByRequest
    ): Single<Result<Unit>> =
        SMSApi.mapToOptionGroups(
            optionCd,
            request,
        )
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                    RetryPolicyConstant.ACCESS_TOKEN_EXPIRED,
                ) { Result.failure(it) })

    override fun deleteOptionMappers(
        optionCd: String,
        request: OptionGroupsDeleteRequest
    ): Single<Result<Unit>> =
        SMSApi.deleteOptionMappers(
            optionCd,
            request,
        )
            .toSingle { }
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                    RetryPolicyConstant.ACCESS_TOKEN_EXPIRED,
                ) { Result.failure(it) })

    override fun changeOptionMapperPriorities(
        optionCd: String,
        request: OptionGroupPrioritiesChangeRequest
    ): Single<Result<Unit>> =
        SMSApi.changeOptionMapperPriorities(
            optionCd,
            request,
        )
            .map { Result.success(it) }
            .compose(
                applyRetryPolicy(
                    RetryPolicyConstant.TIMEOUT,
                    RetryPolicyConstant.NETWORK,
                    RetryPolicyConstant.SERVICE_UNAVAILABLE,
                    RetryPolicyConstant.ACCESS_TOKEN_EXPIRED,
                ) { Result.failure(it) })
}