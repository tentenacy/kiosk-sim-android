package com.tenutz.kiosksim.ui.payment

import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.tenutz.kiosksim.utils.watcher.CouponNumberTextWatcher

object PaymentBindingAdapter {

    @JvmStatic
    @BindingAdapter("bind:couponNumberFormat")
    fun setCouponNumberFormat(editText: EditText, ignored: Any?) {
        editText.addTextChangedListener(CouponNumberTextWatcher(editText))
    }
}