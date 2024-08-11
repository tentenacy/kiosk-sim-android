package com.tenutz.kiosksim.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import com.tenutz.kiosksim.data.datasource.sharedpref.Order
import com.tenutz.kiosksim.data.repository.payment.PaymentRepository
import com.tenutz.kiosksim.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.akarnokd.rxjava3.operators.FlowableTransformers
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.processors.PublishProcessor
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val paymentRepository: PaymentRepository,
) : BaseViewModel() {

    companion object {
        const val EVENT_TOAST = 1000
    }

    private val _timeRemaining = MutableLiveData(0)
    val timeRemaining: LiveData<Int> = _timeRemaining

    private val valve = PublishProcessor.create<Boolean>()

    init {
        Flowable.interval(0, 1000, TimeUnit.MILLISECONDS)
            .compose(FlowableTransformers.valve(valve, true))
            .debounce(10, TimeUnit.MILLISECONDS)
            .map { (5 * 60 * 1000 - (System.currentTimeMillis() - Order.orderedAt) - it).div(1000).toInt() }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                _timeRemaining.value = it
                if(it < 0) Order.clear()
            }.addTo(compositeDisposable)
    }

    fun openValve() {
        valve.onNext(true)
    }

    fun closeValve() {
        valve.onNext(false)
    }

    fun cancelRecentOrder() {
        paymentRepository.deleteMenusPayments(Order.callNumber)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {

                        val callNumber = Order.callNumber

                        _timeRemaining.value = -1
                        Order.clear()

                        viewEvent(EVENT_TOAST to "(주문번호 ${callNumber}번) 주문이 취소되었습니다.")
                    },
                    onFailure = {
                        Logger.e(it.message.toString())
                    },
                )
            }.addTo(compositeDisposable)
    }

}