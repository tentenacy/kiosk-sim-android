package com.tenutz.kiosksim.ui.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.tenutz.kiosksim.databinding.FragmentReviewsBinding
import com.tenutz.kiosksim.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewsFragment : BaseFragment() {

    private var _binding: FragmentReviewsBinding? = null
    val binding: FragmentReviewsBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentReviewsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.imageReviewsBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.imageReviewsHome.setOnClickListener {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}