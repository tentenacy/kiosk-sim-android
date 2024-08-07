package com.tenutz.kiosksim.ui.orderput

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.orhanobut.logger.Logger
import com.tenutz.kiosksim.data.datasource.api.dto.kiosk.option.KioskMenuOptionsResponse
import com.tenutz.kiosksim.data.repository.option.OptionRepository
import com.tenutz.kiosksim.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class OrderPutViewModel @Inject constructor(
    private val optionRepository: OptionRepository,
    savedStateHandle: SavedStateHandle,
): BaseViewModel() {

    private val _options = MutableLiveData<KioskMenuOptionsResponse>()
    val options: LiveData<KioskMenuOptionsResponse> = _options

    val mediatorLiveData = MediatorLiveData<Pair<Int, LinkedHashMap<String, List<KioskMenuOptionsResponse.MenuOptionGroup.OptionGroupOption>>>>()

    private val _checkedOptions = MutableLiveData<LinkedHashMap<String, List<KioskMenuOptionsResponse.MenuOptionGroup.OptionGroupOption>>>(LinkedHashMap())
    val checkedOptions: LiveData<LinkedHashMap<String, List<KioskMenuOptionsResponse.MenuOptionGroup.OptionGroupOption>>> = _checkedOptions

    private val _quantity = MutableLiveData(1)
    val quantity: LiveData<Int> = _quantity

    init {
        mediatorLiveData.addSource(checkedOptions) {
            mediatorLiveData.value = quantity.value!! to checkedOptions.value!!
        }
        mediatorLiveData.addSource(quantity) {
            mediatorLiveData.value = quantity.value!! to checkedOptions.value!!
        }
    }

    fun setOptions(optionGroupCode: String, options: List<KioskMenuOptionsResponse.MenuOptionGroup.OptionGroupOption>) {
        _checkedOptions.value?.put(optionGroupCode, options)
        _checkedOptions.value = _checkedOptions.value
    }

    fun setQuantity(quantity: Int) {
        _quantity.value = quantity
    }

    init {
        OrderPutFragmentArgs.fromSavedStateHandle(savedStateHandle).let {
            menuOptions(it.categoryMenu.menuCode, it.subCategoryCode)
        }
    }

    private fun menuOptions(menuCode: String, subCategoryCode: String) {
        optionRepository.menuOptions(menuCode, subCategoryCode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                result.fold(
                    onSuccess = { options ->
                        options.menuOptionGroups.mapNotNull { optionGroup ->
                            optionGroup.takeIf { it.required }?.optionGroupOptions?.take(1)?.let { _checkedOptions.value?.put(optionGroup.optionGroupCode, it) }
                        }
                        _options.value = options
                    },
                    onFailure = {
                        Logger.d(it.localizedMessage?.toString())
                    },
                )
            }
            .addTo(compositeDisposable)
    }
}