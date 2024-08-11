package com.tenutz.kiosksim.utils

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.tenutz.kiosksim.R
import com.tenutz.kiosksim.databinding.ToastBinding
import com.tenutz.kiosksim.utils.ext.toPx

object MyToast {

    fun create(context: Context, message: String, yOffset: Int? = null, transparent: Boolean = true): Toast? {
        val inflater = LayoutInflater.from(context)
        val binding: ToastBinding =
            DataBindingUtil.inflate(inflater, R.layout.toast, null, false)

        binding.textToastContent.text = message

        binding.cardToastContainer.setCardBackgroundColor(ContextCompat.getColor(context, if(transparent) android.R.color.transparent else R.color.white))

        return Toast(context).apply {
            setGravity(Gravity.BOTTOM or Gravity.CENTER, 0, yOffset?.toPx?.toInt() ?: 16.toPx.toInt())
            duration = Toast.LENGTH_SHORT
            view = binding.root
        }
    }
}