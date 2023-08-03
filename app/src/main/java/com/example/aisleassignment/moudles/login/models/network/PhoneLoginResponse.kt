package com.example.aisleassignment.moudles.login.models.network

import com.google.gson.annotations.SerializedName

data class PhoneLoginResponse(
    @SerializedName("status") val status: Boolean
)