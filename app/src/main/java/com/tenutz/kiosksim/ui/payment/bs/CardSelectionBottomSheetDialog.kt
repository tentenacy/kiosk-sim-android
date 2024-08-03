package com.tenutz.kiosksim.ui.payment.bs

import android.os.Bundle
import android.view.View
import com.tenutz.kiosksim.R
import com.tenutz.kiosksim.databinding.BsCardSelectionBinding
import com.tenutz.kiosksim.ui.base.HandleDraggableBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardSelectionBottomSheetDialog(
    private val onClickListener: (Int, Any?) -> Unit,
) : HandleDraggableBottomSheetDialogFragment<BsCardSelectionBinding>(R.layout.bs_card_selection) {



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.constraintBscardSelectionKukmin.setOnClickListener { v ->
            onClickListener(v.id, null)
            dismiss()
        }
    }
}