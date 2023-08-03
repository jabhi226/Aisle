package com.example.aisleassignment.moudles.login.models.network

import com.google.gson.annotations.SerializedName

data class OtpValidationResponse(
    @SerializedName("token") val token: String?
)