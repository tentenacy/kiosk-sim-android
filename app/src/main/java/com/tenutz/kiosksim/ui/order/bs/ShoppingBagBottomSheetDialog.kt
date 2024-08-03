package com.tenutz.kiosksim.ui.order.bs

import android.os.Bundle
import android.view.View
import com.tenutz.kiosksim.R
import com.tenutz.kiosksim.databinding.BsShoppingBagBinding
import com.tenutz.kiosksim.ui.base.HandleDraggableBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoppingBagBottomSheetDialog(
    private val onClickListener: (Int, Any?) -> Unit,
) : HandleDraggableBottomSheetDialogFragment<BsShoppingBagBinding>(R.layout.bs_shopping_bag) {



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.btnBsshoppingBag.setOnClickListener {
        }
    }
}