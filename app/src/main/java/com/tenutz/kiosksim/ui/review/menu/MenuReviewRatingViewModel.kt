package com.tenutz.kiosksim.ui.review.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.tenutz.kiosksim.ui.base.BaseViewModel
import com.tenutz.kiosksim.ui.review.menu.args.ReviewMenuArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuReviewRatingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
): BaseViewModel() {

    private val _reviewMenu = MutableLiveData<ReviewMenuArgs>()
    val reviewMenu: LiveData<ReviewMenuArgs> get() = _reviewMenu

    val rating = MutableLiveData(0)

    init {
        _reviewMenu.value = MenuReviewRatingFragmentArgs.fromSavedStateHandle(savedStateHandle).args
    }

    fun setRating(rating: Int) {
        this.rating.value = rating
        _reviewMenu.value?.rating = rating
    }
}