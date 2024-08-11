package com.tenutz.kiosksim.ui.review.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import com.tenutz.kiosksim.data.datasource.paging.entity.StoreReviews
import com.tenutz.kiosksim.databinding.TabStoreReviewsBinding
import com.tenutz.kiosksim.di.qualifier.UnitReference
import com.tenutz.kiosksim.di.qualifier.Units
import com.tenutz.kiosksim.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

@AndroidEntryPoint
class StoreReviewsTabFragment: BaseFragment() {

    private val disposable = CompositeDisposable()

    private var _binding: TabStoreReviewsBinding? = null
    val binding: TabStoreReviewsBinding get() = _binding!!

    val vm: StoreReviewsViewModel by viewModels()

    private val adapter: StoreReviewsAdapter by lazy {
        StoreReviewsAdapter { id, item ->
            when(id) {

            }
        }.apply {
            addLoadStateListener { loadState ->
                vm.empty.value =
                    loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && adapter.itemCount < 1
            }
        }
    }

    @Inject
    @field:UnitReference(Units.DP_8)
    lateinit var dividerItemDecoration: DividerItemDecoration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.storeReviews()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = TabStoreReviewsBinding.inflate(inflater, container, false)

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
        binding.recyclerTstoreReviews.adapter = adapter
        dividerItemDecoration?.let { binding.recyclerTstoreReviews.addItemDecoration(it) }
    }

    private fun observeData() {
        vm.viewEvent.observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                when(it.first) {
                    StoreReviewsViewModel.EVENT_REFRESH_MENU_REVIEWS -> {
                        adapter.submitData(viewLifecycleOwner.lifecycle, it.second as PagingData<StoreReviews.StoreReview>)
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