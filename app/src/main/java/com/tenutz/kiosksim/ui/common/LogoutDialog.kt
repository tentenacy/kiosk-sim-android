package com.tenutz.kiosksim.ui.common

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import com.tenutz.kiosksim.R
import com.tenutz.kiosksim.databinding.DlgLogoutBinding
import com.tenutz.kiosksim.ui.base.BaseDialogFragment

class LogoutDialog(
    private val logout: () -> Unit
): BaseDialogFragment<DlgLogoutBinding>(R.layout.dlg_logout) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textDlglogoutCancel.setOnClickListener {
            dismiss()
        }
        binding.textDlglogoutLogout.setOnClickListener {
            logout()
            dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}