package com.tenutz.kiosksim.ui.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tenutz.kiosksim.R
import com.tenutz.kiosksim.databinding.FragmentPaymentBinding
import com.tenutz.kiosksim.ui.base.BaseFragment
import com.tenutz.kiosksim.ui.payment.bs.CardSelectionBottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentFragment : BaseFragment() {

    private var _binding: FragmentPaymentBinding? = null
    val binding: FragmentPaymentBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPaymentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.constraintPaymentCardContainer.setOnClickListener {
            CardSelectionBottomSheetDialog(
                onClickListener = { id, _ ->
                    when(id) {
                        R.id.constraint_bscard_selection_kukmin -> {

                        }
                    }
                }
            ).show(requireActivity().supportFragmentManager, "cardSelection")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}