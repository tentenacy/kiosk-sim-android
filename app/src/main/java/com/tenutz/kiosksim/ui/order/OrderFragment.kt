package com.tenutz.kiosksim.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.orhanobut.logger.Logger
import com.tenutz.kiosksim.R
import com.tenutz.kiosksim.data.datasource.api.dto.kiosk.payment.MenuPayment
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

    private val badgeDrawable: BadgeDrawable by lazy {
        BadgeDrawable.create(mainActivity())
    }

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

        initViews()
        setOnClickListeners()
        observeData()
    }

    private fun initViews() {
        badgeDrawable.horizontalOffset = 56
        badgeDrawable.verticalOffset = 56
        binding.fabOrderShoppingBag.doOnPreDraw {
            BadgeUtils.attachBadgeDrawable(badgeDrawable, binding.fabOrderShoppingBag, null);
        }
    }

    private fun observeData() {

        vm.menusPaymentCount.observe(viewLifecycleOwner) {
            badgeDrawable.number = it
            binding.fabOrderShoppingBag.visibility = if(it > 0) View.VISIBLE else View.INVISIBLE
        }

        vm.menus.observeOnce(viewLifecycleOwner) { menu ->

            if (menu == null) return@observeOnce

            binding.vpagerOrder.adapter = OrderPagerAdapter(
                this@OrderFragment,
                menu.menusCategories.mapIndexed { index, category ->
                    index to {
                        OrderTabFragment().apply {
                            arguments = Bundle().apply { putParcelable("category", category) }
                        }
                    }
                }.toMap()
            )


            TabLayoutMediator(
                binding.tabOrder,
                binding.vpagerOrder,
                object : TabLayoutMediator.TabConfigurationStrategy {

                    private val categories =
                        menu.menusCategories.mapIndexed { index, category -> index to category }.toMap()

                    override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                        tab.text = categories[position]?.categoryName
                    }

                }).attach()
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<MenuPayment>("args")?.observe(viewLifecycleOwner) { menuPayment ->
            if(menuPayment == null) return@observe
            vm.addMenuPayment(menuPayment)
            findNavController().currentBackStackEntry?.savedStateHandle?.set("args", null)
        }

        vm.menusPayments.observe(viewLifecycleOwner) {
            Logger.d("${it}")
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
            )
                .apply { arguments = Bundle().apply { putSerializable("menuPayments", vm.menusPayments.value) } }
                .show(childFragmentManager, "shoppingBagDialog")
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