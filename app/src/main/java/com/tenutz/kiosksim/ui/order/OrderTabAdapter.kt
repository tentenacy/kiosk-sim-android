package com.tenutz.kiosksim.ui.order

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tenutz.kiosksim.data.datasource.api.dto.kiosk.menu.CategoryMenu
import com.tenutz.kiosksim.databinding.ItemTorderMenusBinding
import com.tenutz.kiosksim.ui.base.BaseRecyclerView
import com.tenutz.kiosksim.ui.base.BaseViewHolder

class OrderTabViewHolder(val binding: ItemTorderMenusBinding, private val listener: (CategoryMenu) -> Unit): BaseViewHolder<CategoryMenu>(binding.root) {

    override fun bind(position: Int, item: CategoryMenu) {
        binding.args = item
        binding.constraintItorderMenusContainer.setOnClickListener {
            listener(item)
        }
    }
}

class OrderTabAdapter(private val listener: (CategoryMenu) -> Unit): BaseRecyclerView<CategoryMenu, OrderTabViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderTabViewHolder {
        return OrderTabViewHolder(ItemTorderMenusBinding.inflate(LayoutInflater.from(parent.context), parent, false), listener)
    }

    override fun getItemId(position: Int): Long {
        return "${items[position].menuCode}}".hashCode().toLong()
    }
}