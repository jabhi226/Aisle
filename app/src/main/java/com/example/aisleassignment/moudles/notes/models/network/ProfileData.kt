package com.example.aisleassignment.moudles.notes.models.network

data class ProfileData(
    val invitation_type: String,
    val preferences: List<PreferenceX>,
    val question: String
)