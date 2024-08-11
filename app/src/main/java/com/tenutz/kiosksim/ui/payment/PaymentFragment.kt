package com.tenutz.kiosksim.ui.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tenutz.kiosksim.R
import com.tenutz.kiosksim.databinding.FragmentPaymentBinding
import com.tenutz.kiosksim.ui.base.BaseFragment
import com.tenutz.kiosksim.ui.payment.bs.CardSelectionBottomSheetDialog
import com.tenutz.kiosksim.ui.payment.bs.CouponBottomSheetDialog
import com.tenutz.kiosksim.utils.MyToast
import com.tenutz.kiosksim.utils.ext.observeOnce
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentFragment : BaseFragment() {

    private var _binding: FragmentPaymentBinding? = null
    val binding: FragmentPaymentBinding get() = _binding!!

    private val vm: PaymentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPaymentBinding.inflate(inflater, container, false)

        binding.vm = vm
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
        vm.viewEvent.observeOnce(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                when(it.first) {
                    PaymentViewModel.EVENT_NAVIGATE_TO_COMPLETE -> {
                        findNavController().navigate(PaymentFragmentDirections.actionPaymentFragmentToOrderCompleteFragment())
                    }
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.constraintPaymentCardContainer.setOnClickListener {
            CardSelectionBottomSheetDialog(
                onClickListener = { id, _ ->

                }
            ).show(childFragmentManager, "cardSelectionBottomSheetDialog")
        }
        binding.constraintPaymentCouponContainer.setOnClickListener {
            CouponBottomSheetDialog(
                onClickListener = { id, _ ->
                    when(id) {
                        R.id.btn_bscoupon -> {

                        }
                    }
                }
            ).show(childFragmentManager, "couponBottomSheetDialog")
        }
        binding.btnPayment.setOnClickListener {
            if(!vm.nothingSelected()) {
                vm.createMenusPayments()
            } else {
                MyToast.create(requireContext(), "결제 방식을 선택해주세요.", 80)?.show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}