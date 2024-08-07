package com.tenutz.kiosksim.ui.orderput

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tenutz.kiosksim.R
import com.tenutz.kiosksim.data.datasource.api.dto.kiosk.payment.MenuPayment
import com.tenutz.kiosksim.databinding.FragmentOrderPutBinding
import com.tenutz.kiosksim.ui.base.BaseFragment
import com.tenutz.kiosksim.utils.ext.toCurrency
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderPutFragment: BaseFragment() {

    private var _binding: FragmentOrderPutBinding? = null
    val binding: FragmentOrderPutBinding get() = _binding!!

    private val vm: OrderPutViewModel by viewModels()
    private val args: OrderPutFragmentArgs by navArgs()

    private val adapter: OrderPutAdapter by lazy {
        OrderPutAdapter(
            onClickListener = { id, _ ->
                when(id) {
                    R.id.btn_iorderput_tail -> {
                        val menuPayment = MenuPayment(
                            listOfNotNull(
                                args.subCategoryCode,
                                args.categoryMenu.menuCode,
                                vm.checkedOptions.value!!.values.joinToString("-") {
                                    it.joinToString("-") { it.optionCode }
                                },
                            ).joinToString("-"),
                                    args.categoryMenu.menuCode,
                            "4",
                            args.categoryMenu.menuName,
                            args.categoryMenu.imageUrl,
                            args.categoryMenu.price,
                            (args.categoryMenu.price - args.categoryMenu.discountedPrice + vm.checkedOptions.value!!.values.sumOf { it.sumOf { it.price } }) * vm.quantity.value!!,
                            args.categoryMenu.discountedPrice * vm.quantity.value!!,
                            vm.quantity.value!!,
                            vm.checkedOptions.value?.let {
                                var details = ""
                                for((t, u) in it) {
                                    if(u.isEmpty()) continue
                                    details += "${vm.options.value!!.menuOptionGroups.find { it.optionGroupCode == t }?.optionGroupName ?: "알 수 없음"} : "
                                    val arrayListOf = arrayListOf<String>()
                                    for(option in u) {
                                        arrayListOf.add("${option.optionName}(${option.price.toCurrency}원)")
                                    }
                                    details += arrayListOf.joinToString(", ") + "\n"
                                }
                                details
                            } ?: "",
                        )
                        findNavController().previousBackStackEntry?.savedStateHandle?.set("args", menuPayment)
                        findNavController().popBackStack()
                    }
                }
            },
            onRequiredCheckedChangedListener = { (optionGroupCode, option) ->
                vm.setOptions(optionGroupCode, listOf(option))
            },
            onCheckedChangedListener = { (optionGroupCode, options) ->
                vm.setOptions(optionGroupCode, options)
            },
            onQuantityChangedListener = { quantity ->
                vm.setQuantity(quantity)
            },
            calculateAmount = { updateAmountUi ->
                vm.mediatorLiveData.observe(viewLifecycleOwner) { (quantity, options) ->
                    var optionAmount = 0
                    for(option in options.values) {
                        optionAmount += option.sumOf { it.price }
                    }
                    updateAmountUi((args.categoryMenu.price - args.categoryMenu.discountedPrice + optionAmount) * quantity)
                }
            },
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentOrderPutBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        observeData()
        setOnClickListeners()
    }

    private fun initViews() {
        binding.recyclerOrderPut.adapter = adapter
    }

    private fun observeData() {
        vm.options.observe(viewLifecycleOwner) { options ->
            val arrayListOf = arrayListOf<OrderPutItem>()
            arrayListOf.add(OrderPutItem.Header(args.categoryMenu))
            for(optionGroup in options.menuOptionGroups) {
                arrayListOf.add(if(optionGroup.required) OrderPutItem.OptionRequired(optionGroup) else OrderPutItem.Option(optionGroup))
            }
            arrayListOf.add(OrderPutItem.Tail)
            adapter.updateItems(arrayListOf)
        }
    }

    private fun setOnClickListeners() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}