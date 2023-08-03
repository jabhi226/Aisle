package com.example.aisleassignment.moudles.login.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.aisleassignment.databinding.FragmentEnterOtpBinding
import com.example.aisleassignment.moudles.core.utils.Resource
import com.example.aisleassignment.moudles.core.utils.Utils
import com.example.aisleassignment.moudles.core.utils.sharedPref.SharedPref
import com.example.aisleassignment.moudles.core.utils.sharedPref.SharedPrefConstants
import com.example.aisleassignment.moudles.login.viewModel.OtpViewModel
import com.example.aisleassignment.moudles.notes.ui.activity.NotesActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EnterOtpFragment : Fragment() {

    private var _binding: FragmentEnterOtpBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<OtpViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEnterOtpBinding.inflate(inflater, container, false)
        arguments?.let { bundle ->
            bundle.getString(PIN)?.let { viewModel.pinCode = it }
            bundle.getString(PHONE)?.let { viewModel.phoneNumber = it }
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        initView()
    }

    private fun initView() {
        binding.apply {
            tvPhoneNumber.text = viewModel.pinCode + viewModel.phoneNumber
            btnContinue.setOnClickListener {
                if (etOtp.text.isNotEmpty())
                    viewModel.validateOtp(etOtp.text.toString())
                else
                    Utils.showToast(requireContext(), "Please enter OTP.")
            }
            ivEdit.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observeData() {
        lifecycleScope.launch {
            viewModel.otpResponse.collectLatest {
                if (it == null) return@collectLatest
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        SharedPref.setString(
                            requireContext(),
                            SharedPrefConstants.AUTH_TOKEN,
                            it.data
                        )
                        val i = Intent(requireContext(), NotesActivity::class.java)
                        val b = Bundle()
                        b.putString("token", it.data)
                        i.putExtra("DATA", b)
                        requireContext().startActivity(i)
                    }

                    Resource.Status.ERROR -> {
                        Utils.showToast(requireContext(), it.data.toString())
                    }

                    else -> {}
                }
            }
        }
        viewModel.countDown.removeObservers(viewLifecycleOwner)
        viewModel.countDown.observe(viewLifecycleOwner) {
            binding.tvTime.text = "00:${String.format("%02d", it)}"
        }
    }

    companion object {

        const val PIN = "PIN"
        const val PHONE = "PHONE"

        @JvmStatic
        fun newInstance(pin: String, phone: String) =
            EnterOtpFragment().apply {
                arguments = Bundle().apply {
                    putString(PIN, pin)
                    putString(PHONE, phone)
                }
            }
    }
}