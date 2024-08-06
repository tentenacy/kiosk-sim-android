package com.tenutz.kiosksim.ui.access

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tenutz.kiosksim.data.datasource.sharedpref.Settings
import com.tenutz.kiosksim.databinding.FragmentAccessBinding
import com.tenutz.kiosksim.ui.base.BaseFragment
import com.tenutz.kiosksim.ui.common.LogoutDialog
import com.tenutz.kiosksim.utils.MyToast
import com.tenutz.kiosksim.utils.ext.mainActivity
import com.tenutz.kiosksim.utils.validation.Validator
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.safeCast

@AndroidEntryPoint
class AccessFragment : BaseFragment() {

    private var _binding: FragmentAccessBinding? = null
    val binding: FragmentAccessBinding get() = _binding!!

    private val vm: AccessViewModel by viewModels()

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
        observeData()
    }

    private fun observeData() {
        vm.viewEvent.observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                when (it.first) {
                    AccessViewModel.EVENT_NAVIGATE_TO_MAIN -> {
                        Settings.autoEntered = binding.checkAccessRemember.isChecked
                        findNavController().navigate(AccessFragmentDirections.actionAccessFragmentToMainFragment())
                    }
                    AccessViewModel.EVENT_TOAST -> {
                        String::class.safeCast(it.second)?.let { message ->
                            MyToast.create(mainActivity(), message, 80)?.show()
                        }
                    }
                }
            }
        }
    }

    private fun setOnCLickListeners() {
        binding.imageAccessOff.setOnClickListener {
            LogoutDialog {
                mainActivity().vm.logout()
            }.show(childFragmentManager, "logoutDialog")
        }
        binding.btnAccess.setOnClickListener {
            Validator.validate(
                onValidation = {
                    Validator.validateRequiredInput(binding.editAccessKioskCode.text.toString())
                },
                onSuccess = {
                    vm.storeExists(binding.editAccessKioskCode.text.toString())
                },
                onFailure = { e ->
                    MyToast.create(mainActivity(), e.errorCode.message, 80)?.show()
                }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}