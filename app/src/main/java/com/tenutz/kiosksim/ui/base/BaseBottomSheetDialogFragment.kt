package com.tenutz.kiosksim.ui.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tenutz.kiosksim.R


abstract class BaseBottomSheetDialogFragment<VB : ViewDataBinding>(private val layoutId: Int) :
    BottomSheetDialogFragment() {

    private var _binding: ViewDataBinding? = null

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = _binding!! as VB

    protected val bottomSheet: View by lazy {
        dialog?.findViewById(R.id.design_bottom_sheet)!!
    }

    protected val bottomSheetBehavior: BottomSheetBehavior<View> by lazy {
        BottomSheetBehavior.from(bottomSheet)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate<VB>(inflater, layoutId, container, false)

        binding.lifecycleOwner = this

        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        expandFullHeight()
    }


    private fun expandFullHeight() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}