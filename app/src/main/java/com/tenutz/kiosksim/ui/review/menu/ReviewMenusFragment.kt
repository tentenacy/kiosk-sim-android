package com.tenutz.kiosksim.ui.review.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tenutz.kiosksim.R
import com.tenutz.kiosksim.data.datasource.api.dto.kiosk.menu.KioskReviewMenusResponse
import com.tenutz.kiosksim.databinding.FragmentReviewMenusBinding
import com.tenutz.kiosksim.ui.base.BaseFragment
import com.tenutz.kiosksim.ui.review.menu.args.ReviewMenuArgs
import com.tenutz.kiosksim.utils.ext.observeOnce
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewMenusFragment: BaseFragment() {

    private var _binding: FragmentReviewMenusBinding? = null
    val binding: FragmentReviewMenusBinding get() = _binding!!

    private val vm: ReviewMenusViewModel by viewModels()

    private val adapter: ReviewMenusAdapter by lazy {
        ReviewMenusAdapter {
            when(it.first) {
                R.id.card_ireview_menus -> {
                    val item = it.second as KioskReviewMenusResponse.OrderMenu
                    findNavController().navigate(ReviewMenusFragmentDirections.actionReviewMenusFragmentToMenuReviewRatingFragment(ReviewMenuArgs.of(item)))
                }
            }
        }.apply { setHasStableIds(true) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentReviewMenusBinding.inflate(inflater, container, false)

        binding.vm = vm
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        observeData()
    }

    private fun initViews() {
        binding.recycleReviewMenus.adapter = adapter
    }

    private fun observeData() {
        vm.reviewMenus.observeOnce(viewLifecycleOwner) {
            if (it == null) return@observeOnce
            adapter.updateItems(it.orderMenus)
            binding.recycleReviewMenus.scrollToPosition(0)
        }
    }
}