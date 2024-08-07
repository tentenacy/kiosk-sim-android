package com.tenutz.kiosksim.ui.order.bs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.tenutz.kiosksim.R
import com.tenutz.kiosksim.databinding.BsShoppingBagBinding
import com.tenutz.kiosksim.ui.base.HandleDraggableBottomSheetDialogFragment
import com.tenutz.kiosksim.ui.order.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoppingBagBottomSheetDialog(
    private val onClickListener: (Int, Any?) -> Unit,
) : HandleDraggableBottomSheetDialogFragment<BsShoppingBagBinding>(R.layout.bs_shopping_bag) {

    private val pVm: OrderViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    private val adapter: ShoppingBagBsAdapter by lazy {
        ShoppingBagBsAdapter { key, quantity ->
            pVm.updateQuantity(key, quantity)
        }.apply {
            setHasStableIds(true)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = super.onCreateView(inflater, container, savedInstanceState)

        binding.vm = pVm
        binding.lifecycleOwner = viewLifecycleOwner

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setOnClickListeners()
        observeData()
    }

    private fun initViews() {
        binding.recycleBsshoppingBag.adapter = adapter
    }

    private fun observeData() {
        pVm.menusPayments.observe(viewLifecycleOwner) {
            adapter.updateItems(it.values.toList())
        }
        pVm.menusPaymentCount.observe(viewLifecycleOwner) {
            if(it == 0) dismiss()
        }
    }

    private fun setOnClickListeners() {
        binding.btnBsshoppingBag.setOnClickListener { v ->
            onClickListener(v.id, Unit)
            dismiss()
        }
    }
}