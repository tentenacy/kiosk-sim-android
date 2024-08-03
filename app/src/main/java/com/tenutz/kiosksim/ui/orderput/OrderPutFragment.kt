package com.tenutz.kiosksim.ui.orderput

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tenutz.kiosksim.databinding.FragmentOrderPutBinding
import com.tenutz.kiosksim.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderPutFragment: BaseFragment() {

    private var _binding: FragmentOrderPutBinding? = null
    val binding: FragmentOrderPutBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentOrderPutBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}