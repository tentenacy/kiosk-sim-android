package com.tenutz.kiosksim.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.tenutz.kiosksim.databinding.FragmentOrderSelectionBinding
import com.tenutz.kiosksim.ui.base.BaseFragment
import com.tenutz.kiosksim.utils.ext.mainActivity
import com.tenutz.kiosksim.utils.ext.navigateToMainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderSelectionFragment: BaseFragment() {

    private var _binding: FragmentOrderSelectionBinding? = null
    val binding: FragmentOrderSelectionBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentOrderSelectionBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.imageOrderSelectionHome.setOnClickListener {
            mainActivity().navigateToMainFragment()
        }
        binding.constraintOrderSelectionTakeInContainer.setOnClickListener {
            findNavController().navigate(OrderSelectionFragmentDirections.actionOrderSelectionFragmentToOrderFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}