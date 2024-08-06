package com.tenutz.kiosksim.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.tenutz.kiosksim.R
import com.tenutz.kiosksim.databinding.FragmentOrderBinding
import com.tenutz.kiosksim.ui.base.BaseFragment
import com.tenutz.kiosksim.ui.order.bs.ShoppingBagBottomSheetDialog
import com.tenutz.kiosksim.utils.ext.mainActivity
import com.tenutz.kiosksim.utils.ext.navigateToMainFragment
import com.tenutz.kiosksim.utils.ext.observeOnce
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment: BaseFragment() {

    private var _binding: FragmentOrderBinding? = null
    val binding: FragmentOrderBinding get() = _binding!!

    val vm: OrderViewModel by viewModels()

    private val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentOrderBinding.inflate(inflater, container, false)

        binding.vm = vm
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vpagerOrder.registerOnPageChangeCallback(onPageChangeCallback)

        setOnClickListeners()

        vm.menus.observeOnce(viewLifecycleOwner) { menu ->

            if(menu == null) return@observeOnce

            binding.vpagerOrder.adapter = OrderPagerAdapter(
                this@OrderFragment,
                    menu.menusCategories.mapIndexed { index, category ->
                        index to { OrderTabFragment().apply { arguments = Bundle().apply { putParcelable("category", category) } } }
                    }.toMap()
            )


            TabLayoutMediator(binding.tabOrder, binding.vpagerOrder, object : TabLayoutMediator.TabConfigurationStrategy {

                private val categories = menu.menusCategories.mapIndexed { index, category -> index to category }.toMap()

                override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                    tab.text = categories[position]?.categoryName
                }

            }).attach()
        }
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
            ).show(childFragmentManager, "shoppingBagDialog")
        }
        binding.imageOrderHome.setOnClickListener {
            mainActivity().navigateToMainFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}