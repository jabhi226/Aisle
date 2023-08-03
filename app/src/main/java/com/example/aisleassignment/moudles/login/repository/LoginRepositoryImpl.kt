package com.example.aisleassignment.moudles.login.repository

import com.example.aisleassignment.moudles.core.utils.Resource
import com.example.aisleassignment.moudles.login.convertor.LoginRepositoryDataConvertor
import com.example.aisleassignment.moudles.login.models.network.OtpValidationRequest
import com.example.aisleassignment.moudles.login.models.network.PhoneLoginRequest
import com.example.aisleassignment.moudles.login.network.services.LoginApiService
import com.example.aisleassignment.moudles.notes.models.ui.Notes
import java.lang.Exception

class LoginRepositoryImpl(
    private val loginApiService: LoginApiService,
    private val loginRepositoryDataConvertor: LoginRepositoryDataConvertor
) : LoginRepository {

    override suspend fun loginUsingPhoneNumber(phoneNumber: String): Resource<Boolean> {
        return try {
            loginRepositoryDataConvertor.loginUsingPhoneNumber(
                loginApiService.loginUsingPhoneNumber(
                    PhoneLoginRequest(phoneNumber)
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(e.message.toString())
        }
    }

    override suspend fun validateOtp(phoneNumber: String, otp: String): Resource<String> {
        return try {
            loginRepositoryDataConvertor.validateOtp(
                loginApiService.validateOtp(
                    OtpValidationRequest(phoneNumber, otp)
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(e.message.toString())
        }
    }

    override suspend fun getUserData(): Resource<ArrayList<Notes>> {
        return try {
            loginRepositoryDataConvertor.getUserData(loginApiService.getUserData())
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(e.message.toString())
        }
    }

}