package com.tenutz.kiosksim.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.tenutz.kiosksim.data.datasource.sharedpref.Settings
import com.tenutz.kiosksim.databinding.FragmentMainBinding
import com.tenutz.kiosksim.ui.base.BaseFragment
import com.tenutz.kiosksim.ui.common.QuitDialog
import com.tenutz.kiosksim.ui.main.dlg.OrderCancelDialog
import com.tenutz.kiosksim.utils.MyToast
import com.tenutz.kiosksim.utils.ext.mainActivity
import com.tenutz.kiosksim.utils.ext.navigateToAccessFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment() {

    private var _binding: FragmentMainBinding? = null
    val binding: FragmentMainBinding get() = _binding!!

    private val vm: MainViewModel by viewModels()

    private val badgeDrawable: BadgeDrawable by lazy {
        BadgeDrawable.create(mainActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        observeData()
        setOnClickListeners()
    }

    private fun observeData() {
        vm.timeRemaining.observe(viewLifecycleOwner) {
            badgeDrawable.number = it
            binding.fabMainCancelOrder.visibility = if (it > 0) View.VISIBLE else View.INVISIBLE
        }
        vm.viewEvent.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                when (it.first) {
                    MainViewModel.EVENT_TOAST -> {
                        val message = it.second as String
                        MyToast.create(mainActivity(), message, 80, false)?.show()
                    }
                }
            }
        }
    }

    private fun initViews() {
        badgeDrawable.horizontalOffset = 56
        badgeDrawable.verticalOffset = 56
        binding.fabMainCancelOrder.doOnPreDraw {
            BadgeUtils.attachBadgeDrawable(badgeDrawable, binding.fabMainCancelOrder, null);
        }
    }

    private fun setOnClickListeners() {
        binding.imageMainQuit.setOnClickListener {
            QuitDialog {
                Settings.autoEntered = false
                mainActivity().navigateToAccessFragment()
            }.show(childFragmentManager, "quitDialog")
        }
        binding.constraintMainOrderContainer.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToOrderSelectionFragment())
        }
        binding.constraintMainReviewContainer.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToReviewsFragment())
        }
        binding.fabMainCancelOrder.setOnClickListener {
            OrderCancelDialog {
                vm.cancelRecentOrder()
            }.show(childFragmentManager, "orderCancelDialog")
        }
    }

    override fun onResume() {
        super.onResume()
        vm.openValve()
    }

    override fun onPause() {
        super.onPause()
        vm.closeValve()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}