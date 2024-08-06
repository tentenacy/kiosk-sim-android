package com.tenutz.kiosksim.ui.order

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class OrderPagerAdapter(fragment: Fragment, private val tabFragmentCreators: Map<Int, () -> Fragment>): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = tabFragmentCreators.size
    override fun createFragment(position: Int): Fragment = tabFragmentCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
}