package com.tenutz.kiosksim.utils

interface OnDragListener<VH> {
    fun onDragStart(holder: VH)
    fun onDragOver()
}