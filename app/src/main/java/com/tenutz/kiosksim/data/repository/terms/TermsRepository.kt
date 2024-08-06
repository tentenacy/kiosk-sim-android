package com.tenutz.kiosksim.data.repository.terms

import com.tenutz.kiosksim.data.datasource.api.dto.terms.TermsDetailsResponse
import com.tenutz.kiosksim.data.datasource.api.dto.terms.TermsResponse
import io.reactivex.rxjava3.core.Single

interface TermsRepository {

    fun terms(): Single<Result<TermsResponse>>

    fun termsDetails(termsCd: String): Single<Result<TermsDetailsResponse>>
}