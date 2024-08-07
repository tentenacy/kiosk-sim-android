package com.tenutz.kiosksim.ui.order.bs

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tenutz.kiosksim.data.datasource.api.dto.kiosk.payment.MenuPayment
import com.tenutz.kiosksim.databinding.ItemBsshoppingBagBinding
import com.tenutz.kiosksim.ui.base.BaseRecyclerView
import com.tenutz.kiosksim.ui.base.BaseViewHolder

class ShoppingBsViewHolder(
    val binding: ItemBsshoppingBagBinding,
    private val onQuantityChangedListener: (String, Int) -> Unit
): BaseViewHolder<MenuPayment>(binding.root) {

    var quantity = 0

    override fun bind(position: Int, item: MenuPayment) {

        quantity = item.quantity

        binding.quantity = quantity
        binding.amount = (item.price + item.optionPrice - item.discountPrice) * quantity
        binding.args = item

        binding.imageBsshoppingBagOneMore.setOnClickListener {
            binding.quantity = ++quantity
            binding.amount = (item.price + item.optionPrice - item.discountPrice) * quantity
            onQuantityChangedListener(item.key, quantity)
        }

        binding.imageBsshoppingBagOneLess.setOnClickListener {
            binding.quantity = --quantity
            binding.amount = (item.price + item.optionPrice - item.discountPrice) * quantity
            onQuantityChangedListener(item.key, quantity)
        }

    }
}

class ShoppingBagBsAdapter(
    private val listener: (String, Int) -> Unit,
): BaseRecyclerView<MenuPayment, ShoppingBsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingBsViewHolder {
        return ShoppingBsViewHolder(ItemBsshoppingBagBinding.inflate(LayoutInflater.from(parent.context), parent, false), listener)
    }

    override fun getItemId(position: Int): Long {
        return "${items[position].key}}".hashCode().toLong()
    }
}