package com.tenutz.kiosksim.ui.orderput

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.tenutz.kiosksim.data.datasource.api.dto.kiosk.menu.CategoryMenu
import com.tenutz.kiosksim.data.datasource.api.dto.kiosk.option.KioskMenuOptionsResponse
import com.tenutz.kiosksim.databinding.*
import com.tenutz.kiosksim.ui.base.BaseMVHRecyclerView
import com.tenutz.kiosksim.ui.base.BaseViewHolder

sealed class OrderPutItem(val type: OrderPutType) {
    data class Header(val value: CategoryMenu): OrderPutItem(OrderPutType.HEADER)
    data class OptionRequired(val value: KioskMenuOptionsResponse.MenuOptionGroup): OrderPutItem(OrderPutType.OPTION_REQUIRED)
    data class Option(val value: KioskMenuOptionsResponse.MenuOptionGroup): OrderPutItem(OrderPutType.OPTION)
    data object Tail: OrderPutItem(OrderPutType.TAIL)
}

sealed class OrderPutViewHolder(
    binding: ViewDataBinding,
): RecyclerView.ViewHolder(binding.root) {

    class HeaderViewHolder(
        val binding: ItemOrderputHeadBinding,
    ) : BaseViewHolder<CategoryMenu>(binding.root) {

        override fun bind(position: Int, args: CategoryMenu) {
            binding.args = args
        }
    }

    class OptionRequiredViewHolder(
        val binding: ItemOrderputOptionRequiredBinding,
        private val onRequiredCheckedChangedListener: (Pair<String, KioskMenuOptionsResponse.MenuOptionGroup.OptionGroupOption>) -> Unit,
    ) : BaseViewHolder<KioskMenuOptionsResponse.MenuOptionGroup>(binding.root) {


        private val radioButtons = HashMap<String, RadiobuttonBinding>()
        private val optionGroupOptions = HashMap<String, KioskMenuOptionsResponse.MenuOptionGroup.OptionGroupOption>()

        override fun bind(position: Int, item: KioskMenuOptionsResponse.MenuOptionGroup) = binding.root.context.run {

            binding.optionGroupName = item.optionGroupName

            val layoutInflater = LayoutInflater.from(this)

            item.optionGroupOptions?.forEachIndexed { index, optionGroupOption ->

                val radioButtonBinding = RadiobuttonBinding.inflate(layoutInflater).apply {
                    args = optionGroupOption
                }

                if(index == 0) radioButtonBinding.radiobtn.isChecked = true

                optionGroupOptions[optionGroupOption.optionCode] = optionGroupOption
                radioButtonBinding.radiobtn.setOnClickListener { v ->
                    radioButtons.filterKeys { it != optionGroupOption.optionCode }.forEach { t, u -> u.radiobtn.isChecked = false }
                    val checkedOption = optionGroupOptions.getOrDefault(optionGroupOption.optionCode, item.optionGroupOptions[0])
                    onRequiredCheckedChangedListener(item.optionGroupCode to checkedOption)
                }
                binding.linearIorderputOptionRequired.addView(
                    radioButtonBinding.root
                )
                radioButtons[optionGroupOption.optionCode] = radioButtonBinding
            }

            return@run
        }
    }

    class OptionViewHolder(
        val binding: ItemOrderputOptionBinding,
        private val onCheckedChangedListener: (Pair<String, List<KioskMenuOptionsResponse.MenuOptionGroup.OptionGroupOption>>) -> Unit,
    ) : BaseViewHolder<KioskMenuOptionsResponse.MenuOptionGroup>(binding.root) {

        private val optionGroupOptions = HashMap<String, Boolean>()

        override fun bind(position: Int, item: KioskMenuOptionsResponse.MenuOptionGroup) = binding.root.context.run {

            binding.optionGroupName = item.optionGroupName

            val layoutInflater = LayoutInflater.from(this)

            item.optionGroupOptions?.forEach { optionGroupOption ->
                val checkboxBinding = CheckboxBinding.inflate(layoutInflater).apply {
                    args = optionGroupOption
                }
                binding.linearIorderputOption.addView(checkboxBinding.root)
                checkboxBinding.checkbox.setOnClickListener {
                    optionGroupOptions[optionGroupOption.optionCode] = !optionGroupOptions.getOrDefault(optionGroupOption.optionCode, false)
                    val checkedOptions = optionGroupOptions.filter { it.value }.map { optionGroupOption }
                    onCheckedChangedListener(item.optionGroupCode to checkedOptions)
                }
            }

            return@run
        }
    }

    class TailViewHolder(
        val binding: ItemOrderputTailBinding,
        private val onClickListener: (Int, Any?) -> Unit,
        private val onQuantityChangedListener: (Int) -> Unit,
        calculateAmount: ((Int) -> Unit) -> Unit,
    ) : BaseViewHolder<Unit>(binding.root) {

        var quantity = 1

        init {
            calculateAmount { amount ->
                binding.amount = amount
            }
        }

        override fun bind(position: Int, item: Unit) {

            binding.quantity = quantity

            binding.btnIorderputTail.setOnClickListener {
                onClickListener(it.id, quantity)
            }

            binding.imageIorderputTailOneLess.setOnClickListener {
                if (quantity == 1) return@setOnClickListener
                binding.quantity = --quantity
                onQuantityChangedListener(quantity)
            }

            binding.imageIorderputTailOneMore.setOnClickListener {
                binding.quantity = ++quantity
                onQuantityChangedListener(quantity)
            }
        }

    }

}

