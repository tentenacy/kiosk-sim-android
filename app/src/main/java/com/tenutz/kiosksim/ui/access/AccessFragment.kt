package com.tenutz.kiosksim.ui.access

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tenutz.kiosksim.databinding.FragmentAccessBinding
import com.tenutz.kiosksim.ui.base.BaseFragment

class AccessFragment : BaseFragment() {

    private var _binding: FragmentAccessBinding? = null
    val binding: FragmentAccessBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAccessBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}