package com.example.aisleassignment.moudles.notes.models.network

data class Invites(
    val pending_invitations_count: Int,
    val profiles: List<Profile>,
    val totalPages: Int
)