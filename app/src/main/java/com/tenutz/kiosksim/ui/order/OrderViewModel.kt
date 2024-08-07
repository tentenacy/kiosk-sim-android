package com.tenutz.kiosksim.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.tenutz.kiosksim.data.datasource.api.dto.kiosk.menu.KioskMenusResponse
import com.tenutz.kiosksim.data.datasource.api.dto.kiosk.payment.MenuPayment
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

    private val _menuPayments = MutableLiveData<LinkedHashMap<String, MenuPayment>>(LinkedHashMap())
    val menusPayments: LiveData<LinkedHashMap<String, MenuPayment>> = _menuPayments

    private val _menuPaymentCount = MutableLiveData(0)
    val menusPaymentCount: LiveData<Int> = _menuPaymentCount

    private val _totalAmount = MutableLiveData(0)
    val totalAmount: LiveData<Int> = _totalAmount

    fun updateQuantity(key: String, quantity: Int) {
        if(quantity == 0) _menuPayments.value?.remove(key)
        else _menuPayments.value?.get(key)?.quantity = quantity
        _totalAmount.value = _menuPayments.value?.values?.sumOf { it.totalAmount }
        _menuPayments.value = _menuPayments.value
        _menuPaymentCount.value = menusPayments.value?.values?.count()
    }

    fun addMenuPayment(menuPayment: MenuPayment) {
        _menuPayments.value?.get(menuPayment.key)?.let {
            _menuPayments.value?.put(menuPayment.key, it+menuPayment)
        } ?: _menuPayments.value?.put(menuPayment.key, menuPayment)
        _totalAmount.value = _menuPayments.value?.values?.sumOf { it.totalAmount }
        _menuPaymentCount.value = menusPayments.value?.values?.count()
    }

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