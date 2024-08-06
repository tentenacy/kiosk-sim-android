package com.tenutz.kiosksim.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.tenutz.kiosksim.data.datasource.api.dto.kiosk.menu.MenusCategory
import com.tenutz.kiosksim.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderTabViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): BaseViewModel() {

    private val _menusCategory = MutableLiveData<MenusCategory>()
    val menusCategory : LiveData<MenusCategory> = _menusCategory

    init {
        savedStateHandle.get<MenusCategory>("category")?.let { _menusCategory.value = it }
    }
}