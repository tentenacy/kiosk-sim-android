package com.tenutz.kiosksim.ui.review.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.orhanobut.logger.Logger
import com.tenutz.kiosksim.data.datasource.api.dto.kiosk.review.KioskMenuReviewCreateRequest
import com.tenutz.kiosksim.data.repository.menu.MenuRepository
import com.tenutz.kiosksim.ui.base.BaseViewModel
import com.tenutz.kiosksim.ui.review.menu.args.ReviewMenuArgs
import com.tenutz.kiosksim.utils.ext.toErrorResponseOrNull
import com.tenutz.kiosksim.utils.validation.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MenuReviewContentViewModel @Inject constructor(
    private val menuRepository: MenuRepository,
    savedStateHandle: SavedStateHandle,
): BaseViewModel() {

    companion object {
        const val EVENT_TOAST = 1000
    }

    private val _reviewMenu = MutableLiveData<ReviewMenuArgs>()
    val reviewMenu: LiveData<ReviewMenuArgs> get() = _reviewMenu

    val writer = MutableLiveData("")
    val content = MutableLiveData("")

    val writeEnabled = MediatorLiveData<Boolean>(false)

    init {
        writeEnabled.addSource(writer) {
            writeEnabled.value = !(writer.value.isNullOrEmpty() || content.value.isNullOrEmpty())
        }
        writeEnabled.addSource(content) {
            writeEnabled.value = !(writer.value.isNullOrEmpty() || content.value.isNullOrEmpty())
        }
    }

    init {
        _reviewMenu.value = MenuReviewRatingFragmentArgs.fromSavedStateHandle(savedStateHandle).args
    }

    fun autoCreateReviewContent() {

    }

    fun createMenuReview() {

        Validator.validate(
            onValidation = {

            },
            onSuccess = {
                menuRepository.createMenuReview(
                    KioskMenuReviewCreateRequest(
                        _reviewMenu.value!!.mainCategoryCode,
                        _reviewMenu.value!!.middleCategoryCode,
                        _reviewMenu.value!!.categoryCode,
                        _reviewMenu.value!!.menuCode,
                        writer.value!!,
                        content.value!!,
                        _reviewMenu.value!!.rating,
                    )
                )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { result ->
                        result.fold(
                            onSuccess = {

                            },
                            onFailure = {
                                Logger.e(it.toErrorResponseOrNull().toString())
                            },
                        )
                    }.addTo(compositeDisposable)
            },
            onFailure = {
                viewEvent(EVENT_TOAST to it.errorCode.message)
            },
        )
    }

}