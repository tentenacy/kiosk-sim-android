package com.tenutz.kiosksim.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.tenutz.kiosksim.data.datasource.api.dto.kiosk.menu.KioskMenusResponse
import com.tenutz.kiosksim.data.repository.menu.MenuRepository
import com.tenutz.kiosksim.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val menuRepository: MenuRepository,
): BaseViewModel() {

    private val _menus = MutableLiveData<KioskMenusResponse>()
    val menus: LiveData<KioskMenusResponse> = _menus

    init {
        kioskMenus()
    }

    private fun kioskMenus() {
        menuRepository.kioskMenus()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {
                        _menus.value = it
                    },
                    onFailure = {
                        Logger.d(it.localizedMessage?.toString())
                    },
                )
            }
            .addTo(compositeDisposable)
    }
}