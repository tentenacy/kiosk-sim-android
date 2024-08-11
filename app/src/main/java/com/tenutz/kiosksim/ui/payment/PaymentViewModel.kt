package com.tenutz.kiosksim.ui.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.orhanobut.logger.Logger
import com.tenutz.kiosksim.data.datasource.api.dto.kiosk.payment.KioskMenuPaymentCreateRequest
import com.tenutz.kiosksim.data.datasource.sharedpref.Order
import com.tenutz.kiosksim.data.repository.payment.PaymentRepository
import com.tenutz.kiosksim.ui.base.BaseViewModel
import com.tenutz.kiosksim.ui.order.args.KioskMenuPaymentsArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val paymentRepository: PaymentRepository,
    savedStateHandle: SavedStateHandle,
): BaseViewModel() {

    companion object {
        const val EVENT_NAVIGATE_TO_COMPLETE = 1000
    }

    private val _menuPayments = MutableLiveData<KioskMenuPaymentsArgs>()
    val menuPayments: LiveData<KioskMenuPaymentsArgs> = _menuPayments

    private val _creditCardCompanyCode = MutableLiveData<String>("")
    val creditCardCompanyCode: LiveData<String> = _creditCardCompanyCode

    private val _creditCardCompanyName = MutableLiveData<String>("")
    val creditCardCompanyName: LiveData<String> = _creditCardCompanyName

    private val _cashPaid = MutableLiveData<Boolean>(false)
    val cashPaid: LiveData<Boolean> = _cashPaid

    private val _couponCode = MutableLiveData<String>("")
    val couponCode: LiveData<String> = _couponCode

    fun setCreditCardCompanyInfo(code: String, name: String) {
        _creditCardCompanyCode.value = code
        _creditCardCompanyName.value = name

        _cashPaid.value = false
        _couponCode.value = ""
    }

    fun cashPaid() {
        _cashPaid.value = true

        _creditCardCompanyCode.value = ""
        _creditCardCompanyName.value = ""
        _couponCode.value = ""
    }

    fun setCouponCode(code: String) {
        _couponCode.value = code

        _creditCardCompanyCode.value = ""
        _creditCardCompanyName.value = ""
        _cashPaid.value = false
    }

    fun nothingSelected() = _creditCardCompanyCode.value.isNullOrBlank() && _couponCode.value.isNullOrBlank() && _cashPaid.value == false

    init {
        _menuPayments.value = PaymentFragmentArgs.fromSavedStateHandle(savedStateHandle).menuPayments
        Logger.d("${_menuPayments.value}")
    }

    fun createMenusPayments() {

        val paymentCode = if(!_creditCardCompanyCode.value.isNullOrBlank()) {
            "02"
        } else if(!_couponCode.value.isNullOrBlank()) {
            "03"
        } else {
            "01"
        }

        paymentRepository.createMenusPayments(
            KioskMenuPaymentCreateRequest(
                menuPayments.value!!.menuPayments,
                menuPayments.value!!.mainCategoryCode,
                menuPayments.value!!.middleCategoryCode,
                menuPayments.value!!.orderType,
                paymentCode,
                _creditCardCompanyCode.value,
                menuPayments.value!!.totalAmount,
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = { response ->
                        Order.callNumber = response.callNumber
                        Order.orderedAt = System.currentTimeMillis()
                        viewEvent(EVENT_NAVIGATE_TO_COMPLETE to Unit)
                    },
                    onFailure = {
                        Logger.e("${it.message}")
                    },
                )
            }.addTo(compositeDisposable)
    }
}