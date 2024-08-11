package com.tenutz.kiosksim.ui.review.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.tenutz.kiosksim.data.datasource.api.dto.kiosk.menu.KioskReviewMenusResponse
import com.tenutz.kiosksim.data.datasource.sharedpref.Order
import com.tenutz.kiosksim.data.repository.menu.MenuRepository
import com.tenutz.kiosksim.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class ReviewMenusViewModel @Inject constructor(
    private val menusRepository: MenuRepository,
): BaseViewModel() {

    private val _reviewMenus = MutableLiveData<KioskReviewMenusResponse>()
    val reviewMenus: LiveData<KioskReviewMenusResponse> get() = _reviewMenus

    val empty = MutableLiveData(true)

    init {
        reviewMenus()
    }

    private fun reviewMenus() {
        menusRepository.orderMenus(Order.callNumber)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {
                        _reviewMenus.value = it
                        empty.value = it.orderMenus.isEmpty()
                    },
                    onFailure = {
                        Logger.e("${it.message}")
                    },
                )
            }.addTo(compositeDisposable)
    }
}