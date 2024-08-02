package com.tenutz.kiosksim.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


abstract class BaseBottomSheetDialogFragment<VB : ViewDataBinding>(private val layoutId: Int) :
    BottomSheetDialogFragment() {

    private var _binding: ViewDataBinding? = null

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = _binding!! as VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate<VB>(inflater, layoutId, container, false)

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        expandFullHeight()
    }

    private fun expandFullHeight() {
        BottomSheetBehavior.from(dialog?.findViewById(R.id.design_bottom_sheet)!!).state =
            BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}