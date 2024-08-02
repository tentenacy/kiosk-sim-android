package com.tenutz.kiosksim.utils

import androidx.recyclerview.widget.DiffUtil

class DiffUtilCallback<out T : Any>(
    private val oldList: List<T>,
    private val newList: List<T>,
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return true

        /*return when {
            oldItem is StatisticsSalesByCreditCardResponse.StatisticsSalesByCreditCard && newItem is StatisticsSalesByCreditCardResponse.StatisticsSalesByCreditCard -> {
                oldItem.date == newItem.date &&
                oldItem.creditCardCode == newItem.creditCardCode
            }
            else -> {
                false
            }
        }*/
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}