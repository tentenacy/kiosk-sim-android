package com.tenutz.kiosksim.ui.review.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.tenutz.kiosksim.databinding.FragmentMenuReviewRatingBinding
import com.tenutz.kiosksim.ui.base.BaseFragment
import com.tenutz.kiosksim.utils.MyToast
import com.tenutz.kiosksim.utils.ext.mainActivity
import com.tenutz.kiosksim.utils.validation.Validator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuReviewRatingFragment: BaseFragment() {


    private var _binding: FragmentMenuReviewRatingBinding? = null
    val binding: FragmentMenuReviewRatingBinding get() = _binding!!

    private val vm: MenuReviewRatingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMenuReviewRatingBinding.inflate(inflater, container, false)

        binding.vm = vm
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.btnMenuReviewRating.setOnClickListener {
            Validator.validate(
                onValidation = { Validator.validateRating(vm.rating.value!!) },
                onSuccess = {
//                    findNavController().navigate(MenuReviewRatingFragmentDirections.)
                },
                onFailure = { MyToast.create(mainActivity(), it.errorCode.message, 100)?.show() },
            )

        }
    }
}