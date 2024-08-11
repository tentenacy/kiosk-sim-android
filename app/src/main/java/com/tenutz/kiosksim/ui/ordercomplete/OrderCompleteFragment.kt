package com.tenutz.kiosksim.ui.ordercomplete

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tenutz.kiosksim.databinding.FragmentOrderCompleteBinding
import com.tenutz.kiosksim.ui.base.BaseFragment
import com.tenutz.kiosksim.utils.ext.mainActivity
import com.tenutz.kiosksim.utils.ext.navigateToMainFragment
import dagger.hilt.android.AndroidEntryPoint
import hu.akarnokd.rxjava3.operators.FlowableTransformers
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.processors.PublishProcessor
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class OrderCompleteFragment : BaseFragment() {

    private val compositeDisposable = CompositeDisposable()
    private val valve = PublishProcessor.create<Boolean>()
    private var count = 5

    private var _binding: FragmentOrderCompleteBinding? = null
    val binding: FragmentOrderCompleteBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Flowable.interval(1000, TimeUnit.MILLISECONDS)
            .compose(FlowableTransformers.valve(valve, true))
            .debounce(10, TimeUnit.MILLISECONDS)
            .take(5)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete { mainActivity().navigateToMainFragment() }
            .subscribe {
                binding.textOrderCompleteToMainSeconds.text = (--count).toString()
            }.addTo(compositeDisposable)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentOrderCompleteBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        valve.onNext(true)
    }

    override fun onPause() {
        super.onPause()
        valve.onNext(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
        _binding = null
    }
}