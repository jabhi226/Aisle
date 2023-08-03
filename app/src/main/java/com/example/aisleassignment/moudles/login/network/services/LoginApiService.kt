package com.example.aisleassignment.moudles.login.network.services

import com.example.aisleassignment.moudles.core.utils.EndPoints
import com.example.aisleassignment.moudles.login.models.network.OtpValidationRequest
import com.example.aisleassignment.moudles.login.models.network.OtpValidationResponse
import com.example.aisleassignment.moudles.login.models.network.PhoneLoginRequest
import com.example.aisleassignment.moudles.login.models.network.PhoneLoginResponse
import com.example.aisleassignment.moudles.notes.models.network.UserDataResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginApiService {

    @POST(EndPoints.PHONE_NUMBER_LOGIN_END_POINT)
    suspend fun loginUsingPhoneNumber(@Body requestBody: PhoneLoginRequest): PhoneLoginResponse

    @POST(EndPoints.VERIFY_OTP_END_POINT)
    suspend fun validateOtp(@Body otpValidationRequest: OtpValidationRequest): OtpValidationResponse

    @GET(EndPoints.GET_USER_DATA)
    suspend fun getUserData(): UserDataResponse
}