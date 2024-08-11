package com.tenutz.kiosksim.ui.review.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tenutz.kiosksim.data.datasource.api.dto.kiosk.menu.KioskReviewMenusResponse
import com.tenutz.kiosksim.databinding.ItemReviewMenusBinding
import com.tenutz.kiosksim.ui.base.BaseRecyclerView
import com.tenutz.kiosksim.ui.base.BaseViewHolder

class ReviewMenusViewHolder(
    val binding: ItemReviewMenusBinding,
    private val onClickListener: (Int, Any?) -> Unit,
): BaseViewHolder<KioskReviewMenusResponse.OrderMenu>(binding.root) {

    override fun bind(position: Int, item: KioskReviewMenusResponse.OrderMenu) {
        binding.args = item
        binding.cardIreviewMenus.setOnClickListener {
            onClickListener(it.id, item)
        }
    }
}

class ReviewMenusAdapter(
    private val onClickListener: (Int, Any?) -> Unit,
): BaseRecyclerView<KioskReviewMenusResponse.OrderMenu, ReviewMenusViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewMenusViewHolder {
        return ReviewMenusViewHolder(ItemReviewMenusBinding.inflate(LayoutInflater.from(parent.context), parent, false), onClickListener)
    }

    override fun getItemId(position: Int): Long {
        return "${items[position].categoryCode}-${items[position].menuCode}".hashCode().toLong()
    }
}