enum class OrderPutType { HEADER, OPTION_REQUIRED, OPTION, TAIL }

class OrderPutAdapter(
    private val onClickListener: (Int, Any?) -> Unit,
    private val onRequiredCheckedChangedListener: (Pair<String, KioskMenuOptionsResponse.MenuOptionGroup.OptionGroupOption>) -> Unit,
    private val onCheckedChangedListener: (Pair<String, List<KioskMenuOptionsResponse.MenuOptionGroup.OptionGroupOption>>) -> Unit,
    private val onQuantityChangedListener: (Int) -> Unit,
    private val calculateAmount: ((Int) -> Unit) -> Unit,
) : BaseMVHRecyclerView<OrderPutItem, RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_HEADER = 0
        const val VIEW_TYPE_OPTION_REQUIRED = 1
        const val VIEW_TYPE_OPTION = 2
        const val VIEW_TYPE_TAIL = 3
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        items.getOrNull(position)?.let {
            when(holder) {
                is OrderPutViewHolder.HeaderViewHolder -> {
                    holder.bind(position, (it as OrderPutItem.Header).value)
                }
                is OrderPutViewHolder.OptionRequiredViewHolder -> {
                    holder.bind(position, (it as OrderPutItem.OptionRequired).value)
                }
                is OrderPutViewHolder.OptionViewHolder -> {
                    holder.bind(position, (it as OrderPutItem.Option).value)
                }
                is OrderPutViewHolder.TailViewHolder -> {
                    holder.bind(position, Unit)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                OrderPutViewHolder.HeaderViewHolder(
                    ItemOrderputHeadBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    ),
                )
            }
            VIEW_TYPE_OPTION_REQUIRED -> {
                OrderPutViewHolder.OptionRequiredViewHolder(
                    ItemOrderputOptionRequiredBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    onRequiredCheckedChangedListener,
                )
            }
            VIEW_TYPE_OPTION -> {
                OrderPutViewHolder.OptionViewHolder(
                    ItemOrderputOptionBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    onCheckedChangedListener,
                )
            }
            VIEW_TYPE_TAIL -> {
                OrderPutViewHolder.TailViewHolder(
                    ItemOrderputTailBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    ),
                    onClickListener,
                    onQuantityChangedListener,
                    calculateAmount,
                )
            }
            else -> {
                throw RuntimeException("뷰타입이 존재하지 않습니다.")
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        items.getOrNull(position)?.let {
            return when(it.type) {
                OrderPutType.HEADER -> VIEW_TYPE_HEADER
                OrderPutType.OPTION_REQUIRED -> VIEW_TYPE_OPTION_REQUIRED
                OrderPutType.OPTION -> VIEW_TYPE_OPTION
                OrderPutType.TAIL -> VIEW_TYPE_TAIL
            }
        } ?: kotlin.run {
            return VIEW_TYPE_OPTION
        }
    }
}