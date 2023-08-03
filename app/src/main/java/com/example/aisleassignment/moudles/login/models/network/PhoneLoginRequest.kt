package com.example.aisleassignment.moudles.login.models.network

import com.google.gson.annotations.SerializedName

data class PhoneLoginRequest(
    @SerializedName("number") val number: String
)
