package com.tenutz.kiosksim.ui.review

import com.tenutz.kiosksim.data.datasource.paging.repository.ReviewPagingRepository
import com.tenutz.kiosksim.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReviewsViewModel @Inject constructor(
    private val reviewPagingRepository: ReviewPagingRepository,
): BaseViewModel() {


}