package com.example.aisleassignment.moudles.login.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aisleassignment.moudles.core.utils.Resource
import com.example.aisleassignment.moudles.login.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtpViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val coroutineExceptionHandler: CoroutineExceptionHandler
) : ViewModel() {

    private val _otpResponse = MutableStateFlow<Resource<String>?>(null)
    val otpResponse = _otpResponse.asStateFlow()

    var pinCode: String = ""
    var phoneNumber: String = ""

    val countDown = MutableLiveData(60)

    init {
        startCountDown()
    }

    private fun startCountDown() {
        viewModelScope.launch {
            for (i in (60) downTo (0)) {
                countDown.postValue(i)
                delay(1000)
            }
        }
    }

    fun validateOtp(otp: String) {
        CoroutineScope(Dispatchers.IO).launch(coroutineExceptionHandler) {
            _otpResponse.emit(Resource.loading(null))
            _otpResponse.emit(loginRepository.validateOtp("$pinCode$phoneNumber", otp))
        }
    }
}