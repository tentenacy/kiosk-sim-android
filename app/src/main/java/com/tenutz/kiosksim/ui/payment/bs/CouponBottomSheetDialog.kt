package com.tenutz.kiosksim.ui.payment.bs

import android.os.Bundle
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.content.ContextCompat
import com.tenutz.kiosksim.R
import com.tenutz.kiosksim.databinding.BsCouponBinding
import com.tenutz.kiosksim.ui.base.HandleDraggableBottomSheetDialogFragment
import com.tenutz.kiosksim.utils.ext.randomNumber
import com.tenutz.kiosksim.utils.ext.toCouponNumber


class CouponBottomSheetDialog(
    private val onClickListener: (Int, Any?) -> Unit,
) : HandleDraggableBottomSheetDialogFragment<BsCouponBinding>(R.layout.bs_coupon) {



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.btnCouponAutoCreate.setOnClickListener { v ->
            binding.editBscoupon.setText((randomNumber(1, ('1'..'9'))+randomNumber(15)).toCouponNumber)
            binding.editBscoupon.text.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.rect_4474)),
                0,
                19,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        binding.btnCoupon.setOnClickListener { v ->
            onClickListener(v.id, null)
            dismiss()
        }
        /*binding.editBscoupon.setCursorVisible(false)
        binding.editBscoupon.setOnClickListener {
            binding.editBscoupon.setSelection(0)
        }
        binding.editBscoupon.onFocusChangeListener = OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                binding.editBscoupon.post(Runnable {
                    binding.editBscoupon.setSelection(0)
                    binding.editBscoupon.setCursorVisible(true)
                })
            } else {
                binding.editBscoupon.setCursorVisible(false)
            }
        }*/
    }
}