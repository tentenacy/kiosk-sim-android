package com.tenutz.kiosksim.ui.common

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import com.tenutz.kiosksim.R
import com.tenutz.kiosksim.databinding.DlgQuitBinding
import com.tenutz.kiosksim.ui.base.BaseDialogFragment

class QuitDialog(
    private val logout: () -> Unit
): BaseDialogFragment<DlgQuitBinding>(R.layout.dlg_quit) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textDlgquitCancel.setOnClickListener {
            dismiss()
        }
        binding.textDlgquitQuit.setOnClickListener {
            logout()
            dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}