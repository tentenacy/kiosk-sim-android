package com.tenutz.kiosksim.ui.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.orhanobut.logger.Logger
import com.tenutz.kiosksim.databinding.FragmentReviewsBinding
import com.tenutz.kiosksim.ui.base.BaseFragment
import com.tenutz.kiosksim.utils.ext.mainActivity
import com.tenutz.kiosksim.utils.ext.navigateToMainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewsFragment: BaseFragment() {

    private var _binding: FragmentReviewsBinding? = null
    val binding: FragmentReviewsBinding get() = _binding!!

    val vm: ReviewsViewModel by viewModels()

    private val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            when (position) {
                ReviewsPagerAdapter.MENU_REVIEWS_PAGE_INDEX -> {
                    Logger.d("tab changed to MenuReviewsTabFragment")
                    binding.fabReviews.setOnClickListener {
                        findNavController().navigate(ReviewsFragmentDirections.actionReviewsFragmentToReviewMenusFragment())
                    }
                }
                ReviewsPagerAdapter.STORE_REVIEWS_PAGE_INDEX -> {
                    Logger.d("tab changed to StoreReviewsTabFragment")
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentReviewsBinding.inflate(inflater, container, false)

        binding.vpagerReviews.adapter = ReviewsPagerAdapter(this)

        TabLayoutMediator(binding.tabReviews, binding.vpagerReviews) { tab, position ->
            tab.text = when(position) {
                ReviewsPagerAdapter.MENU_REVIEWS_PAGE_INDEX -> {
                    "메뉴 평가"
                }
                ReviewsPagerAdapter.STORE_REVIEWS_PAGE_INDEX -> {
                    "가맹점 평가"
                }
                else -> null
            }
        }.attach()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vpagerReviews.registerOnPageChangeCallback(onPageChangeCallback)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.imageReviewsBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.imageReviewsHome.setOnClickListener {
            mainActivity().navigateToMainFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}