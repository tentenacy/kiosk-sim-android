package com.tenutz.kiosksim.ui.review.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import com.tenutz.kiosksim.data.datasource.paging.entity.MenuReviews
import com.tenutz.kiosksim.databinding.TabMenuReviewsBinding
import com.tenutz.kiosksim.di.qualifier.UnitReference
import com.tenutz.kiosksim.di.qualifier.Units
import com.tenutz.kiosksim.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

@AndroidEntryPoint
class MenuReviewsTabFragment: BaseFragment() {

    private val disposable = CompositeDisposable()

    private var _binding: TabMenuReviewsBinding? = null
    val binding: TabMenuReviewsBinding get() = _binding!!

    val vm: MenuReviewsViewModel by viewModels()

    private val adapter: MenuReviewsAdapter by lazy {
        MenuReviewsAdapter { id, item ->
            when(id) {

            }
        }.apply {
//            viewLifecycleOwner.lifecycleScope.launch {
//                loadStateFlow.collectLatest {
//                    if(it.refresh is LoadState.NotLoading) {
//                        binding.textTmenuReviewsEmpty.isVisible = adapter.itemCount < 1
//                    }
//                }
//            }
            addLoadStateListener { loadState ->
                vm.empty.value =
                    loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && adapter.itemCount < 1
            }
        }
    }

    @Inject
    @field:UnitReference(Units.DP_8)
    lateinit var dividerItemDecoration: DividerItemDecoration

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = TabMenuReviewsBinding.inflate(inflater, container, false)

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
        binding.recyclerTmenuReviews.adapter = adapter
        dividerItemDecoration?.let { binding.recyclerTmenuReviews.addItemDecoration(it) }
    }

    private fun observeData() {
        vm.viewEvent.observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                when(it.first) {
                    MenuReviewsViewModel.EVENT_REFRESH_MENU_REVIEWS -> {
                        adapter.submitData(viewLifecycleOwner.lifecycle, it.second as PagingData<MenuReviews.MenuReview>)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        disposable.clear()
        super.onDestroyView()
        _binding = null
    }
}