package com.example.aisleassignment.moudles.login.models.network

import com.google.gson.annotations.SerializedName

data class OtpValidationRequest(
    @SerializedName("number") val number: String,
    @SerializedName("otp") val otp: String,
)
