package com.tenutz.kiosksim.data.repository.help

import com.tenutz.kiosksim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.kiosksim.data.datasource.api.dto.help.HelpsResponse
import io.reactivex.rxjava3.core.Single

interface HelpRepository {

    fun helps(commonCond: CommonCondition? = null): Single<Result<HelpsResponse>>
}