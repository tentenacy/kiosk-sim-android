package com.tenutz.kiosksim.ui.access

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.tenutz.kiosksim.data.datasource.sharedpref.Settings
import com.tenutz.kiosksim.databinding.FragmentAccessBinding
import com.tenutz.kiosksim.ui.base.BaseFragment
import com.tenutz.kiosksim.ui.common.LogoutDialog
import com.tenutz.kiosksim.utils.ext.mainActivity

class AccessFragment : BaseFragment() {

    private var _binding: FragmentAccessBinding? = null
    val binding: FragmentAccessBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAccessBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnCLickListeners()
    }

    private fun setOnCLickListeners() {
        binding.imageAccessOff.setOnClickListener {
            LogoutDialog {
                mainActivity().vm.logout()
            }.show(childFragmentManager, "logoutDialog")
        }
        binding.btnAccess.setOnClickListener {
            Settings.autoEntered = true
            findNavController().navigate(AccessFragmentDirections.actionAccessFragmentToMainFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}