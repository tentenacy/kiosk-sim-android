package com.tenutz.kiosksim.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.DEFAULT_ARGS_KEY
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.navigation.fragment.findNavController
import com.tenutz.kiosksim.data.datasource.api.dto.kiosk.menu.MenusCategory
import com.tenutz.kiosksim.databinding.TabOrderMenusBinding
import com.tenutz.kiosksim.ui.base.BaseFragment
import com.tenutz.kiosksim.utils.ext.observeOnce
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderTabFragment: BaseFragment() {

    private var _binding: TabOrderMenusBinding? = null
    val binding: TabOrderMenusBinding get() = _binding!!

    val vm: OrderTabViewModel by viewModels(
        extrasProducer = {
            MutableCreationExtras(defaultViewModelCreationExtras).apply { set(DEFAULT_ARGS_KEY, arguments ?: Bundle()) }
        }
    )
    val pVm: OrderViewModel by viewModels(
        ownerProducer = { requireParentFragment() },
    )

    private val adapter: OrderTabAdapter by lazy {
        OrderTabAdapter {
            findNavController().navigate(
                OrderFragmentDirections.actionOrderFragmentToOrderPutFragment(
                    arguments!!.getParcelable<MenusCategory>("category")!!.categoryCode,
                    it
                ))
        }.apply {
            setHasStableIds(true)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = TabOrderMenusBinding.inflate(inflater, container, false)

        binding.vm = vm
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setOnClickListeners()
        observeData()
    }


    private fun initViews() {
        binding.recyclerOrderMenus.adapter = adapter
    }

    private fun observeData() {

        vm.menusCategory.observeOnce(viewLifecycleOwner) {
            if(it == null) return@observeOnce
            adapter.updateItems(it.categoryMenus)
            binding.recyclerOrderMenus.scrollToPosition(0)
        }
    }

    private fun setOnClickListeners() {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}