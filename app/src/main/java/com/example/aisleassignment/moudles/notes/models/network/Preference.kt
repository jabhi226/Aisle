package com.example.aisleassignment.moudles.notes.models.network

data class Preference(
    val answer_id: Int,
    val id: Int,
    val preference_question: PreferenceQuestion,
    val value: Int
)