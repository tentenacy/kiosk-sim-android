package com.tenutz.kiosksim.ui.review.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.rxjava3.cachedIn
import com.orhanobut.logger.Logger
import com.tenutz.kiosksim.data.datasource.api.dto.common.CommonCondition
import com.tenutz.kiosksim.data.datasource.paging.repository.ReviewPagingRepository
import com.tenutz.kiosksim.data.repository.store.StoreRepository
import com.tenutz.kiosksim.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

@HiltViewModel
class MenuReviewsViewModel @Inject constructor(
    private val reviewPagingRepository: ReviewPagingRepository,
    private val storeRepository: StoreRepository,
): BaseViewModel() {

    companion object {
        const val EVENT_REFRESH_MENU_REVIEWS = 1000
    }

    val empty = MutableLiveData(true)

    fun menuReviews() {
        reviewPagingRepository.menuReviews(CommonCondition())
            .observeOn(AndroidSchedulers.mainThread())
            .cachedIn(viewModelScope)
            .subscribe({
                viewEvent(Pair(EVENT_REFRESH_MENU_REVIEWS, it))
            }) {
                Logger.e("$it")
            }.addTo(compositeDisposable)
    }
}