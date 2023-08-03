package com.example.aisleassignment.moudles.login.viewModel

import androidx.lifecycle.ViewModel
import com.example.aisleassignment.moudles.core.utils.Resource
import com.example.aisleassignment.moudles.login.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val coroutineExceptionHandler: CoroutineExceptionHandler
) : ViewModel() {

    private val _loginResponse = MutableSharedFlow<Resource<Boolean>?>()
    val loginResponse = _loginResponse.asSharedFlow()

    fun loginUsingPhoneNumber(pinCode: String, phoneNumber: String) {
        CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
            _loginResponse.emit(Resource.loading(null))
            _loginResponse.emit(loginRepository.loginUsingPhoneNumber("$pinCode$phoneNumber"))
        }
    }
}