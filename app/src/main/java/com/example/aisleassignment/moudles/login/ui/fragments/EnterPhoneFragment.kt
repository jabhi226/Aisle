package com.example.aisleassignment.moudles.login.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.aisleassignment.databinding.FragmentEnterPhoneBinding
import com.example.aisleassignment.moudles.core.utils.Resource
import com.example.aisleassignment.moudles.core.utils.Utils
import com.example.aisleassignment.moudles.login.ui.activity.LoginActivity
import com.example.aisleassignment.moudles.login.viewModel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EnterPhoneFragment : Fragment() {

    private var _binding: FragmentEnterPhoneBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEnterPhoneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initView()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.loginResponse.collectLatest {
                if (it == null) return@collectLatest
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        if (it.data == true) {
                            (requireActivity() as LoginActivity).addFragment(
                                EnterOtpFragment.newInstance(
                                    binding.etPinCode.text.toString(),
                                    binding.etPhoneNumber.text.toString()
                                )
                            )
                        } else {
                            Utils.showToast(requireContext(), "Incorrect phone number or pin code")
                        }
                    }

                    Resource.Status.ERROR -> {
                        Utils.showToast(requireContext(), "Something went wrong")
                    }

                    Resource.Status.LOADING -> {
                    }

                    else -> {
                        Utils.showToast(requireContext(), "Something went wrong")
                    }
                }
            }
        }
    }

    private fun initView() {
        binding.apply {
            etPinCode.addTextChangedListener {
                etPinCode.isFocusable = true
                etPinCode.isClickable = true
                if (!it.toString().startsWith("+")) {
                    etPinCode.setText("+${it.toString().replace("+", "")}")
                    etPinCode.setSelection(etPinCode.text.toString().length)
                }
            }
            etPinCode.doOnTextChanged { text, start, before, count ->
            }
            btnContinue.setOnClickListener {
                if (Utils.isValidMobile(etPhoneNumber.text.toString()) && etPinCode.text.isNotEmpty()) {
                    viewModel.loginUsingPhoneNumber(
                        "${etPinCode.text.toString().trim()}",
                        "${etPhoneNumber.text.toString().trim()}"
                    )
                } else {
                    Utils.showToast(
                        requireContext(),
                        "Please enter valid phone number and pin code."
                    )
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = EnterPhoneFragment()
    }
}