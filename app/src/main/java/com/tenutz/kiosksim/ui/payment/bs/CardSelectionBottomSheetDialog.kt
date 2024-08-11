package com.tenutz.kiosksim.ui.payment.bs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.tenutz.kiosksim.R
import com.tenutz.kiosksim.databinding.BsCardSelectionBinding
import com.tenutz.kiosksim.ui.base.HandleDraggableBottomSheetDialogFragment
import com.tenutz.kiosksim.ui.payment.PaymentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardSelectionBottomSheetDialog(
    private val onClickListener: (Int, Any?) -> Unit,
) : HandleDraggableBottomSheetDialogFragment<BsCardSelectionBinding>(R.layout.bs_card_selection) {

    private val pVm: PaymentViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    fun setOnClickListeners() {
        binding.constraintBscardSelectionKukmin.setOnClickListener { pVm.setCreditCardCompanyInfo("01", "국민카드"); onClickListener(it.id, null); dismiss() }
        binding.constraintBscardSelectionShinhan.setOnClickListener { pVm.setCreditCardCompanyInfo("02", "신한카드"); onClickListener(it.id, null); dismiss() }
        binding.constraintBscardSelectionBc.setOnClickListener { pVm.setCreditCardCompanyInfo("03", "비씨카드"); onClickListener(it.id, null); dismiss() }
        binding.constraintBscardSelectionSamsung.setOnClickListener { pVm.setCreditCardCompanyInfo("04", "삼성카드"); onClickListener(it.id, null); dismiss() }
        binding.constraintBscardSelectionHyundai.setOnClickListener { pVm.setCreditCardCompanyInfo("05", "현대카드"); onClickListener(it.id, null); dismiss() }
        binding.constraintBscardSelectionNonghyup.setOnClickListener { pVm.setCreditCardCompanyInfo("06", "농협카드"); onClickListener(it.id, null); dismiss() }
        binding.constraintBscardSelectionLotte.setOnClickListener { pVm.setCreditCardCompanyInfo("07", "롯데카드"); onClickListener(it.id, null); dismiss() }
        binding.constraintBscardSelectionHana.setOnClickListener { pVm.setCreditCardCompanyInfo("08", "하나카드"); onClickListener(it.id, null); dismiss() }
        binding.constraintBscardSelectionWoori.setOnClickListener { pVm.setCreditCardCompanyInfo("09", "우리카드"); onClickListener(it.id, null); dismiss() }
        binding.constraintBscardSelectionCiti.setOnClickListener { pVm.setCreditCardCompanyInfo("10", "씨티카드"); onClickListener(it.id, null); dismiss() }
        binding.constraintBscardSelectionGwangju.setOnClickListener { pVm.setCreditCardCompanyInfo("11", "광주은행카드"); onClickListener(it.id, null); dismiss() }
        binding.constraintBscardSelectionJeonbuk.setOnClickListener { pVm.setCreditCardCompanyInfo("12", "전북은행카드"); onClickListener(it.id, null); dismiss() }
        binding.constraintBscardSelectionPost.setOnClickListener { pVm.setCreditCardCompanyInfo("13", "우체국카드"); onClickListener(it.id, null); dismiss() }
        binding.constraintBscardSelectionJeju.setOnClickListener { pVm.setCreditCardCompanyInfo("14", "제주카드"); onClickListener(it.id, null); dismiss() }
        binding.constraintBscardSelectionMg.setOnClickListener { pVm.setCreditCardCompanyInfo("15", "MG새마을카드"); onClickListener(it.id, null); dismiss() }
        binding.constraintBscardSelectionShinhyup.setOnClickListener { pVm.setCreditCardCompanyInfo("16", "신협카드"); onClickListener(it.id, null); dismiss() }
        binding.constraintBscardSelectionKakaobank.setOnClickListener { pVm.setCreditCardCompanyInfo("17", "카카오뱅크카드"); onClickListener(it.id, null); dismiss() }
        binding.constraintBscardSelectionKbank.setOnClickListener { pVm.setCreditCardCompanyInfo("18", "케이뱅크카드"); onClickListener(it.id, null); dismiss() }
        binding.constraintBscardSelectionSb.setOnClickListener { pVm.setCreditCardCompanyInfo("19", "저축은행카드"); onClickListener(it.id, null); dismiss() }
        binding.constraintBscardSelectionKdb.setOnClickListener { pVm.setCreditCardCompanyInfo("20", "KDB산업카드"); onClickListener(it.id, null); dismiss() }
    }
}