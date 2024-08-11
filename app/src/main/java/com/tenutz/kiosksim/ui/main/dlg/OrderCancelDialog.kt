package com.tenutz.kiosksim.ui.main.dlg

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import com.tenutz.kiosksim.R
import com.tenutz.kiosksim.databinding.DlgOrderCancelBinding
import com.tenutz.kiosksim.ui.base.BaseDialogFragment

class OrderCancelDialog(
    private val cancelOrder: () -> Unit
): BaseDialogFragment<DlgOrderCancelBinding>(R.layout.dlg_order_cancel) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.textDlgorderCancelCancel.setOnClickListener {
            dismiss()
        }
        binding.textDlgorderCancel.setOnClickListener {
            cancelOrder()
            dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}