package com.tenutz.kiosksim.ui.payment.bs

import android.os.Bundle
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.View.OnFocusChangeListener
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.tenutz.kiosksim.R
import com.tenutz.kiosksim.databinding.BsCouponBinding
import com.tenutz.kiosksim.ui.base.HandleDraggableBottomSheetDialogFragment
import com.tenutz.kiosksim.ui.payment.PaymentViewModel
import com.tenutz.kiosksim.utils.MyToast
import com.tenutz.kiosksim.utils.ext.randomNumber
import com.tenutz.kiosksim.utils.ext.toCouponNumber
import com.tenutz.kiosksim.utils.validation.Validator


class CouponBottomSheetDialog(
    private val onClickListener: (Int, Any?) -> Unit,
) : HandleDraggableBottomSheetDialogFragment<BsCouponBinding>(R.layout.bs_coupon) {

    private val pVm: PaymentViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.btnCouponAutoCreate.setOnClickListener { v ->
            binding.editBscoupon.setText((randomNumber(1, ('1'..'9'))+randomNumber(15)).toCouponNumber)
            val spans = binding.editBscoupon.text.getSpans(0, 19, ForegroundColorSpan::class.java)
            for(span in spans) { binding.editBscoupon.text.removeSpan(span) }
            binding.editBscoupon.text.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.rect_4474)),
                0,
                19,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        binding.btnBscoupon.setOnClickListener { v ->
            if(couponCodeCompleted()) {
                Validator.validate(
                    onValidation = {
                        Validator.validateCouponCode(binding.editBscoupon.text.toString(), true)
                    },
                    onSuccess = {
                        pVm.setCouponCode(binding.editBscoupon.text.toString())
                        onClickListener(v.id, null)
                        dismiss()
                    },
                    onFailure = { e ->
                        MyToast.create(requireContext(), e.message.toString(), 80)?.show()
                    },
                )
            } else {
                MyToast.create(requireContext(), "쿠폰번호를 입력해주세요.", 80)?.show()
            }
        }
        binding.editBscoupon.setCursorVisible(false)
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
        }
    }

    private fun couponCodeCompleted(): Boolean {
        val spannableString = binding.editBscoupon.text

        val targetColor = ContextCompat.getColor(requireContext(), R.color.rect_4474)
        val spans = spannableString.getSpans(0, 19, ForegroundColorSpan::class.java)

        var spanStart: Int = -1
        var spanEnd: Int = -1

        for (span in spans) {
            val spanColor = span.foregroundColor
            if (spanColor == targetColor) {
                spanStart = spannableString.getSpanStart(span)
                spanEnd = spannableString.getSpanEnd(span)
            }
        }
        return spanStart == 0 && spanEnd == 19
    }
}