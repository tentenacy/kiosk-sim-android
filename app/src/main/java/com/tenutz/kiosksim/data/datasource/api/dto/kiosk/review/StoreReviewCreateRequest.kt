﻿package com.tenutz.kiosksim.data.datasource.api.dto.kiosk.review

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoreReviewCreateRequest(
    val name: String,
    val contents: String,
    val rating: Int,
): Parcelable
