package com.example.aisleassignment.moudles.login.repository

import com.example.aisleassignment.moudles.core.utils.Resource
import com.example.aisleassignment.moudles.notes.models.network.UserDataResponse
import com.example.aisleassignment.moudles.notes.models.ui.Notes

interface LoginRepository {

    suspend fun loginUsingPhoneNumber(phoneNumber: String): Resource<Boolean>

    suspend fun validateOtp(phoneNumber: String, otp: String): Resource<String>

    suspend fun getUserData(): Resource<ArrayList<Notes>>
}