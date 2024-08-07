package com.tenutz.kiosksim.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.tenutz.kiosksim.data.datasource.sharedpref.Settings
import com.tenutz.kiosksim.databinding.FragmentMainBinding
import com.tenutz.kiosksim.ui.base.BaseFragment
import com.tenutz.kiosksim.ui.common.QuitDialog
import com.tenutz.kiosksim.utils.ext.mainActivity
import com.tenutz.kiosksim.utils.ext.navigateToAccessFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment() {

    private var _binding: FragmentMainBinding? = null
    val binding: FragmentMainBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.imageMainQuit.setOnClickListener {
            QuitDialog {
                Settings.autoEntered = false
                mainActivity().navigateToAccessFragment()
            }.show(childFragmentManager, "quitDialog")
        }
        binding.constraintMainOrderContainer.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToOrderSelectionFragment())
        }
        binding.constraintMainReviewContainer.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToReviewsFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}