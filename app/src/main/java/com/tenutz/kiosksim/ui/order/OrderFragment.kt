package com.tenutz.kiosksim.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.tenutz.kiosksim.R
import com.tenutz.kiosksim.databinding.FragmentOrderBinding
import com.tenutz.kiosksim.ui.base.BaseFragment
import com.tenutz.kiosksim.ui.order.bs.ShoppingBagBottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment: BaseFragment() {

    private var _binding: FragmentOrderBinding? = null
    val binding: FragmentOrderBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentOrderBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.fabOrderShoppingBag.setOnClickListener {
            ShoppingBagBottomSheetDialog(
                onClickListener = { id, _ ->
                    when(id) {
                        R.id.btn_bsshopping_bag -> {
                            findNavController().navigate(OrderFragmentDirections.actionOrderFragmentToPaymentFragment())
                        }
                    }
                }
            ).show(childFragmentManager, "shopping bag_dialog")
        }
        binding.imageOrderHome.setOnClickListener {
            findNavController().navigate(OrderFragmentDirections.actionOrderFragmentToOrderPutFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}