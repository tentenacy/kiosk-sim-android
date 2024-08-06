package com.tenutz.kiosksim.ui.access

import com.tenutz.kiosksim.data.datasource.api.err.ErrorCode
import com.tenutz.kiosksim.data.datasource.sharedpref.User
import com.tenutz.kiosksim.data.repository.user.UserRepository
import com.tenutz.kiosksim.ui.base.BaseViewModel
import com.tenutz.kiosksim.utils.ext.toErrorResponseOrNull
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class AccessViewModel @Inject constructor(
    private val userRepository: UserRepository,
): BaseViewModel() {

    companion object {
        const val EVENT_NAVIGATE_TO_MAIN = 1000
        const val EVENT_TOAST = 1001
    }

    fun storeExists(kioskCode: String) {
        userRepository.storeExists(kioskCode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = {
                        User.kioskCode = kioskCode
                        viewEvent(EVENT_NAVIGATE_TO_MAIN to Unit)
                    },
                    onFailure = {
                        it.toErrorResponseOrNull()?.let {
                            when(it.code) {
                                ErrorCode.STORE_MASTER_NOT_FOUND.code -> {
                                    viewEvent(EVENT_TOAST to "유효하지 않은 키오스크 코드입니다.")
                                }
                            }
                        }
                    }
                )
            }
            .addTo(compositeDisposable)
    }

}