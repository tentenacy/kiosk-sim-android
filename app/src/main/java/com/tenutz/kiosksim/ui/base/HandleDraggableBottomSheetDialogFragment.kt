package com.tenutz.kiosksim.ui.base

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.tenutz.kiosksim.R


abstract class HandleDraggableBottomSheetDialogFragment<VB : ViewDataBinding>(layoutId: Int) :
    BaseBottomSheetDialogFragment<VB>(layoutId) {

    companion object {
        private const val SCROLL_VERTICAL_RATIO = 0.75
    }

    private var lastTouchY: Float = 0f

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        makeDraggableByHandle(view)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun makeDraggableByHandle(view: View) {
        //common resource id
        val dragHandle = view.findViewById<View>(R.id.constraint_drag_handle_container)

        bottomSheetBehavior.isDraggable = false

        dragHandle.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    lastTouchY = event.rawY
                    true
                }

                MotionEvent.ACTION_MOVE -> {
                    val deltaY = event.rawY - lastTouchY

                    val maxY = binding.root.height.toFloat() + bottomSheet.top.toFloat()
                    val minY = bottomSheet.top.toFloat()

                    if (bottomSheet.y + deltaY in minY..maxY) {
                        bottomSheet.y += deltaY
                    }

                    lastTouchY = event.rawY

                    true
                }

                MotionEvent.ACTION_UP -> {
                    if (bottomSheet.y < binding.root.height.toFloat() * (1 - SCROLL_VERTICAL_RATIO) + bottomSheet.top) {
                        ObjectAnimator.ofFloat(bottomSheet, "translationY", 0f).apply {
                            duration = 50
                            start()
                        }
                    } else {
                        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                    }

                    true
                }

                else -> false
            }
        }

    }
}