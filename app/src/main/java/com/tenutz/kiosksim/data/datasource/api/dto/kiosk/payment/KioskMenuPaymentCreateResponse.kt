package com.tenutz.kiosksim.data.datasource.api.dto.kiosk.payment

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class KioskMenuPaymentCreateResponse(
    val callNumber: String,
): Parcelable
